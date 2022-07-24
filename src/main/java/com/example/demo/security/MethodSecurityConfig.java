package com.example.demo.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

/*
 * @PreAuthorizeを有効化
 * @PreAuthorizeが動作するために用意しなければいけないクラスの条件・・
 *   1.@Configurationアノテーションをつける
 *   2.@EnableGlobalMethodSecurityアノテーションをつける
 *   3.GlobalMethodSecurityConfigurationを継承する
 * @EnableGlobalMethodSecurityの属性にtrueを設定することで、メソッド単位で権限設定するアノテーションが有効になる
 *   prePostEnabled
 *     trueをセットすると、@PreAuthorizeアノテーションを使えるようになる
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration{

}

