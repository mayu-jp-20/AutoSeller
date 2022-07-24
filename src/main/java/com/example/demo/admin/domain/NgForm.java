package com.example.demo.admin.domain;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class NgForm {

	//NGキーワードの入力フォーム

	@NotBlank
	private String ngThings;

	@NotBlank
	private String type;

}
