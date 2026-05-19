package backend_assignment.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend_assignment.demo.models.Book;
import backend_assignment.demo.services.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {
	@Autowired
	BookService bookService; 

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getBooks();
    }
    
    @GetMapping("/{id}")
    public Book getBook(@PathVariable String id) {
        return bookService.getBook(id);
    }
}
