package com.lll.beizertest.ipc.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by longlong on 2019/4/17.
 *
 * @ClassName: User
 * @Description:
 * @Date 2019/4/17
 */

public class User implements Parcelable {

    private String userName;

    private int age;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userName);
        dest.writeInt(this.age);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.userName = in.readString();
        this.age = in.readInt();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
