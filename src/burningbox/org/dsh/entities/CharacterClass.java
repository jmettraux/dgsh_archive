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
 * $Id: CharacterClass.java,v 1.8 2002/08/26 16:25:00 jmettraux Exp $
 */

//
// CharacterClass.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// Q:	How many IBM 370's does it take to execute a job?
// A:	Four, three to hold it down, and one to rip its head off.
// 

package burningbox.org.dsh.entities;

/**
 * A DnD class (not a Java class!)
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/08/26 16:25:00 $
 * <br>$Id: CharacterClass.java,v 1.8 2002/08/26 16:25:00 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class CharacterClass

    implements Named

{

    //
    // FIELDS
    
    protected String name = null;
    protected String fullName = null;
    protected String magicalAbility = null;
    protected int[][] baseAttackModifiers = null;
    protected int[][] spellsPerDay = null;
    protected SavingThrowSet[] savingThrowPerLevels = null;
    protected boolean spellListUser = false;
    protected boolean mustPrepareSpells = false;
    protected boolean usingDomainSpells = false;

    //
    // CONSTRUCTORS

    public CharacterClass ()
    {
    }

    //
    // BEAN METHODS
    
    public String getName () 
    { 
	return this.name; 
    }
    public String getFullName () 
    { 
	return this.fullName; 
    }
    public String getMagicalAbility () 
    { 
	return this.magicalAbility; 
    }
    public int[][] getBaseAttackModifiers () 
    { 
	return this.baseAttackModifiers; 
    }
    public int[][] getSpellsPerDay () 
    { 
	return this.spellsPerDay; 
    }
    public SavingThrowSet[] getSavingThrowPerLevels () 
    { 
	return this.savingThrowPerLevels; 
    }
    public boolean getSpellListUser () 
    { 
	return this.spellListUser; 
    }    
    public boolean getMustPrepareSpells () 
    { 
	return this.mustPrepareSpells; 
    }    
    public boolean isUsingDomainSpells () 
    { 
	return this.usingDomainSpells;
    }    


    public void setName (String name) 
    { 
	this.name = name; 
    }
    public void setFullName (String fullName) 
    { 
	this.fullName = fullName; 
    }
    public void setMagicalAbility (String magicalAbility) 
    { 
	this.magicalAbility = magicalAbility; 
    }
    public void setBaseAttackModifiers (int[][] baseAttackModifiers) 
    { 
	this.baseAttackModifiers = baseAttackModifiers; 
    }
    public void setSpellsPerDay (int[][] spellsPerDay) 
    { 
	this.spellsPerDay = spellsPerDay; 
    }
    public void setSavingThrowPerLevels (SavingThrowSet[] sts) 
    { 
	this.savingThrowPerLevels = sts; 
    }
    public void setSpellListUser (boolean b)
    {
	this.spellListUser = b;
    }
    public void setMustPrepareSpells (boolean b)
    {
	this.mustPrepareSpells = b;
    }
    public void setUsingDomainSpells (boolean b)
    {
	this.usingDomainSpells = b;
    }

    //
    // METHODS

    public boolean usesSpellList () { return this.spellListUser; }
    public boolean mustPrepareSpells () { return this.mustPrepareSpells; }

    public SavingThrowSet fetchSavingThrowSet (int level)
    {
	return this.savingThrowPerLevels[level-1];
    }

    public int[] getSpellsPerDay (int level)
    {
	if (this.spellsPerDay == null)
	    return null;

	try
	{
	    return this.spellsPerDay[level-1];
	}
	catch (ArrayIndexOutOfBoundsException e)
	{
	    return new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	}
    }

    //
    // STATIC METHODS

    /*
    public static SavingThrowSet getSavingThrowSet (java.util.List levels)
    {
	SavingThrowSet sum = new SavingThrowSet();

	java.util.Iterator it = levels.iterator();
	while (it.hasNext())
	{
	    ClassLevel cLevel = (ClassLevel)it.next();

	    CharacterClass cClass = DataSets
		.findCharacterClass(cLevel.getClassName());

	    if (cClass == null) continue;

	    SavingThrowSet set = 
		(cClass.getSavingThrowPerLevels())[cLevel.getLevelReached()];

	    sum = SavingThrowSet.sum(sum, set);
	}

	return sum;
    }
    */

    //
    // MAIN METHOD

    /*
    public static void main (String[] args)
    {
	try
	{
	    BeanSet bs = new BeanSet();

	    CharacterClass cc = new CharacterClass();
	    cc.setName("drd");
	    cc.setFullName("druid");
	    cc.setBaseAttackModifiers(new int[][] {
		new int[] { 0 },
		new int[] { 1 },
		new int[] { 2 },
		new int[] { 3 },
		new int[] { 3 },
		new int[] { 4 },
		new int[] { 5 },
		new int[] { 6, 1 },
		new int[] { 6, 1 },
		new int[] { 7, 2 },
		new int[] { 8, 3 },
		new int[] { 9, 4 },
		new int[] { 9, 4 },
		new int[] { 10, 5 },
		new int[] { 11, 6, 1 },
		new int[] { 12, 7, 2 },
		new int[] { 12, 7, 2 },
		new int[] { 13, 8, 3 },
		new int[] { 14, 9, 4 },
		new int[] { 15, 10, 5 }
	    });
	    cc.setSpellsPerDay(new int[][] {
	     new int[] { 3, 1, -100, -100, -100, -100, -100, -100, -100, -100 },
	     new int[] { 4, 2, -100, -100, -100, -100, -100, -100, -100, -100 },
	     new int[] { 4, 2, 1, -100, -100, -100, -100, -100, -100, -100 },
	     new int[] { 5, 3, 2, -100, -100, -100, -100, -100, -100, -100 },
	     new int[] { 5, 3, 2, 1, -100, -100, -100, -100, -100, -100 },
	     new int[] { 5, 3, 3, 2, -100, -100, -100, -100, -100, -100 },
	     new int[] { 6, 4, 3, 2, 1, -100, -100, -100, -100, -100 },
	     new int[] { 6, 4, 3, 3, 2, -100, -100, -100, -100, -100 },
	     new int[] { 6, 4, 4, 3, 2, 1, -100, -100, -100, -100 },
	     new int[] { 6, 4, 4, 3, 3, 2, -100, -100, -100, -100 },
	     new int[] { 6, 5, 4, 4, 3, 2, 1, -100, -100, -100 },
	     new int[] { 6, 5, 4, 4, 3, 3, 2, -100, -100, -100 },
	     new int[] { 6, 5, 5, 4, 4, 3, 2, 1, -100, -100 },
	     new int[] { 6, 5, 5, 4, 4, 3, 3, 2, -100, -100 },
	     new int[] { 6, 5, 5, 4, 4, 4, 3, 2, 1, -100 },
	     new int[] { 6, 5, 5, 5, 4, 4, 3, 3, 2, -100 },
	     new int[] { 6, 5, 5, 5, 5, 4, 4, 3, 2, 1 },
	     new int[] { 6, 5, 5, 5, 5, 4, 4, 3, 3, 2 },
	     new int[] { 6, 5, 5, 5, 5, 5, 4, 4, 3, 3 },
	     new int[] { 6, 5, 5, 5, 5, 5, 4, 4, 4, 4 },
	    });
	    cc.setSavingThrowPerLevels(new SavingThrowSet[] {
		new SavingThrowSet(2, 0, 2),
		new SavingThrowSet(3, 0, 3),
		new SavingThrowSet(3, 1, 3),
		new SavingThrowSet(4, 1, 4),
		new SavingThrowSet(4, 1, 4),
		new SavingThrowSet(5, 2, 5),
		new SavingThrowSet(5, 2, 5),
		new SavingThrowSet(6, 2, 6),
		new SavingThrowSet(6, 3, 6),
		new SavingThrowSet(7, 3, 7),
		new SavingThrowSet(7, 3, 7),
		new SavingThrowSet(8, 4, 8),
		new SavingThrowSet(8, 4, 8),
		new SavingThrowSet(9, 4, 9),
		new SavingThrowSet(9, 5, 9),
		new SavingThrowSet(10, 5, 10),
		new SavingThrowSet(10, 5, 10),
		new SavingThrowSet(11, 6, 11),
		new SavingThrowSet(11, 6, 11),
		new SavingThrowSet(12, 6, 12)
	    });

	    bs.add(cc);
	    bs.save("classes.xml");
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	}
    }
    */

    /*
    public static void main (String[] args)
    {
	try
	{
	    BeanSet bs = new BeanSet();

	    CharacterClass cc = new CharacterClass();
	    cc.setName("rog");
	    cc.setFullName("rogue");
	    cc.setBaseAttackModifiers(new int[][] {
		new int[] { 0 },
		new int[] { 1 },
		new int[] { 2 },
		new int[] { 3 },
		new int[] { 3 },
		new int[] { 4 },
		new int[] { 5 },
		new int[] { 6, 1 },
		new int[] { 6, 1 },
		new int[] { 7, 2 },
		new int[] { 8, 3 },
		new int[] { 9, 4 },
		new int[] { 9, 4 },
		new int[] { 10, 5 },
		new int[] { 11, 6, 1 },
		new int[] { 12, 7, 2 },
		new int[] { 12, 7, 2 },
		new int[] { 13, 8, 3 },
		new int[] { 14, 9, 4 },
		new int[] { 15, 10, 5 }
	    });
	    // cc.setSpellsPerDay(new int[][] {
	    //  new int[] { 3, 1, -100, -100, -100, -100, -100, -100, -100, -100 },
	    //  new int[] { 4, 2, -100, -100, -100, -100, -100, -100, -100, -100 },
	    //  new int[] { 4, 2, 1, -100, -100, -100, -100, -100, -100, -100 },
	    //  new int[] { 5, 3, 2, -100, -100, -100, -100, -100, -100, -100 },
	    //  new int[] { 5, 3, 2, 1, -100, -100, -100, -100, -100, -100 },
	    //  new int[] { 5, 3, 3, 2, -100, -100, -100, -100, -100, -100 },
	    //  new int[] { 6, 4, 3, 2, 1, -100, -100, -100, -100, -100 },
	    //  new int[] { 6, 4, 3, 3, 2, -100, -100, -100, -100, -100 },
	    //  new int[] { 6, 4, 4, 3, 2, 1, -100, -100, -100, -100 },
	    //  new int[] { 6, 4, 4, 3, 3, 2, -100, -100, -100, -100 },
	    //  new int[] { 6, 5, 4, 4, 3, 2, 1, -100, -100, -100 },
	    //  new int[] { 6, 5, 4, 4, 3, 3, 2, -100, -100, -100 },
	    //  new int[] { 6, 5, 5, 4, 4, 3, 2, 1, -100, -100 },
	    //  new int[] { 6, 5, 5, 4, 4, 3, 3, 2, -100, -100 },
	    //  new int[] { 6, 5, 5, 4, 4, 4, 3, 2, 1, -100 },
	    //  new int[] { 6, 5, 5, 5, 4, 4, 3, 3, 2, -100 },
	    //  new int[] { 6, 5, 5, 5, 5, 4, 4, 3, 2, 1 },
	    //  new int[] { 6, 5, 5, 5, 5, 4, 4, 3, 3, 2 },
	    //  new int[] { 6, 5, 5, 5, 5, 5, 4, 4, 3, 3 },
	    //  new int[] { 6, 5, 5, 5, 5, 5, 4, 4, 4, 4 },
	    // });
	    cc.setSavingThrowPerLevels(new SavingThrowSet[] {
		new SavingThrowSet(0,  2,  0),
		new SavingThrowSet(0,  3,  0),
		new SavingThrowSet(1,  3,  1),
		new SavingThrowSet(1,  4,  1),
		new SavingThrowSet(1,  4,  1),
		new SavingThrowSet(2,  5,  2),
		new SavingThrowSet(2,  5,  2),
		new SavingThrowSet(2,  6,  2),
		new SavingThrowSet(3,  6,  3),
		new SavingThrowSet(3,  7,  3),
		new SavingThrowSet(3,  7,  3),
		new SavingThrowSet(4,  8,  4),
		new SavingThrowSet(4,  8,  4),
		new SavingThrowSet(4,  9,  4),
		new SavingThrowSet(5,  9,  5),
		new SavingThrowSet(5, 10,  5),
		new SavingThrowSet(5, 10,  5),
		new SavingThrowSet(6, 11,  6),
		new SavingThrowSet(6, 11,  6),
		new SavingThrowSet(6, 12,  6)
	    });

	    bs.add(cc);
	    bs.save("rogue.xml");
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	}
    }
    */

    /*
    public static void main (String[] args)
    {
	try
	{
	    BeanSet bs = new BeanSet();

	    CharacterClass cc = new CharacterClass();
	    cc.setName("clr");
	    cc.setFullName("cleric");
	    cc.setBaseAttackModifiers(new int[][] {
		new int[] { 0 },
		new int[] { 1 },
		new int[] { 2 },
		new int[] { 3 },
		new int[] { 3 },
		new int[] { 4 },
		new int[] { 5 },
		new int[] { 6, 1 },
		new int[] { 6, 1 },
		new int[] { 7, 2 },
		new int[] { 8, 3 },
		new int[] { 9, 4 },
		new int[] { 9, 4 },
		new int[] { 10, 5 },
		new int[] { 11, 6, 1 },
		new int[] { 12, 7, 2 },
		new int[] { 12, 7, 2 },
		new int[] { 13, 8, 3 },
		new int[] { 14, 9, 4 },
		new int[] { 15, 10, 5 }
	    });
	    cc.setSpellsPerDay(new int[][] {
	     new int[] { 3, 1, -100, -100, -100, -100, -100, -100, -100, -100 },
	     new int[] { 4, 2, -100, -100, -100, -100, -100, -100, -100, -100 },
	     new int[] { 4, 2, 1, -100, -100, -100, -100, -100, -100, -100 },
	     new int[] { 5, 3, 2, -100, -100, -100, -100, -100, -100, -100 },
	     new int[] { 5, 3, 2, 1, -100, -100, -100, -100, -100, -100 },
	     new int[] { 5, 3, 3, 2, -100, -100, -100, -100, -100, -100 },
	     new int[] { 6, 4, 3, 2, 1, -100, -100, -100, -100, -100 },
	     new int[] { 6, 4, 3, 3, 2, -100, -100, -100, -100, -100 },
	     new int[] { 6, 4, 4, 3, 2, 1, -100, -100, -100, -100 },
	     new int[] { 6, 4, 4, 3, 3, 2, -100, -100, -100, -100 },
	     new int[] { 6, 5, 4, 4, 3, 2, 1, -100, -100, -100 },
	     new int[] { 6, 5, 4, 4, 3, 3, 2, -100, -100, -100 },
	     new int[] { 6, 5, 5, 4, 4, 3, 2, 1, -100, -100 },
	     new int[] { 6, 5, 5, 4, 4, 3, 3, 2, -100, -100 },
	     new int[] { 6, 5, 5, 4, 4, 4, 3, 2, 1, -100 },
	     new int[] { 6, 5, 5, 5, 4, 4, 3, 3, 2, -100 },
	     new int[] { 6, 5, 5, 5, 5, 4, 4, 3, 2, 1 },
	     new int[] { 6, 5, 5, 5, 5, 4, 4, 3, 3, 2 },
	     new int[] { 6, 5, 5, 5, 5, 5, 4, 4, 3, 3 },
	     new int[] { 6, 5, 5, 5, 5, 5, 4, 4, 4, 4 },
	    });
	    cc.setSavingThrowPerLevels(new SavingThrowSet[] {
		new SavingThrowSet( 2,  0,  2),
		new SavingThrowSet( 3,  0,  3),
		new SavingThrowSet( 3,  1,  3),
		new SavingThrowSet( 4,  1,  4),
		new SavingThrowSet( 4,  1,  4),
		new SavingThrowSet( 5,  2,  5),
		new SavingThrowSet( 5,  2,  5),
		new SavingThrowSet( 6,  2,  6),
		new SavingThrowSet( 6,  3,  6),
		new SavingThrowSet( 7,  3,  7),
		new SavingThrowSet( 7,  3,  7),
		new SavingThrowSet( 8,  4,  8),
		new SavingThrowSet( 8,  4,  8),
		new SavingThrowSet( 9,  4,  9),
		new SavingThrowSet( 9,  5,  9),
		new SavingThrowSet(10,  5, 10),
		new SavingThrowSet(10,  5, 10),
		new SavingThrowSet(11,  6, 11),
		new SavingThrowSet(11,  6, 11),
		new SavingThrowSet(12,  6, 12)
	    });

	    bs.add(cc);
	    bs.save("cleric.xml");
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	}
    }
    */

    /*
    public static void main (String[] args)
    {
	try
	{
	    BeanSet bs = new BeanSet();

	    CharacterClass cc = new CharacterClass();
	    cc.setName("sor");
	    cc.setFullName("sorcerer");
	    cc.setBaseAttackModifiers(new int[][] {
		new int[] { 0 },
		new int[] { 1 },
		new int[] { 1 },
		new int[] { 2 },
		new int[] { 2 },
		new int[] { 3 },
		new int[] { 3 },
		new int[] { 4 },
		new int[] { 4 },
		new int[] { 5 },
		new int[] { 5 },
		new int[] { 6, 1 },
		new int[] { 6, 1 },
		new int[] { 7, 2 },
		new int[] { 7, 2 },
		new int[] { 8, 3 },
		new int[] { 8, 3 },
		new int[] { 9, 4 },
		new int[] { 9, 4 },
		new int[] { 10, 5 }
	    });
	    cc.setSpellsPerDay(new int[][] {
	     new int[] { 5, 3, -100, -100, -100, -100, -100, -100, -100, -100 },
	     new int[] { 6, 4, -100, -100, -100, -100, -100, -100, -100, -100 },
	     new int[] { 6, 5, -100, -100, -100, -100, -100, -100, -100, -100 },
	     new int[] { 6, 6, 3, -100, -100, -100, -100, -100, -100, -100 },
	     new int[] { 6, 6, 4, -100, -100, -100, -100, -100, -100, -100 },
	     new int[] { 6, 6, 5, 3, -100, -100, -100, -100, -100, -100 },
	     new int[] { 6, 6, 6, 4, -100, -100, -100, -100, -100, -100 },
	     new int[] { 6, 6, 6, 5, 3, -100, -100, -100, -100, -100 },
	     new int[] { 6, 6, 6, 6, 4, -100, -100, -100, -100, -100 },
	     new int[] { 6, 6, 6, 6, 5, 3, -100, -100, -100, -100 },
	     new int[] { 6, 6, 6, 6, 6, 4, -100, -100, -100, -100 },
	     new int[] { 6, 6, 6, 6, 6, 5, 3, -100, -100, -100 },
	     new int[] { 6, 6, 6, 6, 6, 6, 4, -100, -100, -100 },
	     new int[] { 6, 6, 6, 6, 6, 6, 5, 3, -100, -100 },
	     new int[] { 6, 6, 6, 6, 6, 6, 6, 4, -100, -100 },
	     new int[] { 6, 6, 6, 6, 6, 6, 6, 5, 3, -100 },
	     new int[] { 6, 6, 6, 6, 6, 6, 6, 6, 4, -100 },
	     new int[] { 6, 6, 6, 6, 6, 6, 6, 6, 5, 3 },
	     new int[] { 6, 6, 6, 6, 6, 6, 6, 6, 6, 4 },
	     new int[] { 6, 6, 6, 6, 6, 6, 6, 6, 6, 6 },
	    });
	    cc.setSavingThrowPerLevels(new SavingThrowSet[] {
		new SavingThrowSet( 0,  0,  2),
		new SavingThrowSet( 0,  0,  3),
		new SavingThrowSet( 1,  1,  3),
		new SavingThrowSet( 1,  1,  4),
		new SavingThrowSet( 1,  1,  4),
		new SavingThrowSet( 2,  2,  5),
		new SavingThrowSet( 2,  2,  5),
		new SavingThrowSet( 2,  2,  6),
		new SavingThrowSet( 3,  3,  6),
		new SavingThrowSet( 3,  3,  7),
		new SavingThrowSet( 3,  3,  7),
		new SavingThrowSet( 4,  4,  8),
		new SavingThrowSet( 4,  4,  8),
		new SavingThrowSet( 4,  4,  9),
		new SavingThrowSet( 5,  5,  9),
		new SavingThrowSet( 5,  5, 10),
		new SavingThrowSet( 5,  5, 10),
		new SavingThrowSet( 6,  6, 11),
		new SavingThrowSet( 6,  6, 11),
		new SavingThrowSet( 6,  6, 12)
	    });

	    bs.add(cc);
	    bs.save("sorcerer.xml");
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	}
    }
    */

    /*
    public static void main (String[] args)
    {
	try
	{
	    BeanSet bs = new BeanSet();

	    CharacterClass cc = new CharacterClass();
	    cc.setName("wiz");
	    cc.setFullName("wizard");
	    cc.setBaseAttackModifiers(new int[][] {
		new int[] { 0 },
		new int[] { 1 },
		new int[] { 1 },
		new int[] { 2 },
		new int[] { 2 },
		new int[] { 3 },
		new int[] { 3 },
		new int[] { 4 },
		new int[] { 4 },
		new int[] { 5 },
		new int[] { 5 },
		new int[] { 6, 1 },
		new int[] { 6, 1 },
		new int[] { 7, 2 },
		new int[] { 7, 2 },
		new int[] { 8, 3 },
		new int[] { 8, 3 },
		new int[] { 9, 4 },
		new int[] { 9, 4 },
		new int[] { 10, 5 }
	    });
	    cc.setSpellsPerDay(new int[][] {
	     new int[] { 3, 1, -100, -100, -100, -100, -100, -100, -100, -100 },
	     new int[] { 4, 2, -100, -100, -100, -100, -100, -100, -100, -100 },
	     new int[] { 4, 2, 1, -100, -100, -100, -100, -100, -100, -100 },
	     new int[] { 4, 3, 2, -100, -100, -100, -100, -100, -100, -100 },
	     new int[] { 4, 3, 2, 1, -100, -100, -100, -100, -100, -100 },
	     new int[] { 4, 3, 3, 2, -100, -100, -100, -100, -100, -100 },
	     new int[] { 4, 4, 3, 2, 1, -100, -100, -100, -100, -100 },
	     new int[] { 4, 4, 3, 3, 2, -100, -100, -100, -100, -100 },
	     new int[] { 4, 4, 4, 3, 2, 1, -100, -100, -100, -100 },
	     new int[] { 4, 4, 4, 3, 3, 2, -100, -100, -100, -100 },
	     new int[] { 4, 4, 4, 4, 3, 2, 1, -100, -100, -100 },
	     new int[] { 4, 4, 4, 4, 3, 3, 2, -100, -100, -100 },
	     new int[] { 4, 4, 4, 4, 4, 3, 2, 1, -100, -100 },
	     new int[] { 4, 4, 4, 4, 4, 3, 3, 2, -100, -100 },
	     new int[] { 4, 4, 4, 4, 4, 4, 3, 2, 1, -100 },
	     new int[] { 4, 4, 4, 4, 4, 4, 3, 3, 2, -100 },
	     new int[] { 4, 4, 4, 4, 4, 4, 4, 3, 2, 1 },
	     new int[] { 4, 4, 4, 4, 4, 4, 4, 3, 3, 2 },
	     new int[] { 4, 4, 4, 4, 4, 4, 4, 4, 3, 3 },
	     new int[] { 4, 4, 4, 4, 4, 4, 4, 4, 4, 4 }
	    });
	    cc.setSavingThrowPerLevels(new SavingThrowSet[] {
		new SavingThrowSet( 0,  0,  2),
		new SavingThrowSet( 0,  0,  3),
		new SavingThrowSet( 1,  1,  3),
		new SavingThrowSet( 1,  1,  4),
		new SavingThrowSet( 1,  1,  4),
		new SavingThrowSet( 2,  2,  5),
		new SavingThrowSet( 2,  2,  5),
		new SavingThrowSet( 2,  2,  6),
		new SavingThrowSet( 3,  3,  6),
		new SavingThrowSet( 3,  3,  7),
		new SavingThrowSet( 3,  3,  7),
		new SavingThrowSet( 4,  4,  8),
		new SavingThrowSet( 4,  4,  8),
		new SavingThrowSet( 4,  4,  9),
		new SavingThrowSet( 5,  5,  9),
		new SavingThrowSet( 5,  5, 10),
		new SavingThrowSet( 5,  5, 10),
		new SavingThrowSet( 6,  6, 11),
		new SavingThrowSet( 6,  6, 11),
		new SavingThrowSet( 6,  6, 12)
	    });

	    bs.add(cc);
	    bs.save("wizard.xml");
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	}
    }
    */

}
