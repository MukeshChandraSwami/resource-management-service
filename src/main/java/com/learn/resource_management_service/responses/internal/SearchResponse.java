package com.learn.resource_management_service.responses.internal;

import com.learn.resource_management_service.models.SearchResult;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SearchResponse {

    private boolean success;
    private String responseMsg;
    private int responseCode;
    private List<SearchResult> searchResponses;
}
