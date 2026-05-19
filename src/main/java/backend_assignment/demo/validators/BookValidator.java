package backend_assignment.demo.validators;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import backend_assignment.demo.models.Book;


// This class only checks for the validity of the incoming request, without
// making trips to the repository layer.
@Component
public class BookValidator {
	public void validate(Book book) {
        if (book.getTitle() == null || book.getTitle().isBlank()) {
            throw new IllegalArgumentException("Title is required");
        }
        if (book.getAuthor() == null || book.getAuthor().isBlank()) {
            throw new IllegalArgumentException("Author is required");
        }
        if (book.getGenre() == null || book.getGenre().isBlank()) {
            throw new IllegalArgumentException("Genre is required");
        }
        if (book.getPublishedDate() == null) {
            throw new IllegalArgumentException("Published date is required");
        }
        if (book.getPublishedDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Published date cannot be in the future");
        }

	}
}
