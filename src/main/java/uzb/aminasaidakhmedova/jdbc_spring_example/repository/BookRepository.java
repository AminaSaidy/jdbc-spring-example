package uzb.aminasaidakhmedova.jdbc_spring_example.repository;

import uzb.aminasaidakhmedova.jdbc_spring_example.model.Book;

import java.util.List;

public interface BookRepository {
    List<Book> findAllBooks();
    Book findBookById(Long id);
}
