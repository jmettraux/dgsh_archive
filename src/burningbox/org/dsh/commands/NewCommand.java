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
 * $Id: NewCommand.java,v 1.5 2002/07/22 05:46:52 jmettraux Exp $
 */

//
// NewCommand.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// Well, anyway, I was reading this James Bond book, and right away I realized
// that like most books, it had too many words.  The plot was the same one that
// all James Bond books have: An evil person tries to blow up the world, but
// James Bond kills him and his henchmen and makes love to several attractive
// women.  There, that's it: 24 words.  But the guy who wrote the book took
// *thousands* of words to say it.
// 	Or consider "The Brothers Karamazov", by the famous Russian alcoholic
// Fyodor Dostoyevsky.  It's about these two brothers who kill their father.
// Or maybe only one of them kills the father.  It's impossible to tell because
// what they mostly do is talk for nearly a thousand pages.  If all Russians 
// talk as much as the Karamazovs did, I don't see how they found time to become
// a major world power.
// 	I'm told that Dostoyevsky wrote "The Brothers Karamazov" to raise
// the question of whether there is a God.  So why didn't he just come right
// out and say: "Is there a God? It sure beats the heck out of me."
// 	Other famous works could easily have been summarized in a few words:
// 
// * "Moby Dick" -- Don't mess around with large whales because they symbolize
//   nature and will kill you.
// * "A Tale of Two Cities" -- French people are crazy.
// 		-- Dave Barry
// 

package burningbox.org.dsh.commands;

import burningbox.org.dsh.Term;
import burningbox.org.dsh.Shell;


/**
 * A starting point for all 'new stuff'...
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/07/22 05:46:52 $
 * <br>$Id: NewCommand.java,v 1.5 2002/07/22 05:46:52 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class NewCommand

    extends
        UndoableAbstractCommand

    implements
        RegularCommand,
        CombatCommand

{

    static org.apache.log4j.Logger log =
        org.apache.log4j.Logger.getLogger(NewCommand.class.getName());

    //
    // FIELDS

    protected UndoableAbstractCommand subNew = null;

    //
    // CONSTRUCTORS

    public NewCommand ()
    {
    }

    //
    // METHODS

    //
    // METHODS FROM UndoableAbstractCommand
    
    public boolean execute ()
    {
	if (noMoreTokens()) return false;
	String target = tokenizer.nextToken();

	try
	{
	    String newCommandName = 
		"New"+
		target.substring(0, 1).toUpperCase()+
		target.substring(1)+
		"Command";

	    this.subNew = (UndoableAbstractCommand)Shell
		.instantiateCommand(newCommandName);

	    return this.subNew.execute();
	}
	catch (Exception e)
	{
	}

	Term.echo("no 'new "+target+"' command found.");
	return false;
    }

    public String getUndoInfo ()
    {
	return this.subNew.getUndoInfo();
    }

    public void undo ()
    {
	if (this.subNew != null)
	    this.subNew.undo();
    }

    public String getSyntax ()
    {
	return "new {character|monster|template}";
    }

    public String getHelp ()
    {
	return "A starting point for all the 'new stuff' commands";
    }

}
