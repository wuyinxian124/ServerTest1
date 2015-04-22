package edu.scut.wusir.SocketExample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class ConnectExceptionClient {

	public static void main(String[] args) {

		//new Thread(new SimpleServer()).start();
		new Thread(new SimpleClient()).start();

	}


	static class SimpleClient implements Runnable {

		@Override
		public void run() {

			Socket socket = null;
			try {

				Thread.sleep(3000);
				
				socket = new Socket("195.134.65.75", 3333);
				
			    PrintWriter outWriter = new PrintWriter(socket.getOutputStream(),true);
			    
			    outWriter.println("Hello Mr. Server!");
			   

			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				
				try {
					socket.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		}

	}
}
