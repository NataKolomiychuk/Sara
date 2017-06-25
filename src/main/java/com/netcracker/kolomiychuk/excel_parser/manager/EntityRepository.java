package com.netcracker.kolomiychuk.excel_parser.manager;


import com.netcracker.kolomiychuk.excel_parser.entities.Author;
import com.netcracker.kolomiychuk.excel_parser.entities.Book;
import com.netcracker.kolomiychuk.excel_parser.entities.BookShelf;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

@Component
public class EntityRepository {
    public static final String CREATE_BOOK = ""+
            "INSERT INTO BOOKS" +
            "( name, pages_count) " +
            "VALUES(?, ?)";

    public static final String CREATE_AUTHORS = ""+
            "INSERT INTO AUTHORS" +
            "( first_name, last_name, age) " +
            "VALUES(?, ?, ?)";

    public static final String CREATE_BOOKSHELFS = ""+
            "INSERT INTO BOOKSHELFS" +
            "(  length) " +
            "VALUES(?)";

    public JdbcTemplate jdbcTemplate;

    public EntityRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insertAll(Collection<? extends Object> entities) {
        for (Object entity:entities) {
            if (entity.getClass().equals(Book.class)) {
                insertBook((Book)entity);
            };
            if (entity.getClass().equals(Author.class)) {
                insertAuthor((Author)entity);

            };
            if (entity.getClass().equals(BookShelf.class)) {
                insertBookShelf((BookShelf)entity);
            };
        }
    }
    @Transactional
    public Book insertBook(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps =
                        connection.prepareStatement(CREATE_BOOK, new String[] {"book_id"});
                int i = 0;
                ps.setString(++i, book.getName());
                ps.setInt(++i, book.getPagesCount());
                return ps;
            }
        }, keyHolder);
        book.setBookId(keyHolder.getKey().intValue());
        return book;
    }

    @Transactional
    public Author insertAuthor(Author author) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps =
                        connection.prepareStatement(CREATE_AUTHORS, new String[] {"author_id"});
                int i = 0;
                ps.setString(++i, author.getFirstName());
                ps.setString(++i, author.getLastName());
                ps.setInt(++i, author.getAge());
                return ps;
            }
        }, keyHolder);
        author.setAuthorId(keyHolder.getKey().intValue());
        return author;
    }

    @Transactional
    public BookShelf insertBookShelf(BookShelf bookShelf) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps =
                        connection.prepareStatement(CREATE_BOOKSHELFS, new String[] {"book_shelf_id"});
                int i = 0;
                ps.setInt(++i, bookShelf.getLength());
                return ps;
            }
        }, keyHolder);
        bookShelf.setShelfNumber(keyHolder.getKey().intValue());
        return bookShelf;
    }
}

