package com.learn.resource_management_service.utils;

import com.learn.resource_management_service.models.ImageAnalysis;

public class EmbeddingUtils {

    public static String toEmbeddingInput(ImageAnalysis imageAnalysis) {
        return toText(imageAnalysis);
    }

    public static String toText(ImageAnalysis analysis) {
        StringBuilder sb = new StringBuilder();

        if (analysis.getMainContent() != null) {
            sb.append("Main content: ").append(analysis.getMainContent()).append(". ");
        }

        if (analysis.getObjects() != null && !analysis.getObjects().isEmpty()) {
            sb.append("Objects: ");
            for (ImageAnalysis.ObjectItem obj : analysis.getObjects()) {
                sb.append(String.format("%s (quantity: %d, position: %s, description: %s). ",
                        obj.getName(),
                        obj.getQuantity(),
                        obj.getPosition(),
                        obj.getDescription()));
            }
        }

        if (analysis.getText() != null && !analysis.getText().isEmpty()) {
            sb.append("Text found: ");
            for (ImageAnalysis.TextItem txt : analysis.getText()) {
                sb.append(String.format("\"%s\" at %s. ", txt.getContent(), txt.getPosition()));
            }
        }

        if (analysis.getBackground() != null) {
            sb.append("Background: ")
                    .append(analysis.getBackground().getEnvironment()).append(", ")
                    .append(analysis.getBackground().getDetails()).append(". ");
        }

        if (analysis.getColorsAndTextures() != null) {
            sb.append("Colors: ")
                    .append(String.join(", ", analysis.getColorsAndTextures().getDominantColors()))
                    .append(". Textures: ")
                    .append(String.join(", ", analysis.getColorsAndTextures().getTextures()))
                    .append(". ");
        }

        if (analysis.getScene() != null) {
            sb.append("Scene type: ").append(analysis.getScene().getType())
                    .append(", activity: ").append(analysis.getScene().getActivity()).append(". ");
        }

        if (analysis.getStyle() != null) {
            sb.append("Style: ").append(analysis.getStyle().getImageType())
                    .append(", mood: ").append(analysis.getStyle().getMoodOrTone()).append(". ");
        }

        if (analysis.getMetadata() != null) {
            sb.append("Metadata: ")
                    .append("Resource Type: ").append(analysis.getMetadata().getResourceType()).append(", ")
                    .append("Resource Id: ").append(analysis.getMetadata().getResourceId()).append(", ")
                    .append("Account Mapping Id: ").append(analysis.getMetadata().getAccountMappingId()).append(". ");
        }

        if (analysis.getTags() != null && !analysis.getTags().isEmpty()) {
            sb.append("Tags: ").append(String.join(", ", analysis.getTags())).append(". ");
        }

        return sb.toString().trim();
    }
}
