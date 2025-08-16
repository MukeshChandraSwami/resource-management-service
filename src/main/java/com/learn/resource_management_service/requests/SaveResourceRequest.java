package com.learn.resource_management_service.requests;

import com.learn.resource_management_service.constants.ResourceTypes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveResourceRequest {

    private ResourceTypes resourceType;
    private String title;
}
