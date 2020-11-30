package com.xian.tinybatis.executor;


import com.xian.tinybatis.config.Configuration;
import com.xian.tinybatis.sqlSession.MappedStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * DefaultExecutor
 *
 * @author lishouxian
 * @since 2020/11/30 19:03
 */
public class DefaultExecutor implements Executor {

    @Override
    public <E> List<E> query(MappedStatement mappedStatement, Configuration configuration) {
        Statement stmt = prepareStatement(configuration);
        ResultSet resultSet = null;
        try {
            resultSet = stmt.executeQuery(mappedStatement.getSqlCommand());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }


    private Statement prepareStatement(Configuration configuration){
        Statement stmt =null;
        Connection connection = null;
        try {
            connection = configuration.getDataSource().getConnection();
            stmt = connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //调用StatementHandler.prepare

        return stmt;
    }
}
