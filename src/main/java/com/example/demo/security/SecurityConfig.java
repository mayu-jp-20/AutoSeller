package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;



@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	@Qualifier("LoginUserService")
	private UserDetailsService userDetailsService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/*
	 * 静的リソースへのアクセスにはセキュリティを適用しない
	 */
	@Override
	public void configure(WebSecurity web)throws Exception{
		web.ignoring().antMatchers("/webjars/**", "/css/**");
	}


	@Override
	protected void configure(HttpSecurity http)throws Exception{
		/*
		 * ログイン不要ページの設定
		 */
		http
		   .authorizeRequests()
		     .antMatchers("/webjars/**").permitAll()
		     .antMatchers("/css/**").permitAll()
		     .antMatchers("/img/**").permitAll()
		     .antMatchers("/login").permitAll()
		     .antMatchers("/signup").permitAll()
		     .antMatchers("//SvTtkOg7Fj").permitAll()
		     .antMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN")//管理者のみがアクセスできるURL
		     .anyRequest().authenticated();
		/*
		 * レスポンスヘッダーの設定
		 * 自分自身（Spring）のサーバー上のスクリプトと画像読み込みのみを許可する設定
		 */
		http
		   .headers()
		   .contentSecurityPolicy("default-src'self'; img-src*");
		/*
		 * Remember Me設定
		 */
		http.rememberMe()
		    .key("YaybuZpwSLQMFb9")//トークン識別のキー(ユニークかつ秘密の値をランダムで生成した）
		    .rememberMeParameter("remember-me")//checkBoxのname属性
		    .rememberMeCookieName("remember-me")//Cookie名
		    .tokenValiditySeconds(86400)//有効期限（秒数指定）/86400は一日
		    .useSecureCookie(true);//HTTPS接続のみ使用可能
		/*
		 * HTTPSにリダイレクト
		 */
		/*http
		   .requiresChannel()
		   .antMatchers("/login*")//"/login"と、ログイン後の画面に設定を適用
		   .requiresSecure();//HTTPでアクセスすると、HTTPSにリダイレクトする


		/*
		 * ログイン処理
		 */
		http
		   .formLogin()
		   .loginProcessingUrl("/login")
		   .loginPage("/login")
		   .failureUrl("/login")
		   .usernameParameter("mailAddress")
		   .passwordParameter("password")
		   .defaultSuccessUrl("/home", true);
		/*
		 * ログアウト処理
		 */
		http
		   .logout()
		     .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		   //.logoutUrl("/logout")
		   .logoutSuccessUrl("/login");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth)throws Exception{
		/*
		 * ログイン処理時のユーザー情報をDBから取得する
		 */
		auth.userDetailsService(userDetailsService)
	      .passwordEncoder(passwordEncoder());
	}


}
