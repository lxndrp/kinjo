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

package com.wallabystreet.kinjo.common.transport.protocol.jxta;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

import net.jxta.document.Advertisement;
import net.jxta.document.AdvertisementFactory;
import net.jxta.document.MimeMediaType;
import net.jxta.protocol.ModuleClassAdvertisement;
import net.jxta.protocol.ModuleSpecAdvertisement;

import org.apache.axis.AxisEngine;
import org.apache.axis.AxisFault;
import org.apache.axis.MessageContext;
import org.apache.axis.client.Call;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.org.apache.bcel.internal.util.ClassLoader;
import com.wallabystreet.kinjo.common.transport.protocol.TransportDescriptor;


/**
 * @author Alexander Papaspyrou
 * @version $Revision$, $Date$
 */
public class Transport
		extends org.apache.axis.client.Transport
		implements TransportDescriptor {

	/**
	 * The default log facility for this class, using the <a
	 * href="http://jakarta.apache.org/commons/logging/">Jakarta "commons
	 * logging" API</a>.
	 * 
	 * @see org.apache.commons.logging.Log
	 * @see org.apache.commons.logging.LogFactory
	 */
	final private static Log log = LogFactory.getLog( Transport.class );

	public Transport() {
		this.transportName = "jxta";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.axis.client.Transport#setupMessageContextImpl(org.apache.axis.MessageContext,
	 *      org.apache.axis.client.Call, org.apache.axis.AxisEngine)
	 */
	@Override
	public void setupMessageContextImpl( MessageContext _context, Call _message,
			AxisEngine _engine )
			throws AxisFault {
		; // do nothing here.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.udo.kinjo.common.transport.TransportDescriptor#getServiceRelatedProperties(java.lang.String)
	 */
	public Properties getServiceRelatedProperties( final String serviceName,
			final String servicePackage ) {
		Properties p = new Properties();
		String path = null;

		path = servicePackage.replace( '.', '/' ) + "/" + serviceName + ".mcadv";
		ModuleClassAdvertisement mcadv = (ModuleClassAdvertisement)this
				.loadFromXML( path );
		p.put( "transport.jxta.adv.mc", mcadv );
		log.debug( "module class advertisement loaded successfully" );

		path = servicePackage.replace( '.', '/' ) + "/" + serviceName + ".msadv";
		ModuleSpecAdvertisement msadv = (ModuleSpecAdvertisement)this
				.loadFromXML( path );
		p.put( "transport.jxta.adv.ms", msadv );
		log.debug( "module spec advertisement loaded successfully" );

		Properties pp = new Properties();
		path = servicePackage.replace( '.', '/' ) + "/" + serviceName + ".pipe";
		try {
			pp.load( ClassLoader.getSystemResourceAsStream( path ) );
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		p.put( "transport.jxta.pipe.name", pp.get( "PipeName" ) );
		p.put( "transport.jxta.pipe.desc", pp.get( "PipeDesc" ) );
		p.put( "transport.jxta.pipe.type", pp.get( "PipeType" ) );
		log.debug( "transport properties loaded successfully" );

		return p;
	}

	/**
	 * @param path
	 * @return
	 */
	private Advertisement loadFromXML( String path ) {
		Reader r = null;
		r = new InputStreamReader( ClassLoader.getSystemResourceAsStream( path ) );

		Advertisement adv = null;
		try {
			adv = AdvertisementFactory.newAdvertisement( MimeMediaType.XMLUTF8, r );
		}
		catch (IOException e) {
			// TODO: do something useful here.
			e.printStackTrace();
		}
		return adv;
	}

}
