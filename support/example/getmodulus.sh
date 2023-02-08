#!/bin/bash
echo -e "\n### PRINT ALL INFO ################################################"
openssl rsa -in $1 -text -noout 
echo -e "\n### PRINT MODULUS #################################################"
openssl rsa -in $1 -text -noout | sed -n '/modulus/,/publicExponent/{//b;p}' 
echo -e "\n### PRINT REMOVE NEWLINE AND SPACE ################################"
openssl rsa -in $1 -text -noout | sed -n '/modulus/,/publicExponent/{//b;p}' | tr -d '\n ' 
echo -e "\n### PRINT REMOVE FIRST 00 #########################################"
openssl rsa -in $1 -text -noout | sed -n '/modulus/,/publicExponent/{//b;p}' | tr -d '\n ' | cut -b 4- 
echo -e "\n### PRINT CONVERT FROM HEX ########################################"
openssl rsa -in $1 -text -noout | sed -n '/modulus/,/publicExponent/{//b;p}' | tr -d '\n ' | cut -b 4- | xxd -r -p 
echo -e "\n### PRINT CONVERT TO BASE64 #######################################"
openssl rsa -in $1 -text -noout | sed -n '/modulus/,/publicExponent/{//b;p}' | tr -d '\n ' | cut -b 4- | xxd -r -p | base64 | tr -d '\n'

echo -e "\n"
