package sample.ws.echo;

public interface Echo extends java.rmi.Remote {
	
	public String echo (String _msg) throws java.rmi.RemoteException;

}