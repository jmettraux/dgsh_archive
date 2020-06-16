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
 * $Id: SpellSlots.java,v 1.9 2002/08/27 14:24:16 jmettraux Exp $
 */

//
// SpellSlots.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@yahoo.com)
//

package burningbox.org.dsh.entities.magic;

import burningbox.org.dsh.Utils;
import burningbox.org.dsh.Definitions;
import burningbox.org.dsh.DshException;
//import burningbox.org.dsh.entities.Being;
import burningbox.org.dsh.entities.DataSets;
import burningbox.org.dsh.entities.ClassLevel;
import burningbox.org.dsh.entities.CharacterClass;


/**
 * A spellSlots instance keeps tracks of the slots for a character.
 *
 * (It should be XML serializable)
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/08/27 14:24:16 $
 * <br>$Id: SpellSlots.java,v 1.9 2002/08/27 14:24:16 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class SpellSlots
{

    //
    // FIELDS

    protected java.util.Map slotsPerClass = new java.util.HashMap();

    //
    // CONSTRUCTORS

    public SpellSlots ()
    {
    }

    //
    // BEAN METHODS

    public java.util.Map getSlotsPerClass () { return this.slotsPerClass; }

    public void setSlotsPerClass (java.util.Map m) { this.slotsPerClass = m; }

    //
    // METHODS

    public void putSlots (String className, int[] slots)
    {
	this.slotsPerClass.put(className, slots);
    }
    
    public java.util.Iterator classIterator ()
    {
	return this.slotsPerClass.keySet().iterator();
    }

    public boolean isASpellCaster ()
    {
	return (this.slotsPerClass.size() > 0);
    }

    public int[] getSlots (String className)
    {
	return (int[])this.slotsPerClass.get(className);
    }

    public int[] getDomainSlots (String className)
    {
	return getSlots(Definitions.DOMAINS+"_"+className);
    }

    /*
    public void consumeStandardSpellSlot (Spell spell, String spellClass)
	throws DshException
    {
	consume(spell, spellClass, false);
    }

    public void consumeDomainSpellSlot (Spell spell, String spellClass)
	throws DshException
    {
	consume(spell, spellClass, true);
    }
    */

    public void consume (Spell spell, String spellClass, boolean domainSpell)
	throws DshException
    {
	int spellLevel = spell.getLevel(spellClass);

	if (domainSpell)
	    spellClass = Definitions.DOMAINS+"_"+spellClass;

	int[] slots = getSlots(spellClass);

	if (slots == null)
	{
	    throw new DshException 
		("The character hasn't any slots for class '"+spellClass+"'");
	}

	int remainingSlots = slots[spellLevel];

	if (remainingSlots < 1)
	{
	    throw new DshException 
		("The character hasn't any slots left for level "+spellLevel+
		 " of class '"+spellClass+"'");
	}

	slots[spellLevel] = remainingSlots-1;
    }

    //
    // STATIC METHODS

    /**
     * Resets the spells per day for a character and one of its classes.
     * It doesn't proceed for every classes, a cleric who has levels as
     * a wizard too cannot pray and get its wizards spell ready at the same 
     * time.
     */
    public static void resetSlots 
	(burningbox.org.dsh.entities.Character spellCaster,
	 CharacterClass spellCasterClass)
    //throws DshException
    {
	int level = spellCaster
	    .computeLevelForClass(spellCasterClass.getName());

	if (level < 1) return;

	SpellSlots slots = spellCaster.fetchSpellSlots();
	if (slots == null)
	{
	    slots = new SpellSlots();
	    spellCaster.setSpellSlots(slots);
	}

	int[] spellsPerDay = spellCasterClass.getSpellsPerDay(level);

	String magicalAbility = spellCasterClass.getMagicalAbility();

	if (spellsPerDay != null && magicalAbility != null)
	{
	    int[] bonusSlots = computeBonusSlots
		(spellCaster.getAbilities().getScore(magicalAbility));

	    spellsPerDay = Utils.addArrays(spellsPerDay, bonusSlots);

	    slots.slotsPerClass.put(spellCasterClass.getName(), spellsPerDay);
		// overrides any previous record about the class
	}

	//
	// domain slots
	
	if (spellCasterClass.isUsingDomainSpells()) // usually a cleric
	{
	    int[] regularSlots = slots.getSlots(spellCasterClass.getName());
	    int[] domainSlots = new int[10];

	    domainSlots[0] = 0;

	    for (int i=1; i<10; i++)
	    {
		if (regularSlots[i] > 0) domainSlots[i] = 1;
	    }

	    slots.slotsPerClass.put
		(Definitions.DOMAINS+"_"+spellCasterClass.getName(), 
		 domainSlots);
	}
    }

    /**
     * After a long rest...
     * Resets the spell slots for every class of a character (if appliable)
     */
    public static void resetSlots 
	(burningbox.org.dsh.entities.Character spellCaster)
    //throws DshException
    {
	java.util.Iterator it = spellCaster.getClasses().iterator();
	while (it.hasNext())
	{
	    ClassLevel cl = (ClassLevel)it.next();

	    CharacterClass cc = DataSets.findCharacterClass(cl.getClassName());

	    if (cc != null) resetSlots (spellCaster, cc);
	}
    }

    /*
     * the table of bonus spell slots
     */
    private final static int[][] abilityBonus =
    {
	// begins at score 12
	new int[] { 0, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
	new int[] { 0, 1, 1, 0, 0, 0, 0, 0, 0, 0 },
	new int[] { 0, 1, 1, 1, 0, 0, 0, 0, 0, 0 },
	new int[] { 0, 1, 1, 1, 1, 0, 0, 0, 0, 0 },
	new int[] { 0, 2, 1, 1, 1, 1, 0, 0, 0, 0 },
	new int[] { 0, 2, 2, 1, 1, 1, 1, 0, 0, 0 },
	new int[] { 0, 2, 2, 2, 1, 1, 1, 1, 0, 0 },
	new int[] { 0, 2, 2, 2, 2, 1, 1, 1, 1, 0 },
	new int[] { 0, 3, 2, 2, 2, 2, 1, 1, 1, 1 },
	new int[] { 0, 3, 3, 2, 2, 2, 2, 1, 1, 1 },
	new int[] { 0, 3, 3, 3, 2, 2, 2, 2, 1, 1 },
	new int[] { 0, 3, 3, 3, 3, 2, 2, 2, 2, 1 },
	new int[] { 0, 4, 3, 3, 3, 3, 2, 2, 2, 2 },
	new int[] { 0, 4, 4, 3, 3, 3, 3, 2, 2, 2 },
	new int[] { 0, 4, 4, 4, 3, 3, 3, 3, 2, 2 },
	new int[] { 0, 4, 4, 4, 4, 3, 3, 3, 3, 2 },
	new int[] { 0, 5, 4, 4, 4, 4, 3, 3, 3, 3 },
	new int[] { 0, 5, 5, 4, 4, 4, 4, 3, 3, 3 },
	new int[] { 0, 5, 5, 5, 4, 4, 4, 4, 3, 3 },
	new int[] { 0, 5 ,5, 5, 5, 4, 4, 4, 4, 3 }
	// could know no limits
    };

    private final static int[] noBonus = 
	new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

    /**
     * compute the bonus slots for the 'magic' ability of the character 
     * (for a wizard, the intelligence, for a cleric, the wisdom, ...)
     */
    public static int[] computeBonusSlots (int abilityScore)
    {
	if (abilityScore < 10) return noBonus;

	int score = (abilityScore - 12) / 2;

	return abilityBonus[score];
    }

}
