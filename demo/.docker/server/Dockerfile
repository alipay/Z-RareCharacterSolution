# jdk版本
FROM moxm/java:1.8-full

# 临时文件目录
VOLUME /tmp
ADD rarecharacterdemo-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8088

# ENTRYPOINT 执行项目 app.jar。为了缩短 Tomcat 启动时间，添加一个系统属性指向 "/dev/urandom" 作为 Entropy Source.
ENTRYPOINT ["java","-jar","app.jar","-Xms512m","-Xmx1024m","-Djava.security.egd=file:/dev/./urandom"]
