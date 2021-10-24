package com.freelancer.service;

import com.freelancer.model.*;
import com.freelancer.repository.TransactionRepository;
import com.freelancer.repository.UserRepository;
import com.freelancer.utils.ConfigLog;
import com.freelancer.utils.Constant;
import com.freelancer.utils.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    Logger logger = ConfigLog.getLogger(TransactionService.class);
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    UserRepository userRepository;



    public ResponseObject search(Specification<Transaction> specification, int page, int size, int sort) {
        List<Transaction> list = null;
        if (page > 0 && size > 0 && (sort > 4 || sort < 1 )) {
            list = transactionRepository.findAll(specification, PageRequest.of(page - 1, size)).getContent();
        } else if (page > 0 && size > 0 && sort == 1) {
            list = transactionRepository
                    .findAll(specification, PageRequest.of(page - 1, size, Sort.by("createAt").descending()))
                    .getContent();
        } else if (page > 0 && size > 0 && sort == 2) {
            list = transactionRepository.findAll(specification, PageRequest.of(page - 1, size, Sort.by("createAt").ascending()))
                    .getContent();
        } else if (page > 0 && size > 0 && sort == 3) {
            list = transactionRepository
                    .findAll(specification, PageRequest.of(page - 1, size, Sort.by("paymentAmount").descending()))
                    .getContent();
        } else if (page > 0 && size > 0 && sort == 4) {
            list = transactionRepository
                    .findAll(specification, PageRequest.of(page - 1, size, Sort.by("paymentAmount").ascending()))
                    .getContent();
        } else if (page == 0 && size == 0 && sort == 0) {
            list = transactionRepository.findAll(specification);
        }

        Long total = Long.valueOf(transactionRepository.findAll(specification).size());
        String message = "success";
        return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, total, list);
    }



    public Transaction createTransactionJob(Transaction transaction, String username){
        User user = userRepository.findByUsername(username);
        transaction.setUser_account_id(user.getId());
        transaction.setCreateAt(DateUtil.getTimeLongCurrent());
       return transactionRepository.save(transaction);
    }

    // get by id
    public ResponseObject getById(Long id) {
        logger.info("call to get obj by id: " + id);
        Optional<Transaction> obj = transactionRepository.findById(id);
        String message = "can not find obj";
        if (obj.isPresent()) {
                message = "success";
                logger.info("get obj success");
                return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, obj.get());

        }
        return new ResponseObject(Constant.STATUS_ACTION_FAIL, message, null);

    }
}
