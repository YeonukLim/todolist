package kr.or.connect.todo.service;

import java.sql.Timestamp;
import java.util.Collection;

import org.springframework.stereotype.Service;

import kr.or.connect.todo.domain.Todo;
import kr.or.connect.todo.persistence.TodoDao;

@Service
public class TodoService {
	private TodoDao dao;
	
	public TodoService(TodoDao dao) {
		this.dao = dao;
	}

	public Todo findById(Integer id) {
		return dao.selectById(id);
	}

	public Collection<Todo> findAll() {
		return dao.selectAll();
	}

	public Todo create(Todo todo) {
		Timestamp curDate = new Timestamp(System.currentTimeMillis());
		todo.setDate(curDate);
		Integer id = dao.insert(todo);
		todo.setId(id);
		return todo;
	}

	public boolean update(Todo todo) {
		Todo preTodo = dao.selectById(todo.getId());
		preTodo.setCompleted(todo.getCompleted());
		int affected = dao.update(preTodo);
		return affected == 1;
	}

	public boolean delete(Integer id) {
		int affected = dao.deleteById(id);
		return affected == 1;
	}
}
