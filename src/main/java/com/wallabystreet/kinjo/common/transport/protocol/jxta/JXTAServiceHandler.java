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
import java.net.SocketException;

import javax.xml.soap.SOAPException;

import net.jxta.endpoint.ByteArrayMessageElement;
import net.jxta.endpoint.Message;
import net.jxta.pipe.PipeMsgEvent;
import net.jxta.pipe.PipeMsgListener;
import net.jxta.protocol.PipeAdvertisement;
import net.jxta.util.JxtaBiDiPipe;
import net.jxta.util.JxtaServerPipe;

import org.apache.axis.AxisFault;
import org.apache.axis.MessageContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wallabystreet.kinjo.common.Stoppable;
import com.wallabystreet.kinjo.common.transport.RegistrationFailureException;
import com.wallabystreet.kinjo.common.transport.ws.ServiceDescriptor;
import com.wallabystreet.kinjo.common.transport.ws.WSEngine;


/**
 * @author Alexander Papaspyrou
 * @version $Revision$, $Date$
 * 
 */
class JXTAServiceHandler
		implements com.wallabystreet.kinjo.common.transport.ServiceHandler {

	/**
	 * The default log facility for this class, using the <a
	 * href="http://jakarta.apache.org/commons/logging/">Jakarta "commons
	 * logging" API</a>.
	 * 
	 * @see org.apache.commons.logging.Log
	 * @see org.apache.commons.logging.LogFactory
	 */
	final private static Log log = LogFactory.getLog( JXTAServiceHandler.class );

	private boolean stop = false;

	private ServiceDescriptor serviceDescriptor;

	private JxtaServerPipe serverPipe;

	/**
	 * Creates a new ServiceHandler object, which initializes a
	 * <code>JxtaServerPipe</code> that blocks until a connection is
	 * established.
	 * 
	 * @param s
	 *            The <code>ServiceDescriptor</code> which describes the
	 * @param p
	 * @throws RegistrationFailureException
	 */
	public JXTAServiceHandler(ServiceDescriptor s, PipeAdvertisement p)
			throws RegistrationFailureException {
		this.serviceDescriptor = s;
		try {
			this.serverPipe = new JxtaServerPipe( JXTAPeer.getInstance().getGroup(), p );
		}
		catch (IOException e) {
			log.debug( "server pipe creation failed", e );
			throw new RegistrationFailureException( e );
		}
		try {
			this.serverPipe.setPipeTimeout( 0 );
		}
		catch (SocketException e) {
			log.debug( "protocol error occured", e );
			throw new RegistrationFailureException( e );
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		JxtaBiDiPipe messagePipe = null;
		while (!this.stop) {
			try {
				messagePipe = this.serverPipe.accept();
			}
			catch (IOException e) {
				log.warn( this.serviceDescriptor.getName()
						+ ": I/O error while waiting for connection", e );
			}

			// Create a new session
			Thread handler = new Thread( new JXTAConnectionHandler( messagePipe ) );
			handler.setName( this.serviceDescriptor.getName() + "(connection handler)" );
			handler.start();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wallabystreet.kinjo.common.Stoppable#stop()
	 */
	public void stop() {
		this.stop = true;
	}

	private class JXTAConnectionHandler
			implements Stoppable, PipeMsgListener {

		private boolean stop = false;

		private JxtaBiDiPipe messagePipe;

		public JXTAConnectionHandler(JxtaBiDiPipe p) {
			this.messagePipe = p;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			this.messagePipe.setMessageListener( this );
			while (!this.stop) {
				; // do nothing here.
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.wallabystreet.kinjo.common.Stoppable#stop()
		 */
		public void stop() {
			this.stop = true;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see net.jxta.pipe.PipeMsgListener#pipeMsgEvent(net.jxta.pipe.PipeMsgEvent)
		 */
		public void pipeMsgEvent( PipeMsgEvent event ) {
			ByteArrayMessageElement msgString = (ByteArrayMessageElement)event.getMessage()
					.getMessageElement( "message" );

			MessageContext m = WSEngine.getInstance().createMessageContext();
			/*
			 * XXX: The following line takes the received data as a whole and
			 * creates a new SOAP <code>Message</code> object from it.
			 * Unfortunately, this simple approach completely breaks SOAP
			 * attachments.
			 */
			org.apache.axis.Message msg = new org.apache.axis.Message( msgString.toString() );

			m.setRequestMessage( msg );
			m.setTransportName( "jxta" );

			try {
				m.setTargetService( serviceDescriptor.getName() );
			}
			catch (AxisFault e) {
				// TODO: not implemented yet
			}

			try {
				log.debug( "invoking web service engine with given message" );
				WSEngine.getInstance().invoke( m );
				log.debug( "retrieving response" );
				msg = m.getResponseMessage();
			}
			catch (AxisFault af) {
				log.debug( "invoke failed, creating a new (error) response message", af );
				msg = new org.apache.axis.Message( af );
				msg.setMessageContext( m );
			}

			Message message = new Message();

			// send the SOAP message
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				msg.writeTo( baos );
			}
			catch (SOAPException e) {
				log.fatal( "response message serialization failed", e );
			}
			catch (IOException e) {
				log.fatal( "response message serialization failed", e );
			}

			// Send the whole request message, including attachments
			ByteArrayMessageElement msgElement = new ByteArrayMessageElement( "message", null, baos
					.toByteArray(), null );
			message.addMessageElement( msgElement );

			try {
				this.messagePipe.sendMessage( message );
			}
			catch (IOException e) {
				log.fatal( "sending response message failed", e );
				/*
				 * If this exception is raised, something really bad has
				 * happened. Usually, JXTA is not configured properly,
				 * networking is down or other things beyond the responsibility
				 * of this class (i.e. acts of god) have happened.
				 * 
				 * At the moment, this problem is frankly ignored.
				 */
				; // XXX: do something useful here.
			}
		}
	}
}
