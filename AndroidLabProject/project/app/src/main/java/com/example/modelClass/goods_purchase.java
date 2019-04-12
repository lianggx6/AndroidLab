package com.example.modelClass;

import java.sql.ResultSet;
import java.sql.SQLException;

public class goods_purchase {
    public int id;
    public int buyer_id;
    public int goods_id;
    public String start_time;
    public String finish_time;
    public String status;

    public goods_purchase(ResultSet rs) throws SQLException {
        id = rs.getInt("id");
        buyer_id = rs.getInt("buyer_id");
        goods_id = rs.getInt("goods_id");
        start_time = rs.getString("start_time");
        finish_time = rs.getString("finish_time");
        status = rs.getString("status");
    }
    public goods_purchase()  {

    }

}
