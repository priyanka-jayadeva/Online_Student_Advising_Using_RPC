//Done by Priyanka Bangalore Jayadeva
//Student ID : 1001512908
package main;

import java.rmi.*;
import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;
import java.util.ArrayList;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

public class Server extends java.rmi.server.UnicastRemoteObject implements ReceiveMessageInterface {
	String address;
	Registry registry;
	ArrayList<Request> requests = new ArrayList<>();
	private static final String FNAME = "objectStorage.ser";
	FileInputStream is = null;
	ObjectInputStream ois = null;
	FileOutputStream fs= null;
	ObjectOutputStream oos=null;
		
	public static void  writeObject(ArrayList<Request> requests) throws IOException 
	{
		//serializing the request object
				FileOutputStream fs = new FileOutputStream(FNAME);
				ObjectOutputStream oos = new ObjectOutputStream(fs);
				oos.writeObject(requests);
				oos.close();
			    fs.close();  
	}
	
	public void receiveMessage(Request request) throws IOException {
		
		//adding the request to the ArrayList
		requests.add(request);
		writeObject(requests);
		
		    		    
		
		System.out.println("Student " + request.name + " sent request for " + request.course);
	}

	public Server() throws RemoteException {
	
		
		try {
			address = (InetAddress.getLocalHost()).toString();
			registry = LocateRegistry.createRegistry(1099);
			registry.rebind("rmiServer", this);
			
		
			//initialize
			FileInputStream is = new FileInputStream(FNAME);
			ObjectInputStream ois = new ObjectInputStream(is);
			
			
			
			
			//deserialize the messages as ArrayList
			requests = (ArrayList<Request>) ois.readObject();
			
			ois.close();
		    is.close();  
			
			//intiliaze to empty once messages retrieved
			writeObject(null);
			
		} catch (RemoteException e) {
			System.out.println("Remote exception" + e);
		}
		catch (Exception e) {
			
			requests = new ArrayList<>();
		
		}
		
	}

	static public void main(String args[]) {
		try {
			Server server = new Server();
			System.out.println("Server is running...");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public ArrayList<Request> getRequests() throws RemoteException {
		return requests;
	}

	@Override
	public void SubmitDecision(Request request) throws RemoteException {
	    for (int i=0; i < requests.size(); i++) {
	    	Request myreq = requests.get(i);
	    	if(myreq.name.equals(request.name) && myreq.course.equals(request.course))
	    	{
	    		//requests.remove(myreq);
	    		myreq.decision=request.decision;
	    		myreq.status=request.status;
	    	}
	    }
	    
	}
	
	@Override
	public void RemoveRequest(Request request) throws RemoteException {
		for (int i=0; i < requests.size(); i++) {
	    	Request myreq = requests.get(i);
	    	if(myreq.name.equals(request.name) && myreq.course.equals(request.course))
	    	{
	    		requests.remove(myreq);
	    		
	    	}
	    }
		try{   
			writeObject(requests);
		      
		    }catch(Exception e){
					e.printStackTrace();
		    }
	}
	
}

//http://docs.oracle.com/javase/7/docs/technotes/guides/rmi/hello/hello-world.html
// https://www.roseindia.net/java/network/rmi-client-and-rmi-server-implementation.shtml