package com.webservices.wsdlutilidad.util;

import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.util.Base64;
import java.util.Collections;
import java.util.Dictionary;

import javax.net.ssl.SSLContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.time.Duration;

/**
 * Clase que consulta el un Web Service SOAP. 
 * 
 * @author Fabian Cataño Sanchez
 *
 */
  public class ContratoWSDL
  {
    private String sNameSpace;
    private String sMethod;
    private String ncCredencial;
    private ObjetosEnvio[] oObjeto;
    private int nCantidadObjetos;
    private String sUrl;
    private String prefObjSend;
    private Boolean prefObjSendParameter;
    private int timeOut;
    private String contentType;

    public void setSoaPActionMethod(String value)
    {sMethod = value;}    
    public String getSoaPActionMethod()
    { return sMethod; } 

    /// <summary>
    /// propiedad de la etiqueta que se utiliza para indicar que en los parametros tambien se agrega el prefijo
    /// </summary>
    public void setPrefObjSendParameter(Boolean value)
    { prefObjSendParameter = value; }    
    public Boolean getPrefObjSendParameter()
    { return prefObjSendParameter; } 

    /// <summary>
    /// propiedad de la etiqueta a utilizar como prefijo del mensaje a enviar
    /// </summary>
    public void setPrefObjSend(String value)
    { prefObjSend = value; } 
    public String getPrefObjSend()
    { return prefObjSend; }

    public void setTimeOut(int value)
    { timeOut = value; } 
    public int getTimeOut()
    { return timeOut; }

    public void setContentType(String value)
    { contentType = value; } 
    public String getContentType()
    { return contentType; }

    /// <summary>
    /// Consume un Web Servicio Web SOAP.
    /// </summary>
    /// <param name="NameSpace">Nombre del namespace del tag que contiene el Objeto u objetos a enviar.</param>
    public ContratoWSDL(String NameSpace)
    {
      sNameSpace = NameSpace;
      ncCredencial = null;
      nCantidadObjetos = 0;
      sMethod = "";
      timeOut = 0;
      prefObjSend = "";
      contentType = "text/xml;charset=UTF-8";
    }

  
    /// <summary>
    /// Consume un Web Servicio Web SOAP.
    /// </summary>
    /// <param name="NameSpace">Nombre del namespace del tag que contiene el Objeto u objetos a enviar.</param>
    /// <param name="Usuario">Usuario para la credencial de seguridad.</param>
    /// <param name="Clave">Contraseña para la credencial de seguridad.</param>
    public ContratoWSDL(String NameSpace, String Usuario, String Clave)
    {
      try
      {
        sNameSpace = NameSpace;
        String auth = Usuario + ":" + Clave;
        String encodedAuth = Base64.getEncoder().encodeToString((auth).getBytes("UTF‌​-8"));
      
        ncCredencial = "Basic " + new String( encodedAuth );
        nCantidadObjetos = 0;
        sMethod = "";
        prefObjSend = "";
        contentType = "text/xml;charset=UTF-8";
      }catch(Exception oExcepcion)
      {        
        throw new ExceptionInInitializerError("ContratoWSDL ->"+oExcepcion.toString());
      }
    }

    /// <summary>
    /// Consume un Web Servicio Web de SAP.
    /// </summary>
    /// <param name="NameSpace">Nombre del namespace del tag que contiene el Objeto u objetos a enviar.</param>
    /// <param name="Credencial">Credencial de seguridad</param>
    public ContratoWSDL(String NameSpace, String Credencial)
    {
      sNameSpace = NameSpace;
      ncCredencial = Credencial;
      nCantidadObjetos = 0;
      sMethod = "";
      prefObjSend = "";
      contentType = "text/xml;charset=UTF-8";
    }

    /// <summary>
    /// Agrega la credencial requerida para ejecutar el Web Service.
    /// </summary>
    /// <param name="Usuario">Usuario</param>
    /// <param name="Clave">Contraseña</param>
    public void Credenciales(String Usuario, String Clave)
    {
      try
      {
        String auth = Usuario + ":" + Clave;
        String encodedAuth = Base64.getEncoder().encodeToString((auth).getBytes("UTF‌​-8"));
        
        ncCredencial = "Basic " + new String( encodedAuth );
      }catch(Exception oExcepcion){
              
        throw new ExceptionInInitializerError("ContratoWSDL ->"+oExcepcion.toString());
      }
    }

    /// <summary>
    /// Asignar la credencial requerida para ejecutar el Web Service.
    /// </summary>
    public void setCredenciales(String value)
    { ncCredencial = value; } 
 

    /// <summary>
    /// Asigna la url larga para consumir el Web Service.
    /// </summary>
    public void setUrl(String value)
    { sUrl = value; } 
    public String getUrl()
    { return sUrl; }


    /// <summary>
    /// Agrega la url corta para consumir el Web Service.
    /// </summary>
    /// <param name="URL">Url corta del Web Service.</param>
    public void UrlCorta(String URL)
    {
      sUrl = cargarUrlLarga(URL);
    }

    /// <summary>
    /// Agrega un nuevo Objeto a enviar al Web Service.
    /// </summary>
    /// <param name="NombreObjeto">Nombre del tag del objeto según el esquema XML del Web Service.</param>
    public void AdicionarObjeto(String NombreObjeto)
    {
      if (nCantidadObjetos == 0)
      {
        oObjeto = new ObjetosEnvio[nCantidadObjetos + 1];
        oObjeto[0] = new ObjetosEnvio(NombreObjeto);
      }
      else
      {
        ObjetosEnvio[] oObjetosAux = oObjeto;
        oObjeto = new ObjetosEnvio[nCantidadObjetos + 1];
        for (int i = 0; i < oObjetosAux.length; i++)
        {
          oObjeto[i] = oObjetosAux[i];
        }
        oObjeto[nCantidadObjetos] = new ObjetosEnvio(NombreObjeto);
      }
      nCantidadObjetos++;
    }

    /// <summary>
    /// Agrega un nuevo Objeto a enviar al Web Service.
    /// </summary>
    /// <param name="objetoEnvio">objeto del tipo ObjetosEnvio que se va a agregar.</param>
    public void AdicionarObjeto(ObjetosEnvio objetoEnvio)
    {
      if (nCantidadObjetos == 0)
      {
        oObjeto = new ObjetosEnvio[nCantidadObjetos + 1];
        oObjeto[0] = objetoEnvio;
      }
      else
      {
        ObjetosEnvio[] oObjetosAux = oObjeto;
        oObjeto = new ObjetosEnvio[nCantidadObjetos + 1];
        for (int i = 0; i < oObjetosAux.length; i++)
        {
          oObjeto[i] = oObjetosAux[i];
        }
        oObjeto[nCantidadObjetos] = objetoEnvio;
      }
      nCantidadObjetos++;
    }

    /// <summary>
    /// Peticion HTTP hacia el servicio SOAP.
    /// </summary>
    private String PeticionHTTPService()
    {
      StringBuilder sXMLEnviar = new StringBuilder();
      String responseServvice;
      if (oObjeto != null && ncCredencial != null && sNameSpace != null)
      {
        SSLContext context = null;
        try {
          context = SSLContext.getInstance("TLSv1.2");
          context.init(null, null, null);

        } catch (Exception e) {
          throw new RuntimeException("ContratoWSDL > PeticionHTTPService()) > SSLContext->" + e.toString());
        }

         //HTTP Client
         HttpClient httpClient = HttpClient.newBuilder()
        .sslContext(context)
        .version(HttpClient.Version.HTTP_1_1)
        .build();

        //Creamos los objetos de respuesta y petición  
        HttpResponse<String>  response;
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(sUrl))
                .headers("Authorization", ncCredencial,"Content-Type", contentType); // add request header

        //Tiempo de espera en la ejecución  del un metodo
        if (timeOut > 0)
        {
            requestBuilder = requestBuilder.timeout(Duration.ofSeconds(timeOut));
        }

        if (!sMethod.equals(""))
        {
            requestBuilder = requestBuilder.headers("SOAPAction", sMethod);
        }

        if (!prefObjSend.equals(""))
        {
          sXMLEnviar.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");

          sXMLEnviar.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:" + prefObjSend + "=\"" + sNameSpace + "\"> <soap:Body>");
        }
        else
        {
          sXMLEnviar.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
          sXMLEnviar.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"" + sNameSpace + "\"><soap:Body>");
        }

        //Cargamos los objetos a enviar
        for (int i = 0; i < oObjeto.length; i++)
        {
          String prefObjSend_= "";

          if (!prefObjSend.equals("")  && !oObjeto[i].getNombre().equals(""))
          {
            prefObjSend_ = prefObjSend+":";
            sXMLEnviar.append("<" + prefObjSend_ + oObjeto[i].getNombre() + oObjeto[i].getAtrubutos() + ">");
          }
          else if(!oObjeto[i].getNombre().equals(""))
          {
            sXMLEnviar.append("<urn:" + oObjeto[i].getNombre() + oObjeto[i].getAtrubutos() + ">");
          }

          //Cargamos los parametros
          for (int j = 0; j < oObjeto[i].getCantidadParametros(); j++)
          {
            sXMLEnviar.append("<" + prefObjSend_ + oObjeto[i].obtenerParametro(j).getName() + oObjeto[i].obtenerParametro(j).getAtributos() + ">");
            sXMLEnviar.append(oObjeto[i].obtenerParametro(j).getValue());
            sXMLEnviar.append("</" + prefObjSend_ + oObjeto[i].obtenerParametro(j).getName() + ">");
          }
          //Si tiene objetos hijos, entonces deben ser cargados en el esquema
          if (oObjeto[i].getCantidadObjetos() > 0)
          {
            sXMLEnviar.append(cargarObjetosHijos(oObjeto[i].obtenerObjetos()));
          }

          if (!prefObjSend.equals("") && !oObjeto[i].getNombre().equals(""))
          {
            sXMLEnviar.append("</" + prefObjSend_ + oObjeto[i].getNombre() + ">");
          }
          else if (!oObjeto[i].getNombre().equals(""))
          {
            sXMLEnviar.append("</urn:" + oObjeto[i].getNombre() + ">");
          }
        }
        sXMLEnviar.append("</soap:Body></soap:Envelope>");


        HttpRequest request  = requestBuilder.POST(BodyPublishers.ofString(sXMLEnviar.toString())).build();
        //Realizamos la petición solicitando una respuesta
        try
        {
          response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
          responseServvice = response.body();
        }
        catch (Exception objExepcion)
        {
          StringWriter errors = new StringWriter();
          objExepcion.printStackTrace(new PrintWriter(errors));
          throw new RuntimeException("ContratoWSDL > PeticionHTTPService() > HttpRequest->" + sUrl + ":"+objExepcion.toString()+"-"+errors.toString());
        }
      } 
      else
      {
        throw new RuntimeException("ContratoWSDL > PeticionHTTPService() -> No existe un objeto. ");
      }
      
      return responseServvice;
    }


    /// <summary>
    /// Consume el webservice y obtiene como respuesta el String que se envia como referencia
    /// </summary>
    /// <param name="Respuesta">Respuesta que se obtiene del XML</param>
    /// <param name="Nodo">Nodo que se busca en el XML obtenido</param>
    public void Invocar(StringBuilder respuesta, String Nodo)
    {
        try
        {
          String response = PeticionHTTPService();
          NodeList nodoXml;
          // Creo una instancia de DocumentBuilderFactory
          DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
          // Creo un documentBuilder
          DocumentBuilder builder = factory.newDocumentBuilder();

          // Obtengo el documento, a partir del XML
          Document xmlDoc;
          try{
             xmlDoc = builder.parse(new InputSource(new StringReader(response)));
          }catch(Exception ex){
            respuesta.append(response);
            return;
          }

          if((nodoXml = xmlDoc.getElementsByTagNameNS(sNameSpace, Nodo)).getLength() > 0){
            respuesta.append(nodoXml.item(0).getTextContent());
          }else if((nodoXml = xmlDoc.getElementsByTagName(Nodo)).getLength() > 0){
            respuesta.append(nodoXml.item(0).getTextContent());
          }else{
            respuesta.append("");
          }

        }
        catch (Exception oExcepcion)
        {
          throw new RuntimeException("ContratoWSDL > Invocar(respuesta,Nodo) ->" + oExcepcion.toString());
        }
    }

    /// <summary>
    /// Consume el webservice y obtiene como respuesta el String que se envia como referencia
    /// </summary>
    /// <param name="Respuesta">Respuesta que se obtiene del XML</param>
    /// <param name="Nodo">Nodo que se busca en el XML obtenido</param>
    public void Invocar(Dictionary<String,String> Nodos)
    {
        try
        {
          String response = PeticionHTTPService();
          NodeList nodoXml;
          // Creo una instancia de DocumentBuilderFactory
          DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
          // Creo un documentBuilder
          DocumentBuilder builder = factory.newDocumentBuilder();

          // Obtengo el documento, a partir del XML
          Document xmlDoc = builder.parse(new InputSource(new StringReader(response)));

          var nodosKey = Collections.list(Nodos.keys());

          for (var nodo : nodosKey) {
            if((nodoXml = xmlDoc.getElementsByTagNameNS(sNameSpace, nodo)).getLength() > 0){

              String datos = "[";
              for(int i =0;i < nodoXml.getLength();i++){
                datos += "\""+nodoXml.item(i).getTextContent()+"\""+",";
              }
              datos += "]";
              datos = datos.replaceAll(",]", "]");

              Nodos.put(nodo, datos) ;

            }else if((nodoXml = xmlDoc.getElementsByTagName(nodo)).getLength() > 0){

              String datos = "[";
              for(int i =0;i < nodoXml.getLength();i++){
                datos += "\""+nodoXml.item(i).getTextContent()+"\""+",";
              }
              datos += "]";
              datos = datos.replaceAll(",]", "]");

              Nodos.put(nodo, datos);

            }else{
              Nodos.put(nodo,"") ;
            }
          }

        }
        catch (Exception oExcepcion)
        {
          throw new RuntimeException("ContratoWSDL > Invocar(Dictionary-Nodos) ->" + oExcepcion.toString());
        }
    }

    /// <summary>
    /// Consume el webservice y obtiene como respuesta el String que se envia como referencia
    /// </summary>
    /// <param name="Respuesta">Respuesta que se obtiene del XML</param>
    public void Invocar(StringBuilder respuesta)
    {
        try
        {
          respuesta.append(PeticionHTTPService());
        }
        catch (Exception oExcepcion)
        {
          throw new RuntimeException("ContratoWSDL > Invocar(respuesta) ->" + oExcepcion.toString());
        }
    }

    /// <summary>
    /// Consume el Web Service y XML Document.
    /// </summary>
    /// <returns>XML Document con el resultado de la llamada al Web Service, devuelve null si no se han especificado todos los atributos necesarios para la llamada.</returns>
    public Document Invocar()
    {
      try
      {
        String sRespuesta = PeticionHTTPService();

        // Creo una instancia de DocumentBuilderFactory
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        // Creo un documentBuilder
        DocumentBuilder builder = factory.newDocumentBuilder();

        // Obtengo el documento, a partir del XML
        return builder.parse(new InputSource(new StringReader(sRespuesta)));
 
      }
      catch (Exception oExcepcion)
      {
        throw new RuntimeException("AfiliacionesContratoWSDL > Invocar() ->" + oExcepcion.toString());
      }
    }

    /// <summary>
    /// Carga el esquema que se va a enviar dentro de un String al estilo de un XML, partiendo de un arreglo de objetos hijos de otro objeto a enviar.
    /// </summary>
    /// <param name="Objetos">Arreglo de objetos</param>
    /// <returns></returns>
    private String cargarObjetosHijos(ObjetosEnvio[] Objetos)
    {
      StringBuilder xmlSchema = new StringBuilder();
      String prefObjSend_= "";
      if(!prefObjSend.equals("")){
        prefObjSend_ = prefObjSend+":";
      }
      //Cargamos los objetos
      for (int i = 0; i < Objetos.length; i++)
      {
        xmlSchema.append("<" + prefObjSend_ + Objetos[i].getNombre() + Objetos[i].getAtrubutos() + ">");
        //Luego los parametros de esos objetos
        for (int j = 0; j < Objetos[i].getCantidadParametros(); j++)
        {
          xmlSchema.append("<" + prefObjSend_ + Objetos[i].obtenerParametro(j).getName() + Objetos[i].obtenerParametro(j).getAtributos() + ">");
          xmlSchema.append(Objetos[i].obtenerParametro(j).getValue());
          xmlSchema.append("</" + prefObjSend_ + Objetos[i].obtenerParametro(j).getName() + ">");
        }
     
        if (Objetos[i].getCantidadObjetos() > 0)
        {
          xmlSchema.append(cargarObjetosHijos(Objetos[i].obtenerObjetos()));
        }

        xmlSchema.append("</"+ prefObjSend_ + Objetos[i].getNombre() + ">");
      }
      return xmlSchema.toString();
    }

    /// <summary>
    /// Consulta el esquema de un Web Service y devuelve la URL larga
    /// </summary>
    /// <returns>Url larga del esquema</returns>
    private String cargarUrlLarga(String ShorURL)
    {
      String sUrl = "";
      return sUrl;
    }

    /// <summary>
    /// Clase de los objetos que se enviarán al Web Service con sus respectivos parámetros.
    /// </summary>
    public class ObjetosEnvio
    {
      private int nCantidadParametros;
      private Parametro[] oParametros;
      private int nCantidadObjetos;
      private ObjetosEnvio[] oObjetosEnvio;
      private String sNombreObjeto;
      private String sAtributos;

      /// <summary>
      /// Nuevo Objeto envío.
      /// </summary>
      /// <param name="NombreObjeto">Nombre del tag del objeto según el esquema XML del Web Service.</param>
      public ObjetosEnvio(String NombreObjeto)
      {
        nCantidadParametros = 0;
        nCantidadObjetos = 0;
        sNombreObjeto = NombreObjeto;
        sAtributos = "";
      }

      /// <summary>
      /// Nuevo Objeto envío.
      /// </summary>
      /// <param name="NombreObjeto">Nombre del tag del objeto según el esquema XML del Web Service.</param>
      public ObjetosEnvio(String NombreObjeto, String Atributos)
      {
        nCantidadParametros = 0;
        nCantidadObjetos = 0;
        sNombreObjeto = NombreObjeto;
        sAtributos = " "+Atributos;
      }

      public void agregarParametro(String NombreParametro, String ValorParametro)
      {
        this.agregarParametrop(NombreParametro, ValorParametro, "");
      }

      public void agregarParametro(String NombreParametro, String ValorParametro, String Atributos)
      {
        this.agregarParametrop(NombreParametro, ValorParametro, Atributos);
      }

      /// <summary>
      /// Agrega un nuevo parametro correspondiente al objeto que se va a enviar.
      /// </summary>
      /// <param name="NombreParametro">Nombre del parametro según el esquema XML del Web Service.</param>
      /// <param name="ValorParametro">Valor del parámetro</param>
      private void agregarParametrop(String NombreParametro, String ValorParametro,String Atributos)
      {
        if (nCantidadParametros == 0)
        {
          oParametros = new Parametro[nCantidadParametros + 1];
          oParametros[0] = new Parametro(NombreParametro, ValorParametro,Atributos);
        }
        else
        {
          Parametro[] oParametrosAux = oParametros;
          oParametros = new Parametro[nCantidadParametros + 1];
          for (int i = 0; i < oParametrosAux.length; i++)
          {
            oParametros[i] = oParametrosAux[i];
          }
          oParametros[nCantidadParametros] = new Parametro(NombreParametro, ValorParametro,Atributos);
        }
        nCantidadParametros++;
      }

      /// <summary>
      /// Agrega un objeto interno dentro de este objeto, únicamente si el objeto a agregar tiene por lo menos un parámetro.
      /// </summary>
      /// <param name="Objeto"></param>
      public void agregarObjeto(ObjetosEnvio Objeto)
      {
        if (Objeto.nCantidadParametros > 0 || Objeto.nCantidadObjetos  > 0)
        {
          if (nCantidadObjetos == 0)
          {
            oObjetosEnvio = new ObjetosEnvio[nCantidadObjetos + 1];
            oObjetosEnvio[0] = Objeto;
          }
          else
          {
            ObjetosEnvio[] oAux = oObjetosEnvio;
            oObjetosEnvio = new ObjetosEnvio[nCantidadObjetos + 1];
            for (int i = 0; i < oAux.length; i++)
            {
              oObjetosEnvio[i] = oAux[i];
            }
            oObjetosEnvio[nCantidadObjetos] = Objeto;
          }
          nCantidadObjetos++;
        }
      }

      /// <summary>
      /// Obtiene una colección de ObjetosEnvio pertenecientes al ObjetoEnvio actual.
      /// </summary>
      /// <returns></returns>
      public ObjetosEnvio[] obtenerObjetos()
      {
        return oObjetosEnvio;
      }

      /// <summary>
      /// Devuelve el parametro según el índice especificado.
      /// </summary>
      /// <param name="Index">Índice del parámetro</param>
      /// <returns>Parametro</returns>
      public Parametro obtenerParametro(int Index)
      {
        return oParametros[Index];
      }

      /// <summary>
      /// Obtiene el nombre del objeto.
      /// </summary>
      /// <returns>Nombre del objeto.</returns>
      public String getNombre()
      { return sNombreObjeto; }
      

      /// <summary>
      /// Obtiene la cantidad de parámetros que posee un objeto.
      /// </summary>
      /// <returns>Cantidad de parámetros.</returns>
      public int getCantidadParametros() 
      { return nCantidadParametros; }
      

      /// <summary>
      /// Obtiene la cantidad de objetos hijos que posee un objeto.
      /// </summary>
      /// <returns>Cantidad de objetos.</returns>
      public int getCantidadObjetos()
      { return nCantidadObjetos; }

      /// <summary>
      /// Obtiene los Atributos que posee un objeto.
      /// </summary>
      /// <returns>Atributos de objetos.</returns>
      public String getAtrubutos()
      { return sAtributos; }

      /// <summary>
      /// Clase de los parametros a enviar al Web Service.
      /// </summary>
      /// 
      public class Parametro
      {
        String sNombreParametro;
        String sValorParametro;
        String sAtributos;

        /// <summary>
        /// Parametro nuevo
        /// </summary>
        /// <param name="NombreParametro">Nombre del parametro</param>
        /// <param name="ValorParametro">Valor del parametro</param>
        public Parametro(String NombreParametro, String ValorParametro)
        {
          sNombreParametro = NombreParametro;
          sValorParametro = ValorParametro;
          sAtributos = "";
        }

        /// <summary>
        /// Parametro nuevo
        /// </summary>
        /// <param name="NombreParametro">Nombre del parametro</param>
        /// <param name="ValorParametro">Valor del parametro</param>
        /// <param name="Atributo">Atributo del parametro</param>
        public Parametro(String NombreParametro, String ValorParametro, String Atributos)
        {
          sNombreParametro = NombreParametro;
          sValorParametro = ValorParametro;
          sAtributos = " "+Atributos;
        }
        /// <summary>
        /// Devuelve el nombre del parámetro.
        /// </summary>
        /// <returns>Nombre del parámetro</returns>
        public String getName()
        {
          return sNombreParametro;
        }

        /// <summary>
        /// Devuelve el valor del parámetro.
        /// </summary>
        /// <returns>Valor del parámetro.</returns>
        public String getValue()
        {
          return sValorParametro;
        }

        /// <summary>
        /// Devuelve Atributos del parámetro.
        /// </summary>
        /// <returns>Atributos del parámetro.</returns>
        public String getAtributos()
        {
          return sAtributos;
        }
      }
    }
  }