package com.example.demo.dao.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.aop.LogAspect;
import com.example.demo.dao.NgDao;

@Repository
public class NgDaoRepository implements NgDao{

	@Autowired
	JdbcTemplate jdbc;

	/*
	 * タイプ(ngType)は"ASIN"か"WORD"か"BROWSE"
	 */
	@Override
	public int insert(String ngContents,String ngType)  {

		int rowNumber=jdbc.update("INSERT INTO ng(ng_id,"
				+ "ng_contents,"
				+ "contents_type)"
				+ "VALUES(?,?,?)"
				,UUID.randomUUID().toString()
				,ngContents
				,ngType);

		if(rowNumber!=1) {
			LogAspect.logger.warn("Failed NgDaoRepository.insert(String1,String2) String1:{} String2:{]",ngContents,ngType);
		}

		return rowNumber;
	}

	@Override
	public List<String> selectNgContents(String contentsType){

		List<Map<String, Object>>getList=jdbc.queryForList("SELECT ng_contents "
				+ "FROM ng "
				+ "WHERE contents_type=?"
				,contentsType);

		List<String> ngAsinList=new ArrayList<>();

		for(Map<String,Object>map:getList) {
			ngAsinList.add((String)map.get("ng_contents"));
		}

		return ngAsinList;
	}
}
