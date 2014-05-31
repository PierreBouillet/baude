package stubs;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;


public class PrivateStubImpl extends UnicastRemoteObject implements PrivateStub, Serializable {

	private String firstName;
	private String lastName;
	private String location;
	private String job;
	private PublicStubImpl publicStub;

	private List<PrivateStub> friends;
	private List<PublicStub> waitingAcceptance;

	private List<String> wall;
	private List<String> notification;

	private Semaphore sem = new Semaphore(1);

	public PrivateStubImpl(String firstName, String lastName, String loc, String job) throws RemoteException {
		super();

		this.firstName=firstName;
		this.lastName=lastName;
		this.job=job;
		this.location=loc;
		
		friends = new ArrayList<PrivateStub>();
		waitingAcceptance = new ArrayList<PublicStub>();
		wall = new ArrayList<String>();
		notification = new ArrayList<String>();
	}

	public String getName() {
		return (firstName + " " + lastName);
	}

	@Override
	public List<String> getWallContent() {
		return wall;
	}

	public void addMessageOnWall(String message) throws RemoteException{
		wall.add(message);
		for(PrivateStub s : friends){
			s.notifyUser(getName() + " a posté un message sur son mur : " + message);
		}
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public PublicStubImpl getPublicStub() {
		return publicStub;
	}

	public void setPublicStub(PublicStubImpl publicStub) {
		this.publicStub = publicStub;
	}

	public List<PrivateStub> getFriends() {
		return friends;
	}

	public void setFriends(List<PrivateStub> friends) {
		this.friends = friends;
	}

	public List<PublicStub> getWaitingAcceptance() {
		return waitingAcceptance;
	}

	public void setWaitingAcceptance(List<PublicStub> waitingAcceptance) {
		this.waitingAcceptance = waitingAcceptance;
	}

	public List<String> getWall() {
		return wall;
	}

	public void addNotification(String notifiaction) {
		try {
			sem.acquire();
			notification.add(notifiaction);
			sem.release();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void notifyUser(String message) {
		try {
			sem.acquire();
			notification.add(message);
			sem.release();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
