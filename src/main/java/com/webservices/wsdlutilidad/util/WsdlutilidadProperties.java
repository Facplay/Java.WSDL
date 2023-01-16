package com.webservices.wsdlutilidad.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Componente de Spring que permite la carga de propiedades personalizadas
 * definidas en el archivo de configuracion del proyecto
 * 
 * @author Fabian Cataño Sanchez
 *
 */
@Component
@ConfigurationProperties(prefix = "wsdlutilidadconf")
public class WsdlutilidadProperties {

    /** Conjunto de propiedades para WSDL utilidad */
    private Wsdlutilidad  wsdlutilidad;
    /** Conjunto de propiedades para Log */
    private Log log;

    /**
     * @return the wsdlutilidad
     */
    public Wsdlutilidad getWsdlutilidad() {
        return wsdlutilidad;
    }

    /**
     * @param wsdlutilidad the wsdlutilidad to set
     */
    public void setWsdlutilidad(Wsdlutilidad wsdlutilidad) {
        this.wsdlutilidad = wsdlutilidad;
    }
    
    /**
     * @return the log
     */
    public Log getLog() {
        return log;
    }

    /**
     * @param log the gigya to set
     */
    public void setLog(Log log) {
        this.log = log;
    }

    public static class  Wsdlutilidad {
        private String endpoint;

        public String getEndpoint() {
            return endpoint;
        }

        public void setEndpoint(String endpoint) {
            this.endpoint = endpoint;
        }        
        
    }  

    public static class Log{
        private String urlLog;
        private String idApp;
        private String usr;
        private String ip;

        public String getUrlLog() {
            return urlLog;
        }
        public void setUrlLog(String urlLog) {
            this.urlLog = urlLog;
        }
        public String getIdApp() {
            return idApp;
        }
        public void setIdApp(String idApp) {
            this.idApp = idApp;
        }
        public String getUsr() {
            return usr;
        }
        public void setUsr(String usr) {
            this.usr = usr;
        }
        public String getIp() {
            return ip;
        }
        public void setIp(String ip) {
            this.ip = ip;
        }      

    }
}
