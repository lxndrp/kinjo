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

package com.wallabystreet.kinjo.common.resource.attribute;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wallabystreet.kinjo.common.ExpiringData;
import com.wallabystreet.kinjo.common.resource.Attribute;
import com.wallabystreet.kinjo.common.resource.Diary;
import com.wallabystreet.kinjo.common.resource.Key;
import com.wallabystreet.kinjo.common.resource.Resource;
import com.wallabystreet.kinjo.common.resource.Value;


/**
 * This class defines the behaviour of attributes with children. It stores the
 * child attributes and overwrites the child-related methods of the abstract
 * superclass.
 * 
 * @author Alexander Papaspyrou
 * @version $Revision$, $Date$
 */
public class CompositeAttribute
		extends AbstractAttribute {

	/**
	 * The default log facility for this class, using the <a
	 * href="http://jakarta.apache.org/commons/logging/">Jakarta "commons
	 * logging" API</a>.
	 * 
	 * @see org.apache.commons.logging.Log
	 * @see org.apache.commons.logging.LogFactory
	 */
	final private static Log log = LogFactory.getLog( CompositeAttribute.class );

	/**
	 * Stores the components of this composite.
	 */
	private List<Attribute> descendants;

	/**
	 * Creates a new instance of this type, using the given parameters.
	 * 
	 * @param parent
	 *            The parent of this attribute.
	 * @param k
	 *            The identifier of this attribute.
	 */
	public CompositeAttribute(Attribute parent, Key k) {
		this( null, parent, k );
		this.descendants = new LinkedList<Attribute>();
	}

	/**
	 * Creates a new instance of this type, using the given parameters.
	 * 
	 * @param r
	 *            The resource this attribute belongs to.
	 * @param parent
	 *            The parent of this attribute.
	 * @param k
	 *            The identifier of this attribute.
	 */
	public CompositeAttribute(Resource r, Attribute parent, Key k) {
		super( r, parent, k );
		this.descendants = new LinkedList<Attribute>();
	}

	/**
	 * Because composite attributes have no expiration date (and thus cannot
	 * expire), this method always returns <code>null</code>.
	 * 
	 * @return <code>null</code>.
	 * @see com.wallabystreet.kinjo.common.ExpiringData#getExpirationDate()
	 */
	public Date getExpirationDate() {
		log.info( "never expires" );
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.udo.kinjo.common.ExpiringData#hasExpired()
	 */
	public boolean hasExpired() {
		log.info( "never expires" );
		return false;
	}

	/**
	 * Notifies all children to update themselves.
	 * 
	 * @see com.wallabystreet.kinjo.common.ExpiringData#update()
	 */
	public void update() {
		for (ExpiringData child : this.descendants) {
			child.update();
		}
	}

	/**
	 * Notifies all children to flush themselves.
	 * 
	 * @see com.wallabystreet.kinjo.common.ExpiringData#flush()
	 */
	public void flush() {
		for (ExpiringData child : this.descendants) {
			child.flush();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.udo.kinjo.common.resource.Attribute#getValue()
	 */
	public Value getValue() {
		log.info( "no value" );
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.udo.kinjo.common.resource.Attribute#setValue(edu.udo.kinjo.common.resource.Value)
	 */
	public void setValue( Value v )
			throws UnsupportedOperationException, PropertyVetoException {
		UnsupportedOperationException e = new UnsupportedOperationException(
				"composite, no child: " + this.toString() );
		log.info( "no value", e );
		throw e;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.udo.kinjo.common.resource.Attribute#getDiary()
	 */
	public Diary getDiary() {
		// TODO Not implemented yet.
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.udo.kinjo.common.Ancestor#descendants()
	 */
	public Iterator<Attribute> descendants() {
		return descendants.isEmpty() ? null : descendants.iterator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.udo.kinjo.common.Ancestor#addDescendant(T)
	 */
	public void addDescendant( Attribute descendant )
			throws PropertyVetoException {
		this.observers.fireVetoableChange( new PropertyChangeEvent( this,
				"descendant", null, descendant ) );
		if (log.isDebugEnabled()) {
			log.debug( "adding descendant: " + descendant.toString() );
		}
		descendants.add( descendant );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.udo.kinjo.common.Ancestor#removeDescendant(T)
	 */
	public void removeDescendant( Attribute descendant )
			throws PropertyVetoException {
		this.observers.fireVetoableChange( new PropertyChangeEvent( this,
				"child", null, descendant ) );
		if (log.isDebugEnabled()) {
			log.debug( "removing descendant: " + descendant.toString() );
		}
		this.descendants.remove( descendant );
	}

}
