package com.example;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SampleController {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@ModelAttribute
	UserForm userForm() {
		return new UserForm();
	}

	@RequestMapping(path = "/sample", method = RequestMethod.GET)
	String index(Model model) {
		List<User> list = jdbcTemplate.queryForObject("select * from user", new UserMapper());
		model.addAttribute("list", list);
		return "sample/index";
	}

	@RequestMapping(path = "/sample/{id}", method = RequestMethod.GET)
	String show(Model model, @PathVariable("id") int id) {
		List<User> list = jdbcTemplate.queryForObject("select * from user where id = ? ", new Object[] { id },
				new UserMapper());
		model.addAttribute("list", list);
		return "sample/index";
	}

	@RequestMapping(path = "/sample", method = RequestMethod.POST)
	String create(Model model, @ModelAttribute UserForm userForm) {
		jdbcTemplate.update("INSERT INTO user (name) values (?)", userForm.getName());
		return "redirect:/sample";
	}

	@RequestMapping(path = "/sample/{id}", method = RequestMethod.PUT)
	String update(Model model, @ModelAttribute UserForm userForm, @PathVariable("id") int id) {
		jdbcTemplate.update("UPDATE user SET name = ? where id = ? ", userForm.getName(), id);
		return "redirect:/sample";
	}

	@RequestMapping(path = "/sample/{id}", method = RequestMethod.DELETE)
	String destory(Model model, @PathVariable("id") int id) {
		jdbcTemplate.update("delete from user where id = ? ", id);
		return "redirect:/sample";
	}
}
