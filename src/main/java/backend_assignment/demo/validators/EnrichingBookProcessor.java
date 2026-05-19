package backend_assignment.demo.validators;

import org.springframework.stereotype.Service;

import backend_assignment.demo.models.Book;


@Service("enriching")
public class EnrichingBookProcessor implements BookProcessor {
    @Override
    public Book process(Book book) {
        book.setTitle("[ENRICHED] " + book.getTitle());
        return book;
    }
    
    @Override
    public String getType() { return "enriching"; }

}
