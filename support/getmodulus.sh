#!/bin/bash
# Get the modulus from the private key. The modulus will then be used in jwks.json file in the "n" element.
# See jwks.json files in support/issuerA and B

# Usage:
#  getmodulus "priv.key"
openssl rsa -in $1 -modulus -noout | cut -b 9- | xxd -r -p | basenc --base64url
echo -e "\n"
