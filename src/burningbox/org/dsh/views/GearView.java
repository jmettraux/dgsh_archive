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
 * $Id: GearView.java,v 1.7 2002/07/29 09:57:46 jmettraux Exp $
 */

//
// GearView.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// Tomorrow, you can be anywhere.
// 

package burningbox.org.dsh.views;

import burningbox.org.dsh.*;
import burningbox.org.dsh.entities.*;
import burningbox.org.dsh.entities.equipment.*;


/**
 * The view used by the gear command 
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/07/29 09:57:46 $
 * <br>$Id: GearView.java,v 1.7 2002/07/29 09:57:46 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class GearView

    implements View

{

    //static org.apache.log4j.Logger log = org.apache.log4j.Logger
    //	.getInstance(GearView.class.getName());

    //
    // FIELDS

    protected Being being = null;

    //
    // CONSTRUCTORS

    public GearView (Being characterOrMonster)
    {
	this.being = characterOrMonster;
    }

    //
    // METHODS

    public StringBuffer process ()
    {
	StringBuffer sb = new StringBuffer();

	sb.append("\n"+this.being.getName()+" holds :\n");

	EquipmentView ev = null;

	ev = new EquipmentView
	    (this.being.getEquipment().getHeldEquipment());
	sb.append(ev.process());

	sb.append("\n"+this.being.getName()+" wears :\n");
	ev = new EquipmentView
	    (this.being.getEquipment().getWornEquipment());
	sb.append(ev.process());

	sb.append("\n"+this.being.getName()+" carries :\n");
	ev = new EquipmentView
	    (this.being.getEquipment().getCarriedEquipment());
	sb.append(ev.process());

	sb.append('\n');
	sb.append(this.being.getEquipment().getWallet().toString());
	sb.append('\n');

	int loadQualifier = this.being.getEquipment().getLoadQualifier
	    (this.being.getAbilities().getScore(Definitions.STRENGTH), 
	     this.being.getSize());

	String slQualifier = "light";
	if (loadQualifier == 1)
	    slQualifier = "medium";
	else if (loadQualifier == 2)
	    slQualifier = "heavy";
	else if (loadQualifier > 2)
	    slQualifier = "UNBEARABLE";

	sb.append("\ntotal load : "+this.being.getEquipment().getTotalLoad());
	sb.append(" lb    load is ");
	sb.append(slQualifier);
	//sb.append('\n');

	return sb;
    }

}
