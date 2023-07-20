package jp.ebacorp.Xiangyan.Wu.LearningSite;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

//ユーザーの入力値を保持してデータのやり取りを行います。
public class SignupForm {

//    Null、空文字、空白をエラーとする
//    入力文字数の最小値と最大値を指定
//    (min = 1, max = 50, message = "ユーザー名は1文字以上100文字以下で入力してください")
    @NotBlank
    @Size(min = 1, max = 50)
    private String username;

    @NotBlank
    @Size(min = 1, max = 20)
    private String password;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}

