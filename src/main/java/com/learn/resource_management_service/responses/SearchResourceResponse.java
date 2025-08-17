package com.learn.resource_management_service.responses;

import com.learn.resource_management_service.models.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class SearchResourceResponse extends Response {

    private List<Resource> resources;
}
