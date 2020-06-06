package pl.sda.jdbc.starter;

public class SQLProductParser {
    public String createSaveQuery(NewProduct newProduct) {
        String query = "insert into products value (" +
                newProduct.getProductCode() +
                newProduct.getProductName() +
                newProduct.getProductLine() +
                newProduct.getProductScale() +
                newProduct.getProductVendor() +
                newProduct.getProductDescription() +
                newProduct.getQuantityInStock() +
                newProduct.getBuyPrice() +
                newProduct.getMSRP() + ")";
        return query;
    }

    public String createUpdateQuery(NewProductMap newProductMap) {
        String query = "update products set " + newProductMap.getProduct();
        return query;
    }

    private boolean mapCheck(NewProductMap newProductMap, String key) {
        return newProductMap.getProduct().containsKey(key);
    }
}
