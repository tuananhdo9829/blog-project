package com.tuananhdo.entity;

import com.tuananhdo.util.AuthenticationType;
import com.tuananhdo.util.DeleteStatus;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    private boolean enabled;
    @Column(length = 64)
    private String photos;
    @Enumerated(EnumType.STRING)
    @Column(name = "delete_status")
    private DeleteStatus deleteStatus;
    @CreationTimestamp
    private LocalDateTime deleteAt;
    @Enumerated(EnumType.STRING)
    @Column(name = "authentication_type", length = 10)
    private AuthenticationType authenticationType;
    @Column(name = "verification_code", length = 64, updatable = false)
    private String verificationCode;
    @Column(name = "reset_password_token", length = 30)
    private String resetPasswordToken;
    @Column(name = "reset_password_token_expiration_time")
    private LocalDateTime resetPasswordTokenExpirationTime;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
            , inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostLike> postLikes = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentLike> commentLikes = new ArrayList<>();

}
