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

import com.wallabystreet.kinjo.common.resource.Attribute;
import com.wallabystreet.kinjo.common.resource.Diary;
import com.wallabystreet.kinjo.common.resource.Key;
import com.wallabystreet.kinjo.common.resource.Resource;
import com.wallabystreet.kinjo.common.resource.Value;



/**
 * Represents leaf attributes in the composition, which have no children.
 * 
 * @author Alexander Papaspyrou
 * @version $Revision$, $Date$
 */
public class LeafAttribute
		extends AbstractAttribute {

	/**
	 * Denotes the expiration date of this attribute.
	 */
	private Date expiration;

	/**
	 * Holds the current last known value of this attribute.
	 */
	private Value value;

	/**
	 * Contains the {@link com.wallabystreet.kinjo.common.resource.Diary} for this
	 * attribute.
	 */
	private Diary diary;

	/**
	 * Creates a new instance of this type, using the given parameters.
	 * 
	 * @param parent
	 *            The parent of this attribute.
	 * @param k
	 *            The key of the newly created attribute.
	 * @param v
	 *            The initial value of the newly created attribute.
	 */
	public LeafAttribute(Attribute parent, Key k, Value v) {
		super( null, parent, k );
		this.value = v;
	}

	/**
	 * Creates a new instance of this type, using the given parameters.
	 * 
	 * @param r
	 *            The resource this attribute belongs to.
	 * @param parent
	 *            The parent of this attribute.
	 * @param k
	 *            The key of the newly created attribute.
	 * @param v
	 *            The initial value of the newly created attribute.
	 */
	public LeafAttribute(Resource r, Attribute parent, Key k, Value v) {
		super( r, parent, k );
		this.value = v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.udo.kinjo.common.ExpiringData#getExpirationDate()
	 */
	public Date getExpirationDate() {
		return (Date)this.expiration.clone();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.udo.kinjo.common.ExpiringData#hasExpired()
	 */
	public boolean hasExpired() {
		if ( this.expiration.before( new Date() ) ) {
			return true;
		}
		else {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.udo.kinjo.common.ExpiringData#update()
	 */
	public void update() {
		if ( this.hasExpired() ) {
			this.refresh();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.udo.kinjo.common.ExpiringData#flush()
	 */
	public void flush() {
		this.refresh();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.udo.kinjo.common.resource.Attribute#getValue()
	 */
	public Value getValue() {
		return this.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.udo.kinjo.common.resource.Attribute#setValue(edu.udo.kinjo.common.resource.Value)
	 */
	public void setValue(Value v)
			throws PropertyVetoException {
		this.observers.fireVetoableChange(new PropertyChangeEvent( this, "value",
				this.value, v));
		this.value = v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.udo.kinjo.common.resource.Attribute#getDiary()
	 */
	public Diary getDiary() {
		return this.diary;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.udo.kinjo.common.Ancestor#descendants()
	 */
	public Iterator<Attribute> descendants() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.udo.kinjo.common.Ancestor#addDescendant(T)
	 */
	public void addDescendant(Attribute descendant)
			throws UnsupportedOperationException, PropertyVetoException {
		/*
		 * The default implementation is to throw an exception, as children do
		 * not support it.
		 */
		throw new UnsupportedOperationException( "child, no composite: "
				+ this.toString() );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.udo.kinjo.common.Ancestor#removeDescendant(T)
	 */
	public void removeDescendant(Attribute descendant)
			throws UnsupportedOperationException, PropertyVetoException {
		/*
		 * The default implementation is to throw an exception, as children do
		 * not support it.
		 */
		throw new UnsupportedOperationException( "child, no composite: "
				+ this.toString() );
	}

	/**
	 * Refresh the value of this attribute from its source.
	 */
	private void refresh() {
		; // TODO: Not implemented yet.
	}

}
