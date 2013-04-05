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


import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wallabystreet.kinjo.common.transport.protocol.UnsupportedTransportException;
import com.wallabystreet.kinjo.common.transport.ws.ServiceDescriptor;


/**
 * @author Alexander Papaspyrou
 * @version $Revision$, $Date$
 */
abstract public class AbstractPeer {

	/**
	 * The default log facility for this class, using the <a
	 * href="http://jakarta.apache.org/commons/logging/">Jakarta "commons
	 * logging" API</a>.
	 * 
	 * @see org.apache.commons.logging.Log
	 * @see org.apache.commons.logging.LogFactory
	 */
	private static final Log log = LogFactory.getLog(AbstractPeer.class);

	/**
	 * 
	 */
	private Properties properties;

	/**
	 * 
	 */
	protected Map<ServiceDescriptor, ServiceHandler> serviceHandlers;

	/**
	 * 
	 */
	protected abstract void initialize();

	/**
	 * @param s
	 * @throws RegistrationFailureException
	 */
	abstract public void registerService(ServiceDescriptor s)
			throws RegistrationFailureException;

	/**
	 * @param s
	 */
	abstract public void deregisterService(ServiceDescriptor s);

	/**
	 * @param s
	 * @param l
	 * @return
	 */
	abstract public Discovery discoverServiceProviders(ServiceDescriptor s, PropertyChangeListener l);

	/**
	 * @param c
	 * @return
	 * @throws UnsupportedTransportException
	 */
	abstract public java.net.URLConnection setupConnection(java.net.URLConnection c)
			throws UnsupportedTransportException;

	/**
	 * 
	 * 
	 * @param key
	 * @return
	 */
	public Object getProperty(String key) {
		return this.properties.getProperty(key);
	}

	/**
	 * Accessor method for the <code>properties</code> field.
	 * 
	 * @return The <code>properties</code> field of this type.
	 */
	final protected Properties getProperties() {
		return this.properties;
	}

	/**
	 * Mutator method for the <code>properties</code> field.
	 * 
	 * @param properties
	 *            The new value of the <code>properties</code> field.
	 */
	final protected void setProperties(Properties properties) {
		this.properties = properties;
	}

	/**
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws InvalidPropertiesFormatException
	 */
	protected Properties loadProperties(File f)
			throws InvalidPropertiesFormatException, FileNotFoundException,
			IOException {
		Properties p = null;
		p.loadFromXML(new FileInputStream(f));
		log.debug("properties successfully loaded");
		return new Properties();
	}
}
