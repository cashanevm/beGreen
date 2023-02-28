package ua.knu.beGreen.web.dto.request;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class СoefficientRequestDto {
    @NotNull
    @DecimalMin(value = "0.0", message = "Value should not be less than 0")
    @DecimalMax(value = "1.0", message = "Value should be less than 1")
    private Double coefficient;
}
