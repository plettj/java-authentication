```
Protocol: assignment_2

Types: Agent C,s;
       Number Payload, sessionToken;
       Symmetric_key KCS;
       Function sk,pk

Knowledge: C: s,pk(s),sk(C,s);
	   s: s,C,pk(s),inv(pk(s)),sessionToken,sk(C,s);
   where C!=s

# 1. Get public key of trusted Server s
# 2. Hash our password and encrypt it with pk(s), include KCS in this method
# 3. Decrypt the load with inv(pk(s)) and verify password, then extract KCS
# 4. Return sessionToken encrypted behind KCS
# 5. If authenticated, then C sends secret Payload, sessionToken to s encrypted with KCS

# This theoretically accomplishes the 3 goals; unsure where we're going wrong.

Actions:
# 1. Public key of s is already known by the client C
# 2. sk(C,s) will represent the hashed + salted password of C
C->s: {KCS, sk(C,s)}pk(s)
# 3. This happens automatically with OFMC
# 4: server returns sessonToken along with shared key to demonstrate
#   to OFMC that the password is valid
s->C: {|sk(C,s),sessionToken|}KCS
C->s: {|sessionToken, Payload|}KCS

Goals:
# Secure connection established between C and s
# C authenticated by s with hashed password
# Secure delivery of Payload from C to s
s authenticates C on sessionToken
Payload secret between s,C
```
