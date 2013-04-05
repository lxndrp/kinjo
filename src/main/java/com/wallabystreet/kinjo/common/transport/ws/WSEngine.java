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

package com.wallabystreet.kinjo.common.transport.ws;

import java.rmi.RemoteException;
import java.util.Vector;

import javax.xml.rpc.ServiceException;

import org.apache.axis.AxisFault;
import org.apache.axis.MessageContext;
import org.apache.axis.client.AxisClient;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.message.SOAPBodyElement;
import org.apache.axis.server.AxisServer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Element;



/**
 * @author Alexander Papaspyrou
 * @version $Revision$, $Date$
 *
 */
public final class WSEngine {
	
	/**
	 * The default log facility for this class, using the <a href="http://jakarta.apache.org/commons/logging/">Jakarta "commons logging" API</a>.
	 *
	 * @see org.apache.commons.logging.Log
	 * @see org.apache.commons.logging.LogFactory
	 */
	final private static Log log = LogFactory.getLog( WSEngine.class );
	
	private static WSEngine instance;
	
	private AxisServer server; 
	
	protected WSEngine() {
		this.server = new AxisServer();
	}
	
	public static WSEngine getInstance() {
		if (instance == null) {
			instance = new WSEngine();
		}
		return instance;
	}
	
	public AxisServer getServer() {
		return this.server;
	}
	
	public void deploy(ServiceDescriptor d) throws DeploymentException {
		this.process(d.getDeploymentWSDD());
	}
	
	public void undeploy(ServiceDescriptor d) throws DeploymentException {
		this.process(d.getUndeploymentWSDD());		
	}
	
	public MessageContext createMessageContext() {
		return new MessageContext(this.server);
	}
	
	public void invoke(MessageContext m) throws AxisFault {
		this.server.invoke(m);
	}
	
	private void process(Element wsdd) throws DeploymentException {
		Service svc = new Service(server.getConfig(), (AxisClient) server
				.getClientEngine());

		Call call = null;
		try {
			call = (org.apache.axis.client.Call) svc.createCall();
		} catch (ServiceException e) {
			log.error("", e);
			throw new DeploymentException(e);
		}

		call.setTargetEndpointAddress("local://AdminService");
		call.setUseSOAPAction(true);
		call.setSOAPActionURI("urn:AdminService");

		Vector result = null;
		Object[] params = new Object[] { new SOAPBodyElement(wsdd) };
		try {
			result = (Vector) call.invoke(params);
		} catch (RemoteException e) {
			/* since this runs locally, this exception shouldn't ever raise */
			throw new DeploymentException(e);
		}

		if (result == null || result.isEmpty()) {
			log.debug("result message of call is empty");
        }
		else {
	        SOAPBodyElement body = (SOAPBodyElement) result.elementAt(0);
	        log.debug("result message of call is: \n" + body.toString());
		}
	}
	
}
