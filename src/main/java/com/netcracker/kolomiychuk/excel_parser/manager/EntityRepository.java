package com.netcracker.kolomiychuk.excel_parser.manager;


import com.netcracker.kolomiychuk.excel_parser.entities.Author;
import com.netcracker.kolomiychuk.excel_parser.entities.Book;
import com.netcracker.kolomiychuk.excel_parser.entities.BookShelf;
import com.netcracker.kolomiychuk.excel_parser.entities.Entity;
import javafx.util.Pair;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
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
            "( shelf_number, length) " +
            "VALUES(?, ?)";

    public JdbcTemplate jdbcTemplate;

    public EntityRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insertAll(Collection<Entity> entities) {
        for (Entity entity : entities) {
            getParametersForEntityAndInsert(entity);
        }
    }

    public void getParametersForEntityAndInsert (Entity entity){
        ArrayList<Object> objects = new ArrayList<>();
        if (entity instanceof Book) {
            objects.add(((Book) entity).getName());
            objects.add(((Book) entity).getPagesCount());
            insertEntity(CREATE_BOOK, objects.toArray());
        } else if (entity instanceof Author) {
            objects.add(((Author) entity).getFirstName());
            objects.add(((Author) entity).getLastName());
            objects.add(((Author) entity).getAge());
            insertEntity(CREATE_AUTHORS, objects.toArray());
        } else if (entity instanceof BookShelf) {
            objects.add(((BookShelf) entity).getShelfNumber());
            objects.add(((BookShelf) entity).getLength());
            insertEntity(CREATE_BOOKSHELFS, objects.toArray());
        }
    }


    @Transactional
    public void insertEntity (String query, Object[] parameters) {
        jdbcTemplate.update(query,getPrepareStatementSetter(parameters));
    }

    private PreparedStatementSetter getPrepareStatementSetter ( Object[] parameters) {
        return new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                for (int i = 0; i < parameters.length; i++) {
                        ps.setObject(i+1, parameters[i]);
                }
            }
        };
    }


}

