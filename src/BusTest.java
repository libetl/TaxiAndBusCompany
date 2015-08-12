import javax.swing.JLabel ; 

/**
 * La classe de test BusTest.
 *
 * @author  Lionel Benychou
 * @version unique
 * @date 20 novembre 2005
 */
public class BusTest extends junit.framework.TestCase
{
    /**
     * Constructeur de classe TaxiTest
     */
    public BusTest()
    {
    }

    public void testMonterDescendre()
    {
      JLabel jl = new JLabel (); 
      Compagnie compagni1 = new Compagnie("stcar", 100);
      Bus bus1 = new Bus ();
      compagni1.acheterBus(bus1, "Roger");
      assertEquals(true, bus1.pasDeTrajet ());
      bus1.ajoutDest("depart1");
      bus1.ajoutDest("destination1");
      bus1.ajoutDest("destination2");
      assertEquals(false, bus1.pasDeTrajet ());
      new Requete(compagni1, "taxi", "depart1", "destination2", jl);
      new Requete(compagni1, "bus", "depart1", "destination2", jl);
      new Requete(compagni1, "bus", "destination1", "destination2", jl);
      assertEquals(-1, bus1.getprog ()); 
      bus1.arretSuivant (10);
      assertEquals(0, bus1.getprog ()); 
      assertEquals(true, bus1.vide ()); 
      bus1.arretSuivant (10);
      assertEquals(1, bus1.getprog ()); 
      assertEquals(false, bus1.vide ()); 
      bus1.arretSuivant (10);
      assertEquals(2, bus1.getprog ()); 
      bus1.arretSuivant (10);
      assertEquals(true, bus1.vide ()); 
    }
    
    public void testVousYAllez()
    {
      Compagnie compagni1 = new Compagnie("stcar", 100);
      Bus bus1 = new Bus ();
      compagni1.acheterBus(bus1, "Roger");
      assertEquals(true, bus1.pasDeTrajet ());
      bus1.ajoutDest("depart1");
      bus1.ajoutDest("destination1");
      bus1.ajoutDest("destination2");
      assertEquals(false, bus1.pasDeTrajet ());
      bus1.arretSuivant (10);
      bus1.arretSuivant (10);
      assertEquals(true,bus1.vousYAllez ("destination1"));
      assertEquals(false,bus1.vousYAllez ("depart1"));
    }
}
