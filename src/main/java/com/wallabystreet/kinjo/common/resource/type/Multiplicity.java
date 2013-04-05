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


import com.wallabystreet.kinjo.common.Pair;


/**
 * Describes multiplicities of arbitrary entities and is typically used to
 * describe bounds for the appearance of certain elements in a collection. The
 * left bound of the multiplicity must be greater or equal to zero ({@value 0}).
 * 
 * @author Alexander Papaspyrou
 * @version $Revision$, $Date$
 */
final public class Multiplicity {

	final private static int NULL = 0;
	
	final public static int INFINITE = -1;

	/**
	 * Holds the upper and lower bound of this multiplicity
	 */
	private Pair<Integer, Integer> range;

	/**
	 * Creates a new instance of this type, using the given parameters.
	 * 
	 * @param lowerBound
	 *            The lower bound of the new multiplicity.
	 * @param upperBound
	 *            The upper bound of the new multiplicity.
	 * @throws IllegalArgumentException,
	 *             IIF
	 *             <ul>
	 *             <li>the given lower bound is greater than the given upper
	 *             bound or</li>
	 *             <li>the lower bound equals the <code>INFINITE</code>
	 *             constant.</li>
	 */
	public Multiplicity(int lowerBound, int upperBound)
			throws IllegalArgumentException {
		if ( testValidity( lowerBound, false ) > testValidity( upperBound, true ) ) {
			String msg = "illegal bounds: lower(" + lowerBound + ") > upper("
					+ upperBound + ")";
			throw new IllegalArgumentException( msg );
		}
		else {
			this.range = new Pair<Integer, Integer>( lowerBound, upperBound );
		}
	}

	/**
	 * Checks for a given integer whether it is within the range of this
	 * multiplicity.
	 * 
	 * @param multiplicity
	 *            The integer to check.
	 * @return <code>true</code>, IIF the given integer is within the range
	 *         of this multiplicity; <code>false</code>, else.
	 */
	public boolean withinRange(int multiplicity)
			throws IllegalArgumentException {
		testValidity( multiplicity, false );
		return this.range.getLeft() <= multiplicity
				&& multiplicity <= this.range.getRight();
	}

	/**
	 * Checks if the given parameter is within the general bounds of a
	 * multiplicity.
	 * 
	 * @param value
	 *            The value to check.
	 * @param allowInfinity
	 *            Denotes whether the <code>INFINITE</code> constant is within
	 *            the allowed range.
	 * @return The given parameter, left unchanged.
	 * @throws IllegalArgumentException,
	 *             IIF the given parameter is out of range.
	 */
	private static int testValidity(int value, boolean allowInfinity)
			throws IllegalArgumentException {
		if ( ( ( allowInfinity == false ) && ( value < NULL ) )
				|| ( ( allowInfinity == true ) && ( value < INFINITE ) ) ) {
			String msg = "illegal argument: value(" + value + ") must be >= " + NULL;
			throw new IllegalArgumentException( msg );
		}
		else {
			return value;
		}
	}
}
