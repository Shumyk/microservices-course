# command to run Config Server in container
docker run -d -p 8012:8012 -e 'spring.rabbitmq.host=172.17.0.2' shumyk/photoapp-config-server

# command to run Discover Service (Eureka) in container
docker run -d -p 8010:8010 -e CONFIG_SERVER_URL='http://172.31.39.206:8012' shumyk/photoapp-discovery-service

# command to run API-Gateway in container
docker run -d -p 8082:8082 -e CONFIG_SERVER_URL='http://172.31.39.206:8012' shumyk/photoapp-api-gateway

# command to run ElasticSearch in container
docker run -d -v esdata1:/usr/share/elasticsearch/data --name elasticsearch -p 9200:9200 -p 9300:9300 -e 'discovery.type=single-node' elasticsearch:8.6.1
