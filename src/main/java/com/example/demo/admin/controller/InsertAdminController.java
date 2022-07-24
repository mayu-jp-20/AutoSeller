package com.example.demo.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.component.PropertyUtil;
import com.example.demo.domain.UserModel;
import com.example.demo.service.UserService;
import com.example.demo.user.domain.SignupForm;

@Controller
public class InsertAdminController {

	@Autowired
	UserService service;

	@GetMapping("/SvTtkOg7Fj")
	public String getInsertAdmin(@ModelAttribute SignupForm signupForm,Model model) {
		return "admin/SvTtkOg7Fj";
	}

	@PostMapping("/SvTtkOg7Fj")
	public String postInsertAdmin(@ModelAttribute @Validated SignupForm form,BindingResult br,Model model) {

		if(br.hasErrors()) {
			return "admin/SvTtkOg7Fj";
		}

		//秘密キーのチェック
		if(form.getSecretKey().equals(PropertyUtil.getAdminSecretKey())==false) {
			model.addAttribute("message", "登録失敗しました。");
			return "admin/SvTtkOg7Fj";
		}

		UserModel user=new UserModel();

		user.setName(form.getName());
		user.setKanaName(form.getKanaName());
		user.setSex(form.getSex());
		user.setBirthday(form.getBirthday());
		user.setUserId(form.getMailAddress());
		user.setPassword(form.getPassword());
		user.setLimitNum(form.getAdminLimit());;

		//ユーザー登録処理
		int result=0;
		try {
			result = service.insertAdmin(user);
		} catch (DuplicateKeyException e) {
    	model.addAttribute("message", "このメールアドレスは使われています");
			return "admin/SvTtkOg7Fj";
		}

		if(result!=1) {
			model.addAttribute("message", "登録に失敗しました。");
			return "admin/SvTtkOg7Fj";
		}

		return "user/login";
	}

}
