#!/bin/bash
# Dumps jwt for issuer to terminal.
# NB: file 'cred-data' contains username and password for the fictional user.

# Usage:
#  getjwt ( A | B )
curl -vvv -X POST localhost:8080/login/issuer$1 -H "Content-Type: application/json" -d @cred.data

