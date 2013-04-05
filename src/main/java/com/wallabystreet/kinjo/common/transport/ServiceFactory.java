/*
 *  | . _ . _
 *  |(|| )|(_) - A P2P Technology Based Grid Framework
 *        /
 *  ---
 *
 *  Copyright (c) 2005  Alexander Papaspyrou
 *
 *
 *  This file is part of kinjo.
 *
 *  kinjo is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  kinjo is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with kinjo; if not, write to the Free Software
 *  Foundation, 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 */

package com.wallabystreet.kinjo.common.transport;


import java.net.URL;

import javax.xml.rpc.ServiceException;

import org.apache.axis.EngineConfiguration;
import org.apache.axis.SimpleTargetedChain;
import org.apache.axis.client.Call;
import org.apache.axis.configuration.EngineConfigurationFactoryFinder;
import org.apache.axis.configuration.SimpleProvider;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sample.ws.echo.EchoService;
import sample.ws.echo.EchoServiceLocator;

import com.wallabystreet.kinjo.common.transport.protocol.jxta.JXTAWebServiceHandler;
import com.wallabystreet.kinjo.common.transport.protocol.jxta.Transport;
import com.wallabystreet.kinjo.common.transport.ws.ServiceDescriptor;



/**
 * @author Alexander Papaspyrou
 * @version $Revision$, $Date$
 *
 */
public class ServiceFactory {
	
	/**
	 * The default log facility for this class, using the <a href="http://jakarta.apache.org/commons/logging/">Jakarta "commons logging" API</a>.
	 *
	 * @see org.apache.commons.logging.Log
	 * @see org.apache.commons.logging.LogFactory
	 */
	final private static Log log = LogFactory.getLog( ServiceFactory.class );

	/**
	 * @param <T>
	 * @param d
	 * @param endpoint
	 * @return
	 */
	public static <T> T getService(ServiceDescriptor d, URL endpoint) {

		Call.addTransportPackage( "edu.udo.kinjo.common.transport" );
		Call.setTransportForProtocol( "jxta", Transport.class );
		log.info( "transport added" );

		EngineConfiguration defaultConfig = EngineConfigurationFactoryFinder
				.newFactory().getClientEngineConfig();
		SimpleProvider config = new SimpleProvider( defaultConfig );

		SimpleTargetedChain c = new SimpleTargetedChain( new JXTAWebServiceHandler() );
		config.deployTransport( "jxta", c );

		EchoService s = new EchoServiceLocator( config );

		T stub = null;
		try {
			stub = (T)s.getTestService( endpoint );
		}
		catch ( ServiceException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stub;
	}

}
