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

            Employee employee = new Employee("Susanna", "Schmide", 10);

            employeeRepository.save(employee);

            Role kitchen = new Role("Kitchen");
            kitchen.addEmployee(new Employee("Tim", "Taylor", 10));
            kitchen.addEmployee(new Employee("Al", "Borland", 100));
            kitchen.addEmployee(new Employee("Wilson", "Wilson", 3));
            kitchen.addEmployee(new Employee("Bob", "Vila", 90));
            kitchen.addEmployee(new Employee("Anna", "Wildhorn", 40));
            kitchen.addEmployee(new Employee("Robert", "Hansen", 8));
            roleRepository.save(kitchen);

            Role service = new Role("Service");
            service.addEmployee(new Employee("Susi", "Leicht", 10));
            service.addEmployee(new Employee("Tom", "Nox", 100));
            roleRepository.save(service);

            Shift shift1 = new Shift("Morgen", 800, 1200, "ms", service, 3);
            Shift shift2 = new Shift("Mittag", 3, 3, "ngs", kitchen, 100);
            Shift shift3 = new Shift("Nacht", 2300, 2400, "ns", kitchen, 1);


            shiftRepository.save(shift1);
            shiftRepository.save(shift2);
            shiftRepository.save(shift3);

            ShiftPlan shiftplan1 = new ShiftPlan(12, 2018);
            ShiftPlan shiftplan2 = new ShiftPlan(11, 2018);
            ShiftPlan shiftplan3 = new ShiftPlan(1, 2019);

            shift1.addShiftPlan(shiftplan1);
            shift3.addShiftPlan(shiftplan3);

            shiftPlanRepository.save(shiftplan1);
            shiftPlanRepository.save(shiftplan2);
            shiftPlanRepository.save(shiftplan3);
        }
    }


}
