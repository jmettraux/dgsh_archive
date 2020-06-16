//
// RandomItem.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@yahoo.com)
//

package burningbox.org.dsh.random;


/**
 * An interface for marking possibility results
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/08/13 09:43:46 $
 * <br>$Id: Item.java,v 1.3 2002/08/13 09:43:46 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public interface Item
{

    /**
     * Implement this method to refine pointer items to static items
     */
    public Item resolve ();

    public String getStaticText ();

    public void setStaticText (String text);

}
