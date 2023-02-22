package el.jdbc_erik_lovisa;

import java.util.List;

public interface CustomerRepository extends CRUDRepository<Customer, Integer>{

    Customer findByName(String name);

    List<Customer> findLimited(int limit, int offset);

}
