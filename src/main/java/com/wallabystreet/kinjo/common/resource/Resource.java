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


import java.net.URI;
import java.util.Iterator;
import java.util.UUID;


/**
 * TODO: Please comment.
 * 
 * @author Alexander Papaspyrou
 * @version $Revision$, $Date$
 */
public interface Resource {

	/**
	 * Returns the unique identifier of this resource.
	 * 
	 * @return The unique identifier of this resource.
	 */
	public UUID getResourceID();

	/**
	 * Returns the unique identifier of this resource's type.
	 * 
	 * @return The unique identifier of this resource's type.
	 */
	public UUID getTypeID();

	/**
	 * Returns the target locator of this resource.
	 * 
	 * @return The target locator of this resource.
	 */
	public URI getTarget();

	/**
	 * Sets the target locator of this resource to the given parameter, if the
	 * resource is mutable. Otherwise, an exception is thrown.
	 * 
	 * @param target
	 *            The new target locator of the resource.
	 * @throws UnsupportedOperationException,
	 *             IIF the resource is immutable.
	 */
	public void setTarget(URI target)
			throws UnsupportedOperationException;

	/**
	 * Returns the human-readable name of this resource.
	 * 
	 * @return The human-readable name of this resource.
	 */
	public String getName();

	/**
	 * Sets the human-readable name of this resource to the given parameter if
	 * the resource is mutable. Otherwise, an exception is thrown.
	 * 
	 * @param name
	 *            The new human-readable name of the resource.
	 * @throws UnsupportedOperationException,
	 *             IIF the resource is immutable.
	 */
	public void setName(String name);

	/**
	 * Returns the concise description of this resource.
	 * 
	 * @return The concise description of this resource.
	 */
	public String getDescription();

	/**
	 * Sets the concise description of this resource to the given parameter if
	 * the resource is mutable. Otherwise, an exception is thrown.
	 * 
	 * @param name
	 *            The new concise description of the resource.
	 * @throws UnsupportedOperationException,
	 *             IIF the resource is immutable.
	 */
	public void setDescription(String description);

	/**
	 * Returns the root attribute of this resource. Note that the returned
	 * {@link Attribute} might be immutable.
	 * 
	 * @return The root attribute of this resource.
	 * @see ImmutableAttributeDecorator
	 */
	public Attribute getRoot();

	/**
	 * Sets the root attribute to the given parameter, if the resource is
	 * mutable. Otherwise, an exception is thrown.
	 * 
	 * @param root
	 *            The new root attribute of the resource.
	 * @throws UnsupportedOperationException,
	 *             IIF the resource is immutable.
	 */
	public void setRoot(Attribute root)
			throws UnsupportedOperationException;

	/**
	 * Returns the attribute(s) identified by the given key, if such an
	 * attribute exists. Otherwise, an exception is thrown.
	 * 
	 * @param key
	 *            The identifier of the attribute to be returned
	 * @return An iterator over the attributes corresponding to the requested
	 *         identifier.
	 * @throws IllegalArgumentException,
	 *             IIF this resource does not have an attribute with the given
	 *             identifier.
	 * @see Key
	 */
	public Iterator<Attribute> getAttribute(Key key)
			throws IllegalArgumentException;
}
