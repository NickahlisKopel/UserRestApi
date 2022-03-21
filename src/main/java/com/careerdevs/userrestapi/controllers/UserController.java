package com.careerdevs.userrestapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    Environment env;

    //URL / endpoint http://localhost:4444/api/user/token
    @GetMapping ("/token")
    public String getToken (){
        return env.getProperty("GOREST_TOKEN");
    }

    //URL / endpoint http://localhost:4444/api/user/{user}
    @GetMapping("/{user}")
    public Object userHandler (RestTemplate restTemplate, @PathVariable("user") String user){
       String apiKey = env.getProperty("GOREST_TOKEN");
       Object requestData = restTemplate.getForObject("https://gorest.co.in/public/v2/users/" + user, Object.class);
       return requestData;
    }

    @GetMapping("/users")
    public Object rootHandler (RestTemplate restTemplate) {
        Object requestData = restTemplate.getForObject("https://gorest.co.in/public/v2/users", Object.class);
        return requestData;
    }

}
