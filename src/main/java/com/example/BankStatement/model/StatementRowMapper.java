package com.example.BankStatement.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.BankStatement.entity.Statements;

public class StatementRowMapper implements RowMapper<Statements> {

    @Override
    public Statements mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Statements(rs.getInt("id"), rs.getString("datefield"), rs.getString("amount"));
    }

}
