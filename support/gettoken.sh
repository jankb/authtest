!#/bin/bash
echo -n "Authorization: Bearer " > token_file
curl -X POST localhost:8080/login/issuer$1 -H "Content-Type: application/json" -d @cred.data | jq -r '.token' >> token_file

