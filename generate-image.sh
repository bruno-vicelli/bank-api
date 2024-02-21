#!/bin/bash

mvn clean package $@

docker build -t bank-api .