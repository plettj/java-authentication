# Zip 1 - Authentication - Printer Authentication Lab

This is the printer authentication lab **Part 1** in Java for DTU course [02239 - Data Security](https://kurser.dtu.dk/course/02239).

Note that since we built our project with a forward-thinking mindset, this zip file contains some infrastructure to support ACLs and role-based authorization. However, we have updated `server/Printer.java.validateRequest` to not use this infrastructure, for your convenience during testing.

## Usage

All commands should be from the root of this (extracted) zip file.

```bash
cd zip-1-authentication
```

1. Run the print server instance:

```bash
gradle runServer
```

2. Run your own print client instance:

```bash
gradle runClient
```

3. Input a username and password in the client, and watch the server logs at `/app/src/logs`!

It is currently not possible to input requested RMIs from the client upon login; instead, the client terminal simply terminates.

However, the infrastructure is there, so please change

## Code Structure

Our code is partitioned into `server/`, `client/`, and a shared `authentication/` folder.

Each of `server/` and `client/` have a:

- `PrinterMain.java` - This is where our printer helpers begin!
- `Printer.java` - The main holder of all logic on that instance of the client or server.
- `Authentication.java` - The class that handles all authentication logic for each printer.

Additionally, `server/` contains:

- `Users.java` - Represents our (statically generated) user objects with `username`, `password`, and `Role`.
- `Sessions.java` - Represents our currently active login sessions.

Lastly, these are the shared utilities in the `authentication/` folder.

- `Hashing.java` - Contains utilities for securely hashing and comparing a plaintext password.
- `Session.java` - Contains the object for a serializable individual session, containing `username` and `sessionToken`.
- `VerificationResult.java` - Containes the object for a serializable result of verification, for notifying the client.

> Note that each _client_ printer instance can only log in as one user at a time.

## Authentication Process

Our authentication process is pretty straightforward.

1. Users are already stored statically on the server.

Users are statically stored on the server under `Printer.Authentication.Users`, and their data is `{username, password, role}` where `password` is hashed under [`SHA3-256`](https://emn178.github.io/online-tools/sha3_256.html).

2. A client **C** establishes a connection with the server.

When the connection is established, the server exposes two methods, `getPublicKey` and `authenticateUser`. The client calls `getPublicKey` to get the public key.

3. **C** sends a request to log into the server.

Using the public key from step 2, **C** encrypts a login request of the format `{username, password, symmetricKey}`, where `symmetricKey` is what the server and **C** will forever use for further communications. (Note, symmetric key encryption not fully implemented)

4. Server receives an encrypted login request.

Server decrypts the login request with its private key, and sends it to the `Server.Authenticate` instance which proceeds to either find a corresponding session or create a session.

5. Server authenticates a user.

First, our `Server.Authenticate` checks for an existing, non-expired session. If there is one, return the `Role` of the user and let the server handle the rest. If not, we need to create a session. But if so, we return the `sessionToken` to the user that made the request, using the `symmetricKey`.

6. Server creates a session for a user.

`Server.Authenticate` creates a new `session` with value `{username, sessionToken, expirationTime, symmetricKey}`. Return the `sessionToken` to the user that made the request, using the `symmetricKey`.

7. **C** sends an RMI request.

**C** can now send RMI requests, of value `{functionName, parameter[], username, sessionToken}` where `sessionToken` is encrypted with the `symmetricKey`.

8. Server verifies **C**'s RMI requests

This is done against `sessionToken` of course, but in future zip files you'll find additional checks against our ACL.
