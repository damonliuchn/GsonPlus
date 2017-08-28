package com.masonliu.gsonplus_demo;

import com.masonliu.gsonplus.Ignore;

import java.io.Serializable;

/**
 * Created by liumeng02 on 2017/8/18.
 */

public class Student implements Serializable {
    private int age;
    private String name;
    @Ignore
    private String classz;
    private double xuefei;
    public Student(int age,String name,String classz,double xuefei){
        this.age = age;
        this.name = name;
        this.classz = classz;
        this.xuefei = xuefei;
    }

    @Override
    public String toString() {
        return "Student{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", classz='" + classz + '\'' +
                ", xuefei=" + xuefei +
                '}';
    }
}
