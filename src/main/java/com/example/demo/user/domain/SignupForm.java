package com.example.demo.user.domain;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.format.annotation.DateTimeFormat;

@lombok.Data
public class SignupForm {

	@NotBlank
	private String name;

	@NotBlank
	@Pattern(regexp="^[ァ-ヶー]*$")
	private String kanaName;

	@NotBlank
	private String sex;

	@NotNull
	@DateTimeFormat(pattern="yyyy/MM/dd")
	private Date birthday;

	@NotBlank
	@Email
	private String mailAddress;

	@NotBlank
	private String password;

	//管理者の登録するときに使う秘密のパスワード
	@NotBlank
	private String secretKey;

	@PositiveOrZero
	private int adminLimit;//管理者用の出品制限

}
