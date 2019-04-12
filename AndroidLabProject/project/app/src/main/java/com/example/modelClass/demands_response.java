package com.example.modelClass;

import java.sql.ResultSet;
import java.sql.SQLException;

public class demands_response {
    public int id;
    public int responeer_id;
    public int goods_id;
    public String start_time;
    public String finish_time;
    public String status;

    public demands_response(ResultSet rs) throws SQLException {
        id = rs.getInt("id");
        responeer_id = rs.getInt("responeer_id");
        goods_id = rs.getInt("goods_id");
        start_time = rs.getString("start_time");
        finish_time = rs.getString("finish_time");
        status = rs.getString("status");
    }
    public demands_response()  {

    }

}
