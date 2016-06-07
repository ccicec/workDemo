package com.qs.xutils3demo.model.dao.table;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by xuyang on 16/5/24.
 */
@Table(name = "person")
public class Person {



    @Column(name = "name",isId = true)//指定主键   不使用自增id
    private String name;

    @Column(name = "email")
    private String email;
    @Column(name = "age")
    private String age;
    @Column(name = "gender")
    private String gender;

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
