package stubs;
import java.io.Serializable;
import java.rmi.Remote;
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
			publicStub.notifyInvitation(getPersonInfo());
		}
	}

	@Override
	public String getPersonInfo()  throws RemoteException{
		return privateStub.getName();
	}

	@Override
	public PrivateStubSmartProxy acceptInvitation(PrivateStubSmartProxy privateStub) throws RemoteException{
		this.privateStub.getFriends().add(privateStub);
		return this.privateStub.getSmartProxy();
	}

    @Override
    public void notifyInvitation(String name)
    {
        this.privateStub.addNotification(name + " vous a ajouté en ami");
    }
}
