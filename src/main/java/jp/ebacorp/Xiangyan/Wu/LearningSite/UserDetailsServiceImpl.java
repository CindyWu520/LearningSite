package jp.ebacorp.Xiangyan.Wu.LearningSite;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//ログイン認証の概略図
//①URLからユーザー名とパスワード（平文）をUserDetailsServiceに送信
//②DBでユーザー名を検索
//③DBから（ユーザ名、ハッシュ化されたパスワード）をUserDetailsServiceに渡す
//④UserDetailsServiceから（ユーザ名、ハッシュ化されたパスワード）をUserDetailsに渡す  >ここです。
//⑤URLとUserDetailsのハッシュ化したパスワードを比較

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

//    JdbcTemplate を使ってSQL文でレコードを抽出して、取得したユーザー情報を UserDetails（UserDetailsImpl）に詰め込んでいます。
    @Autowired
    JdbcTemplate jdbcTemplate;
    //＠Autowired アノテーションを使用して、SecurityConfig クラスで Bean 定義した PasswordEncode を取得します。
    PasswordEncoder passwordEncoder;

    @Override
    //Webページからusernameを入力、usernameを通じてデータベースからusername, password, authoritiesを取得する。
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {

            //ユーザー名より、SQL文でレコードを抽出して、mapに詰め込んでいます
            String sql = "SELECT * FROM user WHERE name = ?";
            Map<String, Object> map = jdbcTemplate.queryForMap(sql, username);

            //mapからpasswordを取得します
            String password = (String)map.get("password");

            //権限情報は、GrantedAuthority にセットします。
            //Collection<GrantedAuthority> は、GrantedAuthority を要素に持つコレクションです。
            //Collection はインターフェイスなので、インスタンスを生成するときは、ArrayList クラスなどを使用することになります。
            Collection<GrantedAuthority> authorities = new ArrayList<>();

            //mapからauthorityを取得します
            //SimpleGrantedAuthority クラスは、GrantedAuthority インターフェイスの実装クラスであり、
            // 引数に権限情報の文字列を指定することで、GrantedAuthority のインスタンスを生成しています。
            //そしてそのインスタンスを、authorities に追加しています。
            authorities.add(new SimpleGrantedAuthority((String)map.get("authority")));

            return new UserDetailsImpl(username, password, authorities);
        } catch (Exception e) {
            //例外処理
            //UsernameNotFoundException は BadCredentialsException という例外に変換してからエラー処理が行われるようになっています
            throw new UsernameNotFoundException("user not found.", e);
        }
    }

    @Transactional
    //ユーザー情報をデータベースに登録する register メソッドを追加します。
    public void register(String username, String password, String authority) {
        String sql = "INSERT INTO user(name, password, authority) VALUES(?, ?, ?)";

        //JdbcTemplate の update メソッドで、データベースにユーザー情報を登録します。
        //パスワードは、PasswordEncoder（BCrypt）でハッシュ化しておきます。
        jdbcTemplate.update(sql, username, passwordEncoder.encode(password), authority);
    }

    //データベースに同一ユーザー名が既に登録されているかを確認する
    public boolean isExistUser(String username) {
        String sql = "SELECT COUNT(*) FROM user WHERE name = ?";
        //JdbcTemplate の queryForObject メソッドを使用してデータベース内の検索結果を取得します。
        int count = jdbcTemplate.queryForObject(sql, Integer.class, new Object[] { username });
        if (count == 0) {
            //存在しなければ　false を返します
            return false;
        }
        //同一ユーザー名が存在すれば true
        return true;
    }
}


