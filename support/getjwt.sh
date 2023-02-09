#!/bin/bash
curl -vvv -X POST localhost:8080/login/issuer$1 -H "Content-Type: application/json" -d @cred.data

