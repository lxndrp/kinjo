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

package com.wallabystreet.kinjo.common.transport.ws;


/**
 * This exception is thrown to indicate that the (un)deployment of a web service 
 * failed.
 * 
 * @author Alexander Papaspyrou
 * @version $Revision$, $Date$
 */
final public class DeploymentException
		extends Exception {

	/**
	 * The serialVersionUID of this type.
	 * 
	 * @see java.io.Serializable
	 */
	private static final long serialVersionUID = -4587954788326728774L;

	/**
	 * Creates a new instance of this exception with the specified cause.
	 * 
	 * @param cause
	 *            The cause, which is saved for later retrieval by the
	 *            {@link Throwable.getCause()} method. (A null value is
	 *            permitted, and indicates that the cause is nonexistent or
	 *            unknown.)
	 */
	public DeploymentException(Throwable cause) {
		super( "failed to (un)deploy web service", cause );
	}

}
