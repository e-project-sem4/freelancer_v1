package com.freelancer.utils;

public class UtilService {

	public static String convertRoomKey(Long senderId, Long receiverId, Long jobId) {
		return senderId + "_" + receiverId + "_" + jobId;
	}
}
