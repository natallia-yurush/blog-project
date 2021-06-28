FROM tomcat:9.0.48-jdk16-corretto
COPY target/blog-project-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/blog_project.war
EXPOSE 8080
CMD ["/usr/local/tomcat/bin/catalina.sh", "run"]
