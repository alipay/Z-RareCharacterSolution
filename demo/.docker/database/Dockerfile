FROM mysql:8
ENV LANG zh_CN.UTF-8
ENV MYSQL_DATABASE rare_characters
ENV MYSQL_ROOT_PASSWORD root123456
ADD init.sql ./docker-entrypoint-initdb.d
EXPOSE 3306