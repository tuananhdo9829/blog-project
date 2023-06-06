package com.tuananhdo.dto;

import com.tuananhdo.entity.Role;
import com.tuananhdo.util.AuthenticationType;
import com.tuananhdo.util.DeleteStatus;
import lombok.*;

import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    @NotBlank(message = "{user.name.not.blank}")
    private String name;
    @NotBlank(message = "{user.email.not.blank}")
    @Email(message = "{user.email}")
    private String email;
    @NotBlank(message = "{user.password.not.blank}")
    @Size(min = 8,max = 20,message = "{user.password.size}")
    private String password;
    private AuthenticationType authenticationType;
    private boolean enabled;
    private String photos;
    private DeleteStatus deleteStatus;
    private LocalDateTime deletedAt;
    private String resetPasswordToken;
    private String verificationCode;
    private LocalDateTime resetPasswordTokenExpirationTime;
    Set<Role> roles;

    @Transient
    public String getUserPhotoPath(){
        if (id == null || photos == null || photos.isEmpty()){
            return "/common/assets/images/products/s1.jpg";
        }
        return "/user-photos/"+this.id + "/"+ this.photos;
    }

}
