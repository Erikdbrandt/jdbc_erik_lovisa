package el.jdbc_erik_lovisa;

import el.jdbc_erik_lovisa.models.CustomerCountry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerRepositoryImpl implements CustomerRepository {
    private final String url;
    private final String username;
    private final String password;

    public CustomerRepositoryImpl(
            @Value("${spring.datasource.url}") String url,
            @Value("${spring.datasource.username}") String username,
            @Value("${spring.datasource.password}") String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public List<Customer> findAll() {

        String sql =
                        "SELECT * " +
                        "FROM customer";
        List<Customer> customers = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            // Write statement
            PreparedStatement statement = conn.prepareStatement(sql);
            // Execute statement
            ResultSet result = statement.executeQuery();
            // Handle result
            while (result.next()) {
                Customer customer = new Customer(
                        result.getInt("customer_id"),
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getString("country"),
                        result.getString("postal_code"),
                        result.getString("phone"),
                        result.getString("email"
                        ));
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    @Override
    public Customer findById(Integer id) {
        String sql = "SELECT * FROM customer WHERE customer_id = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                Customer customer = new Customer(
                        result.getInt("customer_id"),
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getString("country"),
                        result.getString("postal_code"),
                        result.getString("phone"),
                        result.getString("email")
                );
                return customer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Found no customer with id: " + id);
        return null;
    }

    @Override
    public Customer findByName(String name) {

        /*
         * using the LIKE operator to also find customers with names that start with the search string
         * I'm checking for both first and last name
         * */

        String sql =
                        "SELECT * " +
                        "FROM customer " +
                        "WHERE first_name " +
                        "LIKE ? OR last_name LIKE ?";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {

            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setString(1, name + "%");
            statement.setString(2, name + "%");

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return new Customer(
                        result.getInt("customer_id"),
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getString("country"),
                        result.getString("postal_code"),
                        result.getString("phone"),
                        result.getString("email")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // If no customer is found, return null

        System.out.println("No customer found with name: " + name);

        return null;
    }

    @Override
    public List<Customer> findLimited(int limit, int offset) {

        /*
         * Queries the database and returns a limited selection, a "page", with users back.
         * Use the offset parameter to change where the query starts, begins at customer_id = 1.
         * */

        String sql = "SELECT * FROM customer ORDER BY customer_id LIMIT ? OFFSET ?";
        List<Customer> customers = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setInt(1, limit);
            statement.setInt(2, offset);

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Customer customer = new Customer(
                        result.getInt("customer_id"),
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getString("country"),
                        result.getString("postal_code"),
                        result.getString("phone"),
                        result.getString("email"
                        ));
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    @Override
    public int insert(Customer customer) {

        String sql =
                "INSERT INTO customer (first_name, last_name, country, postal_code, phone, email)" +
                        " VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, customer.firstName());
            statement.setString(2, customer.lastName());
            statement.setString(3, customer.country());
            statement.setString(4, customer.postalCode());
            statement.setString(5, customer.phoneNr());
            statement.setString(6, customer.email());
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /*
     * This method returns the country with the most customers if there are multiple countries with the same amount of customers, it will return the first one in the list.
     *
     * */
    @Override
    public CustomerCountry findCountryWithMostCustomers() {
        String sql =
                "SELECT country, COUNT(*) AS customer_count" +
                        " FROM customer " +
                        " GROUP BY country" +
                        " ORDER BY customer_count" +
                        " DESC" +
                        " LIMIT 1";
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return new CustomerCountry(
                        result.getString("country"),
                        result.getInt("customer_count")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int update(Customer object) {
        return 0;
    }

    @Override
    public int delete(Customer object) {
        return 0;
    }

    @Override
    public int deleteById(Integer id) {
        return 0;
    }
}
