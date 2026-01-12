# 基础镜像：Java8（适配SpringBoot2.7，也可用openjdk:11-jre-slim/alpine）
FROM eclipse-temurin:21-jre-alpine
# Alpine镜像缺少时区，需添加
RUN apk add --no-cache tzdata
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 设置工作目录
WORKDIR /app

# 将本地jar包复制到容器内（注意jar包名称要和打包后的一致）
COPY target/gateway-service-1.0-SNAPSHOT.jar /app/gateway-service.jar

# 暴露服务端口（gateway-service用19000）
EXPOSE 19000

# 容器启动命令（运行jar包）
ENTRYPOINT ["java", "-jar", "-Xms256m", "-Xmx512m", "/app/gateway-service.jar"]