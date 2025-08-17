package com.learn.resource_management_service.models;

import com.learn.resource_management_service.constants.ResourceTypes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class SearchResult {

    private UUID id;
    private UUID resourceId;
    private String content;
    private ResourceTypes resourceType;
}
