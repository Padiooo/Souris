package main;

import java.io.IOException;

import view.Fenetre;

public class Souris {

	public static void main(String[] args) {
		
		Fenetre fen = new Fenetre();
		try {
			MyServer server = new MyServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
