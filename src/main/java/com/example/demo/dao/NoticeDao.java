package com.example.demo.dao;

import java.util.List;

import com.example.demo.domain.NoticeModel;

public interface NoticeDao {

	//お知らせテーブルの読み書き用インターフェース

	public int insert(NoticeModel notice);

	public List<NoticeModel> selectAll();


}
