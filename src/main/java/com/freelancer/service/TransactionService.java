package com.freelancer.service;

import com.freelancer.model.Transaction;
import com.freelancer.model.User;
import com.freelancer.repository.TransactionRepository;
import com.freelancer.repository.UserRepository;
import com.freelancer.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    UserRepository userRepository;
    public Transaction createTransactionJob(Transaction transaction, String username){
        User user = userRepository.findByUsername(username);
        transaction.setUser_account_id(user.getId());
        transaction.setCreateAt(DateUtil.getTimeLongCurrent());
       return transactionRepository.save(transaction);
    }
}
