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
 * $Id: EquipmentPiece.java,v 1.5 2002/07/22 05:46:53 jmettraux Exp $
 */

//
// EquipmentPiece.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// Q:	What do you call the money you pay to the government when
// 	you ride into the country on the back of an elephant?
// A:	A howdah duty.
// 

package burningbox.org.dsh.entities.equipment;

import burningbox.org.dsh.entities.Named;
import burningbox.org.dsh.entities.MiscModifier;
import burningbox.org.dsh.entities.MiscModified;


/**
 * A generic piece of equipment
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/07/22 05:46:53 $
 * <br>$Id: EquipmentPiece.java,v 1.5 2002/07/22 05:46:53 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class EquipmentPiece

    implements 
	Cloneable,
	MiscModified,
	Named

{

    //static org.apache.log4j.Logger log =
    //    org.apache.log4j.Logger.getLogger(EquipmentPiece.class.getName());

    //
    // FIELDS
   
    protected String description = null;
    protected float weight = (float)0.0;
    protected String name = null;
    protected String value = null;
    protected String categoryDefinition = null;

    protected java.util.List modifiers = new java.util.ArrayList(1);

    //
    // CONSTRUCTORS

    public EquipmentPiece ()
    {
    }

    public EquipmentPiece
	(String description,
	 float weight)
    {
	this.description = description;
	this.weight = weight;
    }

    //
    // BEAN METHODS

    public String getDescription () { return this.description; }
    public float getWeight () { return this.weight; }
    public String getName () { return this.name; }
    public String getValue () { return this.value; }
    public String getCategoryDefinition () { return this.categoryDefinition; }
    public java.util.List getModifiers () { return this.modifiers; }

    public void setDescription (String d) { this.description = d; }
    public void setWeight (float w) { this.weight = w; }
    public void setName (String s) { this.name = s; }
    public void setValue (String s) { this.value = s; }
    public void setCategoryDefinition (String s) { this.categoryDefinition = s; }
    public void setModifiers (java.util.List m) { this.modifiers = m; }

    //
    // METHODS
    
    public int sumMiscModifiers (String definition)
    {
	return MiscModifier.sumModifiers(definition, this.modifiers);
    }

    protected void copyTo (EquipmentPiece ep)
    {
	ep.description = new String(this.description);
	ep.weight = this.weight;
	ep.name = new String(this.name);
	ep.value = new String(this.value);
	ep.categoryDefinition = new String(this.categoryDefinition);

	if (this.modifiers != null)
	{
	    ep.modifiers = 
		(java.util.List)((java.util.ArrayList)this.modifiers).clone();
	}
    }

    public Object clone ()
    {
	EquipmentPiece ep = new EquipmentPiece();

	this.copyTo(ep);

	return ep;
    }

}
