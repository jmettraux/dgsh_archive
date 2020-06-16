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
 * $Id: RangedWeapon.java,v 1.3 2002/06/26 10:02:36 jmettraux Exp $
 */

//
// RangedWeapon.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// Whatever it is, I fear Greeks even when they bring gifts.
// 		-- Publius Vergilius Maro (Virgil)
// 

package burningbox.org.dsh.entities.equipment;

/**
 * A Weapon that you can use with a -2 per range increment
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/06/26 10:02:36 $
 * <br>$Id: RangedWeapon.java,v 1.3 2002/06/26 10:02:36 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class RangedWeapon

    extends Weapon

{

    //
    // FIELDS

    protected int rangeIncrement = 0;

    //
    // CONSTRUCTORS

    public RangedWeapon ()
    {
	super();
    }

    //
    // BEAN METHODS

    public int getRangeIncrement () { return this.rangeIncrement; }

    public void setRangeIncrement (int i) { this.rangeIncrement = i; }

    //
    // METHODS

    protected void copyTo (RangedWeapon w)
    {
	super.copyTo(w);
	w.rangeIncrement = this.rangeIncrement;
    }

    public Object clone ()
    {
	RangedWeapon w = new RangedWeapon();

	this.copyTo(w);

	return w;
    }

}