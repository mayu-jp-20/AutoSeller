package com.example.demo.api.qoo.model;

import java.util.List;

import lombok.Data;

@Data
public class GetShippingInfoResult {

	private int resultCode;
	private String resultMsg;
	private List<ResultObject> resultObject;
}

