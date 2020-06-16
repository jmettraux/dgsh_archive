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
 * $Id: HelpCommand.java,v 1.8 2002/07/29 06:51:10 jmettraux Exp $
 */

//
// HelpCommand.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// Many a writer seems to think he is never profound except when he can't
// understand his own meaning.
// 		-- George D. Prentice
// 

package burningbox.org.dsh.commands;

import burningbox.org.dsh.Term;
import burningbox.org.dsh.Shell;
import burningbox.org.dsh.Pager;


/**
 * Displays the version of the Dungeon shell
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/07/29 06:51:10 $
 * <br>$Id: HelpCommand.java,v 1.8 2002/07/29 06:51:10 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class HelpCommand

    extends
        AbstractCommand

    implements
        RegularCommand,
        CombatCommand

{

    static org.apache.log4j.Logger log =
        org.apache.log4j.Logger.getLogger("dgsh.HelpCommand");

    //
    // FIELDS

    //
    // CONSTRUCTORS

    public HelpCommand ()
    {
    }

    //
    // METHODS

    private void displayHelpFile ()
    {
	try
	{
	    Pager pager = new Pager("doc/help.txt", false);
	    pager.use();
	}
	catch (java.io.IOException ie)
	{
	    Term.echo("Failed to open doc/help.txt : \n"+ie);
	}
    }

    //
    // METHODS FROM AbstractCommand
    
    public boolean execute ()
    {
	try
	{
	    if ( ! this.tokenizer.hasMoreTokens())
	    {
		displayHelpFile();
		return true;
	    }

	    AbstractCommand command = 
		Shell.instantiateCommand(this.tokenizer.nextToken());
	    Term.echo("\nTYPE\n");
	    if (command instanceof RegularCommand)
		Term.echo("    this command can be used in normal mode\n");
	    if (command instanceof CombatCommand)
		Term.echo("    this command can be used in combat mode\n");
	    Term.echo("SYNTAX\n");
	    Term.echo("    "+command.getSyntax());
	    Term.echo("\nHELP\n");
	    Term.echo("    "+command.getHelp());

	    Term.echon();
	    Term.echon();
	}
	catch (Exception e)
	{
	    Term.echo("Cannot display help for unknown command. \n");
	    return false;
	}
	return true;
    }

    public String getSyntax ()
    {
	return "help {command}";
    }

    public String getHelp ()
    {
	return "Displays Help for a particular command";
    }

}
