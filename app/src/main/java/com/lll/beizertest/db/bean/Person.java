package com.lll.beizertest.db.bean;

/**
 * Created by longlong on 2018/3/1.
 *
 * @ClassName: Person
 * @Description:
 * @Date 2018/3/1
 */

public class Person {

    public Person() {
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    private String name;

    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
