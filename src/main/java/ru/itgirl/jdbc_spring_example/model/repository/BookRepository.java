package ru.itgirl.jdbc_spring_example.model.repository;



import ru.itgirl.jdbc_spring_example.model.entity.Book;

import java.util.List;

public interface BookRepository {
    List<Book> findAllBooks();
    Book findBookById(Long id);
}
