//
// RandomTable.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@yahoo.com)
//

package burningbox.org.dsh.random;

import burningbox.org.dsh.entities.Named;


/**
 * There are two implementing classes :
 * Table and TableSet
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/08/08 07:48:38 $
 * <br>$Id: RandomTable.java,v 1.1 2002/08/08 07:48:38 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public interface RandomTable

    extends Named

{

    public Item roll ();

}
