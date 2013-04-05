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


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import net.jxta.endpoint.ByteArrayMessageElement;
import net.jxta.util.JxtaBiDiPipe;

import org.apache.axis.AxisFault;
import org.apache.axis.MessageContext;
import org.apache.axis.handlers.BasicHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * TODO: please comment.
 * 
 * @author Alexander Papaspyrou
 * @version $Revision$, $Date$
 */
public class JXTAWebServiceHandler
		extends BasicHandler {

	/**
	 * The serial version UID of this class.
	 * 
	 * @see java.io.Serializable
	 */
	final private static long serialVersionUID = 7076341297839088554L;

	
	/**
	 * The default log facility for this class, using the <a
	 * href="http://jakarta.apache.org/commons/logging/">Jakarta "commons
	 * logging" API</a>.
	 * 
	 * @see org.apache.commons.logging.Log
	 * @see org.apache.commons.logging.LogFactory
	 */
	final private static Log log = LogFactory.getLog( JXTAWebServiceHandler.class );

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.axis.Handler#invoke(org.apache.axis.MessageContext)
	 */
	public void invoke( MessageContext messageContext )
			throws AxisFault {

		// create and open a connection to the endpoint peer
		String target = messageContext.getStrProp( MessageContext.TRANS_URL );
		URL u = null;
		try {
			u = new URL( target );
		}
		catch (MalformedURLException e) {
			; // do nothing here
		}

		URLConnection c = null;
		try {
			c = u.openConnection();
		}
		catch (IOException e) {
			log.error( "could not create connection", e );
			throw AxisFault.makeFault( e );
		}

		JxtaBiDiPipe pipe = null;
		try {
			c.connect();
			pipe = ( (JXTAURLConnection)c ).getConnectionPipe();
		}
		catch (IOException e) {
			log.error( "could not connect", e );
			throw AxisFault.makeFault( e );
		}

		// extract the SOAP request from the message context
		net.jxta.endpoint.Message transportMessage = new net.jxta.endpoint.Message();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			messageContext.getRequestMessage().writeTo( baos );
		}
		catch (Exception e) {
			log.error( "could not serialize message", e );
			throw AxisFault.makeFault( e );
		}

		// wrap the SOAP request into a JXTA transport message
		ByteArrayMessageElement bame =
			new ByteArrayMessageElement( "message", null, baos.toByteArray(), null );
		transportMessage.addMessageElement( bame );

		try {
			// send the message over the connection pipe
			pipe.sendMessage( transportMessage );
		}
		catch (IOException e) {
			log.error( "could not send message", e );
			throw AxisFault.makeFault( e );
		}

		// reset the transport message and wait for the SOAP response
		transportMessage = null;
		try {
			/*
			 * XXX: At the moment, we wait forever (until the result message
			 * arrives, that is) here. This seems to be a bad idea, since the
			 * response message might take ages to arrive. Therefore, a timeout
			 * should be introduced here.
			 */
			transportMessage = pipe.getMessage( 0 );
		}
		catch (InterruptedException e) {
			log.error( "interrupt occured during getMessage()", e );
			throw AxisFault.makeFault( e );
		}

		// unwrap the SOAP response
		ByteArrayMessageElement msgString =
			(ByteArrayMessageElement)transportMessage.getMessageElement( "message" );
		org.apache.axis.Message responseMessage =
			new org.apache.axis.Message( msgString.toString() );

		// store the SOAP response in the message context
		messageContext.setResponseMessage( responseMessage );
	}
}
