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
 * $Id: UndoStack.java,v 1.1 2002/06/27 06:25:49 jmettraux Exp $
 */

//
// UndoStack.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

package burningbox.org.dsh;

import burningbox.org.dsh.commands.AbstractCommand;
import burningbox.org.dsh.commands.UndoableAbstractCommand;

/**
 * Took it out of Shell and made it a singleton. (So it can get accessed 
 * by jython scripts)
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/06/27 06:25:49 $
 * <br>$Id: UndoStack.java,v 1.1 2002/06/27 06:25:49 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class UndoStack
{

    final static int UNDO_STACK_MAX_SIZE = 25;

    //
    // THE SINGLETON

    private final static UndoStack theUndoStack = new UndoStack();

    //
    // FIELDS
    
    protected java.util.LinkedList undoStack = new java.util.LinkedList();

    //
    // CONSTRUCTORS

    public UndoStack ()
    {
    }

    //
    // METHODS

    public void pushCommand (AbstractCommand command)
    {
	if ( ! (command instanceof UndoableAbstractCommand)) return;

	this.undoStack.addFirst(command);

	while (this.undoStack.size() > UNDO_STACK_MAX_SIZE)
	{
	    this.undoStack.removeLast();
	}
    }

    /*
    public AbstractCommand popCommand ()
    {
	AbstractCommand result = 
	    (AbstractCommand)this.undoStack.getFirst();

	this.undoStack.removeFirst();

	return result;
    }
    */

    public void displayUndoStack ()
    {
	int i=0;
	java.util.Iterator it = this.undoStack.iterator();
	while (it.hasNext())
	{
	    UndoableAbstractCommand uac = (UndoableAbstractCommand)it.next();
	    Term.echo("  "+i+" ");
	    Term.echo(uac.getUndoInfo());
	    Term.echo("\n");
	    i++;
	}
    }

    public void undoCommand (java.util.StringTokenizer st)
    {
	if ( ! st.hasMoreTokens())
	{
	    displayUndoStack();
	    return;
	}

	int commandId = -1;
	try
	{
	    commandId = Integer.parseInt(st.nextToken());
	}
	catch (NumberFormatException nfe)
	{
	    Term.echo("undo [integer]\n");
	    return;
	}

	UndoableAbstractCommand uac = 
	    (UndoableAbstractCommand)this.undoStack.remove(commandId);

	if (uac != null)
	    uac.undo();
    }

    //
    // STATIC METHODS
    
    public static UndoStack getInstance ()
    {
	return theUndoStack;
    }

}
