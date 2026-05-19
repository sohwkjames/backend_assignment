package backend_assignment.demo.validators;

import org.springframework.stereotype.Service;

import backend_assignment.demo.models.Book;

@Service("sanitising")
public class SanitisingBookProcessor implements BookProcessor {
    @Override
    public Book process(Book book) {
        book.setTitle(book.getTitle().trim());
        return book;
    }
    
    @Override
    public String getType() { return "sanitising"; }
}
