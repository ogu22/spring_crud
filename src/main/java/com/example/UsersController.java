package com.example;

import static com.example.util.ImageTypeCheck.getFormat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

	@RequestMapping(path = "/users/upload", method = RequestMethod.PUT)
	public void upload(InputStream req) throws IOException {

		ByteArrayOutputStream byteos = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		int size = 0;
		while ((size = req.read(buf, 0, buf.length)) != -1) {
			byteos.write(buf, 0, size);
		}

		String filename = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").format(LocalDateTime.now());
		Path uploadfile = Paths.get("/Users/kusakai/Documents/workspace-sts-3.8.4.RELEASE/demo-kusa/image/" + filename
				+ "." + getFormat(byteos.toByteArray()));

		try (OutputStream os = Files.newOutputStream(uploadfile, StandardOpenOption.CREATE)) {
			os.write(byteos.toByteArray());
		} catch (IOException ex) {
			System.err.println(ex);
		}
	}
}
