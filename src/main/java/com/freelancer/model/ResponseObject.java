package com.freelancer.model;

import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseObject {

	private String status;
	private String message;
	private Long total;
	private Object result;

	public ResponseObject(String status, String message, Object result) {
		super();
		this.status = status;
		this.message = message;
		this.result = result;
	}

}
