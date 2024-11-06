# Printer Authentication Lab

This is the printer authentication lab in Java for DTU course #####.

## Code Structure

Our code is partitioned into `server/`, `client/`, and a shared `authentication/` folder.

Each of `server/` and `client/` have a:

- `PrinterMain.java` - This is where our printer helpers begin!
- `Printer.java` - The main holder of all logic on that instance of the client or server.
- `Authentication.java` - The class that handles all authentication logic for each printer.

Additionally, `server/` contains:

- `Users.java` - Represents our (statically generated) user objects with `username`, `password`, and `Role`.
- `Sessions.java` - Represents our currently active login sessions.

Note that each _client_ printer can only log in as one user at a time.

## Authentication Process

Our authentication process is pretty straightforward.

1. Users are already stored statically on the server.

Users are statically stored on the server under `Printer.Authentication.Users`, and their data is `{username, password, role}` where `password` is hashed under [`SHA3-256`](https://emn178.github.io/online-tools/sha3_256.html).

2. A client **C** establishes a connection with the server.

When the connection is established, the server exposes two methods, `getPublicKey` and `authenticateUser`. The client calls `getPublicKey` to get the public key.

3. **C** sends a request to log into the server.

Using the public key from step 2, **C** encrypts a login request of the format `{username, password, symmetricKey}`, where `symmetricKey` is what the server and **C** will forever use for further communications.

4. Server receives an encrypted login request.

Server decrypts the login request with its private key, and sends it to the `Server.Authenticate` instance which proceeds to either find a corresponding session or create a session.

5. Server authenticates a user.

First, our `Server.Authenticate` checks for an existing, non-expired session. If there is one, return the `Role` of the user and let the server handle the rest. If not, we need to create a session. But if so, we return the `sessionToken` to the user that made the request, using the `symmetricKey`.

6. Create a session for a user.

`Server.Authenticate` creates a new `session` with value `{username, sessionToken, expirationTime, symmetricKey}`. Return the `sessionToken` to the user that made the request, using the `symmetricKey`.

7. **C** sends an RMI request.

**C** can now send RMI requests, of value `{functionName, parameter[], username, sessionToken}` where `sessionToken` is encrypted with the `symmetricKey`.

## Usage

You'll want to be in the `Assignment_2` folder for this.

```bash
cd Assignment_2
```

1. Run the print server instance:

```bash
gradle runServer
```

2. Run your own print client instance:

````bash
gradle runClient

To run the print server instance from `ClassWork`:

```bash
javac Assignment_2/src/*.java && java Assignment_2.src.PrintServerMain
````

To then afterwards run the print client

```bash
java Assignment_2.src.PrintClient
```
