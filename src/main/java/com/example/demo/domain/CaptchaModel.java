package com.example.demo.domain;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.example.demo.component.PropertyUtil;

import lombok.Data;

@Data
public class CaptchaModel {

	private String loginURL=PropertyUtil.getLoginQooUrl();
	private String imgLink;
	private Document loginPage;
	private Element id;
	private Element idInputArea;
	private Element pwdInputArea;
	private Element codeInputArea;


}
