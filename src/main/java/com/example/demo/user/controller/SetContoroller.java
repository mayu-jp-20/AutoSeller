package com.example.demo.user.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.domain.UserModel;
import com.example.demo.user.controller.userAop.BaseController;

@Controller
public class SetContoroller extends BaseController{

	@GetMapping("/set")
	public String getSet(@AuthenticationPrincipal UserModel user,Model model) {
		setStatus(user,model);
		return "user/set";
	}



}
