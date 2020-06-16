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
 * $Id: Size.java,v 1.4 2002/08/05 07:05:27 jmettraux Exp $
 */

//
// Size.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// Q:	What do you call the money you pay to the government when
// 	you ride into the country on the back of an elephant?
// A:	A howdah duty.
// 

package burningbox.org.dsh.entities;

import burningbox.org.dsh.Definitions;
import burningbox.org.dsh.GameSession;


/**
 * A representation of a Being's size
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/08/05 07:05:27 $
 * <br>$Id: Size.java,v 1.4 2002/08/05 07:05:27 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class Size
{

    //
    // CONSTANTS

    public final static Size FINE	 	= new Size (6);
    public final static Size DIMINUTIVE	 	= new Size (7);
    public final static Size TINY	 	= new Size (8);
    public final static Size SMALL 		= new Size (9);
    public final static Size MEDIUM 		= new Size(10);
    public final static Size LARGE 		= new Size(11);
    public final static Size HUGE	 	= new Size(12);
    public final static Size GARGANTUAN	 	= new Size(13);
    public final static Size COLOSSAL	 	= new Size(14);

    //
    // FIELDS

    protected int size = 10;

    //
    // CONSTRUCTORS

    public Size ()
    {
    }

    public Size (int size)
    {
	this.size = size;
    }

    //
    // METHODS

    /**
     * Returns this size minus the other's size.
     * if the result is lower than 0 then the other is bigger.
     */
    public int compare (Size othersSize)
    {
	return (this.size - othersSize.size);
    }

    public boolean equals (Size othersSize)
    {
	return (this.size == othersSize.size);
    }

    public int computeSizeModifier ()
    {
	return (MEDIUM.size - this.size);
	// check this
    }

    public int getMassiveDamageThreshold ()
    {
	boolean useVariant = GameSession.getInstance()
	    .getBooleanEnvAttributeValue
		(Definitions.MASSIVE_DAMAGE_BASED_ON_SIZE);

	if ( ! useVariant) return 50;

	return (this.size - 5) * 10;
    }

    //
    // BEAN METHODS

    public int getSize () { return this.size; }

    public void setSize (int size) { this.size = size; }

}
