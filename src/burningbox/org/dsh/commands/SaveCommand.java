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
 * $Id: SaveCommand.java,v 1.8 2002/12/03 08:05:58 jmettraux Exp $
 */

//
// SaveCommand.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// Q:	Why is Christmas just like a day at the office?
// A:	You do all of the work and the fat guy in the suit
// 	gets all the credit.
// 

package burningbox.org.dsh.commands;

import burningbox.org.dsh.Term;
import burningbox.org.dsh.Shell;
import burningbox.org.dsh.Utils;
import burningbox.org.dsh.GameSession;
import burningbox.org.dsh.entities.*;


/**
 * Loads an xml file into the session.
 * The behaviour of this command differs, depending on what can be found in
 * the file to load
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/12/03 08:05:58 $
 * <br>$Id: SaveCommand.java,v 1.8 2002/12/03 08:05:58 jmettraux Exp $</font>
 *
 * @author jmettraux@burningbox.org
 */
public class SaveCommand

    extends
        AbstractCommand

    implements
        RegularCommand,
        CombatCommand

{

    static org.apache.log4j.Logger log =
        org.apache.log4j.Logger.getLogger(SaveCommand.class.getName());

    //
    // FIELDS

    //
    // CONSTRUCTORS

    public SaveCommand ()
    {
    }

    //
    // METHODS

    //
    // METHODS FROM AbstractCommand
    
    public boolean execute ()
    {

	Being creature = null;
	BeanSet set = null;

	if (noMoreTokens()) return false;
	String setName = this.tokenizer.nextToken();

	if (noMoreTokens()) return false;
	String fileName = this.tokenizer.nextToken();

	creature = GameSession.getInstance().findBeing(setName);

	if (creature == null)
	{
	    set = GameSession.getInstance().findSet(setName);

	    if (set == null)
	    {
		Term.echo
		    ("There is no set or creature named '"+setName+"'. \n");
		return false;
	    }
	}

	try
	{
	    if (set == null)
	    {
		Utils.xmlSave(creature, fileName);
		Term.echo(setName+" saved to '"+fileName+"' \n");
		return true;
	    }

	    set.saveToDir(fileName);
	    Term.echo(setName+" saved to dir '"+fileName+"'. \n");
	    return true;
	}
	catch (java.io.IOException ie)
	{
	    Term.echo("Couldn't save set or creature to "+fileName+"\n");
	    Term.echo(ie.toString());
	    return false;
	}
    }

    public String getSyntax ()
    {
	return 
	    "save {{{t[emp]|p[arty]|o[pponents]} dirname} | "+
	    "{creatureName fileName}}";
    }

    public String getHelp ()
    {
	return 
	    "Saves one or a set of objects into a file.";
    }

}
