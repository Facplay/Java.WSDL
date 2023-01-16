package com.webservices.wsdlutilidad;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Dictionary;
import java.util.Hashtable;

import com.webservices.wsdlutilidad.util.ContratoWSDL;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WsdlutilidadApplicationTests {

	@Test
	void contextLoads() {
		StringBuilder res = new StringBuilder();
		//Direccion Larga de la Url
		var contrato = new ContratoWSDL("http://tempuri.org/", "");
		//Objeto a enviar
		var oSend =  contrato.new ObjetosEnvio("probarServicio");
		contrato.AdicionarObjeto(oSend);
		contrato.setUrl("https://<url>/service.svc");
		contrato.setSoaPActionMethod("http://tempuri.org/IService/probarServicio");
		
		//contrato.Invocar(res,"probarServicioResult");

	}

	@Test
	void DiccionrioContratoTest() {
		Dictionary<String,String> Nodos = new Hashtable<String,String>();
		//Direccion Larga de la Url
		var contrato = new ContratoWSDL("http://www.catastro.meh.es/", "");
		//Objeto a enviar
		var oSend =  contrato.new ObjetosEnvio("");
		contrato.AdicionarObjeto(oSend);
		contrato.setUrl("http://ovc.catastro.meh.es/ovcservweb/ovcswlocalizacionrc/ovccallejero.asmx");
		contrato.setSoaPActionMethod("http://tempuri.org/OVCServWeb/OVCCallejero/ConsultaProvincia");		
		
		Nodos.put("cpine", "");
		Nodos.put("np", "");
		Nodos.put("cuprov", "");

		contrato.Invocar(Nodos);

	}

	@Test
	void servicioWebPHPTest() {
		//Dictionary<String,String> Nodos = new Hashtable<String,String>();
		StringBuilder res = new StringBuilder();

		//Direccion Larga de la Url
		var contrato = new ContratoWSDL("uri:WEBSERVICES", "");
		//Objeto a enviar
		var generateArchivoJSON =  contrato.new ObjetosEnvio("generateArchivoJSON");

		var request =  contrato.new ObjetosEnvio("request");
		var attributes =  contrato.new ObjetosEnvio("attributes","SOAP-ENC:arrayType=\"urn:Attribute[]\"");
		
		var attribute =  contrato.new ObjetosEnvio("attribute");
		attribute.agregarParametro("name", "Name");
		attribute.agregarParametro("value", "JF");

		var attribute1 =  contrato.new ObjetosEnvio("attribute");

		attribute1.agregarParametro("name", "Description");
		attribute1.agregarParametro("value", "NA");

		attributes.agregarObjeto(attribute);
		attributes.agregarObjeto(attribute1);

		request.agregarParametro("nombreObjeto","Test");
		request.agregarObjeto(attributes);

		generateArchivoJSON.agregarObjeto(request);
		contrato.AdicionarObjeto(generateArchivoJSON);

		contrato.setUrl("https://webserverphp.000webhostapp.com/webservices/archivojson.php");
		contrato.setSoaPActionMethod("uri:WEBSERVICES/generateArchivoJSON");		
		

		contrato.Invocar(res);

	}

	@Test
	void pruebaConexionHTTP(){
		
		try{
			HttpClient httpClient = HttpClient.newBuilder()
			.version(HttpClient.Version.HTTP_1_1)
			.build();

			//Creamos los objetos de respuesta y petición  
			HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
					.uri(URI.create("https://webserverphp.000webhostapp.com/webservices/archivojson.php"));
					//.headers("Content-Type", "text/xml;charset=UTF-8"); // add request header
			
			//HttpRequest request  = requestBuilder.POST(BodyPublishers.ofString("[]")).build();
			HttpRequest request  = requestBuilder.GET().build();
			var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			var responseBody = response.body();

		}catch(Exception ex){
			var mensaje = ex.getMessage();
		}
	}

}
