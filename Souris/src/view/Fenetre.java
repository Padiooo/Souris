package view;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Fenetre extends JFrame {
	
	private Panneau pan = null;
	
	public Fenetre() {

		this.setTitle("Souris");
		this.setSize(250, 85);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pan = new Panneau();
		this.setContentPane(pan);
		this.setVisible(true);

	}
	
	public Panneau getPan() {
		return this.pan;
	}
}
