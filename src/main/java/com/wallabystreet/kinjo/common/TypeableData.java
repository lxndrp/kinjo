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

package com.wallabystreet.kinjo.common;


import java.io.Serializable;


/**
 * This is a simple wrapper class for arbitrary types. It's purpose is to ensure
 * that the encapsulated object (the "payload") meets a set of requirements,
 * namely
 * <ul>
 * <li>to be serializable and</li>
 * <li>that it can be compared to payloads of the same type.</li>
 * </ul>
 * <p />
 * In order to make the usage of this class completely transparent, the
 * implementation of <code>TypeableData<T></code> overwrites the common
 * {@link java.lang.Object} by passing them to the payload. The
 * {@link java.lang.Comparable#compareTo(T)} must be implemented in the
 * subclasses.
 * 
 * @author Alexander Papaspyrou
 * @version $Revision$, $Date$
 */
abstract public class TypeableData<T extends Comparable<? super T> & Serializable>
		implements Serializable {

	/**
	 * Contains the encapsulated object (the "payload").
	 */
	final private T payload;

	/**
	 * The default constructor of this type, which shouldn't be used at all.
	 * (Except for overwriting classes, that is.)
	 */
	protected TypeableData() {
		super();
		this.payload = null;
	}

	/**
	 * Creates a new instance of this type, using the passed argument as its
	 * payload.
	 * 
	 * @param payload
	 *            The object to be encapsulated by this class.
	 */
	public TypeableData(T payload) {
		this.payload = payload;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other) {
		if(this == other) {
			return true;
		}
		if ( other instanceof TypeableData ) {
			TypeableData me, you;
			me = this;
			you = (TypeableData)other;
			return me.payload.equals( you.payload );
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
		return this.payload.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.payload.toString();
	}

	/**
	 * Accessor method for the <code>payload</code> field.
	 * 
	 * @return The <code>payload</code> field of this type.
	 */
	public T getPayload() {
		return this.payload;
	}
	
	/**
	 * Returns the type of the <code>payload</code> object.
	 * 
	 * @return The type of the <code>payload</code> object.
	 */
	public Class getType() {
		return this.payload.getClass();
	}
}
