package com.freelancer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewFreelancerObject {
	private String fullName;
	private String overview;
	private String skillName;

	public ViewFreelancerObject(String fullName, String overview) {
		super();
		this.fullName = fullName;
		this.overview = overview;
	}

}
