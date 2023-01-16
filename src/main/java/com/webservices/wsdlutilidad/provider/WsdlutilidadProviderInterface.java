package com.webservices.wsdlutilidad.provider;

import java.util.Dictionary;
/**
 * Clase que define el Provider de wsdlutilidad
 * 
 * @author Fabian Cataño Sanchez
 *
 * @version 1.0
 * 
 */
public interface WsdlutilidadProviderInterface {
    
    String GenerarB64(Dictionary<String,String> atributosWsdlutilidad, String nombre);
}
