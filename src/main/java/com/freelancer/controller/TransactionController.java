package com.freelancer.controller;

import com.freelancer.model.ResponseObject;
import com.freelancer.model.Transaction;
import com.freelancer.repository.TransactionRepository;
import com.freelancer.search.SearchCriteria;
import com.freelancer.search.TransactionSpecification;
import com.freelancer.security.JwtTokenProvider;
import com.freelancer.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/transactions")
public class TransactionController {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private static final String AUTHORIZATION = "Authorization";
    @Autowired
    private TransactionService transactionService;

    //get All/SEARCH
    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ResponseObject> search(@RequestParam(value = "username", required = false) Optional<String> username,
                                                 @RequestParam(value = "jobName", required = false) Optional<String> jobName
            , @RequestParam(value = "type", required = false) Optional<Integer> type
            , @RequestParam(value = "startAt", required = false) Optional<Long> startAt
            , @RequestParam(value = "endAt", required = false) Optional<Long> endAt
            , @RequestParam(value = "page", required = false) Optional<Integer> page
            , @RequestParam(value = "size", required = false) Optional<Integer> size
            , @RequestParam(value = "sort", required = false) Optional<Integer> sort) {
        Specification<Transaction> specification = Specification.where(null);
        if (username.isPresent() && !username.get().isEmpty()) {
            specification = specification.and(new TransactionSpecification(new SearchCriteria("username", "likeUsername", username.get())));
        }
        if (jobName.isPresent() && !jobName.get().isEmpty()) {
            specification = specification.and(new TransactionSpecification(new SearchCriteria("name", "likeJobname", jobName.get())));
        }
        if (type.isPresent() && type.get()>=0) {
            specification = specification.and(new TransactionSpecification(new SearchCriteria("type", "==", type.get())));
        }
        if (startAt.isPresent() && startAt.get() > 0) {
            specification = specification.and(new TransactionSpecification(new SearchCriteria("createAt", ">=", startAt.get())));
        }
        if (endAt.isPresent() && endAt.get() > 0) {
            specification = specification.and(new TransactionSpecification(new SearchCriteria("createAt", "<=", endAt.get())));
        }




        ResponseObject result = null;
        if (page.isPresent() && size.isPresent() && sort.isPresent()) {
            result = transactionService.search(specification, page.get(), size.get(), sort.get());
        }

        if (!page.isPresent() || !size.isPresent() || !sort.isPresent()) {
            result = transactionService.search(specification, 0, 0, 0);
        }


        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    //Get 1 by id
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ResponseObject> getById(@PathVariable Long id) {

        ResponseObject result = transactionService.getById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @RequestMapping(value = "/export/excel/transaction/admin", method = RequestMethod.GET, produces = "application/octet-stream")
    public ResponseEntity<Resource> getFileTransactionAdmin() {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String filename = "transaction_" + currentDateTime + ".xlsx";
        InputStreamResource file = new InputStreamResource(transactionService.loadTransactionAdmin());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }
    @RequestMapping(value = "/export/excel/revenue/admin", method = RequestMethod.GET, produces = "application/octet-stream")
    public ResponseEntity<Resource> getFileRevenueAdmin() {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String filename = "transaction_" + currentDateTime + ".xlsx";
        InputStreamResource file = new InputStreamResource(transactionService.loadRevenueAdmin());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }
    
}
