/*  | . _ . _
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

package com.wallabystreet.kinjo.common.resource;


import java.io.Serializable;

import com.wallabystreet.kinjo.common.TypeableData;



/**
 * Represents the current state of an {@link com.wallabystreet.kinjo.common.resource.Attribute} object.
 * 
 * @see com.wallabystreet.kinjo.common.TypeableData
 * @author Alexander Papaspyrou
 * @version $Revision$, $Date$
 */
final public class Value<T extends Comparable<? super T> & Serializable>
		extends TypeableData<T>
		implements Comparable<Value<T>> {

	/**
	 * The serial version UID of this class.
	 * 
	 * @see java.io.Serializable
	 */
	final private static long serialVersionUID = -2253001664676799929L;

	/**
	 * Creates a new <code>Value</code> object with the given payload.
	 * 
	 * @param payload
	 *            The payload of the new value.
	 */
	public Value(T payload) {
		super( payload );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(T)
	 */
	public int compareTo(Value<T> other) {
		return super.getPayload().compareTo( other.getPayload() );
	}
}
