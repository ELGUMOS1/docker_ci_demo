FROM python:2.7

# Install packages
RUN set -ex; \
    apt-get update; \
    apt-get -y -qq install postgresql
RUN rm /usr/local/bin/docker-compose \
    && curl -L https://github.com/docker/compose/releases/download/${DOCKER_COMPOSE_VERSION}/docker-compose-`uname -s`-`uname -m` > docker-compose \
    &&chmod +x docker-compose \
    && mv docker-compose /usr/local/bin  
ADD . /app
WORKDIR /app
RUN pip install -r requirements.txt
