package com.freelancer.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.freelancer.model.ProposalStatusCatalog;
import com.freelancer.model.ResponseObject;
import com.freelancer.search.ProposalCatalogSpecification;
import com.freelancer.search.SearchCriteria;
import com.freelancer.service.ProposalStatusService;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/proposalscs")
public class ProposalStatusController {

    @Autowired
    private ProposalStatusService proposalStatusService;



    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ResponseObject> search(
            @RequestParam(value = "keySearch", required = false) Optional<String> keySearch
            , @RequestParam(value = "status", required = false) Optional<Long> status
            , @RequestParam(value = "page", required = false) Optional<Integer> page
            , @RequestParam(value = "size", required = false) Optional<Integer> size
            , @RequestParam(value = "sort", required = false) Optional<Integer> sort
    ) {
        Specification<ProposalStatusCatalog> specification = Specification.where(null);
        if (keySearch.isPresent() && !keySearch.get().isEmpty()) {
            specification = specification.and(new ProposalCatalogSpecification(new SearchCriteria("statusName"  , "like", keySearch.get()))
            );
        }
        if (status.isPresent()) {
            specification = specification.and(new ProposalCatalogSpecification(new SearchCriteria("status", "==", status.get())));
        }
        ResponseObject result =null;
        if (page.isPresent() && size.isPresent() && sort.isPresent()) {
            result = proposalStatusService.search(specification, page.get(), size.get(), sort.get());
        }

        if (!page.isPresent()||!size.isPresent()||!sort.isPresent()){
            result = proposalStatusService.search(specification, 0,0,0);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //Get 1 by id
    @RequestMapping(value = "/{id}",method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ResponseObject> getById(@PathVariable Long id) {

        ResponseObject result = proposalStatusService.getById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    //Create
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<ResponseObject> add(@RequestBody ProposalStatusCatalog proposalStatusCatalog) {
        ResponseObject result = proposalStatusService.save(proposalStatusCatalog);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //Update
    @RequestMapping(value = "/{id}",method = RequestMethod.PATCH, produces = "application/json")
    public ResponseEntity<ResponseObject> update(@RequestBody ProposalStatusCatalog proposalStatusCatalog, @PathVariable Long id) {

        ResponseObject result = proposalStatusService.update(proposalStatusCatalog,id);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<ResponseObject> delete(@PathVariable Long id) {
        ResponseObject result = proposalStatusService.delete(id);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
