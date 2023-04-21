package shop.mtcoding.metablog.model.user;

import lombok.*;
import shop.mtcoding.metablog.dto.user.UserRequest;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "user_tb")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, length = 20)
    private String username;
    @Column(length = 60)
    private String password;
    @Column(length = 50)
    private String email;
    private String role; // USER(고객)
    private String profile;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void changeProfile(String profile){
        this.profile = profile;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void update(String password, String email) {
        this.password = password;
        this.email = email;
    }
}
