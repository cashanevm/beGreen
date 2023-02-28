package ua.knu.beGreen.web.dto.request;

import ua.knu.beGreen.service.model.UserModel;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContainerRequestDto {
    private String id;
    private String name;
    @NotBlank
    private String description;
    private Integer addedUsers;
    private UserModel user;
    private Integer averageMark;
    boolean isBeSeen;
    private MarkerRequestDto marker;
    private String address;
    @NotNull
    @Min(value = 0, message = "Size should not be less than 0")
    private Integer size;

    // private-comments List<Comment>
    // private-items List<Item>
    // private-readjustments List<Readjustment>
}
