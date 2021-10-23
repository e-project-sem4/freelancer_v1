package com.freelancer.service;


import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.freelancer.model.ExpectedDuration;
import com.freelancer.model.ResponseObject;
import com.freelancer.repository.ExpectedDurationRepository;
import com.freelancer.utils.ConfigLog;
import com.freelancer.utils.Constant;
import com.freelancer.utils.DateUtil;
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
            if (obj.getDurationText()!=null && !obj.getDurationText().isEmpty()){
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
        if (obj.isPresent()) {
            if (obj.get().getStatus()!=0){
                message = "success";
                logger.info("get obj success");
                return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, obj.get());
            }
            return new ResponseObject(Constant.STATUS_ACTION_FAIL, message, null);

        }
        return new ResponseObject(Constant.STATUS_ACTION_FAIL, message, null);
    }



    // Search/list
    public ResponseObject search(Specification<ExpectedDuration> specification, int page, int size, int sort) {
        List<ExpectedDuration> list = null;
        if (page>0&&size>0&&(sort>2||sort<1)){
            list = expectedDurationRepository.findAll(specification,PageRequest.of(page-1,size)).getContent();
        }else if (page>0&&size>0&&sort==1){
            list = expectedDurationRepository.findAll(specification,PageRequest.of(page-1,size, Sort.by("createAt").descending())).getContent();
        }else if (page>0&&size>0&&sort==2){
            list = expectedDurationRepository.findAll(specification,PageRequest.of(page-1,size, Sort.by("createAt").descending())).getContent();
        }else if (page==0&&size==0&&sort==0){
            list = expectedDurationRepository.findAll(specification);
        }

        Long total = Long.valueOf(expectedDurationRepository.findAll(specification).size());
        String message = "success";
        return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, total, list);
    }
}
