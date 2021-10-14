package com.freelancer.controller;

import com.freelancer.model.Complexity;
import com.freelancer.search.ComplexitySpecification;
import com.freelancer.search.SearchCriteria;
import com.freelancer.search.SkillSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.freelancer.model.ResponseObject;
import com.freelancer.model.Skill;
import com.freelancer.service.SkillService;

import java.util.Optional;


@RestController
@CrossOrigin
@RequestMapping("/api/v1/skills")
public class SkillController {
    @Autowired
    private SkillService skillService;




    //get All/SEARCH

    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ResponseObject> search(
            @RequestParam(value = "keySearch", required = false) Optional<String> keySearch
            , @RequestParam(value = "status", required = false) Optional<Long> status
            , @RequestParam(value = "page", required = false) Optional<Integer> page
            , @RequestParam(value = "size", required = false) Optional<Integer> size
            , @RequestParam(value = "sort", required = false) Optional<Integer> sort
    ) {
        Specification<Skill> specification = Specification.where(null);
        if (keySearch.isPresent()) {
            specification = specification.and(new SkillSpecification(new SearchCriteria("skillName"  , "like", keySearch.get()))
            );
        }
        if (status.isPresent()) {
            specification = specification.and(new SkillSpecification(new SearchCriteria("status", "==", status.get())));
        }
        ResponseObject result =null;
        if (page.isPresent() && size.isPresent() && sort.isPresent()) {
            result = skillService.search(specification, page.get(), size.get(), sort.get());
        }

        if (!page.isPresent()||!size.isPresent()||!sort.isPresent()){
            result = skillService.search(specification, 0,0,0);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //Get 1 by id
    @RequestMapping(value = "/{id}",method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ResponseObject> getById(@PathVariable Long id) {

        ResponseObject result = skillService.getById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    //Create
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<ResponseObject> add(@RequestBody Skill skill) {
        ResponseObject result = skillService.save(skill);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //Update
    @RequestMapping(value = "/{id}",method = RequestMethod.PATCH, produces = "application/json")
    public ResponseEntity<ResponseObject> update(@RequestBody Skill skill, @PathVariable Long id) {

        ResponseObject result = skillService.update(skill,id);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<ResponseObject> delete(@PathVariable Long id) {
        ResponseObject result = skillService.delete(id);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
