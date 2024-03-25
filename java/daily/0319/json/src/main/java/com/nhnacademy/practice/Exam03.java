package com.nhnacademy.practice;

import org.json.JSONObject;

public class Exam03 {
    public static void main(String[] args) {
        Person person = new Person("miro");
        person.setAddress(new Address("13487", "Seongnam"));
        JSONObject object = new JSONObject(person);
        System.out.println(object);
    }
}

// class Person {
//     String name;
//     Address address;

//     public Person(String name) {
//         this.name = name;
//     }

//     public String getName() {
//         return name;
//     }

//     public void setName(String name) {
//         this.name = name;
//     }

//     public Address getAddress() {
//         return address;
//     }

//     public void setAddress(Address address) {
//         this.address = address;
//     }
// }
