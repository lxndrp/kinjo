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
import java.net.URL;

import net.jxta.id.ID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * This class extends the abstract <code>URLStreamHandler</code> standard
 * library type. It accepts URLs that represent <a
 * href"http://spec.jxta.org/nonav/v1.0/docbook/JXTAProtocols.html#ids">JXTA IDs</a>
 * and creates appropriate <code>URLConnection</code> instances for them.
 * <p />
 * The expected format corresponds to the legion way of URL description, using
 * the following style:
 *
 * <quote>
 * <code>jxta://</code><i>JXTA_ID</i><code>/</code><i>endpoint_service</i>
 * </quote>
 * 
 * The given <i>JXTA_ID</i> must comply to the aforementioned syntax and has to
 * be semantically valid in terms of being accepted by <code>ID</code>
 * implementations; the <i>endpoint_service</i> part points to arbitrary
 * endpoint services. However, the parsing functionality does not impose any
 * restrictions on this.
 * 
 * Note that the class name must not be changed, because the Java standard
 * library requires <code>URLStreamHandler</code> subclasses to be named like
 * this to be found by the stream handler registry.
 * 
 * @author Alexander Papaspyrou
 * @version $Revision$, $Date$
 */
public class Handler
		extends java.net.URLStreamHandler {

	/**
	 * The default log facility for this class, using the <a
	 * href="http://jakarta.apache.org/commons/logging/">Jakarta "commons
	 * logging" API</a>.
	 * 
	 * @see org.apache.commons.logging.Log
	 * @see org.apache.commons.logging.LogFactory
	 */
	final private static Log log = LogFactory.getLog( Handler.class );

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.net.URLStreamHandler#openConnection(java.net.URL)
	 */
	@Override
	protected java.net.URLConnection openConnection( URL url )
			throws IOException {
		return new JXTAURLConnection( url );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.net.URLStreamHandler#parseURL(java.net.URL, java.lang.String,
	 *      int, int)
	 */
	@Override
	protected void parseURL( URL u, String spec, int start, int limit ) {
		if (spec.substring( 0, 3 ) != ID.URNNamespace) {
			String msg = "invalid URL format (must begin with \"" + ID.URNNamespace + "\")";
			log.error( msg );
			throw new IllegalArgumentException( msg );
		}
		super.parseURL( u, spec, start, limit );
	}
}
