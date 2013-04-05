package sample.ws.echo;

public class EchoImpl implements Echo {
	
	/* (non-Javadoc)
	 * @see edu.udo.kinjo.scheduling.sampleproto.Echo#echo(java.lang.String)
	 */
	public String echo (String _msg) throws java.rmi.RemoteException {
		System.out.println("entering echo(" + _msg.toString() + ")");
		return _msg;
	}
	
}