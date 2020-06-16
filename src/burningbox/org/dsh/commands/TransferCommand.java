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
 * $Id: TransferCommand.java,v 1.7 2002/07/22 05:46:52 jmettraux Exp $
 */

//
// TransferCommand.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// He hath eaten me out of house and home.
// 		-- William Shakespeare, "Henry IV"
// 

package burningbox.org.dsh.commands;

import burningbox.org.dsh.*;
import burningbox.org.dsh.entities.*;


/**
 * Transfers an object from any BeanSet to an other
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/07/22 05:46:52 $
 * <br>$Id: TransferCommand.java,v 1.7 2002/07/22 05:46:52 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class TransferCommand

    extends
        UndoableAbstractCommand

    implements
        RegularCommand,
        CombatCommand

{

    static org.apache.log4j.Logger log =
        org.apache.log4j.Logger.getLogger(TransferCommand.class.getName());

    //
    // FIELDS

    protected java.util.List objectNames = new java.util.ArrayList(5);
    protected BeanSet sourceSet = null;
    protected BeanSet targetSet = null;

    //
    // CONSTRUCTORS

    public TransferCommand ()
    {
    }

    //
    // METHODS

    public String getUndoInfo ()
    {
	StringBuffer sb = new StringBuffer();
	
	sb.append("Transfer of ");
	int i = 0;
	java.util.Iterator it = this.objectNames.iterator();
	while (it.hasNext())
	{
	    if (i > 6) break;

	    sb.append((String)it.next());

	    if (it.hasNext())
		sb.append(", ");

	    i++;
	}
	if (it.hasNext())
	    sb.append("...");

	return sb.toString();
    }


    //
    // METHODS FROM UndoableAbstractCommand
    
    public boolean execute ()
    {
	if (noMoreTokens()) return false;
	String objectName = tokenizer.nextToken();

	if (noMoreTokens()) return false;
	String sourceSetName = tokenizer.nextToken();

	if (noMoreTokens()) return false;
	String targetSetName = tokenizer.nextToken();

	//
	// get the sets...
	
	sourceSet = GameSession.getInstance().findSet(sourceSetName);
	targetSet = GameSession.getInstance().findSet(targetSetName);

	if (sourceSet == null)
	{
	    Term.echo("Set '"+sourceSetName+"' is unknown.\n");
	    return false;
	}
	if (targetSet == null)
	{
	    Term.echo("Set '"+targetSetName+"' is unknown.\n");
	    return false;
	}

	//
	// do it
	
	try
	{
	    BeanSet.transfer(objectName, sourceSet, targetSet);
	}
	catch (DshException de)
	{
	    Term.echo("Transfers failed.\n");
	    Term.echo(""+de+"\n");
	}

	return true;
    }

    public void undo ()
    {
	java.util.Iterator it = this.objectNames.iterator();
	while (it.hasNext())
	{
	    String name = null;
	    try
	    {
		name = (String)it.next();
		BeanSet.transfer(name, targetSet, sourceSet);
		Term.echo("   Transferred back '"+name+"' \n");
	    }
	    catch (DshException de)
	    {
		Term.echo("Failed to transfer '"+name+"' \n");
	    }
	}
    }

    public String getSyntax ()
    {
	return "transfer {name[*]} {fromSet} {toSet}";
    }

    public String getHelp ()
    {
	return "Transfers one or many objects from one set to the another.";
    }

}
