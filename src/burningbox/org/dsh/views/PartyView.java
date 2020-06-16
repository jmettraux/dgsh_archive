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
 * $Id: PartyView.java,v 1.7 2002/07/29 06:51:10 jmettraux Exp $
 */

//
// PartyView.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// Wagner's music is better than it sounds.
// 		-- Mark Twain
// 

package burningbox.org.dsh.views;

import burningbox.org.dsh.GameSession;
import burningbox.org.dsh.CombatSession;
import burningbox.org.dsh.entities.Named;
import burningbox.org.dsh.entities.Monster;
import burningbox.org.dsh.entities.BeanSet;
import burningbox.org.dsh.entities.ClassLevel;


/**
 * The view used by the party command
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/07/29 06:51:10 $
 * <br>$Id: PartyView.java,v 1.7 2002/07/29 06:51:10 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class PartyView

    implements View

{

    static org.apache.log4j.Logger log = org.apache.log4j.Logger
	.getLogger(PartyView.class.getName());

    //
    // FIELDS

    protected BeanSet party = null;

    //
    // CONSTRUCTORS

    public PartyView (BeanSet partyOrOpponents)
    {
	this.party = partyOrOpponents;
    }

    //
    // METHODS

    public StringBuffer process ()
    {
	StringBuffer sb = new StringBuffer();

	//
	// headers
	
	String[] sizes = new String[] 
	    { "l15", "l8", "l10", "l7", "l7", "r3", "r3" };
	String[] titles = new String[]
	    { "Name", "Race", "Classes", "player", "HP", "AC", "ini" };

	Table table = new Table(sizes, titles);

	java.util.Iterator it = party.iterator();
	while (it.hasNext())
	{
	    Named named = (Named)it.next();
	    
	    if (named instanceof burningbox.org.dsh.entities.Character)
	    {
		burningbox.org.dsh.entities.Character c = 
		    (burningbox.org.dsh.entities.Character)named;

		String initiative = "   ";
		if (CombatSession.isCombatGoingOn() &&
		    CombatSession.getInstance().getInstance().getInitiativeTable() != null)
		{
		    initiative = ""+c.getCurrentInitiative();
		}

		String[] data = new String[]
		    { c.getName(), 
		      c.getRaceName(), 
		      Utils.displayClasses(c), 
		      c.getPlayer(), 
		      Utils.hpFormat(c.getCurrentHitPoints())+"/"+
			  Utils.hpFormat(c.getHitPoints()), 
		      ""+c.computeArmorClass(),
		      initiative };

		table.addLine(data);
	    }
	    else
	    {
		Monster m = (Monster)named;

		String initiative = "   ";
		if (CombatSession.isCombatGoingOn() && 
		    CombatSession.getInstance().getInstance().getInitiativeTable() != null)
		{
		    initiative = ""+m.getCurrentInitiative();
		}

		String[] data = new String[]
		    { m.getName(), 
		      m.getRaceName(), 
		      "",
		      "",
		      Utils.hpFormat(m.getCurrentHitPoints())+"/"+
			  Utils.hpFormat(m.getHitPoints()), 
		      ""+m.computeArmorClass(),
		      initiative };

		table.addLine(data);

	    }
	}
	sb.append(table.render());

	return sb;
    }

}
