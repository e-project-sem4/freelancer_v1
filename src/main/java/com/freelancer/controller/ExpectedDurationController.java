package com.freelancer.controller;

import com.freelancer.model.ExpectedDuration;
import com.freelancer.model.ResponseObject;
import com.freelancer.service.ExpectedDurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/durations")
public class ExpectedDurationController {

    @Autowired
    private ExpectedDurationService expectedDurationService;

//get All

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ResponseObject> fillAll(){
        ResponseObject result = expectedDurationService.fillAll();
        return new ResponseEntity<>(result, HttpStatus.OK);

    }


    //get All/SEARCH
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ResponseObject> search(@RequestParam String keysearch, @PathVariable int page,
                                                 @PathVariable int size) {
        ResponseObject result = expectedDurationService.search(keysearch, page, size);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //Get 1 by id
    @RequestMapping(value = "/{id}",method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ResponseObject> getById(@PathVariable Long id) {

        ResponseObject result = expectedDurationService.getById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    //Create
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<ResponseObject> add(@RequestBody ExpectedDuration expectedDuration) {
        ResponseObject result = expectedDurationService.save(expectedDuration);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //Update
    @RequestMapping(value = "/{id}",method = RequestMethod.PATCH, produces = "application/json")
    public ResponseEntity<ResponseObject> update(@RequestBody ExpectedDuration expectedDuration, @PathVariable Long id) {

        ResponseObject result = expectedDurationService.update(expectedDuration,id);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<ResponseObject> delete(@PathVariable Long id) {
        ResponseObject result = expectedDurationService.delete(id);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
