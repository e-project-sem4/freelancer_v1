package com.freelancer.controller;

import com.freelancer.model.Complexity;
import com.freelancer.model.Job;
import com.freelancer.model.ResponseObject;
import com.freelancer.model.UserFreelancer;
import com.freelancer.search.FreelancerSpecification;
import com.freelancer.search.JobSpecification;
import com.freelancer.search.SearchCriteria;
import com.freelancer.security.JwtTokenProvider;
import com.freelancer.service.UserService;
import com.freelancer.utils.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/freelancer")
public class UserFreelancerController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    JWTService jwtService;

    private static final String AUTHORIZATION = "Authorization";

    //get All/SEARCH
    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ResponseObject> search(@RequestParam(value = "keySearch", required = false) Optional<String> keySearch
            , @RequestParam(value = "skill_id", required = false) Optional<ArrayList<Long>> skill_id
            , @RequestParam(value = "status", required = false) Optional<Long> status
            , @RequestParam(value = "page", required = false) Optional<Integer> page
            , @RequestParam(value = "size", required = false) Optional<Integer> size
            , @RequestParam(value = "sort", required = false) Optional<Integer> sort) {
        ResponseObject result = null;
        Specification<UserFreelancer> specification = Specification.where(null);
        if (keySearch.isPresent()) {
            specification = specification.and(new FreelancerSpecification(new SearchCriteria("certifications", "like", keySearch.get())).
                    or(new FreelancerSpecification(new SearchCriteria("location", "like", keySearch.get()))
                    ));
        }
        if (skill_id.isPresent() && skill_id.get().get(0) > 0) {
            for (Long s : skill_id.get()
            ) {
                specification = specification.and(new FreelancerSpecification(new SearchCriteria("skill_id", "==skill", s)));
            }
        }
        if (page.isPresent() && size.isPresent() && sort.isPresent()) {
            result = userService.searchFreelancer(specification, page.get(), size.get(), sort.get());
        }

        if (!page.isPresent() || !size.isPresent() || !sort.isPresent()) {
            result = userService.searchFreelancer(specification, 0, 0, 0);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }



    //Change Status
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @RequestMapping(value = "/changeStatus", method = RequestMethod.PATCH, produces = "application/json")
    public ResponseEntity<ResponseObject> changeStatus(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION);
        String username = jwtTokenProvider.getUsername(token);
        ResponseObject result = userService.changeStatusFreelancer(username);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
