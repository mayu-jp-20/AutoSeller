package com.example.demo.dao;

import java.util.List;

import com.example.demo.domain.UserModel;

public interface UserDao {

	//ユーザーテーブル読み書き用のインターフェース

	public int insert(UserModel user);

	public int insertAdmin(UserModel user);

	public int insertQooInfo(UserModel account);

	public int updateStatus(UserModel user);

	public int updateEnabled(UserModel user);

	public int updateLimitNum(UserModel user);

	public int updateQooConnection(UserModel user);

	public int updatePrice(UserModel price);

	public int updateReferencePrice(UserModel user);

	public int updateQooConfig(UserModel qoo);

	public List<UserModel> selectAll();

	public UserModel selectOneAll(String mailAddress);


}
