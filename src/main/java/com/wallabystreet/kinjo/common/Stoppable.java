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
 * This interface adds a means to stop running threads to the
 * <code>Runnable</code> interface provided by the Java standard library.
 * <p />
 * To achieve this, classes which are supposed to be running as threads must
 * <ol>
 * <li>implement this interface,</li>
 * <li>have an additional <code>private boolean stop = false;</code> field,
 * which is set to <code>true</code> when the {@link #stop()} method is called
 * and</li>
 * <li>provide a <code>while (!stop) {}</code> loop in their
 * <code>run()</code> method as the outermost construct.</li>
 * </ol>
 * Note that this approach implies that the running thread will not stop
 * immediately, but continue to run until the end of the <code>run()</code>
 * method is reached. As such, there is no guarantee on how long the stopping
 * will take or what (potentially costly) actions will still be performed.
 * 
 * @author Alexander Papaspyrou
 * @version $Revision$, $Date$
 */
public interface Stoppable
		extends Runnable {

	/**
	 * Ask a running thread to stop. Note that there is no guarantee how long
	 * the thread continues to run; even if this method returns immediately, the
	 * thread may continue to perform arbitrary actions before coming to a stop.
	 */
	public void stop();
}
