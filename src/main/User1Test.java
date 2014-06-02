package main;

import graphicalInterface.MainView;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import stubs.PrivateStub;
import stubs.PrivateStubImpl;
import stubs.PublicStub;
import stubs.PublicStubImpl;

public class User1Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			
			PrivateStubImpl privateStub = new PrivateStubImpl("William", "Tassoux", "Sophia-Antipolis", "G�nie de l'eau");
            PublicStubImpl publicStub = new PublicStubImpl(privateStub);
            privateStub.setPublicStub(publicStub);

            Registry registry;
            try{
                registry = LocateRegistry.getRegistry(2004);
                registry.rebind(publicStub.getPersonInfo(), publicStub);
            }catch(Exception e)
            {
                registry = LocateRegistry.createRegistry(2004);
                registry.rebind(publicStub.getPersonInfo(), publicStub);
            }

            MainView view = new MainView(privateStub);
            view.setVisible(true);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}