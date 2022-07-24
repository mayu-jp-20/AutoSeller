package com.example.demo.api.amazon.model;

import java.util.List;

import com.amazonservices.mws.products.model.SalesRankType;

import lombok.Data;

@Data
public class MyMatchingProductForIdResult {

	private String id;//ASIN
	private AttributeSets attrList;
	private List<SalesRankType> rankList;
	private com.amazonservices.mws.products.model.Error error;

}
