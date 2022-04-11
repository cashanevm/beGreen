package ua.knu.beGreen.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarkerResponseDto {
    private String id;
    private String name;
    private String lat;
    private String lng;
}
