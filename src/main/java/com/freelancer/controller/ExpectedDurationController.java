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

    //get All/SEARCH
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ResponseObject> search(@RequestParam String keysearch, @PathVariable int page,
                                                 @PathVariable int size) {
        ResponseObject result = expectedDurationService.search(keysearch, page, size);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //Get 1 by id
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getById(@PathVariable Long id) {

        ResponseObject result = expectedDurationService.getById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    //Create
    @PostMapping
    public ResponseEntity<ResponseObject> add(@RequestBody ExpectedDuration expectedDuration) {
        ResponseObject result = expectedDurationService.save(expectedDuration);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //Update
    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> update(@RequestBody ExpectedDuration expectedDuration, @PathVariable Long id) {

        ResponseObject result = expectedDurationService.update(expectedDuration,id);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable Long id) {
        ResponseObject result = expectedDurationService.delete(id);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
