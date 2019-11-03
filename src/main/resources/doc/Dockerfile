FROM docker.io/java

MAINTAINER root

COPY zuulserver.jar /app.jar

# ENV JAVA_OPTS="\
# -server \
# -Xmx2g \
# -Xms2g "

EXPOSE 10001
ENTRYPOINT ["java","-jar","/app.jar"]