/*
 *  . _ . _
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


import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * A typable container object for pairs of arbitrary objects.
 * 
 * @author Alexander Papaspyrou
 * @version $Revision$, $Date$
 */
public class Pair<A, B> {

	/**
	 * Contains the left element of this pair.
	 */
	private A left;

	/**
	 * Contains the right element of this pair.
	 */
	private B right;

	/**
	 * Creates a new, empty pair.
	 */
	public Pair() {
		super();
		; // do nothing.
	}

	/**
	 * Creates a new pair, using the given parameters.
	 * 
	 * @param left
	 *            The left element of the new pair.
	 * @param right
	 *            The right element of the new pair.
	 */
	public Pair(A left, B right) {
		this.left = left;
		this.right = right;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other) {
		if ( this == other ) return true;
		if ( !( other instanceof Pair ) ) return false;
		Pair me = this;
		Pair you = (Pair)other;
		return new EqualsBuilder().append( me.left, you.left ).append(
				me.right, you.right ).isEquals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder( Pair.class.hashCode(), super.hashCode() )
				.append( this.left ).append( this.right ).toHashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder( this ).append( "left", this.left ).append(
				"right", this.right ).toString();
	}

	/**
	 * Accessor method for the <code>left</code> field.
	 * 
	 * @return The <code>left</code> field of this type.
	 */
	public A getLeft() {
		return this.left;
	}

	/**
	 * Mutator method for the <code>left</code> field.
	 * 
	 * @param left
	 *            The new content of the <code>left</code> field.
	 */
	public void setLeft(A left) {
		this.left = left;
	}

	/**
	 * Accessor method for the <code>right</code> field.
	 * 
	 * @return The <code>right</code> field of this type.
	 */
	public B getRight() {
		return this.right;
	}

	/**
	 * Mutator method for the <code>right</code> field.
	 * 
	 * @param right
	 *            The new content of the <code>right</code> field.
	 */
	public void setRight(B right) {
		this.right = right;
	}
}
