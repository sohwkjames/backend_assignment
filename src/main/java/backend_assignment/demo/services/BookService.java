package backend_assignment.demo.services;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import backend_assignment.demo.models.Book;
import backend_assignment.demo.repository.BookRepository;
import backend_assignment.demo.validators.BookProcessor;

@Service
public class BookService {
    private final BookRepository bookRepository;
    
    // We're using strategy pattern with DI here, see the BookProcessor interface for more 
    private final Map<String, BookProcessor> processors;
    
    public BookService(BookRepository bookRepository,
    		List<BookProcessor> processorList) {
		this.bookRepository = bookRepository;
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
    	return bookRepository.getBook(id).get();
    }
    
    public Book addBook(Book book) {    	
    	bookRepository.addBook(book);
        return book;
    }

//    public boolean deleteBook(String id) {
//        return bookRepository.removeIf(b -> b.getId().equals(id));
//    }
//
    
    
    
    


}
