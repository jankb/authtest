#!/bin/bash

openssl rsa -in $1 -modulus -noout | cut -b 9- | xxd -r -p | basenc --base64url
echo -e "\n"
