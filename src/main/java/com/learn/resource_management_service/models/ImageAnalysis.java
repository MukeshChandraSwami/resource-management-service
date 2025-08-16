package com.learn.resource_management_service.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class ImageAnalysis {

    private String mainContent;
    private Metadata metadata;

    private List<ObjectItem> objects;
    private List<TextItem> text;
    private Background background;

    private ColorsAndTextures colorsAndTextures;

    private Scene scene;
    private Style style;
    private List<String> tags;

    @Setter
    @Getter
    @ToString
    public static class ObjectItem {
        private String name;
        private Integer quantity;
        private String position;
        private String description;

    }

    @Setter
    @Getter
    @ToString
    public static class TextItem {
        private String content;
        private String position;

    }

    @Setter
    @Getter
    @ToString
    public static class Background {
        private String environment;
        private String details;

    }

    @Setter
    @Getter
    @ToString
    public static class ColorsAndTextures {
        private List<String> dominantColors;

        private List<String> textures;

    }

    @Setter
    @Getter
    @ToString
    public static class Scene {
        private String type;
        private String activity;

    }

    @Setter
    @Getter
    @ToString
    public static class Style {
        private String imageType;
        private String moodOrTone;
    }

    @Setter
    @Getter
    @ToString
    @Builder
    public static class Metadata {
        private String resourceId;
        private String resourceType;
        private String accountMappingId;
    }
}
