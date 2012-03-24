package ms.heinemann.kontact.sms;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * Send SMS via SMS-Gateway of SMS-Expert.
 * 
 * <p>Copyright 2009 Bastian Treger (SMS-Expert)</p>
 * 
 * <p>Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at</p>
 *
 *      <p>http://www.apache.org/licenses/LICENSE-2.0</p>
 *
 * <p>Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.</p>
 * 
 * @author Bastian Treger
 * @author http://www.sms-expert.de
 * @version 1.0
 */

public class SMSExpertSender {	
	private static String user = "";
	private static String gatewaypassword = "";
	private static String sendMode = "POST";
	private static Boolean debug = false; 
	
	private static String gateway_protocol = "https://";
	private static String gateway_host = "gateway.sms-expert.de";
	private static Integer gateway_port = 443;
	private static String gateway_urlpath = "/send/";
	
	private static String version = "1.0";
	
	private String type = null;
	private String sender = null;
	private String receiver = null;
	private String message = null;
	private long timestamp = 0;
	private Integer responseStatusCode = null;
	private String responseStatusText = null;
	private String responseMessageId = null;
	private Double responseCost = null;
	
	
	/**
	 * Sets the SMS-type. 
	 * 
	 * @param type The SMS-type. Possible values are "standard" or "expert"
	 * @throws SMSExpertSenderException
	 *
	 */
	
	public void setSMSType(String type) throws SMSExpertSenderException{
		if (!type.equals("expert") & !type.equals("standard")){
			throw new SMSExpertSenderException("Ungültiger SMS-Typ");
		}
		this.type = type;
	}
	
	/**
	 * Returns the SMS-type.
	 * 
	 * @return SMS-type
	 */
	
	public String getSMSType(){
		return this.type;
	}
	
	
	/**
	 * Set the SMS sender. 
	 * 
	 * @param sender phone number in international format WITHOUT leading + or 00 (up to 16 digits) or a text (up to 11 characters)
	 * @throws SMSExpertSenderException
	 */
	
	public void setSender(String sender) throws SMSExpertSenderException{
		String regexNum = "[0-9]+";
		String regexNumOK = "[0-9]{6,16}";
		String regexStrOK = "[\\S]{1,11}";
		// Sender is Number
		if (sender.matches(regexNum)){
			if (!sender.matches(regexNumOK)){
				throw new SMSExpertSenderException("Der Absender Number");
			}
		}
		// Sender is String
		else{
			if (!sender.matches(regexStrOK)){
				throw new SMSExpertSenderException("Der Absender String");
			}
		}
		this.sender = sender;
	}
	
	/**
	 * Returns the SMS sender.
	 * 
	 * @return sender
	 */
	
	
	public String getSender(){
		return this.sender;
	}
	
	/**
	 * Set the SMS receiver. 
	 * 
	 * @param receiver phone number in international format WITHOUT leading + or 00 (up to 16 digits)
	 * @throws SMSExpertSenderException
	 */
	
	public void setReceiver(String receiver) throws SMSExpertSenderException{
		String regex = "[1-9]{1}[0-9]{5,15}";
		if (!receiver.matches(regex)){
			throw new SMSExpertSenderException("Empfänger ungültig");
		}
		this.receiver = receiver;
	}
	
	/**
	 * Returns the SMS receiver. 
	 * 
	 * @return receiver
	 */
	
	public String getReceiver(){
		return this.receiver;
	}
	
	
	/**
	 * Sets the SMS message. 
	 * 
	 * @param message up to 1530 signs
	 * @throws SMSExpertSenderException
	 */
	
	public void setMessage(String message) throws SMSExpertSenderException{
		if (message.length() < 1){
			throw new SMSExpertSenderException ("Die Nachricht ist zu kurz. Es wird mindestens ein Zeichen benötigt.");
		}
		if (message.length() > 1530){
			throw new SMSExpertSenderException ("Die Nachricht ist zu lang. Es sind maximal 1530 Zeichen zulässig.");
		}
		this.message = message;
	}
	
	/**
	 * Returns the SMS message.
	 * 
	 * @return message
	 */
	
	public String getMessage(){
		return this.message;
	}
	
