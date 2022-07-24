package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UserDao;
import com.example.demo.domain.UserModel;

@Service
public class UserService {

	@Autowired
	UserDao dao;

	public int insert(UserModel user) {
		return dao.insert(user);
	}

	public int insertAdmin(UserModel user) {
		return dao.insertAdmin(user);
	}

	public int insertQooInfo(UserModel account) {
		return dao.insertQooInfo(account);
	}

	public int updateStatus(UserModel user) {
		return dao.updateStatus(user);
	}

	public int updateEnabled(UserModel user){
		return dao.updateEnabled(user);
	}

	public int updatePrice(UserModel price) {
		return dao.updatePrice(price);
	}

	public int updateReferencePrice(UserModel user) {
		return dao.updateReferencePrice(user);
	}

	public List<UserModel>selectAll(){
		return dao.selectAll();
	}

	public int updateQooConnection(UserModel user) {
		return dao.updateQooConnection(user);
	}

	public int updateQooConfig(UserModel qoo){
		return dao.updateQooConfig(qoo);
	}

	public UserModel selectOneAll(String mailAddress) {
		return dao.selectOneAll(mailAddress);
	}

	public int updateLimitNum(UserModel user) {
		return dao.updateLimitNum(user);
	}
}
