FROM docker.repository.esi.web.com/clientcentral/java:17.0.1-jdk

ARG JAR_FILE=target/Expense-management-0.0.1-SNAPSHOT.jar
#COPY ${JAR_FILE} spring-boot-docker.jar
ADD ${JAR_FILE} Expense-management-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/Expense-management-0.0.1-SNAPSHOT.jar"]
EXPOSE 8888