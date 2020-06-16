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
 * $Id: EquipmentLocator.java,v 1.6 2002/08/05 07:05:27 jmettraux Exp $
 */

//
// EquipmentLocator.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// If you stand on your head, you will get footprints in your hair.
// 

package burningbox.org.dsh.entities.equipment;

import burningbox.org.dsh.*;
import burningbox.org.dsh.entities.*;


/**
 * A class for encapsulating info about an EquipmentPiece location
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/08/05 07:05:27 $
 * <br>$Id: EquipmentLocator.java,v 1.6 2002/08/05 07:05:27 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class EquipmentLocator
{

    //
    // FIELDS

    protected Being owner = null;
    protected Container container = null;
    protected EquipmentPiece piece = null;

    //
    // CONSTRUCTORS

    protected EquipmentLocator ()
    {
    }

    public EquipmentLocator 
	(Being owner,
	 Container container,
	 EquipmentPiece piece)
    {
	this.owner = owner;
	this.container = container;
	this.piece = piece;
    }

    //
    // METHODS

    public Being getOwner () { return this.owner; }
    public Container getContainer () { return this.container; }
    public EquipmentPiece getEquipmentPiece () { return this.piece; }

    /**
     * removes the located equipment from its current container
     */
    public EquipmentPiece removeFromContainer ()
    {
	if (this.container == null)
	{
	    if (this.piece != null)
		return piece;

	    return null;
	}

	this.container.remove(this.piece);
	return piece;
    }

    public String toString ()
    {
	StringBuffer sb = new StringBuffer();

	if (this.owner != null)
	    sb.append(this.owner.getName());
	sb.append('.');
	if (this.container != null)
	    sb.append(this.container.getName());
	sb.append('.');
	if (this.piece != null)
	    sb.append(this.piece.getName());

	return sb.toString();
    }
    
    //
    // STATIC METHODS

    public static void transfer 
	(EquipmentLocator source, EquipmentLocator target)
    {
	EquipmentPiece piece = source.removeFromContainer();

	if (target.container != null)
	    target.container.add(piece);

	target.piece = piece;
    }

    private static boolean isAContainerName (String name)
    {
	if (name.length() == 1)
	{
	    char c = name.charAt(0);
	    return 
		(c == 'w' ||
		 c == 'h' ||
		 c == 'c');
	}
	return 
	    ("worn".equals(name) ||
	     "held".equals(name) ||
	     "carried".equals(name));
    }

    /**
     * like locateEquipment(eqLocator, null)
     */
    public static EquipmentLocator locateEquipment (String eqlocator)
    {
	return locateEquipment(eqlocator, null);
    }

    /**
     * this method awaits an 'URL' or an 'Equipment locator' like
     * 'larm', 'lidda.held', 'lidda.worn.larm' or 'held.sswdp1'
     * and returns the corresponding equipment piece 
     */
    public static EquipmentLocator locateEquipment
	(String eqlocator, Being currentBeing)
    {
	EquipmentLocator el = new EquipmentLocator();

	String s1 = null;
	String s2 = null;
	String s3 = null;

	String ownerName = null;
	String containerName = null;
	String equipmentName = null;

	try
	{
	    java.util.StringTokenizer st = 
		new java.util.StringTokenizer(eqlocator, ".");

	    s1 = st.nextToken();
	    s2 = st.nextToken();
	    s3 = st.nextToken();
	}
	catch (Exception e)
	{
	}

	if (s1 == null) return null;

	if (s2 == null &&
	    s3 == null)
	    //
	    // 'worn'
	    //     or
	    // 'larm' 
	    //     (leather armor)
	    //
	{
	    if (isAContainerName(s1))
	    {
		containerName = s1;
	    }
	    else
	    {
		equipmentName = s1;
	    }
	}
	else if (s3 == null)
	    // 
	    // 'lidda.held'
	    //     or
	    // 'held.sswdp1' 
	    //     (short sword +1)
	    //
	{
	    if (isAContainerName(s1))
	    {
		containerName = s1;
		equipmentName = s2;
	    }
	    else
	    {
		ownerName = s1;
		containerName = s2;
	    }
	}
	else
	    //
	    // full absolute 'lidda.worn.larm'
	    //
	{
	    ownerName = s1;
	    containerName = s2;
	    equipmentName = s3;
	}

	if (containerName == null)
	    // absolute equipment locator
	{
	    if (equipmentName != null)
		el.piece = DataSets.findEquipment(equipmentName);
	    return el;
	}

	if (ownerName == null)
	{
	    el.owner = currentBeing;
	}
	else
	{
	    el.owner = GameSession.getInstance().findBeing(ownerName);
	}
	if (el.owner == null)
	    return null;

	el.container = el.owner.getEquipment().getContainer(containerName);

	if (el.container == null)
	    return null;

	// absolute locator 'lidda.h.dgrp1'
	
	if (equipmentName != null)
	{
	    el.piece = el.container.get(equipmentName);
	}
	return el;
    }

}
