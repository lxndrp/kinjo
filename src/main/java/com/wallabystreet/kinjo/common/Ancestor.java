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
import java.util.Iterator;


/**
 * Describes the ancestor role in a parent-child relationship.
 * <p />
 * It allows traversal and manipulation of its descendants as well as change
 * interception via exceptions.
 * 
 * @see com.wallabystreet.common.exceptions.VetoException
 * @author Alexander Papaspyrou
 * @version $Revision$, $Date$
 */
public interface Ancestor<T extends Descendant> {

	/**
	 * Returns an iterator over the descendants of this object, if applicable.
	 * Note that a particular ordering of the descendants cannot be expected.
	 * 
	 * @return An iterator over the descendants of this object, IIF it has
	 *         children; <code>null</code>, else.
	 */
	public Iterator<T> descendants();

	/**
	 * Denotes whether this object has descendants.
	 * 
	 * @return <code>true</code>, IIF this object has descendants;
	 *         <code>false</code>, otherwise.
	 */
	public boolean hasDescendants();

	/**
	 * Adds the given descendant to this object, IIF it is a composite.
	 * Otherwise, an exception is thrown.
	 * <p />
	 * If the composite already contains the given child (the same, not an
	 * equal), then nothing happens.
	 * 
	 * @param descendant
	 *            The child to be added to the composite.
	 * @throws UnsupportedOperationException,
	 *             IIF this object is no composite.
	 * @throws PropertyVetoException,
	 *             IIF a registered observer does not comply with the desired
	 *             change.
	 */
	public void addDescendant(T descendant)
			throws UnsupportedOperationException, PropertyVetoException;

	/**
	 * Removes the given descendant from this object, IIF it is a composite.
	 * Otherwise, an exception is thrown.
	 * <p />
	 * If the composite does not contain the given child (the same, not an
	 * equal), then nothing happens.
	 * 
	 * @param descendant
	 *            The child to be removed from the composite.
	 * @throws UnsupportedOperationException,
	 *             IIF this object is no composite.
	 * @throws PropertyVetoException,
	 *             IIF a registered observer does not comply with the desired
	 *             change.
	 */
	public void removeDescendant(T descendant)
			throws UnsupportedOperationException, PropertyVetoException;
}
