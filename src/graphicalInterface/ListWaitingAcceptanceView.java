package graphicalInterface;

import graphicalInterface.FriendsListView.ButtonMethod;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import stubs.*;

public class ListWaitingAcceptanceView extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public ListWaitingAcceptanceView(PrivateStubImpl stub) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.99);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		contentPane.add(splitPane, BorderLayout.CENTER);

		DefaultListModel listModel = new DefaultListModel();
		JList list = new JList(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		JScrollPane listScroller = new JScrollPane(list);
		splitPane.setLeftComponent(listScroller);

		for(PublicStub ps: stub.getWaitingAcceptance())
			try {
				listModel.addElement(ps.getPersonInfo());
			} catch (RemoteException e) {
				JOptionPane.showMessageDialog(this, "Un probleme r�seau est survenue veuillez reessayer plus tard");
			}

		JPanel panel = new JPanel();
		splitPane.setRightComponent(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		ButtonMethod buttonMethod = new ButtonMethod(list, listModel, stub, this);

		JButton btnAccept = new JButton("Accepter Comme Ami");
		panel.add(btnAccept);
		btnAccept.setActionCommand("Accepter Comme Ami");
		btnAccept.addActionListener(buttonMethod);
		
		JButton btnRefuse = new JButton("Refuser");
		panel.add(btnRefuse);
		btnRefuse.setActionCommand("Refuser");
		btnRefuse.addActionListener(buttonMethod);
	}

	public class ButtonMethod implements ActionListener{

		private JList list;
		private List<PublicStub> waitingAcceptance;
		private PrivateStubImpl stub;
		private JFrame frame;
		private DefaultListModel listModel;

		public ButtonMethod(JList list,DefaultListModel listModel, PrivateStubImpl stub, JFrame frame){
			this.waitingAcceptance= stub.getWaitingAcceptance();
			this.list = list;
			this.frame= frame;
			this.stub =stub;
			this.listModel = listModel;
		}


		@Override
		public void actionPerformed(ActionEvent e) {

			if("Accepter Comme Ami".equals(e.getActionCommand())){
				int selected = list.getSelectedIndex();
				if(selected!=-1){
					try {
						PublicStub publicFriendStub = waitingAcceptance.get(list.getSelectedIndex());
						PrivateStubSmartProxy privateFriendStub = publicFriendStub.acceptInvitation(stub.getSmartProxy());
						stub.getFriends().add(privateFriendStub);
						stub.getWaitingAcceptance().remove(publicFriendStub);
						listModel.remove(list.getSelectedIndex());
					}
					catch (RemoteException e1) {
						JOptionPane.showMessageDialog(frame, "Un probleme r�seau est survenue veuillez reessayer plus tard");
					}

				}
				else
					JOptionPane.showMessageDialog(frame, "Selectionner d'abord une personne dans la liste");
			}
			
			
			if("Refuser".equals(e.getActionCommand())){
				int selected = list.getSelectedIndex();
				if(selected!=-1){
					try {
						PublicStub publicFriendStub = waitingAcceptance.get(list.getSelectedIndex());
						PrivateStubSmartProxy privateFriendStub = publicFriendStub.acceptInvitation(stub.getSmartProxy());
						stub.getWaitingAcceptance().remove(publicFriendStub);
						listModel.remove(list.getSelectedIndex());
					}
					catch (RemoteException e1) {
						JOptionPane.showMessageDialog(frame, "Un probleme r�seau est survenue veuillez reessayer plus tard");
					}

				}
				else
					JOptionPane.showMessageDialog(frame, "Selectionner d'abord une personne dans la liste");	
			}

		}
	}

}
