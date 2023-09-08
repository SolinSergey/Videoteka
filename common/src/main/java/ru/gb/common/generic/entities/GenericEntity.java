package ru.gb.common.generic.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class GenericEntity {
    @Id
    @Column(name="id",nullable = false)
//    @SequenceGenerator(name="pk_sequence",sequenceName="entity_id_seq", initialValue = 100, allocationSize=1)
//    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="created_by")
    private String createdBy;

    @Column(name="created_when")
    @CreationTimestamp
    private LocalDateTime createdWhen;

    @Column(name="update_by")
    private String updateBy;

    @UpdateTimestamp
    @Column(name="update_when")
    private LocalDateTime updateWhen;

    @Column(name="is_deleted")
    private boolean isDeleted;

    @Column(name="deleted_by")
    private String deletedBy;

    @Column(name="deleted_when")
    private LocalDateTime deletedWhen;
}
