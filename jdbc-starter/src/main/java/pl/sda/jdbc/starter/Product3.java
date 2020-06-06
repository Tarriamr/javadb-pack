package pl.sda.jdbc.starter;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class Product3 implements ProductDao {

    private final ConnectionFactory connectionFactory;

    public Product3(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void save(NewProduct newProduct) {
        try (Connection connection = connectionFactory.getConnection()) {
            String query = "INSERT INTO products (productCode, productName, productLine, productScale, productVendor, productDescription," +
                    " quantityInStock, buyPrice, MSRP) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, newProduct.getProductCode());
            statement.setString(2, newProduct.getProductName());
            statement.setString(3, newProduct.getProductLine());
            statement.setString(4, newProduct.getProductScale());
            statement.setString(5, newProduct.getProductVendor());
            statement.setString(6, newProduct.getProductDescription());
            statement.setInt(7, newProduct.getQuantityInStock());
            statement.setBigDecimal(8, newProduct.getBuyPrice());
            statement.setBigDecimal(9, newProduct.getMSRP());
            statement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void update(NewProduct newProduct) {

        if (newProduct.getProductCode() == null) {
            throw new RuntimeException("Product without ProductCode passed as parameter.");
        }

        try (Connection connection = connectionFactory.getConnection()) {

            String query = "UPDATE products SET (`productName`= ?, `productLine = ?`, `productScale` = ?, `productVendor` = ?," +
                    " `productDescription` = ?, `quantityInStock` = ?, `buyPrice` = ?, `MSRP` = ?) where `productCode` = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newProduct.getProductName());
            statement.setString(2, newProduct.getProductLine());
            statement.setString(3, newProduct.getProductScale());
            statement.setString(4, newProduct.getProductVendor());
            statement.setString(5, newProduct.getProductDescription());
            statement.setInt(6, newProduct.getQuantityInStock());
            statement.setBigDecimal(7, newProduct.getBuyPrice());
            statement.setBigDecimal(8, newProduct.getMSRP());
            statement.setString(9, newProduct.getProductCode());
            statement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(String productCode) {
        try (Connection connection = connectionFactory.getConnection()) {

            String query = "delete from products where `productCode` = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, productCode);
            statement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public NewProduct find(String id) throws SQLException {
        try (Connection connection = connectionFactory.getConnection()) {

            String query = "select * from products where `productCode`like ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                NewProduct p = new NewProduct();
                p.setMSRP(resultSet.getBigDecimal("MSRP"));
                p.setBuyPrice(resultSet.getBigDecimal("buyPrice"));
                p.setProductDescription(resultSet.getString("productDescription"));
                p.setProductVendor(resultSet.getString("productVendor"));
                p.setProductScale(resultSet.getString("productScale"));
                p.setProductLine(resultSet.getString("productLine"));
                p.setProductName(resultSet.getString("productName"));
                p.setProductCode(resultSet.getString("productCode"));
                p.setQuantityInStock(resultSet.getInt("quantityInStock"));
                return p;
            }
            return null;
        }
    }

    @Override
    public List<NewProduct> findAll() throws SQLException {
        try (Connection connection = connectionFactory.getConnection()) {

            String query = "select * from products";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            List<NewProduct> result = new LinkedList<>();

            while (resultSet.next()) {
                NewProduct p = new NewProduct();
                p.setMSRP(resultSet.getBigDecimal("MSRP"));
                p.setBuyPrice(resultSet.getBigDecimal("buyPrice"));
                p.setProductDescription(resultSet.getString("productDescription"));
                p.setProductVendor(resultSet.getString("productVendor"));
                p.setProductScale(resultSet.getString("productScale"));
                p.setProductLine(resultSet.getString("productLine"));
                p.setProductName(resultSet.getString("productName"));
                p.setProductCode(resultSet.getString("productCode"));
                p.setQuantityInStock(resultSet.getInt("quantityInStock"));
                result.add(p);
            }
            return result;
        }
    }
}
