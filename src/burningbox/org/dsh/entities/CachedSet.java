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
 * $Id: CachedSet.java,v 1.7 2002/08/31 06:41:25 jmettraux Exp $
 */

//
// CachedSet.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@yahoo.com)
//

package burningbox.org.dsh.entities;

/**
 * A set that leaves XML object in their file, but caches the most recently
 * used.
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/08/31 06:41:25 $
 * <br>$Id: CachedSet.java,v 1.7 2002/08/31 06:41:25 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class CachedSet
{

    static org.apache.log4j.Logger log =
        org.apache.log4j.Logger.getLogger("dgsh.entities.CachedSet");

    //
    // FIELDS

    protected String directory = null;
    protected int cacheSize = 0;
    protected java.util.List lru = new java.util.ArrayList(0);
    protected java.util.Map cache = new java.util.HashMap(0);

    protected java.util.Map locations = new java.util.HashMap(0);

    //
    // CONSTRUCTORS

    public CachedSet (String directory, int cacheSize)
    {
	if ( ! directory.endsWith(java.io.File.separator))
	    directory += java.io.File.separator;

	this.directory = directory;
	this.cacheSize = cacheSize;

	/*
	this.lru = new java.util.ArrayList(cacheSize);
	this.cache = new java.util.HashMap(cacheSize);

	this.locations = new java.util.HashMap();
	    // will contain the location "eldrytch_might_2/powerbane.xml"
	    // for some names "powerbane"
	*/
	
	init();
    }

    public CachedSet ()
    {
    }

    protected void init ()
    {
	iterateDirectory
	    ("", 
	     new java.io.File(this.directory), 
	     new java.util.ArrayList());
    }

    //
    // BEAN METHODS 
    //
    // (this class is XML serializable for speed purposes)

    public String getDirectory () { return this.directory; }
    public int getCacheSize () { return this.cacheSize; }
    public java.util.List getLru () { return this.lru; }
    public java.util.Map getCache () { return this.cache; }
    public java.util.Map getLocations () { return this.locations; }

    public void setDirectory (String s) { this.directory = s; }
    public void setCacheSize (int i) { this.cacheSize = i; }
    public void setLru (java.util.List l) { this.lru = l; }
    public void setCache (java.util.Map m) { this.cache = m; }
    public void setLocations (java.util.Map m) { this.locations = m; }

    //
    // METHODS
    
    private Named resolve (String name)
    {
	if (name == null) return null;

	String fName = (String)this.locations.get(name);

	if (fName == null)
	    fName = name.replace(' ', '_') + ".xml";

	fName = this.directory + fName;

	//System.out.println("       -- fName is >"+fName+"<");

	return load(fName);
    }

    public boolean contains (String name)
    {
	return this.locations.containsKey(name);
    }

    public Named get (String name)
    {
	//System.out.println("CachedSet.get() >"+name+"<");

	//
	// is cached ?
	
	Named n = (Named)this.cache.get(name);

	if (n != null)
	{
	    //System.out.println("Is cached");
	    setLru(name);
	    return n;
	}

	//System.out.println("Is NOT cached");

	//
	// load from file

	n = resolve(name);
	
	/*
	if (n == null)
	{
	    //
	    // "init the directory tree"
	    
	    iterateDirectory
		("", 
		 new java.io.File(this.directory), 
		 new java.util.ArrayList());

	    //
	    // last try

	    n = resolve(name);
	}
	*/

	if (n == null) return null; // resign

	//
	// cache
	
	this.cache.put(name, n);
	setLru(name);
	
	//
	// return result
	
	return n;
    }

    private Named load (String xmlFileName)
    {
	Object o = null;
	try
	{
	    java.io.BufferedInputStream in =
		new java.io.BufferedInputStream
		    (new java.io.FileInputStream(xmlFileName));
	    java.beans.XMLDecoder decoder = 
		new java.beans.XMLDecoder(in);

	    o = decoder.readObject();

	    Named n = (Named)o;

	    decoder.close();
	    in.close();

	    return n;
	}
	catch (Exception e)
	{
	    //log.debug("Failed to load >"+xmlFileName+"<", e);
	    log.debug("Failed to load >"+xmlFileName+"< "+e);
	}
	return null;
    }

    private void iterateDirectory 
	(String prefix, java.io.File dir, java.util.List result)
    {
	java.io.File[] files = dir.listFiles();

	for (int i=0; i<files.length; i++)
	{
	    java.io.File f = files[i];

	    if (f.isDirectory())
	    {
		String dirPrefix = 
		    prefix + f.getName() + java.io.File.separator;

		iterateDirectory(dirPrefix, f, result);

		continue;
	    }

	    String name = f.getName();

	    if ( ! name.endsWith(".xml")) continue;

	    name = name.substring(0, name.length()-4);
	    name = name.replace('_', ' ');

	    this.locations.put(name, prefix+f.getName());

	    //System.out.println("  - '"+name+"' -> '"+prefix+f.getName()+"'");

	    result.add(this.get(name));
	}
    }

    public java.util.Iterator iterator ()
    {
	java.util.List result = new java.util.ArrayList();

	iterateDirectory("", new java.io.File(this.directory), result);

	return result.iterator();
    }

    /*
    public java.util.Iterator iterator ()
    {
	java.io.File dir = new java.io.File(this.directory);
	java.io.File[] files = dir.listFiles();

	java.util.List result = new java.util.ArrayList(files.length);

	for (int i=0; i<files.length; i++)
	{
	    java.io.File f = files[i];
	    String name = f.getName();

	    if ( ! name.endsWith(".xml")) continue;

	    name = name.substring(0, name.length()-4);
	    name = name.replace('_', ' ');

	    result.add(this.get(name));
	}

	return result.iterator();
    }
    */
    
    //
    // LRU METHODS

    protected void setLru (String name)
    {
	this.lru.add(0, name);

	String leastUsed = null;

	if (this.lru.size() > this.cacheSize)
	    leastUsed = (String)this.lru.remove(this.lru.size()-1);

	//System.out.println("least used is >"+leastUsed+"<");

	if (leastUsed == null || name.equals(leastUsed)) return;

	this.cache.remove(leastUsed);
    }

    public String toString ()
    {
	java.util.Iterator it = null;

	StringBuffer sb = new StringBuffer();

	sb.append("<CachedSet\n");
	sb.append("    directory=\""+this.directory+"\"\n");
	sb.append("    cacheSize=\""+this.cacheSize+"\"\n");
	sb.append(">\n");

	it = this.lru.iterator();
	sb.append(" <lru>\n");
	while (it.hasNext())
	{
	    sb.append("  <item>");
	    sb.append(it.next());
	    sb.append("</item>\n");
	}
	sb.append(" </lru>\n");

	it = this.cache.keySet().iterator();
	sb.append(" <cache>\n");
	while (it.hasNext())
	{
	    sb.append("  <key>");
	    sb.append(it.next());
	    sb.append("</key>\n");
	}
	sb.append(" </cache>\n");

	it = this.locations.entrySet().iterator();
	sb.append(" <locations>\n");
	while (it.hasNext())
	{
	    java.util.Map.Entry entry = (java.util.Map.Entry)it.next();

	    sb.append("  <item\n");
	    sb.append("      key=\""+entry.getKey()+"\"\n");
	    sb.append("      value=\""+entry.getValue()+"\"");
	    sb.append("/>\n");
	}
	sb.append(" </locations>\n");

	sb.append("</CachedSet>");

	return sb.toString();
    }

}
