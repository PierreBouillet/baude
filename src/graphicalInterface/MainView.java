package graphicalInterface;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JSplitPane;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.JTextArea;

import stubs.PrivateStub;
import stubs.PrivateStubImpl;

public class MainView extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public MainView(PrivateStubImpl stub) {
		setTitle("PolyFace");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		contentPane.add(splitPane);
		
		JMenuBar menuBar = new JMenuBar();
		splitPane.setLeftComponent(menuBar);
		
		JMenu mnMur = new JMenu("Mur");
		menuBar.add(mnMur);
		
		MenuBarMethod menuMethod = new MenuBarMethod(stub);
		
		JMenuItem mntmVoirMonMur = new JMenuItem("Voir mon mur");
		mnMur.add(mntmVoirMonMur);
		mntmVoirMonMur.setActionCommand("Voir mon mur");
		mntmVoirMonMur.addActionListener(menuMethod);
				
		JMenu mnAmis = new JMenu("Amis");
		menuBar.add(mnAmis);
		
		JMenuItem mntmListeDeMes = new JMenuItem("Liste de mes amis");
		mnAmis.add(mntmListeDeMes);
		mntmListeDeMes.setActionCommand("Liste de mes amis");
		mntmListeDeMes.addActionListener( menuMethod);
		
		JMenuItem mntmAjouterUnAmi = new JMenuItem("Ajouter un ami");
		mnAmis.add(mntmAjouterUnAmi);
		mntmAjouterUnAmi.setActionCommand("Ajouter un ami");
		mntmAjouterUnAmi.addActionListener( menuMethod);
		
		JMenuItem mntmVoirMesDemandes = new JMenuItem("Voir mes demandes d'ami");
		mnAmis.add(mntmVoirMesDemandes);
		mntmVoirMesDemandes.setActionCommand("Voir mes demandes d'ami");
		mntmVoirMesDemandes.addActionListener(menuMethod);
		
		JSplitPane splitPane_1 = new JSplitPane();
		splitPane.setRightComponent(splitPane_1);
		
		JSplitPane splitPane_2 = new JSplitPane();
		splitPane_2.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_1.setRightComponent(splitPane_2);
		
		JLabel lblNotifications = new JLabel("Notifications :");
		splitPane_2.setLeftComponent(lblNotifications);
		
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		splitPane_2.setRightComponent(textPane);
		
		JLabel lblBienvenueSurPolyface = new JLabel("  Bienvenue sur PolyFace  ");
		splitPane_1.setLeftComponent(lblBienvenueSurPolyface);

	}
	
	public class MenuBarMethod implements ActionListener{
		
		private PrivateStubImpl stub;
		
		public MenuBarMethod(PrivateStubImpl stub){
			this.stub=stub;
		}
		
		@Override
	    public void actionPerformed(ActionEvent e)
	    {
	        if("Voir mon mur".equals(e.getActionCommand())){
	            WallView view = new WallView(stub);
	            view.show();
	        }
	        if("Liste de mes amis".equals(e.getActionCommand())){
	            FriendsListView view = new FriendsListView(stub.getFriends());
	            view.show();
	        }
	        if("Ajouter un ami".equals(e.getActionCommand())){
	            SearchUserView view = new SearchUserView(stub);
	            view.show();
	        }
	        if("Voir mes demandes d'ami".equals(e.getActionCommand())){
	            ListWaitingAcceptanceView view = new ListWaitingAcceptanceView(stub);
	            view.show();
	        }
	    }

	}

}
