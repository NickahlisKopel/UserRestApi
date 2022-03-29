package com.careerdevs.userrestapi.controllers;


import com.careerdevs.userrestapi.models.PostModel;
import com.careerdevs.userrestapi.models.TodosModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/todo")
public class TodosController {
    @Autowired
    Environment env;


    @GetMapping ("/firstpage")
    public TodosModel[] getFirstPage(RestTemplate restTemplate){
        String apiKey = env.getProperty("GOREST_TOKEN");
        String url = "https://gorest.co.in/public/v2/todos" + "?access-token="+apiKey;
        TodosModel[] todos = restTemplate.getForObject(url,TodosModel[].class);

        return todos;
    }

    @GetMapping("/{id}")
    public ResponseEntity todoHandler (RestTemplate restTemplate, @PathVariable("id") int todoId){
        String apiKey = env.getProperty("GOREST_TOKEN");
        try{
            TodosModel requestData = restTemplate.getForObject("https://gorest.co.in/public/v2/todos/" + todoId+"?access-token="+apiKey, TodosModel.class);
            return new ResponseEntity(requestData, HttpStatus.OK);
        }catch(Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTodo (RestTemplate restTemplate, @PathVariable("id") int todoId){
        String apiKey = env.getProperty("GOREST_TOKEN");
        try{
            String url = "https://gorest.co.in/public/v2/todos/" + todoId+"?access-token="+apiKey;
            TodosModel deletedTodo= restTemplate.getForObject(url, TodosModel.class);
            restTemplate.delete(url);

            return new ResponseEntity<>(deletedTodo,HttpStatus.OK);
        }catch(Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }



    }

    @PostMapping
    public ResponseEntity<Object> postTodo (
            RestTemplate restTemplate,
            @RequestBody TodosModel newTodo
    )
    {
        String apiKey = env.getProperty("GOREST_TOKEN");
        try{
            String url = "https://gorest.co.in/public/v2/todos/?access-token="+apiKey;

            HttpEntity<TodosModel> request = new HttpEntity<>(newTodo);
           TodosModel createTodo = restTemplate.postForObject(url, request, TodosModel.class);

            return new ResponseEntity<>(createTodo,HttpStatus.CREATED);
        }catch(Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping
    public ResponseEntity<Object> putTodo(
            RestTemplate restTemplate,
            @RequestBody TodosModel updateTodo
    )
    {
        String apiKey = env.getProperty("GOREST_TOKEN");
        try{
            String url = "https://gorest.co.in/public/v2/todos/"+ updateTodo.getId()+"?access-token="+apiKey;

            HttpEntity<TodosModel> request = new HttpEntity<>(updateTodo);
            ResponseEntity<TodosModel> response = restTemplate.exchange(
                    url,
                    HttpMethod.PUT,
                    request,
                    TodosModel.class);

            return new ResponseEntity<>(response.getBody(),HttpStatus.CREATED);
        }catch(Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }










}
