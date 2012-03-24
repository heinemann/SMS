package ms.heinemann.kontact.sms;

import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class GUIEmpfängerHandler implements DocumentListener {

	@Override
	public void changedUpdate(DocumentEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void insertUpdate(DocumentEvent arg0) {
		// TODO Auto-generated method stub
		SMS.setReciever(GUI.textPaneEmpfänger.getText());
		System.out.println(SMS.getReciever());
		if (SMS.getReciever().length() >= 16 ){
			//GUI.editorPane.setText(SMS.getSMSNachricht().substring(0, SMS.getZeichenlängeSMSMax()));
			//GUI.editorPane.setBackground(Color.RED);
			JOptionPane.showMessageDialog(null, "Für Nummern dürfen maximal 16 Ziffern haben, ohne + oder vorstestellter 00.", "Warnung", JOptionPane.ERROR_MESSAGE);
			
		}

	}

	@Override
	public void removeUpdate(DocumentEvent arg0) {
		// TODO Auto-generated method stub
		SMS.setReciever(GUI.textPaneEmpfänger.getText());
		System.out.println(SMS.getReciever());
		if (SMS.getReciever().length() >= 16 ){
			//GUI.editorPane.setText(SMS.getSMSNachricht().substring(0, SMS.getZeichenlängeSMSMax()));
			//GUI.editorPane.setBackground(Color.RED);
			JOptionPane.showMessageDialog(null, "Für Nummern dürfen maximal 16 Ziffern haben, ohne + oder vorstestellter 00.", "Warnung", JOptionPane.ERROR_MESSAGE);
		}

	}

}
