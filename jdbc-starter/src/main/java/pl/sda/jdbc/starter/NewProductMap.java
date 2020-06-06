package pl.sda.jdbc.starter;

import java.util.Map;

public class NewProductMap {

    private Map<String, String> product;

    public NewProductMap(Map<String, String> product) {
        this.product = product;
    }

    public Map<String, String> getProduct() {
        return product;
    }

    public void setProduct(Map<String, String> product) {
        this.product = product;
    }
}
