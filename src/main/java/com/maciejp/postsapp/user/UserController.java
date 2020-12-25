package com.maciejp.postsapp.user;

import com.maciejp.postsapp.exception.UserRegisterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/process_register")
    public String processRegister(@RequestBody String user) {
        try {
            userService.addUserAsString(user);
            return "{\"valid\" : true}";
        } catch (UserRegisterException e) {
            return "{\"valid\" : false, \"message\" : \"" + e.getMessage() + "\"}";
        }
    }
}
