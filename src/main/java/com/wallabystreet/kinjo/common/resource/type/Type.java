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

package com.wallabystreet.kinjo.common.resource.type;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.util.Map;
import java.util.UUID;

import com.wallabystreet.kinjo.common.resource.Key;



/**
 * @author Alexander Papaspyrou
 * @version $Revision$, $Date$
 */
public class Type
		implements VetoableChangeListener {

	final private UUID typeID;

	private Map<Key, AttributeInformation> attributes;

	protected Type() {
		this.typeID = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.beans.VetoableChangeListener#vetoableChange(java.beans.PropertyChangeEvent)
	 */
	public void vetoableChange(PropertyChangeEvent evt)
			throws PropertyVetoException {
		; // not implemented yet.
	}

	public Type(UUID typeID) {
		this.typeID = typeID;
	}

	public UUID getTypeID() {
		return this.typeID;
	}

	public class AttributeInformation {

	}

	public enum Multiplicity {
		SINGLE, MULTI
	}

	public enum Occurence {
		MANDATORY, OPTIONAL
	}

}
