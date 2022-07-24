package com.example.demo.dao.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.aop.LogAspect;
import com.example.demo.dao.NoticeDao;
import com.example.demo.domain.NoticeModel;

@Repository
public class NoticeDaoRepository implements NoticeDao{

	@Autowired
	JdbcTemplate jdbc;

	@Override
	public int insert(NoticeModel notice)  {

		int rowNumber=jdbc.update("INSERT INTO notice(notice_id,"
				+ "title,"
				+ "contents,"
				+ "insert_date)"
				+ "VALUES(?,?,?,?)"
				,UUID.randomUUID().toString()
				,notice.getTitle()
				,notice.getContents()
				,LocalDate.now().toString());

		if(rowNumber!=1) {
			LogAspect.logger.warn("Failed to NoticeDaoRepository.inesrt(NoticeModel) args:{}", notice);

		}

		return rowNumber;
	}

	@Override
	public List<NoticeModel> selectAll()  {

		List<Map<String,Object>>getList=jdbc.queryForList("SELECT * "
				+ "FROM notice");

		List<NoticeModel>noticeList=new ArrayList<>();

		for(Map<String,Object>map:getList) {

			NoticeModel notice=new NoticeModel();

			notice.setNoticeId((String)map.get("notice_id"));
			notice.setTitle((String)map.get("title"));
			notice.setContents((String)map.get("contents"));
			notice.setDate((String)map.get("insert_date"));
			noticeList.add(notice);
		}
		return noticeList;
	}



}
