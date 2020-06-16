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
 * $Id: BeanSet.java,v 1.12 2002/12/03 08:05:58 jmettraux Exp $
 */

//
// BeanSet.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@burningbox.org)
//

//
// You will gain money by a speculation or lottery.
//

package burningbox.org.dsh.entities;

import burningbox.org.dsh.Utils;
import burningbox.org.dsh.GameSession;
import burningbox.org.dsh.Definitions;
import burningbox.org.dsh.DshException;
import burningbox.org.dsh.random.RandomTable;


/**
 * An object that contains Bean instances
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/12/03 08:05:58 $
 * <br>$Id: BeanSet.java,v 1.12 2002/12/03 08:05:58 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class BeanSet
{

    //static org.apache.log4j.Logger log =
    //    org.apache.log4j.Logger.getLogger(BeanSet.class.getName());

    //
    // FIELDS
    
    protected String fileName = null;
    protected java.util.Map beans = null;

    //
    // CONSTRUCTORS
    
    public BeanSet ()
    {
	this.beans = new java.util.HashMap();
    }

    public BeanSet (String fileName)
	throws java.io.IOException
    {
	this.beans = new java.util.HashMap();
	this.fileName = fileName;
	load(fileName);
    }

    //
    // METHODS

    public String getFileName ()
    {
	return this.fileName;
    }

    public void setFileName (String fn)
    {
	this.fileName = fn;
    }
    
    public void add (Named n)
    {
	this.beans.put(n.getName().toLowerCase(), n);
    }

    public Named get (String name)
    {
	return (Named)this.beans.get(name.toLowerCase());
    }

    public boolean contains (String name)
    {
	//return (null != this.beans.get(name.toLowerCase()));
	return this.beans.containsKey(name.toLowerCase());
    }

    public Named remove (String name)
    {
	return (Named)this.beans.remove(name.toLowerCase());
    }

    public Named remove (Named named)
    {
	return (Named)this.beans.remove(named.getName());
    }

    public void removeAll ()
    {
	this.beans = new java.util.HashMap();
    }

    /**
     * An iterator on the contained beans
     */
    public java.util.Iterator iterator ()
    {
	return this.beans.values().iterator();
    }

    /**
     * An iterator on the names of the contained beans
     */
    public java.util.Iterator nameIterator ()
    {
	return this.beans.keySet().iterator();
    }

    /**
     * Loads the content of an xml file into the BeanSet
     */
    public void load (String xmlFileName)
	throws java.io.IOException
    {
	java.io.BufferedInputStream in =
	    new java.io.BufferedInputStream
	        (new java.io.FileInputStream(xmlFileName));
	java.beans.XMLDecoder decoder = 
	    new java.beans.XMLDecoder(in);

	while(true)
	{
	    Object o = null;
	    try
	    {
		o = decoder.readObject();
	    }
	    catch (ArrayIndexOutOfBoundsException e)
	    {
		break;
	    }

	    try
	    {
		Named n = (Named)o;
		/*
		if (n instanceof MonsterTemplate &&
		    !( n instanceof Monster))
		{
		    //GameSession.getInstance().getTempSet().add(n);
		    DataSets.getMonsterTemplates().add(n);
		}
		else 
		if (n instanceof RandomTable)
		{
		    DataSets.addTable((RandomTable)n);
		}
		else
		{
		*/
		add(n);
		/*
		}
		*/
	    }
	    catch (ClassCastException cce)
	    {
		// ignore
	    }
	}

	decoder.close();
	in.close();
    }

    /**
     * Saves the content of the BeanSet into an xml file
     */
    public void save (String destinationFileName)
	throws java.io.IOException
    {
	java.io.BufferedOutputStream out =
	    new java.io.BufferedOutputStream
	        (new java.io.FileOutputStream(destinationFileName));
	java.beans.XMLEncoder encoder = 
	    new java.beans.XMLEncoder(out);

	java.util.Iterator it = this.beans.keySet().iterator();

	while(it.hasNext())
	{
	    Object key = it.next();
	    Object named = this.beans.get(key);
	    encoder.writeObject(named);
	}

	encoder.close();
	out.close();
    }

    /**
     * Saves each bean in the set to a separate file of the given
     * directory
     */
    public void saveToDir (String directoryPath)
	throws java.io.IOException
    {
	if ( ! directoryPath.endsWith(java.io.File.separator))
	    directoryPath += java.io.File.separator;

	java.util.Iterator it = this.beans.keySet().iterator();
	while (it.hasNext())
	{
	    Object key = it.next();
	    Object named = this.beans.get(key);

	    String fileName = directoryPath + key + ".xml";

	    Utils.xmlSave(named, fileName);

	    System.out.println("saved '"+key+"' to "+fileName);
	}
    }

    /**
     * Returns the number of beans this BeanSet contains
     */
    public int size ()
    {
	return this.beans.size();
    }

    /**
     * A method for GameSession.getBelligerents()
     * ** obsolete, but kept for... perhaps... **
     */
    public void addAllBeansTo (java.util.List list)
    {
	java.util.Iterator it = nameIterator();
	while (it.hasNext())
	{
	    String name = (String)it.next();

	    list.add(this.beans.get(name));
	}
    }

    //
    // STATIC METHODS

    private static java.util.List namesForTransfer 
	(String nameWithStar, BeanSet fromSet)
    {
	java.util.List result = new java.util.ArrayList(10);

	String nameStart = nameWithStar.substring(0, nameWithStar.length()-1);

	java.util.Iterator it = fromSet.nameIterator();
	while (it.hasNext())
	{
	    String name = (String)it.next();

	    if (name.startsWith(nameStart))
		result.add(name);
	}

	return result;
    }

    private static void massTransfer 
	(String nameWithStar, BeanSet fromSet, BeanSet toSet)
    throws DshException
    {
	java.util.List names = namesForTransfer(nameWithStar, fromSet);

	java.util.Iterator it = names.iterator();
	while (it.hasNext())
	{
	    String name = (String)it.next();
	    
	    transfer(name, fromSet, toSet);
	}
    }

    /**
     * Transfers beans from one set to the other.
     * Names ending with * are 'globbed'
     */
    public static void transfer 
	(String name, BeanSet fromSet, BeanSet toSet)
    throws DshException
    {

	if (name.endsWith("*"))
	{
	    massTransfer(name, fromSet, toSet);
	    return;
	}

	//
	// name without a * at the end
	
	Named named = fromSet.remove(name);

	if (named == null)
	{
	    throw new DshException
		("There is no object named '"+name+"' in the source set.");
	}

	if (toSet.contains(name))
	{
	    throw new DshException
		("There is already an object named '"+name+
		 "' in the destination set");
	}

	toSet.add(named);
	//fromSet.remove(named);
	
    }

}
