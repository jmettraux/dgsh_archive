//
// FileItem.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@yahoo.com)
//

package burningbox.org.dsh.random;

/**
 * A pointer to file containing a magic item or an encounter
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/08/06 14:54:44 $
 * <br>$Id: FileItem.java,v 1.2 2002/08/06 14:54:44 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class FileItem

    extends AbstractItem

{

    //
    // FIELDS

    protected String fileName = null;

    //
    // CONSTRUCTORS

    public FileItem ()
    {
	super();
    }

    public FileItem (String fileName)
    {
	super();
	this.fileName = fileName;
    }

    //
    // BEAN METHODS

    public String getFileName () { return this.fileName; }

    public void setFileName (String s) { this.fileName = s; }

    //
    // METHODS

    public Item resolve () 
    {
	return this;
    }

}
