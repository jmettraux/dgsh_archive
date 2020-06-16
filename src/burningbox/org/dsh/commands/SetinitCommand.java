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
 * $Id: SetinitCommand.java,v 1.8 2002/07/22 05:46:52 jmettraux Exp $
 */

//
// SetinitCommand.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// You will be recognized and honored as a community leader.
// 

package burningbox.org.dsh.commands;

import burningbox.org.dsh.*;
import burningbox.org.dsh.entities.*;
import burningbox.org.dsh.views.PartyView;


/**
 * Displays the opponents
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/07/22 05:46:52 $
 * <br>$Id: SetinitCommand.java,v 1.8 2002/07/22 05:46:52 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class SetinitCommand

    extends
        AbstractCommand

    implements
        RegularCommand,
        CombatCommand

{

    //static org.apache.log4j.Logger log =
    //    org.apache.log4j.Logger.getLogger(SetinitCommand.class.getName());

    //
    // FIELDS

    //
    // CONSTRUCTORS

    public SetinitCommand ()
    {
    }

    //
    // METHODS

    //
    // METHODS FROM AbstractCommand
    
    public boolean execute ()
    {
	if (noMoreTokens()) return false;
	String targetName = fetchName();

	Being target = GameSession.getInstance().findBeing(targetName);

	if (target == null)
	{
	    Term.echo
		("'"+targetName+"' was not found. \n");
	    return false;
	}

	int newInitiative = -10;
	while(true)
	{
	    try
	    {
		Term.echo("    ini score > ");
		String score = Term.readLine();

		if (score.trim().length() == 0)
		    //
		    // auto roll demanded
		    //
		{
		    int dRoll = burningbox.org.dsh.Utils.throwD20();
		    Term.echo("   computer rolls "+dRoll);

		    newInitiative = dRoll + target.computeInitiativeModifier();
		    Term.echo("   that makes "+newInitiative+" \n");
		    break;
		}

		newInitiative = Integer.parseInt(score);
		break;
	    }
	    catch (Exception e)
	    {
		// stay in the loop
	    }
	}

	target.setCurrentInitiative(newInitiative);

	CombatSession.getInstance().getInitiativeTable().add(target);
	CombatSession.getInstance().getInitiativeTable().sort();

	Term.echo
	    ("Initiative for '"+target.getName()+
	     "' set to "+newInitiative+". \n");
	return true;
    }

    public String getSyntax ()
    {
	return "setinit {character or monster name}";
    }

    public String getHelp ()
    {
	return
	    "Sets the initiative for someone entering the fight.";
    }

}
