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
 * $Id: HpCommand.java,v 1.11 2002/08/05 07:05:26 jmettraux Exp $
 */

//
// HpCommand.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

package burningbox.org.dsh.commands;

import burningbox.org.dsh.Term;
import burningbox.org.dsh.entities.*;
import burningbox.org.dsh.Definitions;
import burningbox.org.dsh.GameSession;
import burningbox.org.dsh.CombatSession;


/**
 * Adds or remove hit points to a monster or a character.
 * min is then -10 (death) and max is the max hit points.
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/08/05 07:05:26 $
 * <br>$Id: HpCommand.java,v 1.11 2002/08/05 07:05:26 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class HpCommand

    extends 
        UndoableAbstractCommand

    implements
        RegularCommand,
	CombatCommand

{

    static org.apache.log4j.Logger log =
        org.apache.log4j.Logger.getLogger("dgsh.HpCmd");

    //
    // FIELDS
    
    protected Being target = null;
    protected int hpCount = 0;
    protected boolean permanent = false;

    //
    // CONSTRUCTORS

    public HpCommand ()
    {
    }

    //
    // METHODS

    private boolean incrementHitPoints (int hpCount)
    {

	//
	// massive damage ?
	
	int massiveDamageThreshold = this.target.getSize()
	    .getMassiveDamageThreshold();

	if (-hpCount >= massiveDamageThreshold)
	{
	    Term.echo("\n  *** MASSIVE DAMAGE ***\n\n");
	}

	//
	// do the trick

	int newCount = 1;

	newCount = this.target.getCurrentHitPoints() + hpCount;
	if (newCount < -10)
	    newCount = -10;
	if (newCount > target.getHitPoints())
	    newCount = target.getHitPoints();
	this.target.setCurrentHitPoints(newCount);

	if (this.permanent)
	    //
	    // the ceiling is modified, even for non regenerating creatures.
	    // the ceiling blocks only during regeneration.
	{
	    Integer currentCeiling = 
		(Integer)this.target.getAttribute(Definitions.HP_CEILING);

	    int ceiling = this.target.getHitPoints();

	    if (currentCeiling != null)
		ceiling = currentCeiling.intValue();

	    ceiling += hpCount;
	    if (ceiling > this.target.getHitPoints())
		ceiling = this.target.getHitPoints();

	    this.target.setAttribute
		(Definitions.HP_CEILING, new Integer(ceiling));

	    log.info
		("Setting hp_ceiling of "+this.target.getName()+" to "+ceiling);
	}

	Term.echo(this.target.getName()+" now has "+newCount+" hit points \n");
	log.info(this.target.getName()+" hp "+newCount);

	if (newCount > 0) return true;

	if (target instanceof Monster)
	{
	    Term.echo(this.target.getName()+" is dead. \n");
	    return true;
	}

	if (newCount == 0)
	{
	    Term.echo
		(this.target.getName()+" can only do partial actions. "+
		 "A complex action would remove him 1 hp \n");
	}
	else if (newCount <= -10)
	{
	    Term.echo
		(this.target.getName()+" is DEAD. \n");
	}
	else if (newCount < 0)
	{
	    Term.echo
		(this.target.getName()+" is dying. \n");
	}
	return true;

    }

    //
    // METHODS FROM UndoableAbstractCommand
    
    public boolean execute ()
    {
	if (noMoreTokens()) return false;
	String targetName = tokenizer.nextToken();

	this.target = GameSession.getInstance().findBeing(targetName);

	if (this.target == null || Being.isATemplate(target))
	{
	    Term.echo("Didn't find any '"+targetName+"'. \n");
	    return false;
	}

	if (noMoreTokens()) return false;
	try
	{
	    this.hpCount = Integer.parseInt(tokenizer.nextToken());
	}
	catch (NumberFormatException nfe)
	{
	    Term.echo(getSyntax()+"\n");
	    return false;
	}

	if (this.tokenizer.hasMoreTokens())
	{
	    String s = this.tokenizer.nextToken();

	    if (s.charAt(0) == 'p')
		this.permanent = true;
	}

	return 
	    incrementHitPoints(this.hpCount);
    }

    public String getUndoInfo ()
    {
	return
	    "hp added "+this.hpCount+
	    " hit points to "+this.target.getName();
    }

    public void undo ()
    {
	incrementHitPoints( - this.hpCount);
    }

    public String getSyntax ()
    {
	return
	    "hp {targetName} {hpCount} [p[ermanent]]";
    }

    public String getHelp ()
    {
	return
	    "This commands adds (or removes) any number of hit points. "+
	    "-10 and maxHitPoints are the limits.\n"+
	    "You can state 'p' at the end of the command for creatures "+
	    "able to regenerate. They cannot regenerate 'p' damages.";
    }

}
