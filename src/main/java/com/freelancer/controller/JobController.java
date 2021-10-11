package com.freelancer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.freelancer.model.Job;
import com.freelancer.model.ResponseObject;
import com.freelancer.service.JobService;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/job")
public class JobController {
    @Autowired
    private JobService jobService;
    //get All/SEARCH
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ResponseObject> search(@RequestParam String keysearch, @PathVariable int page,
                                                 @PathVariable int size) {
        ResponseObject result = jobService.search(keysearch, page, size);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    //Get 1 by id
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getById(@PathVariable Long id) {
        ResponseObject result = jobService.getById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    //Create
    @PostMapping
    public ResponseEntity<ResponseObject> add(@RequestBody Job obj) {
        ResponseObject result = jobService.save(obj);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //Update
    @PatchMapping("/{id}")
    public ResponseEntity<ResponseObject> update(@RequestBody Job obj, @PathVariable Long id) {
        ResponseObject result = jobService.update(obj,id);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable Long id) {
        ResponseObject result = jobService.delete(id);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
