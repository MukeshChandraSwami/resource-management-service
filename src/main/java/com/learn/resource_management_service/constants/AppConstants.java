package com.learn.resource_management_service.constants;

public class AppConstants {

    public static class RoutingConstants {

        public static final String STATUS = "/status";
        public static final String ACT = "/acct";
        public static final String ACT_ID = "/{acct_id}";
        public static final String SEARCH = "/search";
    }

    public static class Profiles {
        public static final String PG = "pgopenai";
    }

    public static class VectorStoreConstants {
        public static final double SIMILARITY_THRESHOLD = 0.80;

        public static final String RESOURCE_ID = "resource_id";
        public static final String RESOURCE_TYPE = "resource_type";
        public static final String ACCOUNT_MAPPING_ID = "account_mapping_id";
    }
}
