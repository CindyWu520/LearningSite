package jp.ebacorp.Xiangyan.Wu.LearningSite;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.io.Serial;
import java.util.Collection;


//①URLからユーザー名とパスワード（平文）をUserDetailsServiceに送信
//②DBでユーザー名を検索
//③DBから（ユーザ名、ハッシュ化されたパスワード）をUserDetailsServiceに渡す
//④UserDetailsServiceから（ユーザ名、ハッシュ化されたパスワード）をUserDetailsに渡す  >ここです。
//⑤URLとUserDetailsのハッシュ化したパスワードを比較

public class UserDetailsImpl implements UserDetails {
    private String username;
    private String password;
    private Collection<GrantedAuthority> authorities;

    public UserDetailsImpl(String username, String password, Collection<GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return username;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }

//他のサイドを参考して書いてます、データテーブルloginUserのクラスがあります。
//    private final LoginUser loginUser;
//    private final Collection<? extends GrantedAuthority> authorities;
//
//    public UserDetailsImpl(LoginUser loginUser) {
//        this.loginUser = loginUser;
//        this.authorities = loginUser.roleList()
//                .stream()
//                .map(role -> new SimpleGrantedAuthority(role))
//                .toList();
//    }
//
//    public LoginUser getLoginUser(){return loginUser;}
//
//    @Override //ロール// のコレクションを返す
//    //<? extends GrantedAuthority> は GrantedAuthority 型のすべてのサブクラスを表していることになります。
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return authorities;
//    }
//
//    @Override //ハッシュ化済みのパスワードを返す
//    public String getPassword() {
//        return loginUser.password();
//    }
//
//    @Override //ログインで利用するユーザー名を返す
//    public String getUsername() {
//        return loginUser.email();
//    }
//
//    @Override //ユーザーが期限切れてなければtrueを返す
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override //ユーザーがロックされていなければtrueを返す
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override //ユーザーのパスワードが期限切れていなければtrueを返す
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override //ユーザーが有効であればtrueを返す
//    public boolean isEnabled() {
//        return true;
//    }
}

