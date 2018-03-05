package com.masonliu.gsonplus_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.masonliu.gsonplus.GsonManager;

import java.util.Map;

/**
 * 1、 java double 转成 json string 后自动加 小数点
 * 2、 json string 中的数值 都会转成 double
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


        String bb = "{\"age\":null,\"name\":null,\"classz\":\"1\",\"xuefei\":null}";
        Student student3 = gson.fromJson(bb, Student.class);
        Log.e("MainActivity test null", student3.toString());


        Student student = new Student(20, "ming", "1", 12);
        String aa = gson.toJson(student);
        Log.e("MainActivity  ", aa);


        Map<String, Object> requestMap = gson.fromJson(aa, new TypeToken<Map<String, Object>>() {
        }.getType());
        for (String key : requestMap.keySet()) {
            if (requestMap.get(key) instanceof Double) {
                Log.e("MainActivity", requestMap.get(key) + " is double");
            }
            if (requestMap.get(key) instanceof Integer) {
                Log.e("MainActivity", requestMap.get(key) + "is integer");
            }
        }

//        Student student2 = gson.fromJson(aa, Student.class);
//        student2.toString();

    }
}
