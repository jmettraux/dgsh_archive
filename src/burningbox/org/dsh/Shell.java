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
 * $Id: Shell.java,v 1.42 2002/11/25 07:49:14 jmettraux Exp $
 */

//
// Shell.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// You are always busy.
// 

package burningbox.org.dsh;

//import burningbox.org.dsh.views.Table;
import burningbox.org.dsh.commands.AbstractCommand;
import burningbox.org.dsh.commands.UndoableAbstractCommand;
import burningbox.org.dsh.commands.RegularCommand;
import burningbox.org.dsh.commands.CombatCommand;


/**
 * The Dungeon command interpreter itself
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/11/25 07:49:14 $
 * <br>$Id: Shell.java,v 1.42 2002/11/25 07:49:14 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class Shell
{

    static org.apache.log4j.Logger log =
        org.apache.log4j.Logger.getLogger("dgsh");

    //
    // CONSTANTS
    
    public final static String VERSION
	= "dgsh - Dungeon Shell - v0.6.04 - jmettraux@burningbox.org";

    final static String PROMPT = "dgsh~T> ";
    final static String COMBAT_PROMPT = "dgsh-combat~T> ";

    //
    // FIELDS

    //protected Configuration conf = null;
    
    //
    // CONSTRUCTORS

    public Shell ()
    {
    }

    //
    // METHODS

    private boolean commandIsAppliable (AbstractCommand command)
    {
	//
	// this could be written in a shorter way,
	// but it would be then be longer to read and understand it
	// (and eventually debug it)
	//
	
	if (CombatSession.isCombatGoingOn())
	{
	    if ( ! (command instanceof CombatCommand))
		return false;
	}
	else
	{
	    if ( ! (command instanceof RegularCommand))
		return false;
	}
	return true;
    }

    public static AbstractCommand instantiateCommand (String commandName)
	throws Exception
    {
	
	String commandClassName = 
	    commandName.substring(0, 1).toUpperCase()+
	    commandName.substring(1)+"Command";

	Class commandClass = null;

	// try with jython first
	
	AbstractCommand pythonCommand = 
	    (AbstractCommand)PythonInterpreter.getInstance()
		.instantiate(commandClassName, AbstractCommand.class);

	if (pythonCommand != null)
	    return pythonCommand;

	// else look for a regular command

	commandClassName =
	    "burningbox.org.dsh.commands."+
	    commandClassName;

	if (commandClass == null)
	    commandClass = Class.forName(commandClassName);

	return (AbstractCommand)commandClass.newInstance();
    }

    //
    // ALIAS METHODS

    private void aliasCommand 
	(java.util.StringTokenizer st, String originalCommandLine)
    {
	if ( ! st.hasMoreTokens())
	    //
	    // display list of current aliases
	{
	    Aliases.listAliases();
	    return;
	}

	String alias = st.nextToken();

	if ( ! st.hasMoreTokens())
	    //
	    // unalias
	{
	    String aliasedCommand = Aliases.get(alias);
	    if (aliasedCommand != null)
	    {
		Aliases.remove(alias);

		Term.echo
		    ("Alias '"+alias+"' for '"+aliasedCommand+
		     "' got removed. \n");
		return;
	    }
	    else
	    {
		Term.echo
		    ("No aliased command for alias '"+alias+"' found. \n");
		return;
	    }
	}

	//
	// link alias to a command
	
	//String commandToAlias = st.nextToken();
	int index = originalCommandLine.toLowerCase().indexOf(alias);
	String commandToAlias =
	    originalCommandLine.substring(index+alias.length()+1);

	//log.debug("aliasing '"+alias+"' to '"+commandToAlias+"'");

	Aliases.put(alias, commandToAlias);

	Term.echo
	    ("'"+commandToAlias+"' is aliased to '"+alias+"'. \n");
    }

    //
    // THE eval METHOD
    //

    public void eval (String commandLine)
	throws Exception
    {

	if ( ! (commandLine.startsWith("log") ||
		commandLine.startsWith("blog") ||
		commandLine.trim().length() < 1))
	{
	    if (CombatSession.isCombatGoingOn())
		log.info("c>"+commandLine);
	    else
		log.info(">"+commandLine);
	}

	Term.reset(); 
	    // so that output from only one command is taken into account

	String originalCommandLine = commandLine;
	commandLine = commandLine.trim().toLowerCase();

	//
	// determine command className
	
	java.util.StringTokenizer st = 
	    new java.util.StringTokenizer(commandLine);

	if ( ! st.hasMoreTokens()) return;

	String sCommand = st.nextToken();

	//
	// alias command itself
	//
	// (you never can alias 'alias')
	
	if (sCommand.equals("alias"))
	{
	    aliasCommand(st, originalCommandLine);
	    return;
	}

	//
	// aliases
	
	String aliasedCommand = Aliases.get(sCommand);

	//
	// perhaps the aliasedCommand is a full command with options
	//
	if (aliasedCommand != null &&
	    aliasedCommand.indexOf(" ") > -1)
	{
	    eval
		(aliasedCommand+" "+
		 originalCommandLine.substring(sCommand.length()));
	    return;
	}

	//
	// or perhaps it's a simple one word command
	//
	if (aliasedCommand != null) 
	{
	    sCommand = aliasedCommand;
	}

	//
	// undo

	if (sCommand.equals("undo"))
	{
	    UndoStack.getInstance().undoCommand(st);
	    return;
	}
	
	//
	// regular command

	AbstractCommand command = instantiateCommand(sCommand);

	if (command == null)
	    return;

	if ( ! commandIsAppliable(command))
	{
	    Term.echo("Command not appliable at this point.\n");
	    return;
	}

	command.setCommandLine(commandLine);
	command.setOriginalCommandLine(originalCommandLine);
	command.setTokenizer(st);

	boolean successful = command.execute();

	if (successful)
	    UndoStack.getInstance().pushCommand(command);

    }

    private String preparePrompt (String prompt)
    {
	String replacement = "";

	if (GameSession.getInstance()
		.getBooleanEnvAttributeValue("display-game-clock"))
	{
	    replacement = GameSession.getInstance().getClock().display(true);
	    replacement = " "+replacement+" ";
	}

	return prompt.replaceFirst("~T", replacement);
    }

    private void prompt ()
    {
	if (CombatSession.isCombatGoingOn())
	    Term.echo(preparePrompt(COMBAT_PROMPT));
	else
	    Term.echo(preparePrompt(PROMPT));
    }

    public void run ()
    {

	log.info(VERSION);

	while (true)
	{
	    try
	    {
		//
		// Term.echo prompt

		prompt();

		//
		// get and eval DM command
		
		String line = Term.readLine();

		eval(line);
	    }
	    catch (ClassNotFoundException cnfe)
	    {
		Term.echo("Unknown command\n");
	    }
	    catch (Exception e)
	    {
		Term.echo("\n** Exception **\n");
		e.printStackTrace();
		Term.echo("** Exception **\n");
	    }
	}
    }

    //
    // MAIN METHOD

    public static void main (String[] args)
    {
	burningbox.org.dsh.commands.VersionCommand vc =
	    new burningbox.org.dsh.commands.VersionCommand();
	vc.execute();

	//
	// lookup configuration file
	
	String confFileName = "etc/dgsh-config.xml";
	
	try
	{
	    Configuration conf = Configuration.load(confFileName);
	    conf.apply(true);
	}
	catch (Exception e)
	{
	    System.out.println("Failed to apply configuration "+confFileName);
	    e.printStackTrace();
	    System.exit(1);
	}
	
	//
	// launcher interpreter
	
	(new Shell()).run();
    }

}
