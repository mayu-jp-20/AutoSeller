package com.example.demo.component;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dao.UserDao;
import com.example.demo.domain.UserModel;

@Component
public class PriceUtil {

	@Autowired
	UserDao userDao;

	public BigDecimal caliculatePrice(BigDecimal amazonPrice,BigDecimal shipping,String mailAddress) {

		UserModel priceModel=userDao.selectOneAll(mailAddress);

		BigDecimal price1=priceModel.getPrice1();
		BigDecimal price2=priceModel.getPrice2();
		BigDecimal price3=priceModel.getPrice3();
		BigDecimal price4=priceModel.getPrice4();
		BigDecimal price5=priceModel.getPrice5();
		BigDecimal rate1=priceModel.getRate1();
		BigDecimal rate2=priceModel.getRate2();
		BigDecimal rate3=priceModel.getRate3();
		BigDecimal rate4=priceModel.getRate4();
		BigDecimal rate5=priceModel.getRate5();
		BigDecimal rate6=priceModel.getRate6();
		BigDecimal fixing1=priceModel.getFixing1();
		BigDecimal fixing2=priceModel.getFixing2();
		BigDecimal fixing3=priceModel.getFixing3();
		BigDecimal fixing4=priceModel.getFixing4();
		BigDecimal fixing5=priceModel.getFixing5();
		BigDecimal fixing6=priceModel.getFixing6();


		BigDecimal itemPrice = null;

		//amazonPrice<getPrice1のとき-1が返ってくる
		if(amazonPrice.compareTo(price1)==-1) {

			BigDecimal b1=amazonPrice.multiply(rate1);
		    itemPrice=b1.add(fixing1);

		}else if(amazonPrice.compareTo(price2)==-1) {

			BigDecimal b1=amazonPrice.multiply(rate2);
			itemPrice=b1.add(fixing2);

		}else if(amazonPrice.compareTo(price3)==-1){

			BigDecimal b1=amazonPrice.multiply(rate3);
			itemPrice=b1.add(fixing3);

		}else if(amazonPrice.compareTo(price4)==-1){

			BigDecimal b1=amazonPrice.multiply(rate4);
			itemPrice=b1.add(fixing4);

		}else if(amazonPrice.compareTo(price5)==-1) {

			BigDecimal b1=amazonPrice.multiply(rate5);
			itemPrice=b1.add(fixing5);
		}else if(amazonPrice.compareTo(price5)==1){//〇〇円以上の場合

			BigDecimal b1=amazonPrice.multiply(rate6);
			itemPrice=b1.add(fixing6);
		}else {//入力されていない場合
			itemPrice=amazonPrice;
		}

		/*
		 * パントリー商品の場合（配送料が390円のとき
		 */
		if(shipping.equals(BigDecimal.valueOf(390))) {
			itemPrice.add(shipping);
		}

		return itemPrice;

	}

	public BigDecimal referencePrice(BigDecimal sellingPrice,BigDecimal referenceRate) {

		BigDecimal referencePrice=sellingPrice.multiply(referenceRate);

		if(referenceRate==null) {
			referencePrice=sellingPrice;
		}

		return referencePrice;


	}

}
