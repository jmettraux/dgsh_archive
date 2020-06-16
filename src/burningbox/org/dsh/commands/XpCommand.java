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
 * $Id: XpCommand.java,v 1.3 2002/07/22 05:46:52 jmettraux Exp $
 */

//
// XpCommand.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

package burningbox.org.dsh.commands;

import burningbox.org.dsh.Term;
import burningbox.org.dsh.GameSession;
import burningbox.org.dsh.DshException;
import burningbox.org.dsh.CombatSession;
import burningbox.org.dsh.entities.*;


/**
 * Adds or remove experience points to a character or to the whole party.
 * Dgsh doesn't automatically handle level changes.
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/07/22 05:46:52 $
 * <br>$Id: XpCommand.java,v 1.3 2002/07/22 05:46:52 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class XpCommand

    extends 
        UndoableAbstractCommand

    implements
        RegularCommand,
	CombatCommand

{

    static org.apache.log4j.Logger log =
        org.apache.log4j.Logger.getLogger(XpCommand.class.getName());

    //
    // FIELDS

    protected int xp = 0;
    protected java.util.List targets = null;
    protected String undoInfo = null;
    
    //
    // CONSTRUCTORS

    public XpCommand ()
    {
    }

    //
    // METHODS

    private void addXp (int count)
	throws DshException
    {
	int targetCount = 0;
	java.util.Iterator targetIterator = null;
	
	if (this.targets == null)
	{
	    BeanSet party = GameSession.getInstance().getParty();
	    targetCount = party.size();
	    targetIterator = party.iterator();
	}
	else
	{
	    targetCount = this.targets.size();
	    targetIterator = this.targets.iterator();
	}

	if (targetCount == 0)
	{
	    throw new DshException("No targets for xp reward");
	}

	java.util.List foundTargets = new java.util.ArrayList(10);

	count = count / targetCount;

	while (targetIterator.hasNext())
	{
	    burningbox.org.dsh.entities.Character target = null;
	    Object oTarget = targetIterator.next();

	    if (oTarget instanceof String)
	    {
		target = GameSession.getInstance()
		    .findCharacter((String)oTarget);
	    }
	    else if  (oTarget instanceof burningbox.org.dsh.entities.Character)
	    {
		target = (burningbox.org.dsh.entities.Character)oTarget;
	    }

	    if (target != null)
	    {
		foundTargets.add(target);

		target.setExperiencePoints
		    (target.getExperiencePoints() + count);
	    }
	}

	java.util.Iterator it = foundTargets.iterator();
	while (it.hasNext())
	{
	    burningbox.org.dsh.entities.Character target =
		(burningbox.org.dsh.entities.Character)it.next();

	    Term.echo(target.getName()+" receives "+count+" XP");
	    Term.echo("  --> "+target.getExperiencePoints()+" XP\n");
	}

	this.targets = foundTargets;
    }

    //
    // METHODS FROM UndoableAbstractCommand
    
    public boolean execute ()
    {
	if (noMoreTokens()) return false;
	try
	{
	    this.xp = Integer.parseInt(tokenizer.nextToken());
	}
	catch (NumberFormatException nfe)
	{
	    Term.echo(getSyntax()+"\n");
	    return false;
	}

	this.targets = new java.util.ArrayList(5);
	while (this.tokenizer.hasMoreTokens())
	{
	    this.targets.add(this.tokenizer.nextToken());
	}

	if (this.targets.size() < 1) this.targets = null;

	try
	{
	    addXp(this.xp);
	}
	catch (DshException de)
	{
	    Term.echo("Failed : "+de.getMessage()+"\n");
	    return false;
	}

	return true;
    }

    public String getUndoInfo ()
    {
	if (undoInfo != null) return undoInfo;

	StringBuffer sb = new StringBuffer();
	sb.append(this.xp);
	sb.append(" given to ");
	if (this.targets == null)
	{
	    sb.append("the whole party.");
	} 
	else
	{
	    java.util.Iterator it = this.targets.iterator();
	    while (it.hasNext())
	    {
		sb.append((String)it.next());
		if (it.hasNext()) sb.append(", ");
	    }
	}
	undoInfo = sb.toString();
	return undoInfo;
    }

    public void undo ()
    {
	try
	{
	    addXp(-this.xp);
	}
	catch (DshException de)
	{
	    Term.echo("Failed : "+de.getMessage()+"\n");
	}
    }

    public String getSyntax ()
    {
	return
	    "xp {count} [target]*";
    }

    public String getHelp ()
    {
	return
	    "Adds or remove experience points to any number of targets. "+
	    "if no targets are specified, the points are distributed among "+
	    "the whole party.";
    }

}
