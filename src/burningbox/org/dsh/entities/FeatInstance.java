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
 * $Id: FeatInstance.java,v 1.4 2002/08/16 08:37:36 jmettraux Exp $
 */

//
// FeatInstance.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// When a man steals your wife, there is no better revenge than to let him
// keep her.
// 		-- Sacha Guitry
// 

package burningbox.org.dsh.entities;

/**
 * A feat as seen by a character or a monster
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/08/16 08:37:36 $
 * <br>$Id: FeatInstance.java,v 1.4 2002/08/16 08:37:36 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class FeatInstance

    implements MiscModified

{

    //
    // FIELDS

    protected String featName = null;
    protected String beneficiaryDefinition = null;
    protected int modifier = 0;

    //
    // CONSTRUCTORS

    public FeatInstance ()
    {
    }

    public FeatInstance 
	(String featName, 
	 String beneficiaryDefinition,
	 int modifier)
    {
	this.featName = featName;
	this.beneficiaryDefinition = beneficiaryDefinition;
	this.modifier = modifier;
    }

    public FeatInstance
	(String featName)
    {
	this.featName = featName;
    }

    public FeatInstance
	(String featName, 
	 String beneficiaryDefinition)
    {
	this.featName = featName;
	this.beneficiaryDefinition = beneficiaryDefinition;

	if (this.featName.toLowerCase().equals("skill focus"))
	    this.modifier = 2;
    }

    //
    // BEAN METHODS

    public String getFeatName () { return this.featName; }
    public String getBeneficiaryDefinition () { return this.beneficiaryDefinition; }
    public int getModifier () { return this.modifier; }


    public void setFeatName (String s) { this.featName = s; }
    public void setBeneficiaryDefinition (String s) { this.beneficiaryDefinition = s; }
    public void setModifier (int i) { this.modifier = i; }

    //
    // METHODS
    
    public int sumMiscModifiers (String definition)
    {
	Feat feat = DataSets.findFeat(this.featName);

	if (this.beneficiaryDefinition == null && feat != null)
	    return feat.sumMiscModifiers(definition);

	/*
	System.out.println
	    ("<feat name=\""+this.featName+
	     "\" beneficiary=\""+this.beneficiaryDefinition+"\" />");
	System.out.println
	    ("          - checked definition is >"+definition+"<");
	*/

	if (definition.equals(beneficiaryDefinition))
	    return this.modifier;

	return 0;
    }

}
