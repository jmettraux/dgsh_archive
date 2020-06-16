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
 * $Id: Container.java,v 1.6 2002/08/05 07:05:27 jmettraux Exp $
 */

//
// Container.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// Perilous to all of us are the devices of an art deeper than we ourselves
// possess.
// 		-- Gandalf the Grey [J.R.R. Tolkien, "Lord of the Rings"]
// 

package burningbox.org.dsh.entities.equipment;

import burningbox.org.dsh.entities.MiscModifier;


/**
 * A piece of equipment that can hold other pieces of equipment
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/08/05 07:05:27 $
 * <br>$Id: Container.java,v 1.6 2002/08/05 07:05:27 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class Container

    extends EquipmentPiece

{

    static org.apache.log4j.Logger log =
        org.apache.log4j.Logger.getLogger("dgsh.eq.Container");

    //
    // FIELDS

    protected java.util.List equipmentList = 
	new java.util.ArrayList(10);

    //
    // CONSTRUCTORS

    public Container ()
    {
    }

    public Container (String name)
    {
	this.name = name;
    }

    //
    // BEAN METHODS

    public java.util.List getEquipmentList () { return this.equipmentList; }

    public void setEquipmentList (java.util.List el) { this.equipmentList = el; }

    //
    // METHODS

    public java.util.Iterator iterator () 
    { 
	return this.equipmentList.iterator(); 
    }

    public EquipmentPiece get (String equipmentPieceName)
    {
	EquipmentPiece temporaryResult = null;

	java.util.Iterator it = iterator();
	while (it.hasNext())
	{
	    EquipmentPiece ep = (EquipmentPiece)it.next();

	    if (ep.getName().equals(equipmentPieceName))
		return ep;

	    if (ep instanceof Container)
		temporaryResult = ((Container)ep).get(equipmentPieceName);
	}

	return temporaryResult;
    }

    private String checkName (String name)
    {
	EquipmentPiece ep = get(name);

	if (ep == null)
	    return name;

	int usIndex = name.lastIndexOf('_');

	if (usIndex < 0)
	    return name + "_0";

	try
	{
	    String postUs = name.substring(usIndex+1);
	    int count = Integer.parseInt(postUs);
	    return name.substring(0, usIndex+1) + count;
	}
	catch (NumberFormatException nfe)
	{
	}
	return name + "_0";
    }

    public void add (EquipmentPiece piece)
    {
	piece.setName(checkName(piece.getName()));

	this.equipmentList.add(piece);
    }

    public void remove (EquipmentPiece piece)
    {
	this.equipmentList.remove(piece);
    }

    public void remove (int pieceIndex)
    {
	this.equipmentList.remove(pieceIndex);
    }
    
    /**
     * This method is an override
     * (so recursion can occur for containers inside containers)
     */
    public float getWeight ()
    {
	float result = (float)0.0;

	java.util.Iterator it = iterator();
	while (it.hasNext())
	{
	    try
	    {
		EquipmentPiece piece = (EquipmentPiece)it.next();
		result += piece.getWeight();
	    }
	    catch (ClassCastException cce)
	    {
		// ignore
	    }
	}

	return result;
    }

    public int getArmorMaxDexModifier ()
	//
	// this method is intended for use 
	// with Character.equipment.wornEquipement...
	// so there is no need for recursing potential worn containers
	//
    {
	int maxDex = 100;

	java.util.Iterator it = iterator();
	while (it.hasNext())
	{
	    EquipmentPiece piece = (EquipmentPiece)it.next();
	    if (piece instanceof Armor)
	    {
		int md = ((Armor)piece).getMaxDexModifier();
		if (md < maxDex)
		    maxDex = md;
	    }
	}

	return maxDex;
    }

    public int sumMiscModifiers (String definition)
    {
	int modifier = 0;

	modifier += MiscModifier.sumModifiers(definition, this.modifiers);

	java.util.Iterator it = this.iterator();
	while (it.hasNext())
	{
	    EquipmentPiece ep = (EquipmentPiece)it.next();
	    modifier += ep.sumMiscModifiers(definition);
	}

	return modifier;
    }

    protected void copyTo (Container c)
    {
	super.copyTo(c);
	c.equipmentList = 
	    (java.util.List)((java.util.ArrayList)this.equipmentList).clone();
    }

    public Object clone ()
    {
	Container c = new Container();

	this.copyTo(c);

	return c;
    }

}
