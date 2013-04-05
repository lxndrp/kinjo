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
import java.util.Collection;
import java.util.Date;


/**
 * A basic implementation of the {@link com.wallabystreet.kinjo.common.resource.Diary}interface.
 * 
 * @author Alexander Papaspyrou
 * @version $Revision$, $Date$
 */
public class SimpleDiary
		implements Diary {

	private Attribute ancestor;

	/**
	 * The timeframes this diary consists of.
	 */
	private Collection<Timeframe> timeframes;

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.udo.kinjo.common.Descendant#getAncestor()
	 */
	public Attribute getAncestor() {
		return this.ancestor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.udo.kinjo.common.Descendant#setAncestor(T)
	 */
	public void setAncestor(Attribute ancestor)
			throws UnsupportedOperationException, PropertyVetoException {
		throw new UnsupportedOperationException( "type does not support operation: "
				+ this.getClass().toString() );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.udo.kinjo.common.resource.Diary#getTimeframe(java.util.Date,
	 *      java.util.Date, int, int)
	 */
	public Timeframe getTimeframe(Date from, Date until, int step, int count) {
		// TODO Not implemented yet.
		return null;
	}
}