package shop.mtcoding.metablog.model.log.login;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Setter // DTO 만들면 삭제해야됨
@Getter
@Table(name = "login_log_tb")
@Entity
public class LoginLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String userAgent;
    private String clientIP;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }


    @Builder
    public LoginLog(Long id, Long userId, String userAgent, String clientIP, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.userAgent = userAgent;
        this.clientIP = clientIP;
        this.createdAt = createdAt;
    }
}
