/*
 * Created on 13.05.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.wallabystreet.kinjo;


import org.apache.log4j.xml.DOMConfigurator;

import com.wallabystreet.kinjo.common.transport.RegistrationFailureException;
import com.wallabystreet.kinjo.common.transport.AbstractPeer;
import com.wallabystreet.kinjo.common.transport.protocol.jxta.JXTAPeer;
import com.wallabystreet.kinjo.common.transport.ws.MalformedServiceException;
import com.wallabystreet.kinjo.common.transport.ws.ServiceDescriptorFactory;



/**
 * @author tik TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class ServerPeer {

	public static void main(String[] args) {

		DOMConfigurator.configure( ClassLoader
				.getSystemResource( "META-INF/log4j.xml" ) );

		AbstractPeer myPeer = AbstractPeer.getInstance();

		try {
			myPeer.registerService( ServiceDescriptorFactory.getInstance().getServiceDescriptor("TestService", "edu.udo.kinjo.scheduling.sampleproto") );
		}
		catch ( RegistrationFailureException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch ( MalformedServiceException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int count = 0;

		while ( true ) {
			try {
				Thread.sleep( 1000 );
			}
			catch ( InterruptedException e ) {}
			System.out.print( "(S)" );
			count++;

			if ( count == 30 ) {
				System.out.println();
				count = 0;
			}
		}
	}
	
}
