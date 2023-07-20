package jp.ebacorp.Xiangyan.Wu.LearningSite;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class LearningSiteController {

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    //コントローラーは、http://localhost:8080/ にアクセスしたときに、トップページ（home.html）を表示させる記述をしておきます。
    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping("/login")
    //コントローラーには、ログイン画面を表示するための login メソッドを追加します。
    ///login にアクセスすれば、login.html を表示するという単純なものです。
    //こちらをコメントアウトしても、http://localhost:8080/login　にアクセス可能となり、おそらくSecurityConfigに制御があるだと思う。
    public String login(){
        return "login";
    }

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/signup")
    //「ユーザー登録」ページを表示するだけのものです。
    public String newSignup(SignupForm signupForm) {
        return "signup";
    }

    @PostMapping("/signup")
    //signup メソッドでは、UserDetailsServiceImpl において作成した register メソッドを使用して、ユーザー情報をデータベースに格納します。
    //「SignupForm signupForm」にアノテーション ＠Validated を加えて、バリデーションチェックを有効にしています。
    //BindingResult result を引数として追加しています。ここに、エラー情報が格納されます。
//    public String signup(SignupForm signupForm, Model model) {
    public String signup(@Validated SignupForm signupForm, BindingResult result, Model model, HttpServletRequest request) {
        //BindingResult の hasErrors メソッドでエラーの有無をチェックして、エラーの場合には /signup のページを表示するようにしています。
        if (result.hasErrors()) {
            return "signup";
        }

        // isExistUser を使用して、エラーメッセージを返す処理
        if (userDetailsServiceImpl.isExistUser(signupForm.getUsername())) {
            model.addAttribute("signupError", "ユーザー名 " + signupForm.getUsername() + "は既に登録されています");
            return "signup";
        }
        try {
            userDetailsServiceImpl.register(signupForm.getUsername(), signupForm.getPassword(), "ROLE_USER");
        } catch (DataAccessException e) {
            model.addAttribute("signupError", "ユーザー登録に失敗しました");
            return "signup";
        }

        //セキュリティコンテキストの取得
        SecurityContext context = SecurityContextHolder.getContext();
        //セッション中のユーザー情報の取得
        Authentication authentication = context.getAuthentication();
        // 匿名ユーザー（anonymousUser,ログインしていないユーザー）であることの判定
        if (authentication instanceof AnonymousAuthenticationToken == false) {
            //ログアウト処理を実行する
            SecurityContextHolder.clearContext();
        }
        try {
            //(HttpServletRequest.login)新たに登録されたユーザー名とパスワードでログイン処理を行っています。
            request.login(signupForm.getUsername(), signupForm.getPassword());
        } catch (ServletException e) {
            e.printStackTrace();
        }

        //リダイレクトとは、サイト訪問ユーザーを別URLに自動的に誘導や転送するための仕組みのことです。
        //"/"つまり"homePage"にリダイレクトする。
        return "redirect:/";
    }

}
