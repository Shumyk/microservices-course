# command to run Config Server in container
docker run -d -p 8012:8012 -e 'spring.rabbitmq.host=172.17.0.2' -e 'spring.profiles.active=git' shumyk/photoapp-config-server

# command to run Discover Service (Eureka) in container
docker run -p 8010:8010 -e CONFIG_SERVER_URL=http://172.31.39.206:8012 shumyk/photoapp-discovery-service
