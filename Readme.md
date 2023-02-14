
Short example of how to authorize in ktor with jwt using two id-providers.
It has two issuers of jwts found at 
>localhost:8080/login/issuerA

and 
>localhost:8080/login/issuerB

There are also four endpoints that accepts jwt's:

Accepts only jwt from issuer A: 
>localhost:8080/acceptA

Accepts only jwt from issuer B:
>localhost:8080/acceptB

Accepts jwt from issuer A and B:
>localhost:8080/acceptAB

Accepts jwt from issuer B and A:
>localhost:8080/acceptBA


#
##### Usage example:

Use ```$ gettoken A``` to get (and write) token for issuer A to ```token_file```.
Then use ```curl``` to query one of the endpoints requires jwt to access.
>$ curl -vvv localhost:8080/acceptA -H @token_file

