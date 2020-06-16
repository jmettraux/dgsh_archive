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
 * $Id: SearchCommand.java,v 1.8 2002/09/20 13:57:54 jmettraux Exp $
 */

//
// SearchCommand.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// Your mode of life will be changed for the better because of new developments.
//

package burningbox.org.dsh.commands;

import burningbox.org.dsh.*;
import burningbox.org.dsh.entities.*;


/**
 * Performs a 'search' check for each of the party members or just one of them
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/09/20 13:57:54 $
 * <br>$Id: SearchCommand.java,v 1.8 2002/09/20 13:57:54 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class SearchCommand

    extends
        AbstractCommand

    implements
        RegularCommand,
        CombatCommand

{

    static org.apache.log4j.Logger log =
        org.apache.log4j.Logger.getLogger(SearchCommand.class.getName());

    //
    // FIELDS

    //
    // CONSTRUCTORS

    public SearchCommand ()
    {
    }

    //
    // METHODS

    private static void checkForCharacter (int dc, String characterName)
    {
	Being c = (Being)GameSession
		.getInstance().getParty().get(characterName);

	int modifier = c.computeSkillModifier("search");
	int random = burningbox.org.dsh.Utils.throwD20();

	String sMod = ""+modifier;
	if (modifier > 0)
	    sMod = "+"+sMod;
	
	Term.echo
	    ("   "+characterName+
	     " searches at "+sMod+
	     " and rolls a "+random+
	     " so it's a "+(random+modifier));
	if ((random+modifier) >= dc)
	    Term.echo("   SUCCESS ! \n");
	else
	    Term.echo("\n");
    }

    //
    // METHODS FROM AbstractCommand
    
    public boolean execute ()
    {
	if (noMoreTokens()) return false;
	int dc = 10;
	try
	{
	    dc = Integer.parseInt(tokenizer.nextToken());
	}
	catch (NumberFormatException nfe)
	{
	    Term.echo(getSyntax());
	    Term.echo("\n");
	    return false;
	}

	if ( ! tokenizer.hasMoreTokens())
	{
	    //
	    // whole party

	    java.util.Iterator it = 
		GameSession.getInstance().getParty().nameIterator();
	    while(it.hasNext())
	    {
		String name = (String)it.next();
		checkForCharacter(dc, name);
	    }
	    return true;
	}

	//
	// just one character
	
	String characterName = tokenizer.nextToken();
	checkForCharacter(dc, characterName);

	return true;

    }

    public String getSyntax ()
    {
	return "search {dc} [characterName]";
    }

    public String getHelp ()
    {
	return 
	    "Performs an automatic search check with a given DC class "+
	    "for all the party members or for just one of them";
    }

}
