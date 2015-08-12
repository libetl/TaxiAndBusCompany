import javax.swing.JLabel ; 

/**
 * La classe de test RequeteTest.
 *
 * @author  Lionel Benychou
 * @version unique
 * @date 20 novembre 2005
 */
public class RequeteTest extends junit.framework.TestCase
{
    /**
     * Constructeur de classe RequeteTest
     */
    public RequeteTest()
    {
    }
    
    public void testEntree()
    {
      JLabel jl = new JLabel ();
      Requete r;
      Compagnie c = new Compagnie ("stcar",0);
      r = new Requete (c,"bus","depart1","destination1",jl);
      r.laisserPasser ();
      assertEquals("depart1", r.getDepart ());
      assertEquals("destination1", r.getDestination ());
      assertEquals(0, r.getEntree ());  
      assertEquals(1, r.getAttente ());        
    }
}
