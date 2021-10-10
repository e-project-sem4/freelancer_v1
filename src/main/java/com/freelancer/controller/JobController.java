package com.freelancer.controller;

import com.freelancer.model.ExpectedDuration;
import com.freelancer.model.Job;
import com.freelancer.model.ResponseObject;
import com.freelancer.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
