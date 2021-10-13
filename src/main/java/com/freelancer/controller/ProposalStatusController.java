package com.freelancer.controller;

import com.freelancer.model.ProposalStatusCatalog;
import com.freelancer.model.ResponseObject;
import com.freelancer.service.ProposalStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/proposals")
public class ProposalStatusController {

    @Autowired
    private ProposalStatusService proposalStatusService;


    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ResponseObject> findAll(){
        ResponseObject result = proposalStatusService.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    ///SEARCH
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ResponseObject> search(@RequestParam String keysearch, @PathVariable int page,
                                                 @PathVariable int size) {
        ResponseObject result = proposalStatusService.search(keysearch, page, size);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //Get 1 by id
    @RequestMapping(value = "/{id}",method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ResponseObject> getById(@PathVariable Long id) {

        ResponseObject result = proposalStatusService.getById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    //Create
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<ResponseObject> add(@RequestBody ProposalStatusCatalog proposalStatusCatalog) {
        ResponseObject result = proposalStatusService.save(proposalStatusCatalog);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //Update
    @RequestMapping(value = "/{id}",method = RequestMethod.PATCH, produces = "application/json")
    public ResponseEntity<ResponseObject> update(@RequestBody ProposalStatusCatalog proposalStatusCatalog, @PathVariable Long id) {

        ResponseObject result = proposalStatusService.update(proposalStatusCatalog,id);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<ResponseObject> delete(@PathVariable Long id) {
        ResponseObject result = proposalStatusService.delete(id);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
