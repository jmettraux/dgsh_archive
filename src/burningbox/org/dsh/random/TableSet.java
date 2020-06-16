//
// TableSet.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@yahoo.com)
//

package burningbox.org.dsh.random;

/**
 * A set of tables.
 * Implemented because treasure tables per level are in fact
 * triple tables.
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/08/08 07:48:14 $
 * <br>$Id: TableSet.java,v 1.1 2002/08/08 07:48:14 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class TableSet

    implements RandomTable

{

    //
    // FIELDS

    protected String name = null;
    protected java.util.List tables = new java.util.ArrayList(3);

    //
    // CONSTRUCTORS

    public TableSet ()
    {
    }

    //
    // BEAN METHODS

    public String getName () { return this.name; }
    public java.util.List getTables () { return this.tables; }

    public void setName (String s) { this.name = s; }
    public void setTables (java.util.List l) { this.tables = l; }

    //
    // METHODS

    public void addTable (RandomTable t)
    {
	this.tables.add(t);
    }

    public Item roll ()
    {
	ListItem result = new ListItem();

	java.util.Iterator it = this.tables.iterator();
	while (it.hasNext())
	{
	    RandomTable table = (RandomTable)it.next();
	    result.addItem(table.roll());
	}

	return result;
    }

}
