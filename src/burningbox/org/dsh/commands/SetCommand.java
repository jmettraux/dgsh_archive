/*
 * Redistribution and use of this software and associated documentation
 * ("Software"), with or without modification, are permitted provided
 * that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain copyright
 *    statements and notices.  Redistributions must also contain a
 *    copy of this document.
 * 
 * 2. Redistributions in binary form must reproduce the
 *    above copyright notice, this list of conditions and the
 *    following disclaimer in the documentation and/or other
 *    materials provided with the distribution.
 * 
 * 3. The name "dgsh" or "dsh" must not be used to endorse or promote
 *    products derived from this Software without prior written
 *    permission of BurningBox.  For written permission,
 *    please contact john.mettraux@burningbox.org.
 * 
 * 4. Products derived from this Software may not be called "dgsh" or "dsh"
 *    nor may "dgsh" or "dsh" appear in their names without prior written
 *    permission of BurningBox.
 * 
 * 5. Due credit should be given to BurningBox
 *    (http://www.burningbox.com/).
 * 
 * THIS SOFTWARE IS PROVIDED BY BURNINGBOX AND CONTRIBUTORS
 * ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL
 * BURNINGBOX OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * Copyright 2002 (C) BurningBox. All Rights Reserved.
 * 
 * $Id: SetCommand.java,v 1.2 2002/07/31 11:58:16 jmettraux Exp $
 */

//
// SetCommand.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

package burningbox.org.dsh.commands;

import burningbox.org.dsh.Term;
import burningbox.org.dsh.Definitions;
import burningbox.org.dsh.GameSession;
import burningbox.org.dsh.CombatSession;
import burningbox.org.dsh.views.Table;
import burningbox.org.dsh.entities.*;


/**
 * Adds or remove hit points to a monster or a character.
 * min is then -10 (death) and max is the max hit points.
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/07/31 11:58:16 $
 * <br>$Id: SetCommand.java,v 1.2 2002/07/31 11:58:16 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class SetCommand

    extends 
        UndoableAbstractCommand

    implements
        RegularCommand,
	CombatCommand

{

    static org.apache.log4j.Logger log =
        org.apache.log4j.Logger.getLogger("dgsh.SetCmd");

    //
    // FIELDS
    
    protected String envAttributeName = null;
    protected String oldValue = null;
    protected String newValue = null;

    //
    // CONSTRUCTORS

    public SetCommand ()
    {
    }

    //
    // METHODS

    private void listAttributes ()
    {
	String[] sizes = new String[] { "l30", "l40" };
	String[] titles = new String[] { "attribute", "value" };
	Table table = new Table(sizes, titles);

	java.util.Iterator it = GameSession.getInstance()
	    .getEnvAttributes().keySet().iterator();

	while (it.hasNext())
	{
	    String attributeName = (String)it.next();

	    String value = GameSession.getInstance()
		.getEnvAttributeValue(attributeName);

	    table.addLine(new String[] { attributeName, value });
	}

	Term.echo(table.render());
    }

    //
    // METHODS FROM UndoableAbstractCommand
    
    public boolean execute ()
    {
	if ( ! this.tokenizer.hasMoreTokens())
	{
	    listAttributes();
	    return false;
	}

	this.envAttributeName = this.tokenizer.nextToken();

	this.oldValue = GameSession.getInstance()
	    .getEnvAttributeValue(this.envAttributeName);

	StringBuffer sb = new StringBuffer();
	while (this.tokenizer.hasMoreTokens())
	{
	    sb.append(this.tokenizer.nextToken());

	    if (this.tokenizer.hasMoreTokens())
		sb.append(' ');
	}
	if (sb.length() > 1)
	    this.newValue = sb.toString();

	if (this.newValue != null)
	{
	    GameSession.getInstance().getEnvAttributes()
		.put(this.envAttributeName, this.newValue);

	    Term.echo
		("Set '"+this.envAttributeName+"' from '"+this.oldValue+
		 "' to '"+this.newValue+"' \n");
	}
	else
	{
	    GameSession.getInstance().getEnvAttributes()
		.remove(this.envAttributeName);

	    Term.echo
		("Removed attribute '"+this.envAttributeName+"' \n");
	}

	return true;
    }

    public String getUndoInfo ()
    {
	return
	    "Set '"+this.envAttributeName+"' from '"+this.oldValue+
	    "' to '"+this.newValue+"'";
    }

    public void undo ()
    {
	GameSession.getInstance().getEnvAttributes()
	    .put(this.envAttributeName, this.oldValue);
	
	Term.echo
	    ("reverted '"+this.envAttributeName+"' to '"+this.oldValue+"'\n");
    }

    public String getSyntax ()
    {
	return
	    "set [{attributeName} [newValue]]";
    }

    public String getHelp ()
    {
	return
	    "Sets the value for an environment attribute. "+
	    "Using 'set' without parameters lists the current attributes and "+
	    "their values. If you give no newValue, the attribute will be "+
	    "removed from the environment.";
    }

}
