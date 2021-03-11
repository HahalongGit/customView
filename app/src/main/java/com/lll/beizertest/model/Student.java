package com.lll.beizertest.model;

import android.util.Log;

import java.util.Objects;

/**
 * Created by longlong on 2018/2/28.
 *
 * @ClassName: Student
 * @Description: 学生测试类
 * @Date 2018/2/28
 */

public abstract class Student {

    private String stuName;

    private String stuAge;


    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getStuAge() {
        return stuAge;
    }

    public void setStuAge(String stuAge) {
        this.stuAge = stuAge;
    }

    public static void function_static_mehod() {
        Log.e("TAG", "function_static_mehod静态方法调用");
    }

    protected void setText() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Student)) {
            return false;
        }
        Student student = (Student) o;
        return getStuName().equals(student.getStuName()) &&
                getStuAge().equals(student.getStuAge());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStuName(), getStuAge());
    }


}
