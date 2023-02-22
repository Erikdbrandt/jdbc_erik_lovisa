package el.jdbc_erik_lovisa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ChinookAppRunner implements ApplicationRunner {
    private final CustomerRepository customerRepository;

    @Autowired
    public ChinookAppRunner(CustomerRepository costumerRepository) {

        this.customerRepository = costumerRepository;
    }

    @Override
    public void run(ApplicationArguments args) {

        //customerRepository.findAll().forEach(System.out::println);
        //System.out.println(customerRepository.findById(1));
        //customerRepository.findLimited(10, 0).forEach(System.out::println);

        //   customerRepository.insert(new Customer("Erik", "Larsson", "Sweden", "12345", "0701234567", "erikdbrandt@gmail.com"));

        //System.out.println(customerRepository.insert(new Customer("Pia", "Törnquist", "Sweden", "12345", "0701234567", "hanna.tornquist@gmail.com")));

        //  customerRepository.findAll().forEach(System.out::println);

//        System.out.println(customerRepository.findCountryWithMostCustomers());


       System.out.println(customerRepository.findTopGenreByCustomer(12));

        //  System.out.println(customerRepository.findByName("Erik"));


    }

}
