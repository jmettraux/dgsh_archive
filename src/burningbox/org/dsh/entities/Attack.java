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
 * $Id: Attack.java,v 1.7 2002/08/15 15:49:45 jmettraux Exp $
 */

//
// Attack.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// Cold hands, no gloves.
// 

package burningbox.org.dsh.entities;

import burningbox.org.dsh.Utils;
import burningbox.org.dsh.Definitions;

/**
 * A class describing an attack a character is capable of
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/08/15 15:49:45 $
 * <br>$Id: Attack.java,v 1.7 2002/08/15 15:49:45 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class Attack

    implements Cloneable

{

    //
    // FIELDS
    
    protected String name = null;
    protected int[] attackModifier = null;
    protected String damage = null;
    protected String critical = "20 x2";
    protected String attackType = Definitions.MELEE;
    protected String description = null;

    protected transient Monster owner = null;

    //
    // CONSTRUCTORS

    public Attack ()
    {
    }

    public Attack 
	(String name,
	 int[] attackModifier,
	 String damage,
	 String attackType,
	 String description)
    {
	this.name = name;
	this.attackModifier = attackModifier;
	this.damage = damage;
	//this.critical = "20 x2";
	this.attackType = attackType;
	this.description = description;
    }

    public Attack 
	(String name,
	 int[] attackModifier,
	 String damage,
	 String critical,
	 String attackType,
	 String description)
    {
	this.name = name;
	this.attackModifier = attackModifier;
	this.damage = damage;
	this.critical = critical;
	this.attackType = attackType;
	this.description = description;
    }

    //
    // METHODS

    public String getName () { return this.name; }
    public int[] getAttackModifier () { return this.attackModifier; }
    public String getDamage () { return this.damage; }
    public String getCritical () { return this.critical; }
    public String getAttackType () { return this.attackType; }
    public String getDescription () { return this.description; }

    public void setName (String name) 
    { 
	this.name = name; 
    }
    public void setAttackModifier (int[] attackModifier) 
    { 
	this.attackModifier = attackModifier; 
    }
    public void setDamage (String damage) 
    { 
	this.damage = damage; 
    }
    public void setCritical (String s) 
    { 
	this.critical = s; 
    }
    public void setAttackType (String attackType)
    {
	this.attackType = attackType;
    }
    public void setDescription (String description) 
    { 
	this.description = description; 
    }

    //
    // modified access methods

    /*
    private int sumOwnerModifiers (String modifierDefinition)
    {
	int result = 0;

	result += this.owner.sumMiscModifiers(modifierDefinition);
	result += this.owner.sumEffectModifiers(modifierDefinition);

	return result;
    }
    */

    public int[] computeAttackModifier ()
    {
	int[] result = (int[])getAttackModifier().clone();
	    // clone so there is no iterative modifications ;-)

	if (this.owner == null)
	    return result;

	//System.out.println("Attack owner is "+this.owner.getName());

	int modifier = 0;
	modifier += this.owner.sumMiscModifiers(Definitions.ATTACK);
	if (this.attackType.equals(Definitions.RANGED))
	    modifier += this.owner.sumMiscModifiers(Definitions.RANGED);
	else if (this.attackType.equals(Definitions.MELEE))
	    modifier += this.owner.sumMiscModifiers(Definitions.MELEE);

	if (modifier != 0)
	    result = Utils.addToWholeArray(result, modifier);

	return result;
    }

    public String computeDamage ()
    {
	String damage = getDamage();

	if (this.owner == null)
	    return damage;

	int mod = this.owner.sumMiscModifiers(Definitions.DAMAGE);

	if (mod != 0)
	    damage += (burningbox.org.dsh.views.Utils.formatModifier(mod, 2));

	return damage;
    }

    //
    // owner

    public void setOwner (Monster m)
    {
	this.owner = m;
    }

    //
    // cloning stuff

    public Object clone ()
    {
	Attack a = new Attack();

	a.name = this.name;
	a.attackModifier = this.attackModifier;
	a.damage = this.damage;
	a.critical = this.critical;
	a.attackType = this.attackType;
	a.description = this.description;
	a.owner = null;

	return a;
    }

    //
    // a bit of userfriendly output
    
    public String toString ()
    {
	StringBuffer sb = new StringBuffer();

	sb.append("<attack nm=\"");
	sb.append(this.name);
	sb.append("\" mod=\"");
	sb.append(burningbox.org.dsh.views.Utils
	    .formatModifiers(this.attackModifier, 2));
	sb.append("\" dmg=\"");
	sb.append(this.damage);
	sb.append("\" crt=\"");
	sb.append(this.critical);
	sb.append("\" typ=\"");
	sb.append(this.attackType);
	sb.append("\" dsc=\"");
	sb.append(this.description);
	sb.append("\" />");

	return sb.toString();
    }

}
