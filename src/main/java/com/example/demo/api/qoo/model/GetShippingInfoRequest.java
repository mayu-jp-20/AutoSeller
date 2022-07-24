package com.example.demo.api.qoo.model;

import lombok.Data;

@Data
public class GetShippingInfoRequest {

	private String sellerCertificationKey;
	private String shippingStat;
	private String search_Sdate;
	private String search_Edate;

}
