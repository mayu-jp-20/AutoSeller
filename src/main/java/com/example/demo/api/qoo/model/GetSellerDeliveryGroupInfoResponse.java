package com.example.demo.api.qoo.model;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class GetSellerDeliveryGroupInfoResponse {

	private int resultCode;
	private String resultMsg;
	private List<GetSellerDeliveryGroupInfoObject> list;

}
