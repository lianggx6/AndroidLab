package com.example.modelClass;

import java.sql.ResultSet;
import java.sql.SQLException;

public class demands_collect {
    public int id;
    public int collector_id;
    public int goods_id;
    public String status;

    public demands_collect(ResultSet rs) throws SQLException {
        id = rs.getInt("id");
        collector_id = rs.getInt("seller_id");
        goods_id = rs.getInt("goods_id");
        status = rs.getString("status");
    }
    public demands_collect()  {

    }

}
