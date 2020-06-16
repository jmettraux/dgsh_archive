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
 * $Id: SpellsView.java,v 1.13 2002/08/28 08:56:56 jmettraux Exp $
 */

//
// SpellsView.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

package burningbox.org.dsh.views;

import burningbox.org.dsh.Definitions;
import burningbox.org.dsh.entities.DataSets;
import burningbox.org.dsh.entities.magic.Spell;
import burningbox.org.dsh.entities.magic.Spells;
import burningbox.org.dsh.entities.magic.DomainSpell;
import burningbox.org.dsh.entities.magic.SpellCachedSet;
import burningbox.org.dsh.entities.magic.PreparedSpells;


/**
 * The view for showing all the spells your mages could cast if they were
 * strong enough
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/08/28 08:56:56 $
 * <br>$Id: SpellsView.java,v 1.13 2002/08/28 08:56:56 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class SpellsView

    extends FilteredView

{

    static org.apache.log4j.Logger log = org.apache.log4j.Logger
        .getLogger("dgsh.SpellsView");

    //
    // FIELDS

    protected Spells spells = null;
    protected java.util.Collection spellCollection = null;
    protected SpellCachedSet spellCachedSet = null;
    protected PreparedSpells preparedSpells = null;
    
    protected int level = -1;
    //protected String className = null;
    protected String classToDisplay = null;
    protected burningbox.org.dsh.entities.Character spellCaster = null;

    //
    // CONSTRUCTORS

    public SpellsView (Spells spells)
    {
	this.spells = spells;
    }

    public SpellsView (java.util.Collection spells)
    {
	this.spellCollection = spells;
    }

    public SpellsView (SpellCachedSet spells)
    {
	this.spellCachedSet = spells;
    }

    /**
     * An extended constructor for displaying spells
     * a character can prepare (class + domains)
     */
    public SpellsView 
	(burningbox.org.dsh.entities.Character spellCaster, 
	 String className, 
	 int level)
    {
	this.spells = spellCaster.getKnownSpells();
	this.spellCaster = spellCaster;
	this.classToDisplay = className;
	this.level = level;
    }

    public SpellsView (PreparedSpells spells, String className)
    {
	this.preparedSpells = spells;
	this.classToDisplay = className;
    }

    //
    // METHODS

    public void setClassToDisplay (String className)
    {
	this.classToDisplay = className;
    } 
    
    public void setClassLevel (int level, String className)
    {
	this.level = level;
	//this.className = className;
	this.classToDisplay = className;
    }

    protected boolean matchesFilter (Spell s)
    {
	if (s == null) log.debug("Spell is null !");

	if (this.filter != null)
	    return super.matchesFilter(s);

	//if (this.className == null)
	//    return true;

	//int level = s.getLevel(this.className);
	int level = s.getLevel(this.classToDisplay);

	if (level < 0) return false;

	if (this.level == -1) return true;

	return (level == this.level);
    }

    private StringBuffer displayClassLevel (Spell s)
    {
	return 
	    (new StringBuffer())
		.append(this.classToDisplay)
		.append(' ')
		.append(s.getLevel(this.classToDisplay));
    }

    /*
    private StringBuffer displayDomainLevel (String domainName, Spell s)
    {
	return 
	    (new StringBuffer())
		.append(domainName)
		.append(' ')
		.append(s.getLevel(domainName));
    }
    */

    private StringBuffer displayDomains (java.util.List domainNames, Spell s)
    {
	StringBuffer result = new StringBuffer();

	java.util.Iterator it = domainNames.iterator();
	while (it.hasNext())
	{
	    String domainName = (String)it.next();

	    if (s.isFromDomain(domainName))
	    {
		if (result.length() > 0) result.append('\n');

		result
		    .append(domainName)
		    .append(' ')
		    .append(s.getLevel(domainName));
	    }
	}

	return result;
    }

    private StringBuffer displayClassAndDomainLevel (Spell s)
    {
	if (this.classToDisplay == null)
	{
	    return SpellView.displayLevel(s);
	}

	StringBuffer result = displayClassLevel(s);

	if (this.spellCaster == null) return result;

	java.util.List domains = (java.util.List)this.spellCaster
	    .getAttribute(Definitions.DOMAINS+"_"+this.classToDisplay);

	if (domains == null) return result;

	//log.debug("'"+this.spellCaster.getName()+"' has domains.");

	StringBuffer domainOutput = displayDomains(domains, s);

	if (domainOutput.length() > 0)
	{
	    result
		.append('\n')
		.append(domainOutput);
	}

	return result;
    }

    /*
    private StringBuffer displayLevel (Spell s)
    {
	if (this.classToDisplay == null)
	    return SpellView.displayLevel(s);

	if (this.preparedSpells == null)
	{
	    return displayClassLevel(s);
	}

	String domainName = this.preparedSpells
	    .getDomain(this.classToDisplay, s.getName());

	if (domainName != null)
	{
	    return displayDomainLevel(domainName, s);
	}

	return displayClassLevel(s);
    }
    */

    private String[] displaySpell (Spell s, boolean domainSpell)
    {
	String spellName = s.getName();

	if (domainSpell) spellName += " (d)";

	String[] data = new String[]
	    { 
	      Utils.multiLineFormat(spellName, 24),
	      displayClassAndDomainLevel(s).toString(),
	      SpellView.displayType(s).toString(),
	      Utils.multiLineFormat(s.getDescription(), 22)
	    };

	return data;
    }

    private java.util.Iterator iterator ()
    {
	if (this.spells != null)
	{
	    if (this.classToDisplay == null)
		return this.spells.iterator();

	    return this.spells.byClassIterator(this.classToDisplay);
	}

	if (this.spellCollection != null)
	{
	    return this.spellCollection.iterator();
	}

	if (this.spellCachedSet != null)
	{
	    if (this.classToDisplay == null)
		return this.spellCachedSet.iterator();

	    return this.spellCachedSet.byClassIterator(this.classToDisplay);
	}

	if (this.preparedSpells != null)
	{
	    return this.preparedSpells.iterator(this.classToDisplay);
	}

	return null;
    }

    public StringBuffer process ()
    {
	StringBuffer sb = new StringBuffer();
	
	String[] sizes = new String[] 
	    { "l24",  "l6",  "l15",  "l22" };
	String[] titles = new String[]
	    { "name", "lvl", "type", "description" };

	Table table = new Table(sizes, titles);

	java.util.Iterator it = iterator();
	if (it == null) it = (new java.util.ArrayList(0)).iterator();
	while (it.hasNext())
	{
	    Object oSpell = it.next();
	    Spell spell = null;
	    boolean domainSpell = false;
	    if (oSpell instanceof String)
	    {
		spell = DataSets.findSpell((String)oSpell);
	    }
	    else if (oSpell instanceof DomainSpell)
	    {
		domainSpell = true;

		spell = DataSets
		    .findSpell(((DomainSpell)oSpell).getSpellName());
	    }
	    else
	    {
		spell = (Spell)oSpell;
	    }

	    if (matchesFilter(spell))
		table.addLine(displaySpell(spell, domainSpell));
	}

	sb.append(table.render());

	return sb;
    }

}
