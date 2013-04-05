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

package com.wallabystreet.kinjo.common.resource;


import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;

import com.wallabystreet.kinjo.common.Ancestor;
import com.wallabystreet.kinjo.common.Descendant;
import com.wallabystreet.kinjo.common.ExpiringData;



/**
 * Represents an attribute of a resource. <code>Attribute</code>s are kept in
 * a hierarchical, tree-like structure as composite or leaf elements.
 * <p />
 * A single attribute encapsulates a {@link com.wallabystreet.kinjo.common.resource.Key}
 * which identifies the attribute, the current last known
 * {@link com.wallabystreet.kinjo.common.resource.Value} of the attribute and a
 * {@link com.wallabystreet.kinjo.common.resource.Diary}, which is optional; besides
 * that, it can be mutable or not.
 * 
 * @author Alexander Papaspyrou
 * @version $Revision$, $Date$
 */
public interface Attribute
		extends ExpiringData, Ancestor<Attribute>, Descendant<Attribute> {

	/**
	 * Denotes whether this attribute is mutable or not.
	 * 
	 * @return <code>true</code>, IIF this attribute is mutable;
	 *         <code>false</code>, else.
	 */
	public boolean isMutable();

	/**
	 * Returns the resource this attribute belongs to.
	 * 
	 * @return The resource this attribute belongs to.
	 */
	public Resource getResource();

	/**
	 * Returns the key of this attribute.
	 * 
	 * @return The key of this attribute.
	 */
	public Key getKey();

	/**
	 * Returns the currently known last value of this attribute, IIF the
	 * attribute is a leaf. Otherwise, the result is <code>null</code>.
	 * 
	 * @return The value of this attribute, or <code>null</code>.
	 */
	public Value getValue();

	/**
	 * Sets the attribute's value to the given parameter, IIF the attribute is a
	 * leaf. Otherwise, an exception is thrown.
	 * 
	 * @param v
	 *            The new value to be set.
	 * @throws UnsupportedOperationException,
	 *             IIF this attribute is a composite.
	 * @throws PropertyVetoException,
	 *             IIF a registered observer does not comply with the desired
	 *             change.
	 */
	public void setValue(Value v)
			throws UnsupportedOperationException, PropertyVetoException;

	/**
	 * Returns the value diary of this attribute, which can be queried further.
	 * 
	 * @return The diary of this attribute.
	 */
	public Diary getDiary();

	/**
	 * Registers an observer with this attribute. <code>null</code> parameters
	 * are ignored.
	 * 
	 * @param l
	 *            The observer to be registered.
	 */
	public void addChangeListener(VetoableChangeListener l);

	/**
	 * Deregisters an observer with this attribute.
	 * 
	 * @param l
	 *            The observer to be deregistered.
	 */
	public void removeChangeListener(VetoableChangeListener l);
}
