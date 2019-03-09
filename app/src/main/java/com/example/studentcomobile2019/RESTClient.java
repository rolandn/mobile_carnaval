package com.example.studentcomobile2019;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RESTClient {
    /**
     * Les paramètres de la connexion
     */
    private String protocol = "http";
    private String host = "10.0.2.2";//
    private int port = 80;
    private String method = "GET";

    /**
     * Appeler un webservice
     * @param request la requête REST Ex : "/studentCo/login/<username>/<password>/"
     * @return la réponse (JSON) du webservice
     */
    public String callWebservice(String request) {
        //request = "/studentCo/login/"+username+"/"+password+"/";
        URL uri = null;
        BufferedReader reader = null;
        StringBuilder stringBuilder;
        try {
            uri = new URL(protocol,host,port,request);
            HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
            connection.setRequestMethod(method);
            //connection.setReadTimeout(15*1000);//give it max 15 seconds to respond
            //Connecter/envoyer la requête
            connection.connect();
            //
            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);
            //
            stringBuilder = new StringBuilder();
            String line = null;
            int i=0;
            while ((line = reader.readLine()) != null)
            {
                if (i++>0) stringBuilder.append("\n");
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "RESTClient{" +
                "protocol='" + protocol + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", method='" + method + '\'' +
                '}';
    }
}
