package com.freelancer.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.freelancer.model.ChatKeyUser;
import com.freelancer.model.ResponseObject;
import com.freelancer.repository.ChatKeyUserRepository;
import com.freelancer.utils.ConfigLog;
import com.freelancer.utils.Constant;

@Service
public class ChatKeyUserService {

	Logger logger = ConfigLog.getLogger(ChatKeyUserService.class);

	@Autowired
	ChatKeyUserRepository chatKeyUserRepository;

	public ResponseObject getAllChatBySenderId(Long senderId) {
		logger.info("call to get all chat by sender id: " + senderId);
		List<ChatKeyUser> chatKeyUsers = chatKeyUserRepository.findAllBySenderId(senderId);
		return new ResponseObject(Constant.STATUS_ACTION_SUCCESS, "success", null, chatKeyUsers);
	}
}
