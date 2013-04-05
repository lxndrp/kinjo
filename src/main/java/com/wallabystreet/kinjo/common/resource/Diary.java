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


import java.util.Date;

import com.wallabystreet.kinjo.common.Descendant;



/**
 * Provides a common, read-only interface to an {@link Attribute}'s value
 * history and future.
 * 
 * @author Alexander Papaspyrou
 * @version $Revision$, $Date$
 */
public interface Diary
		extends Descendant<Attribute> {

	/**
	 * Returns the specified part of an attribute's diary as a {@link Timeframe}
	 * object.
	 * 
	 * @param from
	 *            as the timestamp at which the timeframe should start.
	 * @param until
	 *            as the timestamp at which the timeframe should end.
	 * @param step
	 *            as the stepping of the timeframe relative to the original data
	 *            source. A value of zero (0) indicates that the result may have
	 *            an arbitrary (possibly variable) stepping.
	 * @param count
	 *            as the maximum number of records in the result.
	 * @return A <code>Timeframe</code> with the indicated parameters.
	 */
	public Timeframe getTimeframe(Date from, Date until, int step, int count);
}
