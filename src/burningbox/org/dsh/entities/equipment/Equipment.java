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
 * $Id: Equipment.java,v 1.5 2002/07/22 05:46:53 jmettraux Exp $
 */

//
// Equipment.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// The smallest worm will turn being trodden on.
// 		-- William Shakespeare, "Henry VI"
// 

package burningbox.org.dsh.entities.equipment;

import burningbox.org.dsh.Definitions;
import burningbox.org.dsh.entities.Size;


/**
 * A set of equipment :  worn, held or carried
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/07/22 05:46:53 $
 * <br>$Id: Equipment.java,v 1.5 2002/07/22 05:46:53 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class Equipment
{

    //static org.apache.log4j.Logger log =
    //    org.apache.log4j.Logger.getLogger(Equipment.class.getName());

    public final static int[][] loadRanges =
    {
	new int[] { 0, 0, 0 },
	new int[] { 3, 6, 10 }, // STRENGTH 1
	new int[] { 6, 13, 20 },
	new int[] { 10, 20, 30 },
	new int[] { 13, 26, 40 },
	new int[] { 20,40,60 },
	new int[] { 23,46,70 },
	new int[] { 26,53,80 },
	new int[] { 30,60,90 },
	new int[] { 33,66,100 },
	new int[] { 38,76,115 },
	new int[] { 43,86,130 },
	new int[] { 50,100,150 },
	new int[] { 58,116,175 },
	new int[] { 66,133,200 },
	new int[] { 76,153,230 },
	new int[] { 86,173,260 },
	new int[] { 100,200,300 }, // STRENGTH 18
	new int[] { 116,233,350 },
	new int[] { 133,266,400 },
	new int[] { 153,306,460 },
	new int[] { 173,346,520 },
	new int[] { 200,400,600 },
	new int[] { 233,466,700 },
	new int[] { 266,533,800 },
	new int[] { 306,617,920 },
	new int[] { 346,693,1040 },
	new int[] { 400,800,1200 },
	new int[] { 466,993,1400 } // STRENGTH 29
    };

    //
    // FIELDS

    protected Container wornEquipment = new Container("worn");
    protected Container heldEquipment = new Container("held");
    protected Container carriedEquipment = new Container("carried");

    protected Wallet wallet = new Wallet();

    //
    // CONSTRUCTORS

    public Equipment ()
    {
    }

    //
    // BEAN METHODS

    public Container getWornEquipment () { return this.wornEquipment; }
    public Container getHeldEquipment () { return this.heldEquipment; }
    public Container getCarriedEquipment () { return this.carriedEquipment; }

    public Wallet getWallet () { return this.wallet; }

    public void setWornEquipment (Container c) { this.wornEquipment = c; }
    public void setHeldEquipment (Container c) { this.heldEquipment = c; }
    public void setCarriedEquipment (Container c) { this.carriedEquipment = c; }

    public void setWallet (Wallet w) { this.wallet = w; }

    //
    // METHODS

    public float getWornLoad ()
    {
	if (this.wornEquipment == null) return (float)0.0;

	return this.wornEquipment.getWeight();
    }

    public float getHeldLoad ()
    {
	if (this.heldEquipment == null) return (float)0.0;

	return this.heldEquipment.getWeight();
    }

    public float getCarriedLoad ()
    {
	if (this.carriedEquipment == null) return (float)0.0;

	return this.carriedEquipment.getWeight();
    }

    public float getMoneyLoad ()
    {
	return this.wallet.computeMoneyLoad();
    }

    public float getTotalLoad ()
    {
	return
	    getWornLoad()+
	    getHeldLoad()+
	    getCarriedLoad()+
	    getMoneyLoad();
    }

    public int getLoadQualifier (int strengthScore, Size size)
    {
	float load = getTotalLoad();

	//System.out.print("Total load is "+load+"\n");

	if (size.equals(Size.FINE))
	    load = load * 8;
	else if (size.equals(Size.DIMINUTIVE))
	    load = load * 4;
	else if (size.equals(Size.TINY))
	    load = load * 2;
	else if (size.equals(Size.SMALL))
	    load = load * (float)1.33;
	else if (size.equals(Size.LARGE))
	    load = load * (float)0.5;
	else if (size.equals(Size.HUGE))
	    load = load * (float)0.25;
	else if (size.equals(Size.GARGANTUAN))
	    load = load * (float)0.125;
	else if (size.equals(Size.COLOSSAL))
	    load = load * (float)0.0625;

	//System.out.print("Load after size adjustment is "+load+"\n");
	//System.out.println("Strength is "+strengthScore);

	int[] loadRange = loadRanges[--strengthScore];

	//System.out.print
	//    ("Load ranges : "+
	//     loadRange[0]+" / "+loadRange[1]+" / "+loadRange[2]+"\n");

	if (load < loadRange[0])
	    return Definitions.LIGHT_LOAD;
	if (load < loadRange[1])
	    return Definitions.MEDIUM_LOAD;
	if (load < loadRange[2])
	    return Definitions.HEAVY_LOAD;
	return Definitions.UNBEARABLE_LOAD;
    }

    public int getMaxDexModifier (int strengthScore, Size size)
    {
	int loadQuali = getLoadQualifier(strengthScore, size);

	if (loadQuali == Definitions.LIGHT_LOAD)
	    return 1000; // is it enough ?

	if (loadQuali == Definitions.MEDIUM_LOAD)
	{
	    return 3;
	}

	return 1; // HEAVY LOAD
    }

    public int computeArmorMaxDexModifier ()
    {
	int maxModifier = 10;

	java.util.Iterator it = this.wornEquipment.iterator();
	while (it.hasNext())
	{
	    Object o = it.next();

	    if ( ! (o instanceof Armor)) continue;

	    int maxDexModifier = ((Armor)o).getMaxDexModifier();

	    if (maxDexModifier < maxModifier)
		maxModifier = maxDexModifier;
	}

	return maxModifier;
    }

    public int sumMiscModifiers (String definition)
    {
	int modifier = 0;

	modifier += this.wornEquipment.sumMiscModifiers(definition);
	modifier += this.heldEquipment.sumMiscModifiers(definition);

	return modifier;
    }

    public int sumArmorClass ()
    {
	int acModifier = 0;

	java.util.Iterator it = this.wornEquipment.iterator();
	while (it.hasNext())
	{
	    Object o = it.next();

	    if ( ! (o instanceof Armor)) continue;

	    acModifier += ((Armor)o).getArmorClassBonus();
	}

	return acModifier;
    }

    public int sumArmorSpellFail ()
    {
	int fail = 0;

	java.util.Iterator it = this.wornEquipment.iterator();
	while (it.hasNext())
	{
	    Object o = it.next();

	    if ( ! (o instanceof Armor)) continue;

	    fail += ((Armor)o).getSpellFailurePercent();
	}

	return fail;
    }

    public int sumArmorPenalty ()
    {
	int ap = 0;

	java.util.Iterator it = this.wornEquipment.iterator();
	while (it.hasNext())
	{
	    Object o = it.next();

	    if ( ! (o instanceof Armor)) continue;

	    ap += ((Armor)o).getArmorPenalty();
	}

	return ap;
    }

    public Container getContainer (String containerName)
    {
	if (containerName.startsWith("w"))
	    return this.wornEquipment;
	if (containerName.startsWith("h"))
	    return this.heldEquipment;
	if (containerName.startsWith("c"))
	    return this.carriedEquipment;
	return null;
    }

    public java.util.List findHeldWeapons ()
    {
	Container c = getHeldEquipment();

	java.util.List result = new java.util.ArrayList();

	java.util.Iterator it = c.iterator();
	while (it.hasNext())
	{
	    EquipmentPiece ep = (EquipmentPiece)it.next();

	    if (ep instanceof Weapon) result.add(ep);
	}

	return result;
    }

    //
    // STATIC METHODS

}
