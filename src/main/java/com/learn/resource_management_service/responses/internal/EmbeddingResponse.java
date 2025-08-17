package com.learn.resource_management_service.responses.internal;


import com.learn.resource_management_service.constants.ResourceTypes;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class EmbeddingResponse {

    private boolean success;
    private String responseMsg;
    private int responseCode;
    private UUID id;
    private UUID resourceId;
    private String content;
    private ResourceTypes resourceType;
}
