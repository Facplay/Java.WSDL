package com.webservices.wsdlutilidad.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Calendar;

import com.webservices.wsdlutilidad.business.WsdlutilidadBusinessInterface;
import com.webservices.wsdlutilidad.domain.WsdlutilidadDTO;
import com.webservices.wsdlutilidad.domain.Response;
import com.webservices.wsdlutilidad.util.WsdlutilidadConstantes;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

/**
 * Clase que define el controlador y los metodos HTTP
 * 
 * @author Fabian Cataño Sanchez
 *
 * @version 1.0
 * 
 */

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.PUT, RequestMethod.GET })
@SwaggerDefinition(tags = { @Tag(name = "general", description = "RestController para el arquetipo") })
public class WsdlutilidadRest {

    private final WsdlutilidadBusinessInterface wsdlutilidadBusiness;

    public WsdlutilidadRest(WsdlutilidadBusinessInterface wsdlutilidadBusiness) {
        super();
        this.wsdlutilidadBusiness = wsdlutilidadBusiness;
    }

    @ApiOperation(value = "Permite consultar y crear JSON.", produces = MediaType.APPLICATION_JSON_VALUE, httpMethod = "GET")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "La consulta se realiza exitosamente", response = Response.class),
			@ApiResponse(code = 400, message = "Error en la consulta, debido a un error en los datos de la peticion recibida", response = Response.class),
			@ApiResponse(code = 500, message = "Error en la consulta, generado por un error en el servidor", response = Response.class) })
	@GetMapping(value = "/consultaWsdlutilidad", produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<WsdlutilidadDTO> consultaWsdlutilidad(
			@ApiParam(value = "Nombre ", name = "nombre", required = true, type = "String") @RequestParam(value = "nombre", required = true) String nombre,
            @ApiParam(value = "Valores separados por comas", name = "valores", required = true, type = "String") @RequestParam(value = "valores", required = true) String valores) {
		return wsdlutilidadBusiness.ConsultarWsdlutilidad(nombre,valores);
	}

	@ApiOperation(value = "Permite testiar  disponibilidad Servicio API.", produces = MediaType.APPLICATION_JSON_VALUE, httpMethod = "GET")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "El test se realiza exitosamente", response = Response.class),
			@ApiResponse(code = 400, message = "Error en la prueba", response = Response.class),
			@ApiResponse(code = 500, message = "Error en la prueba, generado por un error en el servidor", response = Response.class) })
	@GetMapping(value = "/testAPI", produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<String> TestAPI() {

		Response<String> response = null;
		try {
			Calendar today = Calendar.getInstance();
			String testeo = "Servicio Activo....!  " + today.getTime();

		    response = new Response<>(HttpStatus.OK.value(), "Prueba funcionamiento API.", 
			"Prueba funcionamiento API.",
			 WsdlutilidadConstantes.EMPTY_STRING, WsdlutilidadConstantes.EMPTY_STRING
			 ,testeo);      

		} catch (Exception e) {
			response = new Response<>(HttpStatus.CONFLICT.value(), "Prueba fallida funcionamiento API.", 
			"Prueba fallida funcionamiento API.",
			WsdlutilidadConstantes.EMPTY_STRING, WsdlutilidadConstantes.EMPTY_STRING
			 ,e.toString());     
		}

		return response;
	}
}
