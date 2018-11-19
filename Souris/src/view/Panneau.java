package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Panneau extends JPanel {

	private BufferedImage img = null;
	private String IP = "000.000.000.000";

	public Panneau() {
		try {
			this.IP = InetAddress.getLocalHost().getHostAddress().toString();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
	}

	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 250, 80);
		g.setColor(Color.BLUE);
		g.setFont(new Font("Arial", Font.PLAIN, 30));
		g.drawString(IP, 0, 30);
	}

	public BufferedImage getImg() {
		return img;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}

}
