package com.example.modelClass;

import java.sql.ResultSet;
import java.sql.SQLException;

public class demands {
    public int id;
    public String name;
    public String type;
    public String information;
    public double price;
    public String image;

    public demands(ResultSet rs) throws SQLException {
        id = rs.getInt("id");
        name = rs.getString("name");
        type = rs.getString("type");
        information = rs.getString("information");
        price = rs.getDouble("price");
        image = rs.getString("image");
    }
    public demands()  {

    }

}
