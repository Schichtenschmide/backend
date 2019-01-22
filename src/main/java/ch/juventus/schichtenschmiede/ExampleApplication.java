package ch.juventus.schichtenschmiede;
/*
import ch.juventus.schichtenschmiede.persistency.entity.EmployeeOld;
import ch.juventus.schichtenschmiede.persistency.entity.RoleOld;
import ch.juventus.schichtenschmiede.persistency.entity.ShiftOld;
import ch.juventus.schichtenschmiede.persistency.entity.ShiftPlanOld;

import ch.juventus.schichtenschmiede.persistency.repository.EmployeeRepository;
import ch.juventus.schichtenschmiede.persistency.repository.RoleRepository;
import ch.juventus.schichtenschmiede.persistency.repository.ShiftPlanRepository;
import ch.juventus.schichtenschmiede.persistency.repository.ShiftRepository;*/
import ch.juventus.schichtenschmiede.persistency.entity.*;
import ch.juventus.schichtenschmiede.persistency.repositoryNew.*;
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

import java.sql.Date;
import java.util.Calendar;

//TODO Auslagern in eigenen Folder aktuell gleicher folder wie repositories
@SpringBootApplication
@EnableSwagger2
public class ExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExampleApplication.class, args);
    }

    @Component
    class initRepositoryCLR implements CommandLineRunner {

        /*
        private final RoleRepository roleRepository;
        private final EmployeeRepository employeeRepository;
        private final ShiftPlanRepository shiftPlanRepository;
        private final ShiftRepository shiftRepository;
        */
        //New Repos
        private final RoleRepository roleRepository;
        private final EmployeeRepository employeeRepository;
        private final ShiftRepository shiftRepository;
        private final DailyScheduleReopistory dailyScheduleReopistory;

        /*
                @Autowired
                public initRepositoryCLR(RoleRepository roleRepository,
                                         EmployeeRepository employeeRepository,
                                         ShiftPlanRepository shiftPlanRepository,
                                         ShiftRepository shiftRepository,
                                         RoleRepository2 roleRepository2, EmployeeRepository2 employeeRepository2, ShiftPlanRepository2 shiftPlanRepository2, ShiftRepository2 shiftRepository2, DailyScheduleReopistory dailyScheduleReopistory) {
                    this.roleRepository = roleRepository;
                    this.employeeRepository = employeeRepository;
                    this.shiftPlanRepository = shiftPlanRepository;
                    this.shiftRepository = shiftRepository;
                    this.roleRepository2 = roleRepository2;
                    this.employeeRepository2 = employeeRepository2;
                    this.shiftPlanRepository2 = shiftPlanRepository2;
                    this.shiftRepository2 = shiftRepository2;
                    this.dailyScheduleReopistory = dailyScheduleReopistory;
                }
                */
        @Autowired
        public initRepositoryCLR(RoleRepository roleRepository,
                                 EmployeeRepository employeeRepository,
                                 ShiftRepository shiftRepository,
                                 DailyScheduleReopistory dailyScheduleReopistory) {
            this.roleRepository = roleRepository;
            this.employeeRepository = employeeRepository;
            this.shiftRepository = shiftRepository;
            this.dailyScheduleReopistory = dailyScheduleReopistory;
        }

        @Bean
        public WebMvcConfigurer corsConfigurer() {
            return new WebMvcConfigurerAdapter() {
                @Override
                public void addCorsMappings(CorsRegistry registry) {
                    //add delete to cors ...
                    //registry.addMapping("/**").allowedOrigins("https://schichtenschmiede-juventus.scapp.io").allowedMethods("GET", "POST","PUT");
                    registry.addMapping("/**").allowedOrigins("http://localhost:3000").allowedMethods("GET", "POST", "PUT");
                }
            };
        }

        @Override
        public void run(String... strings) throws Exception {

            Role kitchen = new Role(true, "Küche");

            Role service = new Role(true, "Service");

            roleRepository.save(kitchen);
            roleRepository.save(service);

            Employee employee1 = new Employee(true,"Tim", "Taylor", 20, kitchen);
            Employee employee2 = new Employee(true,"Tom", "Nox", 100, service);


            employeeRepository.save(employee1);
            employeeRepository.save(employee2);

            Shift shift1 = new Shift(true,"Morgen", 8, 12, true, true, true, false, false, false, false, 3, service);
            Shift shift2 = new Shift(true,"Mittag", 14, 19, true, false, true, true, false, false, false, 13, kitchen);
            Shift shift3 = new Shift(true,"Nacht", 21, 24, false, false, true, false, true, false, false, 5, service);

            shiftRepository.save(shift1);
            shiftRepository.save(shift2);
            shiftRepository.save(shift3);

            java.util.Date date = new java.util.Date();
            Date sqlDate = new Date(date.getTime());
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

            DailySchedule dailySchedule11 = new DailySchedule(true, new Date(cal.getTime().getTime()), shift1);
            DailySchedule dailySchedule12 = new DailySchedule(true, new Date(cal.getTime().getTime()), shift2);
            DailySchedule dailySchedule13 = new DailySchedule(true, new Date(cal.getTime().getTime()), shift3);
            cal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
            DailySchedule dailySchedule14 = new DailySchedule(true, new Date(cal.getTime().getTime()), shift2);
            cal.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
            DailySchedule dailySchedule15 = new DailySchedule(true, new Date(cal.getTime().getTime()), shift1);
            cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
            DailySchedule dailySchedule16 = new DailySchedule(true, new Date(cal.getTime().getTime()), shift3);

            dailySchedule11.addEmployee(employee2);
            dailySchedule12.addEmployee(employee1);
            dailySchedule13.addEmployee(employee2);
            dailySchedule14.addEmployee(employee2);
            dailySchedule15.addEmployee(employee2);
            dailySchedule16.addEmployee(employee2);

            dailyScheduleReopistory.save(dailySchedule11);
            dailyScheduleReopistory.save(dailySchedule12);
            dailyScheduleReopistory.save(dailySchedule13);
            dailyScheduleReopistory.save(dailySchedule14);
            dailyScheduleReopistory.save(dailySchedule15);
            dailyScheduleReopistory.save(dailySchedule16);






            /*RoleOld kitchen = new RoleOld("Küche", true);
            RoleOld service = new RoleOld("Service", true);

            roleRepository.save(kitchen);
            roleRepository.save(service);

            EmployeeOld employee1 = new EmployeeOld("Tim", "Taylor", 20);
            EmployeeOld employee2 = new EmployeeOld("Tom", "Nox", 100);

            employee1.setRole(kitchen);
            employee2.setRole(service);

            employeeRepository.save(employee1);
            employeeRepository.save(employee2);

            ShiftOld shift1 = new ShiftOld("Morgen", 8, 12, true, true, true, false, false, false, false, "ms", true, 3);
            ShiftOld shift2 = new ShiftOld("Mittag", 14, 19, true, false, true, true, false, false, false, "ms", true, 13);
            ShiftOld shift3 = new ShiftOld("Nacht", 21, 24, false, false, true, false, true, false, false, "ns", true, 5);

            shift1.setRole(service);
            shift2.setRole(kitchen);
            shift3.setRole(service);
            shiftRepository.save(shift1);
            shiftRepository.save(shift2);
            shiftRepository.save(shift3);

            ShiftPlanOld shiftplan2 = new ShiftPlanOld(11, 2018, true);
            shiftplan2.setShiftOld(shift1);



            shiftPlanRepository.save(shiftplan2);

            employee1.addShiftplan(shiftplan2);
            employee2.addShiftplan(shiftplan2);


            employeeRepository.save(employee1);
            employeeRepository.save(employee2);*/


        }
    }
}
