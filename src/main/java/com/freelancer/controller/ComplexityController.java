package com.freelancer.controller;

import com.freelancer.model.Complexity;
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

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ResponseObject> findAll(){
        ResponseObject result = complexityService.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ResponseObject> search(@RequestParam String keysearch, @PathVariable int page,
                                                 @PathVariable int size) {
        ResponseObject result = complexityService.search(keysearch, page, size);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //Get 1 by id
    @RequestMapping(value = "/{id}",method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ResponseObject> getById(@PathVariable Long id) {

        ResponseObject result = complexityService.getById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    //Create
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<ResponseObject> add(@RequestBody Complexity obj) {
        ResponseObject result = complexityService.save(obj);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //Update
    @RequestMapping(value = "/{id}",method = RequestMethod.PATCH, produces = "application/json")
    public ResponseEntity<ResponseObject> update(@RequestBody Complexity obj, @PathVariable Long id) {

        ResponseObject result = complexityService.update(obj,id);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<ResponseObject> delete(@PathVariable Long id) {
        ResponseObject result = complexityService.delete(id);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

}
