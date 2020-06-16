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
 * $Id: RelaxCommand.java,v 1.9 2002/11/11 10:40:30 jmettraux Exp $
 */

//
// RelaxCommand.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// You have a strong appeal for members of the opposite sex.
//

package burningbox.org.dsh.commands;

import burningbox.org.dsh.*;
import burningbox.org.dsh.entities.*;


/**
 * Let the fight end !
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/11/11 10:40:30 $
 * <br>$Id: RelaxCommand.java,v 1.9 2002/11/11 10:40:30 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class RelaxCommand

    extends 
        AbstractCommand

    implements
        CombatCommand

{
    static org.apache.log4j.Logger log =
        org.apache.log4j.Logger.getLogger("dgsh.RelaxCmd");

    //
    // FIELDS
    
    //
    // CONSTRUCTORS

    public RelaxCommand ()
    {
    }

    //
    // METHODS
    
    /*
     * This method transfer opponents not dead to the tempSet
     */
    private static void releaseNotDead ()
    {
	java.util.Iterator it = CombatSession.getInstance()
	    .getOpponents().iterator();

	float deadcr = (float)0.0;
	float alivecr = (float)0.0;

	while (it.hasNext())
	{
	    Being b = (Being)it.next();

	    if (((b instanceof burningbox.org.dsh.entities.Character) && 
			b.getCurrentHitPoints() > -1) ||
		((b instanceof Monster) && b.getCurrentHitPoints() > 0))
	    {
		GameSession.getInstance().getTempSet().add(b);
		alivecr += b.computeChallengeRating();
	    }
	    else
	    {
		deadcr += b.computeChallengeRating();
	    }
	}

	float totalcr = deadcr + alivecr;
	
	BeanSet party = GameSession.getInstance().getParty();
	int partyLevel = 
	    Experience.computeChallengeRating(party) / party.size();

	int deadxp = Experience.computeAward(partyLevel, deadcr);
	int alivexp = Experience.computeAward(partyLevel, alivecr);
	int totalxp = Experience.computeAward(partyLevel, totalcr);

	Term.echo("Party level                      : "+partyLevel);
	Term.echo("\nTotal dead opponents' CR         : "+deadcr);
	Term.echo("  ("+deadxp+" xp) \n");
	Term.echo("Total still alive opponents' CR  : "+alivecr);
	Term.echo("  ("+alivexp+" xp) \n");
	Term.echo("Total CR                         : "+totalcr);
	Term.echo("  ("+totalxp+" xp) \n");

	log.info("Party level                      : "+partyLevel);
	log.info("Total dead opponents' CR         : "+deadcr+ 
		 "  ("+deadxp+" xp)");
	log.info("Total still alive opponents' CR  : "+alivecr+
	         "  ("+alivexp+" xp)");
	log.info("Total CR                         : "+totalcr+
	         "  ("+totalxp+" xp)");
    }

    //
    // METHODS FROM AbstractCommand
    
    public boolean execute ()
    {
	if (CombatSession.getInstance().getInitiativeTable() != null)
	    CombatSession.getInstance().getInitiativeTable().release();

	releaseNotDead();

	int elapsedRounds = CombatSession.getInstance().getRoundsElapsed();

	elapsedRounds++; // count end round

	Term.echo("Combat lasted "+elapsedRounds+" rounds\n");
	log.info("Combat lasted "+elapsedRounds+" rounds");

	CombatSession.endCombatSession();
	return true;
    }

    public String getSyntax ()
    {
	return "relax";
    }

    public String getHelp ()
    {
	return
	    "Quit the combat mode with this command.";
    }

}
