package el.jdbc_erik_lovisa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JdbcErikLovisaApplication implements ApplicationRunner {


    @Autowired
    PostGradDOA postGradDOA;

    public static void main(String[] args) {



        SpringApplication.run(JdbcErikLovisaApplication.class, args);


    }

    @Override
    public void run(ApplicationArguments args) {
        postGradDOA.getAllArtists();
    }
}
