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
 * $Id: Monster.java,v 1.9 2002/11/03 17:13:49 jmettraux Exp $
 */

//
// Monster.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// Your object is to save the world, while still leading a pleasant life.
// 

package burningbox.org.dsh.entities;

import burningbox.org.dsh.Definitions;


/**
 * A Monster (a static Being, not classed)
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/11/03 17:13:49 $
 * <br>$Id: Monster.java,v 1.9 2002/11/03 17:13:49 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class Monster

    extends MonsterTemplate

{

    //
    // FIELDS

    protected int hitPoints = -10;
    protected int currentHitPoints = -10;
    protected int subdualDamage = 0;

    //
    // CONSTRUCTORS

    public Monster ()
    {
    }

    public Monster 
	(String name,
	 MonsterTemplate mt,
	 int hitPoints)
    {
	this.name = name;
	this.hitPoints = hitPoints;
	this.currentHitPoints = hitPoints;

	//
	// 'cloned' stuff
	
	this.category = mt.category;
	this.setRaceName(mt.getRaceName());
	this.alignment = mt.alignment;
	this.armorClass = mt.armorClass;
	this.armorClassVsTouchAttack = mt.armorClassVsTouchAttack;
	this.hitDice = mt.hitDice;
	this.initModifier = mt.initModifier;
	this.triggerToApplyEachRound = mt.triggerToApplyEachRound;
	this.attackRoutine = mt.attackRoutine;
	
	this.specials = mt.specials;

	// attacks
	this.attacks = new java.util.ArrayList(mt.attacks.size());
	java.util.Iterator it = mt.attacks.iterator();
	while (it.hasNext())
	{
	    Attack attackTemplate = (Attack)it.next();
	    Attack attack = (Attack)attackTemplate.clone();
	    attack.setOwner(this);
	    this.attacks.add(attack);
	}

	this.speeds = mt.speeds;
	this.challengeRating = mt.challengeRating;
	this.size = mt.size;
	this.abilities = mt.abilities;

	this.skills = mt.skills;
	this.feats = mt.feats;
	this.savingThrowSet = mt.savingThrowSet;
    }

    //
    // BEAN METHODS

    public int getHitPoints () { return this.hitPoints; }
    public int getCurrentHitPoints () { return this.currentHitPoints; }
    public int getSubdualDamage () { return this.subdualDamage; }

    public void setHitPoints (int hp) { this.hitPoints = hp; }
    public void setCurrentHitPoints (int chp) { this.currentHitPoints = chp; }
    public void setSubdualDamage (int sd) { this.subdualDamage = sd; }

    /**
     * A small override.
     *
     * It's tricky, when loading a monster, this method doesn't get called
     * it seems that XML serialization just bypass gets and sets
     * and works directly with the fields...
     */
    public void setAttacks (java.util.List attacks) 
    { 
	this.attacks = attacks; 

	java.util.Iterator it = this.attacks.iterator();
	while (it.hasNext())
	{
	    Attack a = (Attack)it.next();
	    a.setOwner(this);
	}
    }

    //
    // METHODS

}
