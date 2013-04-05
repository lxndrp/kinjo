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


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.Enumeration;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.wsdl.Definition;
import javax.wsdl.Port;
import javax.wsdl.Service;
import javax.wsdl.WSDLException;
import javax.wsdl.factory.WSDLFactory;
import javax.wsdl.xml.WSDLReader;
import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.axis.utils.XMLUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.wallabystreet.kinjo.common.transport.protocol.MalformedTransportException;
import com.wallabystreet.kinjo.common.transport.protocol.TransportDescriptor;



/**
 * Describes a web service used in the kinjo Grid Management Framework.
 * 
 * <p />
 * A <code>ServiceDescriptor</code> consists of
 * <ul>
 * <li>a name, which corresponds to the &lt;wsdl:service name""&gt; value,</li>
 * <li>a package, which contains the service's configuration, interface, stub
 * and locator,</li>
 * <li>WS-related data, that is
 * <ol>
 * <li>a representation of the service's WSDL. The file containing it is
 * expected to be found in the service's package folder under the name
 * <i>service.wsdl,</i></li>
 * <li>a representation of the service's deployment WSDD. The file containing
 * it is expected to be found in the service's package folder under the name
 * <i>deploy.wsdd and</i></li>
 * <li>a representation of the service's undeployment WSDD. The file containing
 * it is expected to be found in the service's package folder under the name
 * <i>undeploy.wsdd,</i></li>
 * </ol>
 * </li>
 * <li>a property map containing additional configuration. It's file
 * representation expected to be found in the service's package folder under the
 * name <i>properties.xml</i>; the format must correspond to the <a
 * href="http://java.sun.com/dtd/properties.dtd">Java Property File DTD<a/>.</li>
 * </ul>
 * 
 * <p />
 * At the moment, only a single <code>Service</code> per WSDL and a single
 * <code>Port</code> per service are supported. Furthermore, the port name
 * <b>must</b> be equal to the service name.
 * 
 * <p />
 * Each service must support at least one transport. The supported transports
 * are specified in the service's configuration file; for this purpose, the
 * following property names are reserved:
 * <ul>
 * <li><code>transport</code>, which contains a colon(:)-delimited list of
 * supported transports</li>
 * <li><code>transport.</code><i>name</i>, which &ndash; for each <i>name</i>
 * specified in <code>transport</code> &ndash; contains the package name in
 * which an implementor of the <code>TransportDescriptor</code> interface
 * is expected under the name <code>Transport</code>.
 * </ul>
 * 
 * <p />
 * The reserved names are subject to change in future versions of this type.
 * Hence, when putting custom properties into the configuration map, one should
 * consider using a unique namespace for the property key name; for example,
 * Chapter 7.7 (Unique Package Names) from the <a
 * href="http://java.sun.com/docs/books/jls/">Java Language Specification</a>.
 * <p />
 * <code>ServiceDescriptor</code>s cannot be instantiated directly; they must
 * be obtained from the
 * {@link com.wallabystreet.kinjo.common.transport.ws.ServiceDescriptorFactory}.
 * 
 * @see com.wallabystreet.kinjo.common.transport.ws.ServiceDescriptorFactory
 * @see com.wallabystreet.kinjo.common.transport.protocol.TransportDescriptor
 * 
 * @author Alexander Papaspyrou
 * @version $Revision$, $Date$
 */
final public class ServiceDescriptor {

	/**
	 * The default log facility for this class, using the <a
	 * href="http://jakarta.apache.org/commons/logging/">Jakarta Commons Logging
	 * API</a>.
	 * 
	 * @see org.apache.commons.logging.Log
	 * @see org.apache.commons.logging.LogFactory
	 */
	final private static Log log = LogFactory.getLog( ServiceDescriptor.class );

	/**
	 * Contains the name of the described service, which is derived from the
	 * <wsdl:service name=""> attribute in the service's WSDL description.
	 */
	private String name;

	/**
	 * Contains the full qualified package name of the service implementation.
	 * This is used as the path reference to load various configuration files.
	 */
	private String pkg;

