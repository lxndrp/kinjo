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

import java.beans.PropertyChangeListener;

/**
 * Provides a unified interface to classes which can notify interested parties
 * about recent changes of their state. These so-called "listeners" must
 * implement the standard property change listener interface defined within the
 * Java Beans packages.
 * <p />
 * Implementors of this class correspond to the "subject" role of the
 * GoF <i>Observer</i> pattern.
 * 
 * @see java.beans.PropertyChangeListener
 * 
 * @author Alexander Papaspyrou
 * @version $Revision$, $Date$
 *
 */
public interface ObservableData {

	/**
	 * Adds the given parameter to this object's list of interested observers.
	 * 
	 * @param l The observer to be added.
	 */
	public void addObserver(PropertyChangeListener l);

	/**
	 * Removes the given parameter from this object's list of interested
	 * observers. If the given parameter is unknown to the object, then nothing
	 * happens.
	 * 
	 * @param l The observer to be removed.
	 */
	public void removeObserver(PropertyChangeListener l);

}