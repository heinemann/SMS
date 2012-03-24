package ms.heinemann.kontact.sms;
/**
 * SMS-Gatewayclient, based on the 
 * Demo of the {@link SMSExpertSender} class.
 * 
 * <p>Copyright 2012 Adrian Heinemann </p>
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
 * @author Adrian Heinemann; Bastian Treger
 * @version 0.1
 *
 */
public class SMS {
	
	private static String SMSType = null;
	private static  String Sender = ""; 		
	private static  String Reciever =""; 		
	private static  String SMSNachricht = null;
	private static int ZeichenlängeSMS = 160;
	private static int ZeichenlängeVerknüpfteSMS = 153;
	private static int ZeichenlängeSMSMax = 1530;
	private static int ZeichenlängeSMSNachricht = 0; 
	private static int AnzahlSMS = 1;
	
	
	/** 
     * Main demo of the {@link SMSExpertSender} class
     *
     * @param args command line parameter (not used)
     */
	
	public static void main(String[] args) {
		SMS.initialisiereProgramm();
		SMS.standart();
		GUI.GUI();
		if (args.length == 2){
		String parameterN = args[0]; //Nummer
		String parameterF = args[1]; //Textnachricht
		System.out.println(parameterN + parameterF);
		GUI.editorPane.setText(parameterF);
		GUI.textPaneEmpfänger.setText(parameterN);
		SMS.setSMSNachricht(parameterF);
		System.out.println(SMSNachricht + Reciever);
		}else{System.out.println("Keine Argumente übergeben.");}
		
		
		
	}
	/**
	 * Laden der Konfigurationsdatei bei Programmstart. 
	 */
	public static void initialisiereProgramm(){
		Configuration config = new Configuration();
		SMSExpertSender smsSender = new SMSExpertSender();
		smsSender.setUser(config.getBenutzer());
		smsSender.setGatewaypassword(config.getPasswort());
	}
	/**
	 * Diese Methode versendet die SMS.
	 */
	// TODO Statusmitteilungen über GUI ausgeben.
	// TODO Parameter mit Methodenaufruf übergeben. 
	public static void senden(){
		try {
			SMSExpertSender sender = new SMSExpertSender();
			sender.setSMSType(getSMSType());
			if (Sender != null){sender.setSender(Sender);}
			//sender.setSender("491735559999");
			sender.setReceiver(Reciever);
			sender.setMessage(SMSNachricht);
			//sender.setSendDateTime(2010, 12, 31, 23, 12);
			sender.send();
			System.out.println("StatusCode: " + sender.getResponseStatusCode());
			System.out.println("StatusText: " + sender.getResponseStatusText());
			System.out.println("MessageID: " + sender.getResponseMessageId());
			System.out.println("Cost: " + sender.getResponseCost()); 
		}catch (SMSExpertSenderException e) {
			System.out.println("FEHLER: " + e.getMessage());
		}
	}
	
	public static void standart(){
		SMS.setSMSType(("standard"));
		SMS.Sender = null; 
	}
	public static void premium(){
		SMS.setSMSType(("expert"));
		SMS.setSender("Absender");
	}
	public static void AnzahlSMSKalkulieren(){
		SMS.setZeichenlängeSMSNachricht();
		if(SMS.getZeichenlängeSMSNachricht() <= SMS.getZeichenlängeSMS()) 
			{setAnzahlSMS(1);}
			else if(SMS.getZeichenlängeSMSNachricht() >= SMS.getZeichenlängeSMS()) 
			{setAnzahlSMS((SMS.getZeichenlängeSMSNachricht() / SMS.getZeichenlängeVerknüpfteSMS()));
			 if ((SMS.getZeichenlängeSMSNachricht()% SMS.getZeichenlängeVerknüpfteSMS())>0 ){
				 setAnzahlSMS(getAnzahlSMS() + 1);}
			}
			
	}
	
	/**
	 * Getter und Setter Methoden folgen
	 * @return
	 */
	public static String getSender() {
		return Sender;
	}
	public static void setSender(String sender) {
		Sender = sender;
	}
	public static String getReciever() {
		return Reciever;
	}
	public static void setReciever(String reciever) {
		Reciever = reciever;
	}
	public static String getSMSNachricht() {
		return SMSNachricht;
	}
	public static void setSMSNachricht(String sMSNachricht) {
		SMSNachricht = sMSNachricht;
	}
	public static int getAnzahlSMS() {
		return AnzahlSMS;
	}
	public static void setAnzahlSMS(int anzahlSMS) {
		AnzahlSMS = anzahlSMS;
	}
	public static int getZeichenlängeSMS() {
		return ZeichenlängeSMS;
	}
	public static void setZeichenlängeSMS(int zeichenlängeSMS) {
		ZeichenlängeSMS = zeichenlängeSMS;
	}
	public static int getZeichenlängeVerknüpfteSMS() {
		return ZeichenlängeVerknüpfteSMS;
	}
	public static void setZeichenlängeVerknüpfteSMS(
			int zeichenlängeVerknüpfteSMS) {
		ZeichenlängeVerknüpfteSMS = zeichenlängeVerknüpfteSMS;
	}
	public static int getZeichenlängeSMSMax() {
		return ZeichenlängeSMSMax;
	}
	public static void setZeichenlängeSMSMax(int zeichenlängeSMSMax) {
		ZeichenlängeSMSMax = zeichenlängeSMSMax;
	}
	public static int getZeichenlängeSMSNachricht() {
		return ZeichenlängeSMSNachricht;
	}
	public static void setZeichenlängeSMSNachricht() {
		ZeichenlängeSMSNachricht = SMS.getSMSNachricht().length();
	}
	public static String getSMSType() {
		return SMSType;
	}
	public static void setSMSType(String sMSType) {
		SMSType = sMSType;
	}
	
}
