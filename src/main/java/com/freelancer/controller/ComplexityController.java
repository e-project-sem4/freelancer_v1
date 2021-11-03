package com.freelancer.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
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

import com.freelancer.model.Complexity;
import com.freelancer.model.ResponseObject;
import com.freelancer.search.ComplexitySpecification;
import com.freelancer.search.SearchCriteria;
import com.freelancer.service.ComplexityService;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/complexities")
public class ComplexityController {
    @Autowired
    private ComplexityService complexityService;





    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ResponseObject> search(
            @RequestParam(value = "keySearch", required = false) Optional<String> keySearch
            , @RequestParam(value = "status", required = false) Optional<Long> status
            , @RequestParam(value = "page", required = false) Optional<Integer> page
            , @RequestParam(value = "size", required = false) Optional<Integer> size
            , @RequestParam(value = "sort", required = false) Optional<Integer> sort
    ) {
        Specification<Complexity> specification = Specification.where(null);
        if (keySearch.isPresent() && !keySearch.get().isEmpty()) {
            specification = specification.and(new ComplexitySpecification(new SearchCriteria("complexityText"  , "like", keySearch.get()))
                    );
        }
        if (status.isPresent()) {
            specification = specification.and(new ComplexitySpecification(new SearchCriteria("status", "==", status.get())));
        }
        ResponseObject result =null;
        if (page.isPresent() && size.isPresent() && sort.isPresent()) {
            result = complexityService.search(specification, page.get(), size.get(), sort.get());
        }

        if (!page.isPresent()||!size.isPresent()||!sort.isPresent()){
            result = complexityService.search(specification, 0,0,0);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //Get 1 by id
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH, produces = "application/json")
    public ResponseEntity<ResponseObject> update(@RequestBody Complexity obj, @PathVariable Long id) {

        ResponseObject result = complexityService.update(obj, id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<ResponseObject> delete(@PathVariable Long id) {
        ResponseObject result = complexityService.delete(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
