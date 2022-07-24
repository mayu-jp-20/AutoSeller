package com.example.demo.user.controller.userAop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.example.demo.component.ApplicationUtil;
import com.example.demo.domain.UserModel;
import com.example.demo.service.UserService;

@Controller
public class BaseController {

	@Autowired
	UserService userService;

	@Autowired
	ApplicationUtil app;

	public void setStatus(UserModel user,Model model) {

		String role=user.getStatus();
		String status=app.getStatus(role);

        model.addAttribute("status", status);
	}
}
