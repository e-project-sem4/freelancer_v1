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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
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



    //get All/SEARCH
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ResponseObject> search(
                                                @RequestParam(value = "complexityText", required = false) Optional<Long> complexity_id

                                                , @PathVariable int page,
                                                 @PathVariable int size) {
        Specification<Job> specification = Specification.where(null);
        if (complexity_id.isPresent() && complexity_id.get() > 0) {
            specification = specification.and(new JobSpecification(new SearchCriteria("complexity_id", "==", complexity_id.get())));
        }
//        if(pay.isPresent()){
//            specification = specification.and(new JobSpecification(new SearchCriteria("paymentAmount", "==", pay.get()))); // tìm chính xác
//        }
//        if(start.isPresent()){
//            specification = specification.and(new JobSpecification(new SearchCriteria("createAt", ">=", start.get())));
//        }
//        if(end.isPresent()){
//            specification = specification.and(new JobSpecification(new SearchCriteria("createAt", "<=", end.get())));
//        }


        return new ResponseEntity<>(new ResponseObject(Constant.STATUS_ACTION_SUCCESS, "ok", null, jobRepository.findAll(specification)), HttpStatus.OK);
    }
    //Get 1 by id
    @GetMapping("/{id}")
    @RequestMapping(value = "/{id}",method = RequestMethod.GET, produces = "application/json")
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
        ResponseObject result = jobService.save(obj,username);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //Update
    @RequestMapping(value = "/{id}",method = RequestMethod.PATCH, produces = "application/json")
    public ResponseEntity<ResponseObject> update(@RequestBody Job obj, @PathVariable Long id) {
        ResponseObject result = jobService.update(obj,id);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<ResponseObject> delete(@PathVariable Long id) {
        ResponseObject result = jobService.delete(id);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
