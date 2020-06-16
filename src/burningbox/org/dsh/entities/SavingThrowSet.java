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
 * $Id: SavingThrowSet.java,v 1.2 2002/06/26 10:02:36 jmettraux Exp $
 */

//
// SavingThrowSet.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// I don't know half of you half as well as I should like; and I like less
// than half of you half as well as you deserve.
// 		-- J. R. R. Tolkien
// 

package burningbox.org.dsh.entities;

import burningbox.org.dsh.Definitions;


/**
 * The triplet Reflex / Will / Fortitude
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/06/26 10:02:36 $
 * <br>$Id: SavingThrowSet.java,v 1.2 2002/06/26 10:02:36 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class SavingThrowSet
{

    //
    // CONSTANTS

    //
    // FIELDS

    protected int reflexModifier = 0;
    protected int willModifier = 0;
    protected int fortitudeModifier = 0;

    //
    // CONSTRUCTORS

    public SavingThrowSet ()
    {
    }

    public SavingThrowSet
	(int fortitudeModifier,
	 int reflexModifier,
	 int willModifier)
    {
	this.reflexModifier = reflexModifier;
	this.willModifier = willModifier;
	this.fortitudeModifier = fortitudeModifier;
    }

    //
    // METHODS

    /**
     * Take either SavingThrowSet.REFLEX, .WILL or .FORTITUDE value
     * as parameter and you will get the modifier value back
     * If the parameter is given an incorrect value, this method will
     * return 0.
     */
    public int fetchModifier (String savingThrowIdentifier)
    {
	if (savingThrowIdentifier.equals(Definitions.REFLEX))
	    return this.reflexModifier;
	if (savingThrowIdentifier.equals(Definitions.WILL))
	    return this.willModifier;
	if (savingThrowIdentifier.equals(Definitions.FORTITUDE))
	    return this.fortitudeModifier;
	return 0;
    }

    //
    // BEAN METHODS
    
    public int getReflexModifier () { return this.reflexModifier; }
    public int getWillModifier () { return this.willModifier; }
    public int getFortitudeModifier () { return this.fortitudeModifier; }

    public void setReflexModifier (int i) { this.reflexModifier = i; }
    public void setWillModifier (int i) { this.willModifier = i; }
    public void setFortitudeModifier (int i) { this.fortitudeModifier = i; }

    //
    // STATIC METHODS

    public static SavingThrowSet sum (SavingThrowSet s1, SavingThrowSet s2)
    {
	SavingThrowSet result = new SavingThrowSet();

	result.reflexModifier = s1.reflexModifier + s2.reflexModifier;
	result.willModifier = s1.willModifier + s2.willModifier;
	result.fortitudeModifier = s1.fortitudeModifier + s2.fortitudeModifier;

	return result;
    }

}
