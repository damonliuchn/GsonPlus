package com.masonliu.gsonplus_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.masonliu.gsonplus.GsonManager;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * 1、 java double 转成 json string 后自动加 小数点。 {"age":20,"name":"ming","xuefei":12.0}
 * 2、 默认json string 中的数值 都转成 double，需要处理
 * 3、 json string null 转成 java 时  int 默认0 string 默认 null  double 默认0.0
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Gson gson = GsonManager.getInstance().getGson();

//        Gson gson = new GsonBuilder().registerTypeAdapter(Double.class, new JsonDeserializer() {
//
//            @Override
//            public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
//                Log.e("MainActivity test null", json.toString());
//                return null;
//            }
//        }).create();


//        String bb = "{\"age\":null,\"classz\":\"1\",\"name\":null,\"xuefei\":null}";
//        Student student3 = gson.fromJson(bb, Student.class);
//        Log.e("MainActivity test null", student3.toString());


        Student student = new Student(20, "ming", "1", 12);
        String aa = gson.toJson(student);
//        Log.e("MainActivity", aa);


        Map<String, Object> requestMap = gson.fromJson(aa, new TypeToken<Map<String, Object>>() {}.getType());
        for (String key : requestMap.keySet()) {
            if (requestMap.get(key) instanceof Double) {
                Log.e("MainActivity", requestMap.get(key) + "d");
            }
            if (requestMap.get(key) instanceof Integer) {
                Log.e("MainActivity", requestMap.get(key) + "i");
            }
        }

//        Student student2 = gson.fromJson(aa, Student.class);
//        student2.toString();

    }
}
