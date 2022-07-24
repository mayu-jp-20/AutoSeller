package com.example.demo.domain;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * セキュリティのために追加した項目
 * @NoArgsConstructor
 * @AllArgsConstructor
 * @Builder
 * implements UserDetails
 * @Overrideがついているもの
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserModel implements UserDetails{

	/*
	 * Springで必要なフィールド
	 */
	private String userId;//メールアドレス
	private String password;
	//private Date passUpdateDate;//パスワード更新日付
	//private int loginMissTimes;//ログイン失敗回数
	//private boolean unlock;//ロック状態フラグ
	private boolean enabled;//有効・無効フラグ
	//private Date userDueDate;//ユーザー有効期限
	private Collection<?extends GrantedAuthority>authority;
	/*
	 * 独自のフィールド
	 */
	private String name;
	private String kanaName;
	private String sex;
	private Date birthday;
	private String status;
	private String strEnable;
	private int limitNum;
	private String qooId;
	private String qooPass;
	private String apiKey;
	private String sellerCertificationKey;//販売者キー
	private boolean connectQooAccount;
	private BigDecimal price1;
	private BigDecimal price2;
	private BigDecimal price3;
	private BigDecimal price4;
	private BigDecimal price5;
	private BigDecimal rate1;
	private BigDecimal rate2;
	private BigDecimal rate3;
	private BigDecimal rate4;
	private BigDecimal rate5;
	private BigDecimal rate6;
	private BigDecimal fixing1;
	private BigDecimal fixing2;
	private BigDecimal fixing3;
	private BigDecimal fixing4;
	private BigDecimal fixing5;
	private BigDecimal fixing6;
	private BigDecimal referencePrice;
	private String fixingKeywardForItem;
	private String shippingCode;
	private String allShippingCode;
	private int itemQty;
	private String itemPageHeader;
	private String itemPageFooter;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authority;
	}
	@Override
	public String getPassword() {
		return this.password;
	}
	@Override
	public String getUsername() {
		return this.userId;
	}
	/*
	 * アカウントの有効期限チェック
	 * チェック行わないため常にtrueを返す
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	/*
	 * アカウントのロックチェック
	 * チェック行わないため常にtrueを返す
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	/*
	 * パスワードの有効期限チェック
	 * チェック行わないため常にtrueを返す
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

}
