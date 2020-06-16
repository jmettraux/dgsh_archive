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
 * $Id: AbstractCommand.java,v 1.8 2002/07/05 05:50:56 jmettraux Exp $
 */

//
// AbstractCommand.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// The bone-chilling scream split the warm summer night in two, the first
// half being before the scream when it was fairly balmy and calm and
// pleasant, the second half still balmy and quite pleasant for those who
// hadn't heard the scream at all, but not calm or balmy or even very nice
// for those who did hear the scream, discounting the little period of time
// during the actual scream itself when your ears might have been hearing it
// but your brain wasn't reacting yet to let you know.
// 		-- Winning sentence, 1986 Bulwer-Lytton bad fiction contest.
// 

package burningbox.org.dsh.commands;

import burningbox.org.dsh.Term;


/**
 * Every command MUST descend from this abstract class
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/07/05 05:50:56 $
 * <br>$Id: AbstractCommand.java,v 1.8 2002/07/05 05:50:56 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public abstract class AbstractCommand
{

    //
    // FIELDS
    
    protected String commandLine = null;
    protected String originalCommandLine = null;
    protected java.util.StringTokenizer tokenizer = null;

    //
    // CONSTRUCTORS
    
    public AbstractCommand ()
    {
    }

    //
    // METHODS

    public void setCommandLine (String cl) 
    { 
	this.commandLine = cl; 
    }
    public void setOriginalCommandLine (String ocl) 
    { 
	this.originalCommandLine = ocl; 
    }
    public void setTokenizer (java.util.StringTokenizer st) 
    { 
	this.tokenizer = st; 
    }

    public java.util.StringTokenizer getTokenizer ()
    {
	return this.tokenizer;
    }

    protected boolean noMoreTokens ()
    {
	if (this.tokenizer.hasMoreTokens())
	{
	    return false;
	}
	Term.echo(getSyntax());
	Term.echo("\n");
	return true;
    }

    protected String fetchName ()
    {
	String name = this.tokenizer.nextToken();
	return name.replace('_', ' ');
    }

    //
    // ABSTRACT METHODS
    
    /**
     * This method is called when the command is executed.
     */
    public abstract boolean execute ();

    /**
     * as the name implies
     */
    public abstract String getSyntax ();

    /**
     * as the name implies
     */
    public abstract String getHelp ();

}
