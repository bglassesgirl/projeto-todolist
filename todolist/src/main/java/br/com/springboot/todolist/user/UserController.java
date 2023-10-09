package br.com.springboot.todolist.user;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class UserController {
    

    /*
     * Body da requisição
     */

    @PostMapping("/")
    public void create(@RequestBody UserModel userModel){
        System.out.println(userModel.getName());       

    }
}
