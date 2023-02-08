https://swagger.io/docs/specification/authentication/openid-connect-discovery/

https://datatracker.ietf.org/doc/html/rfc7517#appendix-A.1

https://www.iana.org/assignments/jose/jose.xhtml#web-key-types

https://datatracker.ietf.org/doc/rfc7518/

https://www.jhanley.com/blog/security-key-pairs-and-private-public-keys/

https://stackoverflow.com/questions/55974769/get-modulus-of-ssh-rsa-public-key

https://openid.net/specs/openid-connect-discovery-1_0.html

https://www.rfc-editor.org/rfc/rfc8693.html#name-scope-scopes-claim

https://serverfault.com/questions/9708/what-is-a-pem-file-and-how-does-it-differ-from-other-openssl-generated-key-file

openssl rsa -inform PEM -pubin -in jwtRS256.key.pub -text -noout

openssl rsa -in priv -pubout 
