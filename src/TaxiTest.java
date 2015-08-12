import javax.swing.JLabel ; 

/**
 * La classe de test TaxiTest.
 *
 * @author  Lionel Benychou
 * @version unique
 * @date 20 novembre 2005
 */
public class TaxiTest extends junit.framework.TestCase
{
    /**
     * Constructeur de classe TaxiTest
     */
    public TaxiTest()
    {
    }

	public void testMonterDescendre()
	{
	    JLabel jl = new JLabel (); 
		Compagnie compagni1 = new Compagnie("stcar", 100);
		Taxi taxi1 = new Taxi();
		compagni1.acheterTaxi(taxi1, "Roger");
		new Requete(compagni1, "taxi", "depart1", "destination1", jl);
		new Requete(compagni1, "taxi", "depart1", "destination1", jl);	
		compagni1.faireMonterQuelqunTaxi (taxi1, "depart", 10);
		taxi1.courseTerminee () ; 
		assertEquals(true, taxi1.vide());
		assertEquals("destination1", taxi1.getDest ());
	}

	public void testVideTaxi()
	{
		Taxi taxi1 = new Taxi();
		assertEquals(true, taxi1.vide());
	}

	public void testPleinTaxi()
	{
	    JLabel jl = new JLabel ();
		Compagnie compagni1 = new Compagnie("stcar", 100);
		Taxi taxi1 = new Taxi();
		compagni1.acheterTaxi(taxi1, "Roger");
		for (int i = 0 ; i < 7 ; i++)
    		new Requete(compagni1, "taxi", "depart1", "destination1", jl);	
		compagni1.faireMonterQuelqunTaxi (taxi1, "depart", 10);
		assertEquals(true, taxi1.plein());
    }
}



