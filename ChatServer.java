package server;

import javax.swing.JOptionPane;

public class ChatServer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int port = Integer.parseInt( JOptionPane.showInputDialog("Enter Port #"));
		
		Server.start(port);
	}

}
