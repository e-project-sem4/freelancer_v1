package com.freelancer.service;

import java.util.List;

import com.freelancer.JwtAuthServiceApp;
import com.freelancer.model.*;
import com.freelancer.repository.*;
import com.freelancer.sendmail.SendMailModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.freelancer.utils.ConfigLog;
import com.freelancer.utils.Constant;
import com.freelancer.utils.DateUtil;
import com.google.gson.Gson;

@Service
public class ProposalService {

    Logger logger = ConfigLog.getLogger(ExpectedDurationService.class);

    Gson gson = new Gson();
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private ProposalRepository proposalRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserFreelancerRepository userFreelancerRepository;
    @Autowired
    private UserBusinessRepository userBusinessRepository;


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
        UserFreelancer userFreelancerUpdate = userFreelancerRepository.getOne(obj1.getUser_freelancer_id());
        UserBusiness userBusinessUpdate = userBusinessRepository.getOne(jobRepository.findById(obj1.getJob_id()).get().getUser_business_id());

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
                int sum =0;
                int size =0;
                //Average Rate
                for (Job j:userBusinessUpdate.getJobs()
                     ) {
                    for (Proposal p: j.getProposals()
                         ) {
                        if (p.getProposal_status_catalog_id()==3||p.getProposal_status_catalog_id()==5){
                            if (p.getClientGrade() !=null){
                                sum += p.getClientGrade();
                                size++;
                            }

                        }
                    }
                }

                userFreelancerUpdate.setAverageGrade(sum/size);
                userFreelancerRepository.save(userFreelancerUpdate);
            }
            if (obj.getClientComment()!=null && !obj.getClientComment().isEmpty()){
                obj1.setClientComment(obj.getClientComment());
            }
            if (obj.getFreelancerGrade()!=null && obj.getFreelancerGrade()>0){
                obj1.setFreelancerGrade(obj.getFreelancerGrade());

                int sum =0;
                int size =0;
                //Average Rate

                    for (Proposal p: userFreelancerUpdate.getProposals()
                    ) {
                        if (p.getProposal_status_catalog_id()==3||p.getProposal_status_catalog_id()==4){
                            if (p.getFreelancerGrade() !=null){
                                sum += p.getFreelancerGrade();
                                size++;
                            }
                        }
                    }


                userBusinessUpdate.setAverageGrade(sum/size);
                userBusinessRepository.save(userBusinessUpdate);
            }
            if (obj.getFreelancerComment()!=null && !obj.getFreelancerComment().isEmpty()){
                obj1.setFreelancerComment(obj.getFreelancerComment());
            }
            if (obj.getProposal_status_catalog_id()!=null &&((obj.getProposal_status_catalog_id()>0&&obj.getProposal_status_catalog_id()<6)
                    ||obj.getProposal_status_catalog_id()==99)){
                obj1.setProposal_status_catalog_id(obj.getProposal_status_catalog_id());
                if (obj.getProposal_status_catalog_id() == 5){
                    Job job = jobRepository.getOne(obj1.getJob_id());
                    job.setStatus(1);
                    jobRepository.save(job);
                    //send mail
                    User userBusiness = userRepository.getOne(jobRepository.getOne(obj1.getJob_id()).getUserBusiness().getUser_account_id());
                    JwtAuthServiceApp.listSendMail.add(new SendMailModel(userBusiness.getEmail(),"Your partner canceled the current job!", obj1.getJob_id().toString()));

                }else if (obj.getProposal_status_catalog_id() == 4){
                    Job job = jobRepository.getOne(obj1.getJob_id());
                    job.setStatus(1);
                    jobRepository.save(job);

                    //send mail
                    String email = obj1.getUserFreelancer().getUser().getEmail();
                    JwtAuthServiceApp.listSendMail.add(new SendMailModel(email,"Your partner canceled the current job!", obj1.getJob_id().toString()));
                }
                else if (obj.getProposal_status_catalog_id() == 3){
                    Job job = jobRepository.getOne(obj1.getJob_id());
                    job.setStatus(3);
                    jobRepository.save(job);
                    UserFreelancer userfreelancer = userFreelancerRepository.getOne(obj1.getUser_freelancer_id());
                    User user = userRepository.getOne(userfreelancer.getUser_account_id());
                    user.setBalance(user.getBalance()+obj1.getPaymentAmount()*0.8);
                    userRepository.save(user);


                    Transaction transaction = new Transaction();
                    transaction.setPrice(obj1.getPaymentAmount()*0.8);
                    transaction.setContent("Job completed");
                    transaction.setCreateAt(DateUtil.getTimeLongCurrent());
                    transaction.setType(Transaction.TransactionType.WAGE);
                    transaction.setJob_id(obj1.getJob_id());
                    transaction.setUser_account_id(obj1.getUserAccountId());
                    transactionRepository.save(transaction);

                    //send mail
                    String email = obj1.getUserFreelancer().getUser().getEmail();
                    JwtAuthServiceApp.listSendMail.add(new SendMailModel(email,"Your partner has received the results of your work. Amount has been added to the balance. Thank you for using the service!", obj1.getJob_id().toString()));
                }
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

//    public ResponseObject cancelByFl(Long id) {
//        logger.info("call to get obj to delete by id: " + id);
//        Proposal obj = proposalRepository.getOne(id);
//        String message = "can not find obj";
//        Proposal result = null;
//        if (obj.getId()!=null) {
//            obj.setProposal_status_catalog_id(5L);
//            result = proposalRepository.save(obj);
//            Job job = jobRepository.getOne(obj.getJob_id());
//            job.setStatus(1);
//            jobRepository.save(job);
//            message = "delete success";
//            logger.info("delete obj success");
//            return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, result);
//        } else {
//            return new ResponseObject(Constant.STATUS_ACTION_FAIL, message, null);
//        }
//
//    }
//
//
//    public ResponseObject cancelByBsn(Long id) {
//        logger.info("call to get obj to delete by id: " + id);
//        Proposal obj = proposalRepository.getOne(id);
//        String message = "can not find obj";
//        Proposal result = null;
//        if (obj.getId()!=null) {
//            obj.setProposal_status_catalog_id(4L);
//            result = proposalRepository.save(obj);
//            Job job = jobRepository.getOne(obj.getJob_id());
//            job.setStatus(1);
//            jobRepository.save(job);
//            message = "delete success";
//            logger.info("delete obj success");
//            return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, message, result);
//        } else {
//            return new ResponseObject(Constant.STATUS_ACTION_FAIL, message, null);
//        }

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
//}
