package el.jdbc_erik_lovisa;

import el.jdbc_erik_lovisa.models.CustomerCountry;

import java.util.List;

public interface CustomerRepository extends CRUDRepository<Customer, Integer>{

    Customer findByName(String name);

    List<Customer> findLimited(int limit, int offset);

    CustomerCountry findCountryWithMostCustomers();

}
