package graphicalInterface;

import graphicalInterface.MainView.MenuBarMethod;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
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
import javax.swing.AbstractAction;
import javax.swing.Action;

public class WallView extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public WallView(PrivateStub stub) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 515, 352);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btnNewButton = new JButton("Poster");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		panel_1.add(btnNewButton);

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
			System.out.println("Un probleme réseau est survenue veuillez reessayer plus tard");
		}
	}

}
