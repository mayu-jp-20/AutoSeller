package com.example.demo.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


//処理件数が一個じゃないものは処理件数も出力す
//Qoo10はレスポンスを呼び出し元で出力

@Aspect
@Component
public class LogAspect {
	public static final Logger logger=LoggerFactory.getLogger(LogAspect.class);

	/*
	 * 全コントローラーのGETメソッド開始ログ
	 * START メソッドの絶対名/引数/
	 */
	@Before("execution(* com.example.demo.*..*.*Controller.get*(..))")
	public void getControllerStardLog(JoinPoint jp) {
		Signature method=jp.getSignature();//メソッドの絶対名
		logger.info("method:{} ",method);
	}

	/*
	 * 全コントローラーのPOSTメソッド開始ログ
	 * START メソッドの絶対名/引数
	 */
	@Before("execution(* com.example.demo.*..*.*Controller.post*(..))")
	public void postControllerStardLog(JoinPoint jp) {
		Signature method=jp.getSignature();//メソッドの絶対名
		String args=Arrays.toString(jp.getArgs());//引数
		logger.info("method:{}, args:{} ",method,args);
	}

	/*
	 * 全コントローラーの終了ログはいらない
	 */


	/*
	 * スケジュールタスク
	 */
	@Before("@annotation(org.springframework.scheduling.annotation.Scheduled)")
	public void scheduledTaskStartLog(JoinPoint jp) {
		logger.info("SCHEDULED TASK START method:{}", jp.getSignature());
	}

	@After("@annotation(org.springframework.scheduling.annotation.Scheduled)")
	public void scheduledTaskEndlog(JoinPoint jp) {
		logger.info("SCHEDULED TASK END method:{}", jp.getSignature());
	}
}
