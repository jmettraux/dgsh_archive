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
 * $Id: TodefCommand.java,v 1.1 2002/08/08 07:51:10 jmettraux Exp $
 */

//
// TodefCommand.java
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
 * Adds or remove hit points to a monster or a character.
 * min is then -10 (death) and max is the max hit points.
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/08/08 07:51:10 $
 * <br>$Id: TodefCommand.java,v 1.1 2002/08/08 07:51:10 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class TodefCommand

    extends 
        UndoableAbstractCommand

    implements
	CombatCommand

{

    static org.apache.log4j.Logger log =
        org.apache.log4j.Logger.getLogger("dgsh.TodefCmd");

    //
    // FIELDS
    
    protected Being defender = null;
    protected Effect defense = null;

    //
    // CONSTRUCTORS

    public TodefCommand ()
    {
    }

    //
    // METHODS

    //
    // METHODS FROM UndoableAbstractCommand
    
    public boolean execute ()
    {
	// 
	// determine charger
	
	if (this.tokenizer.hasMoreTokens())
	{
	    String defenderName = this.tokenizer.nextToken();
	    this.defender = GameSession.getInstance().findBeing(defenderName);

	    if (this.defender == null || Being.isATemplate(this.defender))
	    {
		Term.echo("Defender '"+defenderName+"' not found.\n");
		return false;
	    }
	}
	else
	{
	    this.defender = CombatSession.getInstance().getCurrentBeing();
	}

	//
	// build effect
	
	this.defense = new Effect
	    (this.defender.getName()+" on total defense",
	     "",
	     this.defender.getCurrentInitiative(),
	     this.defender.computeAbilityScore(Definitions.DEXTERITY),
	     1,
	     this.defender.getName());

	this.defense.addModifier
	    (new MiscModifier(Definitions.ARMOR_CLASS, 4));

	//
	// add effect
	
	Effect.addEffect(this.defense);

	Term.echo(this.defender.getName()+" on total defense\n");
	log.info(this.defender.getName()+" on total defense");
	
	return true;
    }

    public String getUndoInfo ()
    {
	return
	    "'"+this.defender.getName()+"' on total defense";
    }

    public void undo ()
    {
	Effect.removeEffect(this.defense);

	Term.echo
	    (this.defender.getName()+"'s total defense has been cancelled.\n");
	log.info
	    (this.defender.getName()+"'s total defense has been cancelled");
    }

    public String getSyntax ()
    {
	return
	    "todef [creatureName]";
    }

    public String getHelp ()
    {
	return
	    "This command can only get used during a combat. "+
	    "If the 'creatureName' is not specified, the creature "+
	    "whose turn it is will be considered as the defender.\n"+
	    "This method puts the object in 'total defense' mode";
    }

}
