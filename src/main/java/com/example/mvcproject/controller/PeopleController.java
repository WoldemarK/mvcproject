package com.example.mvcproject.controller;

import com.example.mvcproject.dao.PersonDaoJdbcAip;
import com.example.mvcproject.madel.Person;
import com.example.mvcproject.util.PersonValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/people")
public class PeopleController {
    private final PersonDaoJdbcAip personDAOJDBCAIP;
    private final PersonValidator validator;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", personDAOJDBCAIP.index());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAOJDBCAIP.show(id));
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {

        validator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return "people/new";

        personDAOJDBCAIP.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personDAOJDBCAIP.show(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id) {

        validator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return "people/edit";

        personDAOJDBCAIP.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personDAOJDBCAIP.delete(id);
        return "redirect:/people";
    }
}
