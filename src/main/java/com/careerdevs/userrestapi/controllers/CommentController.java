package com.careerdevs.userrestapi.controllers;


import com.careerdevs.userrestapi.models.CommentModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    Environment env;


    @GetMapping ("/firstpage")
    public CommentModel[] getFirstPage(RestTemplate restTemplate){
       String url = "https://gorest.co.in/public/v2/comments";
        CommentModel[] comment = restTemplate.getForObject(url,CommentModel[].class);

        return comment;
    }



    @GetMapping("/{id}")
    public ResponseEntity commentHandler (RestTemplate restTemplate, @PathVariable("id") int commentId){
        String apiKey = env.getProperty("GOREST_TOKEN");
        try{
            CommentModel requestData = restTemplate.getForObject("https://gorest.co.in/public/v2/comments/" + commentId+"?access-token="+apiKey, CommentModel.class);
            return new ResponseEntity(requestData,HttpStatus.OK);
        }catch(Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Object> deleteComment (RestTemplate restTemplate, @PathVariable("id") int commentId){
        String apiKey = env.getProperty("GOREST_TOKEN");
        try{
            String url = "https://gorest.co.in/public/v2/comments/" + commentId+"?access-token="+apiKey;
            CommentModel deletedComment = restTemplate.getForObject(url, CommentModel.class);
            restTemplate.delete(url);

            return new ResponseEntity<>(deletedComment,HttpStatus.OK);
        }catch(Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }



    }

    @PostMapping
    public ResponseEntity<Object> postComment (
            RestTemplate restTemplate,
            @RequestBody CommentModel newComment
    )
    {
        String apiKey = env.getProperty("GOREST_TOKEN");
        try{
            String url = "https://gorest.co.in/public/v2/comments/?access-token="+apiKey;

            HttpEntity<CommentModel> request = new HttpEntity<>(newComment);
            CommentModel createComment = restTemplate.postForObject(url, request, CommentModel.class);

            return new ResponseEntity<>(createComment,HttpStatus.CREATED);
        }catch(Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping
    public ResponseEntity<Object> putComment (
            RestTemplate restTemplate,
            @RequestBody CommentModel updateComment
    )
    {
        String apiKey = env.getProperty("GOREST_TOKEN");
        try{
            String url = "https://gorest.co.in/public/v2/comments/"+ updateComment.getId()+"?access-token="+apiKey;

            HttpEntity<CommentModel> request = new HttpEntity<>(updateComment);
           ResponseEntity<CommentModel> response = restTemplate.exchange(
                   url,
                   HttpMethod.PUT,
                   request,
                   CommentModel.class);

            return new ResponseEntity<>(response.getBody(),HttpStatus.CREATED);
        }catch(Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
