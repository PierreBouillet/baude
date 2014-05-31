package stubs;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


public interface PrivateStub extends Remote{
		
	public List<String> getWallContent() throws RemoteException;
	
	public void addMessageOnWall(String message) throws RemoteException;
	
	public void notifyUser(String message) throws RemoteException;

	public String getName() throws RemoteException;

	public List<String> getWall() throws RemoteException;

}
