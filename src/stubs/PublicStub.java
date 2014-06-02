package stubs;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PublicStub extends Remote {

	public String getPersonInfo()  throws RemoteException;
	
	public PrivateStubSmartProxy acceptInvitation(PrivateStubSmartProxy privateStub)  throws RemoteException;

	public void invite(PublicStub publicStub, String invitationMessage) throws RemoteException;

    public void notifyInvitation(String name) throws RemoteException;
}
