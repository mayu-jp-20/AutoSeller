package com.example.demo.api.qoo.model;

import lombok.Data;

@Data
public class CreateCertificationKeyResponse {

	//販売者認証キー生成の算出データ

	private int resultCode;//呼び出しの成否Ｙ／Ｎ
	private String resultMsg;//失敗理由
	private String resultObject;//販売者認証コード

}
