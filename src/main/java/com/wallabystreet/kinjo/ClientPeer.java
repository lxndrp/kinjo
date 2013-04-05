/*
 * Created on 13.05.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.wallabystreet.kinjo;


import java.net.URL;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import sample.ws.echo.Echo;

import com.wallabystreet.kinjo.common.transport.ServiceDiscoveryListener;
import com.wallabystreet.kinjo.common.transport.ServiceFactory;
import com.wallabystreet.kinjo.common.transport.AbstractPeer;
import com.wallabystreet.kinjo.common.transport.protocol.jxta.JXTAPeer;
import com.wallabystreet.kinjo.common.transport.ws.MalformedServiceException;
import com.wallabystreet.kinjo.common.transport.ws.ServiceDescriptorFactory;



/**
 * @author tik TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class ClientPeer
		implements ServiceDiscoveryListener {

	final private static Logger log = Logger.getLogger( ClientPeer.class );

	AbstractPeer myPeer;

	Enumeration en = null;

	public static void main(String[] args) {

		System
				.setProperty( "java.protocol.handler.pkgs",
						"edu.udo.kinjo.common.transport.protocol|org.apache.axis.transport" );

		DOMConfigurator.configure( ClassLoader
				.getSystemResource( "META-INF/log4j.xml" ) );

		ClientPeer cliPeer = new ClientPeer();
		cliPeer.start();

	}

	public void start() {
		AbstractPeer myPeer = AbstractPeer.getInstance();

		try {
			myPeer.discoverServiceProviders( ServiceDescriptorFactory
					.getInstance().getServiceDescriptor( "TestService",
							"sample.ws.echo" ) );
		}
		catch ( MalformedServiceException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while ( true ) {
			try {
				Thread.sleep( 1000 );
			}
			catch ( InterruptedException e ) {}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.udo.kinjo.common.transport.ServiceDiscoveryListener#notify(java.util.Iterator)
	 */
	public void notify(Iterator<URL> discoveredServices) {
		URL endpoint = discoveredServices.next();
		Echo stub = null;
		try {
			stub = ServiceFactory.<Echo> getService( ServiceDescriptorFactory
					.getInstance().getServiceDescriptor( "TestService",
							"sample.ws.echo" ), endpoint );
		}
		catch ( MalformedServiceException e1 ) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			System.out.println( stub.echo( "TEST" ) );
		}
		catch ( RemoteException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
