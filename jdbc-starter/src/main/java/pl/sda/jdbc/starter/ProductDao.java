package pl.sda.jdbc.starter;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {

    void save(NewProduct newProduct);

    void update (NewProduct newProduct);

    void delete (String id);

    NewProduct find (String id) throws SQLException;

    List<NewProduct> findAll() throws SQLException;

}
