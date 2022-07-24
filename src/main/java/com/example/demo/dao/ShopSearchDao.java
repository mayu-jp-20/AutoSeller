package com.example.demo.dao;

import java.util.List;

import com.example.demo.user.domain.ShopSearchModel;

public interface ShopSearchDao {

	public List<ShopSearchModel>selectAll(String mailAddress);

	public int insert(ShopSearchModel model);

	public int update(ShopSearchModel model);

}
