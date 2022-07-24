package com.example.demo.api.qoo.model;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class ItemResponse {

	//Qoo10API新商品登録メソッドのレスポンス用クラス

	private int ResultCode;//呼び出しの成否

	private String ResultMsg;//成功時に登録された商品コード/失敗時の失敗理由





}
