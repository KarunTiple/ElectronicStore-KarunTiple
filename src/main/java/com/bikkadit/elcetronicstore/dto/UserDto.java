package com.bikkadit.elcetronicstore.dto;

import com.bikkadit.elcetronicstore.config.AppConstants;
import com.bikkadit.elcetronicstore.utility.ImageNameValid;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDto extends CustomFieldsDto{

    private String userId;

    @NotEmpty
    private String name;

    @NotEmpty
    @Email(message = AppConstants.INVALIDEMAIL)
    private String email;

    @NotEmpty
    private String gender;

    @Size(max = 15, min = 6)
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9]*",
            message = AppConstants.PASSWORDPATTERN)
    private String password;

    @ImageNameValid(message = "Image name not valid ..")
    private String imageName;
}
