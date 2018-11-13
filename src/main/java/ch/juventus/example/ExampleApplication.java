package ch.juventus.example;

import ch.juventus.example.data.department.Department;
import ch.juventus.example.data.department.DepartmentRepository;
import ch.juventus.example.data.employee.Employee;
import ch.juventus.example.data.shift.Shift;
import ch.juventus.example.data.shiftplan.ShiftPlan;
import ch.juventus.example.data.shiftplan.ShiftPlan;
import ch.juventus.example.data.shiftplan.ShiftPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class ExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExampleApplication.class, args);
    }

    @Component
    class initRepositoryCLR implements CommandLineRunner {

        private final DepartmentRepository departmentRepository;
        private final ShiftPlanRepository shiftPlanRepository;

        @Autowired
        public initRepositoryCLR(DepartmentRepository departmentRepository, ShiftPlanRepository shiftPlanRepository) {
            this.departmentRepository = departmentRepository;
            this.shiftPlanRepository = shiftPlanRepository;
        }

        @Override
        public void run(String... strings) throws Exception {
            Department kitchen = new Department("Kitchen");
            kitchen.addEmployee(new Employee("Tim", "Taylor", 10));
            kitchen.addEmployee(new Employee("Al", "Borland", 100));
            kitchen.addEmployee(new Employee("Wilson", "Wilson", 3));
            kitchen.addEmployee(new Employee("Bob", "Vila", 90));
            kitchen.addEmployee(new Employee("Anna", "Wildhorn", 40));
            kitchen.addEmployee(new Employee("Robert", "Hansen", 8));
            departmentRepository.save(kitchen);

            Department service = new Department("Service");
            service.addEmployee(new Employee("Susi", "Leicht", 10));
            service.addEmployee(new Employee("Tom", "Nox", 100));
            departmentRepository.save(service);


            Shift shift = new Shift("Morgen", 800, 1200, "ms", service, 3);
            ShiftPlan shiftplan = new ShiftPlan(12,2018);
            shift.addShiftPlan(shiftplan);
            shiftPlanRepository.save(shiftplan);

            Shift shift2 = new Shift("Nacht", 2300, 2400, "ns", kitchen, 1);
            Shift shift3 = new Shift("dll", 3, 3, "ngs", kitchen, 100);
            ShiftPlan shiftplan2 = new ShiftPlan(12,2018);
            shift2.addShiftPlan(shiftplan);
            shift3.addShiftPlan(shiftplan);

            shiftPlanRepository.save(y);

        }
    }


}
