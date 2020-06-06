package pl.sda.jdbc.starter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Product {
    private final ConnectionFactory connectionFactory;
    List<String> result = new ArrayList<>();
    String query = "select * from  products where `productName` like ?";

    public Product(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public List<String> findProductByName(String nameMatcher) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + nameMatcher + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String row = resultSet.getRow() + "; " +
                        resultSet.getString("productCode") + "; " +
                        resultSet.getString("productName") + "; " +
                        resultSet.getString("productLine") + "; " +
                        resultSet.getString("productScale") + "; " +
                        resultSet.getString("productVendor") + "; " +
                        resultSet.getString("productDescription") + "; " +
                        resultSet.getString("quantityInStock") + "; " +
                        resultSet.getString("buyPrice") + "; " +
                        resultSet.getString("MSRP");
                result.add(row);
            }
            return result;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
