package com.learn.resource_management_service.utils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

public class EmbeddingUtils {

    public static String toEmbeddingInput(Object obj) {
        StringBuilder sb = new StringBuilder();
        serializeObject(obj, sb, "");
        return sb.toString().trim();
    }

    private static void serializeObject(Object obj, StringBuilder sb, String prefix) {
        if (obj == null) {
            return;
        }

        Class<?> clazz = obj.getClass();

        // If primitive, wrapper, or string → append directly
        if (clazz.isPrimitive() || obj instanceof String || obj instanceof Number || obj instanceof Boolean) {
            sb.append(prefix).append(obj.toString()).append(". ");
            return;
        }

        // If it's a collection
        if (obj instanceof Collection<?>) {
            Collection<?> collection = (Collection<?>) obj;
            for (Object item : collection) {
                serializeObject(item, sb, prefix);
            }
            return;
        }

        // If it's a map
        if (obj instanceof Map<?, ?>) {
            Map<?, ?> map = (Map<?, ?>) obj;
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                sb.append(prefix).append(entry.getKey()).append(": ");
                serializeObject(entry.getValue(), sb, prefix + entry.getKey() + " ");
            }
            return;
        }

        // If it's an array
        if (clazz.isArray()) {
            int length = java.lang.reflect.Array.getLength(obj);
            for (int i = 0; i < length; i++) {
                serializeObject(java.lang.reflect.Array.get(obj, i), sb, prefix);
            }
            return;
        }

        // Otherwise, assume it's a custom object → reflect fields
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object value = field.get(obj);
                if (value != null) {
                    String fieldName = field.getName();
                    sb.append(prefix).append(fieldName).append(": ");
                    serializeObject(value, sb, prefix + fieldName + " ");
                    sb.append("\n");
                }
            } catch (IllegalAccessException e) {
                // ignore inaccessible fields
            }
        }
    }
}
