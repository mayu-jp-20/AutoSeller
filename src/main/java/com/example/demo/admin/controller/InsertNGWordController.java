package com.example.demo.admin.controller;

import java.util.List;

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

import com.example.demo.admin.domain.NgForm;
import com.example.demo.aop.LogAspect;
import com.example.demo.service.NgService;

//NGワードまたはASINの個別登録
@Controller
public class InsertNGWordController {

	@Autowired
	NgService service;

	@GetMapping("/insert_NG_word")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String getInsertNgWord(@ModelAttribute NgForm form,Model model) {
		return "admin/insert_NG_word";
	}

	@PostMapping("/insert_NG_word")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String postInsertNgWord(@ModelAttribute@Validated NgForm form,BindingResult br,Model model) throws DataAccessException{

		if(br.hasErrors()) {
			model.addAttribute("message", "どちらの項目も必須入力です");
			return "admin/insert_NG_word";
		}

		LogAspect.logger.info("args:{}", form);

		//カンマで区切ってリストにする
		String[] ngList=form.getNgThings().split(",");

		//登録処理
		List<String> insert=service.insert(ngList, form.getType());

		if(insert.isEmpty()) {//全件登録成功
			model.addAttribute("link", "insert_NG_word");
			return "admin/success_for_admin";
		}

		//登録失敗したものがある場合
		model.addAttribute("message", "登録に失敗しました。【"+insert+"】");

		return "admin/insert_NG_word";
	}

}
