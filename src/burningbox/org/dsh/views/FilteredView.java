//
// FilteredView.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@yahoo.com)
//

package burningbox.org.dsh.views;

import burningbox.org.dsh.entities.Named;


/**
 * A view with a filter on the 'named' items
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/07/01 06:19:36 $
 * <br>$Id: FilteredView.java,v 1.1 2002/07/01 06:19:36 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public abstract class FilteredView

    implements View

{

    //
    // FIELDS

    protected String filter = null;

    //
    // CONSTRUCTORS

    public FilteredView ()
    {
    }

    //
    // METHODS

    public void setFilter (String filter)
    {
	this.filter = filter;
    }

    protected boolean matchesFilter (Named n)
    {
	if (this.filter == null)
	    return true;

	return n.getName().toLowerCase().matches(filter);
    }

}
