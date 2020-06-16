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
 * $Id: InitiativeTable.java,v 1.9 2002/08/09 08:18:16 jmettraux Exp $
 */

//
// InitiativeTable.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// You may get an opportunity for advancement today.  Watch it!
// 

package burningbox.org.dsh.entities;

import burningbox.org.dsh.CombatSession;


/**
 * A table for tracking who or what is going to play next
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/08/09 08:18:16 $
 * <br>$Id: InitiativeTable.java,v 1.9 2002/08/09 08:18:16 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class InitiativeTable
{

    static org.apache.log4j.Logger log =
	org.apache.log4j.Logger.getLogger("dgsh.entities.InitiativeTable");

    //
    // INNER CLASSES

    class InitiativeComparator
	implements java.util.Comparator
    {
	public InitiativeComparator ()
	{
	}

	public int compare (Object o1, Object o2)
	{
	    if ( ! (o1 instanceof WithInitiative &&
		    o2 instanceof WithInitiative))
	    {
		throw new ClassCastException
		    ("Found something that cannot get an initiative.");
	    }

	    WithInitiative wi1 = (WithInitiative)o1;
	    WithInitiative wi2 = (WithInitiative)o2;

	    int i1 = wi1.getCurrentInitiative();
	    int i2 = wi2.getCurrentInitiative();

	    if (i1 == i2) 
		return (wi2.computeDexterity() - wi1.computeDexterity());

	    return i2 - i1;
	}

	public boolean equals (Object o)
	{
	    return false;
	}
    }

    //
    // FIELDS

    protected java.util.List table = null;

    protected WithInitiative current = null;

    //
    // CONSTRUCTORS

    public InitiativeTable ()
    {
	this.table = new java.util.ArrayList(20);
    }

    //
    // METHODS

    private void flushAttackOfOpportunities (WithInitiative wi)
    {
	if (wi instanceof Being)
	    ((Being)wi).flushAttackOfOpportunities();
    }

    public void add (WithInitiative wi)
    {
	flushAttackOfOpportunities(wi);

	this.table.add(wi);
	sort();
    }

    public void addNow (WithInitiative wi)
    {
	flushAttackOfOpportunities(wi);

	int index = this.table.indexOf(this.current);
	index++;
	wi.setCurrentInitiative(this.current.getCurrentInitiative());
	this.table.add(index, wi);
    }

    public void remove (WithInitiative wi)
    {
	if (wi == this.current)
	{
	    next();
	}
	this.table.remove(wi);
    }

    public void sort ()
    {
	java.util.Collections
	    .sort(this.table, new InitiativeComparator());
    }

    public int size ()
    {
	return this.table.size();
    }

    protected WithInitiative inc (int increment)
    {
	int index = this.table.indexOf(this.current);

	index += increment;

	if (index >= this.table.size())
	{
	    index = 0;

	    // increase CombatSession.roundElapsed
	    CombatSession.getInstance().nextRound();
	}
	else if (index < 0)
	{
	    index = this.table.size()-1;

	    // decrease CombatSession.roundElapsed
	    CombatSession.getInstance().previousRound();
	}

	this.current = (WithInitiative)this.table.get(index);

	//
	// is the current an outdated effect ?
	
	if (this.current instanceof Effect)
	{
	    Effect fx = (Effect)this.current;

	    int roundsToGo = fx.incrementRoundsToGo(-increment);

	    log.debug("'"+fx.getName()+"' roundsToGo = "+fx.getRoundsToGo());

	    if (roundsToGo == 0)
		Effect.removeEffect(fx);
	}

	return this.current;
    }

    public WithInitiative next ()
    {
	return inc(1);
    }

    public WithInitiative previous ()
    {
	return inc(-1);
    }

    public WithInitiative current ()
    {
	return this.current;
    }

    public int getCurrentInitiative ()
    {
	return this.current.getCurrentInitiative();
    }

    public void setInitiative 
	(String belligerentOrEffectName, int newInitiativeScore)
    {
	java.util.Iterator it = this.table.iterator();
	while (it.hasNext())
	{
	    WithInitiative wi = (WithInitiative)it.next();
	    if (wi.getName().equals(belligerentOrEffectName))
	    {
		wi.setCurrentInitiative(newInitiativeScore);
		return;
	    }
	}
    }

    public boolean isReady ()
    {
	return (this.table != null && this.table.size() > 0);
    }

    /**
     * at the end of a fight sets the initiative of all table 
     * to -10
     */
    public void release ()
    {
	flushAttackOfOpportunities();

	//
	// remove the initiative table !
	java.util.List oldTable = this.table;
	this.table = null;
	// so effects won't get added
	//

	java.util.Iterator it = oldTable.iterator();
	while (it.hasNext())
	{
	    WithInitiative wi = (WithInitiative)it.next();
	    wi.setCurrentInitiative(-10);

	    if (wi instanceof Effect)
	    {
		((Effect)wi).end();
	    }
	}
    }

    public void flushAttackOfOpportunities ()
    {
	java.util.Iterator it = this.table.iterator();
	while (it.hasNext())
	{
	    Object o = it.next();

	    if (o instanceof Being)
		((Being)o).flushAttackOfOpportunities();
	}
    }

    public java.util.Iterator iterator ()
    {
	return this.table.iterator();
    }

}
