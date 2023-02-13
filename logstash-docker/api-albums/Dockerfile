FROM logstash:8.6.1

RUN rm -f /usr/share/logstash/pipeline/logstash.conf

COPY logstash.conf /usr/share/logstash/pipeline/logstash.conf
COPY logstash.yml config/logstash.yml
