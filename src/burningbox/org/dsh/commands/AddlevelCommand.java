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
 * $Id: AddlevelCommand.java,v 1.2 2002/07/29 06:51:10 jmettraux Exp $
 */

//
// AddlevelCommand.java
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
import burningbox.org.dsh.views.DetailView;
import burningbox.org.dsh.entities.*;


/**
 * Adds or remove levels in classes to a character
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/07/29 06:51:10 $
 * <br>$Id: AddlevelCommand.java,v 1.2 2002/07/29 06:51:10 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class AddlevelCommand

    extends 
        UndoableAbstractCommand

    implements
        RegularCommand

{

    //
    // FIELDS

    protected burningbox.org.dsh.entities.Character target = null;
    protected int levelCount = 1;
    protected String className = null;
    
    //
    // CONSTRUCTORS

    public AddlevelCommand ()
    {
    }

    //
    // METHODS
    
    //
    // METHODS FROM UndoableAbstractCommand
    
    public boolean execute ()
    {
	if (noMoreTokens()) return false;
	String characterName = this.tokenizer.nextToken();

	if (noMoreTokens()) return false;
	String className = this.tokenizer.nextToken();

	if (this.tokenizer.hasMoreTokens())
	{
	    try
	    {
		this.levelCount = Integer.parseInt(this.tokenizer.nextToken());
	    }
	    catch (NumberFormatException nfe)
	    {
		Term.echo(getSyntax() + "\n");
		return false;
	    }
	}

	//
	// resolve character and class
	
	this.target = GameSession.getInstance()
	    .findCharacter(characterName);
	if (this.target == null)
	{
	    Term.echo("There is no character named "+characterName+"\n");
	    return false;
	}

	CharacterClass cClass = DataSets.findCharacterClass(className);
	if (cClass == null)
	{
	    Term.echo("There is no class named "+className+" loaded in dgsh\n");
	    return false;
	}

	//
	// do the job
	
	try
	{
	    this.target.addClassLevel(className, this.levelCount);
	}
	catch (DshException de)
	{
	    Term.echo("Failed to add/remove levels : "+de.getMessage()+"\n");
	    return false;
	}
	
	//
	// display
	
	DetailView dv = new DetailView(this.target);
	Term.echo(dv);
	return true;
    }

    public String getUndoInfo ()
    {
	return
	    "Added "+this.levelCount+" "+this.className+" level(s) to "+
	    this.target.getName();
    }

    public void undo ()
    {
	//
	// do the job
	
	try
	{
	    this.target.addClassLevel(className, -this.levelCount);
	}
	catch (DshException de)
	{
	    Term.echo("Failed to add/remove levels : "+de.getMessage()+"\n");
	}
	
	//
	// display
	
	DetailView dv = new DetailView(this.target);
	Term.echo(dv);
    }

    public String getSyntax ()
    {
	return
	    "addlevel {characterName} {className} [levelCount]";
    }

    public String getHelp ()
    {
	return
	    "Adds levels to a character in a given class.";
    }

}
