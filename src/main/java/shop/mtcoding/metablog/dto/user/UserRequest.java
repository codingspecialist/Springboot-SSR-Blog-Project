package shop.mtcoding.metablog.dto.user;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.metablog.model.user.User;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRequest {
    @Getter
    @Setter
    public static class JoinInDTO {

        @Size(min = 3, max = 20, message = "3~20 자리수를 지켜주세요")
        @NotEmpty
        private String username;

        @Size(min = 4, max = 20, message = "4~20 자리수를 지켜주세요")
        @NotEmpty
        private String password;

        @Size(min = 10, max = 50, message = "10~50 자리수를 지켜주세요")
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식이 아닙니다")
        @NotEmpty
        private String email;

        public User toEntity(){
            return User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .role("USER")
                    .status(true)
                    .profile("person.png")
                    .build();
        }
    }
}
