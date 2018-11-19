package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {

	private static Socket s;
	private static ServerSocket ss;

	@SuppressWarnings("unused")
	private ActionPerformer actionPerformer = null;

	public MyServer() throws IOException {

		// server is listening on port 7800
		ss = new ServerSocket(7800);

		while (true) {

			s = ss.accept();

			BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			PrintWriter out = new PrintWriter(s.getOutputStream());
			@SuppressWarnings("unused")
			Thread t = new ActionPerformer(s, in, out);
			t.start();
		}
	}
}
