package com.example.demo.user.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.domain.UserModel;
import com.example.demo.service.UserService;
import com.example.demo.user.controller.userAop.BaseController;
import com.example.demo.user.domain.PriceForm;


@Controller
public class QooPriceController extends BaseController{

	@Autowired
	UserService userService;

	@GetMapping("/qoo10_price")
	public String getQooPrice(@ModelAttribute PriceForm form,@AuthenticationPrincipal UserModel user,Model model){

		//データバインドするときに、PriceFormを使いたいからPriceFormに値を設定する
		form.setPrice1(user.getPrice1());
		form.setPrice2(user.getPrice2());
		form.setPrice3(user.getPrice3());
		form.setPrice4(user.getPrice4());
		form.setPrice5(user.getPrice5());
		form.setRate1(user.getRate1());
		form.setRate2(user.getRate2());
		form.setRate3(user.getRate3());
		form.setRate4(user.getRate4());
		form.setRate5(user.getRate5());
		form.setRate6(user.getRate6());
		form.setFixing1(user.getFixing1());
		form.setFixing2(user.getFixing2());
		form.setFixing3(user.getFixing3());
		form.setFixing4(user.getFixing4());
		form.setFixing5(user.getFixing5());
		form.setFixing6(user.getFixing6());

		model.addAttribute("form", form);
		setStatus(user,model);

		return "user/qoo10_price";
	}

	@PostMapping("/qoo10_price")
	public String postQooPrice(@ModelAttribute@Validated PriceForm form,BindingResult br,@AuthenticationPrincipal UserModel user,
			Model model) {

		if(br.hasErrors()) {
			model.addAttribute("message", "0より大きい数字を入力してください");
			return "user/qoo10_price";
		}

		BigDecimal two=BigDecimal.valueOf(2);

		List<BigDecimal>priceList=new ArrayList<>();
		priceList.add(form.getPrice1());
		priceList.add(form.getPrice2());
		priceList.add(form.getPrice3());
		priceList.add(form.getPrice4());
		priceList.add(form.getPrice5());

		List<BigDecimal>priceList2=new ArrayList<>();
		priceList2.add(form.getPrice2());
		priceList2.add(form.getPrice3());
		priceList2.add(form.getPrice4());
		priceList2.add(form.getPrice5());

		for(BigDecimal price:priceList) {

			if(price==null) {

			}else {//priceが空欄じゃない場合
				for(BigDecimal price2:priceList2) {

					if(price2==null) {

					}else {//price2が空欄じゃない場合

						int result=price.add(two).compareTo(price2);

						if(result==0||result==1) {

							model.addAttribute("message", "金額の順番に誤りがあります。");

							return getQooPrice(form,user,model);
						}
					}
				}
			}
			if(priceList2.size()==0) {
				break;
			}else {
				priceList2.remove(0);
			}
		}

		//Qoo10の上乗せ額をユーザーのデータベースに登録する
		UserModel price=new UserModel();

		price.setUserId(user.getUserId());
		price.setPrice1(form.getPrice1());
		price.setPrice2(form.getPrice2());
		price.setPrice3(form.getPrice3());
		price.setPrice4(form.getPrice4());
		price.setPrice5(form.getPrice5());
		price.setRate1(form.getRate1());
		price.setRate2(form.getRate2());
		price.setRate3(form.getRate3());
		price.setRate4(form.getRate4());
		price.setRate5(form.getRate5());
		price.setRate6(form.getRate6());
		price.setFixing1(form.getFixing1());
		price.setFixing2(form.getFixing2());
		price.setFixing3(form.getFixing3());
		price.setFixing4(form.getFixing4());
		price.setFixing5(form.getFixing5());
		price.setFixing6(form.getFixing6());

		int result=userService.updatePrice(price);

		if(result!=1) {
			model.addAttribute("message", "データの更新に失敗しました。");
		}

		model.addAttribute("link", "qoo10_price");
		setStatus(user,model);

		return "user/success_for_user";
	}
}
