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
 * $Id: LoadCommand.java,v 1.11 2002/07/22 05:46:52 jmettraux Exp $
 */

//
// LoadCommand.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// No violence, gentlemen -- no violence, I beg of you!  Consider the furniture!
// 		-- Sherlock Holmes
// 

package burningbox.org.dsh.commands;

import burningbox.org.dsh.Term;
import burningbox.org.dsh.Shell;
import burningbox.org.dsh.GameSession;
import burningbox.org.dsh.PythonInterpreter;
import burningbox.org.dsh.entities.BeanSet;


/**
 * Loads an xml file into the session.
 * The behaviour of this command differs, depending on what can be found in
 * the file to load
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/07/22 05:46:52 $
 * <br>$Id: LoadCommand.java,v 1.11 2002/07/22 05:46:52 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class LoadCommand

    extends
        AbstractCommand

    implements
        RegularCommand,
        CombatCommand

{

    //static org.apache.log4j.Logger log =
    //    org.apache.log4j.Logger.getLogger(LoadCommand.class.getName());

    //
    // FIELDS

    //
    // CONSTRUCTORS

    public LoadCommand ()
    {
    }

    //
    // METHODS

    //
    // METHODS FROM AbstractCommand
    
    public boolean execute ()
    {
	this.tokenizer = new java.util.StringTokenizer(originalCommandLine);
	this.tokenizer.nextToken(); // skip command name

	if (noMoreTokens()) return false;
	String fileName = this.tokenizer.nextToken();

	//
	// is it a jython file ?
	
	if (fileName.endsWith(".py") ||
	    fileName.endsWith(".jy"))
	{
	    PythonInterpreter.getInstance().loadAndInterpret(fileName);

	    Term.echo("Python file loaded and interpreted.\n");
	    return true;
	}
	
	//
	// no it's a regular xml one

	BeanSet targetSet = GameSession.getInstance().getTempSet();
	if (this.tokenizer.hasMoreTokens())
	{
	    targetSet = GameSession.getInstance()
		.findSet(this.tokenizer.nextToken());
	}

	if (targetSet == null)
	{
	    Term.echo("The target set you specified doesn't exist.\n");
	    return false;
	}

	try
	{
	    targetSet.load(fileName);
	    Term.echo("File got loaded.\n");
	    return true;
	}
	catch (java.io.IOException ie)
	{
	    Term.echo("Couldn't load anything from "+fileName+"\n");
	    Term.echo(ie.toString()+"\n");
	    return false;
	}
    }

    public String getSyntax ()
    {
	return "load {filename} [t[emp]|p[arty]|o[pponents]]";
    }

    public String getHelp ()
    {
	return 
	    "Loads the content of a file and stores it into a set. "+
	    "By default, the objects are stored into the temp set.";
    }

}
