package com.freelancer.controller;

import com.freelancer.model.ExpectedDuration;
import com.freelancer.model.Job;
import com.freelancer.model.ResponseObject;
import com.freelancer.repository.JobRepository;
import com.freelancer.search.JobSpecification;
import com.freelancer.search.SearchCriteria;
import com.freelancer.security.JwtTokenProvider;
import com.freelancer.service.JobService;
import com.freelancer.utils.Constant;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.Console;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/job")
public class JobController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private static final String AUTHORIZATION = "Authorization";
    @Autowired
    private JobService jobService;
    @Autowired
    private JobRepository jobRepository;
    private Gson gson;


    //get All/SEARCH
    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ResponseObject> search(@RequestParam(value = "keySearch", required = false) Optional<String> keySearch
            , @RequestParam(value = "complexity_id", required = false) Optional<Long> complexity_id
            , @RequestParam(value = "expected_duration_id", required = false) Optional<Long> expected_duration_id
            , @RequestParam(value = "skill_id", required = false) Optional<ArrayList<Long>> skill_id
            , @RequestParam(value = "status", required = false) Optional<Long> status
            , @RequestParam(value = "isPaymentStatus", required = false) Optional<Integer> isPaymentStatus
            , @RequestParam(value = "page", required = false) Optional<Integer> page
            , @RequestParam(value = "size", required = false) Optional<Integer> size
            , @RequestParam(value = "sort", required = false) Optional<Integer> sort) {
        Specification<Job> specification = Specification.where(null);
        if (keySearch.isPresent()) {
            specification = specification.and(new JobSpecification(new SearchCriteria("name", "like", keySearch.get())).
                    or(new JobSpecification(new SearchCriteria("description", "like", keySearch.get()))
                    ));
        }
        if (complexity_id.isPresent() && complexity_id.get() > 0) {
            specification = specification.and(new JobSpecification(new SearchCriteria("complexity_id", "==", complexity_id.get())));
        }
        if (expected_duration_id.isPresent() && expected_duration_id.get() > 0) {
            specification = specification.and(new JobSpecification(new SearchCriteria("expected_duration_id", "==", expected_duration_id.get())));
        }

        if (skill_id.isPresent() && skill_id.get().get(0) > 0) {
            for (Long s : skill_id.get()
            ) {
                specification = specification.and(new JobSpecification(new SearchCriteria("skill_id", "==skill", s)));
            }
        }
        if (isPaymentStatus.isPresent()&& isPaymentStatus.get()>=0&&isPaymentStatus.get()<=1) {
            specification = specification.and(new JobSpecification(new SearchCriteria("isPaymentStatus", "==", isPaymentStatus.get())));
        }

        if (status.isPresent()&& status.get()>=0&&status.get()<3) {
            specification = specification.and(new JobSpecification(new SearchCriteria("status", "==", status.get())));
        }

        ResponseObject result = null;
        if (page.isPresent() && size.isPresent() && sort.isPresent()) {
            result = jobService.search(specification, page.get(), size.get(), sort.get());
        }

        if (!page.isPresent() || !size.isPresent() || !sort.isPresent()) {
            result = jobService.search(specification, 0, 0, 0);
        }


        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //Get 1 by id
    @GetMapping("/{id}")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ResponseObject> getById(@PathVariable Long id) {
        ResponseObject result = jobService.getById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    //Create
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_CLIENT')")
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<ResponseObject> add(@RequestBody Job obj, HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION);
        String username = jwtTokenProvider.getUsername(token);
        ResponseObject result = jobService.save(obj, username);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //Update
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_CLIENT')")
    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH, produces = "application/json")
    public ResponseEntity<ResponseObject> update(@RequestBody Job obj, @PathVariable Long id, HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION);
        String username = jwtTokenProvider.getUsername(token);
        ResponseObject result = jobService.update(obj, id,username);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_CLIENT')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<ResponseObject> delete(@PathVariable Long id) {
        ResponseObject result = jobService.delete(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
