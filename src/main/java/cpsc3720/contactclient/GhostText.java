/*
 * This class was copied directly from the following online source:
 * http://stackoverflow.com/questions/10506789/how-to-display-faint-gray-ghost-text-in-a-jtextfield
 * By User: Guillaume Polet
 * 
 * We claim no credit for this code, and it adds NO functionality to our program.
 * It is used to display the greyed-out text hints in the text fields before a Player types anything
 */
package cpsc3720.contactclient;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
/**
 * This class renders the text in the text boxes on the login screen.
 * 
 * @author Keven "Beans" Hayes
 */
public class GhostText implements FocusListener, DocumentListener, PropertyChangeListener
{
    private final JTextComponent textComp;
    private boolean isEmpty;
    private Color ghostColor;
    private Color foregroundColor;
    private final String ghostText;

    public GhostText(final JTextComponent textComp, String ghostText)
    {
        super();
        this.textComp = textComp;
        this.ghostText = ghostText;
        this.ghostColor = Color.LIGHT_GRAY;
        textComp.addFocusListener(this);
        registerListeners();
        updateState();
        if (!this.textComp.hasFocus())
        {
            focusLost(null);
        }
    }

    public void delete()
    {
        unregisterListeners();
        textComp.removeFocusListener(this);
    }

    private void registerListeners()
    {
        textComp.getDocument().addDocumentListener(this);
        textComp.addPropertyChangeListener("foreground", this);
    }

    private void unregisterListeners()
    {
        textComp.getDocument().removeDocumentListener(this);
        textComp.removePropertyChangeListener("foreground", this);
    }

    public Color getGhostColor()
    {
        return ghostColor;
    }

    public void setGhostColor(Color ghostColor)
    {
        this.ghostColor = ghostColor;
    }

    private void updateState()
    {
        isEmpty = textComp.getText().length() == 0;
        foregroundColor = textComp.getForeground();
    }

    @Override
    public void focusGained(FocusEvent e)
    {
        if (isEmpty)
        {
            unregisterListeners();
            try
            {
                textComp.setText("");
                textComp.setForeground(foregroundColor);
            }
            finally
            {
                registerListeners();
            }
        }

    }

    @Override
    public void focusLost(FocusEvent e)
    {
        if (isEmpty)
        {
            unregisterListeners();
            try
            {
                textComp.setText(ghostText);
                textComp.setForeground(ghostColor);
            }
            finally
            {
                registerListeners();
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        updateState();
    }

    @Override
    public void changedUpdate(DocumentEvent e)
    {
        updateState();
    }

    @Override
    public void insertUpdate(DocumentEvent e)
    {
        updateState();
    }

    @Override
    public void removeUpdate(DocumentEvent e)
    {
        updateState();
    }

}