	/**
	 * Sets the date and time for a time shift SMS. If you want send the SMS immediately don't call these method.
	 * 
	 * @param year int yyyy (4 digits)
	 * @param month int MM (2 digits)
	 * @param day int dd (2 digits)
	 * @param hour int HH (2 digits)
	 * @param minute int mm (2 digits)
	 * @throws SMSExpertSenderException
	 */
	
	public void setSendDateTime(int year,int month,int day, int hour, int minute) throws SMSExpertSenderException{
		try {
			Date dateNow = new Date();
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm" );
			sd.setLenient(false);
			Date dateSend = sd.parse(year + "-" + month + "-" +  day + " " + hour + ":" + minute);
			long dateDiff = dateSend.getTime() - dateNow.getTime();
			if (dateDiff < 120){
				throw new SMSExpertSenderException("Datum für den Termin-Versand befindet sich in der Vergangenheit oder innerhalb der nächsten 2 Minuten. " +
												   "Es ist kein zeitversetzter Versand mehr möglich.");
			}
			this.timestamp = dateSend.getTime()/1000;

		} catch (ParseException e) {
			System.out.println("Ungültiges Datum für den Termin-Versand eingegeben.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Sends the message. Checks if all necessary values are set. Calls {@link #sendGET()} or {@link #sendPOST()}
	 * 
	 * @throws SMSExpertSenderException
	 * 
	 */
	
	
	public void send() throws SMSExpertSenderException{
		if (type == null | receiver == null | message == null){
			throw new SMSExpertSenderException("Nicht alle notwendige Parameter gesetzt");
		}
		if (type.equals("expert") & sender == null){
			throw new SMSExpertSenderException("Parmaeter \"sender\" erforderlich für den SMS-Typ \"expert\"");
		}
		if (sendMode.equals("POST")){
			this.sendPOST();
		}
		if (sendMode.equals("GET")){
			this.sendGET();
		}
		
	}
	
	/**
	 * Sends the message via GET. Calls {@link #readResponse(String)} to handle the XML response.
	 * 
	 */
	
	private void sendGET(){
		String requestData = this.getRequestData();
		String urlStr = gateway_protocol + gateway_host + ":" + gateway_port + gateway_urlpath + "?" + requestData;
		if (debug){
			System.out.println("URL: " + urlStr);
		}
		// Create connection
		try {
			URL url = new URL(urlStr);
			URLConnection conn = url.openConnection ();
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String buffer;
			String response = "";
			while((buffer = rd.readLine()) != null) {
				response += buffer;
			}
			this.readResponse(response);
		} catch (MalformedURLException e) {
			System.out.println("Ungültige URL. Es wurde eine ungültige URL für das SMS-Gateways eingegeben.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("I/O Fehler. Antwort des SMS-Gateway kann nicht gelesen werden.");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Sends the message via POST. Calls {@link #readResponse(String)} to handle the XML response.
	 * 
	 */
	
	private void sendPOST(){
		String requestData = this.getRequestData();
		String urlStr = gateway_protocol + gateway_host + ":" + gateway_port + gateway_urlpath;
		if (debug){
			System.out.println("URL: " + urlStr);
		}
		// Create connection
		try {
			URL url = new URL(urlStr);
			URLConnection conn = url.openConnection();
			((HttpURLConnection)conn).setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestProperty("User-Agent", "SMSExpertSenderJava v" + version);
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Length", ""+ requestData.length());
			// Send request
			DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
			outStream.writeBytes(requestData);
			outStream.flush();
			outStream.close();
			// Get Response
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String buffer;
			String response = "";
			while((buffer = rd.readLine()) != null) {
				response += buffer;
			}
	    	// Close I/O streams
	    	rd.close();
	    	this.readResponse(response);
	    }
		catch (MalformedURLException e) {
			System.out.println("Ungültige URL. Es wurde eine ungültige URL für das SMS-Gateways eingegeben.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("I/O Fehler. Antwort des SMS-Gateway kann nicht gelesen werden.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns the request data.
	 * 
	 * @return String request data urlencoded
	 * 
	 */
	
	private String getRequestData(){
		String request = "";
		try {
			request = "user=" + URLEncoder.encode(user, "UTF-8");
			request += "&type=" + URLEncoder.encode(this.type,"UTF-8");
			if (this.sender != null){
				request += "&sender=" + URLEncoder.encode(this.sender,"UTF-8");
			}
			request += "&receiver=" + URLEncoder.encode(this.receiver.toString(),"UTF-8");
			request += "&message=" + URLEncoder.encode(this.message,"UTF-8");
			if (this.timestamp > 0){
				request += "&timestamp=" + URLEncoder.encode(""+ this.timestamp,"UTF-8");
			}
			request += "&hash=" + URLEncoder.encode(this.getHash(),"UTF-8");
		} 
		catch (UnsupportedEncodingException e) {
			System.out.println("Ungültige Zeichenkodierung für den URLEncoder angegeben.");
			e.printStackTrace();
		}
		if (debug){
			System.out.println("RequestData: " + request);
		}
		return request;
	}
	
	/**
	 * Returns the MD5 hash.
	 * 
	 * @return String MD5 hash
	 * 
	 */
	
	private String getHash(){
		String data = user + "|" + gatewaypassword + "|" + this.type + "|";
		if (this.sender != null) {
			data += this.sender;
		}
		data += "|" + this.receiver + "|" + this.message + "|";
		if (this.timestamp > 0){
			data += this.timestamp;
		}
		if (debug){
			System.out.println("Hash-Source: " + data );
		}
		String hash = null;
		try {
			byte[] databytes = data.getBytes("UTF-8");
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.reset();
		    md5.update(databytes);
		    byte messageDigest[] = md5.digest();
		    StringBuffer hexString = new StringBuffer();
		    for (int i=0;i<messageDigest.length;i++) {
		    	String hex = Integer.toHexString(0xFF & messageDigest[i]); 
		    	if(hex.length()==1){
		    		hexString.append('0');
		    	}
		    	hexString.append(hex);
		    }
		    hash = hexString.toString();
		}
		  catch (NoSuchAlgorithmException e) {
			  System.out.println("Ungültiger Algorithmus zur Hash-Wert-Berechnung angegeben.");
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			System.out.println("Ungültige Zeichenkodierung bei der Hash-Wert-Berechnung angegeben.");
			e.printStackTrace();
		}
		if (debug){
			System.out.println("Hash-MD5: " + hash);
		}
		return hash;
	}
	
	/**
	 * Reads the XML response of the SMS-Gateway. Requires JDOM.
	 * 
	 * 
	 */
	
	private void readResponse(String response){
		try {
			if (debug){
				System.out.println("Response: " + response);
			}
			SAXBuilder builder = new SAXBuilder();
			StringReader in = new StringReader(response);
			Document doc = builder.build(in);
			Element root =  doc.getRootElement(); 
			this.responseStatusCode = Integer.parseInt(root.getChildText("statusCode"));
			this.responseStatusText = root.getChildText("statusText");
			this.responseMessageId = root.getChildText("messageId");
			this.responseCost = Double.parseDouble(root.getChildText("cost"));
		} catch (JDOMException e) {
			System.out.println("JDOM Fehler:" + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("I/O Fehler: XML Antwort des SMS-Gateways kann nicht gelesen werden.");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Returns the status code of the XML response
	 * 
	 * @return Integer status code (200 = ok, 4xx = error)
	 */
	
	public Integer getResponseStatusCode(){
		return this.responseStatusCode;
	}
	
	/**
	 * Returns the status text of the XML response
	 * 
	 * @return String status text
	 */
	
	public String getResponseStatusText(){
		return this.responseStatusText;
	}
	
	/**
	 * Returns the message id of the XML response
	 * 
	 * @return String message id
	 */
	
	public String getResponseMessageId(){
		return this.responseMessageId;
	}
	
	/**
	 * Returns the cost of the XML response
	 * 
	 * @return Double cost
	 */
	
	public Double getResponseCost(){
		return this.responseCost;
	}

	public static void setUser(String user) {
		SMSExpertSender.user = user;
	}

	public static void setGatewaypassword(String gatewaypassword) {
		SMSExpertSender.gatewaypassword = gatewaypassword;
	}
}
