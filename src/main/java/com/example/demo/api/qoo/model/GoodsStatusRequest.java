package com.example.demo.api.qoo.model;

import lombok.Data;

@Data
public class GoodsStatusRequest {

	private String key;//required
	private String ItemCode;//required
	private String SellerCode;
	private String Status;//required

}
