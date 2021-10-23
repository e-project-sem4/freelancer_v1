package com.freelancer.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.freelancer.model.Contract;
import com.freelancer.model.ResponseObject;
import com.freelancer.security.JwtTokenProvider;
import com.freelancer.service.ContractService;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/contracts")
public class ContractController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private static final String AUTHORIZATION = "Authorization";
    @Autowired
    private ContractService contractService;
    //Create
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_CLIENT')")
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<ResponseObject> add(@RequestBody Contract obj, HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION);
        String username = jwtTokenProvider.getUsername(token);
        ResponseObject result = contractService.save(obj, username);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //Get 1 by id
    @GetMapping("/{id}")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ResponseObject> getById(@PathVariable Long id) {
        ResponseObject result = contractService.getById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

}
