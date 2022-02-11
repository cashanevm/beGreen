package ua.knu.be_green.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarkerModel {
    private String id;
    private String name;
    private String lat;
    private String lng;
}
