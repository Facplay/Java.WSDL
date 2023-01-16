package com.webservices.wsdlutilidad.exception;

/**
 * Excepcion personalizada de ejemplo para el arquetipo de los microservicios.
 * Permite controlar definir excepciones propias de la aplicacion.
 * 
 * @author Fabian Cataño Sanchez
 *
 * @version 1.0
 * 
 */
public class WsdlutilidadException extends Exception {

	/** Serialize */
	private static final long serialVersionUID = 6365652257268547172L;

	/** Mensaje informativo para el usuario */
	private final String userMessage;

	/** Codigo que define el estado de la transaccion */
	private final int status;

	/**
	 * Metodo constructor
	 */
	public WsdlutilidadException(String developerMessage, String userMessage, int status, Throwable e) {
		super(developerMessage, e);
		this.userMessage = userMessage;
		this.status = status;
	}

	/**
	 * Metodo constructor
	 */
	public WsdlutilidadException(String developerMessage, String userMessage, int status) {
		super(developerMessage);
		this.userMessage = userMessage;
		this.status = status;
	}

	/**
	 * Metodo constructor
	 */
	public WsdlutilidadException(String userMessage, int status, Throwable e) {
		super(e);
		this.userMessage = userMessage;
		this.status = status;
	}

	/**
	 * @return the userMessage
	 */
	public String getUserMessage() {
		return userMessage;
	}

	/**
	 * @return the status.
	 */
	public int getStatus() {
		return status;
	}

}
