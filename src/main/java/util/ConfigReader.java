package util;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class ConfigReader {
    String username;
    String password;
    String url;

    /**
     * Reads the config file.
     * @param path pathe to the file
     */
    public ConfigReader(String path) {
        File xml = new File(path);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Document doc = null;
        try {
            doc = builder.parse(xml);
        } catch (SAXException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.username = doc.getElementsByTagName("username").item(0).getTextContent();
        this.password = doc.getElementsByTagName("password").item(0).getTextContent();
        this.url = doc.getElementsByTagName("url").item(0).getTextContent();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
