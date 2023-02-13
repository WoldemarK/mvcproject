package com.example.mvcproject.dao;
import com.example.mvcproject.PersonMapper.PersonMapper;
import com.example.mvcproject.madel.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.util.List;
@Component
@RequiredArgsConstructor
public class PersonDaoJdbcTemplate {
    private final JdbcTemplate jdbcTemplate;
    private static final String SQL_GET_ALL_PERSON = "SELECT * FROM Person";
    private static final String SQL_INSERT_PERSON = "insert into person values(1,?,?,?)";
    private static final String SQL_GET_PERSON_BY_ID = "select * from person where id =?";
    private static final String SQL_UPDATE_PERSON = "update person set name=?, age=?, email=? where id=?";
    private static final String SQL_DELETE_PERSON_BY_ID = "delete from person where id=?";

    public List<Person> index() {
        return jdbcTemplate.query(SQL_GET_ALL_PERSON, new PersonMapper());
    }

    public Person show(int id) {
        return jdbcTemplate.query(SQL_GET_PERSON_BY_ID,
                        new Object[]{id},
                        new BeanPropertyRowMapper<>(Person.class))
                .stream()
                .findAny()
                .orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update(SQL_INSERT_PERSON,
                person.getName(),
                person.getEmail(),
                person.getAge());
    }


    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update(SQL_UPDATE_PERSON,
                updatedPerson.getName(),
                updatedPerson.getEmail(),
                updatedPerson.getAge(),
                id
        );
    }

    public void delete(int id) {
        jdbcTemplate.update(SQL_DELETE_PERSON_BY_ID, id);
    }
}
