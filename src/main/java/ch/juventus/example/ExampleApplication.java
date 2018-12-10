package ch.juventus.example;

import ch.juventus.example.data.employee.Employee;
import ch.juventus.example.data.employee.EmployeeRepository;
import ch.juventus.example.data.role.Role;
import ch.juventus.example.data.role.RoleRepository;
import ch.juventus.example.data.shift.Shift;
import ch.juventus.example.data.shift.ShiftRepository;
import ch.juventus.example.data.shiftplan.ShiftPlan;
import ch.juventus.example.data.shiftplan.ShiftPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

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

            Role kitchen = new Role("Küche");
            Role service = new Role("Service");

            roleRepository.save(kitchen);
            roleRepository.save(service);

            Employee employee1 = new Employee("Tim", "Taylor", 10);
            Employee employee2 = new Employee("Tom", "Nox", 100);

            employee1.setRole(kitchen);
            employee2.setRole(service);

            employeeRepository.save(employee1);
            employeeRepository.save(employee2);

            Shift shift1 = new Shift("Morgen", 800, 1200, true, false, true, true, false, false, false, "ms", true, 3);
            Shift shift2 = new Shift("Mittag", 0300, 0400, true, false, true, true, false, false, false, "ms", true, 13);
            Shift shift3 = new Shift("Nacht", 2300, 2400, true, false, true, true, false, false, false, "ns", true, 5);

            shiftRepository.save(shift1);
            shiftRepository.save(shift2);
            shiftRepository.save(shift3);

            ShiftPlan shiftplan2 = new ShiftPlan(11, 2018);
            shiftplan2.setShift(shift1);

            shiftPlanRepository.save(shiftplan2);

            employee1.addShiftplan(shiftplan2);
            employee2.addShiftplan(shiftplan2);
            /*
            employeeRepository.save(employeeRepository.findOne(employee1.getStid()));
            employeeRepository.save(employeeRepository.findOne(employee2.getStid()));
            */

            employeeRepository.save(employee1);
            employeeRepository.save(employee2);



        }
    }
}
