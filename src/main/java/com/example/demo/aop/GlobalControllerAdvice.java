package com.example.demo.aop;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.PatternSyntaxException;

import javax.xml.parsers.ParserConfigurationException;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientException;
import org.xml.sax.SAXException;

/*
 * エラー時
 * エラーログを出力
 */

@Aspect
@ControllerAdvice
@Component
public class GlobalControllerAdvice{

	/*public String getRole(JoinPoint jp) {
		//メソッドの引数名
		String[] argNames=((CodeSignature) jp.getSignature()).getParameterNames();
		//メソッドの引数の値
		Object[] argValues=jp.getArgs();

		for(int i = 0; i < argNames.length; i++) {

			if(argNames[i]=="roll") {
				//テスト用
				System.out.println("role:"+argValues[i]);
				return argValues[i].toString();
			}
		}
		System.out.println("なし");
		return null;
	}*/

	@ExceptionHandler(IncorrectResultSizeDataAccessException.class)
	public String incorrectResultSizeDataAccessExceptionHandler(
			IncorrectResultSizeDataAccessException e,JoinPoint jp,Model model) {

		LogAspect.logger.error("",e);

		/*String roll=getRole(jp);
		if(roll=="admin") {
			return "error_admin";
		}*/
		 model.addAttribute("status", null);

		return "error";
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public String duplicationKeyExceptionHandler(DuplicateKeyException e,JoinPoint jp,Model model) {

		LogAspect.logger.error("",e);

		/*String roll=getRole(jp);
		if(roll=="admin") {
			return "error_admin";
		}*/
		model.addAttribute("status", null);

		return "error";
	}

	@ExceptionHandler(UnsupportedOperationException.class)
	public String unsupportedOperationExceptionHandler(
			UnsupportedOperationException e,JoinPoint jp,Model model) {

		LogAspect.logger.error("",e);

		/*String roll=getRole(jp);
		if(roll=="admin") {
			return "error_admin";
		}*/
		model.addAttribute("status", null);

		return "error";
	}

	@ExceptionHandler(ClassCastException.class)
	public String classCastExceptionHandler(ClassCastException e,JoinPoint jp,Model model) {

		LogAspect.logger.error("",e);

		/*String roll=getRole(jp);
		if(roll=="admin") {
			return "error_admin";
		}*/
		model.addAttribute("status", null);

		return "error";
	}

	@ExceptionHandler(NullPointerException.class)
	public String nullPointerExceptionHandler(NullPointerException e,JoinPoint jp,Model model) {

		LogAspect.logger.error("",e);

		/*String roll=getRole(jp);
		if(roll=="admin") {
			return "error_admin";
		}*/
		model.addAttribute("status", null);

		return "error";
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public String illegalArgumentExceptionHandler(IllegalArgumentException e,JoinPoint jp,Model model) {

		LogAspect.logger.error("",e);

		/*String roll=getRole(jp);
		if(roll=="admin") {
			return "error_admin";
		}*/
		model.addAttribute("status", null);

		return "error";
	}

	@ExceptionHandler(IndexOutOfBoundsException.class)
	public String indexOutOfBoundsExceptionHandler(IndexOutOfBoundsException e,JoinPoint jp,Model model) {

		LogAspect.logger.error("",e);

		/*String roll=getRole(jp);
		if(roll=="admin") {
			return "error_admin";
		}*/
		model.addAttribute("status", null);
		return "error";
	}

	@ExceptionHandler(PatternSyntaxException.class)
	public String patternSyntaxExceptionHandler(PatternSyntaxException e,JoinPoint jp,Model model) {

		LogAspect.logger.error("",e);
		/*String roll=getRole(jp);
		if(roll=="admin") {
			return "error_admin";
		}*/
		model.addAttribute("status", null);
		return "error";
	}

	@ExceptionHandler(IOException.class)
	public String iOExceptionHandler(IOException e,JoinPoint jp,Model model) {

		LogAspect.logger.error("",e);
		/*String roll=getRole(jp);
		if(roll=="admin") {
			return "error_admin";
		}*/
		model.addAttribute("status", null);
		return "error";
	}

	@ExceptionHandler(NumberFormatException.class)
	public String numberFormatExceptionHandler(NumberFormatException e,JoinPoint jp,Model model) {

		LogAspect.logger.error("",e);
		/*String roll=getRole(jp);
		if(roll=="admin") {
			return "error_admin";
		}*/
		model.addAttribute("status", null);
		return "error";
	}

	@ExceptionHandler(DataAccessException.class)
	public String dataAccessExceptionHandler(DataAccessException e,JoinPoint jp,Model model) {

		LogAspect.logger.error("",e);
		/*String roll=getRole(jp);
		if(roll=="admin") {
			return "error_admin";
		}*/
		model.addAttribute("status", null);

		return "error";
	}

	@ExceptionHandler(ParserConfigurationException.class)
	public String parserConfigurationExceptionHandler(ParserConfigurationException e,JoinPoint jp,Model model) {

		LogAspect.logger.error("",e);
		/*String roll=getRole(jp);
		if(roll=="admin") {
			return "error_admin";
		}*/
		model.addAttribute("status", null);
		return "error";
	}

	@ExceptionHandler(RestClientException.class)
	public String restClientExceptionHandler(RestClientException e,JoinPoint jp,Model model) {

		LogAspect.logger.error("",e);

		/*String roll=getRole(jp);
		if(roll=="admin") {
			return "error_admin";
		}*/
		model.addAttribute("status", null);

		return "error";
	}

	@ExceptionHandler(SAXException.class)
	public String sAXExceptionHandler(SAXException e,JoinPoint jp,Model model) {

		LogAspect.logger.error("",e);
		/*String roll=getRole(jp);
		if(roll=="admin") {
			return "error_admin";
		}*/
		model.addAttribute("status", null);
		return "error";
	}

	@ExceptionHandler(SQLException.class)
	public String sqlExceptionHandler(SQLException e,JoinPoint jp,Model model) {

		LogAspect.logger.error("",e);

		/*String roll=getRole(jp);
		if(roll=="admin") {
			return "error_admin";
		}*/
		model.addAttribute("status", null);
		return "error";
	}

	@ExceptionHandler(Exception.class)
	public String exceptionHandler(Exception e,Model model) {
		LogAspect.logger.error("",e);
		return "error";
	}

}
