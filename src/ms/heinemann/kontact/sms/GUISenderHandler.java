package ms.heinemann.kontact.sms;

import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class GUISenderHandler implements DocumentListener {

	@Override
	public void changedUpdate(DocumentEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void insertUpdate(DocumentEvent arg0) {
		// TODO Auto-generated method stub
		SMS.setSender(GUI.textPaneAbsender.getText());
		System.out.println(SMS.getSender());
		if (SMS.getSender().length() == 11 || SMS.getSender().length() >= 16 ){
			//GUI.editorPane.setText(SMS.getSMSNachricht().substring(0, SMS.getZeichenlängeSMSMax()));
			//GUI.editorPane.setBackground(Color.RED);
			JOptionPane.showMessageDialog(null, "Der Absender Name darf höchstens 11 Zeichen lang sein. /n Für Nummer ist das Limit 16 Ziffern, ohne + oder vorstestellter 00.", "Warnung", JOptionPane.ERROR_MESSAGE);
			
		}

	}

	@Override
	public void removeUpdate(DocumentEvent arg0) {
		// TODO Auto-generated method stub
		SMS.setSender(GUI.textPaneAbsender.getText());
		System.out.println(SMS.getSender());
		if (SMS.getSender().length() >= 16){
			//GUI.editorPane.setText(SMS.getSMSNachricht().substring(0, SMS.getZeichenlängeSMSMax()));
			//GUI.editorPane.setBackground(Color.RED);
			JOptionPane.showMessageDialog(null, "Der Absender Name darf höchstens 16 Zeichen lang sein.", "Fehler", JOptionPane.ERROR_MESSAGE);
			
		}

	}

}
