package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.NoticeDao;
import com.example.demo.domain.NoticeModel;

@Service
public class NoticeService {

	@Autowired
	NoticeDao dao;

	//メッセージを返却しても良い
	public int insert(NoticeModel notice) {
		return dao.insert(notice);
	}

	public List<NoticeModel>selectAll(){
		return dao.selectAll();
	}



}
