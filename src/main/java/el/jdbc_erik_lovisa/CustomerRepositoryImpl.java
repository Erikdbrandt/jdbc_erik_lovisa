package el.jdbc_erik_lovisa;

import el.jdbc_erik_lovisa.models.Customer;
import el.jdbc_erik_lovisa.models.CustomerCountry;
import el.jdbc_erik_lovisa.models.CustomerSpender;
import el.jdbc_erik_lovisa.models.CustomerGenre;
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

    /**
     *
     * @param url
     * @param username
     * @param password
     */
    public CustomerRepositoryImpl(
            @Value("${spring.datasource.url}") String url,
            @Value("${spring.datasource.username}") String username,
            @Value("${spring.datasource.password}") String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /**
     * This method finds all customers in the database
     * @return List of all customers
     * @throws SQLException if a database access error occurs
     */
    @Override
    public List<Customer> findAll() {

        String sql =
                        """
                        SELECT *  
                        FROM customer""";
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

    /**
     * Read a specific customer from the database by ID.
     * @param id This is the customer_id that is used to find the specific customer.
     * @return This returns a Customer if found in the database, otherwise it will print
     * "Found no customer with id: " + id and then return null.
     */
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

    /**
     * Using the LIKE operator to also find customers with names that start with the search string
     * We're checking for both first and last name
     * @param name the name of the customer to search for
     * @return a customer with the given name, or null if no customer is found
     * @exception SQLException if a database access error occurs
     */
    @Override
    public Customer findByName(String name) {



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

        System.out.println("No customer found with name: " + name);

        return null;
    }

    /**
     * Return a page of customers from the database. This should take in limit and offset as parameters and make use
     * of the SQL limit and offset keywords to get a subset of the customer data. The customer model from above
     * should be reused.
     * @param limit This sets the limit in the SQL query on how many customers to display.
     * @param offset This sets the offset of the SQL query, i.e. on which row to start. Begins at customer_id = 1.
     * @return This returns a list, or "page", with the specified amount of customers from the desired starting point.
     */
    @Override
    public List<Customer> findLimited(int limit, int offset) {
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


    /**
     * This method inserts a new customer into the database
     * @return a int with the number of rows affected or 0 if something went wrong
     * @exception SQLException if a database access error occurs
     */
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

    /**
     * This method returns the country with the most customers
     * @return a CustomerCountry object with the country and the number of customers
     * @exception SQLException if a database access error occurs
     */
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


    /**
     *  This method returns the top 5 genres for a customer
     * @param customerId the id of the customer
     * @return a list of CustomerGenre objects
     * @exception SQLException if a database access error occurs
     */
    @Override
    public List<CustomerGenre> findTopGenreByCustomer(int customerId) {
        String sql = """
                SELECT g.name AS genre_name, COUNT(*) AS invoice_count
                FROM customer c
                INNER JOIN invoice i ON c.customer_id = i.customer_id
                INNER JOIN invoice_line il ON i.invoice_id = il.invoice_id
                INNER JOIN track t ON il.track_id = t.track_id
                INNER JOIN genre g ON t.genre_id = g.genre_id
                WHERE c.customer_id = ?
                GROUP BY g.name
                HAVING COUNT(*) = (
                  SELECT COUNT(*)
                  FROM customer c
                  INNER JOIN invoice i ON c.customer_id = i.customer_id
                  INNER JOIN invoice_line il ON i.invoice_id = il.invoice_id
                  INNER JOIN track t ON il.track_id = t.track_id
                  INNER JOIN genre g ON t.genre_id = g.genre_id
                  WHERE c.customer_id = ?
                  GROUP BY g.name
                  ORDER BY COUNT(*) DESC
                  LIMIT 1
                )
                ORDER BY invoice_count DESC;""";


        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, customerId);
            statement.setInt(2, customerId);
            ResultSet result = statement.executeQuery();
            List<CustomerGenre> customerGenres = new ArrayList<>();
            while (result.next()) {
                customerGenres.add(new CustomerGenre(
                        result.getString("genre_name"),
                        result.getInt("invoice_count")
                ));
            }
            return customerGenres;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Customer who is the highest spender (total in invoice table is the largest.
     * @return a CustomerSpender object containing the customer's id, first name, last name and a total of what
     * they have spent.
     */
    @Override
    public CustomerSpender highestSpendingCustomer() {
        String sql =
                "SELECT c.customer_id, c.first_name, c.last_name, SUM(total) AS total_sum " +
                        "FROM customer c " +
                        "INNER JOIN invoice i ON c.customer_id = i.customer_id " +
                        "GROUP BY c.customer_id, c.first_name " +
                        "ORDER BY total_sum DESC ";

        try (Connection conn = DriverManager.getConnection(url, username, password)){
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return new CustomerSpender(
                        result.getInt("customer_id"),
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getDouble("total_sum")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Update an existing customer.
     * @param customer A Customer object with the updated customer information.
     * @return The updated Customer object.
     */
    @Override
    public int update(Customer customer) {
        String sql = "UPDATE customer SET first_name = ?, last_name = ?, country = ?, postal_code = ?, phone = ?, email =? WHERE customer_id = ?";

        try(Connection conn = DriverManager.getConnection(url, username, password)) {

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, customer.firstName());
            statement.setString(2, customer.lastName());
            statement.setString(3, customer.country());
            statement.setString(4, customer.postalCode());
            statement.setString(5, customer.phoneNr());
            statement.setString(6, customer.email());
            statement.setInt(7, customer.id());
            return statement.executeUpdate();

        } catch(SQLException e) {
            e.printStackTrace();
        }
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
