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


import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



/**
 * @author Alexander Papaspyrou
 * @version $Revision$, $Date$
 */
public class ServiceDescriptorFactory {

	/**
	 * The default log facility for this class, using the <a
	 * href="http://jakarta.apache.org/commons/logging/">Jakarta "commons
	 * logging" API</a>.
	 * 
	 * @see org.apache.commons.logging.Log
	 * @see org.apache.commons.logging.LogFactory
	 */
	final private static Log log = LogFactory
			.getLog( ServiceDescriptorFactory.class );

	/**
	 * 
	 */
	private static ServiceDescriptorFactory instance = null;

	/**
	 * 
	 */
	private Map<String, ServiceDescriptor> serviceDescriptors;

	/**
	 * 
	 */
	protected ServiceDescriptorFactory() {
		this.serviceDescriptors = new HashMap<String, ServiceDescriptor>();
	}

	/**
	 * Returns the only instance of this class. See the <i>Singleton</i> design
	 * pattern by the GoF.
	 * 
	 * @return The only instance of this class. If there is no instance, a new
	 *         one is created internally.
	 */
	final public static ServiceDescriptorFactory getInstance() {
		if ( instance == null ) {
			log.debug("creating new instance");
			instance = new ServiceDescriptorFactory();
		}
		return instance;
	}

	/**
	 * @param servicePackage
	 * @return
	 * @throws MalformedServiceException
	 */
	public ServiceDescriptor getServiceDescriptor(String servicePackage)
			throws MalformedServiceException {
		ServiceDescriptor d = this.serviceDescriptors.get( servicePackage );
		if ( d == null ) {
			log.debug("descriptor does not exist, create a new one");
			d = new ServiceDescriptor( servicePackage );
		}
		return d;
	}
	
	/**
	 * @param serviceName
	 * @param servicePackage
	 * @return
	 * @throws MalformedServiceException
	 * 
	 * @deprecated Setting the service name by hand is not portable and
	 *             dangerous.
	 */
	@Deprecated
	public ServiceDescriptor getServiceDescriptor(String serviceName, String servicePackage)
			throws MalformedServiceException {
		ServiceDescriptor d = this.serviceDescriptors.get( servicePackage );
		if ( d == null ) {
			log.debug("descriptor does not exist, create a new one");
			d = new ServiceDescriptor( serviceName, servicePackage );
		}
		return d;
	}

}
