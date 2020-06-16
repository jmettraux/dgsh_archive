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
 * $Id: PythonInterpreter.java,v 1.4 2002/08/15 15:49:45 jmettraux Exp $
 */

//
// PythonInterpreter.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

package burningbox.org.dsh;

import org.python.core.PyObject;


/**
 * An encapsulation of the jython interpreter with methods for easily 
 * getting python-written commands ready to use.
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/08/15 15:49:45 $
 * <br>$Id: PythonInterpreter.java,v 1.4 2002/08/15 15:49:45 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class PythonInterpreter
{

    private final static PythonInterpreter theInterpreter 
	= new PythonInterpreter();

    org.apache.log4j.Logger log = 
	org.apache.log4j.Logger.getLogger("dgsh.PythonInterpreter");

    //
    // FIELDS
    
    private org.python.util.PythonInterpreter interpreter;

    //
    // CONSTRUCTORS

    public PythonInterpreter ()
    {
	org.python.core.PySystemState.initialize();
	this.interpreter = new org.python.util.PythonInterpreter();
    }

    //
    // METHODS

    public Object instantiate (String className, Class classType)
	throws DshException
    {
	//System.out.println("Looking for class '"+className+"'");

	PyObject pyClass = this.interpreter.get(className);

	if (pyClass == null) 
	{
	    //System.out.println("found nothing...");
	    return null;
	}

	PyObject po = pyClass.__call__();

	Object o = po.__tojava__(classType);

	if (o == org.python.core.Py.NoConversion)
	{
	    throw new DshException
		("Found class '"+className+
		 "' in python interpreter but failed to use it\n");
	}

	return o;
    }

    public void loadAndInterpret (String fileName)
    {
	this.interpreter.execfile(fileName);
    }

    public void loadAndInterpretDirectory (String dir)
    {
	loadAndInterpretDirectory(new java.io.File(dir));
    }

    private void loadAndInterpretDirectory (java.io.File dir)
    {
	java.io.File[] files = dir.listFiles();

	for (int i=0; i<files.length; i++)
	{
	    java.io.File f = files[i];

	    if (f.isDirectory())
	    {
		loadAndInterpretDirectory(f);
		continue;
	    }

	    if (f.getName().endsWith(".py"))
	    {
		loadAndInterpret(f.getPath());
	    }
	}
    }

    /*
    public void loadAndInterpret (java.util.List fileNames)
    {
	if (fileNames == null || fileNames.size() < 1) return;

	java.util.Iterator it = fileNames.iterator();
	while (it.hasNext())
	{
	    loadAndInterpret((String)it.next());
	}
    }
    */

    public PyObject eval (String command)
	throws DshException
    {
	java.util.StringTokenizer st = new java.util.StringTokenizer(command);

	st.nextToken(); // skip 'eval';

	if ( ! st.hasMoreTokens())
	{
	    throw new DshException
		("'get' or 'exec' awaited at the beginning of '"+command+"'");
	}

	String intention = st.nextToken().toLowerCase();
	String pyStuff = command.substring(6+intention.length());

	if (intention.equals("exec"))
	{
	    this.interpreter.exec(pyStuff);
	    return null;
	}
	else if (intention.equals("get"))
	{
	    return this.interpreter.get(pyStuff);
	}

	throw new DshException
	    ("'get' or 'exec' awaited at the beginning of '"+command+"'");
    }

    //
    // STATIC METHODS

    public static PythonInterpreter getInstance ()
    {
	return theInterpreter;
    }

}
