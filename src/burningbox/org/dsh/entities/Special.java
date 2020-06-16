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
 * $Id: Special.java,v 1.3 2002/07/16 12:10:30 jmettraux Exp $
 */

//
// Special.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@yahoo.com)
//

package burningbox.org.dsh.entities;

import burningbox.org.dsh.DshException;
import burningbox.org.dsh.PythonInterpreter;
import burningbox.org.dsh.commands.Trigger;


/**
 * Special represents for monsters either a special ability or a special attack
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/07/16 12:10:30 $
 * <br>$Id: Special.java,v 1.3 2002/07/16 12:10:30 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class Special
{

    public final static String SPECIAL = "special.self";
	// a label for a trigger to retrieve the special instance
	// triggering it
	
    public final static int TYPE_EX	= 0;
    public final static int TYPE_SP	= 1;
    public final static int TYPE_SU	= 2;

    //
    // FIELDS

    protected String name = null;
    protected int type = -1;
    protected String description = null;
    protected String triggerName = null;
    protected transient Trigger trigger = null;

    //
    // CONSTRUCTORS

    public Special ()
    {
    }

    public Special 
	(String name, int type, String description, String triggerName)
    {
	this.name = name;
	this.type = type;
	this.description = description;
	this.triggerName = triggerName;
    }

    public Special 
	(String name, int type, String description)
    {
	this(name, type, description, null);
    }

    public Special 
	(String name, String description)
    {
	this(name, -1, description, null);
    }

    public Special 
	(String name)
    {
	this(name, -1, null, null);
    }

    //
    // BEAN METHODS

    public String getName () { return this.name; }
    public int getType () { return this.type; }
    public String getDescription () { return this.description; }
    public String getTriggerName () { return this.triggerName; }

    public void setName (String s) { this.name = s; }
    public void setType (int i) { this.type = i; }
    public void setDescription (String s) { this.description = s; }
    public void setTriggerName (String s) { this.triggerName = s; }

    //
    // METHODS

    protected Trigger resolveTrigger (Being being)
	throws DshException
    {
	if (this.triggerName == null) return null;

	Trigger trigger = (Trigger)PythonInterpreter.getInstance()
	    .instantiate(this.triggerName, Trigger.class);

	//
	// set trigger context
	
	trigger.setTarget(being);
	
	java.util.Map context = new java.util.HashMap();
	context.put(SPECIAL, this);
	trigger.init(context);

	return trigger;
    }

    /**
     * Triggers the effect.
     * The name of the being using the special power must be given as 
     * parameter.
     */
    protected void trigger (Being being)
	throws DshException
    {
	if (this.triggerName == null) return;

	resolveTrigger(being).trigger();
    }

}
