package com.example.demo.admin.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.admin.domain.NgForm;
import com.example.demo.aop.LogAspect;
import com.example.demo.component.ApplicationUtil;
import com.example.demo.service.NgService;

//NGワードまたはNG＿ASINをCSVで一括登録
@Controller
public class InsertNGcsvController {

	@Autowired
	NgService service;

	@Autowired
	ApplicationUtil util;

	@GetMapping("/insert_NG_csv")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String getInsertNgCsv(@ModelAttribute NgForm form,Model model) {
		return "admin/insert_NG_csv";
	}

	@PostMapping("/insert_NG_csv")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String postInsertNgCsv(@ModelAttribute NgForm form,@RequestParam("upload_file")MultipartFile file,Model model)
			throws IOException,DataAccessException {

		String[]ngWordList=null;

		LogAspect.logger.info("args:{}", form+","+file.toString());

		//ファイルの種類チェック
		String fileName = file.getOriginalFilename();
		String extension = fileName.substring(fileName.lastIndexOf("."));

		if(extension.equals(".csv")) {
			ngWordList=util.readCSV(file);
		}else {
			model.addAttribute("message", "CSVファイルをアップロードしてください");
			return "admin/insert_NG_csv";
		}

		//登録処理
		List<String> insert=service.insert(ngWordList, form.getType());

		if(insert.isEmpty()) {//全件登録成功
			model.addAttribute("link", "insert_NG_csv");
			return "admin/success_for_admin";
		}

		//登録失敗したものがある場合
		model.addAttribute("message", "登録に失敗しました。【"+insert+"】");
		return "admin/insert_NG_csv";

	}

}