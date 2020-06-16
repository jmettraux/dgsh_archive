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
 * $Id: InstCommand.java,v 1.5 2002/07/05 05:50:56 jmettraux Exp $
 */

//
// InstCommand.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// A pickup with three guys in it pulls into the lumber yard.  One of the men
// gets out and goes into the office.
// 	"I need some four-by-two's," he says.
// 	"You must mean two-by-four's" replies the clerk.
// 	The man scratches his head.  "Wait a minute," he says, "I'll go
// check." 
// 	Back, after an animated conversation with the other occupants of the
// truck, he reassures the clerk, that, yes, in fact, two-by-fours would be
// acceptable.
// 	"OK," says the clerk, writing it down, "how long you want 'em?"
// 	The guy gets the blank look again.  "Uh... I guess I better go
// check," he says.
// 	He goes back out to the truck, and there's another animated
// conversation.  The guy comes back into the office.  "A long time," he says,
// "we're building a house".
// 

package burningbox.org.dsh.commands;

import burningbox.org.dsh.*;
import burningbox.org.dsh.entities.*;


/**
 * Instatiates a MonsterTemplate and adds the resulting monster to the 'opp'
 * set.
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/07/05 05:50:56 $
 * <br>$Id: InstCommand.java,v 1.5 2002/07/05 05:50:56 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class InstCommand

    extends 
        AbstractCommand

    implements
        CombatCommand

{

    //
    // FIELDS
    
    //
    // CONSTRUCTORS

    public InstCommand ()
    {
    }

    //
    // METHODS

    private boolean instantiateMonsters
	(String templateName,
	 int count,
	 String namePrefix)
    {

	MonsterTemplate mt = DataSets.findMonsterTemplate(templateName);

	if (mt == null)
	{
	    Term.echo("Unknown monster template '"+templateName+"'\n");
	    return false;
	}

	/*
	Named n = GameSession.getInstance().getTempSet().get(templateName);

	if (n == null)
	{
	    Term.echo("Unknown monster template '"+templateName+"'\n");
	    return false;
	}

	MonsterTemplate mt = null;
	try
	{
	    mt = (MonsterTemplate)n;
	}
	catch (ClassCastException cce)
	{
	    Term.echo("'"+templateName+"' is not a monster template.\n");
	    return false;
	}
	*/

	Monster[] monsters = mt.instantiate(count, namePrefix);

	for (int i=0; i<monsters.length; i++)
	{
	    CombatSession.getInstance().getOpponents().add(monsters[i]);
	}

	Term.echo
	    (""+monsters.length+" monsters '"+templateName+
	     "' added to the opponents\n");

	return true;
    }

    //
    // METHODS FROM AbstractCommand

    public boolean execute ()
    {
	if (noMoreTokens()) return false;
	String templateName = fetchName();

	int count = 1;
	String namePrefix = null;

	if (this.tokenizer.hasMoreTokens())
	{
	    try
	    {
		count = Integer.parseInt(tokenizer.nextToken());
	    }
	    catch (NumberFormatException nfe)
	    {
		Term.echo(getSyntax()+"\n");
		return false;
	    }

	    if (this.tokenizer.hasMoreTokens())
		namePrefix = this.tokenizer.nextToken();
	}

	return instantiateMonsters(templateName, count, namePrefix);
    }

    public String getSyntax ()
    {
	return "inst {monster template name} [{count} [name prefix]]";
    }

    public String getHelp ()
    {
	return
	    "type 'inst goblin 10' and 10 gobs will face your party "+
	    "(they are automatically transferred to opp";
    }

}
