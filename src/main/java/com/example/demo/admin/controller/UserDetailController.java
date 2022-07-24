package com.example.demo.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.component.ApplicationUtil;
import com.example.demo.domain.UserModel;
import com.example.demo.service.UserService;

@Controller
public class UserDetailController {

	@Autowired
	UserService userService;

	@Autowired
	ApplicationUtil appUtil;

	@GetMapping("/user_detail_admin/{id:.+}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String getUserDetail(@ModelAttribute UserModel userForm,@PathVariable("id")String mailAddress,
			Model model){

		if(mailAddress!=null&&mailAddress.length()>0) {

			UserModel userModel=userService.selectOneAll(mailAddress);

			String enable=null;
			String change=null;

			if(userModel.isEnabled()) {
				enable="可";
				change="使用停止にする";
			}else {
				enable="不可";
				change="使用可能にする";
			}

			String role=userModel.getStatus();
			String status=appUtil.getStatus(role);


			model.addAttribute("change", change);

			userForm.setName(userModel.getName());
			userForm.setKanaName(userModel.getKanaName());
			userForm.setUserId(userModel.getUserId());
			userForm.setSex(userModel.getSex());
			userForm.setBirthday(userModel.getBirthday());
			userForm.setStatus(status);
			userForm.setStrEnable(enable);

			model.addAttribute("userForm", userForm);
		}
		return "admin/user_detail_admin";
	}


	@GetMapping("/user_detail_admin/change/{id:.+}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String getEnable(Model model,@PathVariable("id")String mailAddress,UserModel userForm){

		boolean enable = false;

		UserModel userModel=userService.selectOneAll(mailAddress);

		if(userModel.isEnabled()) {//可の状態のときは、不可にする
			enable=false;
		}else {//不可の状態のときは、可にする
			enable=true;
		}

		userModel.setEnabled(enable);
		int result=userService.updateEnabled(userModel);

		if(result!=1) {
			model.addAttribute("message", "変更に失敗しました。");
		}

		model.addAttribute("link", "user_detail__admin/"+mailAddress);

		return "admin/success_for_admin";
	}

}
