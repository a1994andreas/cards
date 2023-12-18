# Cards Application

## Solution
- Used java 17, Spring 3.2.0 and a dockerized MYSQL instance
- Endpoints are documented using swagger: http://localhost:8081/swagger-ui (username: admin@logicea.com , password: 1234)
- The endpoints can be used with basic authentication through swagger but also support JWT tokens that can be generated
using /authenticate endpoint and then can be used as a Bearer tokens

## Run Details
To run you could use
> ./run.sh
It will build/run the application and start the MYSQL instance
Prerequisites: maven, Java 17 and a running docker daemon 
