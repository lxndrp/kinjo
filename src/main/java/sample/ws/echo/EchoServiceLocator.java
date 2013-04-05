/**
 * EchoServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2.1 Jun 14, 2005 (09:15:57 EDT) WSDL2Java emitter.
 */

package sample.ws.echo;

public class EchoServiceLocator extends org.apache.axis.client.Service implements sample.ws.echo.EchoService {

    public EchoServiceLocator() {
    }


    public EchoServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public EchoServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for TestService
    private java.lang.String TestService_address = "http://example.com/TestService";

    public java.lang.String getTestServiceAddress() {
        return TestService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String TestServiceWSDDServiceName = "TestService";

    public java.lang.String getTestServiceWSDDServiceName() {
        return TestServiceWSDDServiceName;
    }

    public void setTestServiceWSDDServiceName(java.lang.String name) {
        TestServiceWSDDServiceName = name;
    }

    public sample.ws.echo.Echo getTestService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(TestService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getTestService(endpoint);
    }

    public sample.ws.echo.Echo getTestService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            sample.ws.echo.EchoStub _stub = new sample.ws.echo.EchoStub(portAddress, this);
            _stub.setPortName(getTestServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setTestServiceEndpointAddress(java.lang.String address) {
        TestService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (sample.ws.echo.Echo.class.isAssignableFrom(serviceEndpointInterface)) {
                sample.ws.echo.EchoStub _stub = new sample.ws.echo.EchoStub(new java.net.URL(TestService_address), this);
                _stub.setPortName(getTestServiceWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("TestService".equals(inputPortName)) {
            return getTestService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("urn:Example", "EchoService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("urn:Example", "TestService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("TestService".equals(portName)) {
            setTestServiceEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
