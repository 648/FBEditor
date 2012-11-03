package de.FBEditor;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.text.JTextComponent;

import java.awt.event.*;

/**
 * Rightclick menu
 *
 */
public class CutAndPastePopup extends JComponent implements ClipboardOwner {

	public CutAndPastePopup(ActionListen action) {
		
		popupMenu = new JPopupMenu();
		popupMenu.setLabel("Edit");
		
		// hier noch nicht implementiert
		// r�ckg�ngig = new JMenuItem("R�ckg�ngig");
		ausschneiden = new JMenuItem("Ausschneiden");
		kopieren = new JMenuItem("Kopieren");
		einf�gen = new JMenuItem("Einf�gen");
		l�schen = new JMenuItem("L�schen");
		markall = new JMenuItem("Alles markieren");
		
		clipbd  = getToolkit().getSystemClipboard();
		// undoManager = fbedit.getUndoManager();

		ausschneiden.addActionListener(action);
		kopieren.addActionListener(action);
		einf�gen.addActionListener(action);
		l�schen.addActionListener(action);
		markall.addActionListener(action);
		
		// r�ckg�ngig.addActionListener(this);
		// r�ckg�ngig.setEnabled(false);
		// popupMenu.add(r�ckg�ngig);
		// popupMenu.addSeparator();
		
		popupMenu.add(ausschneiden);
		popupMenu.add(kopieren);
		popupMenu.add(einf�gen);
		popupMenu.add(l�schen);
		popupMenu.addSeparator();
		popupMenu.add(markall);
	}

	public void updateMenu(JTextComponent source) {
		this.source = source;
		if (source.getSelectedText() != null) {
			kopieren.setEnabled(true);
			ausschneiden.setEnabled(true);
			l�schen.setEnabled(true);
		} else {
			kopieren.setEnabled(false);
			ausschneiden.setEnabled(false);
			l�schen.setEnabled(false);
		}
		if (clipbd.getContents(this) != null)
			einf�gen.setEnabled(true);
		else
			einf�gen.setEnabled(false);
		// r�ckg�ngig.setEnabled(undoManager.canUndo());
	}

	public void lostOwnership(Clipboard c, Transferable t) {
		selection = null;
	}

	public void cut() {
		selection = source.getSelectedText();
		StringSelection clipString = new StringSelection(selection);
		clipbd.setContents(clipString, clipString);
		source.replaceSelection("");
		/* updateMenu(); */
	}

	public void copy() {
		selection = source.getSelectedText();
		StringSelection clipString = new StringSelection(selection);
		clipbd.setContents(clipString, clipString);
		/* updateMenu(); */
	}

	public void markall() {
		source.setSelectionStart(0);
		source.setSelectionEnd(source.getDocument().getLength());
		/* updateMenu(); */
	}

	public void paste() {
		Transferable clipData = clipbd.getContents(this);
		try {
			String clipString = (String) clipData.getTransferData(DataFlavor.stringFlavor);
			source.replaceSelection(clipString);
			/* updateMenu(); */
		} catch (Exception ex) {
			System.out.println("not String flavor");
		}
	}

	public void delete() {
		selection = source.getSelectedText();
		source.replaceSelection("");
		/* updateMenu(); */
	}

	public JPopupMenu getPopupMenu() {
		return popupMenu;
	}

	private static final long serialVersionUID = 1L;
	private static JPopupMenu popupMenu;
	private Clipboard clipbd;
	private String selection;
	JMenuItem r�ckg�ngig;
	JMenuItem ausschneiden;
	JMenuItem kopieren;
	JMenuItem einf�gen;
	JMenuItem l�schen;
	JMenuItem markall;
	JTextComponent source;
	// CompoundUndoManager undoManager;
}
