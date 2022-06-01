package ua.knu.beGreen.service.model;

import java.time.LocalDateTime;

import javax.persistence.Column;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    private Integer contents;

    private double pointKef;
    private boolean isPoint;
    private boolean isFull;

    private String createdOn;
    private String updatedOn;

    // private-comments List<Comment>
    // private-items List<Item>
    // private-readjustments List<Readjustment>
}
