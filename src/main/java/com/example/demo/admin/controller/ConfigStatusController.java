package com.example.demo.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.domain.UserModel;
import com.example.demo.service.UserService;

//ユーザーのステータス変更画面
@Controller
public class ConfigStatusController {

	@Autowired
	UserService userService;

	@GetMapping("/config_status/{id:.+}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String getConfigStatus(@PathVariable("id")String mailAddress,
			Model model){

		UserModel user=userService.selectOneAll(mailAddress);
		model.addAttribute("user", user);

		return "admin/config_status";
	}

	@PostMapping("/config_status/{id:.+}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String postConfigStatus(
			@PathVariable("id")String mailAddress,
			@ModelAttribute("status")String status,Model model,UserModel userForm) {

		UserModel user=new UserModel();
		user.setStatus(status);
		user.setUserId(mailAddress);
		int result=userService.updateStatus(user);

		if(result==1) {
			model.addAttribute("message", "ステータスの変更に失敗しました。");
		}else {
			model.addAttribute("message", "ステータスを変更しました。");
		}
		return getConfigStatus(mailAddress,model);
	}

}
