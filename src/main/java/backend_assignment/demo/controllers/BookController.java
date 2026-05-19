package backend_assignment.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import backend_assignment.demo.models.Book;
import backend_assignment.demo.services.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {
	@Autowired
	BookService bookService; 

	// This endpoint is just for debugging and demo purposes, not a requirement
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getBooks());
    }
    
    
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable String id) {
        return ResponseEntity.ok(bookService.getBook(id));
    }
    
    // the processorType is to showcase how we use 
    // dependency injection at runtime. Consumers can specify what processor strategy
    // they want in the request. See BookProcessor for more.
    // We also provide default value because user will probably forget to input.
    @PostMapping
    public ResponseEntity<Book> addBook(
            @RequestBody Book book,
            @RequestParam(value = "processorType", defaultValue = "sanitising") String processorType) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.addBook(book, processorType));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable String id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok("Book deleted successfully");
    }


}

