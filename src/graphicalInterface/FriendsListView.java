package graphicalInterface;

import graphicalInterface.AddAFriendView.ButtonMethod;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;

import stubs.PrivateStub;
import stubs.PrivateStubImpl;
import stubs.PrivateStubSmartProxy;
import stubs.PublicStub;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.List;

public class FriendsListView extends JFrame {

	private JPanel contentPane;
	/**
	 * Create the frame.
	 */
	public FriendsListView(List<PrivateStubSmartProxy> friends) {

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

		for(PrivateStub ps: friends)
			try {
				listModel.addElement(ps.getName());
			} catch (RemoteException e) {
				JOptionPane.showMessageDialog(this, "Un probleme r�seau est survenue veuillez reessayer plus tard");
			}

		JPanel panel = new JPanel();
		splitPane.setRightComponent(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		ButtonMethod buttonMethod = new ButtonMethod(friends,list,this);

		JButton btnVisiterMur = new JButton("Visiter mur");
		panel.add(btnVisiterMur);
		btnVisiterMur.setActionCommand("Visiter mur");
		btnVisiterMur.addActionListener(buttonMethod);
	}

	public class ButtonMethod implements ActionListener{

		private JList list;
		private List<PrivateStubSmartProxy> friends;
		private JFrame frame;

		public ButtonMethod(List<PrivateStubSmartProxy> friends, JList list, JFrame frame){
			this.friends=friends;
			this.list = list;
			this.frame=frame;
		}


		@Override
		public void actionPerformed(ActionEvent e) {

			if("Visiter mur".equals(e.getActionCommand())){
				int selected = list.getSelectedIndex();
				if(selected!=-1){
					WallView view = new WallView(friends.get(list.getSelectedIndex()));
					view.show();
					frame.dispose();
				}
			}
			else
				JOptionPane.showMessageDialog(frame, "Selectionner d'abord une personne dans la liste");

		}
	}

}
