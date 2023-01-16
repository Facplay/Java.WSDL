package com.webservices.wsdlutilidad.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Dictionary;
import java.util.Hashtable;
import com.webservices.wsdlutilidad.domain.WsdlutilidadDTO;
import com.webservices.wsdlutilidad.util.WsdlutilidadConstantes;
import com.webservices.wsdlutilidad.domain.Response;
import com.webservices.wsdlutilidad.provider.WsdlutilidadProviderInterface;

/**
 * Clase a implementar la logica
 * 
 * @author Fabian Cata�o Sanchez
 *
 */
@Service
public class WsdlutilidadBusinessImpl implements WsdlutilidadBusinessInterface {

	/** Logger */
	private static final Logger LOGGER = LoggerFactory.getLogger(WsdlutilidadBusinessImpl.class);


	/** Objeto para realizar la generacion del archivo Json*/
	private WsdlutilidadProviderInterface wsdlutilidadProvider;

	/**
	 * Metodo constructor
	 */
	public WsdlutilidadBusinessImpl(WsdlutilidadProviderInterface wsdlutilidadProvider) {
		this.wsdlutilidadProvider = wsdlutilidadProvider;
	}

	public Response<WsdlutilidadDTO> ConsultarWsdlutilidad(String nombre,String valores){
		LOGGER.debug("Init ConsultarWsdlutilidad: {}", nombre+":"+valores);
		Response<WsdlutilidadDTO> response = null;
		String wsdlutilidadB64;
		Dictionary<String,String> atributos =  new Hashtable<String,String>();

		try{		
			var valoresarr = valores.split(",");	
			for (String item : valoresarr) {
				atributos.put(String.format("%d",item.hashCode()), item);
			}

			wsdlutilidadB64 = wsdlutilidadProvider.GenerarB64(atributos,nombre);

			response = new Response<>(HttpStatus.OK.value(), "Se ha generado correctamente.",
											"Se ha generado correctamente.", WsdlutilidadConstantes.EMPTY_STRING, WsdlutilidadConstantes.EMPTY_STRING,
											new WsdlutilidadDTO(wsdlutilidadB64));
				

		}catch(Exception e){
			response = new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), 
									e.fillInStackTrace().toString(), 
									e.getMessage(), 
									WsdlutilidadConstantes.EMPTY_STRING, WsdlutilidadConstantes.EMPTY_STRING);
		}

		LOGGER.debug("Finish ConsultarWsdlutilidad: {}", nombre+":"+valores);

		return response;
	}

}
