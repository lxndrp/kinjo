/**
 * EchoService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2.1 Jun 14, 2005 (09:15:57 EDT) WSDL2Java emitter.
 */

package sample.ws.echo;

public interface EchoService extends javax.xml.rpc.Service {
    public java.lang.String getTestServiceAddress();

    public sample.ws.echo.Echo getTestService() throws javax.xml.rpc.ServiceException;

    public sample.ws.echo.Echo getTestService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
