//
// Wallet.java
//
// jmettraux@burningbox.org
//
// generated with 
// jtmpl 1.0.04 20.11.2001 John Mettraux (jmettraux@yahoo.com)
//

package burningbox.org.dsh.entities.equipment;

/**
 * A character's wallet
 *
 * <p><font size=2>CVS Info :
 * <br>$Author: jmettraux $
 * <br>$Date: 2002/07/10 06:37:08 $
 * <br>$Id: Wallet.java,v 1.2 2002/07/10 06:37:08 jmettraux Exp $ </font>
 *
 * @author jmettraux@burningbox.org
 */
public class Wallet
{

    //
    // FIELDS

    protected int platinum = 0;
    protected int gold = 0;
    protected int silver = 0;
    protected int copper = 0;

    //
    // CONSTRUCTORS

    public Wallet ()
    {
    }

    public Wallet (int[] coins)
    {
	this.platinum = coins[0];
	this.gold = coins[1];
	this.silver = coins[2];
	this.copper = coins[3];
    }

    //
    // BEAN METHODS

    public int getPlatinum () { return this.platinum; }
    public int getGold () { return this.gold; }
    public int getSilver () { return this.silver; }
    public int getCopper () { return this.copper; }

    public void setPlatinum (int i) { this.platinum = i; }
    public void setGold (int i) { this.gold = i; }
    public void setSilver (int i) { this.silver = i; }
    public void setCopper (int i) { this.copper = i; }

    //
    // METHODS

    public float computeMoneyLoad ()
    {
	float coinCount = 
	    this.platinum +
	    this.gold +
	    this.silver +
	    this.copper;
	return (coinCount / 50);
    }

    public Wallet negative ()
    {
	Wallet result = new Wallet();

	result.platinum = - this.platinum;
	result.gold = - this.gold;
	result.silver = - this.silver;
	result.copper = - this.copper;

	return result;
    }

    public void credit (Wallet toAdd)
    {
	this.platinum += toAdd.platinum;
	this.gold += toAdd.gold;
	this.silver += toAdd.silver;
	this.copper += toAdd.copper;

	if (this.platinum < 0) this.platinum = 0;
	if (this.gold < 0) this.gold = 0;
	if (this.silver < 0) this.silver = 0;
	if (this.copper < 0) this.copper = 0;
    }

    public String toString ()
    {
	StringBuffer sb = new StringBuffer();

	sb.append(this.platinum);
	sb.append("pp ");
	sb.append(this.gold);
	sb.append("gp ");
	sb.append(this.silver);
	sb.append("sp ");
	sb.append(this.copper);
	sb.append("cp ");

	return sb.toString();
    }

    //
    // static methods

    /**
     * This method awaits tokenizer holding values like "10gp", "5pp" and 
     * so on...
     */
    public static Wallet parse (java.util.StringTokenizer tok)
    {
	Wallet result = new Wallet();

	while (tok.hasMoreTokens())
	{
	    String s = tok.nextToken();
	    String sValue = s.substring(0, s.length()-2);
	    String coins = s.substring(s.length()-2);

	    int value = 0;
	    try
	    {
		value = Integer.parseInt(sValue);
	    }
	    catch (NumberFormatException nfe)
	    {
		continue;
	    }

	    if(coins.equals("pp"))
	    {
		result.platinum += value;
	    }
	    else if(coins.equals("gp"))
	    {
		result.gold += value;
	    }
	    else if(coins.equals("sp"))
	    {
		result.silver += value;
	    }
	    else if(coins.equals("cp"))
	    {
		result.copper += value;
	    }
	}

	return result;
    }

}
