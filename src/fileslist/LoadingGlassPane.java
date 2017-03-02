package fileslist;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.event.*;
/**
 * We have to provide our own glass pane so that it can paint a loading
 * dialog and then the user can see the progress but can't operation
 * the GUI, it's a 4./? pane so the below contents is visible.
 *
 * Also please refer to articles by Sun - How to Use Root Panes:
 * {@link http://java.sun.com/docs/books/tutorial/uiswing/components/rootpane.ht ml}
 * @author Jacky Liu
 * @version 1.0 2006-08
 */
public class LoadingGlassPane extends JPanel {
	private static final long serialVersionUID = 1L;
	/**
	 * A label displays status text and loading icon.
	 */
	private JLabel statusLabel = new JLabel ("Reading data, please wait...");
	public LoadingGlassPane() {
		try {
			statusLabel.setIcon(new ImageIcon(getClass().getResource(
					"loading.gif")));
		} catch (RuntimeException e1) {
			e1.printStackTrace();
		} 
		statusLabel.setHorizontalAlignment(JLabel.CENTER);
		// Must add a mouse listener, otherwise the event will not be
		// captured
		this.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(MouseEvent e) {
			} 
		});
		this.setLayout(new BorderLayout());
		this.add (statusLabel);
		// Transparent
		setOpaque(true);
	}
	/**
	 * Set the text to be displayed on the glass pane.
	 *
	 * @param text
	 */
	public void setStatusText(final String text) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				statusLabel.setText(text);
			}
		});
	}
	/**
	 * Install this to the jframe. as glass pane.
	 * @param frame
	 */
	public void installAsGlassPane(JFrame frame) {
		frame.setGlassPane (this);
	}
	/**
	 * A small demo code of how to use this glasspane.
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame("Test GlassPane");
		final LoadingGlassPane glassPane = new LoadingGlassPane ();
		glassPane.installAsGlassPane(frame);
		JButton button = new JButton("Test Query");
		button.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				// Call in new thread to allow the UI to update
				Thread th = new Thread() {
					public void run() {
						glassPane.setVisible (true);
						glassPane.setCursor(new Cursor(Cursor.WAIT_CURSOR));
						// TODO Long time operation here
						try {
							Thread.sleep(20000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						glassPane.setCursor(new Cursor (Cursor.DEFAULT_CURSOR));
						glassPane.setVisible(false);
					} 
				};
				th.start();
			}
		});
		frame.getContentPane().setLayout (new FlowLayout());
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add (button);
		frame.setSize(200, 200);
		frame.setVisible(true);
	} 
}