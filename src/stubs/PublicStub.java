package stubs;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PublicStub extends Remote {

	
	public String getPersonInfo()  throws RemoteException;
	
	public PrivateStub acceptInvitation(PrivateStub privateStub)  throws RemoteException;

	public void invite(PublicStub publicStub, String invitationMessage) throws RemoteException;
}
