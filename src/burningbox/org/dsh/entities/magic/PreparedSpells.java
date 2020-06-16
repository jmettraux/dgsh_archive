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
 * $Id: PreparedSpells.java,v 1.12 2002/08/27 06:53:37 jmettraux Exp $
 */

//
// PreparedSpells.java
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
import burningbox.org.dsh.entities.DataSets;
import burningbox.org.dsh.entities.CharacterClass;


/**
 * Prepared spells, class by class
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/08/27 06:53:37 $
 * <br>$Id: PreparedSpells.java,v 1.12 2002/08/27 06:53:37 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class PreparedSpells
{

    //
    // CONSTANTS and the like

    static org.apache.log4j.Logger log =
	org.apache.log4j.Logger.getLogger("dgsh.PreparedSpells");

    //
    // FIELDS

    protected transient burningbox.org.dsh.entities.Character spellCaster = null;
    protected java.util.Map preparedSpells = new java.util.HashMap();

    //
    // CONSTRUCTORS

    public PreparedSpells ()
    {
    }

    public PreparedSpells (burningbox.org.dsh.entities.Character spellCaster)
    {
	this.spellCaster = spellCaster;
    }

    //
    // BEAN METHODS

    public java.util.Map getPreparedSpells () { return this.preparedSpells; }

    public void setPreparedSpells (java.util.Map m) { this.preparedSpells = m; }

    //
    // METHODS

    public java.util.List getSpells (String className)
    {
	return (java.util.List)this.preparedSpells.get(className);
    }

    public java.util.Iterator iterator (String className)
    {
	java.util.List list = getSpells(className);

	if (list == null) return null;

	return list.iterator();
    }

    public void setSpellCaster (burningbox.org.dsh.entities.Character sc) 
    { 
	this.spellCaster = sc;
    }

    public SpellSlots unpreparedSlots (CharacterClass cClass)
    {
	int[] credit = this.spellCaster.getSpellSlots()
	    .getSlots(cClass.getName());

	int[] preparedSlots = new int[credit.length];
	for (int i=0; i<credit.length; i++)
	{
	    preparedSlots[i] = spellCount(cClass.getName(), i);
	}

	int[] freeSlots = 
	    Utils.addArrays(credit, Utils.negateArray(preparedSlots));

	SpellSlots result = new SpellSlots();

	result.putSlots(cClass.getName(), freeSlots);

	//
	// domain slots ?
	
	if ( ! cClass.isUsingDomainSpells()) return result;

	credit = this.spellCaster.getSpellSlots()
	    .getSlots(Definitions.DOMAINS+"_"+cClass.getName());

	preparedSlots = new int[credit.length];
	for (int i=0; i<credit.length; i++)
	{
	    preparedSlots[i] = domainSpellCount(cClass.getName(), i);
	}

	int[] freeDomainSlots = 
	    Utils.addArrays(credit, Utils.negateArray(preparedSlots));

	result.putSlots
	    (Definitions.DOMAINS+"_"+cClass.getName(), freeDomainSlots);

	return result;
    }

    public void flush (String spellCasterClassName)
    {
	this.preparedSpells
	    .put(spellCasterClassName, new java.util.ArrayList(10));
    }

    private int domainSpellCount (String className, int level)
    {
	java.util.List spells = 
	    (java.util.List)this.preparedSpells.get(className);

	if (spells == null)
	    return 0;

	java.util.Iterator it = spells.iterator();
	while (it.hasNext())
	{
	    Object o = it.next();

	    if (o instanceof DomainSpell)
	    {
		if (level == ((DomainSpell)o).getLevel()) return 1;
	    }
	}

	return 0;
    }

    /**
     * Returns true if the spell is prepared and is a prepared as a 
     * domain spell.
     */
    public boolean isADomainSpell (String className, String spellName)
    {
	java.util.List spells = 
	    (java.util.List)this.preparedSpells.get(className);

	if (spells == null) return false;

	java.util.Iterator it = spells.iterator();
	while (it.hasNext())
	{
	    Object o = it.next();

	    if (o instanceof DomainSpell)
	    {
		if (((DomainSpell)o).getSpellName().equals(spellName))
		    return true;
	    }
	}

	return false;
    }

    /**
     * Returns null if the spell is not a domain spell
     */
    public String getDomain (String className, String spellName)
    {
	java.util.List spells = 
	    (java.util.List)this.preparedSpells.get(className);

	if (spells == null) return null;

	java.util.Iterator it = spells.iterator();
	while (it.hasNext())
	{
	    Object o = it.next();

	    if (o instanceof DomainSpell)
	    {
		DomainSpell ds = (DomainSpell)o;

		if (ds.getSpellName().equals(spellName))
		    return ds.getDomainName();
	    }
	}

	return null;
    }

    private int spellCount (String className, int level)
    {
	java.util.List spells = 
	    (java.util.List)this.preparedSpells.get(className);

	if (spells == null)
	    return 0;

	int result = 0;

	java.util.Iterator it = spells.iterator();
	while (it.hasNext())
	{
	    Object o = it.next();

	    String spellName = null;

	    if (o instanceof DomainSpell)
	    {
		continue; // ignore
	    }
	    else
	    {
		spellName = (String)o;
	    }

	    //System.out.println("Requesting >"+spellName+"< for counting");

	    Spell spell = DataSets.findSpell(spellName);

	    if (spell.getLevel(className) == level)
		result++;
	}

	return result;
    }

    public boolean isSpellReady (String className, String spellName)
    {
	java.util.List spells = 
	    (java.util.List)this.preparedSpells.get(className);

	if (spells == null) return false;

	//spellName = spellName.toLowerCase().replace('_', ' ');

	java.util.Iterator it = spells.iterator();
	while (it.hasNext())
	{
	    Object o = it.next();

	    String sName = null;

	    if (o instanceof DomainSpell)
	    {
		DomainSpell ds = (DomainSpell)o;

		CharacterClass cc = DataSets.findCharacterClass(className);

		if (cc.isUsingDomainSpells() && 
		    spellName.equals(ds.getSpellName()))
		{
		    return true;
		}
		else
		{
		    continue;
		}
	    }
	    else
	    {
		sName = (String)o;
	    }

	    sName = sName.toLowerCase();

	    if (sName.equals(spellName)) return true;
	}

	return false;
    }

    public void removeSpell 
	(CharacterClass cClass, String spellName, boolean domainSpell)
    {
	java.util.List spells = 
	    (java.util.List)this.preparedSpells.get(cClass.getName());

	if (spells == null) return;

	spellName = spellName.toLowerCase();

	java.util.Iterator it = spells.iterator();
	while (it.hasNext())
	{
	    Object o = it.next();

	    String sName = null;

	    if (o instanceof DomainSpell)
	    {
		if ( ! domainSpell) continue;

		DomainSpell ds = (DomainSpell)o;

		//log.debug("spellName is >"+spellName+"<");
		//log.debug("ds.getSpellName is >"+ds.getSpellName()+"<");

		if (cClass.isUsingDomainSpells() && 
		    spellName.equals(ds.getSpellName()))
		{
		    it.remove();
		    break;
		}
		else
		{
		    continue;
		}
	    }
	    else
	    {
		if (domainSpell) continue;

		sName = (String)o;
	    }

	    sName = sName.toLowerCase();

	    if (sName.equals(spellName))
	    {
		it.remove();
		break;
	    }
	}
    }

    public void addPreparedDomainSpell 
	(String domainName, 
	 String className, 
	 Spell spell)
    throws 
	DshException
    {

	//
	// determine spell level for given domain name
	
	int level = spell.getDomainLevel(domainName);

	//
	// is the slot free ?
	
	int i = domainSpellCount(className, level);

	if (i >= 1)
	{
	    throw new DshException
		("Domain spell for level "+level+" is already prepared");
	}

	//
	// prepare spell
	
	java.util.List spells = 
	    (java.util.List)this.preparedSpells.get(className);

	if (spells == null)
	{
	    spells = new java.util.ArrayList(10);
	    this.preparedSpells.put(className, spells);
	}

	spells.add(new DomainSpell
		(level, domainName, className, spell.getName().toLowerCase()));

    }

    public void addPreparedSpell (String className, String spellName)
	throws DshException
    {
	//System.out.println("Requesting spell >"+spellName+"<");

	Spell spell = DataSets.findSpell(spellName);

	if (spell == null)
	{
	    throw new DshException("Unknown spell '"+spellName+"'");
	}

	addPreparedSpell(className, spell);
    }

    public void addPreparedSpell (String className, Spell spell)
	throws DshException
    {

	//
	// check the slots !
	
	/*
	if (this.spellCaster == null)
	{
	    throw new DshException
		("No spellCaster has been linked "+
		 "to the PreparedSpells instance");
	}
	*/

	int level = spell.getLevel(className);

	//System.out.println("Spell level = "+level);
	
	if (level < 0)
	{
	    throw new DshException 
		("Spell '"+spell.getName()+
		 "' cannot be casted by '"+className+"'");
	}

	int[] availableSlots = 
	    this.spellCaster.getSpellSlots().getSlots(className);

	if (availableSlots == null)
	{
	    throw new DshException
		("SpellCaster not allowed to use '"+spell.getName()+
		 "' as a "+className);
	}

	int count = spellCount(className, level);

	if (count == availableSlots[level])
	{
	    throw new DshException
		("SpellCaster has already reached its max for "+
		 className + level);
	}

	if (count > availableSlots[level])
	{
	    throw new DshException
		("What happened ? "+
		 "SpellCaster has prepared too much spells for "+
		 className + level);
	}

	// we don't consume spell slots here, they get consumed
	// at casting time
	
	//
	// ok ? so add
	
	java.util.List spells = 
	    (java.util.List)this.preparedSpells.get(className);

	if (spells == null)
	{
	    spells = new java.util.ArrayList(10);
	    this.preparedSpells.put(className, spells);
	}

	spells.add(spell.getName().toLowerCase());

    }

}
