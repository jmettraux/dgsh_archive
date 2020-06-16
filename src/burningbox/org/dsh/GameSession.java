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
 * $Id: GameSession.java,v 1.17 2002/11/03 17:13:49 jmettraux Exp $
 */

//
// GameSession.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// You work very hard.  Don't try to think as well.
// 

package burningbox.org.dsh;

import burningbox.org.dsh.entities.*;
import burningbox.org.dsh.entities.equipment.*;


/**
 * A gaming session
 * There may be one and only one instance of a GamingSession per dsh.
 * (I made a Singleton out of it)
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/11/03 17:13:49 $
 * <br>$Id: GameSession.java,v 1.17 2002/11/03 17:13:49 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class GameSession

//    implements Session

{

    static org.apache.log4j.Logger log =
        org.apache.log4j.Logger.getLogger("dgsh.GameSession");

    //
    // THE SINGLETON

    //
    // it is not a 'full singleton' because the class constructor is public

    private static GameSession theGameSession = new GameSession();

    //
    // FIELDS
    
    private BeanSet party = new BeanSet();
    private BeanSet tempSet = new BeanSet(); 
        // a temporary set of items

    private String logFileName = null;
    private String scenarioDirectory = null;

    private java.util.Map envAttributes = null;

    private GameClock clock = new GameClock();

    //
    // CONSTRUCTORS
    
    public GameSession ()
    {
	this.party = new BeanSet();
    }

    //
    // (BEAN) METHODS
    
    public BeanSet getParty ()
    {
	return this.party;
    }

    public BeanSet getTempSet ()
    {
	return this.tempSet;
    }

    public GameClock getClock ()
    {
	return this.clock;
    }

    public void setClock (GameClock clock)
    {
	this.clock = clock;
    }

    //
    // METHODS

    public void setEnvAttributes (java.util.Map envAttributes)
    {
	this.envAttributes = envAttributes;
    }

    public java.util.Map getEnvAttributes ()
    {
	return this.envAttributes;
    }

    public String getEnvAttributeValue (String attributeName)
    {
	return (String)this.envAttributes.get(attributeName);
    }

    public boolean getBooleanEnvAttributeValue (String attributeName)
    {
	String sValue = (String)this.envAttributes.get(attributeName);

	if (sValue == null) return false;

	return ("true".equals(sValue.toLowerCase()));
    }

    public String getLogFileName ()
    {
	return this.logFileName;
    }

    public void setLogFileName (String name)
    {
	this.logFileName = name;
    }

    public String getScenarioDirectory ()
    {
	return this.scenarioDirectory;
    }

    public void setScenarioDirectory (String path)
    {
	if (! path.endsWith(java.io.File.separator))
	    path += java.io.File.separator;

	this.scenarioDirectory = path;
    }

    //
    // STATIC METHODS

    /**
     * returns an iterator that points on all the belligerents
     * i.e. party + opponents
     *
    public static java.util.Iterator getBelligerentIterator ()
    {
	return new BelligerentIterator();
    }
     *
     */

    public Being findBeing (String name)
    {
	Being result = null;

	name = name.replace('_', ' ');

	result = (Being)theGameSession.party.get(name);

	if (result == null && CombatSession.isCombatGoingOn())
	{
	    result = (Being)CombatSession.getInstance()
		.getOpponents().get(name);
	}

	if (result == null)
	{
	    result = (Being)theGameSession.tempSet.get(name);
	}

	return result;
    }

    public burningbox.org.dsh.entities.Character findCharacter (String name)
    {
	Being b = findBeing(name);

	if (b == null) return null;

	if ( ! (b instanceof burningbox.org.dsh.entities.Character))
	    return null;

	return (burningbox.org.dsh.entities.Character)b;
    }

    public BeanSet findSet (String setName)
    {
	if (setName.startsWith("p"))
	{
	    return theGameSession.party;
	}
	if (setName.startsWith("t"))
	{
	    return theGameSession.tempSet;
	}
	if (setName.startsWith("o"))
	{
	    if (CombatSession.isCombatGoingOn())
	    {
		return CombatSession.getInstance().getOpponents();
	    }
	}
	return null;
    }

    //
    // SINGLETON METHOD
    
    public static GameSession getInstance ()
    {
	return theGameSession;
    }

}
