package com.freelancer.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.freelancer.model.Proposal;
import com.freelancer.model.ResponseObject;
import com.freelancer.model.User;
import com.freelancer.repository.JobRepository;
import com.freelancer.repository.ProposalRepository;
import com.freelancer.repository.UserRepository;
import com.freelancer.utils.ConfigLog;
import com.freelancer.utils.Constant;
import com.freelancer.utils.DateUtil;
import com.google.gson.Gson;

@Service
public class ProposalService {

    Logger logger = ConfigLog.getLogger(ExpectedDurationService.class);

    Gson gson = new Gson();
    @Autowired
    private ProposalRepository proposalRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JobRepository jobRepository;


    // add
    public ResponseObject save(Proposal obj,String username) {
        String message = "not success";
        logger.info("call to Create obj" + obj.toString());
        obj.setCreateAt(DateUtil.getTimeLongCurrent());
        obj.setProposal_status_catalog_id(1L);
        User user = userRepository.findByUsername(username);
        Long currentBusiness = user.getUserBusinesses().getId();
        Long currentFreelancer = user.getUserBusinesses().getId();
        Long businessJob = jobRepository.findById(obj.getJob_id()).get().getUser_business_id();
        if (currentBusiness == businessJob){
            message="You are the post owner";
            return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, null);
        }
        obj.setUser_freelancer_id(currentFreelancer);
        Proposal result = proposalRepository.save(obj);
            message = "success";
        return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, result);
    }

    public ResponseObject getByJob(Long jobId) {
        List<Proposal> proposals = proposalRepository.findAllByJob_id(jobId);
        String message ="success";
        return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, proposals);
    }

    // delete
    public ResponseObject delete(Long id) {

        logger.info("call to get obj to delete by id: " + id);
        Proposal obj = proposalRepository.getOne(id);
        String message = "can not find obj";
        Proposal result = null;
        if (obj.getId()!=null) {
            obj.setProposal_status_catalog_id(99L);
            result = proposalRepository.save(obj);
            message = "delete success";
            logger.info("delete obj success");
            return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, result);
        } else {
            return new ResponseObject(Constant.STATUS_ACTION_FAIL, message, null);
        }

    }

    // update
    public ResponseObject update(Proposal obj, Long id) {
        logger.info("call to get obj to update by id: " + id);
        Proposal obj1 = proposalRepository.getOne(id);
        String message = "can not find obj";
        Proposal result = null;
        if (obj.getId()!=null) {
            if (obj.getDescription()!=null && !obj.getDescription().isEmpty()){
                obj1.setDescription(obj.getDescription());
            }
            if (obj.getPaymentAmount()!=null && obj.getPaymentAmount()>0){
                obj1.setPaymentAmount(obj.getPaymentAmount());
            }
            if (obj.getJob_id()!=null && obj.getJob_id()>0){
                obj1.setJob_id(obj.getJob_id());
            }
            if (obj.getClientGrade()!=null && obj.getClientGrade()>0){
                obj1.setClientGrade(obj.getClientGrade());
            }
            if (obj.getClientComment()!=null && !obj.getClientComment().isEmpty()){
                obj1.setClientComment(obj.getClientComment());
            }
            if (obj.getFreelancerGrade()!=null && obj.getFreelancerGrade()>0){
                obj1.setFreelancerGrade(obj.getFreelancerGrade());
            }
            if (obj.getFreelancerComment()!=null && !obj.getFreelancerComment().isEmpty()){
                obj1.setFreelancerComment(obj.getFreelancerComment());
            }
            if (obj.getProposal_status_catalog_id()!=null &&((obj.getProposal_status_catalog_id()>0&&obj.getProposal_status_catalog_id()<6)
                    ||obj.getProposal_status_catalog_id()==99)){
                obj1.setProposal_status_catalog_id(obj.getProposal_status_catalog_id());
            }

            obj1.setUpdateAt(DateUtil.getTimeLongCurrent());
            result = proposalRepository.save(obj1);
            message = "update success";
            logger.info("update obj success");
            return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, result);
        } else {
            return new ResponseObject(Constant.STATUS_ACTION_FAIL, message, null);
        }

    }

    public ResponseObject cancelByFl(Long id) {
        logger.info("call to get obj to delete by id: " + id);
        Proposal obj = proposalRepository.getOne(id);
        String message = "can not find obj";
        Proposal result = null;
        if (obj.getId()!=null) {
            obj.setProposal_status_catalog_id(5L);
            result = proposalRepository.save(obj);
            message = "delete success";
            logger.info("delete obj success");
            return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, result);
        } else {
            return new ResponseObject(Constant.STATUS_ACTION_FAIL, message, null);
        }

    }


    public ResponseObject cancelByBsn(Long id) {
        logger.info("call to get obj to delete by id: " + id);
        Proposal obj = proposalRepository.getOne(id);
        String message = "can not find obj";
        Proposal result = null;
        if (obj.getId()!=null) {
            obj.setProposal_status_catalog_id(4L);
            result = proposalRepository.save(obj);
            message = "delete success";
            logger.info("delete obj success");
            return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, result);
        } else {
            return new ResponseObject(Constant.STATUS_ACTION_FAIL, message, null);
        }

    }
//
//    // get by id
//    public ResponseObject getById(Long id) {
//        logger.info("call to get obj by id: " + id);
//        Optional<ExpectedDuration> obj = expectedDurationRepository.findById(id);
//        String message = "can not find obj";
//        ExpectedDuration obj1 = null;
//        if (obj.isPresent()) {
//            if (obj.get().getStatus()!=0){
//                message = "success";
//                logger.info("get obj success");
//                return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, obj.get());
//            }
//            return new ResponseObject(Constant.STATUS_ACTION_FAIL, message, null);
//
//        }
//        return new ResponseObject(Constant.STATUS_ACTION_FAIL, message, null);
//    }
//
//
//
//    // Search/list
//    public ResponseObject search(Specification<ExpectedDuration> specification, int page, int size, int sort) {
//        List<ExpectedDuration> list = null;
//        if (page>0&&size>0&&(sort>2||sort<1)){
//            list = expectedDurationRepository.findAll(specification, PageRequest.of(page-1,size)).getContent();
//        }else if (page>0&&size>0&&sort==1){
//            list = expectedDurationRepository.findAll(specification,PageRequest.of(page-1,size, Sort.by("createAt").descending())).getContent();
//        }else if (page>0&&size>0&&sort==2){
//            list = expectedDurationRepository.findAll(specification,PageRequest.of(page-1,size, Sort.by("createAt").descending())).getContent();
//        }else if (page==0&&size==0&&sort==0){
//            list = expectedDurationRepository.findAll(specification);
//        }
//
//        Long total = Long.valueOf(expectedDurationRepository.findAll(specification).size());
//        String message = "success";
//        return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, total, list);
//    }
}
