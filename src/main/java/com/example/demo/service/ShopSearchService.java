package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.ShopSearchDao;
import com.example.demo.user.domain.ShopSearchModel;

@Service
public class ShopSearchService {

	@Autowired
	ShopSearchDao dao;

	public List<ShopSearchModel>selectAll(String mailAddress){
		return dao.selectAll(mailAddress);
	}

	public void insert(List<ShopSearchModel> list) {

		for(ShopSearchModel model:list) {
			dao.insert(model);
		}
	}

	public void update(List<ShopSearchModel> list) {

		for(ShopSearchModel model:list) {
			dao.update(model);
		}
	}

}
