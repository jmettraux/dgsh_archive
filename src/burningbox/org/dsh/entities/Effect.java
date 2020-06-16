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
 * $Id: Effect.java,v 1.10 2002/11/11 10:40:30 jmettraux Exp $
 */

//
// Effect.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// The secret source of humor is not joy but sorrow; 
// there is no humor in Heaven.
// 		-- Mark Twain
// 

package burningbox.org.dsh.entities;

import burningbox.org.dsh.GameSession;
import burningbox.org.dsh.CombatSession;
import burningbox.org.dsh.commands.Trigger;


/**
 * A magical effect or a rage...
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/11/11 10:40:30 $
 * <br>$Id: Effect.java,v 1.10 2002/11/11 10:40:30 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class Effect

    implements
        Named,
        WithInitiative,
	MiscModified

{

    static org.apache.log4j.Logger log =
	org.apache.log4j.Logger.getLogger("dgsh.Effect");

    //
    // FIELDS
    
    protected String name = null;
    protected String description = null;
    protected int currentInitiative = 100;
    protected int ownerDexterity = 0;
    protected int roundsToGo = 0;
    protected java.util.List targetNames = null;
    protected java.util.List miscModifiers = new java.util.ArrayList(1);

    protected Trigger eachRoundTrigger = null;
    protected Trigger postEffectTrigger = null;

    //
    // CONSTRUCTORS
    
    public Effect
	(String name,
	 String description,
	 int initiative,
	 int roundsToGo,
	 java.util.List targetNames)
    {
	this(name, description, initiative, 0, roundsToGo, targetNames);
    }

    public Effect
	(String name,
	 String description,
	 int initiative,
	 int ownerDexterity,
	 int roundsToGo,
	 String targetName)
    {
	this
	    (name, 
	     description, 
	     initiative, 
	     0, 
	     roundsToGo, 
	     new java.util.ArrayList(1));

	this.targetNames.add(targetName);
    }

    public Effect
	(String name,
	 String description,
	 int initiative,
	 int ownerDexterity,
	 int roundsToGo,
	 java.util.List targetNames)
    {
	this.name = name;
	this.description = description;
	this.currentInitiative = initiative;
	this.ownerDexterity = ownerDexterity;
	this.roundsToGo = roundsToGo;
	this.targetNames = targetNames;
    }

    //
    // METHODS

    public String getName () { return this.name; }
    public String getDescription () { return this.description; }
    public int getCurrentInitiative () { return this.currentInitiative; }
    public int computeDexterity () { return this.ownerDexterity; }
    public int getRoundsToGo () { return this.roundsToGo; }
    public java.util.List getTargetNames () { return this.targetNames; }

    public void setCurrentInitiative (int i) { this.currentInitiative = i; }

    public int incrementRoundsToGo (int inc) 
    { 
	this.roundsToGo += inc; 

	if (this.eachRoundTrigger != null)
	{
	    this.eachRoundTrigger.trigger();
	}

	return this.roundsToGo;
    }

    public void end ()
    {
	this.roundsToGo = -100;
	this.miscModifiers = null;

	//
	// launch post effect
	
	if (this.postEffectTrigger != null)
	{
	    this.postEffectTrigger.trigger();
	}
    }

    public void addModifier (MiscModifier mm)
    {
	this.miscModifiers.add(mm);
    }

    public java.util.Iterator modifierIterator ()
    {
	if (this.miscModifiers == null) return null;

	return this.miscModifiers.iterator();
    }

    public int sumMiscModifiers (String definition)
    {
	if (this.roundsToGo <= 0) return 0; 
    	    // will get removed some way somehow

	return MiscModifier.sumModifiers(definition, this.miscModifiers);
    }

    public void setPostEffectTrigger (Trigger t)
    {
	this.postEffectTrigger = t;
    }

    //
    // some methods for recognizing the effect

    public boolean isDazed ()
    {
	return this.name.toLowerCase().matches(".* dazed.*");
    }

    //
    // STATIC METHODS

    public static void addEffect (Effect fx)
    {
	if ( ! CombatSession.getInstance().getInitiativeTable().isReady())
	    //
	    // initiative table got removed...
	    // don't add effect !
	{
	    //System.out.println("addEffect() : initiative table not ready");
	    return;
	}

	//System.out.println("addEffect() : adding effect "+fx.getName());

	CombatSession.getInstance().getInitiativeTable().add(fx);

	//
	// add effect to each target
	
	java.util.Iterator it = fx.getTargetNames().iterator();
	while (it.hasNext())
	{
	    Being b = GameSession.getInstance().findBeing((String)it.next());
	    b.addEffect(fx);
	}
    }

    public static void removeEffect (Effect fx)
    {
	log.debug("removing effect '"+fx.getName()+"'");

	fx.end();
	CombatSession.getInstance().getInitiativeTable().remove(fx);
    }

}
