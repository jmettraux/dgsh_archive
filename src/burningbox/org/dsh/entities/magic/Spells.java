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
 * $Id: Spells.java,v 1.13 2002/08/28 08:56:56 jmettraux Exp $
 */

//
// Spells.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@yahoo.com)
//

package burningbox.org.dsh.entities.magic;

import burningbox.org.dsh.Definitions;
import burningbox.org.dsh.DshException;
import burningbox.org.dsh.entities.BeanSet;
import burningbox.org.dsh.entities.DataSets;
import burningbox.org.dsh.entities.ClassLevel;
import burningbox.org.dsh.entities.CharacterClass;


/**
 * A container for all the spells, class by class and level by level.
 * This class is not meant to be XML serialized. It is used in the DataSets
 * singleton.
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/08/28 08:56:56 $
 * <br>$Id: Spells.java,v 1.13 2002/08/28 08:56:56 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class Spells
{

    org.apache.log4j.Logger log =
	org.apache.log4j.Logger.getLogger("dgsh.Spells");

    //
    // FIELDS

    protected burningbox.org.dsh.entities.Character spellCaster = null;

    protected java.util.Map spellsByName = new java.util.HashMap();
    protected java.util.Map spellsByClass = new java.util.HashMap();
    protected java.util.Map spellsByDomain = new java.util.HashMap();

    //
    // CONSTRUCTORS

    public Spells ()
    {
    }

    /**
     * Building the known spells for a character, either from its
     * spell list (spellbook) or from his whole class spell list.
     */
    public Spells (burningbox.org.dsh.entities.Character character)
    {
	this.spellCaster = character;

	java.util.Iterator it = character.getClasses().iterator();
	while (it.hasNext())
	{
	    ClassLevel cl = (ClassLevel)it.next();
	    CharacterClass cc = DataSets.findCharacterClass(cl.getClassName());

	    if (cc.usesSpellList())
		//
		// instantiate spells from character's spellbook/memories
	    {
		if (character.getSpellList() == null) continue;

		java.util.Iterator slit = 
		    character.getSpellList().classIterator(cl.getClassName());
		while (slit.hasNext())
		{
		    String spellName = (String)slit.next();
		    this.addSpell(spellName.toLowerCase(), cl.getClassName());
		}
	    }
	    else
		//
		// instantiate spells from all class spells
	    {
		SpellSet set = (SpellSet)DataSets.getSpells()
		    .getSpellsForClass(cl.getClassName());
		this.spellsByClass.put(cl.getClassName(), set);
	    }

	    //
	    // domain spells

	    if (cc.isUsingDomainSpells())
	    {
		java.util.List domains = (java.util.List)character
		    .getAttribute(Definitions.DOMAINS+"_"+cc.getName());

		if (domains != null)
		{
		    java.util.Iterator dit = domains.iterator();
		    while (dit.hasNext())
		    {
			String domainName = (String)dit.next();

			SpellSet set = (SpellSet)DataSets.getSpells()
			    .getSpellsForDomain(domainName);

			this.spellsByDomain
			    .put(cc.getName()+"_"+domainName, set);
		    }
		}
	    }
	}
    }

    //
    // METHODS

    /**
     * Returns the domain for a given spell
     */
    public String findDomain (String className, Spell spell)
    {
	java.util.Iterator it = this.spellsByDomain.keySet().iterator();
	while (it.hasNext())
	{
	    String key = (String)it.next();

	    if ( ! key.startsWith(className)) continue;

	    String domainName = key.substring(key.indexOf("_")+1);

	    SpellSet set = (SpellSet)this.spellsByDomain.get(key);

	    if (set.contains(spell)) return domainName;
	}

	return null;
    }
    
    public java.util.List getSpells (String className, int level)
    {
	SpellSet set = (SpellSet)this.spellsByClass.get(className);

	if (set == null) return null;

	return set.getSpells(level);
    }
    
    public java.util.List getSpells 
	(String className, String domainName, int level)
    {
	SpellSet set = (SpellSet)this.spellsByDomain
	    .get(className+"_"+domainName);

	if (set == null) return null;

	return set.getSpells(level);
    }

    public void addSpell (String spellName, String className)
    {
	Spell spell = DataSets.findSpell(spellName);
	addSpell(spell, className);
    }

    public void removeSpell (String spellName, String className)
    {
	SpellSet set = (SpellSet)this.spellsByClass.get(className);
	if (set != null) set.removeSpell(spellName);

	this.spellsByName.remove(spellName);

	this.spellCaster.getSpellList().removeSpell(className, spellName);
    }

    public void addSpell (Spell spell, String className)
    {
	//
	// by name

	this.spellsByName.put(spell.getName().toLowerCase(), spell);

	//
	// by class

	SpellSet set = (SpellSet)this.spellsByClass.get(className);

	if (set == null)
	{
	    set = new SpellSet(className);
	    this.spellsByClass.put(className, set);
	}

	set.addSpell(spell);

	//
	// by domain
	
	CharacterClass spellCasterClass = 
	    DataSets.findCharacterClass(className);

	if (spellCasterClass == null ||
	    ! spellCasterClass.isUsingDomainSpells())
	{
	    return;
	}

	java.util.List domains = (java.util.List)this.spellCaster
	    .getAttribute(Definitions.DOMAINS+"_"+spellCasterClass.getName());

	java.util.Iterator dit = domains.iterator();
	while (dit.hasNext())
	{
	    String domainName = (String)dit.next();
	    int level = spell.getDomainLevel(domainName);

	    if (level < 0) continue;

	    set = (SpellSet)this.spellsByDomain.get(className+"_"+domainName);

	    if (set == null)
	    {
		set = new SpellSet(className+"_"+domainName);
		this.spellsByDomain.put(className+"_"+domainName, set);
	    }

	    set.addSpell(spell);
	}
    }

    public void addSpell (Spell spell)
    {
	java.util.Iterator it = spell.getClasses().iterator();
	while (it.hasNext())
	{
	    ClassLevel cl = (ClassLevel)it.next();
	    addSpell(spell, cl.getClassName());
	}
    }

    public Spell findSpell (String className, String filter)
    {
	SpellSet set = (SpellSet)this.spellsByClass.get(className);

	return set.findSpell(filter);
    }

    public Spell findSpell (String spellName)
    {
	return (Spell)this.spellsByName.get(spellName.toLowerCase());
    }

    public String findClass (Spell spell)
    {
	java.util.Iterator it = this.spellsByClass.keySet().iterator();
	while (it.hasNext())
	{
	    String className = (String)it.next();
	    SpellSet set = (SpellSet)this.spellsByClass.get(className);

	    if (set.contains(spell)) return className;
	}
	return null;
    }

    public java.util.Iterator byClassIterator (String className)
    {
	CharacterClass cc = DataSets.findCharacterClass(className);

	if (cc == null ||  ! cc.isUsingDomainSpells())
	    return ((SpellSet)this.spellsByClass.get(className)).iterator();

	//
	// concat with domain spells

	SpellSet wholeSet = (SpellSet)this.spellsByClass.get(className);

	java.util.List domains = (java.util.List)this.spellCaster
	    .getAttribute(Definitions.DOMAINS+"_"+className);
	java.util.Iterator dit = domains.iterator();
	while (dit.hasNext())
	{
	    String domainName = 
		(String)dit.next();
	    SpellSet domainSet = 
		(SpellSet)this.spellsByDomain.get(className+"_"+domainName);
	    wholeSet.addAll(domainSet);
	}

	return wholeSet.iterator();
    }

    public java.util.Iterator classIterator ()
    {
	return this.spellsByClass.keySet().iterator();
    }

    public java.util.Iterator iterator ()
    {
	return this.spellsByName.values().iterator();
    }

    /**
     * allows a spellcaster to learn / copy a spell (in his spellbook)
     */
    public void learnSpell (String className, String spellName)
	throws DshException
    {

	if (this.spellCaster.computeLevelForClass(className) < 1)
	{
	    throw new DshException
		("'"+this.spellCaster.getName()+"' has no level of '"+
		 className+"'.");
	}

	CharacterClass cClass = DataSets.findCharacterClass(className);

	if ( ! cClass.usesSpellList())
	{
	    throw new DshException
		("Class '"+className+"' doesn't have to know spell "+
		 "or copy them in a spellbook.");
	}

	Spell spell = DataSets.findSpell(spellName);

	if (spell == null)
	{
	    throw new DshException
		("Unknown spell '"+spellName+"'.");
	}

	int level = spell.getLevel(className);

	if (level < 0)
	{
	    throw new DshException
		("Class '"+className+"' cannot cast '"+spellName+"'.");
	}

	//
	// OK
	
	this.addSpell(spell, className);

	//
	// add spell to spellList (which is saved to xml)
	
	this.spellCaster.getSpellList().addSpell(className, spellName);

    }

}
