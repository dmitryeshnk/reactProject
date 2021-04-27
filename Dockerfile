FROM buildo/h2database:latest
COPY src/database/database.mv.db /h2-data/database.mv.db

FROM maven:latest AS build  
COPY src /usr/src/24_react/src  
COPY pom.xml /usr/src/24_react 
RUN mvn -f /usr/src/24_react/pom.xml clean install -Dmaven.test.skip=true

FROM tomcat:9.0.39
COPY --from=build usr/src/24_react/target/24_react.war /usr/local/tomcat/webapps/ROOT.war
ADD src/database/tomcat-users.xml /usr/local/tomcat/conf/tomcat-users.xml
EXPOSE 8086
CMD ["catalina.sh", "run"]
