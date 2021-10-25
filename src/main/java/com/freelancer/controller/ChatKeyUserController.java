package com.freelancer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.freelancer.model.ResponseObject;
import com.freelancer.service.ChatKeyUserService;

@RestController
@RequestMapping("/api/v1/chatkeyuser")
@CrossOrigin
public class ChatKeyUserController {

	@Autowired
	ChatKeyUserService chatKeyUserService;

	@RequestMapping(value = "/getbysender", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<ResponseObject> getAll(@RequestParam Long senderId) {
		ResponseObject result = chatKeyUserService.getAllChatBySenderId(senderId);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
