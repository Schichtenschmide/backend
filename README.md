# Install cloudfoundry cli
https://github.com/cloudfoundry/cli

https://github.com/cloudfoundry/cli#downloads

# Swisscom plugin
https://github.com/swisscom/appcloud-cf-cli-plugin

Run 
`cf install-plugin -r CF-Community "Swisscom Application Cloud"`

# Login
Online: Go to https://console.developer.swisscom.com <br/>
Command line tool: cf login -u user@example.com -p MySecretPassword -a api.lyra-836.appcloud.swisscom.com

# Deployment

Edit the ExampleApplication and comment in the production URL and comment out the localhost URL
```
registry.addMapping("/**").allowedOrigins("https://schichtenschmiede-juventus.scapp.io").allowedMethods("GET", "POST","PUT", "DELETE");
//registry.addMapping("/**").allowedOrigins("http://localhost:3000").allowedMethods("GET", "POST","PUT", "DELETE");
 ```   
 Edit the application.properties and add your credentials that your DB provided (Service: MariaDB --> go to youService/Service Keys: Create Service key...)<br/>
        spring.jpa.hibernate.ddl-auto=create-drop --> should be configured to your liking... <br/>
        Every time the instance is restarted it drops the DB and recreates all the tables, you loose all the data that was persisted!!
  ```  
 spring.jpa.spring.jpa.database-platform=org.hibernate.dialect.MySQL5Dialect
 spring.jpa.hibernate.ddl-auto=create-drop
 spring.datasource.url=jdbc:mysql://localhost:3306/jpademo
 spring.datasource.username=jpauser
 spring.datasource.password=password    
  ```   