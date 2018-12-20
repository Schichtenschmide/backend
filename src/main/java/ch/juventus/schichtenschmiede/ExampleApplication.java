package ch.juventus.schichtenschmiede;

import ch.juventus.schichtenschmiede.persistency.entity.Employee;
import ch.juventus.schichtenschmiede.persistency.entity.Role;
import ch.juventus.schichtenschmiede.persistency.entity.Shift;
import ch.juventus.schichtenschmiede.persistency.entity.ShiftPlan;
import ch.juventus.schichtenschmiede.persistency.repository.EmployeeRepository;
import ch.juventus.schichtenschmiede.persistency.repository.RoleRepository;
import ch.juventus.schichtenschmiede.persistency.repository.ShiftPlanRepository;
import ch.juventus.schichtenschmiede.persistency.repository.ShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//TODO Auslagern in eigenen Folder aktuell gleicher folder wie repositories
@SpringBootApplication
@EnableSwagger2
public class ExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExampleApplication.class, args);
    }

    @Component
    class initRepositoryCLR implements CommandLineRunner {

        private final RoleRepository roleRepository;
        private final EmployeeRepository employeeRepository;
        private final ShiftPlanRepository shiftPlanRepository;
        private final ShiftRepository shiftRepository;


        @Autowired
        public initRepositoryCLR(RoleRepository roleRepository,
                                 EmployeeRepository employeeRepository,
                                 ShiftPlanRepository shiftPlanRepository,
                                 ShiftRepository shiftRepository
        ) {
            this.roleRepository = roleRepository;
            this.employeeRepository = employeeRepository;
            this.shiftPlanRepository = shiftPlanRepository;
            this.shiftRepository = shiftRepository;
        }

        @Bean
        public WebMvcConfigurer corsConfigurer() {
            return new WebMvcConfigurerAdapter() {
                @Override
                public void addCorsMappings(CorsRegistry registry) {
                    //registry.addMapping("/**").allowedOrigins("https://schichtenschmiede-juventus.scapp.io").allowedMethods("GET", "POST","PUT");
                    registry.addMapping("/**").allowedOrigins("http://localhost:3000").allowedMethods("GET", "POST", "PUT");
                }
            };
        }

        @Override
        public void run(String... strings) throws Exception {

            Role kitchen = new Role("Küche", true);
            Role service = new Role("Service", true);

            roleRepository.save(kitchen);
            roleRepository.save(service);

            Employee employee1 = new Employee("Tim", "Taylor", 20);
            Employee employee2 = new Employee("Tom", "Nox", 100);

            employee1.setRole(kitchen);
            employee2.setRole(service);

            employeeRepository.save(employee1);
            employeeRepository.save(employee2);

            Shift shift1 = new Shift("Morgen", 8, 12, true, true, true, false, false, false, false, "ms", true, 3);
            Shift shift2 = new Shift("Mittag", 14, 19, true, false, true, true, false, false, false, "ms", true, 13);
            Shift shift3 = new Shift("Nacht", 21, 24, false, false, true, false, true, false, false, "ns", true, 5);

            shift1.setRole(service);
            shift2.setRole(kitchen);
            shift3.setRole(service);
            shiftRepository.save(shift1);
            shiftRepository.save(shift2);
            shiftRepository.save(shift3);

            ShiftPlan shiftplan2 = new ShiftPlan(11, 2018, true);
            shiftplan2.setShift(shift1);



            shiftPlanRepository.save(shiftplan2);

            employee1.addShiftplan(shiftplan2);
            employee2.addShiftplan(shiftplan2);


            employeeRepository.save(employee1);
            employeeRepository.save(employee2);


        }
    }
}
