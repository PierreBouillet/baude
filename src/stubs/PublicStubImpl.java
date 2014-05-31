package stubs;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class PublicStubImpl extends UnicastRemoteObject implements PublicStub, Serializable{

	private PrivateStubImpl privateStub;

	public PublicStubImpl(PrivateStubImpl ps) throws RemoteException {
		super();
		privateStub =ps;
	}

	@Override
	public void invite(PublicStub publicStub, String invitationMessage) throws RemoteException {
		
		if(! privateStub.getWaitingAcceptance().contains(publicStub)){
			
			privateStub.getWaitingAcceptance().add(publicStub);
			privateStub.addNotification(publicStub.getPersonInfo() +"vous a ajouté comme ami");
		}
	}

	@Override
	public String getPersonInfo()  throws RemoteException{
		return privateStub.getName();
	}

	@Override
	public PrivateStub acceptInvitation(PrivateStub privateStub)  throws RemoteException{
		this.privateStub.getFriends().add(privateStub);
		return this.privateStub;
	}

}
