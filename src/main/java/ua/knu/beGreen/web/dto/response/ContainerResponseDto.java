package ua.knu.beGreen.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContainerResponseDto {
    private String id;
    private String name;
    private String description;
    private Integer addedUsers;
    private Integer averageMark;
    boolean isBeSeen;
    private String address;
    private String ownerName;

    private double pointKef;
    private boolean isPoint;
    private boolean isFull;

    // private-comments List<Comment>
    // private-items List<Item>
    // private-readjustments List<Readjustment>
}
