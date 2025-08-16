package com.learn.resource_management_service.responses;


import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@SuperBuilder
public class Response {

    private boolean success;
    private String responseMsg;
    private int responseCode;
    private UUID id;
}
