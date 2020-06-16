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
 * $Id: CreditCommand.java,v 1.3 2002/07/22 05:46:52 jmettraux Exp $
 */

//
// CreditCommand.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// Your ignorance cramps my conversation.
// 

package burningbox.org.dsh.commands;

import burningbox.org.dsh.Term;
import burningbox.org.dsh.entities.*;
import burningbox.org.dsh.GameSession;
import burningbox.org.dsh.CombatSession;
import burningbox.org.dsh.entities.equipment.Wallet;


/**
 * Credits or debits a character's wallet
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/07/22 05:46:52 $
 * <br>$Id: CreditCommand.java,v 1.3 2002/07/22 05:46:52 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class CreditCommand

    extends 
        UndoableAbstractCommand

    implements
        RegularCommand,
	CombatCommand

{

    static org.apache.log4j.Logger log =
        org.apache.log4j.Logger.getLogger(CreditCommand.class.getName());

    //
    // FIELDS
    
    protected burningbox.org.dsh.entities.Character target = null;
    protected Wallet walletDelta = null;

    //
    // CONSTRUCTORS

    public CreditCommand ()
    {
    }

    //
    // METHODS

    //
    // METHODS FROM UndoableAbstractCommand
    
    public boolean execute ()
    {
	if (noMoreTokens()) return false;
	String targetName = this.tokenizer.nextToken();

	this.walletDelta = Wallet.parse(this.tokenizer);

	//
	// do the job
	
	this.target = GameSession.getInstance().findCharacter(targetName);

	this.target.getEquipment().getWallet()
	    .credit(this.walletDelta);

	Term.echo("New credit : "+this.target.getEquipment().getWallet());
	Term.echo("\n");

	return true;
    }

    public String getUndoInfo ()
    {
	return
	    "Credited "+this.walletDelta+" to "+this.target.getName();
    }

    public void undo ()
    {
	this.target.getEquipment().getWallet()
	    .credit(this.walletDelta.negative());

	Term.echo("New credit : "+this.target.getEquipment().getWallet());
	Term.echo("\n");
    }

    public String getSyntax ()
    {
	return
	    "credit {targetName} {{amount}{gp|pp|cp|sp}}*";
    }

    public String getHelp ()
    {
	return
	    "Use 'credit' to add or withdraw money to a character's wallet. "+
	    "For example : 'credit shana 10gp 120sp'";
    }

}
