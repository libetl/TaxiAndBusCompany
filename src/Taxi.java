import javax.swing.ImageIcon ; 
import javax.swing.JLabel    ; 

 /**
  * Classe de gestion d'un taxi <img src="../imgs/taxid.png">
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
public class Taxi extends Vehicule
{
    private static int MAX_PASSAGERS = 4 ; 
    private String destination ; 
     
    
    /**
     * Fournit :
     * <li>l'appel au constructeur Vehicule ()</li>
     * <li>l'allocation en mémoire des données d'un taxi</li>
     * <li>la création du JLabel</li>
     * 
     * @param  aucun
     * @return un objet de classe Taxi
     */
    public Taxi ()
    {
        super (MAX_PASSAGERS) ; 
        destination = "" ; 
        entrees [0] = -1 ; 
        nbPassagers = 0  ; 
        type = "taxi" ; 
        jl = new JLabel (new ImageIcon ("imgs/taxihg.png")) ;         
        jl.setBounds (x, y, 47, 47)  ; 
    }

    
    /**
     * Ajoute une requete dans l'ensemble des requêtes traitées.
     * (routine appellée par faireMonterTaxi de la classe compagnie)
     * 
     * @param  r la requête à traiter
     * @return aucun
     */
    public void monter (Requete r)
    {
      super.monter (r) ; 
      destination = r.getDestination () ; 
    }    
    
    
    /**
     * Fait descendre tous les passagers à la destination actuelle.
     * <br>Complexité linéaire.
     * 
     * @param  aucun
     * @return aucun
     */    
    public void courseTerminee ()
    {
        for (int i = 0 ; i < nbPassagers ; i++)
          c.faireDescendre (entrees [i]) ; 
        entrees [0] = -1 ; 
        nbPassagers = 0  ; 
    }

    
    /**
     * Renvoit vrai si le taxi ne peut plus accepter personne
     * 
     * @param  aucun
     * @return un booléen
     */ 
    public boolean plein ()
    {
       return nbPassagers >= MAX_PASSAGERS ; 
    }   
    
    
    /**
     * Renvoit vrai si le taxi ne transporte personne
     * 
     * @param  aucun
     * @return un booléen
     */ 
    public boolean vide ()
    {
       return nbPassagers == 0 ; 
    }      
    
    
    /**
     * Retourne la chaîne destination vers où se dirige un taxi
     * 
     * @param  aucun
     * @return la chaîne destination
     */     
    public String getDest ()
    {
       return destination ; 
    }
    
    
    /**
     * Modifie la chaîne destination
     * 
     * @param  la chaîne destination
     * @return aucun
     */     
    public void setDest (String dest)
    {
       destination = dest ; 
    }
}
