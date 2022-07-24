package com.example.demo.dao.repository;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.admin.domain.ExhibitLimitModel;
import com.example.demo.aop.LogAspect;
import com.example.demo.dao.MaximumDao;

@Repository
public class MaximumDaoRepository implements MaximumDao{

	@Autowired
	JdbcTemplate jdbc;

	@Override
	public int update(ExhibitLimitModel model) {

		int rowNumber=jdbc.update("UPDATE maximum SET platinumQ=?, "
				+ "goldQ=?,"
				+ "standardQ=?,"
				+ "platinumY=?,"
				+ "goldY=?,"
				+ "standardY=?,"
				+ "platinumQY=?,"
				+ "goldQY=?,"
				+ "standardQY=? "
				+ "WHERE maximum_id=?"
				,model.getPlatinumQ()
				,model.getGoldQ()
				,model.getStandardQ()
				,model.getPlatinumY()
				,model.getGoldY()
				,model.getStandardY()
				,model.getPlatinumQY()
				,model.getGoldQY()
				,model.getStandardQY()
				,model.getMaximumId());

		if(rowNumber!=1) {
			LogAspect.logger.warn("Failed MaximumDaoRepository.update(ExhibitLimitModel) ExhibitLimitModel:{}",model);
		}

		return rowNumber;
	}

	@Override
	public ExhibitLimitModel select(String id) {

		Map<String,Object>map=jdbc.queryForMap("SELECT * "
				+ "FROM maximum "
				+ "WHERE maximum_id=?"
				, id);

		ExhibitLimitModel limit=new ExhibitLimitModel();
		limit.setPlatinumQ((int)map.get("platinumQ"));
		limit.setGoldQ((int)map.get("goldQ"));
		limit.setStandardQ((int)map.get("standardY"));
		limit.setPlatinumY((int)map.get("platinumY"));
		limit.setGoldY((int)map.get("goldY"));
		limit.setStandardY((int)map.get("standardY"));
		limit.setPlatinumQY((int)map.get("platinumQY"));
		limit.setGoldQY((int)map.get("goldQY"));
		limit.setStandardQY((int)map.get("standardQY"));
		return limit;
	}
}
