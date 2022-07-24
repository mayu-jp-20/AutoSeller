package com.example.demo.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.admin.domain.NoticeForm;
import com.example.demo.aop.LogAspect;
import com.example.demo.domain.NoticeModel;
import com.example.demo.service.NoticeService;

@Controller
public class InsertNoticeController {

	@Autowired
	NoticeService service;

	@GetMapping("/post_notification")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String getInputNotice(@ModelAttribute NoticeForm form,Model model) {
		return "admin/post_notification";
	}

	@PostMapping("/post_notification")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String postInputNotice(@ModelAttribute@Validated NoticeForm form,BindingResult br,Model model) throws DataAccessException{

		if(br.hasErrors()) {
			return "admin/post_notification";
		}
		LogAspect.logger.info("args:{}", form);
		NoticeModel notice=new NoticeModel();
		notice.setTitle(form.getTitle());
		notice.setContents(form.getContents());

		int result=service.insert(notice);

		if(result==1) {//登録成功
			model.addAttribute("link","post_notification");
			return "admin/success_for_admin";
		}

		//登録失敗
		model.addAttribute("message", "登録に失敗しました。");
		return "admin/post_notification";
	}
}
