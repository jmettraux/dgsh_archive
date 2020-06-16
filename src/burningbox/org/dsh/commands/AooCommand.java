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
 * $Id: AooCommand.java,v 1.1 2002/07/22 05:46:52 jmettraux Exp $
 */

//
// AooCommand.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

package burningbox.org.dsh.commands;

import burningbox.org.dsh.Term;
import burningbox.org.dsh.entities.*;
import burningbox.org.dsh.GameSession;
import burningbox.org.dsh.CombatSession;


/**
 * Consumes an attack of opportunity for a creature or a character
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/07/22 05:46:52 $
 * <br>$Id: AooCommand.java,v 1.1 2002/07/22 05:46:52 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class AooCommand

    extends 
        UndoableAbstractCommand

    implements
        RegularCommand,
	CombatCommand

{

    static org.apache.log4j.Logger log =
        org.apache.log4j.Logger.getLogger("dgsh.AooCmd");

    //
    // FIELDS
    
    protected Being target = null;

    //
    // CONSTRUCTORS

    public AooCommand ()
    {
    }

    //
    // METHODS

    public boolean attack (int attackCount)
    {
	if (attackCount > 0)
	{
	    return this.target.performAttackOfOpportunity();
	}

	this.target.incOpportunitiesLeft(1);
	return true;
    }

    //
    // METHODS FROM UndoableAbstractCommand
    
    public boolean execute ()
    {
	if (noMoreTokens()) return false;
	String targetName = tokenizer.nextToken();

	this.target = GameSession.getInstance().findBeing(targetName);

	if (this.target == null)
	{
	    Term.echo("Cannot find '"+targetName+"'.\n");
	    return false;
	}

	boolean result = attack(1);

	if (result) 
	{
	    Term.echo
		("'"+targetName+"' performed its attack of opportunity.\n");
	    log.info("'"+targetName+"' performed aao.");
	    return true;
	}

	Term.echo
	    ("'"+targetName+"' has already performed all its attacks "+
	     "of opportunity.\n");

	return false;
    }

    public String getUndoInfo ()
    {
	return
	    this.target.getName()+" performed an attack of opportunity.";
    }

    public void undo ()
    {
	attack(-1);
	log.info("'"+this.target.getName()+"' undid aao.");
	Term.echo("undo successful.\n");
    }

    public String getSyntax ()
    {
	return
	    "aoo {targetName}";
    }

    public String getHelp ()
    {
	return
	    "This command removes 1 attack of opportunity to a character or "+
	    "a monster (usually they have 1 AoO per round, except if they "+
	    "have the 'Combat Reflexes' feat.";
    }

}
