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
public class Insert {
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
            "( book_shelf_id, length) " +
            "VALUES(?, ?)";

    public JdbcTemplate jdbcTemplate;

    public Insert(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insertAll(Collection<? extends Object> collection) {
        for (Object obj:collection) {
            if (obj.getClass().equals(Book.class)) {
                createBook((Book)obj);
            };
            if (obj.getClass().equals(Author.class)) {
                createAuthors((Author)obj);

            };
            if (obj.getClass().equals(BookShelf.class)) {
                createBookShelfs((BookShelf)obj);
            };
        }
    }
    @Transactional
    public Book createBook(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps =
                        connection.prepareStatement(CREATE_BOOK, new String[] {"book_id"});
                int i = 0;
                ps.setString(++i, book.getName());
                ps.setString(++i, String .valueOf(book.getPagesCount()));
                return ps;
            }
        }, keyHolder);
        book.setBookId(keyHolder.getKey().intValue());
        return book;
    }

    @Transactional
    public Author createAuthors(Author author) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps =
                        connection.prepareStatement(CREATE_AUTHORS, new String[] {"author_id"});
                int i = 0;
                ps.setString(++i, author.getFirstName());
                ps.setString(++i, author.getLastName());
                ps.setString(++i, String .valueOf(author.getAge()));
                return ps;
            }
        }, keyHolder);
        author.setAuthor_id(keyHolder.getKey().intValue());
        return author;
    }

    @Transactional
    public BookShelf createBookShelfs(BookShelf bookShelf) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps =
                        connection.prepareStatement(CREATE_BOOKSHELFS, new String[] {"book_shelf_id"});
                int i = 0;
                ps.setString(++i, String.valueOf(bookShelf.getShelfNumber()));
                ps.setString(++i, String .valueOf(bookShelf.getLength()));
                return ps;
            }
        }, keyHolder);
        return bookShelf;
    }
}

