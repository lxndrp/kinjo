/*
 *  | . _ . _ *  |(|| )|(_) - A P2P Technology Based Grid Framework
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
import java.util.NoSuchElementException;
import java.util.SortedMap;


/**
 * A <code>Timeframe</code> encapsulates the changes of an attribute's value
 * over a certain period of time by mapping timestamps to
 * {@link com.wallabystreet.kinjo.common.resource.Value}s and is supposed to be used as a
 * resultset-like view on a {@link com.wallabystreet.kinjo.common.resource.Diary} query.
 * 
 * @author Alexander Papaspyrou
 * @version $Revision$, $Date$
 */
public interface Timeframe {

	/**
	 * An enumeration of different timeframe interval types, including
	 * <ul>
	 * <li><code>CLOSED</code>, which denotes the closed interval,</li>
	 * <li><code>LEFT</code>, which denotes the left open interval,</li>
	 * <li><code>RIGHT</code>, which denotes the right open interval and</li>
	 * <li><code>OPEN</code>, which denotes the open interval.</li>
	 * </ul>
	 * 
	 * @author Alexander Papaspyrou
	 */
	public enum Interval {
		CLOSED, LEFT, RIGHT, OPEN
	};

	/**
	 * Returns a {@link java.util.SortedMap} view on the underlying key/value
	 * dataset as a whole.
	 * <p />
	 * Note that the returned collection might be immutable; this can be checked
	 * via the {@link #isMutable()} method.
	 * 
	 * @return The underlying data structure as a {@link java.util.SortedMap}
	 *         view.
	 */
	public SortedMap<Date, Value> entries();

	/**
	 * Returns a {@link java.util.SortedMap} view on the underlying key/value
	 * dataset obeying the given boundary timestamps.
	 * <p />
	 * Note that the returned collection might be immutable; this can be checked
	 * via the {@link #isMutable()} method.
	 * 
	 * @param from
	 *            The timestamp at which the view should start.
	 * @param until
	 *            The timestamp at which the view should end.
	 * @param i
	 *            The interval type to be used for the search.
	 * @return The underlying data structure as a {@link java.util.SortedMap}
	 *         view, obeying the given boundary timestamps.
	 * @throws IllegalArgumentException,
	 *             IIF the given timestamps are not within the range of the
	 *             dataset.
	 */
	public SortedMap<Date, Value> entries(Date from, Date until, Interval i)
			throws IllegalArgumentException;

	/**
	 * Returns a {@link java.util.SortedMap} view on the underlying key/value
	 * dataset obeying the given boundary timestamps and record count.
	 * <p />
	 * Note that the returned collection might be immutable; this can be checked
	 * via the {@link #isMutable()} method.
	 * 
	 * @param from
	 *            The timestamp at which the view should start.
	 * @param until
	 *            The timestamp at which the view should end.
	 * @param i
	 *            The interval type to be used for the search.
	 * @param count
	 *            The first <code>count</code> number of records to be
	 *            returned.
	 * @return The underlying data structure as a {@link java.util.SortedMap}
	 *         view, obeying the given boundary timestamps and record count.
	 * @throws IllegalArgumentException,
	 *             IIF the given timestamps are not within the range of the
	 *             dataset.
	 */
	public SortedMap<Date, Value> entries(Date from, Date until, Interval i, int count)
			throws IllegalArgumentException;

	/**
	 * Adds a new entry to the internal dataset of the timeframe. If the given
	 * timestamp is already in use, its old value is overwritten.
	 * 
	 * @param timestamp
	 *            The key for the dataset entry to save.
	 * @param value
	 *            The value of the entry at the time denoted by the timestamp.
	 * @throws UnsupportedOperationException,
	 *             IIF this timeframe is immutable.
	 */
	public void addEntry(Date timestamp, Value value)
			throws UnsupportedOperationException;

	/**
	 * Removes the entry indexed by the given timestamp.
	 * 
	 * @param timestamp
	 *            The key of the entry to be removed.
	 * @throws UnsupportedOperationException,
	 *             IIF this timeframe is immutable.
	 * @throws NoSuchElementException,
	 *             IIF the given timestamp has no corresponding entry in the
	 *             internal dataset.
	 */
	public void removeEntry(Date timestamp)
			throws UnsupportedOperationException, NoSuchElementException;

	/**
	 * Checks whether an entry with the given timestamp exists in the underlying
	 * dataset.
	 * 
	 * @param timestamp
	 *            The timestamp to check.
	 * @return <code>true</code>, IIF an entry with this timestamp exists;
	 *         <code>false</code>, otherwise.
	 */
	public boolean contains(Date timestamp);

	/**
	 * Returns the earliest timestamp in the underlying dataset. The content and
	 * validity of this field must be handled internally by the implementors of
	 * this interface.
	 * 
	 * @return The earliest timestamp of this <code>Timeframe</code>.
	 */
	public Date from();

	/**
	 * Returns the latest timestamp in the underlying dataset. The content and
	 * validity of this field must be handled internally by the implementors of
	 * this interface.
	 * 
	 * @return The latest timestamp of this timeframe.
	 */
	public Date until();

	/**
	 * Returns the nearest available later timestamp relative to the given one.
	 * For example, if the underlying dataset contains the timestamps
	 * "02-07-2003 15:13:04" and "04-07-2003 17:02:18", then asking for the
	 * timestamp "03-07-2003 06:22:57" would return the second entry.
	 * 
	 * @param timestamp
	 *            The reference timestamp to to check.
	 * @return The next available later timestamp of this timeframe.
	 * @throws IllegalArgumentException,
	 *             IIF the given timestamp is later than the latest within the
	 *             dataset.
	 */
	public Date nextAfter(Date timestamp)
			throws IllegalArgumentException;

	/**
	 * Returns the nearest available earlier timestamp relative to the given
	 * one. For example, if the underlying dataset contains the timestamps
	 * "02-07-2003 15:13:04" and "04-07-2003 17:02:18", then asking for the
	 * timestamp "03-07-2003 06:22:57" would return the first entry.
	 * 
	 * @param timestamp
	 *            The reference timestamp to to check.
	 * @return The next available earlier timestamp of this
	 *         <code>Timeframe</code>.
	 * @throws IllegalArgumentException,
	 *             IIF the given timestamp is earlier than the earliest within
	 *             the dataset.
	 */
	public Date nextBefore(Date timestamp)
			throws IllegalArgumentException;

	/**
	 * Returns the stepping of this timeframe relative to its data source.
	 * 
	 * @return The stepping of this timeframe. A value of <code>0</code>
	 *         indicates a timeframe without a fixed stepping.
	 */
	public int getStepping();

	/**
	 * Sets the stepping interval of this timeframe, if it is mutable. Note that
	 * this method should be used <b>with extreme care</b>, as the interface
	 * contract does not require any validity checks whatsoever; wrong values
	 * might have great impact on timeframe searches and can lead to wrong
	 * results.
	 * <p />
	 * However, interface users (such as
	 * {@link com.wallabystreet.kinjo.common.resource.Diary} implementors) hopefully know
	 * their timeframe reconstruction algorithms well enough to keep the
	 * stepping value in a sane state.
	 * 
	 * @param value
	 *            as the new stepping value for this timeframe.
	 * @throws UnsupportedOperationException,
	 *             IIF this timeframe is immutable.
	 */
	public void setStepping(int value)
			throws UnsupportedOperationException;
}
