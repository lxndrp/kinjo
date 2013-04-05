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


import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.SortedMap;
import java.util.TreeMap;


/**
 * A simple, no-frills implementation of the {@link Timeframe} interface, which
 * uses a {@link TreeMap} as the internal data structure.
 * 
 * @author Alexander Papaspyrou
 * @version $Revision$, $Date$
 */
public class SimpleTimeframe
		implements Timeframe {

	/**
	 * Contains the real <{@link Date},{@link Value}> entries of the
	 * timeframe.
	 */
	private TreeMap<Date, Value> entries;

	/**
	 * Contains the stepping interval of this timeframe.
	 */
	private int stepping;

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.udo.kinjo.common.resource.Timeframe#contains(java.util.Date)
	 */
	public boolean contains(Date timestamp) {
		return this.entries.containsKey( timestamp );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.udo.kinjo.common.resource.MutableTimeframe#mutableEntries()
	 */
	public SortedMap<Date, Value> entries() {
		return this.entries;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.udo.kinjo.common.resource.MutableTimeframe#mutableEntries(java.util.Date,
	 *      java.util.Date, byte)
	 */
	public SortedMap<Date, Value> entries(Date from, Date until, Interval interval)
			throws IllegalArgumentException {
		switch ( interval ) {
			case CLOSED:
				return this.entries.subMap( from, this
						.increaseTimestampByOne( until ) );
			case OPEN:
				return this.entries.subMap(
						this.increaseTimestampByOne( from ), until );
			case LEFT:
				return this.entries.subMap(
						this.increaseTimestampByOne( from ), this
								.increaseTimestampByOne( until ) );
			case RIGHT:
				return this.entries.subMap( from, until );
			default:
				throw new IllegalArgumentException( "bad interval: "
						+ interval.toString() );
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.udo.kinjo.common.resource.Timeframe#entries(java.util.Date,
	 *      java.util.Date, edu.udo.kinjo.common.resource.Timeframe.Interval,
	 *      int)
	 */
	public SortedMap<Date, Value> entries(Date from, Date until, Interval interval, int count)
			throws IllegalArgumentException {
		/* create an iterator over the requested interval */
		SortedMap<Date, Value> m;
		m = this.entries( from, until, interval );
		Iterator<Map.Entry<Date, Value>> i = m.entrySet().iterator();
		/* find the last entry before "count" is reached */
		Date d = null;
		int c = 0;
		while ( i.hasNext() && c < count ) {
			Map.Entry<Date, Value> e = i.next();
			d = e.getKey();
			c++;
		}
		/*
		 * check whether the queried interval's number of entries exceeds the
		 * maximum number of records requested
		 */
		if ( c + 1 < count ) {
			/*
			 * no: the whole interval is returned
			 */
			return m;
		}
		else {
			/*
			 * yes: the first "count" entries are returned
			 */
			return m.headMap( d );
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.udo.kinjo.common.resource.Timeframe#addEntry(java.util.Date,
	 *      edu.udo.kinjo.common.resource.Value)
	 */
	public void addEntry(Date timestamp, Value value) {
		this.entries.put( timestamp, value );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.udo.kinjo.common.resource.Timeframe#removeEntry(java.util.Date)
	 */
	public void removeEntry(Date timestamp)
			throws NoSuchElementException {
		Value removed = null;
		removed = (Value)this.entries.remove( timestamp );
		if ( removed == null ) { throw new NoSuchElementException(
				"key doesn't exist: " + timestamp ); }
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.udo.kinjo.common.resource.Timeframe#getStepping()
	 */
	public int getStepping() {
		return this.stepping;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.udo.kinjo.common.resource.Timeframe#setStepping(int)
	 */
	public void setStepping(int value) {
		this.stepping = value;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.udo.kinjo.common.resource.Timeframe#from()
	 */
	public Date from() {
		return (Date)this.entries.firstKey();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.udo.kinjo.common.resource.Timeframe#until()
	 */
	public Date until() {
		return (Date)this.entries.lastKey();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.udo.kinjo.common.resource.Timeframe#nextAfter(java.util.Date)
	 */
	public Date nextAfter(Date timestamp)
			throws IllegalArgumentException {
		return (Date)this.entries.tailMap( increaseTimestampByOne( timestamp ) )
				.firstKey();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.udo.kinjo.common.resource.Timeframe#nextBefore(java.util.Date)
	 */
	public Date nextBefore(Date timestamp)
			throws IllegalArgumentException {
		return (Date)this.entries.headMap( timestamp ).lastKey();
	}

	/**
	 * Increases a given timestamp by the smallest possible unit; for the
	 * {@link Date} type this is one millisecond.
	 * 
	 * @param date
	 *            as the timestamp to be increased.
	 * @return The increased timestamp as a <code>Date</code>.
	 */
	private Date increaseTimestampByOne(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime( date );
		c.setTimeInMillis( c.getTimeInMillis() + 1 );
		return c.getTime();
	}
	
	/**
	 * Decreases a given timestamp by the smallest possible unit; for the
	 * {@link Date} type this is one millisecond.
	 * 
	 * @param date
	 *            as the timestamp to be decreased.
	 * @return The decreased timestamp as a <code>Date</code>.
	 */
	@SuppressWarnings("unused") // provided for future needs
	private Date decreaseTimestampByOne(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime( date );
		c.setTimeInMillis( c.getTimeInMillis() - 1 );
		return c.getTime();
	}
}
