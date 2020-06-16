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
 * $Id: EffectCommand.java,v 1.11 2002/07/22 05:46:52 jmettraux Exp $
 */

//
// EffectCommand.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// Your best consolation is the hope that the things you failed to get weren't
// really worth having.
// 

package burningbox.org.dsh.commands;

import burningbox.org.dsh.Term;
import burningbox.org.dsh.Shell;
import burningbox.org.dsh.GameSession;
import burningbox.org.dsh.CombatSession;
import burningbox.org.dsh.entities.Being;
import burningbox.org.dsh.entities.Effect;
import burningbox.org.dsh.entities.Monster;
//import burningbox.org.dsh.entities.EffectMap;
import burningbox.org.dsh.entities.MiscModifier;


/**
 * Adds an effect to the initiative table and to the effect map
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/07/22 05:46:52 $
 * <br>$Id: EffectCommand.java,v 1.11 2002/07/22 05:46:52 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class EffectCommand

    extends
        UndoableAbstractCommand

    implements
        CombatCommand

{

    //static org.apache.log4j.Logger log =
    //    org.apache.log4j.Logger.getLogger(EffectCommand.class.getName());

    //
    // FIELDS

    protected Effect createdEffect = null;

    //
    // CONSTRUCTORS

    public EffectCommand ()
    {
    }

    //
    // METHODS

    private boolean aTargetDoesntExist (java.util.List targetNames)
    {
	if (targetNames.size() < 1)
	    // there can be effects that don't affect anyone
	    // there are just reminders
	    return false;

	java.util.Iterator it = targetNames.iterator();
	while (it.hasNext())
	{
	    Being b = GameSession.getInstance().findBeing((String)it.next());

	    if ( ! (b instanceof burningbox.org.dsh.entities.Character) ||
		 ! (b instanceof Monster))
	    {
		return false;
	    }
	}
	return true;
    }

    private MiscModifier askForModifier (String effectName)
    {
	try
	{
	    Term.echo("    MiscModifier\n");
	    Term.echo("        definition > ");
	    String definition = Term.readLine().trim();

	    if (definition.trim().equals(""))
		return null;

	    int mod = 0;
	    while (true)
	    {
		Term.echo("        modifier   > ");
		String sMod = Term.readLine();
		try
		{
		    mod = Integer.parseInt(sMod);
		    break;
		}
		catch (NumberFormatException nfe)
		{
		    // continue
		}
	    }

	    MiscModifier mm = 
		new MiscModifier(definition, mod, "fx '"+effectName+"'");

	    return mm;
	}
	catch (java.io.IOException ie)
	{
	    return null;
	}
    }

    //
    // METHODS FROM AbstractCommand
    
    public boolean execute ()
    {
	if (CombatSession.getInstance().getInitiativeTable() == null)
	{
	    Term.echo
		("Initiative has not yet been determined. "+
		 "Use the 'init' command.\n");
	    return false;
	}

	//
	// parse

	if (noMoreTokens()) return false;
	String name = tokenizer.nextToken();

	java.util.List targetNames = new java.util.ArrayList();
	while(this.tokenizer.hasMoreTokens())
	{
	    targetNames.add(this.tokenizer.nextToken());
	}

	//
	// check if targets exist
	
	if (aTargetDoesntExist(targetNames))
	{
	    Term.echo("One of the targets you specified doesn't exist.\n");
	    return false;
	}

	//
	// ask for a description
	
	Term.echo("    description of effect > ");
	String description = "";
	try
	{
	    description = Term.readLine().trim();
	}
	catch (java.io.IOException ie)
	{
	    // ignore
	}

	//
	// ask for the 'rounds to go'
	
	int roundsToGo = 0;
	while (true)
	{
	    Term.echo("    rounds to go > ");
	    try
	    {
		roundsToGo = Integer.parseInt(Term.readLine());
		break;
	    }
	    catch (Exception e)
	    {
		// ignore
	    }
	}

	int initiative = 
	    CombatSession.getInstance().getInitiativeTable().getCurrentInitiative();

	//
	// build effect
	
	this.createdEffect = new Effect
	    (name,
	     description,
	     initiative,
	     roundsToGo,
	     targetNames);

	//
	// ask for modifiers
	
	while (true)
	{
	    MiscModifier mm = askForModifier(name);
	    if (mm == null)
		break;
	    this.createdEffect.addModifier(mm);
	}

	//
	// add modifiers

	Effect.addEffect(this.createdEffect);

	Term.echo("Effect created and added.\n");
	return true;
    }

    public String getUndoInfo ()
    {
	return
	    "Created effect '"+this.createdEffect.getName()+"'";
    }

    public void undo ()
    {
	/*
	CombatSession.getInstance().getEffectMap()
	    .removeEffect(this.createdEffect);
	
	CombatSession.getInstance().getInitiativeTable()
	    .remove(this.createdEffect);
	*/
	Effect.removeEffect(this.createdEffect);

	Term.echo("Effect removed. \n");
    }

    public String getSyntax ()
    {
	return "effect {name} {target}*";
    }

    public String getHelp ()
    {
	return "Adds an effect";
    }

}
