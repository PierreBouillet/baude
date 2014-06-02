package main;

import graphicalInterface.MainView;
import stubs.PrivateStubImpl;
import stubs.PublicStubImpl;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class User3Test {


	public static void main(String[] args) {
		try {

			PrivateStubImpl privateStub = new PrivateStubImpl("Damien", "Viano", "Sophia-Antipolis", "RMIste");
			PublicStubImpl publicStub = new PublicStubImpl(privateStub);
			privateStub.setPublicStub(publicStub);

			Registry registry;
			try{
				registry = LocateRegistry.getRegistry(2004);
			}catch(Exception e)
			{
				registry = LocateRegistry.createRegistry(2004);
			}
            registry.rebind(publicStub.getPersonInfo(), publicStub); //

            MainView view = new MainView(privateStub);
            view.setVisible(true);

        } catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}


