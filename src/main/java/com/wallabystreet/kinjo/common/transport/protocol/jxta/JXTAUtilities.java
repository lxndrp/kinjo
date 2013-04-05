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

package com.wallabystreet.kinjo.common.transport.protocol.jxta;


import net.jxta.document.AdvertisementFactory;
import net.jxta.protocol.PipeAdvertisement;

import com.wallabystreet.kinjo.common.transport.ws.ServiceDescriptor;


/**
 * @author Alexander Papaspyrou
 * @version $Revision$, $Date$
 * 
 */
public class JXTAUtilities {

	public static PipeAdvertisement createPipeAdvertisement( ServiceDescriptor s ) {
		PipeAdvertisement p = (PipeAdvertisement)AdvertisementFactory
				.newAdvertisement( PipeAdvertisement.getAdvertisementType() );
		p.setName( (String)s.getProperty( "transport.jxta.pipe.name" ) );
		p.setDescription( (String)s
				.getProperty( "transport.jxta.pipe.desc" ) );
		p.setType( (String)s.getProperty( "transport.jxta.pipe.type" ) );
		return p;
	}
}
