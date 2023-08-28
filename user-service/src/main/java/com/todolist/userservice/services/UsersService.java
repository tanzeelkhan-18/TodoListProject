package com.todolist.userservice.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todolist.userservice.model.Users;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class UsersService {

    ObjectMapper mapper = new ObjectMapper();

    public Users findUserByUserName(String userName) throws IOException {
        List<Users> allUsers = Arrays.asList(mapper.readValue(new File("data/users.json"), Users[].class));
        for(Users user:allUsers){
            if(user.getUserName().equals(userName)){
                return user;
            }
        }
        return null;
    }

    public List<Users> findAllUsers() throws IOException {
        List<Users> allUsers = Arrays.asList(mapper.readValue(new File("data/users.json"), Users[].class));
        return allUsers;
    }
}
