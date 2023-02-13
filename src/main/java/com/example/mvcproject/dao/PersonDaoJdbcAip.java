package com.example.mvcproject.dao;

import com.example.mvcproject.madel.Person;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDaoJdbcAip {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USERNAME = "user";
    private static final String PASSWORD = "123";
    private static Connection connection;
    private static final String SQL_GET_ALL_PERSON = "SELECT * FROM Person";
    private static final String SQL_INSERT_PERSON = "insert into person values(1,?,?,?,?)";
    private static final String SQL_GET_PERSON_BY_ID = "select * from person where id =?";
    private static final String SQL_UPDATE_PERSON = "update person set name=?, age=?, email=? where id=?";
    private static final String SQL_DELETE_PERSON_BY_ID = "delete from person where id=?";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Person> index() {
        List<Person> people = new ArrayList<>();

        try {
            var preparedStatement = connection.prepareStatement(SQL_GET_ALL_PERSON);

            var resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                var person = Person.builder()

                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .email(resultSet.getString("email"))
                        .age(resultSet.getInt("age"))
                        .build();

                people.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return people;
    }

    public Person show(int id) {
        Person person = null;
        try {
            var preparedStatement = connection.prepareStatement(SQL_GET_PERSON_BY_ID);

            preparedStatement.setInt(1, id);

            var resultSet = preparedStatement.executeQuery();

            resultSet.next();

            person = Person.builder()
                    .id(resultSet.getInt("id"))
                    .name(resultSet.getString("name"))
                    .email(resultSet.getString("email"))
                    .age(resultSet.getInt("age"))
                    .build();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }

    public void save(Person person) {
        try {
            var preparedStatement = connection.prepareStatement(SQL_INSERT_PERSON);

            preparedStatement.setInt(1, person.getId());
            preparedStatement.setString(2, person.getName());
            preparedStatement.setString(3, person.getEmail());
            preparedStatement.setInt(4, person.getAge());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void update(int id, Person updatedPerson) {
        try {
            var preparedStatement = connection.prepareStatement(SQL_UPDATE_PERSON);

            preparedStatement.setInt(1, updatedPerson.getId());
            preparedStatement.setString(2, updatedPerson.getName());
            preparedStatement.setString(3, updatedPerson.getEmail());
            preparedStatement.setInt(4, updatedPerson.getAge());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int id) {
        try {
            var preparedStatement = connection.prepareStatement(SQL_DELETE_PERSON_BY_ID);

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

