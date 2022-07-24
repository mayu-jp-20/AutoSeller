package com.example.demo.user.domain;

import lombok.Data;

@Data
public class AccountForm {

	//販売者キー入力の際に使う
	//バリデーションなし

	private String apiKey;
	private String qooId;//販売者IDまたはEmail
	private String qooPwd;//販売者パスワード



}
