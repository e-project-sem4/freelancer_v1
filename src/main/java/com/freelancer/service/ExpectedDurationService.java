package com.freelancer.service;


import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.freelancer.model.Complexity;
import com.freelancer.utils.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.freelancer.model.ExpectedDuration;
import com.freelancer.model.ResponseObject;
import com.freelancer.repository.ExpectedDurationRepository;
import com.freelancer.utils.ConfigLog;
import com.freelancer.utils.Constant;
import com.google.gson.Gson;

@Service
public class ExpectedDurationService {
    Logger logger = ConfigLog.getLogger(ExpectedDurationService.class);

    Gson gson = new Gson();
    @Autowired
    private ExpectedDurationRepository expectedDurationRepository;


    // add
    public ResponseObject save(ExpectedDuration obj) {
        String message = "not success";
        logger.info("call to Create obj" + obj.toString());
        obj.setCreateAt(DateUtil.getTimeLongCurrent());
        obj.setStatus(1);
        ExpectedDuration result = expectedDurationRepository.save(obj);
        if (result != null) {
            message = "success";
        }
        return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, result);
    }

    // delete
    public ResponseObject delete(Long id) {

        logger.info("call to get obj to delete by id: " + id);
        ExpectedDuration obj = expectedDurationRepository.getOne(id);
        String message = "can not find obj";
        ExpectedDuration result = null;
        if (obj.getId()!=null) {
            obj.setStatus(0);
            result = expectedDurationRepository.save(obj);
            message = "delete success";
            logger.info("delete obj success");
            return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, result);
        } else {
            return new ResponseObject(Constant.STATUS_ACTION_FAIL, message, null);
        }

    }

    // update
    public ResponseObject update(ExpectedDuration obj, Long id) {
        logger.info("call to get obj to update by id: " + id);
        ExpectedDuration obj1 = expectedDurationRepository.getOne(id);
        String message = "can not find obj";
        ExpectedDuration result = null;
        if (obj.getId()!=null) {
            if (obj.getDurationText()!=null){
                obj1.setDurationText(obj.getDurationText());
            }
            if (obj.getStatus()!=null){
                obj1.setStatus(obj.getStatus());
            }
            obj1.setUpdateAt(DateUtil.getTimeLongCurrent());
            result = expectedDurationRepository.save(obj1);
            message = "update success";
            logger.info("update obj success");
            return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, result);
        } else {
            return new ResponseObject(Constant.STATUS_ACTION_FAIL, message, null);
        }

    }

    // get by id
    public ResponseObject getById(Long id) {
        logger.info("call to get obj by id: " + id);
        Optional<ExpectedDuration> obj = expectedDurationRepository.findById(id);
        String message = "can not find obj";
        ExpectedDuration obj1 = null;
        if (obj.isPresent()) {
            message = "success";
            obj1 = obj.get();
            logger.info("get obj success");
        }
        return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, obj1);
    }

    //All
    public ResponseObject findAll(){
        String message = "success";
        List<ExpectedDuration> list = expectedDurationRepository.findAll();
        return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message,null, list);

    }

    // Search/list
    public ResponseObject search(String keysearch, int page, int size) {
        logger.info("call to search obj with keysearch: " + keysearch);
        String message = "success";
        List<ExpectedDuration> list = expectedDurationRepository.searchObj(keysearch, PageRequest.of(page - 1, size));
        Long total = expectedDurationRepository.countObj(keysearch);
        return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, total, list);
    }
}
