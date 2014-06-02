package main;

import java.rmi.RemoteException;
import java.util.Scanner;

import rmiHandler.rmiHandler;
import stubs.*;

public class ConsoleMenu {

	private PrivateStubImpl privateStub;
	private rmiHandler rmiHandler;

	public ConsoleMenu(PrivateStubImpl ps){
		privateStub = ps;
		rmiHandler = new rmiHandler();
		launch();
	}

	public void launch(){
		int choice = 1;
		while(choice!=0){
			DisplayMenu();
			choice = getMenuChoice();
		}
	}

	public void DisplayMenu(){
		System.out.println("=================================================================");
		System.out.println("\t\t PolyFace");
		System.out.println("=================================================================");
		System.out.println("0) Quitter");
		System.out.println("1) Ajouter un message au mur");
		System.out.println("2) Ajouter un ami");
		System.out.println("3) Afficher mes amis");
		System.out.println("4) Afficher mes demandes d'amis");
		System.out.println("5) Afficher mon mur");
		System.out.println("6) Afficher le mur d'un ami");
		System.out.print("Votre choix : ");
	}

	public int getMenuChoice(){
		Scanner sc = new Scanner(System.in);
		int choice = sc.nextInt();

		switch(choice){
		case(0) : break;
		case(1) : addMessageToWall(privateStub);break;
		case(2) : addFriend();break;
		case(3) : displayFriends();break;
		case(4) : displayWaitingAcceptance();break;
		case(5) : displayWall(privateStub);break;
		case(6) : selectFriendsMenu();break;
		default : System.out.println("Le parametre rentre n'est pas correct");break;
		}
		return choice;
	}

	private void displayWall(PrivateStub stub) {
		
		try {
			
			for(String s : stub.getWall()){
				System.out.println(s);
			}
			
		} catch (RemoteException e) {
			System.out.println("Un probleme r�seau est survenue veuillez reessayer plus tard");
		}

	}

	private void displayFriends(){
		try {
			
			for(PrivateStub ps : privateStub.getFriends()){
				System.out.println(ps.getName());
			}
			
		}
		
		catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void addMessageToWall(PrivateStub stub){
		Scanner sc = new Scanner(System.in);
		System.out.print("Tapez votre message : ");
		String message = sc.nextLine();
		try {
			stub.addMessageOnWall(message);
		} catch (RemoteException e) {
			System.out.println("Un probleme r�seau est survenue veuillez reessayer plus tard");
		}
	}

	private void addFriend(){
		Scanner sc = new Scanner(System.in);
		System.out.print("Tapez le pr�nom de la personne que vous rechercher : ");
		String firstName = sc.nextLine();
		System.out.print("Tapez le nom de la personne que vous rechercher : ");
		String lastName = sc.nextLine();

		PublicStub friendStub = rmiHandler.searchUser(firstName, lastName);

		if(friendStub == null){
			System.out.println("La personne que vous rechercher n'est pas sur notre r�seau");
			return;
		}

		System.out.println("Etes vous sur de vouloir ajouter " + firstName + " " + lastName + " � vos amis (y/n)");
		String confirm = sc.nextLine();
		if(confirm.equals("y")){
			System.out.println("Tapez votre message d'invitation : ");
			String message = sc.nextLine();
			try {
				friendStub.invite(privateStub.getPublicStub(), message);
			} catch (RemoteException e) {
				System.out.println("Un probleme r�seau est survenue veuillez reessayer plus tard");
			}
		}

	}

	private void displayWaitingAcceptance(){
		int i=1;
		System.out.println("0) Quitter");
		for(PublicStub ps : privateStub.getWaitingAcceptance()){
			try {
				System.out.println(i + ") " + ps.getPersonInfo());
			} catch (RemoteException e) {
				System.out.println("Un probleme r�seau est survenue veuillez reessayer plus tard");
			}
		}
		Scanner sc = new Scanner(System.in);
		System.out.print("Selectionner la demande d'ami que vous souhaitez accepter : ");
		int choice = sc.nextInt();

		if(choice==0) return;
		else if(choice -1 >=0 && choice-1 <privateStub.getWaitingAcceptance().size()){
			try {
                PrivateStubSmartProxy bambambam = privateStub.getSmartProxy();
                PrivateStubSmartProxy bambombam = privateStub.getWaitingAcceptance().get(choice-1).acceptInvitation(bambambam);
                privateStub.getFriends().add(bambombam);
				//privateStub.getFriends().add(privateStub.getWaitingAcceptance().get(choice-1).acceptInvitation(privateStub.getSmartProxy()));
			} catch (RemoteException e) {

			}
		}
		else{
			System.out.println("Le parametre selectionner n'est pas correct");
			return;
		}
	}

	private void selectFriendsMenu(){
		int i=1;
		System.out.println("0) Quitter");

		try {

			for(PrivateStub ps : privateStub.getFriends()){
				System.out.println(i + ") " + ps.getName());
			}

		} 
		catch (RemoteException e) {
			System.out.println("Un probleme r�seau est survenue veuillez reessayer plus tard");
			return;
		}
		Scanner sc = new Scanner(System.in);
		System.out.print("Selectionner l'ami dont vous souhaitez consulter le mur : ");
		int choice = sc.nextInt();
		if(choice==0) return;
		else if(choice -1 >=0 && choice-1 <privateStub.getFriends().size()){
			addMessageOrConsultMenu(privateStub.getFriends().get(choice-1));
		}
		else{
			System.out.println("Le parametre selectionn� n'est pas correct");
			return;
		}
	}

	private void addMessageOrConsultMenu(PrivateStub stub) {
		System.out.println("Souhaitez vous consulter ou ajouter un message au mur de votre ami");
		System.out.println("0) Quitter");
		System.out.println("1) Consulter");
		System.out.println("2) Ajouter un message");

		Scanner sc = new Scanner(System.in);
		int choice = sc.nextInt();

		switch(choice){
		case(0): return;
		case(1):displayWall(stub);break;
		case(2):addMessageToWall(stub);break;
		default: System.out.println("Le parametre selectionn� n'est pas correct");break;
		}
	}

}