package stubs;
import java.rmi.RemoteException;
import java.util.List;


public interface PrivateStubBase {

	public void addMessageOnWall(String message) throws RemoteException;
	
	public void notifyUser(String message) throws RemoteException;

	public String getName() throws RemoteException;

	public List<String> getWall() throws RemoteException;

}
