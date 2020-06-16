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
 * $Id: MonstersView.java,v 1.7 2002/08/12 13:53:32 jmettraux Exp $
 */

//
// MonstersView.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// Hain't we got all the fools in town on our side?  And hain't that a big
// enough majority in any town?
// 		-- Mark Twain, "Huckleberry Finn"
// 

package burningbox.org.dsh.views;

import burningbox.org.dsh.*;
import burningbox.org.dsh.views.*;
import burningbox.org.dsh.entities.*;
import burningbox.org.dsh.entities.equipment.*;


/**
 * The view for showing all the beasts sleeping in the template list
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/08/12 13:53:32 $
 * <br>$Id: MonstersView.java,v 1.7 2002/08/12 13:53:32 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class MonstersView

    extends FilteredView

{

    //static org.apache.log4j.Logger log = org.apache.log4j.Logger
    //    .getInstance(MonstersView.class.getName());

    //
    // FIELDS

    protected CachedSet monsterTemplates = null;

    //
    // CONSTRUCTORS

    public MonstersView (CachedSet monsterTemplates)
    {
	this.monsterTemplates = monsterTemplates;
    }

    //
    // METHODS

    private String displayAttacks (MonsterTemplate mt)
    {
	if (mt.getAttacks().size() < 1) return "";

	StringBuffer sb = new StringBuffer();

	java.util.Iterator it = mt.getAttacks().iterator();
	while (it.hasNext())
	{
	    Attack a = (Attack)it.next();

	    sb.append(Utils.format(a.getName(), 14));
	    sb.append(Utils.formatModifiers(a.getAttackModifier(), 3));
	    sb.append(' ');
	    sb.append(Utils.format(a.getDamage(), 7));

	    if (it.hasNext())
		sb.append('\n');
	}

	return sb.toString();
    }

    private String displaySpecial (MonsterTemplate mt)
    {
	if (mt.getSpecials().size() < 1) return "";

	StringBuffer sb = new StringBuffer();

	java.util.Iterator it = mt.getSpecials().values().iterator();
	while (it.hasNext())
	{
	    Special s = (Special)it.next();

	    sb.append(Utils.format(s.getName(), 13));
	    sb.append(' ');
	    switch (s.getType())
	    {
		case Special.TYPE_EX :
		    sb.append("(EX)"); break;
		case Special.TYPE_SU :
		    sb.append("(SU)"); break;
		case Special.TYPE_SP :
		    sb.append("(SP)");
	    }

	    if (it.hasNext())
		sb.append('\n');
	}

	return sb.toString();
    }

    private String[] displayMonster (MonsterTemplate mt)
    {
	String[] data = new String[]
	    { 
	      Utils.multiLineFormat(mt.getName(), 10),
	      "hp "+mt.getPotentialHitPoints()+"\n"+
	      "in "+Utils.formatModifier(mt.getInitModifier(), 3)+"\n"+
	      "AC "+Utils.format(""+mt.getArmorClass(), 2, true),
	      displayAttacks(mt),
	      displaySpecial(mt)
	    };

	return data;
    }

    public StringBuffer process ()
    {
	StringBuffer sb = new StringBuffer();
	
	String[] sizes = new String[] 
	    { "l10",  "l9", "l30",   "l18" };
	String[] titles = new String[]
	    { "Name", "", "attacks", "special" };

	Table table = new Table(sizes, titles);

	java.util.Iterator it = this.monsterTemplates.iterator();
	while (it.hasNext())
	{
	    MonsterTemplate mt = (MonsterTemplate)it.next();

	    if (matchesFilter(mt))
		table.addLine(displayMonster(mt));
	}

	sb.append(table.render());

	return sb;
    }

}
