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
 * $Id: EffectView.java,v 1.8 2002/07/29 06:51:10 jmettraux Exp $
 */

//
// EffectView.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// Generosity and perfection are your everlasting goals.
// 

package burningbox.org.dsh.views;

import burningbox.org.dsh.*;
import burningbox.org.dsh.entities.*;


/**
 * The view used by the skills command
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/07/29 06:51:10 $
 * <br>$Id: EffectView.java,v 1.8 2002/07/29 06:51:10 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class EffectView

    implements View

{

    static org.apache.log4j.Logger log = org.apache.log4j.Logger
	.getLogger(EffectView.class.getName());

    //
    // FIELDS

    protected Effect effect = null;

    //
    // CONSTRUCTORS

    public EffectView (Effect effect)
    {
	this.effect = effect;
    }

    //
    // METHODS

    public StringBuffer process ()
    {
	StringBuffer sb = new StringBuffer();

	//
	// headers
	
	String[] sizes = new String[] 
	    { "l20", "l20", "r5", "l20" };
	String[] titles = new String[]
	    { "Effect", "description", "ini", "targets" };

	Table table = new Table(sizes, titles);

	String targets = "";

	java.util.List targetNames = this.effect.getTargetNames();

	if (targetNames != null)
	{
	    java.util.Iterator it = targetNames.iterator();
	    while (it.hasNext())
	    {
		String name = (String)it.next();
		targets += name;

		if (it.hasNext())
		    targets += '\n';
	    }
	}

	String[] data = new String[]
	{
	    this.effect.getName(),
	    this.effect.getDescription(),
	    ""+this.effect.getCurrentInitiative()+
		" "+this.effect.computeDexterity(),
	    targets
	};

	table.addLine(data);

	sb.append(table.render());

	return sb;
    }

}
