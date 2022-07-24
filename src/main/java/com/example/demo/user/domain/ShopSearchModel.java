package com.example.demo.user.domain;

import java.util.Date;

import lombok.Data;

@Data
public class ShopSearchModel {

	private String searchId;
	private Date searchDate;
	private String mailAddress;
	private String itemCode;
	private int review;
	private String url;
	private String itemName;
}
