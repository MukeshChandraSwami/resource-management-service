package com.learn.resource_management_service.responses;


import com.learn.resource_management_service.constants.ResourceTypes;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class ResourceResponse extends Response {

    private ResourceTypes resourceType;
    private String title;
    private String imgUrl;
}
