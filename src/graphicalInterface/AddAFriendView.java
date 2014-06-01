package graphicalInterface;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSplitPane;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JTextPane;

import rmiHandler.rmiHandler;
import stubs.PrivateStubImpl;
import stubs.PublicStub;

public class AddAFriendView extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public AddAFriendView(PrivateStubImpl stub, PublicStub friendStub) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JSplitPane splitPane = new JSplitPane();
		contentPane.add(splitPane, BorderLayout.CENTER);
		
		JLabel lblTapezVotreMessage = new JLabel("Tapez votre message d'inviattion");
		splitPane.setLeftComponent(lblTapezVotreMessage);
		
		JTextPane textPane = new JTextPane();
		splitPane.setRightComponent(textPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		ButtonMethod buttonMethod = new ButtonMethod(stub, friendStub,textPane,this);
		
		JButton btnNewButton = new JButton("Ajouter");
		panel.add(btnNewButton);
		btnNewButton.setActionCommand("Ajouter");
		btnNewButton.addActionListener(buttonMethod);
	}
	
	public class ButtonMethod implements ActionListener{

		private PrivateStubImpl stub;
		private JTextPane message;
		private PublicStub friendStub;
		private JFrame frame;

		public ButtonMethod(PrivateStubImpl stub, PublicStub friendStub, JTextPane message, JFrame frame){
			this.stub=stub;
			this.message = message;
			this.friendStub = friendStub;
			this.frame = frame;
		}


		@Override
		public void actionPerformed(ActionEvent e) {

			if("Ajouter".equals(e.getActionCommand())){
				
				try {
					friendStub.invite(stub.getPublicStub(), message.getText());
				} catch (RemoteException ex) {
					JOptionPane.showMessageDialog(frame, "Un probleme réseau est survenue veuillez reessayer plus tard");
				}
				
				frame.dispose();
			}

		}
	}

}
