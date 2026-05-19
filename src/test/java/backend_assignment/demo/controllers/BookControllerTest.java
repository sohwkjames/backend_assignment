package backend_assignment.demo.controllers;

import backend_assignment.demo.exceptions.GlobalExceptionHandler;
import backend_assignment.demo.models.Book;
import backend_assignment.demo.services.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BookControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private ObjectMapper objectMapper;
    private Book sampleBook;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockMvc = MockMvcBuilders
                .standaloneSetup(bookController)
                .setControllerAdvice(new GlobalExceptionHandler()) // wire in your exception handler
                .build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        sampleBook = new Book("1", "Dune", "Frank Herbert", "Science Fiction", LocalDate.of(1965, 8, 1));
    }

    @Test
    void getAllBooks_returnsListOfBooks() throws Exception {
        when(bookService.getBooks()).thenReturn(List.of(sampleBook));

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Dune"))
                .andExpect(jsonPath("$[0].author").value("Frank Herbert"));
    }

    @Test
    void getBookById_returnsBook() throws Exception {
        when(bookService.getBook("1")).thenReturn(sampleBook);

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Dune"));
    }

    @Test
    void getBookById_notFound_returns404() throws Exception {
        when(bookService.getBook("99"))
                .thenThrow(new RuntimeException("Book not found with id: 99"));

        mockMvc.perform(get("/api/books/99"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Book not found with id: 99"));
    }

    @Test
    void addBook_returnsCreatedBook() throws Exception {
        when(bookService.addBook(any(Book.class), eq("sanitising"))).thenReturn(sampleBook);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleBook)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Dune"));
    }

    @Test
    void addBook_invalidBody_returns400() throws Exception {
        when(bookService.addBook(any(Book.class), eq("sanitising")))
                .thenThrow(new IllegalArgumentException("Title is required"));

        Book invalidBook = new Book(null, null, "Frank Herbert", "Science Fiction", LocalDate.of(1965, 8, 1));

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidBook)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Title is required")); 

    }

    @Test
    void deleteBook_returnsSuccess() throws Exception {
        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Book deleted successfully"));
    }

    @Test
    void deleteBook_notFound_returns404() throws Exception {
        doThrow(new RuntimeException("Book not found with id: 99"))
                .when(bookService).deleteBook("99");

        mockMvc.perform(delete("/api/books/99"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Book not found with id: 99"));
    }
}