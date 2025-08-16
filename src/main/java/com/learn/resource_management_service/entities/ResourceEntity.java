package com.learn.resource_management_service.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "resource_data")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResourceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, insertable = false)
    private UUID id;

    @Column(name = "account_mapping_id", nullable = false)
    private UUID accountMappingId;

    @Column(name = "resource_type", nullable = false)
    private String resourceType;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "img_url", nullable = false)
    private String imgUrl;

    @Column(name = "created_at", updatable = false, insertable = false)
    private Date createdAt;
}
