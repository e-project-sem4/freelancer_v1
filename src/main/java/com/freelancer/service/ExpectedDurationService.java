package com.freelancer.service;


import com.freelancer.model.ExpectedDuration;
import com.freelancer.model.ResponseObject;
import com.freelancer.repository.ExpectedDurationRepository;
import com.freelancer.utils.ConfigLog;
import com.freelancer.utils.Constant;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpectedDurationService {
    Logger logger = ConfigLog.getLogger(ExpectedDurationService.class);

    Gson gson = new Gson();
    @Autowired
    private ExpectedDurationRepository expectedDurationRepository;

    //add
    public ResponseObject save(ExpectedDuration expectedDuration){
        String message = "not success";
        logger.info("call to Create Expected Duration" + expectedDuration.toString());
        ExpectedDuration result = expectedDurationRepository.save(expectedDuration);
        if (result!=null){
            message="success";
        }
        return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, result);
    }


    //delete
    public ResponseObject delete(Long id) {

        logger.info("call to get Expected Duration to delete by id: " + id);
        Optional<ExpectedDuration> optionalExpectedDuration = expectedDurationRepository.findById(id);
        String message = "can not find Expected Duration";
        ExpectedDuration result = null;
        if (optionalExpectedDuration.isPresent()) {
            expectedDurationRepository.deleteById(id);
            message = "delete success";
            logger.info("delete Expected Duration success");
            return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, null);
        }else {
            return new ResponseObject(Constant.STATUS_ACTION_FAIL,message,null);
        }

    }

    //update
    public ResponseObject update(ExpectedDuration expectedDuration,Long id){
        logger.info("call to get Expected Duration to update by id: " + id);
        Optional<ExpectedDuration> optionalExpectedDuration = expectedDurationRepository.findById(id);
        String message = "can not Expected Duration";
        ExpectedDuration result = null;
        if (optionalExpectedDuration.isPresent()) {
            ExpectedDuration expectedDuration1 = optionalExpectedDuration.get();
            result = expectedDurationRepository.save(expectedDuration);
            message = "update success";
            logger.info("update Expected Duration success");
            return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, result);
        }else {
            return new ResponseObject(Constant.STATUS_ACTION_FAIL,message,null);
        }

    }

    //get by id
    public ResponseObject getById(Long id) {
        logger.info("call to get Expected Duration by id: " + id);
        Optional<ExpectedDuration> optionalExpectedDuration = expectedDurationRepository.findById(id);
        String message = "can not find Expected Duration";
        ExpectedDuration expectedDuration = null;
        if (optionalExpectedDuration.isPresent()) {
            message = "success";
            expectedDuration = optionalExpectedDuration.get();
            logger.info("get Expected Duration success");
        }
        return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, expectedDuration);
    }



    // Search/list
    public ResponseObject search(String keysearch, int page, int size) {
        logger.info("call to search Expected Duration with keysearch: " + keysearch);
        String message = "success";
        List<ExpectedDuration> list = expectedDurationRepository.searchExpectedDuration(keysearch, PageRequest.of(page - 1, size));
        Long total = expectedDurationRepository.countExpectedDuration(keysearch);
        return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, total, list);
    }
}
