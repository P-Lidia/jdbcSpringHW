package ru.itgirl.jdbc_spring_example.model.repository.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.itgirl.jdbc_spring_example.model.entity.Book;
import ru.itgirl.jdbc_spring_example.model.repository.BookRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepositoryImpl implements BookRepository {
    @Autowired
    private DataSource dataSource;

    public BookRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Book> findAllBooks() {
        List<Book> result = new ArrayList<>();
        String SQL_findAllBooks = "SELECT * FROM books;";
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_findAllBooks)) {
            while (resultSet.next()) {
                Book book = converRowToBook(resultSet);
                result.add(book);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return result;
    }

    @Override
    public Book findBookById(Long id) {
        String SQL_findById = new StringBuilder().append("SELECT * FROM books WHERE id = ").append(id).toString();
        Book book = new Book();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_findById)) {
            while (resultSet.next()) {
                book = converRowToBook(resultSet);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return book;
    }

    private Book converRowToBook(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        return new Book(id, name);
    }
}
