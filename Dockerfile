FROM python:3.5

# Install packages
RUN set -ex; \
    apt-get update; \
    apt-get -y -qq install postgresql
    
RUN apk --update add 'py-pip==8.1.2-r0' && \
pip install 'docker-compose==1.8.0'
 
ADD . /app
WORKDIR /app
RUN pip install -r requirements.txt
