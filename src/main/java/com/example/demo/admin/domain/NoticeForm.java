package com.example.demo.admin.domain;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class NoticeForm {

	@NotBlank
	private String title;

	@NotBlank
	private String contents;

}
