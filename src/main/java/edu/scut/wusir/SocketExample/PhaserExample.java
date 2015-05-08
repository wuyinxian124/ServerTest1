package edu.scut.wusir.SocketExample;

import java.util.concurrent.*;
import java.util.logging.Level;

public class PhaserExample {  
 
   public static void main(String[] args) {
 
      Phaser phaser = new Phaser();
 
      int i = 0;
      while(i<100){
    	  
          new MyThread(phaser,100).start();
          new MyThread(phaser,3000).start();
          new MyThread(phaser,10000).start();
          i++;
          
      }

 
   }
}
 
class MyThread extends Thread {
 
   Phaser phaser;
   int sleep;
 
   MyThread(Phaser phaser, int sleep){
      this.phaser=phaser;
      this.sleep=sleep;
   }
 
   public void run() {
      phaser.register();
      System.out.println(this.getName() + " begin");
      try {
         Thread.sleep(sleep);
      } catch (Exception e) { 
      }
      phaser.arriveAndAwaitAdvance();
      System.out.println(this.getName() + " middle");
      try {
         Thread.sleep(sleep);
      } catch (Exception e) { 
      }
      phaser.arriveAndAwaitAdvance();
      System.out.println(this.getName() + " end");
      try {
          Thread.sleep(sleep);
       } catch (Exception e) { 
       }
      System.out.println(this.getName() + " ==over");
   }
}
