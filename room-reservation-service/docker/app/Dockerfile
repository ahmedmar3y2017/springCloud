
FROM openjdk:17
COPY example /example
WORKDIR /example
RUN javac -cp lombok-1.18.36.jar test.java
RUN rm test.java
CMD ["java", "test" , "-cp" , "lombok-1.18.36.jar"]
