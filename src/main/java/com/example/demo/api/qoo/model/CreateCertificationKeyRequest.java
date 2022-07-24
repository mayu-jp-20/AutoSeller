package com.example.demo.api.qoo.model;

import lombok.Data;

@Data
public class CreateCertificationKeyRequest {

	//販売者認証キー生成のリクエストパラメータ

	private String key;//付与されたAPIキー
	private String qooId;//販売者IDまたはEmail
	private String qooPwd;//販売者パスワード

}
