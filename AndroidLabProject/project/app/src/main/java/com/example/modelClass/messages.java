package com.example.modelClass;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Sen7u on 2018/1/20.
 */

public class messages {
    public int id;
    public int sender_id;
    public int reciver_id;
    public String content;
    public String time;
    public String sender_avatar;
    public String reciver_avatar;
    public String sender_name;
    public String reciver_name;
    public String direction;

    public messages(ResultSet rs) throws SQLException {
        id = rs.getInt("id");
        sender_id = rs.getInt("sender_id");
        reciver_id = rs.getInt("reciver_id");
        content = rs.getString("content");
        time = rs.getString("time");
    }
    public messages()  {
    }
}
