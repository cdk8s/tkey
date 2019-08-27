FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER cdk8s cdk8s@qq.com

VOLUME /tmp

ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ADD ./target/tkey-sso-server-1.0.0.jar /app.jar
RUN bash -c 'touch /app.jar'

EXPOSE 9091
EXPOSE 19091
ENTRYPOINT ["java", "-jar", "-Xms1024m", "-Xmx1024m", "-XX:MetaspaceSize=124m", "-XX:MaxMetaspaceSize=224M"  ,"/app.jar"]
