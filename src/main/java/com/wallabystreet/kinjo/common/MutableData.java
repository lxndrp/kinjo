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


/**
 * A marker interface for objects that switch between being mutable and
 * immutable.
 * 
 * @author Alexander Papaspyrou
 * @version $Revision$, $Date$
 */
public interface MutableData {

	/**
	 * Denotes whether this type instance is mutable.
	 * 
	 * @return <code>true</code>, IIF this object is mutable;
	 *         <code>false</code>, else.
	 */
	public boolean isMutable();
}
