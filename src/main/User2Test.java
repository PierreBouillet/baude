package main;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import stubs.PrivateStubImpl;
import stubs.PublicStubImpl;

public class User2Test {


	public static void main(String[] args) {
		try {

			PrivateStubImpl privateStub = new PrivateStubImpl("Pierre", "Bouillet", "Sophia-Antipolis", "Trapéziste");
			PublicStubImpl publicStub = new PublicStubImpl(privateStub);
			privateStub.setPublicStub(publicStub);

			Registry registry;
			try{
				registry=LocateRegistry.getRegistry(2004);
				registry.rebind(publicStub.getPersonInfo(), publicStub); // 
			}catch(Exception e)
			{
				System.out.println("------->"+e);
				registry = LocateRegistry.createRegistry(2004);
				registry.rebind(publicStub.getPersonInfo(), publicStub); // 
			}
			new ConsoleMenu(privateStub);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}


