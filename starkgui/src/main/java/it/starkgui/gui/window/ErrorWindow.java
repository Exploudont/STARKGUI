package it.starkgui.gui.window;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.SwingConstants;

import it.starkgui.common.Language;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Window class that allow to communicate the errors to the user.
 * 
 * @author Daniele Longobardi (matricola 737547)
 * @version 1.0.0
 * @since JDK 17 
 */
public class ErrorWindow {

	private JFrame frame;
	
	private JFrame caller;
	private JFrame previous;
	private final String description;
	
	private static final int WIDTH = 450;
	private static final int HEIGHT = 160;

	/**
	 * Create the application.
	 * 
	 * @param caller the caller frame
	 * @param previous the previous frame
	 * @param description the error description
	 */
	public ErrorWindow(JFrame caller, JFrame previous, final String description) {
		this.caller = caller;
		this.previous = previous;
		this.description = description;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		
		Rectangle rec = caller.getBounds();
		int x = rec.x + rec.width/2 - WIDTH/2;
		int y = rec.y + rec.height/2 - HEIGHT/2;
		
		frame.setBounds(x, y, WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel(Language.getLabel(Language.ERROR));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		frame.getContentPane().add(lblNewLabel, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
		
		JButton btnNewButton = new JButton(Language.getLabel(Language.BACK));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				caller.setVisible(false);
				previous.setVisible(true);
				frame.dispose();
				
			}
		});
		panel.add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel(this.description);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lblNewLabel_1, BorderLayout.CENTER);
		
		frame.setVisible(true);
	}

}
