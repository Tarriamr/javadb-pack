package pl.sda.jdbc.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClassicModelsManager {

    // tworzenie logera do obsługi tekstów
    private static final Logger logger = LoggerFactory.getLogger(ClassicModelsManager.class);

    // nie chcemy przy pomocy new kozystać z Factory,cdlstego tworzymy prywatne pole connectionFactory,
    // które uruchamiamy przy pomocy kosttuktora
    private final ConnectionFactory connectionFactory;

    public ClassicModelsManager(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        ConnectionFactory connectionFactory = new ConnectionFactory();
        ClassicModelsManager manager = new ClassicModelsManager(connectionFactory);
        logger.info("\n\n" +
                "Choice one options:\n" +
                "1. printAllEmployees.\n" +
                "2. printAllOffices.\n" +
                "3. insertProductLine.\n" +
                "4. updateProductPrices.\n" +
                "5. printAllCustomersWithSalesRepName.\n" +
                "6. public List<Product> findProductByName(String nameMatcher).\n" +
                "7. public List<Product> findProductByName(String nameMatcher) v2.\n" +
                "8. public List<Order> findOrdersByEmloyeeId(int id) v2.\n" +
                "9. public List<Order> findOrdersByDate(Date from, Date to) v2.");
        String choice = scanner.nextLine();
        switch (choice) {
            case "1": {
                manager.printAllEmployees();
                break;
            }
            case "2": {
                manager.printAllOffices();
                break;
            }
            case "3": {
                logger.info("What is the name of the new product line?");
                String answer = scanner.nextLine();
                manager.insertProductLine(answer);
                break;
            }
            case "4": {
                logger.info("By what percentage do you want to change the price?");
                int answer = scanner.nextInt();
                manager.updateProductPrices(answer);
                break;
            }
            case "5": {
                manager.printAllCustomersWithSalesRepName();
                break;
            }
            case "6": {
                Product product = new Product(connectionFactory);
                logger.info("What is the name of the product?");
                String s1 = scanner.nextLine();
                List<String> info = product.findProductByName(s1);
                for (String s :
                        info) {
                    logger.info("{}", s);
                }
                break;
            }
            case "7": {
                Product2 product2 = new Product2(connectionFactory);
                logger.info("What is the name of the product?");
                String s2 = scanner.nextLine();
                List<ArrayList<String>> productByName = product2.findProductByName(s2);
                for (ArrayList<String> result :
                        productByName) {
                    for (String s :
                            result) {
                        logger.info("{}", s);
                    }
                }
                break;
            }
            case "8": {
                Order order = new Order(connectionFactory);
                logger.info("What is the id of employ?");
                int i8 = scanner.nextInt();
                List<ArrayList<String>> ordersByEmloyeeId = order.findOrdersByEmployeeId(i8);
                for (ArrayList<String> resoult :
                        ordersByEmloyeeId) {
                    for (String s :
                            resoult) {
                        logger.info("{}", s);
                    }
                }
                break;
            }
            case "9": {
                Order order = new Order(connectionFactory);
                logger.info("What is the date from?.");
                String from = scanner.nextLine();
                logger.info("What ist the date to?");
                String to = scanner.nextLine();
                List<ArrayList<String>> ordersByDate = order.findOrdersByDate(Date.valueOf(from), Date.valueOf(to));
                for (ArrayList<String> resoult :
                        ordersByDate) {
                    for (String s :
                            resoult) {
                        logger.info("{}", s);
                    }
                }
                break;
            }
            default: {
                logger.info("Incorrect choice.");
            }
        }
    }

    public void printAllEmployees() {
        // pozyskujemu obiekt connectionFactory i łączymy się przez niego z bazą
        // .var - automatycznie robi przypisanie na początku
        // ​try-with-resources - otwiera i automatycznie zamyka połączenie które jest w nawiasie po try
        try (Connection connection = connectionFactory.getConnection()) {
            // statement obsługuje jednorazowe zapytanie (połączenie) do bazy danych
            Statement statement = connection.createStatement();
            // wykonujemy zapytanie do bazy w formacie sql, i odbieramu odpowiedż resultSet
            ResultSet resultSet = statement.executeQuery("SELECT * FROM employees");
            // next - odpowiada na pytanie czy jest jeszcze jeden wynik do przekazania, jezeli tak,
            // to przejdź na niego i przekaż go do wykonania
            // next zaczyna od pierwszego dostępnego wiersza w otrzymanej odpowiedzi z bazy
            while (resultSet.next()) {
                // przekazuje dane zgodnie z kolumnami jakie zawiera odpowiedź
                String employeeNumber = resultSet.getString("employeeNumber");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                // logger wyswietla otrzymane wyniki
                logger.info("Employee: number {}, name {} {}", employeeNumber, firstName, lastName);
            }
        } catch (SQLException throwables) {
            logger.error("Problem with connection.", throwables);
        }
    }

    public void printAllOffices() {
        try (Connection connection = connectionFactory.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM offices");
            while (resultSet.next()) {
                String officeCode = resultSet.getString("officeCode");
                String city = resultSet.getString("city");
                String phone = resultSet.getString("phone");
                String addressLine1 = resultSet.getString("addressLine1");
                String addressLine2 = resultSet.getString("addressLine2");
                String state = resultSet.getString("state");
                String country = resultSet.getString("country");
                String postalCode = resultSet.getString("postalCode");
                String territory = resultSet.getString("territory");
                logger.info("Offices: {}, {}, {}, {}, {}, {}, {}, {}, {}",
                        officeCode, city, phone, addressLine1, addressLine2, state, country, postalCode, territory);
            }
        } catch (SQLException throwables) {
            logger.info("Problem with connection.", throwables);
        }
    }

    public void insertProductLine(String name) {
        try (Connection connection = connectionFactory.getConnection()) {
            Statement statement = connection.createStatement();
            String newProductLine = "INSERT INTO productlines (productLine) VALUES ('" + name + "')";
            int numberOfRows = statement.executeUpdate(newProductLine);
            logger.info("{} Rows affested", numberOfRows);
        } catch (SQLException throwables) {
            logger.error("Problem with connection.", throwables);
        }

    }

    public void updateProductPrices(int percent) {
        try (Connection connection = connectionFactory.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM products");
            while (resultSet.next()) {
                String productCode = resultSet.getString("productCode");
                String buyPrice = resultSet.getString("buyPrice");
                double price = Double.parseDouble(buyPrice) * (1 + percent / 100.00);
                Statement statement1 = connection.createStatement();
                statement1.executeUpdate("UPDATE products SET buyPrice = '" + price + "' WHERE productCode = '" + productCode + "'");
            }
        } catch (SQLException throwables) {
            logger.error("Problem with connection.", throwables);
        }
    }

    public void printAllCustomersWithSalesRepName() {
        try (Connection connection = connectionFactory.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select c.`customerName`, concat(e.`firstName`, ' ', e.`lastName`) as employerName\n" +
                    " from  customers as c join employees as e on c.`salesRepEmployeeNumber`= e.`employeeNumber`\n" +
                    " order by c.`customerName`");
            while (resultSet.next()) {
                String customerName = resultSet.getString("customerName");
                String employerName = resultSet.getString("employerName");
                logger.info("{} - {}", customerName, employerName);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
