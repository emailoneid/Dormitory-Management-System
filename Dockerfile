FROM tomcat:10.1.20-jre17-temurin-jammy
COPY target/damns.war /usr/local/tomcat/webapps/