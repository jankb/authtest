


### Generate private key?
> openssl genrsa -out private.pem 2048

### Get modulo from private key.
> openssl rsa -in priv.key -modulus -noout | cut -b 9- | xxd -r -p | basenc --base64url

### Other stuff

openssl rsa -inform PEM -pubin -in jwtRS256.key.pub -text -noout

openssl rsa -in priv -pubout 