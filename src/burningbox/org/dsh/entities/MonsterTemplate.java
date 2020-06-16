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
 * $Id: MonsterTemplate.java,v 1.12 2002/11/03 17:13:49 jmettraux Exp $
 */

//
// MonsterTemplate.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// A kind of Batman of contemporary letters.
// 		-- Philip Larkin on Anthony Burgess
//

package burningbox.org.dsh.entities;

import burningbox.org.dsh.Utils;
import burningbox.org.dsh.Definitions;
import burningbox.org.dsh.DshException;
import burningbox.org.dsh.PythonInterpreter;
import burningbox.org.dsh.commands.Trigger;


/**
 * A monster that is ready for instantiating
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/11/03 17:13:49 $
 * <br>$Id: MonsterTemplate.java,v 1.12 2002/11/03 17:13:49 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class MonsterTemplate

    extends Being

{

    public final static String TREASURE_STANDARD 
	= "Standard";
    public final static String TREASURE_DOUBLE_STANDARD 
	= "Double standard";
    public final static String TREASURE_TRIPLE_STANDARD 
	= "Triple standard";
    public final static String TREASURE_NONE
	= "None";

    //
    // FIELDS

    protected String category = null;
    protected String potentialHitPoints = null;
    protected SavingThrowSet savingThrowSet = null;
    protected int initModifier = 0;
    protected int armorClass = 10;
    protected int armorClassVsTouchAttack = 10;
    protected String treasure = TREASURE_NONE;
    protected String triggerToApplyEachRound = null;
    protected String attackRoutine = null;

    protected java.util.List attacks = new java.util.ArrayList(0);


    //
    // CONSTRUCTORS

    public MonsterTemplate ()
    {
    }

    //
    // BEAN METHODS

    public String getCategory () { return this.category; }
    public String getPotentialHitPoints () { return this.potentialHitPoints; }
    public SavingThrowSet getSavingThrowSet () { return this.savingThrowSet; }
    public int getInitModifier () { return this.initModifier; }
    public int getArmorClass () { return this.armorClass; }
    public int getArmorClassVsTouchAttack () { return this.armorClassVsTouchAttack; }
    public String getTreasure () { return this.treasure; }
    public String getTriggerToApplyEachRound () { return this.triggerToApplyEachRound; }
    public String getAttackRoutine () { return this.attackRoutine; }
    public java.util.List getAttacks () { return this.attacks; }


    public void setCategory (String s) { this.category = s; }
    public void setPotentialHitPoints (String php) { this.potentialHitPoints = php; }
    public void setSavingThrowSet (SavingThrowSet sts) { this.savingThrowSet = sts; }
    public void setInitModifier (int init) { this.initModifier = init; }
    public void setArmorClass (int ac) { this.armorClass = ac; }
    public void setArmorClassVsTouchAttack (int ac) { this.armorClassVsTouchAttack = ac; }
    public void setTreasure (String s) { this.treasure = s; }
    public void setTriggerToApplyEachRound (String s) { this.triggerToApplyEachRound = s; }
    public void setAttackRoutine (String s) { this.attackRoutine = s; }
    public void setAttacks (java.util.List attacks) { this.attacks = attacks; }

    //
    // METHODS
    
    //
    // instantiation methods

    public final Monster instantiate (String name)
    {
	int hitPoints = Utils.throwSetOfDices(this.potentialHitPoints);

	if (hitPoints == 0) hitPoints = 1;

	Monster result = new Monster
	    (name,
	     this,
	     hitPoints);

	// I should add random treasure generation !
	
	return result;
    }

    public final Monster[] instantiate (int count, String namePrefix)
    {
	if (namePrefix == null)
	{
	    namePrefix = this.getName();
	}

	Monster[] result = new Monster[count];

	for (int i=0; i<count; i++)
	{
	    result[i] = instantiate(namePrefix+(i+1));
	}

	return result;
    }

    //
    // METHODS FROM Being

    public int getHitPoints () { return -10; }
    public int getCurrentHitPoints () { return -10; }
    public int getSubdualDamage () { return 0; }
    public void setHitPoints (int hp) { }
    public void setCurrentHitPoints (int chp) { }
    public void setSubdualDamage (int sd) { }

    public SavingThrowSet computeSavingThrowSet ()
    {
	return this.savingThrowSet;
    }

    public int computeSkillModifier (String skillDefinition)
    {
	MonsterSkill mSkill = getSkill(skillDefinition);
	if (mSkill == null)
	    return -15;
	return mSkill.computeModifier();
    }

    public int computeArmorClass ()
    {
	return getArmorClass();
    }

    public int computeArmorClassVsTouchAttack ()
    {
	return getArmorClassVsTouchAttack();
    }

    public int computeInitiativeModifier ()
    {
	return this.initModifier;
    }

    public int computeAbilityModifier (String abilityDefinition)
    {
	return Abilities
	    .computeModifier(this.abilities.getScore(abilityDefinition));
    }

    public float computeChallengeRating ()
    {
	return getChallengeRating();
    }

    public Trigger fetchTriggerToApplyEachRound ()
	throws DshException
    {
	if (this.triggerToApplyEachRound == null) return null;

	Trigger t = (Trigger)PythonInterpreter.getInstance()
	    .instantiate(this.triggerToApplyEachRound, Trigger.class);

	if (t == null)
	{
	    throw new DshException
		("Trigger '"+this.triggerToApplyEachRound+"' not found");
	}

	t.setTarget(this);

	return t;
    }

}
