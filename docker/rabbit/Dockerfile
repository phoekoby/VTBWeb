FROM bitnami/rabbitmq:latest
ADD definitions.json /etc/rabbitmq/
RUN rabbitmqctl import_definitions /etc/rabbitmq/definitions.json
EXPOSE 4369 5672 25672 15672
