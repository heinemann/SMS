package ms.heinemann.kontact.sms;

import java.awt.Color;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public  class GUISmsTextHandler implements DocumentListener {

	

	@Override
	public void insertUpdate(DocumentEvent e) {
		//Object sms ; 
		SMS.setSMSNachricht(GUI.editorPane.getText());
		//System.out.print(SMS.getSMSNachricht() + " i "+ GUI.editorPane.getText());
		GUI.lblZeichen.setText("Zeichen:" + SMS.getSMSNachricht().length());
		//GUI.setZeichen("Zeichen:" + SMS.getSMSNachricht().length());
		SMS.AnzahlSMSKalkulieren();
		GUI.lblAnzahlSms.setText("Anzahl SMS:" + SMS.getAnzahlSMS());
		if (SMS.getZeichenlängeSMSNachricht() >= SMS.getZeichenlängeSMSMax()){
			//GUI.editorPane.setText(SMS.getSMSNachricht().substring(0, SMS.getZeichenlängeSMSMax()));
			//GUI.editorPane.setBackground(Color.RED);
			JOptionPane.showMessageDialog(null, "Die SMS darf höchstens "+ SMS.getZeichenlängeSMSMax()+" Zeichen haben!", "Fehler", JOptionPane.ERROR_MESSAGE);
			
		}

	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		SMS.setSMSNachricht(GUI.editorPane.getText());
		//System.out.print(SMS.getSMSNachricht() + " r " + GUI.editorPane.getText());
		GUI.lblZeichen.setText("Zeichen:" + SMS.getSMSNachricht().length());
		SMS.AnzahlSMSKalkulieren();
		GUI.lblAnzahlSms.setText("Zeichen:" + SMS.getAnzahlSMS());
		//if (SMS.getZeichenlängeSMSNachricht() >= SMS.getZeichenlängeSMSMax()){
			//GUI.editorPane.setText(SMS.getSMSNachricht().substring(0, SMS.getZeichenlängeSMSMax()));
			//GUI.editorPane.setBackground(Color.RED);
		//}

	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		
	}

}
