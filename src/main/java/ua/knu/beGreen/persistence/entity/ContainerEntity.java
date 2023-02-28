package ua.knu.beGreen.persistence.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Table(name = "container")
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"marker", "user"})
@ToString(exclude = {"marker", "user"})
public class ContainerEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String name;

    private String description;

    private Integer addedUsers;

    @ManyToOne
    @JoinColumn(name="user_id")
    private UserEntity user;

    private Integer averageMark;

    boolean isBeSeen;

    private String address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "marker_id", referencedColumnName = "id")
    private MarkerEntity marker;

    @CreationTimestamp
    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @UpdateTimestamp
    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    @Column(nullable = false)
    private Integer size;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int contents;

    @Column(nullable = false, columnDefinition = "FLOAT(8) default 0.0")
    private double pointKef;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isPoint;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isFull;

    // private-comments List<Comment>
    // private-items List<Item>
    // private-readjustments List<Readjustment>
}
