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
 * $Id: CastCommand.java,v 1.9 2002/08/27 06:53:37 jmettraux Exp $
 */

//
// CastCommand.java
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
import burningbox.org.dsh.GameSession;
import burningbox.org.dsh.DshException;
import burningbox.org.dsh.CombatSession;
import burningbox.org.dsh.views.SpellView;
import burningbox.org.dsh.entities.DataSets;
import burningbox.org.dsh.entities.CharacterClass;
import burningbox.org.dsh.entities.magic.Spell;


/**
 * Casts a spell ...
 * For the moment it is not undoable
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/08/27 06:53:37 $
 * <br>$Id: CastCommand.java,v 1.9 2002/08/27 06:53:37 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class CastCommand

    extends 
        AbstractCommand

    implements
        RegularCommand,
	CombatCommand

{

    static org.apache.log4j.Logger log =
        org.apache.log4j.Logger.getLogger("dgsh.CastCmd");

    //
    // FIELDS

    //
    // CONSTRUCTORS

    public CastCommand ()
    {
    }

    //
    // METHODS

    //
    // METHODS FROM AbstractCommand
    
    public boolean execute ()
    {
	//
	// parse command line
	
	if (noMoreTokens()) return false;
	String spellCasterName = fetchName();

	if (noMoreTokens()) return false;
	String spellName = fetchName();

	if (noMoreTokens()) return false;
	String className = fetchName();

	//
	// can the character cast any spell ?
	
	burningbox.org.dsh.entities.Character spellCaster = null;
	try
	{
	    spellCaster = (burningbox.org.dsh.entities.Character)GameSession
		.getInstance().findBeing(spellCasterName);

	    if ( ! spellCaster.isASpellCaster()) throw new Exception();
	}
	catch (Exception e)
	{
	    Term.echo
		("'"+spellCasterName+
		 "' wasn't found or he isn't a spellCaster");
	    return false;
	}

	//
	// for which class does the character casts ?
	
	CharacterClass spellCasterClass = 
	    DataSets.findCharacterClass(className);

	//
	// can the character cast this spell ?
	
	Spell spell = spellCaster.getKnownSpells()
	    .findSpell(className, spellName);
	if (spell == null)
	{
	    Term.echo("'"+spellCasterName+"' doesn't know '"+spellName+"'\n");
	    return false;
	}

	//
	// is the spell prepared ?
	
	if (spellCasterClass.mustPrepareSpells())
	{
	    if ( ! spellCaster.getPreparedSpells()
		    .isSpellReady(spellCasterClass.getName(), spellName))
	    {
		Term.echo
		    ("'"+spellCasterName+"' has not prepared '"+
		     spellName+"'\n");
		return false;
	    }
	}

	//
	// is the spell a domain spell ?
	
	boolean isADomainSpell = false;
	
	if (spellCasterClass.isUsingDomainSpells())
	{
	    isADomainSpell = spellCaster.getPreparedSpells()
		.isADomainSpell(spellCasterClass.getName(), spellName);
	}

	//
	// do the job
	
	try
	{
	    spell.cast(spellCaster, spellCasterClass, isADomainSpell);
	}
	catch (DshException de)
	{
	    Term.echo("spellcasting failed. \n");
	    Term.echo(de.getMessage());
	    Term.echo("\n");
	    return false;
	}

	SpellView sv = new SpellView(spell);
	Term.echo(sv);
	Term.echo("\n");

	Term.echo("spellcasting successful. \n");
	return true;
    }

    public String getSyntax ()
    {
	return
	    "cast {spellCasterName} {spellName} {className}";
    }

    public String getHelp ()
    {
	return
	    "Casts a spell.";
    }

}
