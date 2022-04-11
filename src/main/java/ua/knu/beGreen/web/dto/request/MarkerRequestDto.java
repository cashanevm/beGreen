package ua.knu.beGreen.web.dto.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarkerRequestDto {
    @NotBlank(message = "Name must be filled!")
    private String name;
    private String lat;
    private String lng;
}
