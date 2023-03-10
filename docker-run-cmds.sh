# command to run Config Server in container
docker run -d -p 8012:8012 -e 'spring.rabbitmq.host=172.17.0.2' shumyk/photoapp-config-server

# command to run Discover Service (Eureka) in container
docker run -d -p 8010:8010 -e CONFIG_SERVER_URL='http://172.31.39.206:8012' shumyk/photoapp-discovery-service

# command to run API-Gateway in container
docker run -d -p 8082:8082 -e CONFIG_SERVER_URL='http://172.31.39.206:8012' shumyk/photoapp-api-gateway

# command to run ElasticSearch in container in the same network as Kibana, with disabled security
docker network create elk-network
docker run -d -v esdata1:/usr/share/elasticsearch/data --name elasticsearch -p 9200:9200 -p 9300:9300 -e 'discovery.type=single-node' -e 'xpack.security.enabled=false' -e 'xpack.security.enrollment.enabled=false' --network elk-network elasticsearch:8.6.1

# command to run Kibana in container in the same network as Elastic, specifying Elastic host
docker run -d --network elk-network -p 5601:5601 -e 'ELASTICSEARCH_HOSTS=http://elasticsearch:9200' kibana:8.6.1

# command to run API-Albums microservice in the docker container with mounting logs files
docker run -d --network host -e CONFIG_SERVER_URL='http://172.31.39.206:8012' -e 'logging.file.name=/api-logs/albums-ws.log' -v /home/ec2-user/api-logs:/api-logs shumyk/photoapp-api-albums

# command to run Logstash for API-Albums in the docker container with mounting volume to service logs
docker run -d --name api-albums-logstash -v /home/ec2-user/api-logs:/api-logs shumyk/photoapp-albums-logstash

# command to run Postgres in the docker container, mounting container data to host directory
docker run -d --name postgres-container -e POSTGRES_PASSWORD=changeme69 -e POSTGRES_USER=photorobot -e POSTGRES_DB=photoapp -p 5432:5432 -v /var/lib/postgresql/data:/var/lib/postgresql/data postgres

# command to run API-Users microservice in the docker container with mounting logs file
docker run -d --name api-users --network host -e CONFIG_SERVER_URL='http://172.31.39.206:8012' -e 'logging.file.name=/api-logs/users-ws.log' -v /home/ec2-user/api-logs:/api-logs shumyk/photoapp-api-users

# command to run Logstash for API-Users in the docker container with mounting volume to service logs
docker run -d --name api-users-logstash -v /home/ec2-user/api-logs:/api-logs shumyk/photoapp-users-logstash
