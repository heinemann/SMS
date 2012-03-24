/**
 * 
 */
package ms.heinemann.kontact.sms;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author adrian
 * Die Klasse Configuration verwaltet Benutzereinstellungen für die Hauptanwendung.
 * ! Achtung ! Passwörter werden im Klartext gespeichert ! Achtung !
 *
 */

//TODO Das Passwort ist zuverschlüsseln. Mögliche Verschlüsselung: Speicherung eines gesaltzenden Hashwerts.
//TODO Über Verschlüsselungstechniken informieren.


public class Configuration {
	
	Properties prop = new Properties();
	String configfile = "config.sms";
	String Benutzer;
	String Passwort;
	/**
	 * Die Mehtode schreibeKonfiguration erstell eine Konfigurationsdatei, 
	 * mit den Werten Benutzer und Passwort.
	 * 
	 * @param benutzer Name des Benutzers
	 * @param passwort Das Passwort
	 */
	public void schreibeKonfiguration(String benutzer, String passwort){
	try {
		//set the properties value
		prop.setProperty("Benutzer",benutzer);
		prop.setProperty("Passwort", passwort);
		//save properties to project root folder
		prop.store(new FileOutputStream(configfile), null);

	} catch (IOException ex) {
		ex.printStackTrace();
		}
		
	}
	
	/**
	 * Die Methode leseKonfiguration liest die Werte Benutzer und Passwort aus der
	 * Konfigurationsdatei config.sms, die im Ordner der Anwendung liegen muß. 
	 */
	public void leseKonfiguration(){
		Properties prop = new Properties();
		 
    	try {
               //load a properties file
    		prop.load(new FileInputStream(configfile));
 
               //get the property value and print it out
                Benutzer = (prop.getProperty("Benutzer"));
                Passwort = (prop.getProperty("Passwort"));
 
    	} catch (IOException ex) {
    		ex.printStackTrace();
        }
	}

	public String getConfigfile() {
		return configfile;
	}

	public void setConfigfile(String configfile) {
		this.configfile = configfile;
	}

	public String getBenutzer() {
		leseKonfiguration();
		return Benutzer;
	}

	public void setBenutzer(String benutzer) {
		Benutzer = benutzer;
	}

	public String getPasswort() {
		leseKonfiguration();
		return Passwort;
	}

	public void setPasswort(String passwort) {
		Passwort = passwort;
	}

}
