package ua.knu.beGreen.web.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KgRequestDto {
    @NotNull
    @Min(value = 0, message = "Value should not be less than 0")
    private Integer kg;
}
