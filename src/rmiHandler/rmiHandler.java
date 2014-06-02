package rmiHandler;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import stubs.PublicStub;
import stubs.PublicStubImpl;

public class rmiHandler {

	private Registry reg;
	public rmiHandler() {
		try {
			reg = LocateRegistry.getRegistry(2004);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public PublicStub searchUser(String firstName, String lastName){
		try {
			return (PublicStub) (reg.lookup(firstName + " " + lastName));
		} catch (Exception e) {
			return null;
		}
	}

}
