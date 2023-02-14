#!/bin/bash
# Gets the jwt from either A or B and writes to file 'token_file' that can later be used to
# access endpoints (/accept[A|B|AB|BA]) using e.g curl.
# curl localhost:8080/acceptA -H @token_file

# Usage:
#  gettoken A
echo -n "Authorization: Bearer " > token_file
curl -X POST localhost:8080/login/issuer$1 -H "Content-Type: application/json" -d @cred.data | jq -r '.token' >> token_file

