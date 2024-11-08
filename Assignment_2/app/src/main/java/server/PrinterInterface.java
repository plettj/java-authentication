package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import authentication.VerificationResult;

public interface PrinterInterface extends Remote {

    // Authentication methods //
    String getPublicKey() throws RemoteException;

    VerificationResult login(String encryptedLoginRequest) throws RemoteException;

    // Printer methods //
    void print(String filename, String printer) throws RemoteException;

    void queue(String printer) throws RemoteException;

    void status(String printer) throws RemoteException;

    void start() throws RemoteException;

    void stop() throws RemoteException;

    void restart() throws RemoteException;

    void topQueue(String printer, int job) throws RemoteException;

    void setConfig(String parameter, String value) throws RemoteException;

    void readConfig(String parameter) throws RemoteException;

}
