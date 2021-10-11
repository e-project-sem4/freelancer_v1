package com.freelancer.controller;

import com.freelancer.model.ResponseObject;
import com.freelancer.model.Skill;
import com.freelancer.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/skills")
public class SkillController {
    @Autowired
    private SkillService skillService;


    //get All/SEARCH
    @PreAuthorize("hasRole('ROLE_CLIENT') OR hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ResponseObject> search(@RequestParam String keysearch, @PathVariable int page,
                                                 @PathVariable int size) {
        ResponseObject result = skillService.search(keysearch, page, size);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //Get 1 by id
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getById(@PathVariable Long id) {

        ResponseObject result = skillService.getById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    //Create
    @PostMapping
    public ResponseEntity<ResponseObject> add(@RequestBody Skill skill) {
        ResponseObject result = skillService.save(skill);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //Update
    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> update(@RequestBody Skill skill, @PathVariable Long id) {

        ResponseObject result = skillService.update(skill,id);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable Long id) {
        ResponseObject result = skillService.delete(id);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
