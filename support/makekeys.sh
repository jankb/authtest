#!/bin/bash
# Create private key
ssh-keygen -t rsa -P "" -b 4096 -m PEM -f jwtRS256.key

# Create public key from private key.
ssh-keygen -e -m PEM -f jwtRS256.key > jwtRS256.key.pub
