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


import java.beans.PropertyVetoException;


/**
 * Describes the descendant role in a parent-child relationship and allows
 * retrieval and manipulation (including change interception) of the ancestor.
 * 
 * @see java.beans.PropertyVetoException
 * 
 * @author Alexander Papaspyrou
 * @version $Revision$, $Date$
 */
public interface Descendant<T extends Ancestor> {

	/**
	 * Returns the ancestor of this object.
	 * 
	 * @return This object's ancestor.
	 */
	public T getAncestor();

	/**
	 * Sets the ancestor of this object to the given parameter.
	 * 
	 * @param ancestor
	 *            The new ancestor of this object.
	 * @throws UnsupportedOperationException,
	 *             IIF the implementor considers his ancestor as a read-only
	 *             value.
	 * @throws PropertyVetoException,
	 *             IIF an observer of this descendant does not comply with the
	 *             change.
	 */
	public void setAncestor(T ancestor)
			throws UnsupportedOperationException, PropertyVetoException;
}