	/**
	 * Stores the WSDL definition of the described service in a
	 * <code>Definition</code> model.
	 * 
	 * @see javax.wsdl.Definition
	 */
	private Definition wsdl;

	/**
	 * Stores the deployment WSDD of the described service in an
	 * <code>Element</code> model.
	 * 
	 * @see org.w3c.dom.Element
	 */
	private Element deploymentWSDD;

	/**
	 * Stores the undeployment WSDD of the described service in an
	 * <code>Element</code> model.
	 * 
	 * @see org.w3c.dom.Element
	 */
	private Element undeploymentWSDD;

	/**
	 * Holds configuration parameters of the described service. Currently, the
	 * following keys are reserved:
	 * <ul>
	 * <li><code>transport</code></li>
	 * <li><code>transport.</code><i>name</i> for the transport packages
	 * ending with "name"</li>
	 * </ul>
	 * Please note that the values stored along with a key can be of arbitrary
	 * type.
	 * <p />
	 * Besides that, only <code>java.lang.String</code> values should be
	 * retrieved using the
	 * {@link java.util.Properties#getProperty(java.lang.String)} method; for
	 * other types, the result will always be <code>null</code> (regardless of
	 * the real value).
	 * 
	 * @see java.lang.Properties
	 */
	private Properties properties;

	/**
	 * Creates a new instance of this type, using the given parameters.
	 * 
	 * @param name
	 *            The name of this service. Not portable.
	 * @param pkg
	 *            The name of the package this service resides at.
	 * @throws MalformedServiceException,
	 *             IIF an error occures during service creation.
	 * 
	 * @deprecated Setting the service name by hand is not portable and
	 *             dangerous.
	 */
	ServiceDescriptor(String name, String pkg)
			throws MalformedServiceException {
		/*
		 * FIXME: broken service name handling.
		 * 
		 * At the moment, we allow to set the service descriptor's "name" field
		 * to some obscure value, which -- by chance -- must correspond to the
		 * name of the web service's only port.
		 * 
		 * Alas, this is an evil hack (tm).
		 * 
		 * The current solution to this problem is the deprecation of this
		 * constructor. Users of the service descriptor class should use
		 * getPort() to retrieve the port name of the service, which -- at least
		 * at the moment is equal to the "name" field; internally we force
		 * <wsdl:service name=""> == <wsdl:port name"">.
		 * 
		 * Unfortunately, this simple approach does not address the
		 * "single-port-per-service" problem.
		 * 
		 * A clean implementation (and that's what we really want to do -- at
		 * least at some point) would provide accessor methods for both service
		 * and port names. This would also imply that each service may have
		 * multiple ports.
		 * 
		 * (Note that this does not seem to be necessary -- I have no idea how a
		 * service interface has to look like to make java2wsdl map it to
		 * multiple ports. Besides that, we then would have to change the
		 * handling of service listener threads to consider multiple ports.)
		 */
		this.name = name;

		this.pkg = pkg;

		this.wsdl = this.loadWSDL();
		this.deploymentWSDD = this.loadWSDD( true );
		this.undeploymentWSDD = this.loadWSDD( false );

		this.properties = this.loadProperties();

		this.loadTransports();
	}

	/**
	 * Creates a new instance of this type, using the given parameters.
	 * 
	 * @param pkg
	 *            The name of the package this service resides at.
	 * @throws MalformedServiceException,
	 *             IIF an error occures during service creation.
	 */
	ServiceDescriptor(String pkg)
			throws MalformedServiceException {
		this.pkg = pkg;

		this.wsdl = this.loadWSDL();

		this.deploymentWSDD = this.loadWSDD( true );
		this.undeploymentWSDD = this.loadWSDD( false );

		this.name = this.getServiceNameFromWSDL();
		this.properties = this.loadProperties();

		this.loadTransports();
	}

