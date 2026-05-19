package backend_assignment.demo.services;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import backend_assignment.demo.models.Book;
import backend_assignment.demo.repository.BookRepository;
import backend_assignment.demo.validators.BookProcessor;
import backend_assignment.demo.validators.BookValidator;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookValidator bookValidator;
    // We're using strategy pattern with DI here, see the BookProcessor interface for more 
    private final Map<String, BookProcessor> processors;
    
    public BookService(BookRepository bookRepository,
    		List<BookProcessor> processorList, BookValidator bookValidator) {
		this.bookRepository = bookRepository;
		this.bookValidator = bookValidator;
        this.processors = processorList.stream()
                .collect(Collectors.toMap(
                    BookProcessor::getType,
                    Function.identity()
                ));
    }
    
    public List<Book> getBooks() {
    	return bookRepository.getBooks();
    }
    
    public Book getBook(String id) {
        return bookRepository.getBook(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
    }
    
    public Book addBook(Book book, String processorType) {
    	bookValidator.validate(book);
        BookProcessor processor = processors.get(processorType);
        if (processor == null) {
            throw new IllegalArgumentException("Unknown processor type: " + processorType);
        }
        processor.process(book);
        return bookRepository.addBook(book);
    }
    
    public void deleteBook(String id) {
        if (bookRepository.getBook(id).isEmpty()) {
            throw new RuntimeException("Book not found with id: " + id);
        }
        bookRepository.deleteBook(id);
    }
}
