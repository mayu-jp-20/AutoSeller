package com.example.demo.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.domain.UserModel;
import com.example.demo.service.UserService;
import com.example.demo.user.controller.userAop.BaseController;

@Controller
public class SetConnectionController extends BaseController{

	@Autowired
	UserService service;

	@GetMapping("/set_connection")
	public String getSetConnection(@AuthenticationPrincipal UserModel user,Model model){

		//アカウント接続状態表示（trueは接続中、falseは未接続
		boolean connection=user.isConnectQooAccount();

		String str=null;

		if(connection==true) {
			str="接続状態：接続中";
		}else {
			str="接続状態：未接続";
		}

		model.addAttribute("connection", str);
		setStatus(user,model);

		return "user/set_connection";
	}

	@PostMapping("/set_connection")
	public String updateConnect(@AuthenticationPrincipal UserModel user,Model model) {

		boolean connection=user.isConnectQooAccount();

		int result=0;

		//接続中なら接続を切るメソッド呼び出し
		if(connection) {
			user.setConnectQooAccount(false);
			result=service.updateQooConnection(user);

		//未接続なら接続するメソッド呼び出し
		}else {
			result=service.updateQooConnection(user);
		}

		if(result!=1) {
			model.addAttribute("message", "更新に失敗しました");
			setStatus(user,model);
			return getSetConnection(user,model);
		}

		model.addAttribute("link", "set_connection");
		setStatus(user,model);

		return "user/success_for_user";
	}

}
