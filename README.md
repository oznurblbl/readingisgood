# readingisgood
authentication replaced with jwt based authentication
# READINGISGOOD
It is a backend service developed by using SpringBoot and MySQL. It runs on an embedded Tomcat via port 8181.
To begin, you must generate a Bearer token to send a request to the /authenticate endpoint, and then use that token to send requests to other controller endpoints. Otherwise, an UnAuthorized exception is thrown.
## Controller
- CustomerController (Persists a new customer, Query customer's orders)
- BookController (Persist a new book, Update book stock)
- OrderController (Persist anew Order, Query order detail, Query orders by date interval)
- StaticsController (Query customer's monthly statics data)
## Used Technologies and Libraries
- Java 11
- SpringBoot 2.7.7-SNAPSHOT
- MySQL
- Apache Maven
- Gson
- Lombok
## Compile
mvn clean
mvn install
## Run
mvn spring-boot:run

