package com.example.demo.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.aop.LogAspect;
import com.example.demo.domain.UserModel;
import com.example.demo.service.UserService;
import com.example.demo.user.controller.userAop.BaseController;
import com.example.demo.user.domain.PriceForm;

@Controller
public class ReferencePriceController extends BaseController{

	@Autowired
	UserService userService;

	@GetMapping("/reference_price")
	public String getReferencePrice(@ModelAttribute PriceForm form,@AuthenticationPrincipal UserModel user,Model model) {

		//データバインドでPriceFormを使うため、PriceFormで表示させる
		form.setReferencePrice(user.getReferencePrice());

		model.addAttribute("priceForm", form);
		setStatus(user,model);

		return "user/reference_price";
	}

	@PostMapping("/reference_price")
	public String postReferencePrice(@ModelAttribute@Validated PriceForm priceForm,BindingResult br,@AuthenticationPrincipal UserModel user,Model model) {

		if(br.hasErrors())	{
			model.addAttribute("message", "0より大きい数字を入力してください");
			return getReferencePrice(priceForm,user,model);
		}

		LogAspect.logger.info("args:{}", priceForm);

		int result=userService.updateReferencePrice(user);

		if(result!=1) {
			model.addAttribute("message", "更新に失敗しました。");
		}

		model.addAttribute("link", "reference_price");
		setStatus(user,model);

		return "user/success_for_user";
	}

}
