package edu.scut.wusir.SocketExample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import fy.socket.SocketAPPClient.exception.ConnectWebsocketException;
import fy.socket.SocketAPPClient.exception.HandshakeWebsocketException;
import fy.socket.SocketAPPClient.exception.IllegalWebsocketException;
import fy.socket.SocketAPPClient.service.APPClient;


public class ConnectExceptionClient {
	
	public static void main(String[] args) {
		Phaser phaser=new Phaser();
		int i = 0;
		while(i<100){
			new Thread(new SimpleClient(phaser)).start();
			i++;
		}
		//new Thread(new SimpleServer()).start();
	}
}
class SimpleClient implements Runnable {
	private Logger logger = LoggerUtil.getLogger(this.getClass().getName());
	
	private Phaser phaser;
	
	public SimpleClient(Phaser phaser){
		this.phaser = phaser;
	}
	
	@Override
	public void run() {

		Socket socket = null;
		try {
			phaser.register();
//			Thread.sleep(3000);
			logger.log(Level.INFO, "new socket connect");
			//socket = new Socket("222.201.139.162", 8777);
			APPClient client = new APPClient("222.201.139.162", 8777);
			client.connection();
			phaser.arriveAndAwaitAdvance();
			TimeUnit.SECONDS.sleep(5);
			client.virify("user" , "verify" , "homewtb");
			logger.log(Level.INFO, "socket write---");
			TimeUnit.SECONDS.sleep(10);

			// 等待所有线程一起收发消息
			logger.log(Level.INFO,"socket write===等待线程数目:"+phaser.arriveAndAwaitAdvance());
			for (int i = 0; i < 60; i++) {
				int chatid = new Random().nextInt(5);
				String msg = "chatroom" + chatid + "##" + 0 + "##" + "user"
						 + " send a mes " + i + " to chatroom" + chatid;
				logger.log(Level.INFO, "user"  + " send a msg to"
						+ " chatroom" + chatid + " msg=(" + msg + ")");
				client.sendMsg(msg, 0, 0);
				TimeUnit.SECONDS.sleep(1);
			}
			
//			TimeUnit.SECONDS.sleep(5);
//		    PrintWriter outWriter = new PrintWriter(socket.getOutputStream(),true);
//		    logger.log(Level.INFO, "socket write---");
//		    
//			phaser.arriveAndAwaitAdvance();
//			TimeUnit.SECONDS.sleep(10);
//		    outWriter.println("Hello Mr. Server!");
//		    outWriter.flush();
//		    logger.log(Level.INFO, "socket write===");
//		    
//		    phaser.arriveAndAwaitAdvance();
//		    outWriter.println("Hello baby !");
//		    outWriter.flush();
//		    logger.log(Level.INFO, "==over");
//		    
//		    phaser.arriveAndDeregister();
			logger.log(Level.INFO, "==over");
//			phaser.arriveAndDeregister();
		    TimeUnit.SECONDS.sleep(20);
		} catch (InterruptedException e) {
			phaser.arriveAndDeregister();
			e.printStackTrace();
		} catch (UnknownHostException e) {
			phaser.arriveAndDeregister();
			e.printStackTrace();
		} catch (IOException e) {
			phaser.arriveAndDeregister();
			e.printStackTrace();
		} catch (IllegalWebsocketException e) {
			phaser.arriveAndDeregister();
			e.printStackTrace();
		} catch (URISyntaxException e) {
			phaser.arriveAndDeregister();
			e.printStackTrace();
		} catch (ConnectWebsocketException | HandshakeWebsocketException e) {
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