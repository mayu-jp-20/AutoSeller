package com.example.demo.api;

import org.springframework.stereotype.Service;

import com.example.demo.aop.LogAspect;
import com.example.demo.component.PropertyUtil;
import com.twocaptcha.TwoCaptcha;
import com.twocaptcha.captcha.Normal;

@Service
public class CaptchaService {

	//reCaptchaの突破
	public String captchaSolver(String imgLink){

		LogAspect.logger.info("CONNECT 2Captcha method:[com.example.demo.api.CaptchaService.captchaSolver(String)] ,imgLink={}", imgLink);

		String apiKey=PropertyUtil.getCaptchaKey();

		TwoCaptcha solver=new TwoCaptcha(apiKey);
		Normal captcha = new Normal(imgLink);

		String code=new String();
		try {
			solver.solve(captcha);
			code = captcha.getCode();
		} catch (Exception e) {
			LogAspect.logger.error("Error occured:"+e.getMessage());
			return null;
		}

		//本番環境では出力しない
		LogAspect.logger.info("captchaSolver Result code:{}"
				,code);

		LogAspect.logger.info("DISCONNECT 2Catcha method:[com.example.demo.api.CaptchaService.captchaSolver(String)],response:{}",captcha.toString());

		return code;
	}

}
