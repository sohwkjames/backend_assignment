package backend_assignment.demo.repository;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import backend_assignment.demo.models.Book;
import jakarta.annotation.PostConstruct;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@Repository
public class BookRepository {
    private final List<Book> books = new ArrayList<Book>();
    private final ObjectMapper objectMapper;
    private int nextId = 1;

    public BookRepository(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    // Loads book data form json to memory
    @PostConstruct
    public void load() throws Exception {
        InputStream is = new ClassPathResource("books.json").getInputStream();
        List<Book> loaded = objectMapper.readValue(is, new TypeReference<List<Book>>() {});
        
        books.addAll(loaded);
        nextId = books.size() + 1; // start after the preloaded data
    }
    
    public List<Book> getBooks() {
    	return books;
    }
    
    public Optional<Book> getBook(String id) {
        return books.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst();
    }
    
    public Book addBook(Book book) {
        book.setId(String.valueOf(nextId++));
        books.add(book);
        return book;
    }

    public boolean deleteBook(String id) {
        return books.removeIf(b -> b.getId().equals(id));
    }

}
