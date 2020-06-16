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
 * $Id: Utils.java,v 1.2 2002/09/12 06:30:36 jmettraux Exp $
 */

//
// Utils.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@yahoo.com)
//

package burningbox.org.dsh.entities;

/**
 * Some static methods for the entities package.
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/09/12 06:30:36 $
 * <br>$Id: Utils.java,v 1.2 2002/09/12 06:30:36 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class Utils
{
    private final static int[][] FIGHTER_ATTACK_MODIFIERS
	= new int[][] 
	{
	    new int[] { 0 }, // so array[0] is { 0 }...
	    new int[] { 1 },
	    new int[] { 2 },
	    new int[] { 3 },
	    new int[] { 4 },
	    new int[] { 5 },
	    new int[] { 6, 1 },
	    new int[] { 7, 2 },
	    new int[] { 8, 3 },
	    new int[] { 9, 4 },
	    new int[] { 10, 5 },
	    new int[] { 11, 6, 1 },
	    new int[] { 12, 7, 2 },
	    new int[] { 13, 8, 3 },
	    new int[] { 14, 9, 4 },
	    new int[] { 15, 10, 5 },
	    new int[] { 16, 11, 6, 1 },
	    new int[] { 17, 12, 7, 2 },
	    new int[] { 18, 13, 8, 3 },
	    new int[] { 19, 14, 9, 4 },
	    new int[] { 20, 15, 10, 5 },
	    new int[] { 21, 16, 11, 6, 1 },
	    new int[] { 22, 17, 12, 7, 2 },
	    new int[] { 23, 18, 13, 8, 3 },
	    new int[] { 24, 19, 14, 9, 4 },
	    new int[] { 25, 20, 15, 10, 5 },
	    new int[] { 26, 21, 16, 11, 6, 1 }
	};

    /**
     * This brave methods rectifies an attack modifier (after 
     * multiclass cumulation) to make it a real 
     * multi-attack attack modifier...
     */
    public static int[] rectifyAttackModifier (int[] attackModifier)
    {
	/*
	System.out.println
	    ("BAB : "+
	     burningbox.org.dsh.views.Utils.formatModifiers(attackModifier, 2));
	*/

	if (attackModifier == null || 
	    attackModifier.length < 1)
	{
	    return attackModifier;
	}

	//System.out.println("am[0] = "+attackModifier[0]);

	int[] result = 
	    (int[])FIGHTER_ATTACK_MODIFIERS[attackModifier[0]].clone();

	//
	// I have to clone the result, because the whole array is final, not 
	// its sub arrays...
	//

	/*
	System.out.println
	    ("rectified BAB : "+
	     burningbox.org.dsh.views.Utils.formatModifiers(result, 2));
	*/

	return result;
    }

    //
    // the main method, for testing purpose
    
    /*
    public static void main (String[] args)
    {
	for (int i=0; i<FIGHTER_ATTACK_MODIFIERS.length; i++)
	{
	    int[] mod = FIGHTER_ATTACK_MODIFIERS[i];
	    int[] mod2 = rectifyAttackModifier(new int[] { i });

	    System.out.println
		(""+i+" --> "+
		 burningbox.org.dsh.views.Utils.formatModifiers(mod, 2)+" | "+
		 burningbox.org.dsh.views.Utils.formatModifiers(mod2, 2));
	}
    }
    */
}
