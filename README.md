# spring-boot-kafka-demo
spring-boot kafka demo

2 separate spring-boot projects: BE + FE.
Model is separated into commonsdemo lib mvn project.
Model is simplified because it is just for demonstration purposes.
FE also simplified and has only just 1 controller(rest) and just 1 post mapping.

Aim here is to demonstrate blocking queue. FE - Kafka - BE.

FE rest api recieves post request(json) and sends it to the request topic,
and waits for the reply from reply topic(blocking, waits for certain amount time).

BE listens on request topic and saves data to db and sends back a respond to the reply topic.