	/**
	 * Creates a new instance of this type, using the given parameters.
	 * 
	 * @param pkg
	 *            The name of the package this service resides at.
	 * @param props
	 *            A list of additional properties this service should store.
	 * @throws MalformedServiceException,
	 *             IIF an error occures during service creation.
	 */
	ServiceDescriptor(String pkg, Properties props)
			throws MalformedServiceException {
		/* create standard service descriptor */
		this( pkg );

		/* load additional properties */
		Enumeration e = props.propertyNames();
		while ( e.hasMoreElements() ) {
			String property = (String)e.nextElement();
			this.properties
					.setProperty( property, props.getProperty( property ) );
		}
	}

	/**
	 * Accessor method for the <code>pkg</code> field.
	 * 
	 * @return The <code>pkg</code> field of this type.
	 */
	public String getPackage() {
		return this.pkg;
	}

	/**
	 * Accessor method for the <code>name</code> field.
	 * 
	 * @return The <code>name</code> field of this type.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Returns the only WSDL port of the only WSDL service represented by this
	 * service descriptor's <code>Definition</code>.
	 * 
	 * <p />
	 * This method assumes that the name of the port is equal to the name of the
	 * service. If this is not the case, it will return <code>null</code>.
	 * 
	 * @return The service's WSDL port as a <code>Port</code> instance.
	 * 
	 * @see javax.wsdl.Definition
	 * @see javax.wsdl.Port
	 */
	public Port getPort() {
		Service s = this.wsdl.getService( new QName( this.name ) );
		return s.getPort( s.getQName().toString() );
	}

	/**
	 * Accessor method for the <code>wsdl</code> field.
	 * 
	 * @return The <code>wsdl</code> field of this type.
	 * 
	 * @see javax.wsdl.Definition
	 */
	public Definition getWSDL() {
		return this.wsdl;
	}

	/**
	 * Accessor method for the <code>deploymentWSDD</code> field.
	 * 
	 * @return The <code>deploymentWSDD</code> field of this type.
	 */
	public Element getDeploymentWSDD() {
		return this.deploymentWSDD;
	}

	/**
	 * Accessor method for the <code>undeploymentWSDD</code> field.
	 * 
	 * @return The <code>undeploymentWSDD</code> field of this type.
	 * 
	 * @see org.w3c.dom.Element
	 */
	public Element getUndeploymentWSDD() {
		return this.undeploymentWSDD;
	}

	/**
	 * Accessor method for the <code>properties</code> field.
	 * 
	 * @return The <code>properties</code> field of this type.
	 * 
	 * @see org.w3c.dom.Element
	 */
	Properties getProperties() {
		return this.properties;
	}

	/**
	 * Mutator method for the <code>properties</code> field.
	 * 
	 * @param properties
	 *            The new content of the <code>properties</code> field.
	 */
	void setProperties(Properties properties) {
		this.properties = properties;
	}

	/**
	 * Returns &ndash; if existant &ndash; the property identified by the given
	 * key. Otherwise, the result is <code>null</code>.
	 * 
	 * @param key
	 *            The requested property's key.
	 * @return The property identified by the given key, IIF the key exists;
	 *         <code>null</code>, otherwise.
	 */
	public Object getProperty(String key) {
		return this.properties.get( key );
	}

	/**
	 * Retrieves the first (and only) <code>Service</code> from this service
	 * description's WSDL and returns its name.
	 * 
	 * @return The <code>&lt;wsdl:service name=""&gt;</code> value of the only
	 *         service in the WSDL.
	 * @throws MalformedServiceException,
	 *             IIF the WSDL service definition's number of specified
	 *             services is <code>!= 1</code>.
	 * 
	 * @see javax.wsdl.Service
	 */
	private String getServiceNameFromWSDL()
			throws MalformedServiceException {
		String name = null;

		Map m = this.wsdl.getServices();
		if ( m.size() != 1 ) {
			String msg = "only single-service WSDLs are supported: " + this.pkg;
			throw new MalformedServiceException( msg );
		}

		Collection c = m.values();
		for ( Object entry : c ) {
			Map.Entry e = (Map.Entry)entry;
			Service s = (Service)e.getValue();
			name = s.getQName().toString();
		}
		return name;
	}

