//Done by Priyanka Bangalore Jayadeva
//Student ID : 1001512908
package main;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

@SuppressWarnings("unused")
public class Advisor {
	@SuppressWarnings("static-access")
	static public void main(String args[]) throws IOException{
		ReceiveMessageInterface rmiServer;
		Registry registry;

		try {
			registry = LocateRegistry.getRegistry("localhost");
			rmiServer = (ReceiveMessageInterface) (registry.lookup("rmiServer"));
			// call the remote method
			while (true) {
				try {
					new Thread().sleep(3000);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
				ArrayList<Request> reqs = rmiServer.getRequests();
				for (int i = 0; i < reqs.size(); i++) {
					Request req = reqs.get(i);
					if(req.status==false){ //added
						 
						req.status=true;
						req.decision = getRandomBoolean();
						rmiServer.SubmitDecision(req);	
						System.out.println("Advisor has sent the decision for Student: "+req.name+" to the MQS");
					}
					
				}

			}

		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			System.err.println(e);
		}
	}

	public static boolean getRandomBoolean() {
		Random random = new Random();
		return random.nextBoolean();
	}

}
