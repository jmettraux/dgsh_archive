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
 * $Id: Abilities.java,v 1.5 2002/06/26 14:23:41 jmettraux Exp $
 */

//
// Abilities.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// There was a phone call for you.
// 

package burningbox.org.dsh.entities;

import burningbox.org.dsh.Definitions;


/**
 * A set of the 6 abilities
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/06/26 14:23:41 $
 * <br>$Id: Abilities.java,v 1.5 2002/06/26 14:23:41 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class Abilities
{

    //
    // FIELDS

    protected java.util.Map abilities 
	= new java.util.HashMap();

    protected transient java.util.Map abilityDeltas
	= new java.util.HashMap();

    //
    // CONSTRUCTORS

    public Abilities ()
    {
    }

    public Abilities
	(int strength,
	 int dexterity,
	 int constitution,
	 int intelligence,
	 int wisdom,
	 int charisma)
    {
	this.abilities.put(Definitions.STRENGTH, new Integer(strength));
	this.abilities.put(Definitions.DEXTERITY, new Integer(dexterity));
	this.abilities.put(Definitions.CONSTITUTION, new Integer(constitution));
	this.abilities.put(Definitions.INTELLIGENCE, new Integer(intelligence));
	this.abilities.put(Definitions.WISDOM, new Integer(wisdom));
	this.abilities.put(Definitions.CHARISMA, new Integer(charisma));
    }

    //
    // METHODS

    public java.util.Map getAbilities () { return this.abilities; }

    public void setAbilities (java.util.Map abilities)
    {
	this.abilities = abilities;
    }

    public int getScore (String abilityDefinition)
    {
	return ((Integer)this.abilities.get(abilityDefinition)).intValue();
    }

    /*
    public int getModifier (String abilityDefinition)
    {
	return ((getScore(abilityDefinition) + 
		 getAbilityDelta(abilityDefinition)) / 2) - 5;
    }
    */

    public static int computeModifier (int score)
    {
	return (score / 2) - 5;
    }

    public int getAbilityDelta (String abilityDefinition)
    {
	Integer i = (Integer)this.abilityDeltas.get(abilityDefinition);

	if (i == null) return 0;

	return i.intValue();
    }

    public void setAbilityDelta (String abilityDefinition, int delta)
    {
	int initialDelta = getAbilityDelta(abilityDefinition);
	delta += initialDelta;
	this.abilityDeltas.put(abilityDefinition, new Integer(delta));
    }

}
