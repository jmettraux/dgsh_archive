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
 * $Id: AbCommand.java,v 1.5 2002/07/22 05:46:52 jmettraux Exp $
 */

//
// AbCommand.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// Your ignorance cramps my conversation.
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
 * <br>$Date: 2002/07/22 05:46:52 $
 * <br>$Id: AbCommand.java,v 1.5 2002/07/22 05:46:52 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class AbCommand

    extends 
        UndoableAbstractCommand

    implements
        RegularCommand,
	CombatCommand

{

    //static org.apache.log4j.Logger log =
    //    org.apache.log4j.Logger.getLogger(AbCommand.class.getName());

    //
    // FIELDS
    
    protected String targetName = null;
    protected String abilityDefinition = null;
    protected int delta = 0;

    //
    // CONSTRUCTORS

    public AbCommand ()
    {
    }

    //
    // METHODS

    private static boolean incrementHitPoints 
	(String targetName, int hpCount)
    {

	//
	// first search among the party
	
	Being target = null;
	int newCount = 1;

	try
	{
	    target = (Being)GameSession.getInstance().getParty()
		.get(targetName);
	}
	catch (ClassCastException cce)
	{
	    Term.echo("'"+targetName+"' is not an object with abilities\n");
	    return false;
	}

	//
	// then among the opponents
	
	if (target == null &&
            CombatSession.isCombatGoingOn())
	{
	    target = (Being)CombatSession.getInstance().getOpponents()
		.get(targetName);
	}

	if (target == null)
	    return false;

	//
	// do the trick
	
	return true;

    }

    //
    // METHODS FROM UndoableAbstractCommand

    public boolean modifyAbility (int delta)
    {
	Being b = GameSession.getInstance().findBeing(this.targetName);

	if (b == null)
	{
	    Term.echo("No creature named '"+targetName+"' was found.\n");
	    return false;
	}

	b.getAbilities().setAbilityDelta(this.abilityDefinition, delta);

	int newDelta = b.getAbilities().getAbilityDelta(this.abilityDefinition);

	Term.echo
	    ("Ability delta for "+this.targetName+"'s "+
	     this.abilityDefinition+" is now "+
	     burningbox.org.dsh.views.Utils.formatModifier(newDelta, 3));
	Term.echo("\n");

	return true;
    }

    private boolean isAbilityDefinitionOk ()
    {
	if (this.abilityDefinition.startsWith("st"))
	{
	    this.abilityDefinition = Definitions.STRENGTH;
	    return true;
	}
	if (this.abilityDefinition.startsWith("de"))
	{
	    this.abilityDefinition = Definitions.DEXTERITY;
	    return true;
	}
	if (this.abilityDefinition.startsWith("co"))
	{
	    this.abilityDefinition = Definitions.CONSTITUTION;
	    return true;
	}
	if (this.abilityDefinition.startsWith("in"))
	{
	    this.abilityDefinition = Definitions.INTELLIGENCE;
	    return true;
	}
	if (this.abilityDefinition.startsWith("wi"))
	{
	    this.abilityDefinition = Definitions.WISDOM;
	    return true;
	}
	if (this.abilityDefinition.startsWith("ch"))
	{
	    this.abilityDefinition = Definitions.CHARISMA;
	    return true;
	}
	return false;
    }
    
    public boolean execute ()
    {
	if (noMoreTokens()) return false;
	this.targetName = tokenizer.nextToken();

	if (noMoreTokens()) return false;
	this.abilityDefinition = tokenizer.nextToken().toLowerCase();
	if ( ! isAbilityDefinitionOk())
	{
	    Term.echo(this.abilityDefinition+" doesn't match any ability.\n");
	    return false;
	}

	if (noMoreTokens()) return false;
	try
	{
	    this.delta = Integer.parseInt(tokenizer.nextToken());
	}
	catch (NumberFormatException nfe)
	{
	    Term.echo(getSyntax()+"\n");
	    return false;
	}

	return modifyAbility(this.delta);
    }

    public String getUndoInfo ()
    {
	return
	    "Added "+this.delta+" to "+this.targetName+" "+
	    this.abilityDefinition+" score.";
    }

    public void undo ()
    {
	modifyAbility(-this.delta);
    }

    public String getSyntax ()
    {
	return
	    "ab {targetName} {abilityDefinition} {delta}";
    }

    public String getHelp ()
    {
	return
	    "This commands adds (or removes) points to an ability score. "+
	    "these points are not considered as permanently lost or gained.";
    }

}
