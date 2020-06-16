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
 * $Id: CombatSession.java,v 1.14 2002/11/11 10:40:30 jmettraux Exp $
 */

//
// CombatSession.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// Give your very best today.  Heaven knows it's little enough.
// 

package burningbox.org.dsh;

import burningbox.org.dsh.entities.*;


/**
 * A CombatSession lasts only the duration of a combat.
 * It contains mainly the opponents (foe) of the PC party.
 *
 * As there can be only one active CombatSession at a time,
 * there is also a singleton system (see static methods)
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/11/11 10:40:30 $
 * <br>$Id: CombatSession.java,v 1.14 2002/11/11 10:40:30 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class CombatSession

//    implements Session

{

    //
    // THE SINGLETON 
    //
    // (may be null when there is no combat)
    
    protected static CombatSession theCombatSession = null;

    //
    // FIELDS
    
    protected BeanSet opponents = new BeanSet();
    //protected float totalDefeatedChallengeRating = (float)0.0;
    protected int roundsElapsed = 0;
    protected int currentInitiative = 1000;

    protected InitiativeTable initiativeTable = new InitiativeTable();
    //protected EffectMap effectMap = new EffectMap();

    //
    // CONSTRUCTORS

    public CombatSession ()
    {
    }

    //
    // METHODS

    //public float getTotalDefeatedChallengeRating () 
    //{ 
    //    return this.totalDefeatedChallengeRating; 
    //}

    public int getRoundsElapsed () 
    { 
	return this.roundsElapsed; 
    }

    public void setOpponents (BeanSet bs) 
    { 
	this.opponents = bs; 
    }

    //public void setTotalDefeatedChallengeRating (float tdcr) 
    //{ 
    //    this.totalDefeatedChallengeRating = tdcr; 
    //}

    public void setRoundsElapsed (int re) 
    { 
	this.roundsElapsed = re; 
    }

    public void nextRound ()
    {
	this.roundsElapsed++;
	this.initiativeTable.flushAttackOfOpportunities();
	GameSession.getInstance().getClock().addRounds(1);
    }
    
    public void previousRound ()
    {
	this.roundsElapsed--;
	GameSession.getInstance().getClock().addRounds(-1);
    }
    
    /*
    public void prepareInitiatives ()
    {
	this.initiativeTable = new InitiativeTable();
	//this.effectMap = new EffectMap();
    }
    */

    public InitiativeTable getInitiativeTable ()
    {
	return this.initiativeTable;
    }

    public Being getCurrentBeing ()
    {
	return (Being)this.initiativeTable.current();
    }

    /*
    public EffectMap getEffectMap ()
    {
	return this.effectMap;
    }
    */

    public BeanSet getOpponents () 
    { 
	return this.opponents; 
    }

    //
    // STATIC METHODS
    
    /**
     * Returns the current combat session 
     * (or null if there is no combat going on)
     */
    public static CombatSession getInstance ()
    {
	return theCombatSession; // even if it's null
    }

    public static boolean initiativeGotRolled ()
    {
	if ( ! isCombatGoingOn()) return false;

	return (getInstance().initiativeTable != null);
    }

    /**
     * Returns true if a combat session is active
     */
    public static boolean isCombatGoingOn ()
    {
	return (theCombatSession != null);
    }

    public static boolean thereAreNoOpponents ()
    {
	return 
	    (theCombatSession.opponents == null ||
	     theCombatSession.opponents.size() < 1);
    }

    /**
     * Begins a new combat.
     * This method will throw an exception if the last combat
     * has not been ended (with endCombatSession())
     */
    public static CombatSession beginCombatSession ()
	throws DshException
    {
	if (theCombatSession != null)
	{
	    throw new DshException
		("There is already a combat session going on.");
	}

	theCombatSession = new CombatSession();

	return theCombatSession;
    }

    /**
     * Ends the current combat session.
     */
    public static void endCombatSession ()
    {
	//
	// flush the effects
	
	/*
	if (theCombatSession.effectMap != null)
	    theCombatSession.effectMap.flush();
	*/

	theCombatSession = null;
    }

}
