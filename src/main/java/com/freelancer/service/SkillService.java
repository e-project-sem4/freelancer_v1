package com.freelancer.service;

import org.springframework.stereotype.Service;
import com.freelancer.model.ResponseObject;
import com.freelancer.model.Skill;
import com.freelancer.repository.SkillRepository;
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
public class SkillService  {
    Logger logger = ConfigLog.getLogger(SkillService.class);

    Gson gson = new Gson();
    @Autowired
    private SkillRepository skillRepository;

    //add
    public ResponseObject save(Skill skill){
        String message = "not success";
        logger.info("call to Create Skill" + skill.toString());
        Skill result = skillRepository.save(skill);
        if (result!=null){
            message="success";
        }
        return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, result);
    }


    //delete
    public ResponseObject delete(Long id) {

        logger.info("call to get skill to delete by id: " + id);
        Optional<Skill> optionalSkill = skillRepository.findById(id);
        String message = "can not find skill";
        Skill result = null;
        if (optionalSkill.isPresent()) {
            skillRepository.deleteById(id);
            message = "delete success";
            logger.info("delete skill success");
            return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, null);
        }else {
            return new ResponseObject(Constant.STATUS_ACTION_FAIL,message,null);
        }

    }

    //update
    public ResponseObject update(Skill skill,Long id){
        logger.info("call to get skill to update by id: " + id);
        Optional<Skill> optionalSkill = skillRepository.findById(id);
        String message = "can not skill";
        Skill result = null;
        if (optionalSkill.isPresent()) {
            Skill skill1 = optionalSkill.get();
            result = skillRepository.save(skill);
            message = "update success";
            logger.info("update skill success");
            return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, result);
        }else {
            return new ResponseObject(Constant.STATUS_ACTION_FAIL,message,null);
        }

    }

    //get by id
    public ResponseObject getById(Long id) {
        logger.info("call to get skill by id: " + id);
        Optional<Skill> optionalSkill = skillRepository.findById(id);
        String message = "can not find skill";
        Skill skill = null;
        if (optionalSkill.isPresent()) {
            message = "success";
            skill = optionalSkill.get();
            logger.info("get skill success");
        }
        return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, skill);
    }



    // Search/list
    public ResponseObject search(String keysearch, int page, int size) {
        logger.info("call to search skill with keysearch: " + keysearch);
        String message = "success";
        List<Skill> list = skillRepository.searchSkill(keysearch, PageRequest.of(page - 1, size));
        Long total = skillRepository.countSkill(keysearch);
        return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, total, list);
    }
}
