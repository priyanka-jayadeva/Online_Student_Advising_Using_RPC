//Done by Priyanka Bangalore Jayadeva
//Student ID : 1001512908
package main;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ReceiveMessageInterface extends Remote{
	  void receiveMessage(Request request) throws RemoteException,IOException;
	  void SubmitDecision(Request request) throws RemoteException,IOException;
	  void RemoveRequest(Request request) throws RemoteException,IOException;
	  ArrayList<Request> getRequests() throws RemoteException,IOException;
}