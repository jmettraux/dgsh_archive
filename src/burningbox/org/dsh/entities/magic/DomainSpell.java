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
 * $Id: DomainSpell.java,v 1.2 2002/11/11 10:40:30 jmettraux Exp $
 */

//
// DomainSpell.java
//
// jmettraux@burningbox.org
//

package burningbox.org.dsh.entities.magic;

/**
 * A small helper class for storing prepared domain spells
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/11/11 10:40:30 $
 * <br>$Id: DomainSpell.java,v 1.2 2002/11/11 10:40:30 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class DomainSpell
{
    protected int level = -1;
    protected String domainName = null;
    protected String className = null;
    protected String spellName = null;

    public DomainSpell ()
    {
    }

    public DomainSpell 
	(int level, String domainName, String className, String spellName)
    {
	this.level = level;
	this.domainName = domainName;
	this.className = className;
	this.spellName = spellName;
    }

    public int getLevel () { return this.level; }
    public String getDomainName () { return this.domainName; }
    public String getClassName () { return this.className; }
    public String getSpellName () { return this.spellName; }

    public void setLevel (int i) { this.level = i; }
    public void setDomainName (String s) { this.domainName = s; }
    public void setClassName (String s) { this.className = s; }
    public void setSpellName (String s) { this.spellName = s; }
}
