package com.example.demo.user.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.api.qoo.QooService;
import com.example.demo.api.qoo.RequestMakerQoo;
import com.example.demo.api.qoo.model.GetShippingInfoRequest;
import com.example.demo.api.qoo.model.GetShippingInfoResult;
import com.example.demo.api.qoo.model.ResultObject;
import com.example.demo.domain.NoticeModel;
import com.example.demo.domain.UserModel;
import com.example.demo.service.NoticeService;
import com.example.demo.service.UserService;
import com.example.demo.user.controller.userAop.BaseController;

//お知らせと新規注文の表示
@Controller
public class HomeController extends BaseController {

	@Autowired
	NoticeService noticeDao;

	@Autowired
	UserService userDao;

	@Autowired
	QooService qooService;

	@Autowired
	RequestMakerQoo requestMaker;

	@GetMapping("/home")
	public String getHome(@AuthenticationPrincipal UserModel user,Model model) {

		/*
		 * お知らせ表示
		 */
		List<NoticeModel> info=noticeDao.selectAll();
		List<NoticeModel>list=new ArrayList<>();

		Comparator<NoticeModel>compare=Comparator.comparing(NoticeModel::getDate,Comparator.reverseOrder());

		info.stream().sorted(compare)
			 .forEach(a ->
			 list.add(a));

		model.addAttribute("info", list);

		/*
		 * 新規注文を表示
		 */
		List<ResultObject>orderList=new ArrayList<>();

		GetShippingInfoRequest dto=requestMaker.getShippingInfoRequest(user,"2");
		GetShippingInfoResult res=qooService.getShippingInfo(dto);

		if(res.getResultCode()==0) {
			List<ResultObject>objList=res.getResultObject();

			for (ResultObject object:objList) {
				object.setShippingStatus("配送状況：" + object.getShippingStatus());
				object.setEstShippingDate("発送予定日：" + object.getEstShippingDate());
				object.setShippingDate("発送日：" + object.getShippingDate());
				object.setStrOrderPrice("注文価格：" + object.getOrderPrice().toString());
				object.setStrOrderQty("注文数量：" + String.valueOf(object.getOrderQty()));
				object.setStrDiscount("割引金額：" + object.getDiscount().toString());
				object.setReceiver(object.getReceiver() + "(" + object.getReceiver_gata() + ")");
				object.setReceiver("お届け先：" + object.getReceiver());
				object.setZipCode("〒" + object.getZipCode());
				object.setShippingAddr(object.getShippingAddr() + object.getAdd1() + object.getAdd2());
				object.setBuyer("購入者：" + object.getBuyer() + "(" + object.getBuyer_gata() + ")");
				object.setBuyerTel("購入者電話番号：" + object.getBuyerTel());
				object.setBuyerMobile("購入者携帯電話：" + object.getBuyerMobile());
				object.setReceiverTel("受取人電話番号：" + object.getReceiverTel());
				object.setReceiverMobile("受取人携帯電話：" + object.getReceiverMobile());
				object.setBuyerEmail("購入者メールアドレス：" + object.getBuyerEmail());

				orderList.add(object);
			}
		}else {
			model.addAttribute("message", "新規注文を取得できませんでした。");
			setStatus(user,model);
			return "user/home";
		}

		model.addAttribute("orderList", orderList);

		setStatus(user,model);
		return "user/home";
	}

}
