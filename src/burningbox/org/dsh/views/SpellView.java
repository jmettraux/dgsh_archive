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
 * $Id: SpellView.java,v 1.5 2002/08/05 07:05:28 jmettraux Exp $
 */

//
// SpellView.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// You own a dog, but you can only feed a cat.
//

package burningbox.org.dsh.views;

import burningbox.org.dsh.*;
import burningbox.org.dsh.entities.*;
import burningbox.org.dsh.entities.magic.Spell;
import burningbox.org.dsh.views.utils.*;


/**
 * Displays all the detail of a spell
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/08/05 07:05:28 $
 * <br>$Id: SpellView.java,v 1.5 2002/08/05 07:05:28 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class SpellView

    implements View

{

    //static org.apache.log4j.Logger log = org.apache.log4j.Logger
    //	.getInstance(SpellView.class.getName());

    //
    // FIELDS

    protected Spell spell = null;

    //
    // CONSTRUCTORS

    public SpellView (Spell spell)
    {
	this.spell = spell;
    }

    //
    // METHODS

    /*
     * PROCESS()
     */
    public StringBuffer process ()
    {
	StringBuffer sb = new StringBuffer();

	sb.append('\n');

	Line l = null;

	MultiTable table = new MultiTable(80);

	//
	// 1st line
	l = new Line();
	l.addCell(new Cell(20, Cell.ALIGN_LEFT, this.spell.getName()));
	l.addCell(new Cell(30, Cell.ALIGN_LEFT, displayType(this.spell)));
	l.addCell(new Cell(10, Cell.ALIGN_LEFT, displayComponents(this.spell)));
	table.addLine(l);

	//
	// 2nd line
	l = new Line();
	l.addCell(new Cell(25, Cell.ALIGN_LEFT, "ctime: "+this.spell.getCastingTime()));
	l.addCell(new Cell(40, Cell.ALIGN_LEFT, "range: "+this.spell.getRange()));
	table.addLine(l);

	//
	// 3rd line
	l = new Line();
	l.addCell(new Cell(45, Cell.ALIGN_LEFT, "duration: "+this.spell.getDuration()));
	l.addCell(new Cell(20, Cell.ALIGN_LEFT, "save: "+this.spell.getSavingThrow()));
	table.addLine(l);

	//
	// 4th line
	l = new Line();
	l.addCell(new Cell(30, Cell.ALIGN_LEFT, "resistance: "+this.spell.getSpellResistance()));
	l.addCell(new Cell(30, Cell.ALIGN_LEFT, "focus: "+this.spell.getFocus()));
	table.addLine(l);

	//
	// 5th line
	l = new Line();
	l.addCell(new Cell(50, Cell.ALIGN_LEFT, "mat: "+this.spell.getMaterialComponents()));
	l.addCell(new Cell(22, Cell.ALIGN_LEFT, "trig: "+this.spell.getTriggerName()));
	table.addLine(l);

	//
	// 6th line
	l = new Line();
	l.addCell(new Cell(76, Cell.ALIGN_LEFT, Utils.multiLineFormat(this.spell.getLongDescription(), 76)));
	table.addLine(l);

	sb.append(table.render());

	sb.append('\n');

	return sb;
    }

    //
    // some static methods available for other views

    public static StringBuffer displayType (Spell s)
    {
	StringBuffer sb = new StringBuffer();

	sb.append(s.getSchool());

	if (s.getSubSchool() != null)
	{
	    sb.append("\n(");
	    sb.append(s.getSubSchool());
	    sb.append(")");
	}
	if (s.getType() != null)
	{
	    sb.append("\n[");
	    sb.append(s.getType());
	    sb.append("]");
	}

	return sb;
    }

    public static StringBuffer displayComponents (Spell s)
    {
	StringBuffer sb = new StringBuffer();

	java.util.Iterator it = s.getComponents().iterator();
	while (it.hasNext())
	{
	    String c = (String)it.next();
	    sb.append(c);

	    if (it.hasNext()) sb.append(", ");
	}
	return sb;
    }

    public static StringBuffer displayLevel (Spell s)
    {
	StringBuffer sb = new StringBuffer();

	java.util.Iterator it = s.getClasses().iterator();
	while (it.hasNext())
	{
	    ClassLevel cl = (ClassLevel)it.next();

	    sb.append(cl.getClassName());
	    sb.append(' ');
	    sb.append(cl.getLevelReached());

	    if (it.hasNext())
		sb.append('\n');
	}

	return sb;
    }

}
