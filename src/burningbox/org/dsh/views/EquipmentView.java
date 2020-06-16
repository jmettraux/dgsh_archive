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
 * $Id: EquipmentView.java,v 1.7 2002/08/15 15:49:45 jmettraux Exp $
 */

//
// EquipmentView.java
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
import burningbox.org.dsh.entities.*;
import burningbox.org.dsh.entities.equipment.*;


/**
 * The view used by the eqlist and eq commands
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/08/15 15:49:45 $
 * <br>$Id: EquipmentView.java,v 1.7 2002/08/15 15:49:45 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class EquipmentView

    implements View

{

    //static org.apache.log4j.Logger log = org.apache.log4j.Logger
    //    .getInstance(EquipmentView.class.getName());

    //
    // FIELDS

    protected String filter = null;
    protected Object equipments = null;

    //
    // CONSTRUCTORS

    public EquipmentView (CachedSet equipments)
    {
	this.equipments = equipments;
    }

    public EquipmentView (Container container)
    {
	this.equipments = container;
    }

    //
    // METHODS

    public void setFilter (String filter)
    {
	this.filter = filter;
    }

    private String displayCharacteristics (EquipmentPiece ep)
    {
	StringBuffer sb = new StringBuffer();

	//
	// armor or weapon ?
	
	if (ep instanceof Armor)
	{
	    Armor a = (Armor)ep;
	    sb.append("AC ");
	    if (a.getArmorClassBonus() > -1)
		sb.append('+');
	    sb.append(a.getArmorClassBonus());
	    sb.append("  MaxDex +");
	    sb.append(a.getMaxDexModifier());
	    sb.append(" SplFail ");
	    sb.append(a.getSpellFailurePercent());
	    sb.append("%\nAP ");
	    sb.append(a.getArmorPenalty());
	    sb.append("  MSpd ");
	    sb.append(a.getBaseSpeedMedium());
	    sb.append("ft  SSpd ");
	    sb.append(a.getBaseSpeedSmall());
	    sb.append("ft");
	}
	else if (ep instanceof Weapon)
	{
	    Weapon w = (Weapon)ep;
	    if (w instanceof MeleeWeapon)
		sb.append("melee");
	    else
		sb.append("ranged");
	    sb.append("\n");
	    sb.append(w.getDamages());
	    sb.append("  ");
	    sb.append(w.getCritical());
	}
	
	//
	// misc modifiers
	
	if (ep.getModifiers() != null)
	{
	    java.util.Iterator it = ep.getModifiers().iterator();
	    while (it.hasNext())
	    {
		MiscModifier mm = (MiscModifier)it.next();
		sb.append(mm.getDefinition());
		sb.append("   ");
		if (mm.getModifier() > -1)
		    sb.append('+');
		sb.append(mm.getModifier());
		sb.append('\n');
	    }
	}

	return sb.toString();
    }

    private void browseEquipment (java.util.Iterator it, Table table)
    {
	while (it.hasNext())
	{
	    EquipmentPiece ep = (EquipmentPiece)it.next();

	    if (this.filter != null &&
		ep.getDescription() != null &&
		! ep.getDescription().toLowerCase().matches(this.filter))
	    {
		continue;
	    }
	
	    String[] data = new String[]
		{ 
		  ep.getName(), 
		  ep.getDescription(),
		  ""+ep.getWeight(),
		  displayCharacteristics(ep) 
		};

	    table.addLine(data);

	    if (ep instanceof Container &&
		this.equipments instanceof Container)
	    {
		java.util.Iterator itt = ((Container)ep).iterator();
		browseEquipment(itt, table);
	    }
	}
    }

    public StringBuffer process ()
    {
	StringBuffer sb = new StringBuffer();

	//
	// headers
	
	String[] sizes = new String[] 
	    { "l10", "l23", "r4", "l30" };
	String[] titles = new String[]
	    { "Name", "Description", "weight", "misc" };

	Table table = new Table(sizes, titles);

	java.util.Iterator it = null;
	if (this.equipments instanceof CachedSet)
	    it = ((CachedSet)this.equipments).iterator();
	else if (this.equipments instanceof Container)
	    it = ((Container)this.equipments).iterator();

	if (it != null) browseEquipment(it, table);

	sb.append(table.render());

	return sb;
    }

}
