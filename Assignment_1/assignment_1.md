# Assignment 1 - Protocol Security Lab

This `.md` file holds my thought processes and resources during the solving of Assignment 1.

### Useful Commands

Running a file against the ofmc.

```sh
ofmc task1.AnB
```

Running my updated `.AnB` files against my fixes

```sh
ofmc --numSess 2 --ctf task1.AnB task1-fix#.AnB
```

### OFMC Details

sk(A, B) - Shared key of A and B
pw(A, B) - The password of A at server B
pk(A) - The public key of A
inv(K) - The private key that belongs to K.
exp(A) - ?????
{|M|}\_K - Symmetrically encrypt message M with key K.
{M}inv(K) - Signed message M with the private key of K.
h(M) - Cryptographic hash of message M. (h is custom defined).
M1,M2,M3 - Concatenation.

#### Assymetric Encryption

In assymetric encryption (public-key encryption), every agent A has key-pair (K, inv(K)) consisting of a public key K and a private key inv(K).

#### Symmetric Encryption

In symmetric encryption, two or more agents share a secret key K. One can decrypt symmetrically encrypted message {|M|}\_K if they have K.

#### Signatures

Signing is encrypting with private keys. {M}inv(K) is M signed by K's secret key, so it can only be produced by someone who holds the full key pair (K, inv(K)).

#### Nonces and Hashes

A nonce (number-once) is denoted by NK conventionally for K variable. It's a random number only used once.
A hash is denoted by h(M)
