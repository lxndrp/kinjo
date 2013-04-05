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
 * TODO Please comment.
 * 
 * @author Alexander Papaspyrou
 * @version $Revision$, $Date$
 */
public class SimpleResource
		implements Resource {

	/**
	 * Denotes the unique identifier of this resource.
	 */
	final private UUID resourceID;

	/**
	 * Denotes the unique identifier of this resource's type.
	 */
	final private UUID typeID;

	/**
	 * Contains the target locator of this resource.
	 */
	private URI target;

	/**
	 * Saves the name of this resource.
	 */
	private String name;

	private String description;

	/**
	 * Holds the root attribute of this resource.
	 * 
	 * @see Attribute
	 */
	private Attribute root;

	/**
	 * The default constructor of this type, which should not be used at all.
	 */
	protected SimpleResource() {
		this.resourceID = null;
		this.typeID = null;
	}

	/**
	 * Creates a new resource, using the given argument as its unique
	 * identifier.
	 * 
	 * @param resourceID
	 *            The unique identifier of the new resource.
	 * @param typeID
	 *            The type of the new resource.
	 */
	public SimpleResource(UUID resourceID, UUID typeID) {
		this.resourceID = resourceID;
		this.typeID = typeID;
	}

	/**
	 * Creates a new resource, using the given arguments.
	 * 
	 * @param resourceID
	 *            The unique identifier of the new resource.
	 * @param typeID
	 *            The type of the new resource.
	 * @param target
	 *            The target locator of the new resource.
	 * @param root
	 *            This resource's attribute tree root.
	 */
	public SimpleResource(UUID resourceID, UUID typeID, URI target, Attribute root) {
		this.resourceID = resourceID;
		this.typeID = typeID;
		this.target = target;
		this.root = root;
	}

	/**
	 * Creates a new resource, using the given arguments.
	 * 
	 * @param resourceID
	 *            The unique identifier of the new resource.
	 * @param name
	 *            The human-readable name of the new resource.
	 * @param typeID
	 *            The type of the new resource.
	 * @param target
	 *            The target locator of the new resource.
	 * @param name
	 *            A human-readable name for the new resource.
	 * @param description
	 *            A concise description of the new resource.
	 * @param root
	 *            This resource's attribute tree root.
	 */
	public SimpleResource(UUID resourceID, UUID typeID, URI target, String name, String description, Attribute root) {
		this.resourceID = resourceID;
		this.typeID = typeID;
		this.target = target;
		this.name = name;
		this.description = description;
		this.root = root;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.udo.kinjo.common.resource.Resource#getResourceID()
	 */
	public UUID getResourceID() {
		return this.resourceID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.udo.kinjo.common.resource.Resource#getTypeID()
	 */
	public UUID getTypeID() {
		return this.typeID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.udo.kinjo.common.resource.Resource#getName()
	 */
	public String getName() {
		return this.name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.udo.kinjo.common.resource.Resource#setName(java.lang.String)
	 */
	public void setName(String name) {
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.udo.kinjo.common.resource.Resource#getDescription()
	 */
	public String getDescription() {
		return this.description;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.udo.kinjo.common.resource.Resource#setDescription(java.lang.String)
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.udo.kinjo.common.resource.Resource#getRoot()
	 */
	public Attribute getRoot() {
		return this.root;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.udo.kinjo.common.resource.Resource#setRoot(edu.udo.kinjo.common.resource.Attribute)
	 */
	public void setRoot(Attribute root) {
		// TODO: Not implemented yet.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.udo.kinjo.common.resource.Resource#getTarget()
	 */
	public URI getTarget() {
		return this.target;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.udo.kinjo.common.resource.Resource#setTarget(java.net.URI)
	 */
	public void setTarget(URI target) {
		this.target = target;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.udo.kinjo.common.resource.Resource#getAttribute(edu.udo.kinjo.common.resource.Key)
	 */
	public Iterator<Attribute> getAttribute(Key key) {
		// TODO Not implemented yet.
		return null;
	}
}
