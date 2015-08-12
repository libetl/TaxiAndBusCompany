import javax.swing.JLabel   ; 

 /**
  * Classe abstraite de représentation d'un véhicule.
  * <br>
  * L'interface graphique doit être lancée. 
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
  * @see MonGUI
  */
abstract public class Vehicule
{
    /** Appartenance du véhicule */
    protected  Compagnie c  ; 
    
    /** Type du véhicule */
    protected  String type  ; 
    
    /** JLabel du véhicule */
    protected  JLabel jl    ; 
    
    /** Coordonnées du véhicule */
    protected  int x, y     ; 
    
    /** Coordonnées de destination du véhicule */
    protected  int lax, lay ; 
    
    /** Vitesse du véhicule */
    protected  int vitesse  ; 
    
    /** Table de requetes */
    protected  int entrees [], nbPassagers ; 
    
    private    String etat  ; 
    
    private    int   nbSec  ;     
    
    
    /**
     * Constructeurs d'objets de classe Vehicule
     * Ne devrait jamais être appellé.
     * 
     * @param mp nombre maximum de passagers
     * @return   un objet de classe Vehicule
     */
    public Vehicule (int mp)
    {
        entrees = new int [mp] ; 

        x       = 658 ; 
        y       = 47  ; 
        lax     = 658 ;
        lay     = 47  ; 
        
        etat    = "a été employé" ; 
        nbSec   = 0 ; 
    }
    
    /**
     * Enregistre le numéro de requête dans le véhicule
     * <br>(fait monter la personne dans le véhicule)
     * 
     * @param r  la requête
     * @return   aucun
     */
    public void monter (Requete r)
    {
       entrees [nbPassagers++] = r.getEntree () ; 
    }
    
    /**
     * Modifie l'appartenance d'un véhicule
     * 
     * @param c  la compagnie à qui doit appartenir le véhicule
     * @return   aucun
     * @see      Compagnie.acheterBus, Compagnie.acheterTaxi
     */    
    public void setCompagnie (Compagnie c)
    {
        this.c = c ;  
    }

    /**
     * Obtenir l'appartenance d'un véhicule
     * 
     * @param    aucun
     * @return   l'instance de Compagnie
     */        
    public Compagnie getCompagnie ()
    {
        return c ; 
    }

    /**
     * Obtenir le type de véhicule
     * 
     * @param    aucun
     * @return   soit "bus", soit "taxi"
     */        
    public String getType ()
    {
        return type ; 
    }

    /**
     * Obtenir l'abscisse du véhicule
     * 
     * @param    aucun
     * @return   l'entier abscisse
     */ 
    public int getX ()
    {
        return x ; 
    }


    /**
     * Obtenir l'ordonnée du véhicule
     * 
     * @param    aucun
     * @return   l'entier ordonnée
     */ 
    public int getY ()
    {
        return y ; 
    }
        
    
    /**
     * Donner l'abscisse du véhicule
     * 
     * @param    la nouvelle abscisse
     * @return   aucun
     */ 
    public void setX (int x)
    {
        this.x = x ; 
    }
    
    
    /**
     * Donner l'ordonnée du véhicule
     * 
     * @param    la nouvelle ordonnée
     * @return   aucun
     */ 
    public void setY (int y)
    {
        this.y = y ; 
    }
    
    
    /**
     * Obtenir l'abscisse de l'endroit où se rend le véhicule
     * 
     * @param    aucun
     * @return   l'entier abscisse
     */ 
    public int getlaX ()
    {
        return lax ; 
    }

    
    /**
     * Obtenir l'ordonnée de l'endroit où se rend le véhicule
     * 
     * @param    aucun
     * @return   l'entier ordonnée
     */ 
    public int getlaY ()
    {
        return lay ; 
    }
    
    
    /**
     * Obtenir l'abscisse de l'endroit où doit se rendre le véhicule
     * 
     * @param    l'entier abscisse
     * @return   aucun
     */ 
    public void setlaX (int lax)
    {
        this.lax = lax ; 
    }
    
    
    /**
     * Obtenir l'ordonnée de l'endroit où doit se rendre le véhicule
     * 
     * @param    l'entier ordonnée
     * @return   aucun
     */ 
    public void setlaY (int lay)
    {
        this.lay = lay ; 
    }
    
    
    /**
     * Obtenir le JLabel
     * 
     * @param    aucun
     * @return   le JLabel
     */ 
    public JLabel getJl ()
    {
        return jl ; 
    }
    
    
    /**
     * Affecter un le JLabel
     * 
     * @param    le JLabel
     * @return   aucun
     */ 
    public void setJl (JLabel jl)
    {
        this.jl = jl ; 
    }
    
    
    /**
     * Obtenir la vitesse actuelle
     * 
     * @param    aucun
     * @return   l'entier vitesse
     */ 
    public int getVitesse ()
    {
        return vitesse ; 
    }

    
    /**
     * Donner la vitesse actuelle
     * 
     * @param    la vitesse
     * @return   aucun
     */ 
    public void setVitesse (int vitesse)
    {
        this.vitesse = vitesse ; 
    }

    
    /**
     * Obtenir l'état du véhicule
     * ("a été employé, "a terminé sa course", "se dirige vers l'arret", ...)
     * @param    aucun
     * @return   la chaîne état
     */ 
    public String getEtat ()
    {
        return etat ; 
    }
    
    
    /**
     * Donner l'état du véhicule
     * 
     * @param    la chaîne état
     * @return   aucun
     */ 
    public void setEtat (String etat)
    {
        this.etat = etat ; 
        nbSec     = 0 ; 
    }    
    
    
    /**
     * Obtenir le temps durant lequel l'état du véhicule n'a pas changé
     * 
     * @param    aucun
     * @return   l'entier temps
     */ 
    public int getNbSecondes ()
    {
        return nbSec ; 
    }

    /**
     * Faire avancer le temps
     * (temps pendant lequel l'état du véhicule n'a pas changé)
     * @param    aucun
     * @return   aucun
     */ 
    public void avancerTemps ()
    {
        nbSec++ ; 
    }
}
