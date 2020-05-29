package pl.sda.jdbc.starter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Order {

    private final ConnectionFactory connectionFactory;
    List<ArrayList<String>> result = new ArrayList<>();
    String query1 = "select o.* from customers as c join orders as o on c.`customerNumber` = o.`customerNumber`" +
            "where c.`salesRepEmployeeNumber` like  ?";
    String query2 = "select * from orders where `orderDate` between ? and ?";

    public Order(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public List<ArrayList<String>> findOrdersByEmloyeeId(int id) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query1);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            String[] columns = new String[resultSet.getMetaData().getColumnCount()];

            for (int i = 0; i < columns.length; i++) {
                columns[i] = resultSet.getMetaData().getColumnName(i + 1);
            }

            while (resultSet.next()) {
                ArrayList<String> row = new ArrayList<>();
                row.add("row - " + resultSet.getRow());
                for (String column :
                        columns) {
                    row.add(column + " - " + resultSet.getString(column));
                }
                result.add(row);
            }
            return result;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public List<ArrayList<String>> findOrdersByDate(Date from, Date to) {
        try (Connection connection = connectionFactory.getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(query2);
            preparedStatement.setDate(1, from);
            preparedStatement.setDate(2, to);
            final ResultSet resultSet = preparedStatement.executeQuery();

            String[] columns = new String[resultSet.getMetaData().getColumnCount()];

            for (int i = 0; i < columns.length; i++) {
                columns[i] = resultSet.getMetaData().getColumnName(i + 1);
            }

            while (resultSet.next()){
                ArrayList<String> row = new ArrayList<>();
                row.add("row - " + resultSet.getRow());
                for (String column :
                        columns) {
                    row.add(column + " - " + resultSet.getString(column));
                }
                result.add(row);
            }
            return result;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
