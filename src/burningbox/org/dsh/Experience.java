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
 * $Id: Experience.java,v 1.1 2002/07/15 06:56:24 jmettraux Exp $
 */

//
// Experience.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@yahoo.com)
//

package burningbox.org.dsh;

import burningbox.org.dsh.entities.Being;
import burningbox.org.dsh.entities.Named;
import burningbox.org.dsh.entities.BeanSet;


/**
 * Static methods for determining experience awards (or levels reached)
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/07/15 06:56:24 $
 * <br>$Id: Experience.java,v 1.1 2002/07/15 06:56:24 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public abstract class Experience
{

    //
    // CONSTANTS

    final static int[][] awards = new int[][]
    {
	new int[] { 300, 600, 900, 1350, 1800, 2700, 3600, 5400, 7200, 10800, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
	new int[] { 300, 600, 800, 1200, 1600, 2400, 3200, 4800, 6400, 9600, 12800, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
	new int[] { 300, 500, 750, 1000, 1500, 2250, 3000, 4500, 6000, 9000, 12000, 18000, -1, -1, -1, -1, -1, -1, -1, -1 },
	new int[] { 300, 450, 600, 900, 1200, 1800, 2700, 3600, 5400, 7200, 10800, 14400, 21600, -1, -1, -1, -1, -1, -1, -1 },
	new int[] { 263, 394, 525, 700, 1050, 1400, 2100, 3150, 4200, 6300, 8400, 12600, 16800, 25200, -1, -1, -1, -1, -1, -1 },
	new int[] { 200, 300, 450, 600, 800, 1200, 1600, 2400, 3600, 4800, 7200, 9600, 14400, 19200, 28800, -1, -1, -1, -1, -1 },
	new int[] { 0,   225, 338, 506, 675, 900, 1350, 1800, 2700, 4050, 5400, 8100, 10800, 16200, 21600, 32400, -1, -1, -1, -1 },
	new int[] { 0,   0,   250, 375, 563, 750, 1000, 1500, 2000, 3000, 4500, 6000, 9000, 12000, 18000, 24000, 36000, -1, -1, -1 },
	new int[] { 0,   0,   0,   275, 413, 619, 825, 1100, 1650, 2200, 3300, 4950, 6600, 9900, 13200, 19800, 26400, 39600, -1, -1 },
	new int[] { 0,   0,   0,   0,   300, 450, 675, 900, 1200, 1800, 2400, 3600, 5400, 7200, 10800, 14400, 21600, 28800, 43200, -1 },
	new int[] { 0,   0,   0,   0,   0,   325, 488, 731, 975, 1300, 1950, 2600, 3900, 5850, 7800, 11700, 15600, 23400, 31200, 46800 },
	new int[] { 0,   0,   0,   0,   0,   0,   350, 525, 788, 1050, 1400, 2100, 2800, 4200, 6300, 8400, 12600, 16800, 25200, 33600 },
	new int[] { 0,   0,   0,   0,   0,   0,   0,   375, 563, 844, 1125, 1500, 2250, 3000, 4500, 6750, 9000, 13500, 18000, 27000 },
	new int[] { 0,   0,   0,   0,   0,   0,   0,   0,   400, 600, 900, 1200, 1600, 2400, 3200, 4800, 7200, 9600, 14400, 19200 },
	new int[] { 0,   0,   0,   0,   0,   0,   0,   0,   0,   425, 638, 956, 1275, 1700, 2550, 3400, 5100, 7650, 10200, 15300 },
	new int[] { 0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   450, 675, 1013, 1350, 1800, 2700, 3600, 5400, 8100, 10800 },
	new int[] { 0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   475, 713, 1069, 1425, 1900, 2850, 3800, 5700, 8550 },
	new int[] { 0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   500, 750, 1000, 1500, 2000, 3000, 4000, 6000 }
    };

    //
    // METHODS

    public static int computeAward (int partyLevel, float challengeRating)
    {
	if (partyLevel < 4) 
	    partyLevel = 0;
	else
	    partyLevel -= 3;

	if (challengeRating < 1.0)
	{
	    return (int)(challengeRating * (float)awards[partyLevel][0]);
	}

	int icr = (int)challengeRating - 1;

	return awards[partyLevel][icr];
    }

    private static int computeChallengeRating (java.util.Iterator it)
    {
	int result = 0;

	while (it.hasNext())
	{
	    Named n = (Named)it.next();

	    if (n instanceof Being)
		result += ((Being)n).computeChallengeRating();
	}

	return result;
    }

    public static int computeChallengeRating (BeanSet bs)
    {
	return computeChallengeRating(bs.iterator());
    }

    public static int computeChallengeRating (java.util.List l)
    {
	return computeChallengeRating(l.iterator());
    }

}
