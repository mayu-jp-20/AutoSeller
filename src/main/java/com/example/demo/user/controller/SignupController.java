package com.example.demo.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.domain.UserModel;
import com.example.demo.service.UserService;
import com.example.demo.user.domain.SignupForm;

@Controller
public class SignupController {

	@Autowired
	UserService service;


	@GetMapping("/signup")
	public String getInsertUser(@ModelAttribute SignupForm form,Model model) {
		return "user/signup";
	}

	@PostMapping("/signup")
	public String postInsertUser(@ModelAttribute@Validated SignupForm form,BindingResult bindingResult,
			Model model) {

		if(bindingResult.hasErrors()) {
			return "user/signup";
		}

		UserModel user=new UserModel();

		user.setName(form.getName());
		user.setKanaName(form.getKanaName());
		user.setSex(form.getSex());
		user.setBirthday(form.getBirthday());
		user.setUserId(form.getMailAddress());
		user.setPassword(form.getPassword());

		//ユーザー登録処理
		int result=0;
		try {
			result = service.insert(user);
		} catch (DuplicateKeyException e) {
    	model.addAttribute("message", "このメールアドレスは使われています");
			return "user/signup";
		}

		if(result!=1) {
			model.addAttribute("message", "登録に失敗しました。");
			return "user/signup";
		}

		return "user/login";
	}

}
