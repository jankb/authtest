#!/bin/bash
# Create a 2048 bits private key.

# Usage:
#  genrsa <privatekeyfilename>
openssl genrsa -out $1 2048
