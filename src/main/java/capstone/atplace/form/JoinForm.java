package capstone.atplace.form;


import lombok.Data;

@Data
public class JoinForm {
    //회원가입 폼

    private String username;
    private String password;

    private String name;

    private String address;
    private String phoneNumber;
}
