//
// TableItem.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@yahoo.com)
//

package burningbox.org.dsh.random;

import burningbox.org.dsh.Utils;
import burningbox.org.dsh.entities.DataSets;


/**
 * This item is used for describing things like 'gp' or 'gems'...
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/08/13 09:43:46 $
 * <br>$Id: TableItem.java,v 1.5 2002/08/13 09:43:46 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class TableItem

    extends AbstractItem

{

    static org.apache.log4j.Logger log =
	org.apache.log4j.Logger.getLogger("dgsh.random.TableItem");

    //
    // FIELDS

    protected String staticText = null;
    protected String tableName = null;

    //
    // CONSTRUCTORS

    public TableItem ()
    {
	super();

	this.count = 1;
    }

    public TableItem (String countRoll, String tableName)
    {
	super();

	this.countRoll = countRoll;
	this.tableName = tableName;
    }

    public TableItem (String tableName)
    {
	super();

	this.count = 1;
	this.tableName = tableName;
    }

    /**
     * an example :
     * <tt>ti = 
     * TableItem.buildWithStaticText("+1 shield", "SRD_magic_shields");</tt>
     */
    public static TableItem buildWithStaticText 
	(String staticText, 
	 String tableName)
    {
	TableItem ti = new TableItem();

	ti.count = 1;
	ti.staticText = staticText;
	ti.tableName = tableName;

	return ti;
    }

    //
    // BEAN METHODS

    public String getTableName () { return this.tableName; }

    public void setTableName (String s) { this.tableName = s; }

    //
    // METHODS

    /**
     * Note: resolving is ordered by the Table instances
     */
    public Item resolve ()
    {
	RandomTable table = DataSets.findTable(this.tableName);

	if (table == null) 
	{
	    log.debug("Didn't find table '"+this.tableName+"'");
	    return null;
	}

	if (this.count == 1) 
	{
	    Item i = table.roll();

	    if (this.staticText != null)
		i.setStaticText(this.staticText);

	    return i;
	}

	ListItem result = new ListItem();

	if (this.countRoll != null)
	    this.count = Utils.throwDices(this.countRoll);

	if (this.count < 1) 
	{
	    //log.debug("count = "+this.count+" which is < 1");
	    return null;
	}

	for (int i=0; i<this.count; i++)
	{
	    Item item = table.roll();

	    if (this.staticText != null)
		item.setStaticText(this.staticText);

	    result.addItem(item);
	}
	return result;
    }

}
