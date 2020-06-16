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
 * $Id: MiscModifier.java,v 1.6 2002/08/16 08:37:36 jmettraux Exp $
 */

//
// MiscModifier.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// Stay away from hurricanes for a while.
// 

package burningbox.org.dsh.entities;

import burningbox.org.dsh.Definitions;


/**
 * A miscellaneous modifier.
 * A mapping between a Definition (See burningbox.org.dsh.Definitions)
 * and a modifier.
 * For example a mapping between Definitions.FORTITUDE and +2
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/08/16 08:37:36 $
 * <br>$Id: MiscModifier.java,v 1.6 2002/08/16 08:37:36 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class MiscModifier
{

    //
    // FIELDS

    //protected String groupIdentifier;
    protected String definition;
    protected int modifier;
    protected String description;

    //
    // CONSTRUCTORS

    public MiscModifier ()
    {
    }

    public MiscModifier
	(String definition,
	 int modifier)
    {
	//this.groupIdentifier = null;
	this.definition = definition;
	this.modifier = modifier;
	//this.description = description;
    }

    public MiscModifier 
	(String definition,
	 int modifier,
	 String description)
    {
	//this.groupIdentifier = null;
	this.definition = definition;
	this.modifier = modifier;
	this.description = description;
    }

    /*
    public MiscModifier 
	(String groupIdentifier,
	 String definition,
	 int modifier,
	 String description)
    {
	this.groupIdentifier = groupIdentifier;
	this.definition = definition;
	this.modifier = modifier;
	this.description = description;
    }
    */

    //
    // BEAN METHODS

    //public String getGroupIdentifier () { return this.groupIdentifier; }
    public String getDefinition () { return this.definition; }
    public int getModifier () { return this.modifier; }
    public String getDescription () { return this.description; }

    //public void setGroupIdentifier (String gi) { this.groupIdentifier = gi; }
    public void setDefinition (String d) { this.definition = d; }
    public void setModifier (int m) { this.modifier = m; }
    public void setDescription (String d) { this.description = d; }

    //
    // STATIC METHODS

    public static int sumModifiers 
	(String definition,
	 java.util.List miscModifiersList)
    {
	if (miscModifiersList == null) 
	    return 0;

	int result = 0;

	java.util.Iterator it = miscModifiersList.iterator();
	while (it.hasNext())
	{
	    Object o = it.next();
	    if (o instanceof MiscModifier)
	    {
		MiscModifier mm = (MiscModifier)o;
		if ( ! mm.getDefinition().equals(definition)) continue;
		result += mm.getModifier();
	    }
	    else if (o instanceof MiscModified)
	    {
		MiscModified mm = (MiscModified)o;
		result += mm.sumMiscModifiers(definition);
	    }
	}

	return result;
    }

}
