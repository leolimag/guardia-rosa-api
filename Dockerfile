FROM openjdk:8
ADD ./guardia-rosa-1.jar guardia-rosa-1.jar
ENTRYPOINT ["java", "-jar", "guardia-rosa-1.jar"]