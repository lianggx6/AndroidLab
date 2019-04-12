package com.example.modelClass;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class goods_publish implements Serializable {
    public int id;
    public int seller_id;
    public int goods_id;
    public String start_time;
    public String finish_time;
    public String status;

    public goods_publish(ResultSet rs) throws SQLException {
        id = rs.getInt("id");
        seller_id = rs.getInt("seller_id");
        goods_id = rs.getInt("goods_id");
        start_time = rs.getString("start_time");
        finish_time = rs.getString("finish_time");
        status = rs.getString("status");
    }
    public goods_publish()  {

    }

}
