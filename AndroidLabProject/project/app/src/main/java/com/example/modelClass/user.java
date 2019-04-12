package com.example.modelClass;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class user implements Serializable {
    public int id;
    public String name;
    public String sex;
    public String email;
    public String phone;
    public String password;
    public String avatar;
    public double star;

    public user(ResultSet rs) throws SQLException {
        id = rs.getInt("id");
        name = rs.getString("name");
        sex = rs.getString("sex");
        email = rs.getString("email");
        phone = rs.getString("phone");
        password = rs.getString("password");
        avatar = rs.getString("avatar");
        star = rs.getDouble("star");
    }
    public user()  {

    }
}
