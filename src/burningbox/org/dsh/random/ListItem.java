//
// ListItem.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@yahoo.com)
//

package burningbox.org.dsh.random;

/**
 * A list of items
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/08/13 09:43:46 $
 * <br>$Id: ListItem.java,v 1.4 2002/08/13 09:43:46 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class ListItem
 
    implements Item

{

    //
    // FIELDS

    protected java.util.List items = new java.util.ArrayList(0);

    protected String staticText = null;

    //
    // CONSTRUCTORS

    public ListItem ()
    {
    }

    //
    // BEAN METHODS

    public java.util.List getItems () { return this.items; }
    public String getStaticText () { return this.staticText; }

    public void setItems (java.util.List l) { this.items = l; }
    public void setStaticText (String s) { this.staticText = s; }

    //
    // METHODS

    public java.util.Iterator iterator () { return this.items.iterator(); }

    public void addItem (Item i) { this.items.add(i); }

    public Item resolve ()
    {
	ListItem result = new ListItem();

	java.util.Iterator it = this.items.iterator();
	while (it.hasNext())
	{
	    Item i = (Item)it.next();
	    result.addItem(i.resolve());
	}

	return result;
    }

}
