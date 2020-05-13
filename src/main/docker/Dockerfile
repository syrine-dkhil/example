FROM amazonlinux:latest

WORKDIR /tmp
RUN yum install -y java-11-amazon-corretto-headless unzip

ADD target/example.zip /tmp
RUN unzip example.zip -d /opt/ol && rm /tmp/example.zip

EXPOSE 9080 9443

CMD ["/opt/ol/wlp/bin/server", "run", "sampleAppServer"]
