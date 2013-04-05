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


import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wallabystreet.kinjo.common.resource.Attribute;
import com.wallabystreet.kinjo.common.resource.Key;
import com.wallabystreet.kinjo.common.resource.Resource;


/**
 * An abstract attribute implementation which provides basic funcionality and
 * holds data common to all attributes.
 * 
 * @author Alexander Papaspyrou
 * @version $Revision$, $Date$
 */
abstract public class AbstractAttribute
		implements Attribute {

	/**
	 * The default log facility for this class, using the <a
	 * href="http://jakarta.apache.org/commons/logging/">Jakarta "commons
	 * logging" API</a>.
	 * 
	 * @see org.apache.commons.logging.Log
	 * @see org.apache.commons.logging.LogFactory
	 */
	final private static Log log = LogFactory.getLog(AbstractAttribute.class);

	/**
	 * Denotes the parent resource of this attribute.
	 */
	private Resource resource;

	/**
	 * Keeps the parent attribute of this object.
	 */
	private Attribute ancestor;

	/**
	 * Contains the key which identifies this attribute.
	 */
	final private Key key;

	/**
	 * Holds the observers of this attribute.
	 */
	protected VetoableChangeSupport observers;

	/**
	 * The default constructor for this type, which shouldn't be used at all.
	 */
	private AbstractAttribute() {
		this.ancestor = null;
		this.key = null;
	}

	/**
	 * Creates a new attribute instance, using the given parameters.
	 * 
	 * @param r
	 *            The resource this attribute belongs to.
	 * @param parent
	 *            The parent of this attribute.
	 * @param k
	 *            The identifier of this attribute.
	 * @see Key
	 */
	protected AbstractAttribute(Resource r, Attribute parent, Key k) {
		this.resource = r;
		this.ancestor = parent;
		this.key = k;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (other instanceof Attribute) {
			Attribute me = this;
			Attribute you = ( Attribute )other;
			return me.getKey().equals(you.getKey());
		}
		else {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return (( Key<?> )this.getKey()).hashCode();
	}

	@Override
	public String toString() {
		// TODO Not implemented yet.
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.udo.kinjo.common.resource.Attribute#isMutable()
	 */
	public boolean isMutable() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.udo.kinjo.common.Descendant#getAncestor()
	 */
	public Attribute getAncestor() {
		return this.ancestor;
	}

	/**
	 * @param ancestor
	 */
	public void setAncestor(Attribute ancestor) {
		this.ancestor = ancestor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.udo.kinjo.common.resource.Attribute#getResource()
	 */
	public Resource getResource() {
		if (this.resource == null) {
			if (log.isDebugEnabled()) {
				log.debug("cache miss: asking ancestor");
			}
			this.resource = this.ancestor.getResource();
			if (log.isDebugEnabled()) {
				log.debug("resource found: caching result");
			}
		}
		return this.resource;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.udo.kinjo.common.resource.Attribute#getKey()
	 */
	public Key getKey() {
		return this.key;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.udo.kinjo.common.resource.Attribute#addChangeListener(java.beans.
	 * VetoableChangeListener)
	 */
	public void addChangeListener(VetoableChangeListener l) {
		this.observers.addVetoableChangeListener(l);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.udo.kinjo.common.resource.Attribute#removeChangeListener(java.beans
	 * .VetoableChangeListener)
	 */
	public void removeChangeListener(VetoableChangeListener l) {
		this.observers.removeVetoableChangeListener(l);
	}

}
