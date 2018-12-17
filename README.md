# MySQL mit Spring Data JPA/Hibernate

## API documentation with Swagger
The documentation for the Backend API can be found under <br/>
localhost: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) <br/>
online: [https://hello-world-jpa-ch-p.scapp.io/swagger-ui.html](https://hello-world-jpa-ch-p.scapp.io/swagger-ui.html)
## How to use the local instance for development
Start the application

    mvn spring-boot:run
    
You can now call the application under the following URLs: 
- http://localhost:8080/employees
- http://localhost:8080/roles
- http://localhost:8080/shifts
- http://localhost:8080/shiftplans

### MySQL konfigurieren
Prerequisite for running the application locally is an installation of a MySQL Server with root access.   
First, a new database and a technical user must be created within the database, so much so that your backend can access it.

    $ mysql -u root -p
    Enter password: 
    ...
    mysql> 
    GRANT ALL PRIVILEGES ON *.* TO 'jpauser'@'localhost' IDENTIFIED BY 'password';
    CREATE DATABASE jpademo;

The name of the database and standard port 3306 results in the following URL: `jdbc:mysql://localhost:3306/jpademo`.

Now MySQL can be configured for Spring Data.  
To do this, add the following lines in `src/main/resources/application.properties`:

    spring.jpa.hibernate.ddl-auto=create-drop
    spring.datasource.url=jdbc:mysql://localhost:3306/jpademo
    spring.datasource.username=jpauser
    spring.datasource.password=password
    
Now in `pom.xml` you have to replace the dependency of the H2 database with the one for MySQL:

    <dependency>
       <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
    </dependency>

### Launch the application and inspect MySQL

Restart the application:

    mvn spring-boot:run
    
With  `spring.jpa.hibernate.ddl-auto = create-drop`, the application will create the necessary tables in MySQL 
and it will fill it with initial data as before.

Connect (while the application is running) to MySQL and look at the contents of the tables:

    $ mysql -u jpauser -p
    Enter password: 
    ...
    mysql> use jpademo;  
    mysql> select * from employee;
    
### Final configuration

At the moment the schema is deleted after the application is closed. It would be nice if the data were stored permanently.  
  
Initially, the schema should be created at the start of the application, but not deleted at the end of the application.
In `src/main/resources/application.properties`, adjust the following line:

    spring.jpa.hibernate.ddl-auto=create
    
Restart the application and then stop it again (so that the tables are created,
but not deleted again).  

Now change the line again (so that tables are no longer created when the application is started):  

    spring.jpa.hibernate.ddl-auto=none
    
In the class `ExampleApplication`, comment out the entire inner class` initRepositoryCLR` so that
no new data is inserted when the application is started.

At the next start of the application, tables will no longer be created and at the end of the application,
no more tables deleted.

# Deployment to Cloud
1. Install cloudfoundry cli  
Go to the following URL below to install the cloudfoundry client
[https://github.com/cloudfoundry/cli](https://github.com/cloudfoundry/cli)

## Swisscom plugin
On top of that we need to install the Swisscom client plugin. Please just run the following command below  
[https://github.com/swisscom/appcloud-cf-cli-plugin](https://github.com/swisscom/appcloud-cf-cli-plugin)

`cf install-plugin -r CF-Community "Swisscom Application Cloud"`

## Login to Swisscom Cloud Foundry
Online: Go to https://console.developer.swisscom.com <br/>
Command line tool: `cf login  -a api.lyra-836.appcloud.swisscom.com -u user@example.com`

## Prepare push to Cloud Foundry
Edit the `src\main\java\ch\juventus\example\ExampleApplication.java` and comment in the production URL and comment out the localhost URL
```
registry.addMapping("/**").allowedOrigins("https://schichtenschmiede-juventus.scapp.io").allowedMethods("GET", "POST","PUT", "DELETE");
//registry.addMapping("/**").allowedOrigins("http://localhost:3000").allowedMethods("GET", "POST","PUT", "DELETE");
 ```   
 Edit the application.properties and add your credentials that your DB provided (Service: MariaDB --> go to yourService/Service Keys: Create Service key...)<br/>
        spring.jpa.hibernate.ddl-auto=create-drop --> should be configured to your liking... <br/>
        Every time the instance is restarted it drops the DB and recreates all the tables, you loose all the data that was persisted!!
  ```  
 spring.jpa.spring.jpa.database-platform=org.hibernate.dialect.MySQL5Dialect
 spring.jpa.hibernate.ddl-auto=create-drop
 spring.datasource.url=jdbc:mysql://localhost:3306/jpademo
 spring.datasource.username=jpauser
 spring.datasource.password=password    
  ```   
  to use with MariaDB with cloud foundry use  
  spring.jpa.spring.jpa.database-platform=org.hibernate.dialect.MariaDB10Dialect
  ### run package
  Build the jar file
  ```
  mvn package
  ```
  ### Push
  Login to Cloudfoundry --> <br/>
  `cf login -a api.lyra-836.appcloud.swisscom.com -u user@example.com -p MySecretPassword` <br/>
  chose your space --> <br/>
  `cf push`
  
  ### Only after (first) successful Push
  Bind Service to Application in CloudFoundry<br/>
  Service: MariaDB --> go to yourService/Apps --> Bind to App --> yourBackend<br/>
  or <br/>
  yourBackend/Services --> Bind to Service --> yourService
  
   # Sonar
   This project uses the SonarCloud to validate the code. Please visit the link below to see the analysis<br/>
     [Sonar Schichtenschmiede](https://sonarcloud.io/organizations/schichtenschmiede/projects)  <br/>
     ![quality gate](https://sonarcloud.io/api/project_badges/measure?project=Schichtenschmiede_backend&metric=alert_status)
     1. Install the sonar-client  
     [How to install SonarQube Scanner](https://docs.sonarqube.org/display/SCAN/Analyzing+with+SonarQube+Scanner)
     
    2. In order to push the code to the sonar cloud for analysis, please use the following command below
    ```
     sonar-scanner
    ```