package com.freelancer.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.freelancer.model.Proposal;
import com.freelancer.model.ResponseObject;
import com.freelancer.security.JwtTokenProvider;
import com.freelancer.service.ProposalService;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/proposals")
public class ProposalController {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private static final String AUTHORIZATION = "Authorization";
    @Autowired
    private ProposalService proposalService;

    //Create
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_CLIENT')")
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<ResponseObject> add(@RequestBody Proposal obj, HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION);
        String username = jwtTokenProvider.getUsername(token);
        ResponseObject result = proposalService.save(obj, username);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/getByJob", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ResponseObject> getByJob(@RequestParam Long jobId) {
        ResponseObject result = proposalService.getByJob(jobId);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    //Update
    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH, produces = "application/json")
    public ResponseEntity<ResponseObject> update(@RequestBody Proposal obj, @PathVariable Long id) {

        ResponseObject result = proposalService.update(obj, id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<ResponseObject> delete(@PathVariable Long id) {
        ResponseObject result = proposalService.delete(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

//    @RequestMapping(value = "/cancelByFl/{id}", method = RequestMethod.DELETE, produces = "application/json")
//    public ResponseEntity<ResponseObject> cancelByFl(@PathVariable Long id) {
//        ResponseObject result = proposalService.cancelByFl(id);
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
//
//    @RequestMapping(value = "/cancelByBsn/{id}", method = RequestMethod.DELETE, produces = "application/json")
//    public ResponseEntity<ResponseObject> cancelByBsn(@PathVariable Long id) {
//        ResponseObject result = proposalService.cancelByBsn(id);
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }



}
