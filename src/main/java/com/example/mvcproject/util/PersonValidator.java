package com.example.mvcproject.util;

import com.example.mvcproject.dao.PersonDaoJdbcTemplate;
import com.example.mvcproject.madel.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class PersonValidator implements Validator {
    private final PersonDaoJdbcTemplate personDaoJdbcTemplate;

    @Override
    public boolean supports(Class<?> aClazz) {
        return Person.class.equals(aClazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;
        if (personDaoJdbcTemplate.show(person.getEmail()).isPresent()) {
            errors.rejectValue("email", "", "This email is already taken");
        }
    }
}
