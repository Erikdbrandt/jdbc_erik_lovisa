package el.jdbc_erik_lovisa;

public interface CustomerRepository extends CRUDRepository<Customer, Integer>{

    Customer findByName(String name);

}
