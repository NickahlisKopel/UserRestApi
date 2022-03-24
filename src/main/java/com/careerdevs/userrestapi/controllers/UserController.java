package com.careerdevs.userrestapi.controllers;

import com.careerdevs.userrestapi.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
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
    @GetMapping("/{id}")
    public Object userHandler (RestTemplate restTemplate, @PathVariable("id") String id){
       String apiKey = env.getProperty("GOREST_TOKEN");
       Object requestData = restTemplate.getForObject("https://gorest.co.in/public/v2/users/" + id+"?access-token="+apiKey, Object.class);
       return requestData;
    }

   @DeleteMapping("/{id}")
    public Object deleteOneUser (@PathVariable("id") String userId,RestTemplate restTemplate){
    try{
        String url = "https://gorest.co.in/public/v2/users/"+userId;
        String token = env.getProperty("GOREST_TOKEN");

        url += "?accesss-token=" + token;
        restTemplate.delete(url);

        return "Successfully Deleted user#" + userId;
    } catch(HttpClientErrorException.NotFound exception) {
        return "User could not be deleted, user #"+userId+" does not exist";
       }catch(HttpClientErrorException.Unauthorized exception){
        return "You are not authorized to delete user #"+userId;
       }catch (Exception exception){
        return exception.getMessage();
    }
    }

    @PostMapping("/")
    public Object postUser (
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("gender") String gender,
            @RequestParam("status") String status

    ) {
        try {
            String url = "https://goresst.co.in/public/v2/users/";
            String token = env.getProperty("GOREST_TOKEN");
            url += "?access-token" + token;

            UserModel newUser = new UserModel(name,email,gender,status);
            return newUser;

        } catch (Exception exception){
            System.out.println(exception.getClass());
            return exception.getMessage();

        }
    }

}
