package com.learn.resource_management_service.models;

import com.learn.resource_management_service.constants.ResourceTypes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Resource {

    private UUID id;
    private ResourceTypes resourceType;
    private String title;
    private String imgUrl;
}
