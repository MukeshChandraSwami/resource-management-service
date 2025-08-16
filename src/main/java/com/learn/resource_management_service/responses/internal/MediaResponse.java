package com.learn.resource_management_service.responses.internal;


import com.learn.resource_management_service.models.ImageAnalysis;
import com.learn.resource_management_service.responses.Response;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class MediaResponse extends Response {

    private ImageAnalysis imageAnalysis;
}