	/**
	 * Loads the WSDL of the described service, assuming that the file is stored
	 * in the service package's folder with the file name <i>service.wsdl</i>.
	 * 
	 * @return The WSDL of this service as a <code>javax.wsdl.Definition</code>
	 *         instance.
	 * @throws MalformedServiceException,
	 *             IIF the WSDL file is missing or invalid.
	 * 
	 * @see javax.wsdl.Definition
	 */
	private Definition loadWSDL()
			throws MalformedServiceException {
		// constant for the WSDL file name
		final String WSDL_FILENAME = "service.wsdl";

		Definition d = null;
		try {
			WSDLReader r = WSDLFactory.newInstance().newWSDLReader();

			URL u = ClassLoader.getSystemResource( this.pkg.replace( '.', '/' )
					+ "/" + WSDL_FILENAME );
			d = r.readWSDL( u.toString() );
		}
		catch ( WSDLException e ) {
			String msg = "failed to load the WSDL file";
			if ( log.isErrorEnabled() ) {
				log.error( msg, e );
			}
			throw new MalformedServiceException( msg, e );
		}
		return d;
	}

	/**
	 * Loads the WSDD of the described service, assuming that the file is stored
	 * in the service package's folder with the file name
	 * <ul>
	 * <li><i>deploy.wsdd</i> for the deployment WSDD and</li>
	 * <li><i>undeploy.wsdd</i> for the undeployment WSDD,</li>
	 * </ul>
	 * depending on the passed argument.
	 * 
	 * @param deploy
	 *            Set this parameter to
	 *            <ul>
	 *            <li><code>true</code> for the <b>deployment</b> WSDD</li>
	 *            <li><code>false</code> for the <b>undeployment</b> WSDD</li>
	 *            </ul>
	 * @return The (un)deployment WSDD of this service as an
	 *         <code>org.w3c.dom.Element</code>
	 * @throws MalformedServiceException,
	 *             IIF the requested WSDD file is missing or invalid.
	 * 
	 * @see org.w3c.dom.Element
	 */
	private Element loadWSDD(boolean deploy)
			throws MalformedServiceException {
		// constants for the (un)deployment WSDD file names
		final String DEPLOY_WSDD_FILENAME = "deploy.wsdd";
		final String UNDEPLOY_WSDD_FILENAME = "undeploy.wsdd";

		String wsddFileName = null;
		if ( deploy ) {
			wsddFileName = DEPLOY_WSDD_FILENAME;
		}
		else {
			wsddFileName = UNDEPLOY_WSDD_FILENAME;
		}

		InputStream is = null;
		try {
			is = ClassLoader.getSystemResourceAsStream( pkg.replace( '.', '/' )
					+ "/" + wsddFileName );
			return XMLUtils.newDocument( is ).getDocumentElement();
		}
		catch ( ParserConfigurationException e ) {
			String msg = "parser instantiation failed";
			if ( log.isErrorEnabled() ) {
				log.error( msg, e );
			}
			throw new MalformedServiceException( msg, e );
		}
		catch ( SAXException e ) {
			String msg = "deployment descriptor has errors: " + this.pkg;
			if ( log.isErrorEnabled() ) {
				log.error( msg, e );
			}
			throw new MalformedServiceException( msg, e );
		}
		catch ( IOException e ) {
			String msg = "failed to find " + wsddFileName + " for service \""
					+ this.pkg + "\"";
			if ( log.isErrorEnabled() ) {
				log.error( msg, e );
			}
			throw new MalformedServiceException( msg, e );
		}
		finally {
			try {
				is.close();
			}
			catch ( IOException e ) {
				if ( log.isWarnEnabled() ) {
					log.warn( "InputStream.close() failed", e );
				}
			}
		}
	}

