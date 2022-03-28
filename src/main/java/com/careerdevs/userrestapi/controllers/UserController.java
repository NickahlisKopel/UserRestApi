package com.careerdevs.userrestapi.controllers;

import com.careerdevs.userrestapi.models.UserModel;
import com.careerdevs.userrestapi.models.UserModelArray;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    Environment env;


    @GetMapping("/all")
    public ResponseEntity getAll(RestTemplate restTemplate){
        try{
            ArrayList<UserModel> allUsers = new ArrayList<>();
            String url = "https://gorest.co.in/public/v2/users";

            ResponseEntity<UserModel[]> response = restTemplate.getForEntity(url, UserModel[].class);
            allUsers.addAll(Arrays.asList(Objects.requireNonNull(response.getBody())));

            int totalPageNumber = Integer.parseInt(Objects.requireNonNull(response.getHeaders().get("X-Pagination-Pages")).get(0));

            for(int i = 2; i <= totalPageNumber;i++){
                String tempUrl = url + "?page=" + i;
                UserModel[] pageData = restTemplate.getForObject(tempUrl,UserModel[].class);
                assert pageData != null;
                allUsers.addAll(Arrays.asList(pageData));

            }

            return new ResponseEntity(allUsers,HttpStatus.OK);


        }catch(Exception e){
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    //URL / endpoint http://localhost:4444/api/user/token
    @GetMapping ("/token")
    public String getToken (){
        return env.getProperty("GOREST_TOKEN");
    }

    //URL / endpoint http://localhost:4444/api/user/page
    @GetMapping("/page/{pageNum}")
    public Object getFirstPage(RestTemplate restTemplate, @PathVariable("pageNum") String pageNumber) {

        try{
            String url = "https://gorest.co.in/public/v2/users?page=" + pageNumber;

            ResponseEntity<UserModel[]> response = restTemplate.getForEntity(url, UserModel[].class);

            UserModel[] firstPageUsers = response.getBody();

            HttpHeaders responseHeaders = response.getHeaders();

            String totalPages = Objects.requireNonNull(responseHeaders.get("X-Pagination-Pages")).get(0);

            System.out.println("Total Pages: " + totalPages);

            return new ResponseEntity<>(firstPageUsers, HttpStatus.OK);

        } catch(Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }


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
    public ResponseEntity postUser (
            RestTemplate restTemplate,
            @RequestBody UserModel newUser

    ) {
        try {
            String url = "https://goresst.co.in/public/v2/users/";
            String token = env.getProperty("GOREST_TOKEN");
            url += "?access-token=" + token;

            HttpEntity<UserModel> request = new HttpEntity<>(newUser);
            return restTemplate.postForEntity(url, request, UserModel.class);

        } catch (Exception e){
            System.out.println(e.getClass() + " \n " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }


    @PutMapping("/")
    public ResponseEntity putUser (
            RestTemplate restTemplate,
            @RequestBody UserModel updateData

    )
    {
        try{

            String url = "https://goresst.co.in/public/v2/users/" + updateData.getId();
            String token = env.getProperty("GOREST_TOKEN");
            url += "?access-token=" + token;

            HttpEntity<UserModel> request = new HttpEntity<>(updateData);

           ResponseEntity<UserModel> response = restTemplate.exchange(url, HttpMethod.PUT,request,UserModel.class);

           return new ResponseEntity(response.getBody(),HttpStatus.OK);

        } catch(HttpClientErrorException.UnprocessableEntity e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);

        }catch (Exception e){
            System.out.println(e.getClass() + " \n " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }





}
