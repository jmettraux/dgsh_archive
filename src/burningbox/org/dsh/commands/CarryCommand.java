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
 * $Id: CarryCommand.java,v 1.10 2002/08/05 07:05:26 jmettraux Exp $
 */

//
// CarryCommand.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// You will be run over by a beer truck.
//

package burningbox.org.dsh.commands;

import burningbox.org.dsh.Term;
import burningbox.org.dsh.entities.*;
import burningbox.org.dsh.entities.equipment.*;
import burningbox.org.dsh.GameSession;
import burningbox.org.dsh.CombatSession;


/**
 * Makes a being carry something
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/08/05 07:05:26 $
 * <br>$Id: CarryCommand.java,v 1.10 2002/08/05 07:05:26 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class CarryCommand

    extends 
        UndoableAbstractCommand

    implements
        RegularCommand,
	CombatCommand

{

    static org.apache.log4j.Logger log =
        org.apache.log4j.Logger.getLogger("dgsh.CarryCmd");

    //
    // FIELDS

    protected EquipmentLocator oldLocation = null;
    protected EquipmentLocator newLocation = null;
    
    //
    // CONSTRUCTORS

    public CarryCommand ()
    {
    }

    //
    // METHODS
    
    //
    // METHODS FROM UndoableAbstractCommand
    
    public boolean execute ()
    {
	if (noMoreTokens()) return false;
	String beingName = fetchName();

	Being newOwner = GameSession.getInstance().findBeing(beingName);

	if (newOwner == null)
	{
	    Term.echo("There is nobody named '"+beingName+"'.\n");
	    return false;
	}

	if (noMoreTokens()) return false;
	String sLocator = fetchName();
	    // 'bilbo_baggins.worn.ring' underscore are OK now

	this.oldLocation = 
	    EquipmentLocator.locateEquipment(sLocator, newOwner);

	if (this.oldLocation == null ||
	    this.oldLocation.getEquipmentPiece() == null)
	{
	    Term.echo
		("Cannot find "+sLocator+" \n");
	    return false;
	}

	this.newLocation = new EquipmentLocator
	    (newOwner, 
	     newOwner.getEquipment().getCarriedEquipment(), 
	     this.oldLocation.getEquipmentPiece());

	EquipmentLocator.transfer(this.oldLocation, this.newLocation);

	Term.echo(getUndoInfo());
	Term.echo("\n");

	return true;
    }

    public String getUndoInfo ()
    {
	return
	    "moved '"+this.oldLocation.toString()+
	    "' to '"+this.newLocation.toString()+"'.";
    }

    public void undo ()
    {
	EquipmentLocator.transfer(this.newLocation, this.oldLocation);
	Term.echo
	    ("moved '"+this.newLocation.toString()+
	     "' to '"+this.oldLocation.toString()+"'.\n");
    }

    public String getSyntax ()
    {
	return
	    "carry {beingName} {equipmentLocator}";
    }

    public String getHelp ()
    {
	return
	    "Make a character or a monster carry something. like "+
	    "'carry lidda larm' or 'carry lidda krusk.carried.larm'";
    }

}
