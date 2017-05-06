package com.example;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.Repository.UsersRepository;

@RestController
public class UsersController {

	@Autowired
	private UsersRepository repository;

	@RequestMapping(path = "/users", method = RequestMethod.GET)
	@Transactional
	public List<User> get() {
		return repository.findAll();
	}

	@RequestMapping(path = "/users/{id}", method = RequestMethod.GET)
	public List<User> show(Model model, @PathVariable("id") int id) {
		return repository.findById(id);
	}

	@RequestMapping(path = "/users", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public User create(Model model, @RequestBody User user) {
		return repository.save(user);
	}

	@RequestMapping(path = "/users/{id}", method = RequestMethod.PUT)
	public User update(Model model, @PathVariable("id") int id, @RequestBody User user) {
		user.setId(id);
		return repository.save(user);
	}

	@RequestMapping(path = "/users/{id}", method = RequestMethod.DELETE)
	public void destory(Model model, @PathVariable("id") int id) {
		repository.delete(id);
	}
}
