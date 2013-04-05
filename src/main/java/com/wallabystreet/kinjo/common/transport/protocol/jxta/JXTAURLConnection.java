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
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import net.jxta.document.AdvertisementFactory;
import net.jxta.id.ID;
import net.jxta.peer.PeerID;
import net.jxta.peergroup.PeerGroup;
import net.jxta.pipe.PipeService;
import net.jxta.protocol.PipeAdvertisement;
import net.jxta.util.JxtaBiDiPipe;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wallabystreet.kinjo.common.transport.AbstractPeer;
import com.wallabystreet.kinjo.common.transport.protocol.UnsupportedTransportException;


/**
 * This class provides a basic implementation of the <code>URLConnection</code>
 * type for the <a href="http://www.jxta.org">JXTA</a> network.
 * <p />
 * The connection is established via the {@link #connect()} method, which sets
 * up a JXTA bidirectional, reliable communication pipe to a JXTA peer. The
 * identity of the connection target is denoted by an ID, which is encoded in
 * the <code>URL</code> object given at class construction time. The syntax
 * and semantics of the URL expected by this class is described in the
 * corresponding URL handler for JXTA-style identifiers.
 * <p />
 * After calling the {@link #connect()} method, one can obtain a
 * <code>JxtaBiDiPipe</code> instance for exchanging messages with the other
 * endpoint via the {@link #getConnectionPipe()} method. However, until a
 * connection is established successfully, this method will always return
 * <code>null</code>.
 * <p />
 * Note that this class heavily relies on the functionality of the JXTA
 * implementation of the <code>AbstractPeer</code> interface and cannot exist without
 * it. Besides that, this early implementation does not support any of the
 * standard facilities for stream handling.
 * 
 * @see java.net.URLConnection
 * @see com.wallabystreet.kinjo.common.transport.jxta.Handler
 * @see com.wallabystreet.kinjo.common.transport.jxta.JXTAPeer
 * @see net.jxta.util.JxtaBiDiPipe
 * 
 * @author Alexander Papaspyrou
 * @version $Revision$, $Date$
 */
public class JXTAURLConnection
		extends java.net.URLConnection {

	/**
	 * The default log facility for this class, using the <a
	 * href="http://jakarta.apache.org/commons/logging/">Jakarta "commons
	 * logging" API</a>.
	 * 
	 * @see org.apache.commons.logging.Log
	 * @see org.apache.commons.logging.LogFactory
	 */
	final private static Log log = LogFactory.getLog( JXTAURLConnection.class );

	/**
	 * Stores the peer group within which the connection is to be established.
	 */
	private PeerGroup connectionGroup = null;

	/**
	 * Keeps an instance of the pipe that represents the connection.
	 */
	private JxtaBiDiPipe connectionPipe = null;

	/**
	 * Creates a new instance of this class, using the given parameters.
	 * 
	 * @param endpoint
	 *            The URL of the target peer this class should connect to (see
	 *            the class description for details on the format of the URL).
	 */
	protected JXTAURLConnection(URL endpoint) {
		super( endpoint );
	}

	/**
	 * Mutator method for the <code>connectionGroup</code> field.
	 * 
	 * @param connectionGroup
	 *            The new value of the <code>connectionGroup</code> field.
	 */
	public void setConnectionGroup( PeerGroup connectionGroup ) {
		this.connectionGroup = connectionGroup;
	}

	/**
	 * Accessor method for the <code>connectionPipe</code> field.
	 * 
	 * @return The <code>connectionPipe</code> field of this type.
	 */
	public JxtaBiDiPipe getConnectionPipe() {
		return connectionPipe;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.net.URLConnection#connect()
	 */
	@Override
	public void connect()
			throws IOException {
		// let the AbstractPeer class setup this connection as appropriate
		AbstractPeer peer = com.wallabystreet.kinjo.common.transport.protocol.jxta.JXTAPeer.getInstance();
		try {
			peer.setupConnection( this );
			log.debug( "external connection setup complete" );
		}
		catch (UnsupportedTransportException e) {
			/*
			 * This exception should not ever be raised, since we _know_ that a
			 * JXTAPeer supports JXTAURLConnection objects for transport
			 * configuration. Still, in case something weird happens, we log it.
			 */
			if (log.isErrorEnabled()) {
				String msg = "unforeseen transport rejection";
				log.error( msg, e );
			}
		}

		// convert the provided URL to a JXTA-compatible URI
		URI uri = null;
		try {
			uri = new URI( ID.URIEncodingName, ID.URNNamespace + ":" + this.getURL().getHost(),
					null );
			log.debug( "uri is" + uri.toString() );
		}
		catch (URISyntaxException e) {
			/*
			 * This exception should not ever be raised, since we _know_ that
			 * our URI construction is correct. Still, in case something weird
			 * happens, we log it.
			 */
			String msg = "unable to create URL";
			log.error( msg, e );
		}

		// create and setup the communication pipe
		PipeAdvertisement a = (PipeAdvertisement)AdvertisementFactory
				.newAdvertisement( PipeAdvertisement.getAdvertisementType() );
		a.setType( PipeService.UnicastType );
		log.debug( "JXTA pipe setup complete" );

		// attempt to establish a connection
		this.connectionPipe = new JxtaBiDiPipe();
		try {
			this.connectionPipe.connect( this.connectionGroup, (PeerID)ID.create( uri ), a, 5000,
					null, true );
			log.debug( "connection established successfully" );
		}
		catch (IOException e) {
			this.connectionPipe = null;
			String msg = "can't establish connection";
			log.error( msg, e );
			throw e;
		}
	}
}
