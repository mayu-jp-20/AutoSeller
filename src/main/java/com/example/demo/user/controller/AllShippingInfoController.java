package com.example.demo.user.controller;

import java.util.ArrayList;
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
import com.example.demo.domain.UserModel;
import com.example.demo.service.UserService;
import com.example.demo.user.controller.userAop.BaseController;

//注文履歴
@Controller
public class AllShippingInfoController extends BaseController{

	@Autowired
	QooService qooService;

	@Autowired
	UserService userDao;

	@Autowired
	RequestMakerQoo requestMaker;

	@GetMapping("/order_history")
	public String getAllShippingInfo(@AuthenticationPrincipal UserModel user,Model model) {

	    List<GetShippingInfoRequest>requestList=new ArrayList<>();

		List<String>statusList=new ArrayList<>();
		statusList.add("1");
		statusList.add("2");
		statusList.add("3");
		statusList.add("4");
		statusList.add("5");

		for (int i = 1; i < statusList.size(); i++) {
			GetShippingInfoRequest request=requestMaker.getShippingInfoRequest(user, statusList.get(i));
			requestList.add(request);
		}

		List<ResultObject>orderList=new ArrayList<>();

		for (GetShippingInfoRequest request:requestList) {

			GetShippingInfoResult res=qooService.getShippingInfo(request);

			if(res.getResultCode()==0) {//ResultObjectのみをリストに追加する

				//表示用にタイトルを追加する
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

			}else {//一つでも取得失敗しているものがあれば終了させる
				model.addAttribute("message", "注文履歴を取得できませんでした。");
				return "user/all_shipping_info";
			}
		}

		model.addAttribute("orderList", orderList);
		setStatus(user,model);

		return "user/order_history";
	}

}
