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
 * $Id: DisplayFileCommand.java,v 1.1 2002/08/01 07:54:00 jmettraux Exp $
 */

//
// DisplayFileCommand.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// Many a writer seems to think he is never profound except when he can't
// understand his own meaning.
// 		-- George D. Prentice
// 

package burningbox.org.dsh.commands;

import burningbox.org.dsh.Term;
import burningbox.org.dsh.Shell;
import burningbox.org.dsh.Pager;


/**
 * This command will never be used : its extensions will.
 * Planned extensions : LicenseCommand and CreditsCommand
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/08/01 07:54:00 $
 * <br>$Id: DisplayFileCommand.java,v 1.1 2002/08/01 07:54:00 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public abstract class DisplayFileCommand

    extends
        AbstractCommand

    implements
        RegularCommand,
        CombatCommand

{

    //
    // FIELDS

    protected String fileName = null;

    //
    // CONSTRUCTORS

    public DisplayFileCommand ()
    {
    }

    //
    // METHODS

    private void displayFile ()
    {
	try
	{
	    Pager pager = new Pager(this.fileName, false);
	    pager.use();
	}
	catch (java.io.IOException ie)
	{
	    Term.echo("Failed to open "+this.fileName+": \n"+ie);
	}
    }

    //
    // METHODS FROM AbstractCommand
    
    public boolean execute ()
    {
	displayFile();
	return true;
    }

}
