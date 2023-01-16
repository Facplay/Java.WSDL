package com.webservices.wsdlutilidad.business;

import com.webservices.wsdlutilidad.domain.WsdlutilidadDTO;
import com.webservices.wsdlutilidad.domain.Response;

/**
 * Interface que permite definir las operaciones
 * 
 * @author Fabian Cata�o Sanchez
 *
 */
public interface WsdlutilidadBusinessInterface {

    Response<WsdlutilidadDTO> ConsultarWsdlutilidad(String nombre,String valores);
}
