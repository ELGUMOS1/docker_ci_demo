FROM python:3.5

# Install packages
RUN set -ex; \
    apt-get update; \
    apt-get -y -qq install postgresql
 
ADD . /app
WORKDIR /app
RUN pip install -r requirements.txt
