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
 * $Id: Weapon.java,v 1.4 2002/08/15 15:49:45 jmettraux Exp $
 */

//
// Weapon.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// I've touch'd the highest point of all my greatness;
// And from that full meridian of my glory
// I haste now to my setting.  I shall fall,
// Like a bright exhalation in the evening
// And no man see me more.
// 		-- Shakespeare
// 

package burningbox.org.dsh.entities.equipment;


/**
 * As its name implies
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/08/15 15:49:45 $
 * <br>$id$ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class Weapon

    extends EquipmentPiece

{

    //
    // FIELDS

    protected String damages = "";
    protected String critical = "20 x2";
    protected String proficiencyType = "";

    //
    // CONSTRUCTORS

    public Weapon ()
    {
    }

    public Weapon
	(String damages,
	 String critical,
	 String proficiencyType)
    {
	this.damages = damages;
	this.critical = critical;
	this.proficiencyType = proficiencyType;
    }

    //
    // BEAN METHODS

    public String getDamages () { return this.damages; }
    public String getCritical () { return this.critical; }
    public String getProficiencyType () { return this.proficiencyType; }

    public void setDamages (String d) { this.damages = d; }
    public void setCritical (String c) { this.critical = c; }
    public void setProficiencyType (String pt) { this.proficiencyType = pt; }

    //
    // METHODS

    protected void copyTo (Weapon w)
    {
	super.copyTo(w);
	w.damages = new String(this.damages);
	w.critical = new String(this.critical);
	w.proficiencyType = new String(this.proficiencyType);
    }

    public Object clone ()
    {
	Weapon w = new Weapon();

	this.copyTo(w);

	return w;
    }

}
