package com.liftlab.utils;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import java.io.StringReader;
import java.io.StringWriter;

public class JsonPayloadModifier {
	public static String replaceIdWithValue(String originalJson, int newId) {
        try (JsonReader reader = Json.createReader(new StringReader(originalJson))) {
            JsonObject jsonObject = reader.readObject();
            modifyJsonValue(jsonObject, "id", newId);

            StringWriter stringWriter = new StringWriter();
            try (JsonWriter writer = Json.createWriter(stringWriter)) {
                writer.writeObject(jsonObject);
            }
            return stringWriter.toString();
        }
    }

    private static void modifyJsonValue(JsonObject jsonObject, String dynamicKey, int newValue) {
        for (String key : jsonObject.keySet()) {
            JsonValue value = jsonObject.get(key);

            if (value instanceof JsonObject) {
                modifyJsonValue((JsonObject) value, dynamicKey, newValue);
            } else if (value instanceof JsonValue && key.equals(dynamicKey)) {
                jsonObject.put(key, Json.createValue(newValue));
            }
        }
    }	
}