	/**
	 * Loads the configuration of the described service, assuming that the file
	 * is stored in the service package's folder with the file name
	 * <i>properties.xml</i>. It is expected that the file corresponds to the
	 * <a href="http://java.sun.com/dtd/properties.dtd">Java Property File DTD<a/>.
	 * 
	 * @return The configuration of this service as a
	 *         <code>java.util.Properties</code> instance.
	 * @throws MalformedServiceException,
	 *             IIF the property file is missing or invalid.
	 * 
	 * @see java.util.Properties
	 */
	private Properties loadProperties()
			throws MalformedServiceException {
		// constant for the property file name
		final String PROPERTIES_FILENAME = "properties.xml";

		Properties p = new Properties();
		try {
			p.loadFromXML( ClassLoader.getSystemResourceAsStream( this.pkg
					.replace( '.', '/' )
					+ "/" + PROPERTIES_FILENAME ) );

			/*
			 * due to the stoopid implementation of
			 * Properties#loadFromXML(InputStream), leading and trailing
			 * whitespace of values is preserved; we have to fix this to prevent
			 * ClassLoader#getSystemResource(String) and
			 * ClassLoader#getSystemResourceAsStream(String) from wreckage.
			 */
			Enumeration e = p.propertyNames();
			while ( e.hasMoreElements() ) {
				String key = (String)e.nextElement();
				Object o = p.getProperty( key );
				if ( o instanceof String ) {
					String value = (String)o;
					p.setProperty( key, value.trim() );
				}
			}

			/* return the demoronized result */
			return p;
		}
		catch ( InvalidPropertiesFormatException e ) {
			String msg = "invalid configuration file: "
					+ this.pkg.replace( '.', '/' ) + "/" + PROPERTIES_FILENAME;
			if ( log.isErrorEnabled() ) {
				log.error( msg, e );
			}
			throw new MalformedServiceException( msg, e );
		}
		catch ( IOException e ) {
			String msg = "could not open configuration file: "
					+ this.pkg.replace( '.', '/' ) + "/" + PROPERTIES_FILENAME;
			if ( log.isErrorEnabled() ) {
				log.error( msg, e );
			}
			throw new MalformedServiceException( msg, e );
		}
	}

	/**
	 * Loads the transports for the described service, using the values in the
	 * <code>properties</code> field.
	 * <p />
	 * The methods expects to find the supported transports in the
	 * <code>transport</code> property of the aforementioned field, delimited
	 * by a colon (":"). Then, for every token found, it reads the
	 * <code>transport.</code><i>token</i> property. Finally, the value
	 * &ndash; which is expected to be a package name &ndash; is passed to the
	 * {@link #loadTransportSpecificConfiguration(String)} method.
	 * 
	 * @throws MalformedServiceException,
	 *             IIF
	 *             <ol>
	 *             <li>the "transport" property is missing, empty or invalid</li>
	 *             <li>none of the found transports could be configured
	 *             properly</li>
	 *             </ol>
	 * 
	 * @see #loadTransportSpecificConfiguration(String)
	 */
	private void loadTransports()
			throws MalformedServiceException {
		// constant for the "transport" property token delimiter
		final String TRANSPORT_PROPERTY = "transport";
		final String TRANSPORT_DELIMITER = ":";

		/* get the "transport" property */
		String supportedTransports = null;
		supportedTransports = this.properties.getProperty( TRANSPORT_PROPERTY );

		if ( supportedTransports == null ) {
			String msg = "property not found: \""
					+ this.properties.getProperty( TRANSPORT_PROPERTY ) + "\"";
			if ( log.isErrorEnabled() ) {
				log.error( msg );
			}
			throw new MalformedServiceException( msg );
		}

		/* tokenize it using the delimiter constant defined above */
		StringTokenizer t = new StringTokenizer( supportedTransports,
				TRANSPORT_DELIMITER );

		int transportCount = t.countTokens();
		/*
		 * check if we have at least one transport name (however, this doesn't
		 * check whether the property is valid)
		 */
		if ( transportCount < 1 ) {
			String msg = "no transports specified: " + this.pkg;
			if ( log.isErrorEnabled() ) {
				log.error( msg );
			}
			throw new MalformedServiceException( msg );
		}

		/* put the transport(s) into an array */
		String[] transports = new String[t.countTokens()];
		for ( int i = 0; i < transports.length; i++ ) {
			transports[i] = t.nextToken();
		}

		int validTransports = 0;

		for ( String transport : transports ) {
			String transportPackage = this.properties
					.getProperty( TRANSPORT_PROPERTY + "." + transport );
			if ( transportPackage != null ) {
				try {
					this.loadTransportSpecificConfiguration( transportPackage );
					validTransports++;
				}
				catch ( MalformedTransportException e ) {
					if ( log.isWarnEnabled() ) {
						String msg = "could not load transport-specific configuration for "
								+ transportPackage;
						log.warn( msg, e );
					}
				}
			}
			else {
				if ( log.isWarnEnabled() ) {
					String msg = "no package specified for transport \""
							+ transport + "\"";
					log.warn( msg );
				}
			}
		}
		if ( validTransports == 0 ) {
			String msg = "no transports available: " + this.pkg;
			if ( log.isErrorEnabled() ) {
				log.error( msg );
			}
			throw new MalformedServiceException( msg );
		}
	}

