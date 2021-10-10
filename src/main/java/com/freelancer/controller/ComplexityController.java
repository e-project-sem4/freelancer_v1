package com.freelancer.controller;

import com.freelancer.model.ResponseObject;
import com.freelancer.service.ComplexityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/complexities")
public class ComplexityController {
    @Autowired
    private ComplexityService complexityService;


    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ResponseObject> search(@RequestParam String keysearch, @PathVariable int page,
                                                 @PathVariable int size) {
        ResponseObject result = complexityService.search(keysearch, page, size);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
