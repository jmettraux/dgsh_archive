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
 * $Id: PrepareCommand.java,v 1.14 2002/09/20 13:57:54 jmettraux Exp $
 */

//
// PrepareCommand.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

package burningbox.org.dsh.commands;

import java.util.StringTokenizer;

import burningbox.org.dsh.Term;
import burningbox.org.dsh.Pager;
import burningbox.org.dsh.GameSession;
import burningbox.org.dsh.views.Utils;
import burningbox.org.dsh.views.SpellsView;
import burningbox.org.dsh.views.utils.Cell;
import burningbox.org.dsh.views.utils.Line;
import burningbox.org.dsh.views.utils.MultiTable;
import burningbox.org.dsh.entities.Being;
import burningbox.org.dsh.entities.BeanSet;
import burningbox.org.dsh.entities.DataSets;
import burningbox.org.dsh.entities.CharacterClass;
import burningbox.org.dsh.entities.magic.Spell;
import burningbox.org.dsh.entities.magic.Spells;
import burningbox.org.dsh.entities.magic.SpellSlots;


/**
 * A utility for preparing spells in the morning, the evening or at midnight !
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/09/20 13:57:54 $
 * <br>$Id: PrepareCommand.java,v 1.14 2002/09/20 13:57:54 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class PrepareCommand

    extends
        AbstractCommand

    implements
        RegularCommand

{
    
    static org.apache.log4j.Logger log =
        org.apache.log4j.Logger.getLogger("dgsh.prep");

    //
    // FIELDS

    private burningbox.org.dsh.entities.Character spellCaster = null;
    private CharacterClass spellCasterClass = null;

    //
    // CONSTRUCTORS

    public PrepareCommand ()
    {
    }

    //
    // METHODS

    private String prompt ()
    {
	Term.echo
	    ("   "+this.spellCaster.getName()+" "+
	     this.spellCasterClass.getName()+"> ");
	try
	{
	    return Term.readLine();
	}
	catch (java.io.IOException ie)
	{
	}
	return "x"; // breaks the loop...
    }

    private java.util.StringTokenizer tokenize (String command)
    {
	StringTokenizer st = new StringTokenizer(command);
	st.nextToken(); // skip command itself

	return st;
    }

    private void listAvailableSpells (String command)
    {
	StringTokenizer st = tokenize(command);

	int level = -1;
	try
	{
	    level = Integer.parseInt(st.nextToken());
	}
	catch (Exception e)
	{
	    Term.echo("      l[ist] {level}\n");
	    return;
	}

	SpellsView sv = new SpellsView
	    (this.spellCaster,
	     this.spellCasterClass.getName(),
	     level);

	Term.reset();
	//Term.echo(sv);
	
	(new Pager(sv)).use();
    }

    private void freeSlots ()
    {
	SpellSlots freeSlots = this.spellCaster.getPreparedSpells()
	    .unpreparedSlots(this.spellCasterClass);

	MultiTable table = new MultiTable(80);
	Line l = new Line();
	l.addCell(new Cell(76, Cell.ALIGN_CENTER, Utils.displaySpellSlots(freeSlots, this.spellCasterClass)));
	table.addLine(l);

	Term.echo(table.render());
	Term.echo("\n");
    }

    private void listPreparedSpells ()
    {
	/*
	java.util.List spells = this.spellCaster.getPreparedSpells()
	    .getSpells(this.spellCasterClass.getName());

	if (spells == null) return;
	*/

	SpellsView sv = new SpellsView
	    (this.spellCaster.getPreparedSpells(), 
	     this.spellCasterClass.getName());
	//sv.setClassToDisplay(this.spellCasterClass.getName());

	Term.reset();
	Term.echo(sv);
    }

    private void prepare (String command)
    {
	StringTokenizer st = tokenize(command);

	if ( ! st.hasMoreTokens())
	{
	    Term.echo("        p[repare] {spellName} [d|s] [count]\n");
	    Term.echo("          - the spellName can be a regular expression\n");
	    Term.echo("          - use _ (underscores) instead of space in the spellName\n");
	    Term.echo("          - you can specify how many times the spell gets prepared with [count]\n");
	    Term.echo("          - if you flag the command with a 'd' after the spell name, the spell\n");
	    Term.echo("            will be prepared as a domain spell (if possible).\n");
	    return;
	}

	String filter = st.nextToken().toLowerCase().replace('_', ' ');

	boolean domainSpell = false;

	int count = 1;
	if (st.hasMoreTokens())
	{
	    String sCount = st.nextToken();

	    if (sCount.equals("d"))
	    {
		domainSpell = true;
	    }
	    else
	    {
		try
		{
		    count = Integer.parseInt(sCount);
		}
		catch (NumberFormatException nfe)
		{
		    // ignore, prepare only once
		}
	    }
	}
	if (count < 1) count = 1;

	Spell spell = this.spellCaster.getKnownSpells()
	    .findSpell(this.spellCasterClass.getName(), filter);

	if (spell == null)
	{
	    Term.echo("      spell not known.\n");
	    return;
	}

	try
	{
	    if (domainSpell)
	    {
		//
		// which domain ?

		String domainName = 
		    this.spellCaster.getKnownSpells().findDomain
			(this.spellCasterClass.getName(), spell);

		//log.debug("DomainName is >"+domainName+"<");
		
		//
		// add prepared spells
		
		for (int i=0; i<count; i++)
		{
		    this.spellCaster.getPreparedSpells().addPreparedDomainSpell
			(domainName, this.spellCasterClass.getName(), spell);
		}
	    }
	    else
	    {
		for (int i=0; i<count; i++)
		{
		    this.spellCaster.getPreparedSpells().addPreparedSpell
			(this.spellCasterClass.getName(), spell);
		}
	    }
	}
	catch (Exception e)
	{
	    Term.echo
		("      Failed to add spell '"+spell.getName()+
		 "'\n      "+e.getMessage()+"\n");
	    //log.debug("Failed to add spell '"+spell.getName()+"'", e);
	    return;
	}

	Term.echo("      '"+spell.getName()+"' got added.\n");
    }

    private void unprepare (String command)
    {
	StringTokenizer st = tokenize(command);

	if ( ! st.hasMoreTokens())
	{
	    Term.echo("      u[nprepare] {spellName} [d]\n");
	    return;
	}

	String spellName = st.nextToken().toLowerCase().replace('_', ' ');

	boolean domainSpell = false;

	if (st.hasMoreTokens())
	{
	    if ("d".equals(st.nextToken()))
		domainSpell = true;
	}

	this.spellCaster.getPreparedSpells()
	    .removeSpell(this.spellCasterClass, spellName, domainSpell);

	Term.echo("      spell '"+spellName+"' removed. \n");
    }

    private void reset ()
    {
	//
	// flush spell list
	
	this.spellCaster.getPreparedSpells()
	    .flush(this.spellCasterClass.getName());
	
	//
	// reset spell slots
	
	SpellSlots.resetSlots(this.spellCaster, this.spellCasterClass);

	Term.echo
	    ("'"+this.spellCaster.getName()+"' has all its spellslots "+
	     "available and\nis ready to prepare spells as '"+
	     this.spellCasterClass.getName()+"'. \n");
    }

    private void help ()
    {
	Term.echo("      available command during preparation : \n");
	Term.echo("        r[eset] \n");
	Term.echo("          - flushes the prepared spell list and resets the spell slots\n");
	Term.echo("        f[ree] \n");
	Term.echo("          - displays the free slots available for preparation\n");
	Term.echo("        l[ist] {level}\n");
	Term.echo("          - lists the spells that can be prepared for a given level\n");
	Term.echo("        p[repare] {spellName} [d] [count]\n");
	Term.echo("          - the spellName can be a regular expression\n");
	Term.echo("          - use _ (underscores) instead of space in the spellName\n");
	Term.echo("          - you can specify how many times the spell gets prepared with [count]\n");
	Term.echo("          - if you flag the command with a 'd' after the spell name, the spell\n");
	Term.echo("            will be prepared as a domain spell (if possible).\n");
	Term.echo("        u[nprepare] {spellName} [d]\n");
	Term.echo("          - removes a spell from the prepared ones\n");
	Term.echo("          - flag with a 'd' if the spell is a domain spell\n");
	Term.echo("        {ENTER} \n");
	Term.echo("          - displays the prepared spells \n");
	Term.echo("        x|ex[it]\n");
	Term.echo("          - quits the preparation mode\n");
    }

    private boolean eval (String command)
    {
	log.info(">"+command);

	Term.reset();
	    // so that the 'more' prompt appears at the right moment

	if (command.startsWith("q") || 
	    command.startsWith("x") ||
	    command.startsWith("ex"))
	{
	    return false;
	}

	if (command.startsWith("l"))
	    //
	    // list
	{
	    listAvailableSpells(command);
	}
	else if (command.startsWith("f"))
	    //
	    // free slots
	{
	    freeSlots();
	}
	else if (command.startsWith("u"))
	    //
	    // cancel preparation
	{
	    unprepare(command);
	}
	else if (command.startsWith("p"))
	    //
	    // prepare
	{
	    prepare(command);
	}
	else if (command.startsWith("r"))
	    //
	    // reset
	{
	    reset();
	}
	else if (command.startsWith("h"))
	    //
	    // help
	{
	    help();
	}
	else
	    //
	    // prepared
	{
	    listPreparedSpells();
	}

	return true;
    }

    private void loop ()
    {
	while (true)
	{
	    String command = prompt();

	    if ( ! eval(command)) break;
	}
    }

    /**
     * This method is used for spellcaster that don't need to prepare
     * spells. It simply asks the DM if he wants the spellcaster to 
     * be marked as 'fresh', i.e. all his spell slots are marked as
     * 'unconsumed'.
     */
    private boolean resetSpellCaster ()
    {
	Term.echo
	    ("'"+this.spellCaster.getName()+
	     "' doesn't have to prepare his spells as he is a "+
	     this.spellCasterClass.getFullName());
	Term.echo
	    ("\nWould you like to reset his spell slots ? \n(y/n)> ");
	try
	{
	    String reply = Term.readLine();
	    if (reply.charAt(0) == 'y' ||
		reply.charAt(0) == 'Y')
	    {
		SpellSlots.resetSlots(this.spellCaster, this.spellCasterClass);

		Term.echo
		    ("All slots are now available for '"+
		     this.spellCaster.getName()+"' as "+
		     this.spellCasterClass.getFullName());
		Term.echo("\n");
	    }
	}
	catch (java.io.IOException ie)
	{
	}
	return true;
    }

    //
    // METHODS FROM AbstractCommand
    
    public boolean execute ()
    {
	if (noMoreTokens()) return false;
	String name = fetchName();

	if (noMoreTokens()) return false;
	String className = this.tokenizer.nextToken();

	Being b = GameSession.getInstance().findBeing(name);
	if ( ! (b instanceof burningbox.org.dsh.entities.Character))
	{
	    Term.echo("'"+name+"' is not a spell caster. \n");
	    return false;
	}
	this.spellCaster = (burningbox.org.dsh.entities.Character)b;

	this.spellCasterClass = DataSets.findCharacterClass(className);
	if (this.spellCasterClass == null)
	{
	    Term.echo("class '"+className+"' isn't loaded in dgsh. \n");
	    return false;
	}

	if ( ! this.spellCasterClass.mustPrepareSpells())
	{
	    return resetSpellCaster();
	}

	int level = this.spellCaster.computeLevelForClass(className);

	if (level == 0)
	{
	    Term.echo("'"+name+"' has no levels in '"+className+"' \n");
	    return false;
	}

	loop();

	return true;
    }

    public String getSyntax ()
    {
	return "prepare {characterName} {className}";
    }

    public String getHelp ()
    {
	return 
	    "An assistant for preparing spells for a spell caster";
    }

}
