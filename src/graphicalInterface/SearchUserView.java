package graphicalInterface;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;

import rmiHandler.rmiHandler;
import stubs.PrivateStubImpl;
import stubs.PublicStub;

public class SearchUserView extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Create the frame.
	 */
	public SearchUserView(PrivateStubImpl stub) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 404, 262);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		contentPane.add(splitPane, BorderLayout.CENTER);

		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setRightComponent(splitPane_1);

		JSplitPane splitPane_3 = new JSplitPane();
		splitPane_3.setResizeWeight(0.1);
		splitPane_1.setLeftComponent(splitPane_3);

		JLabel lblPrenom = new JLabel("      Prenom");
		splitPane_3.setLeftComponent(lblPrenom);

		textField_1 = new JTextField();
		splitPane_3.setRightComponent(textField_1);
		textField_1.setColumns(10);

		JSplitPane splitPane_4 = new JSplitPane();
		splitPane_4.setResizeWeight(0.99);
		splitPane_4.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_1.setRightComponent(splitPane_4);

		JPanel panel = new JPanel();
		splitPane_4.setRightComponent(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		DefaultListModel listModel = new DefaultListModel();
		JList list = new JList(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

		JScrollPane listScroller = new JScrollPane(list);
		splitPane_4.setLeftComponent(listScroller);

		JSplitPane splitPane_2 = new JSplitPane();
		splitPane_2.setResizeWeight(0.1);
		splitPane.setLeftComponent(splitPane_2);

		JLabel lblNom = new JLabel("         Nom :");
		splitPane_2.setLeftComponent(lblNom);

		textField_2 = new JTextField();
		splitPane_2.setRightComponent(textField_2);
		textField_2.setColumns(10);



		JButton btnAjouterCommeAmi = new JButton("Ajouter Comme ami");
		panel.add(btnAjouterCommeAmi);
		btnAjouterCommeAmi.setActionCommand("Ajouter Comme ami");
		btnAjouterCommeAmi.setEnabled(false);

		JButton btnNewButton = new JButton("Chercher");
		panel.add(btnNewButton);
		btnNewButton.setActionCommand("Chercher");

		ButtonMethod buttonMethod = new ButtonMethod(stub, textField_2, textField_1,listModel, btnAjouterCommeAmi,this);
		btnAjouterCommeAmi.addActionListener(buttonMethod);
		btnNewButton.addActionListener(buttonMethod);
	}

	public class ButtonMethod implements ActionListener{

		private PrivateStubImpl stub;
		private JTextField nom;
		private JTextField prenom;
		private rmiHandler rmiHandler;
		private DefaultListModel foundPerson;
		private PublicStub friendStub;
		private JButton button;
		private JFrame frame;

		public ButtonMethod(PrivateStubImpl stub, JTextField nom, JTextField prenom, DefaultListModel foundPerson, JButton button, JFrame frame){
			this.stub=stub;
			this.prenom = prenom;
			this.nom =nom;
			this.foundPerson = foundPerson;
			this.button = button;
			this.frame=frame;
			rmiHandler = new rmiHandler();
		}


		@Override
		public void actionPerformed(ActionEvent e) {

			if("Chercher".equals(e.getActionCommand())){
				friendStub = rmiHandler.searchUser(prenom.getText(), nom.getText());
				if(friendStub!=null){
					try {
						foundPerson.addElement(friendStub.getPersonInfo());
					} catch (RemoteException e1) {
						JOptionPane.showMessageDialog(frame, "Un probleme réseau est survenue veuillez reessayer plus tard");
					}
					button.setEnabled(true);
				}
				else
					foundPerson.addElement("La personne recherchee n'est pas sur notre reseau");
			}

			if("Ajouter Comme ami".equals(e.getActionCommand())){
				try {
					
					if(friendStub.getPersonInfo().equals(stub.getName())){
						JOptionPane.showMessageDialog(frame, "Forever Alone");
					}
					else{
						AddAFriendView view = new AddAFriendView(stub, friendStub);
						view.show();
						frame.dispose();
					}
					
				} catch (RemoteException e1) {
					JOptionPane.showMessageDialog(frame, "Un probleme réseau est survenue veuillez reessayer plus tard");
				}

			}

		}
	}

}
