FROM mysql:8.0

ENV MYSQL_ROOT_PASSWORD=root
ENV MYSQL_DATABASE=luz_colectiva
ENV MYSQL_USER=luzcolectiva
ENV MYSQL_PASSWORD=luzcolectiva

EXPOSE 3306

COPY init/ /docker-entrypoint-initdb.d/
