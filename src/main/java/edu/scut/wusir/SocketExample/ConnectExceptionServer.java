package edu.scut.wusir.SocketExample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class ConnectExceptionServer {

	public static void main(String[] args) {

		new Thread(new SimpleServer()).start();
		//new Thread(new SimpleClient()).start();

	}

	static class SimpleServer implements Runnable {

		@Override
		public void run() {

			ServerSocket serverSocket = null;
			while (true) {
				
				try {
					serverSocket = new ServerSocket(3333);

					Socket clientSocket = serverSocket.accept();

					BufferedReader inputReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					
					System.out.println("Client said :"+inputReader.readLine());

				} catch (IOException e) {
					e.printStackTrace();
				}finally{
					try {
						serverSocket.close();
					
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		}

	}

}
