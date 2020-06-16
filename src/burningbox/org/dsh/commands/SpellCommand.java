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
 * $Id: SpellCommand.java,v 1.6 2002/07/29 13:05:10 jmettraux Exp $
 */

//
// SpellCommand.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

package burningbox.org.dsh.commands;

import burningbox.org.dsh.*;
import burningbox.org.dsh.views.SpellView;
import burningbox.org.dsh.entities.*;
import burningbox.org.dsh.entities.magic.Spell;


/**
 * Displays all the monsters available as templates
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/07/29 13:05:10 $
 * <br>$Id: SpellCommand.java,v 1.6 2002/07/29 13:05:10 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class SpellCommand

    extends
        AbstractCommand

    implements
        RegularCommand,
        CombatCommand

{

    //
    // FIELDS

    //
    // CONSTRUCTORS

    public SpellCommand ()
    {
    }

    //
    // METHODS

    //
    // METHODS FROM AbstractCommand
    
    public boolean execute ()
    {
	if (noMoreTokens()) return false;

	String filter = fetchName();

	Spell spell = null;
	java.util.Iterator it = DataSets.getSpells().iterator();
	while (it.hasNext())
	{
	    Spell s = (Spell)it.next();
	    if (s.getName().toLowerCase().matches(filter))
	    {
		spell = s;
		break;
	    }
	}

	if (spell == null)
	{
	    Term.echo("Did not find any spell matching '"+filter+"' \n");
	    return false;
	}

	SpellView sv = new SpellView(spell);

	//Term.echo(sv);
	
	Pager pager = new Pager(sv);
	pager.use();

	return true;
    }

    public String getSyntax ()
    {
	return "spell {filter}";
    }

    public String getHelp ()
    {
	return 
	    "Returns the whole details for the first spell whose name matches "+
	    "filter";
    }

}
