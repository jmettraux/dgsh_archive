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
 * $Id: ClockCommand.java,v 1.2 2002/08/12 08:22:44 jmettraux Exp $
 */

//
// ClockCommand.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

package burningbox.org.dsh.commands;

import burningbox.org.dsh.Term;
import burningbox.org.dsh.entities.*;
import burningbox.org.dsh.Definitions;
import burningbox.org.dsh.GameSession;
import burningbox.org.dsh.CombatSession;


/**
 * Displays the clock, add time to it or sets its time
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/08/12 08:22:44 $
 * <br>$Id: ClockCommand.java,v 1.2 2002/08/12 08:22:44 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class ClockCommand

    extends 
        UndoableAbstractCommand

    implements
        RegularCommand

{

    static org.apache.log4j.Logger log =
        org.apache.log4j.Logger.getLogger("dgsh.ClockCmd");

    //
    // FIELDS

    protected long oldTime = -1;
    
    //
    // CONSTRUCTORS

    public ClockCommand ()
    {
    }

    //
    // METHODS

    //
    // METHODS FROM UndoableAbstractCommand
    
    public boolean execute ()
    {
	GameClock clock = GameSession.getInstance().getClock();

	if ( ! this.tokenizer.hasMoreTokens())
	{
	    Term.echo(clock.display(false)+'\n');
	    return false;
	}

	String command = this.tokenizer.nextToken();

	if (noMoreTokens()) return false;

	String timeString = this.tokenizer.nextToken();

	this.oldTime = clock.getElapsedMillis();

	if (command.equals("add"))
	{
	    clock.add(timeString);
	    return true;
	}

	if (command.equals("set"))
	{
	    clock.set(timeString);
	    return true;
	}

	Term.echo(getSyntax()+"\n");
	return false;
    }

    public void undo ()
    {
	GameSession.getInstance().getClock().set(this.oldTime);
	Term.echo(GameSession.getInstance().getClock().display(false)+'\n');
    }

    public String getUndoInfo ()
    {
	return
	    "Game clock set to "+GameClock.display(this.oldTime, false);
    }

    public String getSyntax ()
    {
	return
	    "clock [add {timeString} | set {timeString}]";
    }

    public String getHelp ()
    {
	return
	    "With no arguments, this command displays the current game time. If you specify 'add', the {timeString} is interpreted and added to the current game time. A timeString example : '1d2h3m4r5s' which is read as '1 day, 2 hours, 3 minutes, 4 rounds and 5 seconds'";
    }

}
