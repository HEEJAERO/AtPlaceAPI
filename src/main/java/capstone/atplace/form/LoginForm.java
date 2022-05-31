package capstone.atplace.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginForm {

    // 로그인 폼

    @NotNull
    private String username;

    @NotNull
    private String password;
}
