package uzb.aminasaidakhmedova.jdbc_spring_example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uzb.aminasaidakhmedova.jdbc_spring_example.model.Book;

import javax.sql.DataSource;
import java.sql.*;
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
        String SQL_findAllBooks = "Select * from books;";

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_findAllBooks)) {
            while (resultSet.next()) {
                Book book = convertRowToBook(resultSet);
                result.add(book);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return result;
    }

        private Book convertRowToBook (ResultSet resultSet) throws SQLException {
            Long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new Book(id, name);
        }

    public Book findBookById(Long id) {
        String SQL_findBookById = "SELECT * FROM books WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_findBookById)) {
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return convertRowToBook(resultSet);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
