package kr.or.connect.todo.presentation;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import kr.or.connect.todo.domain.Todo;
import kr.or.connect.todo.service.TodoService;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
	private final TodoService service;
	private final Logger log = LoggerFactory.getLogger(TodoController.class);

	@Autowired
	public TodoController(TodoService service) {
		this.service = service;
	}
	
	@CrossOrigin
	@GetMapping
	Collection<Todo> readList() {
		log.info("todo readList! ");
		return service.findAll();
	}

	@CrossOrigin
	@GetMapping("/{id}")
	Todo read(@PathVariable  Integer id) {
		log.info("todo read : {}" , id);
		return service.findById(id);
	}
	
	@CrossOrigin
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	Todo create(@RequestBody Todo todo) {
		log.info("todo created : {}" , todo);
		return service.create(todo);
	}
	
	@CrossOrigin
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void update(@PathVariable Integer id, @RequestBody Todo todo) {
		todo.setId(id);
		log.info("todo update : {}" , todo.getTodo());
		log.info("todo update : {}" , todo.getCompleted());
		service.update(todo);
	}

	@CrossOrigin
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void delete(@PathVariable Integer id) {
		log.info("todo delete : {}" , id);
		service.delete(id);
	}
}
