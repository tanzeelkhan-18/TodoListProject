package com.todolist.todoservice.controller;

import com.todolist.todoservice.model.ToDo;
import com.todolist.todoservice.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class ToDoController {

    @Autowired
    TodoService todoService;

    @GetMapping("/getTodoListByUser/{userName}")
    public List<ToDo> getToDoList(@PathVariable("userName") String userName) throws IOException {
        List<ToDo> toDoList = todoService.getAllToDos(userName);
        return toDoList;
    }
}
