/*
 *  | . _ . _
 *  |(|| )|(_) - A P2P Technology Based Grid Framework
 *        /
 *  ---
 *
 *  Copyright (c) 2005  Alexander Papaspyrou, Tim Kettler
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


import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;

import net.jxta.discovery.DiscoveryService;
import net.jxta.exception.PeerGroupException;
import net.jxta.id.IDFactory;
import net.jxta.peergroup.PeerGroup;
import net.jxta.peergroup.PeerGroupFactory;
import net.jxta.protocol.ModuleClassAdvertisement;
import net.jxta.protocol.ModuleSpecAdvertisement;
import net.jxta.protocol.PipeAdvertisement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wallabystreet.kinjo.common.transport.AbstractPeer;
import com.wallabystreet.kinjo.common.transport.Discovery;
import com.wallabystreet.kinjo.common.transport.DiscoveryHandler;
import com.wallabystreet.kinjo.common.transport.RegistrationFailureException;
import com.wallabystreet.kinjo.common.transport.protocol.UnsupportedTransportException;
import com.wallabystreet.kinjo.common.transport.ws.DeploymentException;
import com.wallabystreet.kinjo.common.transport.ws.ServiceDescriptor;
import com.wallabystreet.kinjo.common.transport.ws.WSEngine;


/**
 * This class implements the <code>AbstractPeer</code> interface for the <a
 * href="http://www.jxta.org">JXTA</a> P2P Protocol Family.
 * <p />
 * 
 * 
 * A peer is the basic building block of the P2P network. It is a wrapper around
 * the Jxta-Library on which this framwork is build. It introduces the concept
 * of protocols that are used by the peers to communicate with each other.
 * <p />
 * Since there can be only exactly one AbstractPeer instance per running VM the
 * class is implemented as a singleton. The one and only instance is obtained
 * via the getInstance() method.
 * <p />
 * Protocols can be registered and unregistered at a running peer. When a
 * protocol is registered the peer starts listening for incomming connections
 * for this protocol and advertises the fact that it is capable to speak the
 * registered protocol to the network.
 * <p />
 * A client peer can search for other peers on the network that speak a given
 * protocol and initiate a communication session with found peers.
 * <p />
 * TODO: Explain that each running peer needs it own home directory. <br />
 * TODO: Implement and explain configuration.
 * 
 * @author Tim Kettler
 * @version $Revision: 10 $, $Date: 2005-07-17 18:11:26 +0200 (Sun, 17 Jul 2005)
 *          $
 */
final public class Peer extends AbstractPeer {

	/**
	 * The default log facility for this class, using the <a
	 * href="http://jakarta.apache.org/commons/logging/">Jakarta "commons
	 * logging" API</a>.
	 * 
	 * @see org.apache.commons.logging.Log
	 * @see org.apache.commons.logging.LogFactory
	 */
	private static final Log log = LogFactory.getLog(Peer.class);

	/**
	 * Holds the only instance of this class, corresponding to the GoF
	 * <i>Singleton</i> design pattern.
	 */
	private static JXTAPeer instance;

	/**
	 * 
	 */
	private PeerGroup group;

	/**
	 * 
	 */
	private Map<ServiceDescriptor, JXTAServiceHandler> serviceHandlers;

