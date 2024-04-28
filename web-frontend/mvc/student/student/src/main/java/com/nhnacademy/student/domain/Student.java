package com.nhnacademy.student.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Student implements Serializable {

    public Student() {
    }

    public Student(String id, String name, Gender gender, int age) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        createdAt = LocalDateTime.now();
    }

    //아이디
    private String id;
    //이름
    private String name;
    //성별
    private Gender gender;
    //나이
    private int age;
    //생성일
    private LocalDateTime createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "id=" + id + ", name=" + name + ", gender=" + gender + ", age=" + age;
    }

    // ... java beans 특징을 고려하여 작성합니다.

    public boolean isValid(){
        return id != null && name != null && gender != null && age > 0;
    }

}