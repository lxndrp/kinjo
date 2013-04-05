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
 * This exception is thrown to indicate that a {@link ServiceDescriptor} could
 * not be instantiated.
 * 
 * @author Alexander Papaspyrou
 * @version $Revision$, $Date$
 */
final public class MalformedServiceException
		extends Exception {

	/**
	 * The serialVersionUID of this type.
	 * 
	 * @see java.io.Serializable
	 */
	private static final long serialVersionUID = 7636178648756062607L;

	/**
	 * Creates a new instance of this exception with the specified detail
	 * message.
	 * 
	 * @param message
	 *            The detail message.
	 */
	public MalformedServiceException(String message) {
		super( message );
	}

	/**
	 * Creates a new instance of this exception with the specified cause.
	 * 
	 * @param cause
	 *            The cause, which is saved for later retrieval by the
	 *            {@link Throwable.getCause()} method. (A null value is
	 *            permitted, and indicates that the cause is nonexistent or
	 *            unknown.)
	 */
	public MalformedServiceException(Throwable cause) {
		super( "failed to create service descriptor", cause );
	}

	/**
	 * Creates a new instance of this exception with the specified detail
	 * message and cause.
	 * 
	 * @param message
	 *            The detail message.
	 * @param cause
	 *            The cause, which is saved for later retrieval by the
	 *            {@link Throwable.getCause()} method. (A null value is
	 *            permitted, and indicates that the cause is nonexistent or
	 *            unknown.)
	 */
	public MalformedServiceException(String message, Throwable cause) {
		super( message, cause );
	}
}
