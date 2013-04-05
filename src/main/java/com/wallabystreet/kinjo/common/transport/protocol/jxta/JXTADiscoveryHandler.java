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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import net.jxta.discovery.DiscoveryService;
import net.jxta.peergroup.PeerGroup;
import net.jxta.protocol.ModuleSpecAdvertisement;
import net.jxta.protocol.PipeAdvertisement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wallabystreet.kinjo.common.Sink;
import com.wallabystreet.kinjo.common.transport.ws.ServiceDescriptor;


/**
 * @author Alexander Papaspyrou
 * @version $Revision$, $Date$
 */
public class JXTADiscoveryHandler
		implements com.wallabystreet.kinjo.common.transport.DiscoveryHandler {

	/**
	 * The default log facility for this class, using the <a
	 * href="http://jakarta.apache.org/commons/logging/">Jakarta "commons
	 * logging" API</a>.
	 * 
	 * @see org.apache.commons.logging.Log
	 * @see org.apache.commons.logging.LogFactory
	 */
	final private static Log log = LogFactory.getLog( JXTADiscoveryHandler.class );

	/**
	 * 
	 */
	private boolean stop = false;

	/**
	 * 
	 */
	private Sink<URL> sink;

	/**
	 * 
	 */
	final private PeerGroup peerGroup;

	/**
	 * 
	 */
	final private DiscoveryService discoveryService;

	/**
	 * 
	 */
	final private ServiceDescriptor serviceDescriptor;

	/**
	 * 
	 */
	final private ModuleSpecAdvertisement moduleSpecAdvertisement;

	/**
	 * @param p
	 * @param d
	 */
	JXTADiscoveryHandler(PeerGroup p, ServiceDescriptor d) {
		super();
		this.peerGroup = p;
		this.discoveryService = this.peerGroup.getDiscoveryService();
		this.serviceDescriptor = d;
		this.moduleSpecAdvertisement = (ModuleSpecAdvertisement)this.serviceDescriptor
				.getProperty( "transport.jxta.adv.ms" );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		this.stop = false;
		Set<ModuleSpecAdvertisement> s = null;
		while (!stop) {
			// start an advertisement discovery.
			s = fetchAdvertisementsFromDiscoveryService();
			if (s != null) {
				for (ModuleSpecAdvertisement m : s) {
					this.sink.put( this.createURLFromAdvertisement( m ) );
				}
			}
			try {
				/*
				 * TODO: make the sleep time configurable.
				 */
				Thread.sleep( 1000 );
			}
			catch (InterruptedException e) {
				// TODO: do something useful here.
				e.printStackTrace();
			}
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
	 * @see com.wallabystreet.kinjo.common.transport.DiscoveryHandler#setParent(com.wallabystreet.kinjo.common.Sink)
	 */
	public void setSink( Sink<URL> s ) {
		this.sink = s;
	}

	/**
	 * Discovers new advertisements and extracts them from the local discovery
	 * service.
	 * 
	 * @return A <code>Set</code> of discovered advertisements.
	 */
	private Set<ModuleSpecAdvertisement> fetchAdvertisementsFromDiscoveryService() {
		/*
		 * TODO: check how to set the threshold, or -- at least -- make it
		 * configurable.
		 */
		discoveryService.getRemoteAdvertisements( null, DiscoveryService.ADV, "Name",
				this.moduleSpecAdvertisement.getName(), 10, null );
		Enumeration discoveredAdvertisements = null;
		try {
			discoveredAdvertisements = discoveryService.getLocalAdvertisements(
					DiscoveryService.ADV, "Name", this.moduleSpecAdvertisement.getName() );
		}
		catch (IOException e) {
			// TODO: do something useful here.
			e.printStackTrace();
		}
		Set<ModuleSpecAdvertisement> s = null;
		if (discoveredAdvertisements != null) {
			s = new HashSet<ModuleSpecAdvertisement>();
			while (discoveredAdvertisements.hasMoreElements()) {
				ModuleSpecAdvertisement element = (ModuleSpecAdvertisement)discoveredAdvertisements
						.nextElement();
				s.add( element );
			}
		}
		return s;
	}

	/**
	 * Creates a new endpoint address for a service from a given module spec
	 * advertisement. The style of the created <code>URL</code> object is
	 * <quote> <code>jxta://</code><i>pipeID</i><code>/</code><i>serviceName</i>
	 * </quote>
	 * 
	 * @see java.lang.URL
	 * 
	 * @param m The advertisement from which the URL should be created.
	 * @return The endpoint address as an <code>URL</code> object.
	 */
	private URL createURLFromAdvertisement( ModuleSpecAdvertisement m ) {
		PipeAdvertisement p = m.getPipeAdvertisement();
		URL endpoint = null;
		try {
			endpoint = new URL( "jxta://" + ( p.getID().toString().substring( 9 ) ) + "/"
					+ serviceDescriptor.getName() );
		}
		catch (MalformedURLException e) {
			// just log this exception, since it shouldn't be raised at all.
			if (log.isErrorEnabled()) {
				String msg = "unable to create URL";
				log.error( msg, e );
			}
		}
		return endpoint;
	}

}