	/**
	 * 
	 */
	protected Peer() {
		File f = null;
		try {
			super.loadProperties(f);
		}
		catch (InvalidPropertiesFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.serviceHandlers = new HashMap<ServiceDescriptor, JXTAServiceHandler>();
		this.initialize();
		/*
		 * XXX: At the moment, this class is not configurable. Therefore, the
		 * peer group in which this JXTAPeer runs is never put into the
		 * AbstractPeer class' property list.
		 * 
		 * However, since other classes depend on this, we have to force the
		 * value into the Properties object, which -- of course -- is bad style.
		 */
		super.getProperties().put("jxta.peerGroup", this.group);
	}

	/**
	 * Returns the only instance of this class to the caller, according to the
	 * GoF <i>Singleton</i> design pattern.
	 * 
	 * @return The only instance of this class.
	 */
	public static JXTAPeer getInstance() {
		if (instance == null) {
			instance = new JXTAPeer();
		}
		return instance;
	}

	/**
	 * Accessor method for the <code>group</code> field.
	 * 
	 * @return The <code>group</code> field of this type.
	 */
	PeerGroup getGroup() {
		return this.group;
	}

	/**
	 * 
	 */
	@Override
	protected void initialize() {
		try {
			/*
			 * TODO: Here, we just create a new JXTA NetPeerGroup. However, this
			 * should be configurable, so that users can run their JXTAPeer
			 * instance within some private peer group.
			 */
			this.group = PeerGroupFactory.newNetPeerGroup();
			this.group.startApp(null);
		}
		catch (PeerGroupException e) {
			log.fatal("error during net peer group creation", e);
			/*
			 * If this exception is raised, something really bad has happened.
			 * Usually, JXTA is not configured properly, networking is down or
			 * other things beyond the responsibility of this class (i.e. acts
			 * of god) have happened.
			 * 
			 * At the moment, this problem is frankly ignored.
			 */
			; // XXX: do something useful here.
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wallabystreet.kinjo.common.transport.protocol.jxta.iPeer#registerService
	 * (com.wallabystreet.kinjo.common.transport.ws.ServiceDescriptor)
	 */
	@Override
	public void registerService(ServiceDescriptor s)
			throws RegistrationFailureException {

		/* Get the peer group's discovery service. */
		DiscoveryService ds = this.group.getDiscoveryService();

		/*
		 * Obtain the module class advertisement for the requested service from
		 * its service descriptor and try to publish it to the JXTA network.
		 */
		ModuleClassAdvertisement mca = ( ModuleClassAdvertisement )s
				.getProperty("transport.jxta.adv.mc");
		try {
			ds.publish(mca);
			log
					.debug("module class advertisement publishing (local) successful");
		}
		catch (IOException e) {
			String msg = "publishing of the ModuleClassAdvertisement failed";
			log.debug(msg, e);
			throw new RegistrationFailureException(e);
		}
		ds.remotePublish(mca);
		log.debug("module class advertisement publishing (remote) successful");

		/*
		 * Create the pipe advertisement for the given service, respecting the
		 * peer group the system is connected to.
		 */
		PipeAdvertisement pa = Utilities.createPipeAdvertisement(s);
		pa.setPipeID(IDFactory.newPipeID(this.group.getPeerGroupID()));

		/*
		 * Obtain the module spec advertisement for the requested service from
		 * its service descriptor, add the service's pipe advertisement to it
		 * and try to publish it to the JXTA network.
		 */
		ModuleSpecAdvertisement msa = ( ModuleSpecAdvertisement )s
				.getProperty("transport.jxta.adv.ms");
		msa.setPipeAdvertisement(pa);
		try {
			ds.publish(msa);
			log
					.debug("module spec advertisement publishing (local) successful");
		}
		catch (IOException e) {
			String msg = "publishing of the ModuleSpecAdvertisement failed";
			log.debug(msg, e);
			throw new RegistrationFailureException(e);
		}
		ds.remotePublish(msa);
		log.debug("module spec advertisement publishing (remote) successful");

		// deploy the service to the WS engine.
		try {
			WSEngine.getInstance().deploy(s);
		}
		catch (DeploymentException e) {
			log.debug("service deployment failed", e);
			throw new RegistrationFailureException(e);
		}

		/*
		 * Create a listener thread for the JXTA server pipe, using a
		 * ServiceHandler instance, name it and keep the object in the classes
		 */
		JXTAServiceHandler h = new JXTAServiceHandler(s, pa);
		Thread handler = new Thread(h);
		handler.setName(s.getName() + " (service handler)");
		this.serviceHandlers.put(s, h);
		handler.start();
		log.debug("service registration complete");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.wallabystreet.kinjo.common.transport.protocol.jxta.iPeer#
	 * deregisterService
	 * (com.wallabystreet.kinjo.common.transport.ws.ServiceDescriptor)
	 */
	@Override
	public void deregisterService(ServiceDescriptor s) {
		; // TODO: not implemented yet
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.wallabystreet.kinjo.common.transport.protocol.jxta.iPeer#
	 * discoverServiceProviders
	 * (com.wallabystreet.kinjo.common.transport.ws.ServiceDescriptor,
	 * java.beans.PropertyChangeListener)
	 */
	@Override
	public Discovery discoverServiceProviders(ServiceDescriptor s, PropertyChangeListener l) {
		DiscoveryHandler h = new com.wallabystreet.kinjo.common.transport.protocol.jxta.JXTADiscoveryHandler(
				this.group, s);

		Discovery d = new Discovery(h);
		d.addObserver(l);

		h.setSink(d);

		Thread handler = new Thread(h);
		handler.setName(s.getName() + " (search handler)");
		log.debug("starting discovery thread");
		handler.start();

		return d;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wallabystreet.kinjo.common.transport.protocol.jxta.iPeer#setupConnection
	 * (java.net.URLConnection)
	 */
	@Override
	public URLConnection setupConnection(URLConnection c)
			throws UnsupportedTransportException {
		if (c instanceof URLConnection) {
			URLConnection j = c;
			j.setConnectionGroup(this.group);
			return j;
		}
		else {
			String msg = "accepting JXTA only";
			throw new UnsupportedTransportException(msg);
		}
	}
}
