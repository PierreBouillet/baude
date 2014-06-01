package graphicalInterface;

import graphicalInterface.MainView.MenuBarMethod;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JTextPane;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;
import java.rmi.RemoteException;

import javax.swing.Box;

import stubs.PrivateStub;
import stubs.PrivateStubImpl;

import javax.swing.AbstractAction;
import javax.swing.Action;

public class WallView extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public WallView(PrivateStub stub) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 515, 352);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.8);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		contentPane.add(splitPane, BorderLayout.CENTER);

		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setRightComponent(splitPane_1);

		Component verticalStrut = Box.createVerticalStrut(16);
		splitPane_1.setLeftComponent(verticalStrut);

		JTextPane textPane_1 = new JTextPane();
		splitPane_1.setRightComponent(textPane_1);

		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		splitPane.setLeftComponent(textPane);

		try {
			for(String s : stub.getWall()){
				textPane.setText(textPane.getText() + "\n" + s);
			}
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(this, "Un probleme réseau est survenue veuillez reessayer plus tard");
		}
		
		ButtonMethod buttonMethod = new ButtonMethod(textPane_1, stub, textPane,this);
		
		JButton btnNewButton = new JButton("Poster");
		panel_1.add(btnNewButton);
		btnNewButton.setActionCommand("Poster");
		btnNewButton.addActionListener(buttonMethod);
	}
	
	public class ButtonMethod implements ActionListener{
		
		private JTextPane textPane;
		private JTextPane wall;
		private PrivateStub stub;
		private JFrame frame;
		
		public ButtonMethod(JTextPane text, PrivateStub stub, JTextPane wall, JFrame frame){
			this.textPane = text;
			this.stub=stub;
			this.wall = wall;
			this.frame=frame;
		}
		

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if("Poster".equals(e.getActionCommand())){
				try {
					stub.addMessageOnWall(textPane.getText());
				} catch (RemoteException ex) {
					System.out.println("Un probleme réseau est survenue veuillez reessayer plus tard");
				}
				textPane.setText("");
				
				wall.setText("");
				try {
					for(String s : stub.getWall()){
						wall.setText(wall.getText() + "\n" + s);
					}
				} catch (RemoteException e1) {
					JOptionPane.showMessageDialog(frame, "Un probleme réseau est survenue veuillez reessayer plus tard");
				}
			}
			
		}
	}

}
