package com.freelancer.service;

import com.freelancer.model.Complexity;
import com.freelancer.model.ResponseObject;
import com.freelancer.model.User;
import com.freelancer.repository.ComplexityRepository;
import com.freelancer.utils.ConfigLog;
import com.freelancer.utils.Constant;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplexityService {

    Logger logger = ConfigLog.getLogger(UserService.class);

    Gson gson = new Gson();
    @Autowired
    private ComplexityRepository complexityRepository;

    public List<Complexity> listAll(){
        return complexityRepository.findAll();

    }

    public void save(Complexity complexity){
        complexityRepository.save(complexity);
    }

    public Complexity get(Long id) {
        return complexityRepository.findById(id).get();
    }

    public void delete(Long id) {
        complexityRepository.deleteById(id);
    }

    public ResponseObject search(String keysearch, int page, int size) {
        logger.info("call to search user with keysearch: " + keysearch);
        String message = "success";
        List<Complexity> list = complexityRepository.searchComplexity(keysearch, PageRequest.of(page - 1, size));
        Long total = complexityRepository.countComplexity(keysearch);
        return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, total, list);
    }
}
