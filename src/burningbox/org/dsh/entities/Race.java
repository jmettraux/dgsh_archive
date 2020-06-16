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
 * $Id: Race.java,v 1.8 2002/07/22 05:46:53 jmettraux Exp $
 */

//
// Race.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// You will be awarded some great honor.
// 

package burningbox.org.dsh.entities;

import burningbox.org.dsh.DshException;
import burningbox.org.dsh.PythonInterpreter;
import burningbox.org.dsh.commands.Trigger;


/**
 * As its name implies
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/07/22 05:46:53 $
 * <br>$Id: Race.java,v 1.8 2002/07/22 05:46:53 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class Race

    implements Named

{

    static org.apache.log4j.Logger log =
        org.apache.log4j.Logger.getLogger(Race.class.getName());

    //
    // FIELDS

    protected String raceName = null;
    protected java.util.List miscModifiers = null;
    protected String predilectionClass = null;
    protected int levelEquivalentModifier = 0;
    protected String triggerToApplyEachRound = null;

    protected java.util.Map specials = new java.util.HashMap(0);

    //
    // CONSTRUCTORS

    public Race ()
    {
    }

    //
    // BEAN METHODS

    public String getName ()
    {
	return this.raceName;
    }
    public java.util.List getMiscModifiers ()
    {
	return this.miscModifiers;
    }
    public String getPredilectionClass ()
    {
	return this.predilectionClass;
    }
    public int getLevelEquivalentModifier ()
    {
	return this.levelEquivalentModifier;
    }
    public String getTriggerToApplyEachRound () 
    { 
	return this.triggerToApplyEachRound; 
    }
    public java.util.Map getSpecials ()
    {
	return this.specials;
    }

    public void setName (String rn) { this.raceName = rn; }
    public void setMiscModifiers (java.util.List mm) { this.miscModifiers = mm; }
    public void setPredilectionClass (String pc) { this.predilectionClass = pc; }
    public void setLevelEquivalentModifier (int i) { this.levelEquivalentModifier = i; }
    public void setTriggerToApplyEachRound (String s) { this.triggerToApplyEachRound = s; }
    public void setSpecials (java.util.Map m) { this.specials = m; }

    //
    // METHODS

    public Trigger fetchTriggerToApplyEachRound ()
	throws DshException
    {
	if (this.triggerToApplyEachRound == null) return null;

	Trigger t = (Trigger)PythonInterpreter.getInstance()
	    .instantiate(this.triggerToApplyEachRound, Trigger.class);

	if (t == null)
	{
	    throw new DshException
		("Trigger '"+this.triggerToApplyEachRound+"' not found");
	}

	return t;
    }

}
