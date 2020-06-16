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
 * $Id: InitCommand.java,v 1.5 2002/07/23 05:50:35 jmettraux Exp $
 */

//
// InitCommand.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// Today is the tomorrow you worried about yesterday.
//

package burningbox.org.dsh.commands;

import burningbox.org.dsh.*;
import burningbox.org.dsh.entities.*;


/**
 * Builds the initiative table i.e. automatically rolls initiative for all
 * the opponents and asks it for each party member
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/07/23 05:50:35 $
 * <br>$Id: InitCommand.java,v 1.5 2002/07/23 05:50:35 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class InitCommand

    extends 
        AbstractCommand

    implements
        CombatCommand

{
    static org.apache.log4j.Logger log =
        org.apache.log4j.Logger.getLogger("dgsh.InitCmd");

    //
    // FIELDS
    
    //
    // CONSTRUCTORS

    public InitCommand ()
    {
    }

    //
    // METHODS

    //
    // METHODS FROM AbstractCommand

    protected void addOpponentsToInitiativeTable ()
    {
	Term.echo("  Opponents \n");
	java.util.Iterator it = 
	    CombatSession.getInstance().getOpponents().iterator();
	while (it.hasNext())
	{
	    Being b = (Being)it.next();
	    Term.echo("    '"+b.getName()+"'");

	    int dRoll = burningbox.org.dsh.Utils.throwD20();
	    Term.echo(" rolls a "+dRoll);

	    int initiativeScore = dRoll + b.computeInitiativeModifier();
	    Term.echo(" that makes a\t\t"+initiativeScore+"\n");

	    b.setCurrentInitiative(initiativeScore);

	    CombatSession.getInstance().getInitiativeTable().add(b);

	    log.info(b.getName()+" ini "+initiativeScore);
	}
    }

    protected void addEachPartyMemberToInitiativeTable ()
	//
	// players are acustomed to give directly their modified
	// init result to the DM
	//
    {
	Term.echo("  Party members \n");
	java.util.Iterator it = 
	    GameSession.getInstance().getParty().iterator();
	while (it.hasNext())
	{
	    Being b = (Being)it.next();
	    Term.echo("    '"+b.getName()+"'");

	    int initModifier = b.computeInitiativeModifier();
	    String sInitModifier = ""+initModifier;
	    if (initModifier > -1)
		sInitModifier = "+"+sInitModifier;

	    Term.echo(" has "+sInitModifier+" \n");

	    int result = -1;
	    while(true)
	    {
		Term.echo("        d20 roll > ");
		String input = null;
		try
		{
		    input = Term.readLine().trim();
		}
		catch (java.io.IOException ie)
		{
		    continue; // ask again
		}

		if (input.length() < 1)
		{
		    // computer rolls for character / friendly monster
		    int roll = burningbox.org.dsh.Utils.throwD20();
		    Term.echo("        automatic roll -> "+roll);
		    result = roll + initModifier;
		    break;
		}

		try
		{
		    result = Integer.parseInt(input);
		    break;
		}
		catch (NumberFormatException nfe)
		{
		    continue;
		}
	    }

	    Term.echo("        initiative score is "+result+". \n");

	    b.setCurrentInitiative(result);

	    CombatSession.getInstance().getInitiativeTable().add(b);

	    log.info(b.getName()+" ini "+result);
	}
    }
    
    public boolean execute ()
    {
	if (CombatSession.thereAreNoOpponents())
	{
	    Term.echo("Cannot roll for initiative. There are no opponents !\n");
	    return false;
	}

	/*
	if (CombatSession.getInstance().getInitiativeTable() != null)
	{
	    Term.echo("You already rolled for initiative. \n");
	    return false;
	}
	CombatSession.getInstance().prepareInitiatives();
	*/

	addOpponentsToInitiativeTable();
	addEachPartyMemberToInitiativeTable();

	CombatSession.getInstance().getInitiativeTable().sort();

	return true;
    }

    public String getSyntax ()
    {
	return "init";
    }

    public String getHelp ()
    {
	return
	    "Run this command when your opponents are ready for combat.";
    }

}
