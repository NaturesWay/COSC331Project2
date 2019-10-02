package client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {

	
	private DatagramSocket socket;
	private InetAddress address;
	private int port;
	private String name;
	
	private boolean running;
	
	public Client(String name, InetAddress address, int port) {
	try {
		this.address = address;
		this.port = port;
		this.name = name;
		socket = new DatagramSocket();
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	running = true;
	listen();
	send("\\con: " + name + "@ : " + address + "; " + port);
	
	}

	
	public void send(String message) {
		try {
			
			if (!message.startsWith("\\")) {
				message = name + ": " + message;
			}
			message += "\\e";
			byte[] data = message.getBytes();
			DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
			socket.send(packet);
			System.out.println("Sent Message To: " + address.getHostAddress() + ";" + port);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void listen()
	{
		Thread listenThread = new Thread("ChatProgram Listener")
				{
			public void run() {
				try {
					while(running) {
					
						byte[] data = new byte[1024];
						DatagramPacket packet = new DatagramPacket(data, data.length);
						socket.receive(packet);
						
						String message = new String(data);
						message = message.substring(0, message.indexOf("\\e"));
						
						//MANAGE MESSAGE
						if (!isCommand(message, packet)) {
							ClientWindow.printToConsole(message);
							
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
				}; listenThread.start();
				
		
	}
	
	private static boolean isCommand(String message, DatagramPacket packet) {
		if (message.startsWith("\\con:")) {
			//RUN CONNECTION CODE
			
			return true;
			
		}
			
		return false;
		
	}
	
	
	
}

