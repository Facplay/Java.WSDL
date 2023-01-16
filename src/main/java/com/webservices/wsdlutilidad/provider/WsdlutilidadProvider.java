package com.webservices.wsdlutilidad.provider;

import java.util.Dictionary;
import java.util.Enumeration;

import com.webservices.wsdlutilidad.util.WsdlutilidadProperties;
import com.webservices.wsdlutilidad.util.ContratoWSDL;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Clase que define el Provider de Wsdlutilidad
 * 
 * @author Fabian Cataño Sanchez
 *
 * @version 1.0
 * 
 */

@Component
@Primary
public class WsdlutilidadProvider implements WsdlutilidadProviderInterface  {
    /** Objeto para la carga de propiedades de configuracion */
    private final WsdlutilidadProperties properties;
    /** Logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(WsdlutilidadProvider.class);
    
    /**
     * Metodo constructor
     * 
     * @param properties
     */
    public WsdlutilidadProvider(WsdlutilidadProperties properties) {
         this.properties = properties;
    }

    public String GenerarB64(Dictionary<String,String>  atributosWsdlutilidad, String nombre) {

		StringBuilder response = new StringBuilder();

		//Instancia Contrato con targetNamespace
		var contrato = new ContratoWSDL("uri:WEBSERVICES", "");
		//Objeto a enviar
		var generateArchivoJSON =  contrato.new ObjetosEnvio("generateArchivoJSON");

		var request =  contrato.new ObjetosEnvio("request");		
		request.agregarParametro("nombreObjeto",nombre);

		var attributes =  contrato.new ObjetosEnvio("attributes","SOAP-ENC:arrayType=\"urn:Attribute[]\"");
		

        Enumeration<String> keys = atributosWsdlutilidad.keys();

        while(keys.hasMoreElements()){
            var attribute =  contrato.new ObjetosEnvio("attribute");
            var key = keys.nextElement();
            attribute.agregarParametro("name", key);
            attribute.agregarParametro("value", atributosWsdlutilidad.get(key));

            attributes.agregarObjeto(attribute);
        }
   

		request.agregarObjeto(attributes);

		generateArchivoJSON.agregarObjeto(request);
		contrato.AdicionarObjeto(generateArchivoJSON);

		contrato.setUrl(this.properties.getWsdlutilidad().getEndpoint());
		contrato.setSoaPActionMethod("uri:WEBSERVICES/generateArchivoJSON");				

		contrato.Invocar(response,"return");

        return response.toString();

	}
}
