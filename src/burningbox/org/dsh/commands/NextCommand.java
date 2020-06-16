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
 * $Id: NextCommand.java,v 1.15 2002/08/08 07:51:10 jmettraux Exp $
 */

//
// NextCommand.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// You will be run over by a beer truck.
//
// (5d6 damage)
//

package burningbox.org.dsh.commands;

import burningbox.org.dsh.Term;
import burningbox.org.dsh.Pager;
import burningbox.org.dsh.entities.*;
import burningbox.org.dsh.GameSession;
import burningbox.org.dsh.DshException;
import burningbox.org.dsh.CombatSession;
import burningbox.org.dsh.views.InitiativeView;


/**
 * Adds or remove hit points to a monster or a character.
 * min is then -10 (death) and max is the max hit points.
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/08/08 07:51:10 $
 * <br>$Id: NextCommand.java,v 1.15 2002/08/08 07:51:10 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class NextCommand

    extends 
        UndoableAbstractCommand

    implements
	CombatCommand

{

    static org.apache.log4j.Logger log =
        org.apache.log4j.Logger.getLogger("dgsh.NextCmd");

    //
    // FIELDS

    WithInitiative withInitiative = null;
    
    //
    // CONSTRUCTORS

    public NextCommand ()
    {
    }

    //
    // METHODS

    private boolean next (int increment)
    {
	this.withInitiative = null;

	if (increment >= 0)
	    this.withInitiative = CombatSession.getInstance().getInitiativeTable().next();
	else
	    this.withInitiative = CombatSession.getInstance().getInitiativeTable().previous();

	//
	// work on effect
	
	if (this.withInitiative instanceof Effect)
	{
	    Effect effect = (Effect)this.withInitiative;

	    if (effect.getRoundsToGo() < 1)
	    {
		/*
		 * seems never to appear
		 */
		Term.echo("\nEffect ends !");
	    }
	}
	else if (this.withInitiative instanceof Being)
	{
	    Being b = (Being)this.withInitiative;

	    b.purgeOldEffects();
	}
	
	//
	// display effect or being
	
	InitiativeView iv = new InitiativeView(this.withInitiative);

	log.info("next is "+this.withInitiative.getName());

	//Term.echo(iv);
	Pager pager = new Pager(iv);
	pager.use();

	//
	// use the trigger that applies each round (if any)
	// and display its effect
	
	if (this.withInitiative instanceof Being)
	{
	    // treat effects to apply each round

	    try
	    {
		Being b = (Being)this.withInitiative;

		Trigger t = b.fetchTriggerToApplyEachRound();

		if (t != null) t.trigger();
	    }
	    catch (DshException de)
	    {
		Term.echo
		    ("Failed to use the 'trigger to apply each round' : \n"+
		     de.getMessage());
	    }
	}

	return true;
    }

    //
    // METHODS FROM UndoableAbstractCommand
    
    public boolean execute ()
    {
	if ( ! CombatSession.getInstance().getInitiativeTable().isReady())
	{
	    Term.echo
		("Initiative should get rolled for first. Try to 'init'. \n");
	    return false;
	}

	return next(1);
    }

    public String getUndoInfo ()
    {
	return 
	    "Initiative passed to '"+this.withInitiative.getName()+"'";
    }

    public void undo ()
    {
	next(-1);
    }

    public String getSyntax ()
    {
	return
	    "next";
    }

    public String getHelp ()
    {
	return
	    "type 'next' to know which is next to play in the round, "+
	    "(or what effect will end)";
    }

}
