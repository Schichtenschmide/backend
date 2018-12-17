# MySQL mit Spring Data JPA/Hibernate

# Swagger
Die Dokumentation der API ist unter http://localhost:8080/swagger-ui.html

# Local instance
Starte die Applikation:

    mvn spring-boot:run
    
Du kannst nun die Applikation unter folgenden URLs aufrufen: 
- http://localhost:8080/employees
- http://localhost:8080/roles
- http://localhost:8080/shifts
- http://localhost:8080/shiftplans

### MySQL konfigurieren

Vorraussetzung für das Lokale ausführen ist eine Installation von MySQL mit Root-Zugriff.

Zunächst muss eine neue Datenbank und ein technischer User für den Zugriff angelegt werden.

    $ mysql -u root -p
    Enter password: 
    ...
    mysql> 
    GRANT ALL PRIVILEGES ON *.* TO 'jpauser'@'localhost' IDENTIFIED BY 'password';
    CREATE DATABASE jpademo;

Aus dem Namen der Datenbank und Standard-Port 3306 ergibt sich folgende URL: `jdbc:mysql://localhost:3306/jpademo`.

Nun lässt sich MySQL für Spring Data konfigurieren. 
Füge dazu folgende Zeilen in `src/main/resources/application.properties` hinzu:

    spring.jpa.hibernate.ddl-auto=create-drop
    spring.datasource.url=jdbc:mysql://localhost:3306/jpademo
    spring.datasource.username=jpauser
    spring.datasource.password=password
    
Nun muss in `pom.xml` noch die Dependency der H2 Datenbank mit der für MySQL ersetzt werden: 

    <dependency>
       <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
    </dependency>

### Applikation starten und MySQL inspizieren

Starte die Applikation erneut:

    mvn spring-boot:run
    
Durch `spring.jpa.hibernate.ddl-auto=create-drop` wird die Applikation die nötigen Tabellen in MySQL
anlegen und wie zuvor mit initialen Daten füllen.

Verbinde Dich (während die Applikation läuft) mit MySQL und schau den Inhalt der Tabellen an:

    $ mysql -u jpauser -p
    Enter password: 
    ...
    mysql> use jpademo;  
    mysql> select * from employee;
    
### Finale Konfiguration

Derzeit wird das Schema nach Beenden der Applikation wieder gelöscht. Schön wäre, wenn die Daten dauerhaft abgelegt wären.

Zunächst soll das Schema beim Applikationsstart zwar angelegt, aber bei Applikationsende nicht mehr gelöscht werden.
In `src/main/resources/application.properties`, passe folgende Zeile an:

    spring.jpa.hibernate.ddl-auto=create
    
Starte die Applikation neu und beende sie dann wieder (sodass die Tabellen zwar angelegt,
aber nicht wieder gelöscht werden). 

Nun ändere die Zeile erneut (damit nun auch bei Applikationsstart keine Tabellen mehr angelegt werden):

    spring.jpa.hibernate.ddl-auto=none
    
In der Klasse `ExampleApplication`, kommentiere die gesamte innere Klasse `initRepositoryCLR` aus, sodass
bei Applikationsstart keine neuen Daten mehr eingefügt werden.

Beim nächsten Start der Applikation werden nun keine Tabellen mehr frisch angelegt und bei Applikationsende
keine Tabellen mehr gelöscht.

# Deployment to Cloud
## Install cloudfoundry cli
https://github.com/cloudfoundry/cli

https://github.com/cloudfoundry/cli#downloads

## Swisscom plugin
https://github.com/swisscom/appcloud-cf-cli-plugin

Run 
`cf install-plugin -r CF-Community "Swisscom Application Cloud"`

## Login
Online: Go to https://console.developer.swisscom.com <br/>
Command line tool: cf login -u user@example.com -p MySecretPassword -a api.lyra-836.appcloud.swisscom.com

## Prepare push to Cloud Foundry
Edit the ExampleApplication and comment in the production URL and comment out the localhost URL
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
   
   Das Projekt nutzt die SonarCloud um den Code zu überprüfen.  
   [Sonar Schichtenschmiede](https://sonarcloud.io/organizations/schichtenschmiede/projects)
   ![quality gate](https://sonarcloud.io/api/project_badges/measure?project=Schichtenschmiede_backend&metric=alert_status)
   ## install sonar-client
   https://docs.sonarqube.org/display/SCAN/Analyzing+with+SonarQube+Scanner
   
   ## run to push to the sonar cloud
   ´´´
   sonar-scanner
   ´´´

