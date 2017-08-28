package com.masonliu.gsonplus;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liumeng on 8/3/15.
 */
public class GsonManager {
    private volatile static GsonManager _instance;
    private Gson gson;

    private GsonManager() {
        gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new SerializeExclusionStrategy())
                .addDeserializationExclusionStrategy(new DeserializeExclusionStrategy())
                .registerTypeAdapter(Map.class, new MapDeserializer())
                .registerTypeAdapter(List.class, new ListDeserializer())
                .create();
    }

    public static GsonManager getInstance() {
        if (_instance == null) {
            synchronized (GsonManager.class) {
                if (_instance == null) {
                    _instance = new GsonManager();
                }
            }
        }
        return _instance;
    }

    public Gson getGson() {
        return gson;
    }

    private static class MapDeserializer implements JsonDeserializer<Map<String, Object>> {
        public Map<String, Object> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            Map<String, Object> m = new LinkedHashMap<>();
            JsonObject jo = json.getAsJsonObject();
            for (Map.Entry<String, JsonElement> mx : jo.entrySet()) {
                String key = mx.getKey();
                JsonElement v = mx.getValue();
                if (v.isJsonArray()) {
                    m.put(key, GsonManager.getInstance().getGson().fromJson(v, List.class));
                } else if (v.isJsonObject()) {
                    m.put(key, GsonManager.getInstance().getGson().fromJson(v, Map.class));
                } else if (v.isJsonPrimitive()) {
                    JsonPrimitive prim = v.getAsJsonPrimitive();
                    if (prim.isBoolean()) {
                        m.put(key, prim.getAsBoolean());
                    } else if (prim.isString()) {
                        m.put(key, prim.getAsString());
                    } else {
                        //handle double. integer .null
                        String vString = v.getAsString();
                        try {
                            Double d = Double.parseDouble(vString);
                            if (vString.contains(".")) {
                                m.put(key, d);
                            } else {
                                m.put(key, Integer.parseInt(vString));
                            }
                            continue;
                        } catch (Exception e) {
                        }
                        m.put(key, null);
                    }
                }

            }
            return m;
        }
    }

    private static class ListDeserializer implements JsonDeserializer<List<Object>> {

        public List<Object> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            List<Object> m = new ArrayList<>();
            JsonArray arr = json.getAsJsonArray();
            for (JsonElement v : arr) {
                if (v.isJsonObject()) {
                    m.add(GsonManager.getInstance().getGson().fromJson(v, Map.class));
                } else if (v.isJsonArray()) {
                    m.add(GsonManager.getInstance().getGson().fromJson(v, List.class));
                } else if (v.isJsonPrimitive()) {
                    JsonPrimitive prim = v.getAsJsonPrimitive();
                    if (prim.isBoolean()) {
                        m.add(prim.getAsBoolean());
                    } else if (prim.isString()) {
                        m.add(prim.getAsString());
                    } else {
                        //handle double.integer. null
                        String vString = v.getAsString();
                        try {
                            Double d = Double.parseDouble(vString);
                            if (vString.contains(".")) {
                                m.add(d);
                            } else {
                                m.add(Integer.parseInt(vString));
                            }
                            continue;
                        } catch (Exception e) {
                        }
                        m.add(null);
                    }
                }
            }
            return m;
        }
    }

    class SerializeExclusionStrategy implements ExclusionStrategy {

        public boolean shouldSkipClass(Class<?> arg0) {
            return false;
        }

        public boolean shouldSkipField(FieldAttributes f) {

            Ignore annotation = f.getAnnotation(Ignore.class);
            if (annotation != null && annotation.serialize()) {
                return true;
            }
            return false;
        }

    }

    class DeserializeExclusionStrategy implements ExclusionStrategy {

        public boolean shouldSkipClass(Class<?> arg0) {
            return false;
        }

        public boolean shouldSkipField(FieldAttributes f) {

            Ignore annotation = f.getAnnotation(Ignore.class);
            if (annotation != null && annotation.deserialize()) {
                return true;
            }
            return false;
        }

    }
}
