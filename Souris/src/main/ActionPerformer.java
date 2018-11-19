package main;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

public class ActionPerformer extends Thread {

	private String delims = "[ ]+";
	private String[] action = null;
	private String msg = null;

	private Robot robot = null;

	private Socket s;
	private BufferedReader in;
	private PrintWriter out;

	public ActionPerformer(Socket s, BufferedReader in, PrintWriter out) {

		try {
			this.robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.s = s;
		this.in = in;
		this.out = out;
	}

	@Override
	public void run() {
		try {
			this.msg = in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (msg != null) {
			this.action = msg.split(delims);
			doAction();
		}
		try {
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean doAction() {

		switch (action[0]) {

		case "M":
			moveMouse(Integer.parseInt(action[1]), Integer.parseInt(action[2]));
			try {
				screenCapture(Integer.parseInt(action[1]), Integer.parseInt(action[2]));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "L":
			leftClick();
			break;
		case "R":
			rightClick();
			break;
		default:
			if (msg != null) {
				printText(msg);
			}
			break;
		}

		return true;

	}

	public void moveMouse(int x, int y) {

		robot.mouseMove(x, y);
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void rightClick() {
		robot.mousePress(InputEvent.BUTTON3_MASK);
		robot.mouseRelease(InputEvent.BUTTON3_MASK);
	}

	public void leftClick() {
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}

	public void printText(String text) {

		StringSelection stringSelection = new StringSelection(text);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, stringSelection);

		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
	}

	public void screenCapture(int mouseX, int mouseY) throws IOException {

		int width = 200;
		int height = 100;
		int x = 0;
		int y = 0;

		// x
		if (mouseX - width / 2 < 0) {
			x = width / 2;
		} else if (mouseX + width / 2 > (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()) {
			x = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() - width / 2);
		} else {
			x = mouseX - width / 2;
		}
		// y
		if (mouseY - height / 2 < 0) {
			y = height / 2;
		} else if (y + height / 2 > (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()) {
			y = mouseY - height / 2;
		} else {
			y = mouseY - height / 2;
		}

		Rectangle area = new Rectangle(x, y, width, height);

		BufferedImage image = robot.createScreenCapture(area);

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ImageIO.write(image, "jpg", byteArrayOutputStream);

		OutputStream outputStream = s.getOutputStream();
		byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
		outputStream.write(size);
		outputStream.write(byteArrayOutputStream.toByteArray());
		outputStream.flush();
		s.close();
	}
}
