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
import java.beans.PropertyChangeSupport;
import java.net.URL;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wallabystreet.kinjo.common.ObservableData;
import com.wallabystreet.kinjo.common.Sink;


/**
 * @author Alexander Papaspyrou
 * @version $Revision$, $Date$
 * 
 */
public class Discovery
		implements ObservableData, Sink<URL> {

	/**
	 * The default log facility for this class, using the <a
	 * href="http://jakarta.apache.org/commons/logging/">Jakarta "commons
	 * logging" API</a>.
	 * 
	 * @see org.apache.commons.logging.Log
	 * @see org.apache.commons.logging.LogFactory
	 */
	final private static Log log = LogFactory.getLog( Discovery.class );

	/**
	 * 
	 */
	private Set<URL> results;

	/**
	 * 
	 */
	private DiscoveryHandler handler;

	/**
	 * 
	 */
	private PropertyChangeSupport observers;

	/**
	 * @param h
	 */
	public Discovery(DiscoveryHandler h) {
		super();
		this.handler = h;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wallabystreet.kinjo.common.ObservableData#addObserver(java.beans.PropertyChangeListener)
	 */
	public void addObserver( PropertyChangeListener l ) {
		this.observers.addPropertyChangeListener( l );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wallabystreet.kinjo.common.ObservableData#removeObserver(java.beans.PropertyChangeListener)
	 */
	public void removeObserver( PropertyChangeListener l ) {
		this.observers.removePropertyChangeListener( l );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wallabystreet.kinjo.common.Sink#put(T)
	 */
	public void put( URL payload ) {
		this.results.add( payload );
		this.observers.firePropertyChange( "results", null, payload );
	}

	/**
	 * Starts the service discovery process. This method is asynchronous; it
	 * forks a discovery thread and returns immediately.
	 * <p />
	 * The results can either be obtained by calling {@link #getResults()} or by
	 * registering as a listener to the <code>ObservableData</code> interface.
	 * 
	 * @see com.wallabystreet.kinjo.common.ObservableData
	 */
	public void start() {
		this.handler.run();
		log.debug( "discovery started" );
	}

	/**
	 * Stops the service discovery process. Note that the underlying discovery
	 * thread might not stop immediately.
	 * 
	 * @see com.wallabystreet.kinjo.common.Stoppable
	 */
	public void stop() {
		this.handler.stop();
		log.debug( "discovery stopped" );
	}

	
	/**
	 * Accessor method for the <code>results</code> field.
	 * 
	 * @return The <code>results</code> field of this type.
	 */
	public Set<URL> getResults() {
		return results;
	}
}
