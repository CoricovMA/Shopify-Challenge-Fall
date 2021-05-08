#!/usr/bin/env bash
mvn clean install
docker build -t shopif-challenge-backend .
docker run -t shopif-challenge-backend -d -p 8080:8080 shopify-challenge-backend