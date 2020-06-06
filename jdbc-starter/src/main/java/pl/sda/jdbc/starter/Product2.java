package pl.sda.jdbc.starter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Product2 {
    private final ConnectionFactory connectionFactory;
    List<ArrayList<String>> result = new ArrayList<>();
    String query = "select * from  products where `productName` like ?";

    public Product2(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public List<ArrayList<String>> findProductByName(String nameMatcher) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + nameMatcher + "%");
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
}
