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


import java.util.Date;


/**
 * Represents objects with an expiration date after which they are no longer
 * valid.
 * 
 * @author Alexander Papaspyrou
 * @version $Revision$, $Date$
 */
public interface ExpiringData {

	/**
	 * Returns a timestamp denoting the expiration of this object. Note that,
	 * until refreshed, it is possible that the result lies in the past. A
	 * <code>null</code> value as a result denotes a never-expiring object.
	 * 
	 * @return The expiration date of this object.
	 */
	public Date getExpirationDate();

	/**
	 * Tells whether this objects validity has expired.
	 * 
	 * @return <code>true</code>, if the expiration date of this object lies
	 *         in the future; <code>false</code>, otherwise.
	 */
	public boolean hasExpired();

	/**
	 * Notifies this object to update itself, IIF its validity has expired.
	 * Otherwise, nothing happens. May include potential children.
	 * 
	 * @param recursive
	 *            Denotes whether to include potential children in the
	 *            operation.
	 */
	public void update(boolean recursive);

	/**
	 * Notifies this object to update itself, <b>regardless</b> of its
	 * validity. May include potential children.
	 * <p />
	 * Please remember that, depending on the efforts necessary to perform such
	 * an update, this operation might be expensive.
	 * 
	 * @param recursive
	 *            Denotes whether to include potential children in the
	 *            operation.
	 */
	public void flush(boolean recursive);
}
