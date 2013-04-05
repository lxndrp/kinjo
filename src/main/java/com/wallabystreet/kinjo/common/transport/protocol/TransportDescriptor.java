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

package com.wallabystreet.kinjo.common.transport.protocol;


import java.util.Properties;


/**
 * Provides a unified view on the configuration-related parts of transports.
 * 
 * @author Alexander Papaspyrou
 * @version $Revision$, $Date$
 */
public interface TransportDescriptor {

	/**
	 * Returns a list of
	 * {@link com.wallabystreet.kinjo.common.transport.ws.ServiceDescriptor}-related,
	 * transport-specific <code>Properties</code>.
	 * <p />
	 * To avoid key collisions, it is <b>mandatory</b> that the stored
	 * properties use a <blockquote><code>transport.</code><i>&lt;name&gt;</i><code>.*</code></blockquote>
	 * namespace for their keys, where <i>&lt;name&gt;</i> is the last element
	 * of the transport package name.
	 * <p />
	 * The elements within the <code>Properties</code> list, however, can be
	 * of any type.
	 * 
	 * @pram serviceName The name of the services, which should be the result of
	 * 
	 * @param servicePackage
	 *            The full package name of the service to be configured.
	 * @return A <code>Properties</code> list containing the
	 *         transport-specific configuration elements.
	 * @see java.util.Properties
	 */
	public Properties getServiceRelatedProperties(String serviceName, String servicePackage);

}
