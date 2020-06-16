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
 * $Id: LoggingConfigurator.java,v 1.3 2002/09/20 13:57:54 jmettraux Exp $
 */

//
// LoggingConfigurator.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.03 30.01.2001 John Mettraux (jmettraux@yahoo.com)
//

package burningbox.org.dsh;

/**
 * A static configuration for log4j
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/09/20 13:57:54 $
 * <br>$Id: LoggingConfigurator.java,v 1.3 2002/09/20 13:57:54 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class LoggingConfigurator
{

    public static void configure
        (String sRoot,
	 String logFileName, 
	 String sPriority)
    throws java.io.IOException
    {
	org.apache.log4j.Logger root = org.apache.log4j.Logger
	    .getLogger(sRoot);

	org.apache.log4j.Level level = org.apache.log4j.Level
	    .toLevel(sPriority, org.apache.log4j.Level.INFO);

	root.setLevel(level);

	org.apache.log4j.Layout layout = new org.apache.log4j.PatternLayout
	//    ("%d [%t] %-5p %c - %m%n");
	    ("%d %-5p %c - %m%n");

	root.addAppender
	    (new org.apache.log4j.FileAppender(layout, logFileName, false));
    }

}
