# 基于JAVA镜像
FROM openjdk:21-jdk-alpine
# 设置工作目录
WORKDIR /transaction
# 复制项目的 JAR 文件到容器中
COPY target/banktmsystemservice-1.0-SNAPSHOT.jar transaction.jar
# 暴露应用程序的端口
EXPOSE 8080
# 运行应用程序
CMD ["java", "-jar", "transaction.jar"]