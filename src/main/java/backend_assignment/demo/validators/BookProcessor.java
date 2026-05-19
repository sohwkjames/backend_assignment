package backend_assignment.demo.validators;

import backend_assignment.demo.models.Book;

// This approach allows Spring Boot to choose which BookProcessor to use
// during runtime. 
// main upsides: Flexible (a bit like Strategy design pattern), 
// and easy to extend more types of processors without affecting other classes.
public interface BookProcessor {
	Book process(Book book);
    String getType();
}
