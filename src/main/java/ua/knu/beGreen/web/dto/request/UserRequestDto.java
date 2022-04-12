package ua.knu.beGreen.web.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    @NotBlank(message = "Field \"User Name\" is required")
    private String username;

    @NotBlank(message = "Field \"Password\" is required")
    private String password;

    @Email(message = "Field \"Email\" is incorrect")
    @NotBlank(message = "Field \"Email\" is required")
    private String email;

    private String role;
}
