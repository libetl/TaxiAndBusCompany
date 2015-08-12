import java.util.ArrayList   ; 
import javax.swing.ImageIcon ; 
import javax.swing.JLabel    ; 

 /**
  * Classe de gestion d'un bus <img src="../imgs/busd.png">
  * <br>Un véhicule est créé (c'est la classe parente)
  *  
  * <br><br><br>
  * Certaines images sont extraites du jeu Sim City, les personnages sont issus
  * du site Internet http://www.gnomz.com
  * Ces ressources sont à la propriété exclusive de leurs auteurs. Elles n'ont
  * été utilisées qu'à but scolaire.
  * Les taxis et bus ont été réalisés sous KColourPaint, un logiciel de 
  * traitement d'image libre sous KDE.
  * 
  * @author Lionel Benychou
  * @version unique
  * @date 20 novembre 2005
  * 
  * @see Vehicule, Compagnie
  */
public class Bus extends Vehicule
{
    private static int MAX_PASSAGERS = 55, MAX_DESTINATIONS = 40 ; 
    private int progression, aller ;  
    private ArrayList destinations ; 
    
    /**
     * Fournit :
     * <li>l'appel au constructeur Vehicule ()</li>
     * <li>l'allocation en mémoire des données d'un bus</li>
     * <li>la création du JLabel</li>
     * <br>Le bus créé est dès le départ une navette
     * @param  aucun
     * @return un objet de classe Bus
     */    
    public Bus ()
    {
        super (MAX_PASSAGERS) ; 
        destinations = new ArrayList () ; 
        nbPassagers  =  0 ; 
        progression  = -1 ; 
        aller        =  1 ; 
        type = "bus" ; 
        jl = new JLabel (new ImageIcon ("imgs/bushg.png")) ; 
        jl.setBounds (x, y, 47, 47)  ; 
    }
    
    
    /**
     * Fait descendre les passagers qui s'arrêtent à la destination actuelle.
     * <br>Complexité linéaire.
     * 
     * @param  seuil  taux d'enevement des personnes (des requêtes)
     * @return        les détails sur les personnes non satisfaites
     */    
    public ArrayList arretSuivant (int seuil)
    {
        ArrayList problemes ; 
        int i = 0, progSauv = progression; 
        
        if (progression < destinations.size () && aller == 1)
        progression++ ;
        
        if (progression >= 0 && aller == 0)
            progression-- ; 
            
        if (progression >= destinations.size () && aller == 1)
        {
            aller = 0 ;
            progression-- ; 
        }
        
        if (progression < 0 && aller == 0)
        {
            aller = 1 ;
            progression++ ; 
        }
        
        while (i < nbPassagers)
        {
            if (c.signalDepart (entrees [i], 
                (String) destinations.get (progression))) 
            {
               entrees [i] = entrees [nbPassagers - 1] ; 
               nbPassagers-- ;
           }else i++ ; 
        }
        
        if (progSauv != -1)
        problemes = c.faireMonterQuelqunBus (this, 
                         (String) destinations.get (progSauv), seuil) ; 
        else problemes = new ArrayList () ; 
        
        return problemes ; 
    }
    
    
    /**
     * Demande au bus de repartir à zéro (revenir au premier arrêt)
     * Appellé lorsque le bus a changé de trajet.
     * 
     * @param  aucun
     * @return aucun
     */
    public void revenir ()
    {
      aller = 1 ; 
      progression = -1 ; 
    }
    
    
    /**
     * Renvoit vrai si le bus ne peut plus accepter personne
     * 
     * @param  aucun
     * @return un booléen
     */ 
    public boolean busPlein ()
    {
       return nbPassagers >= MAX_PASSAGERS ; 
    } 

    /**
     * Renvoit vrai si le bus est vide
     * 
     * @param  aucun
     * @return un booléen
     */ 
    public boolean vide ()
    {
       return nbPassagers == 0 ; 
    }  
    
    /**
     * Renvoit vrai si une requête peut aller à sa destination avec le bus
     * <br>(renvoit vrai si le bus passe par l'arrêt où veut aller une requête)
     * <br>(renvoit vrai si destination appartient à l'ensemble des destinations
     * et que l'indice d'aller/retour est le bon)
     * 
     * @param  destination la chaîne à tester
     * @return un booléen
     */    
    public boolean vousYAllez (String destination)
    {
       int i = -1 ; 
       while (++i < destinations.size () && 
              !destination.equals ((String) destinations.get (i))) ; 
       return i < destinations.size () && ((aller == 1 && i >= progression) ||
              (aller == 0 && i <= progression)) ; 
    } 

    
    /**
     * Ajoute la destination dest au trajet du bus
     * 
     * @param  dest  une chaîne à ajouter
     * @return aucun
     */ 
    public void ajoutDest (String dest)
    {
       destinations.add ((String)dest) ; 
    } 


    /**
     * Obtenir la progression du bus
     * 
     * @param  aucun
     * @return un entier indice sur le l'ensemble des destinations
     */     
    public int getprog ()
    {
       return progression ; 
    }     
    
    
    /**
     * Obtenir la destination actuelle
     * 
     * @param  aucun
     * @return la chaîne destination
     */     
    public String getDest ()
    {
      return (String) destinations.get (progression) ; 
    }

    
    /**
     * Obtenir la destination d'indice i
     * <br>Pré-conditions : i appartient aux valeurs d'indices
     * <br>i n'est pas testé.
     * @param  un indice sur l'ensemble des destinations
     * @return la chaîne destination
     */     
    public String getDest (int i)
    {
      return (String) destinations.get (i) ; 
    }    
    
    
    /**
     * Supprime le trajet d'un bus.
     * <br>Permet aussi de le retransformer en navette
     * @param  aucun
     * @return aucun
     */         
    public void supprTrajet ()
    {
      destinations.clear () ; 
    }
    
    
    /**
     * Renvoie vrai si le bus est une navette
     * <br>(ou n'a pas de trajet)
     * @param  aucun
     * @return un booléen
     */     
    public boolean pasDeTrajet ()
    {
      return destinations.size () == 0 ; 
    }
}