	/**
	 * Loads the transport-specific service-related properties and stores them
	 * in the service descriptor's <code>properties</code> field.
	 * <p />
	 * This method expects a class
	 * <ul>
	 * <li>named <code>Transport</code>
	 * <li>with a public default (no parameter, that is) constructor</li>
	 * <li>which implements the <code>TransportDescriptor</code> interface
	 * </ul>
	 * in the package name specified in the argument. It creates a new instance
	 * of this class, calls the
	 * {@link com.wallabystreet.kinjo.common.transport.protocol.TransportDescriptor#getServiceRelatedProperties(String, String)}
	 * method on it and copies all key/value pairs from the result into the
	 * service descriptor's <code>properties</code> field.
	 * 
	 * @param transportPackage
	 *            The package containing the <code>Transport</code> class used
	 *            for configuration.
	 * @throws MalformedTransportException,
	 *             IIF the <code>Transport</code> class is missing,
	 *             inaccessible or incompatible.
	 * 
	 * @see com.wallabystreet.kinjo.common.transport.protocol.TransportDescriptor
	 * @see com.wallabystreet.kinjo.common.transport.protocol.TransportDescriptor#getServiceRelatedProperties(String,
	 *      String)
	 */
	private void loadTransportSpecificConfiguration(String transportPackage)
			throws MalformedTransportException {
		// constant for the transport class and package name
		final String transportPackageName = transportPackage.trim();
		final String transportClassName = "Transport";

		TransportDescriptor d = null;
		try {
			d = (TransportDescriptor)Class.forName(
					transportPackageName + "." + transportClassName )
					.newInstance();
		}
		catch ( InstantiationException e ) {
			String msg = "instantiation failed: " + transportPackageName + "."
					+ transportClassName;
			if ( log.isWarnEnabled() ) {
				log.warn( msg, e );
			}
			throw new MalformedTransportException( msg );
		}
		catch ( IllegalAccessException e ) {
			String msg = "illegal access: " + transportPackageName + "."
					+ transportClassName;
			if ( log.isWarnEnabled() ) {
				log.warn( msg, e );
			}
			throw new MalformedTransportException( msg );
		}
		catch ( ClassNotFoundException e ) {
			String msg = "class not found: " + transportPackageName + "."
					+ transportClassName;
			if ( log.isWarnEnabled() ) {
				log.warn( msg, e );
			}
			throw new MalformedTransportException( msg );
		}

		Properties p = d.getServiceRelatedProperties( this.name, this.pkg );
		Enumeration e = p.propertyNames();
		while ( e.hasMoreElements() ) {
			String property = (String)e.nextElement();
			/*
			 * since we don't know whether the returned properties are
			 * java.lang.String instances, we have to use the generic
			 * java.util.Hashtable methods to get and set the properties
			 */
			this.properties.put( property, p.get( property ) );
		}
	}

}
