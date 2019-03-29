//Done by Priyanka Bangalore Jayadeva
//Student ID : 1001512908

package main;

import java.rmi.*;
import java.rmi.registry.*;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import java.io.IOException;
import java.net.*;


public class RmiClient{
  
static public void main(String args[]) throws IOException{
  ReceiveMessageInterface rmiServer;
  Registry registry;
  
  
  do{
  Scanner scanner = new Scanner(System.in);
  System.out.println("Enter students name:");
  String name = scanner.nextLine();
  
  System.out.println("Enter course name:");
  String course = scanner.nextLine();

  
  
 
  try{
  registry=LocateRegistry.getRegistry("localhost");
  rmiServer=(ReceiveMessageInterface)(registry.lookup("rmiServer"));
  // call the remote method
  Request request = new Request();
  request.name = name;
  request.course = course;
  rmiServer.receiveMessage(request);
  
  System.out.println("Request is sent to the MQS");
  
  System.out.println("------------------------------------------------------------------");
  
  }
  catch(RemoteException e){
  e.printStackTrace();
  }
  catch(NotBoundException e){
  System.err.println(e);
  }
  }while(true);
  }


  

} 