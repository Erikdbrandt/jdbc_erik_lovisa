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

    }

}
