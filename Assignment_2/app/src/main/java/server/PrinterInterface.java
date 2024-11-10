package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.PublicKey;

import authentication.VerificationResult;
import authentication.Session;

public interface PrinterInterface extends Remote {

    // Authentication methods //
    PublicKey getPublicKey() throws RemoteException;

    VerificationResult login(byte[] encryptedLoginRequest) throws RemoteException;

    // Printer methods //
    void print(Session session, String filename, String printer) throws RemoteException;

    void queue(Session session, String printer) throws RemoteException;

    void status(Session session, String printer) throws RemoteException;

    void start(Session session) throws RemoteException;

    void stop(Session session) throws RemoteException;

    void restart(Session session) throws RemoteException;

    void topQueue(Session session, String printer, int job) throws RemoteException;

    void setConfig(Session session, String parameter, String value) throws RemoteException;

    void readConfig(Session session, String parameter) throws RemoteException;

}
