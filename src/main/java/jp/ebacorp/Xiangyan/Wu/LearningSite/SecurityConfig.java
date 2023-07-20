package jp.ebacorp.Xiangyan.Wu.LearningSite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(login -> login                                //フォーム認証の設定記述開
                        .loginPage("/login")	                         //ログインページのURLを指定します（後でこれにひもづくコントローラを設定します
                        .defaultSuccessUrl("/hello")                     //ログイン認証成功後の遷移先URLを指定します。これがないとディフォルトhomePageに遷移される(なぜ？)
//                      .usernameParameter("wu")                         //ログインページで指定したユーザ名を入力する項目を指定。
//                      .passwordParameter("123456")                     //ログインページで指定したパスワードを入力する項目を指定。
//                      .failureUrl("/login?error")                      //ログイン失敗後のリダイレクト先URL
                        .permitAll()                                     //ログイン画面は未ログインでもアクセス可能
                )

                .logout(LogoutConfigurer::permitAll
//                        .logoutSuccessUrl("/login")                     //⑤ログアウトが成功した際の遷移先
//                        .invalidateHttpSession(true)                    //⑥ログアウト時にHTTPセッションを無効にする
                )

                .authorizeHttpRequests(authz -> authz                     //URLごとの認可設定記述開始
                                .requestMatchers("/", "/home")   //"/"はログインなしでもアクセス可能
                                .permitAll()                              //"/css/**"等はログインなしでもアクセス可能
                                .requestMatchers("/signup")      //登録画面に「登録」ボタンを押するとログイン画面に遷移する
                                .permitAll()                               //かつログイン画面で「ユーザー登録」リンクを押すとユーザー登録に遷移する
//                              .requestMatchers("/general")
//                              .hasRole("GENERAL")                         //"/general"はROLE_GENERALのみアクセス可能
//                        `     .requestMatchers("/admin")
//                              .hasRole("ADMIN")                           //"/admin"はROLE_GENERALのみアクセス可能
                                .anyRequest().authenticated()               //他のURLはログイン後のみアクセス可能
                )

                //AuthenticationManagerBuilder
                .authenticationProvider(authenticationProvider());;

        System.out.println(passwordEncoder().encode("password"));
        return http.build();
    }

    //こちらのコードがあるとパスワードにpasswordを入力するとログインできない
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
//    指定されたユーザー情報でログインできる
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    //DaoAuthenticationProvider クラスの設定部分です。
    //このように書くことで、認証に使用するユーザー情報を userDetailsService を介してデータベースから受け取ることになります。
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
}
