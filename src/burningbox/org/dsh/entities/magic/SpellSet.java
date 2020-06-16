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
 * $Id: SpellSet.java,v 1.3 2002/08/27 12:28:17 jmettraux Exp $
 */

package burningbox.org.dsh.entities.magic;

import burningbox.org.dsh.entities.DataSets;


/**
 * Spells ordered by class and level
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/08/27 12:28:17 $
 * <br>$Id: SpellSet.java,v 1.3 2002/08/27 12:28:17 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class SpellSet 
{

    private final static int MAX_SPELL_LEVEL = 30;

    //
    // FIELDS

    String className = null;
    java.util.Map levelMap = new java.util.HashMap();

    //
    // CONSTRUCTORS

    public SpellSet (String className)
    {
        this.className = className;
    }

    public SpellSet ()
    {
    }

    //
    // BEAN METHODS

    public String getClassName () { return this.className; }
    public java.util.Map getLevelMap () { return this.levelMap; }

    public void setClassName (String s) { this.className = s; }
    public void setLevelMap (java.util.Map m) { this.levelMap = m; }

    //
    // METHODS

    public void addAll (SpellSet set)
    {
	java.util.Iterator levelIt = set.levelMap.keySet().iterator();
	while (levelIt.hasNext())
	{
	    Object level = levelIt.next();
	    java.util.List source = (java.util.List)set.levelMap.get(level);
	    java.util.List target = (java.util.List)this.levelMap.get(level);
	    target.addAll(source);
	}
    }

    public void addSpell (Spell spell)
    {
        int level = spell.getLevel(this.className);
        java.util.List list = (java.util.List)getSpells(level);
        if (list == null)
        {
	    list = new java.util.ArrayList();
	    this.levelMap.put(new Integer(level), list);
        }
        list.add(spell.getName().trim().toLowerCase());
    }

    public void removeSpell (String spellName)
    {
	spellName = spellName.trim().toLowerCase();

        for (int level=0 ; level < MAX_SPELL_LEVEL ; level++)
        {
	    java.util.List list = 
		(java.util.List)this.levelMap.get(new Integer(level));

	    if (list == null) continue;

	    if (list.contains(spellName))
	    {
		list.remove(spellName);
		return;
	    }
        }
    }

    public boolean contains (Spell spell)
    {
	String spellName = spell.getName().trim().toLowerCase();

        java.util.Iterator it = this.levelMap.values().iterator();
        while (it.hasNext())
        {
	    java.util.List list = (java.util.List)it.next();

	    if (list.contains(spellName)) return true;
        }

        return false;
    }

    public java.util.List getSpells (int level)
    {
        return (java.util.List)this.levelMap.get(new Integer(level));
    }

    public Spell findSpell (String filter)
    {
        for (int level=0; level <= MAX_SPELL_LEVEL; level++)
        {
	    java.util.List list = 
		(java.util.List)this.levelMap.get(new Integer(level));

	    if (list != null)
	    {
		java.util.Iterator sit = list.iterator();
		while (sit.hasNext())
		{
		    String spellName = (String)sit.next();

		    if (spellName.matches(filter))
			return (Spell)DataSets.getSpells().get(spellName);
		}
	    }
        }
        return null;
    }

    public java.util.Iterator iterator ()
    {
        java.util.List result = new java.util.ArrayList();

        for (int level=0 ; level < MAX_SPELL_LEVEL ; level++)
        {
	    java.util.List list = 
		(java.util.List)this.levelMap.get(new Integer(level));

	    if (list == null) continue;

	    result.addAll(list);
        }

        return result.iterator();
    }
}
