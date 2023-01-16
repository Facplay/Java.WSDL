package com.webservices.wsdlutilidad.domain;

public class WsdlutilidadDTO {
    private String wsdlutilidadB64;

    public WsdlutilidadDTO(String wsdlutilidadB64){
        this.wsdlutilidadB64 = wsdlutilidadB64;
    }

    public String getwsdlutilidadB64() {
        return wsdlutilidadB64;
    }

    public void setwsdlutilidadB64(String wsdlutilidadB64) {
        this.wsdlutilidadB64 = wsdlutilidadB64;
    }
}
