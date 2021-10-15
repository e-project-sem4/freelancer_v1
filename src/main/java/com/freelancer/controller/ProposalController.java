package com.freelancer.controller;

import com.freelancer.model.Job;
import com.freelancer.model.Proposal;
import com.freelancer.model.ResponseObject;
import com.freelancer.security.JwtTokenProvider;
import com.freelancer.service.ProposalService;
import com.freelancer.service.ProposalStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
}
