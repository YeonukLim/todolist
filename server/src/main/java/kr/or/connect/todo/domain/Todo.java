package kr.or.connect.todo.domain;

import java.sql.Timestamp;

public class Todo {
	private Integer id;
	private String todo;
	private Integer completed;
	private Timestamp date;

	public Todo(){
	}

	public Todo(String todo, Integer completed) {
		this.setTodo(todo);
		this.setCompleted(completed);
	}
	
	public Todo(String todo, Integer completed, Timestamp date) {
		this.setTodo(todo);
		this.setCompleted(completed);
		this.setDate(date);
	}

	public Todo(Integer id, String todo, Integer completed) {
		this(todo, completed);
		this.setId(id);
		this.setTodo(todo);
		this.setCompleted(completed);
	}
	
	public Todo(Integer id, String todo, Integer completed, Timestamp date) {
		this(todo, completed);
		this.setId(id);
		this.setTodo(todo);
		this.setCompleted(completed);
		this.setDate(date);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTodo() {
		return todo;
	}

	public void setTodo(String todo) {
		this.todo = todo;
	}

	public Integer getCompleted() {
		return completed;
	}

	public void setCompleted(Integer completed) {
		this.completed = completed;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}
}
