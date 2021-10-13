package com.freelancer.controller;

import com.freelancer.model.Complexity;
import com.freelancer.model.ExpectedDuration;
import com.freelancer.model.ResponseObject;
import com.freelancer.search.ComplexitySpecification;
import com.freelancer.search.DurationSpecification;
import com.freelancer.search.SearchCriteria;
import com.freelancer.service.ExpectedDurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/durations")
public class ExpectedDurationController {

    @Autowired
    private ExpectedDurationService expectedDurationService;




    //get all/SEARCH
    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ResponseObject> search(
            @RequestParam(value = "keySearch", required = false) Optional<String> keySearch
            , @RequestParam(value = "status", required = false) Optional<Long> status
            , @RequestParam(value = "page", required = false) Optional<Integer> page
            , @RequestParam(value = "size", required = false) Optional<Integer> size
            , @RequestParam(value = "sort", required = false) Optional<Integer> sort
    ) {
        Specification<ExpectedDuration> specification = Specification.where(null);
        if (keySearch.isPresent()) {
            specification = specification.and(new DurationSpecification(new SearchCriteria("durationText"  , "like", keySearch.get()))
            );
        }
        if (status.isPresent()) {
            specification = specification.and(new DurationSpecification(new SearchCriteria("status", "==", status.get())));
        }
        ResponseObject result =null;
        if (page.isPresent() && size.isPresent() && sort.isPresent()) {
            result = expectedDurationService.search(specification, page.get(), size.get(), sort.get());
        }

        if (!page.isPresent()||!size.isPresent()||!sort.isPresent()){
            result = expectedDurationService.search(specification, 0,0,0);
        }
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
