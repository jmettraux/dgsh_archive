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
 * $Id: FeatsView.java,v 1.6 2002/08/15 15:49:45 jmettraux Exp $
 */

//
// FeatsView.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

package burningbox.org.dsh.views;

import burningbox.org.dsh.views.Utils;
import burningbox.org.dsh.views.utils.*;
import burningbox.org.dsh.entities.Feat;
import burningbox.org.dsh.entities.DataSets;
import burningbox.org.dsh.entities.CachedSet;

/**
 * As its name implies.
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/08/15 15:49:45 $
 * <br>$Id: FeatsView.java,v 1.6 2002/08/15 15:49:45 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class FeatsView

    extends FilteredView

{

    //static org.apache.log4j.Logger log = org.apache.log4j.Logger
    //    .getInstance(FeatsView.class.getName());

    //
    // FIELDS

    protected CachedSet spells = null;

    //
    // CONSTRUCTORS

    public FeatsView (CachedSet spells)
    {
	this.spells = spells;
    }

    //
    // METHODS

    private String displayStats (Feat f)
    {
	StringBuffer sb = new StringBuffer();

	sb.append(f.getName());
	sb.append('\n');
	sb.append(f.getType());

	if (f.getPrerequisite() != null)
	{
	    sb.append('\n');
	    sb.append(Utils.multiLineFormat(f.getPrerequisite(), 15));
	}

	return sb.toString();
    }

    private Line displayFeat (Feat f)
    {
	Line l = new Line();

	l.addCell(new Cell(20, Cell.ALIGN_LEFT, Utils.multiLineFormat(displayStats(f), 20)));
	l.addCell(new Cell(53, Cell.ALIGN_LEFT, Utils.multiLineFormat(f.getDescription(), 53)));

	return l;
    }

    public StringBuffer process ()
    {
	StringBuffer sb = new StringBuffer();
	
	MultiTable table = new MultiTable(80);

	java.util.Iterator it = DataSets.getFeats().iterator();
	while (it.hasNext())
	{
	    Feat f = (Feat)it.next();

	    if (matchesFilter(f))
		table.addLine(displayFeat(f));
	}

	sb.append(table.render());

	return sb;
    }

}
