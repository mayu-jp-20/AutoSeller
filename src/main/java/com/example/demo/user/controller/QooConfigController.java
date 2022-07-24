package com.example.demo.user.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.aop.LogAspect;
import com.example.demo.api.qoo.QooService;
import com.example.demo.domain.UserModel;
import com.example.demo.service.UserService;
import com.example.demo.user.controller.userAop.BaseController;
import com.example.demo.user.domain.QooShopForm;

//Qoo10のショップ設定
@Controller
public class QooConfigController extends BaseController{

	@Autowired
	UserService userService;

	@Autowired
	QooService qooService;

	private Map<String,String>getRadioItems(String[]codeList){

		Map<String,String>selectMap=new LinkedHashMap<String,String>();

		for(String code:codeList) {
			selectMap.put(code, code);
		}
		return selectMap;
	}

	private Map<String,Integer> getQtyRadio(){

		Map<String,Integer>selectMap=new LinkedHashMap<String,Integer>();

		selectMap.put("1", 1);
		selectMap.put("2", 2);
		selectMap.put("3", 3);

		return selectMap;
	}

	@GetMapping("/qoo10_config")
	public String getQooShop(@ModelAttribute QooShopForm form,@AuthenticationPrincipal UserModel user,Model model) {

		String strCodeList=user.getAllShippingCode();

		String[]codeList=null;
		if (strCodeList==null) {
			model.addAttribute("shipping", "送料グループが設定されていません。Qoo10アカウントを登録してください。");
		}else {
			codeList = strCodeList.split(",");
			model.addAttribute("radioItems", getRadioItems(codeList));
		}

		model.addAttribute("qtyRadio", getQtyRadio());
		model.addAttribute("qooShopForm", user);
		setStatus(user,model);

		return "user/qoo10_config";
	}

	@PostMapping("/qoo10_config")
	public String postQooShop(@ModelAttribute QooShopForm form,@AuthenticationPrincipal UserModel user,Model model) {

		LogAspect.logger.info("args:{}", form);

		user.setFixingKeywardForItem(form.getFixingKeywardForItem());
		user.setShippingCode(form.getShippingCode());
		user.setItemPageHeader(form.getItemPageHeader());
		user.setItemPageFooter(form.getItemPageFooter());
		user.setUserId(user.getUserId());

		int result=userService.updateQooConfig(user);

		if(result!=1) {//失敗
			model.addAttribute("message", "登録に失敗しました。もう一度お試しください。");
		}

		model.addAttribute("link", "qoo_config");
		setStatus(user,model);

		return "user/success_for_user";
	}
}
