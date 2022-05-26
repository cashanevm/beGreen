package ua.knu.beGreen.service.model;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContainerModel {
    private String id;
    private String name;
    private String description;
    private Integer addedUsers;
    private UserModel user;
    private Integer averageMark;
    boolean isBeSeen;
    private MarkerModel marker;
    private String address;
    private Integer size;

    private double pointKef;
    private boolean isPoint;
    private boolean isFull;

    // private-comments List<Comment>
    // private-items List<Item>
    // private-readjustments List<Readjustment>
}
