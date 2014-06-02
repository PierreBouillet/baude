package stubs;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * Created by William TASSOUX.
 */
public class PrivateStubSmartProxy implements PrivateStub, Serializable {

    private PrivateStub privateStub;
    private String name;

    public PrivateStubSmartProxy(PrivateStub privateStub) throws RemoteException
    {
        this.privateStub = privateStub;
        this.name = privateStub.getName();
    }

    @Override
    public void addMessageOnWall(String message) throws RemoteException {
        privateStub.addMessageOnWall(message);
    }

    @Override
    public void notifyUser(String message) throws RemoteException {
        privateStub.notifyUser(message);
    }

    @Override
    public String getName() throws RemoteException {
        // Utilisation de la donnée en cache
        return (name);
    }

    @Override
    public List<String> getWall() throws RemoteException {
        return (privateStub.getWall());
    }
}
