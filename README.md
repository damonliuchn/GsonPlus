# GsonPlus

### 一、gson默认实现
1.  java double 转成 json string 后自动加 小数点
2.  默认json string 中的数值 都转成 double
3.  json string null 转成 java 时  int 默认0 string 默认 null  double 默认0.0

### 二、本项目功能
1. 添加Ignore注解，可配置某些字段不序列化 或 不反序列化。
2. 解析Map<String, Object>、List<Object>时，精准解析Double和Integer。
```java
Map<String, Object> requestMap = gson.fromJson(aa, new TypeToken<Map<String, Object>>() {}.getType());
List<Object> stringList = gson.fromJson(jsonArray, new TypeToken<List<Object>>() {}.getType());
```
  
### 三、使用
```groovy
repositories {
    maven {
        url "https://jitpack.io"
    }
}
dependencies {
	compile 'com.github.MasonLiuChn:GsonPlus:2.8.2.0'
}
```

```java
Gson gson = GsonManager.getInstance().getGson();
```



