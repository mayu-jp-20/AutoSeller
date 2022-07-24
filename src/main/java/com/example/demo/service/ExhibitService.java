package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.example.demo.dao.ExhibitDao;
import com.example.demo.domain.ExhibitModel;

@Service
public class ExhibitService {

	@Autowired
	ExhibitDao exhibitDao;

	public int countSeller(String asin){
		return exhibitDao.countSeller(asin);
	}

	public List<ExhibitModel>selectExhibitAll(String mailAddress){
		return exhibitDao.selectExhibitAll(mailAddress);
	}

	//Listを登録する場合、このメソッドの引数をListにしてメソッド内でループを回す
	public int insert(ExhibitModel exhibit)throws DataAccessException {

		int rowNumber=exhibitDao.insert(exhibit);

		return rowNumber;
	}

	//戻り値は成功件数にする
	public int insertAnalytics(String mailAddress,List<ExhibitModel>list) {

		int result=0;

		for (ExhibitModel model:list) {
			int rowNumber = exhibitDao.insertAnalytics(mailAddress, model);
			result=result+rowNumber;
		}
		return result;
	}

	public int deleteItem(String mailAddress,String asin){
		return exhibitDao.deleteItem(mailAddress,asin);

	}

	public List<ExhibitModel>selectByAsin(String asin){
		return exhibitDao.selectByAsin(asin);
	}

	public int countExhibitNum(String mailAddress){
		return exhibitDao.countExhibiNum(mailAddress);
	}

	public List<String>getItemCode(String mailAddress,String asin){
		return exhibitDao.getItemCode(mailAddress, asin);
	}

	public ExhibitModel selectOne(String mailAddress,String asin) {
		return exhibitDao.selectOne(mailAddress, asin);
	}
}
