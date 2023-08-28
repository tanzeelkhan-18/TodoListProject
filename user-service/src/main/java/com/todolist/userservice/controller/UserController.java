package com.todolist.userservice.controller;

import com.todolist.userservice.model.ToDo;
import com.todolist.userservice.model.Users;
import com.todolist.userservice.model.UsersTodoList;
import com.todolist.userservice.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Autowired
    UsersService usersService;

    private String getUrl(){
        ServiceInstance instance = loadBalancerClient.choose("TODO-SERVICE");
        String url = instance.getUri().toString();
        return url;
    }

    @GetMapping("/listUsers")
    public List<Users> list() throws IOException {
        List<Users> listOfUsers = usersService.findAllUsers();
        return listOfUsers;
    }

    @GetMapping("/getUserByUserName/{userName}")
    public Users getUserByName(@PathVariable String userName) throws IOException {
        Users user = usersService.findUserByUserName(userName);
        return user;
    }

    @GetMapping("/getAllToDos/{userName}")
    public UsersTodoList fetchUserAllTodo(@PathVariable("userName") String userName) throws IOException {
        Users user = usersService.findUserByUserName(userName);

        //Calling ToDo service
        String url = getUrl();
        ResponseEntity<List<ToDo>> todoResponse =
                restTemplate.exchange(url+"/getTodoListByUser/"+ userName,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<ToDo>>() {
                        });
        List<ToDo> toDos = todoResponse.getBody();

        return UsersTodoList.builder().user(user).toDoList(toDos).build();
    }
}
