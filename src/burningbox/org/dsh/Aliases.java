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
 * $Id: Aliases.java,v 1.3 2002/07/31 11:51:06 jmettraux Exp $
 */

//
// Aliases.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// Death is nature's way of telling you to slow down.
// 

package burningbox.org.dsh;

import burningbox.org.dsh.views.Table;


/**
 * A list of aliases for the shell, accessible as a singleton (though not
 * through a getInstance() method).
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/07/31 11:51:06 $
 * <br>$Id: Aliases.java,v 1.3 2002/07/31 11:51:06 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class Aliases
{

    //
    // FIELDS

    private final static Aliases theAliases = new Aliases();
    private java.util.Map aliasMap = null;

    //
    // CONSTRUCTORS

    private Aliases ()
    {
    }

    //
    // METHODS

    public static void listAliases ()
    {
	String[] sizes = new String[] { "l9", "l25" };
	String[] titles = new String[] { "alias", "for" };
	Table table = new Table(sizes, titles);

	java.util.Iterator it = theAliases.aliasMap.keySet().iterator();
	while (it.hasNext())
	{
	    String alias = (String)it.next();
	    String commandToAlias = (String)(theAliases.aliasMap.get(alias));
	    table.addLine(new String[] { alias, commandToAlias });
	}

	Term.echo(table.render());
    }

    public static String get (String alias)
    {
	return (String)theAliases.aliasMap.get(alias);
    }

    public static void remove (String alias)
    {
	theAliases.aliasMap.remove(alias);
    }

    public static void put (String alias, String aliasedCommand)
    {
	theAliases.aliasMap.put(alias, aliasedCommand);
    }

    public static void init (java.util.Map aliasMap)
    {
	theAliases.aliasMap = aliasMap;
    }

}
