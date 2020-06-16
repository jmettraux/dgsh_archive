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
 * $Id: SpellCachedSet.java,v 1.3 2002/08/27 12:28:17 jmettraux Exp $
 */

//
// SpellCachedSet.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@yahoo.com)
//

package burningbox.org.dsh.entities.magic;

import burningbox.org.dsh.Utils;
import burningbox.org.dsh.entities.CachedSet;
import burningbox.org.dsh.entities.ClassLevel;


/**
 * All the spells, cached ...
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/08/27 12:28:17 $
 * <br>$Id: SpellCachedSet.java,v 1.3 2002/08/27 12:28:17 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class SpellCachedSet

    extends CachedSet

{

    static org.apache.log4j.Logger log =
	org.apache.log4j.Logger.getLogger("dgsh.entities.SpellCachedSet");

    protected final static String FILE_NAME = "_CachedSpells.xml";

    //
    // FIELDS

    protected java.util.Map spellsByClass = new java.util.HashMap();

    //
    // CONSTRUCTORS

    public static SpellCachedSet buildSpellCachedSet 
	(String directory, int cacheSize)
    {
	java.io.File f = new java.io.File(directory+FILE_NAME);

	if (f.exists())
	{
	    SpellCachedSet cached = load(f);

	    if (cached != null) return cached;
	}

	SpellCachedSet result = new SpellCachedSet();
	result.setDirectory(directory);
	result.setCacheSize(cacheSize);
	result.init();

	return result;
    }

    public SpellCachedSet ()
    {
	super();
    }

    //
    // BEAN METHODS 
    //
    // (this class is XML serializable for speed purposes)

    public java.util.Map getSpellsByClass () { return this.spellsByClass; }

    public void setSpellsByClass (java.util.Map m) { this.spellsByClass = m; }

    //
    // METHODS

    public SpellSet getSpellsForClass (String className)
    {
	return (SpellSet)this.spellsByClass.get(className);
    }

    public SpellSet getSpellsForDomain (String domainName)
    {
	// ;-)
	return (SpellSet)this.spellsByClass.get(domainName);
    }

    public java.util.Iterator byClassIterator (String className)
    {
	return ((SpellSet)this.spellsByClass.get(className)).iterator();
    }

    protected void init ()
    {
	super.init();

	//java.util.Iterator it = this.iterator();
	java.util.Iterator it = super.iterator();
	while(it.hasNext())
	{
	    Spell spell = (Spell)it.next();

	    // 
	    // by class
	    
	    java.util.Iterator cit = spell.getClasses().iterator();
	    while (cit.hasNext())
	    {
		ClassLevel cLevel = (ClassLevel)cit.next();

		String className = cLevel.getClassName();

		SpellSet set = (SpellSet)this.spellsByClass.get(className);

		if (set == null)
		{
		    set = new SpellSet(className);
		    this.spellsByClass.put(className, set);
		}

		set.addSpell(spell);
	    }
	}

	// save self
	
	save();
    }

    protected static SpellCachedSet load (java.io.File file)
    {
	return (SpellCachedSet)Utils.xmlLoad(file.getPath());
    }

    protected void save ()
    {
	Utils.xmlSave(this, this.directory+FILE_NAME);
    }

}
