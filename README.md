# Data Security

This repository is a repo for holding my [02239 - Data Security](https://kurser.dtu.dk/course/02239) course materials at DTU.

## OFMC Syntax

`A` - Variable (starts uppercase)
`s` - Constant (starts lowercase)
`sk(A,B)` _or_ `ck(A,B)` - Shared secret key of `A` and `B`
`pw(A,B)` - The password of `A` at server `B`
`pk(A)` - The public key of `A`
`inv(K)` - The private key that belongs to `K`
`exp(g,X)` - Computes `g^X` via the modulo process making it too hard to undo without `X`
`{M}K` - A message `M` encrypted by `K`
`{|M|}K` - Symmetrically encrypt message `M` with key `K`
`{M}inv(K)` - Signed message `M` with the private key of `K`
`h(M)` - Cryptographic hash of message `M` (`h()` is a manually defined function)
`M1,M2,M3` - Concatenation
`A->B:` - `A` is sending a message to `B`; note they are variables so they could be intruders

#### Assymetric Encryption

In assymetric encryption (public-key encryption), every agent `A` has key-pair `(K,inv(K))` consisting of a public key `K` and a private key `inv(K)`.

#### Symmetric Encryption

In symmetric encryption, the same key that encrypts a message also decrypts it. Usually, two agents share a secret key `K`, and an agent can decrypt symmetrically encrypted message `{|M|}K` only if they have `K`.

#### Signatures

Signing is encrypting with private keys. `{M}inv(pk(A))` is `M` signed by `A`'s private key, so it can only be produced by someone who holds the full key pair `(A,inv(pk(A)))`, aka `A`.

#### Nonces and Hashes

A nonce (number-once) is denoted by `NK` conventionally for `K` variable, and it's for ensuring duplicate messages are caught, oftentimes preventing replay attacks. It's a random number only used once.

A hash is denoted by `h(M)`, a manually defined function.
