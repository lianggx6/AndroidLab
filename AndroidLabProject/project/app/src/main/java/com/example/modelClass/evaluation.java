package com.example.modelClass;
import java.sql.ResultSet;
import java.sql.SQLException;

public class evaluation {
    public int id;
    public int evaluator_id;
    public int aims_id;
    public String content;
    public double star;

    public evaluation(ResultSet rs) throws SQLException {
        id = rs.getInt("id");
        evaluator_id = rs.getInt("evaluator_id");
        aims_id = rs.getInt("aims_id");
        content = rs.getString("content");
        star = rs.getDouble("star");
    }
    public evaluation()  {

    }

}
