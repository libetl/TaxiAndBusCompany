import javax.swing.JLabel ; 

 /**
  * Classe définissant la représentation abstraite d'une personne sur l'interface
  * de gestion de cdt.
  * <br>
  * L'interface graphique doit être lancée et on doit disposer d'au minimum une 
  * compagnie
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
  * @see Compagnie
  */
public class Requete
{
    private String depart, destination ; 
    private String type ; 
    private int entree  ; 
    private JLabel jl   ; 
    private int attente ; 
    
    /**
     * Constructeur d'objets de type requête
     * 
     * @param c           Instance de compagnie
     * @param type        Doit être soit "bus", soit "taxi"
     * @param depart      Une chaine de caractères
     * @param destination Une chaine de caractères
     * @param jl          Un JLabel
     * @return            Une requête
     */
    public Requete (Compagnie c  , String type, 
                    String depart, String destination, JLabel jl)
    { 
        this.type        = type        ;         
        this.destination = destination ;
        this.depart      = depart      ;        
        entree           = c.ajouterRequete (this) ; 
        this.jl          = jl ;  
        attente          = 0  ; 
    }
    
    /**
     * Obtenir la chaîne départ
     * 
     * @param     aucun
     * @return    la chaîne depart
     * 
     */    
    public String getDepart ()
    {
        return depart ; 
    }
    
    
    /**
     * Obtenir la chaîne destination
     * 
     * @param     aucun
     * @return    la chaîne destination
     * 
     */    
    public String getDestination ()
    {
        return destination ; 
    }
   
    
    /**
     * Obtenir l'uid de la requête
     * 
     * @param     aucun
     * @return    l'uid (un entier)
     * 
     */    
    public int getEntree ()
    {
        return entree ; 
    }
     
    
    /**
     * Affecter une valeur d'uid
     * 
     * @param     le nouvel uid
     * @return    aucun
     * 
     */    
    public void setEntree (int entree)
    {
        this.entree = entree ; 
    }
    
    
    /**
     * Action de ne pas monter dans un véhicule
     * <br>Incrémente le temps d'attente
     * 
     * @param     aucun
     * @return    aucun
     * 
     */    
    public void laisserPasser ()
    {
        attente++ ; 
    }

    
    /**
     * Obtenir le temps d'attente
     * 
     * @param     aucun
     * @return    l'entier d'attente
     * 
     */    
    public int getAttente ()
    {
        return attente ; 
    }   
    
    
    /**
     * Obtenir le type de véhicule demandé
     * 
     * @param     aucun
     * @return    soit "bus", soit "taxi"
     * 
     */    
    public String getType ()
    {
        return type ; 
    }   

    
    /**
     * Obtenir le JLabel
     * 
     * @param     aucun
     * @return    le JLabel
     * 
     */    
    public JLabel getJl ()
    {
        return jl ; 
    }  
    
    
    /**
     * Effacer le JLabel
     * 
     * @param     aucun
     * @return    aucun
     * 
     */        
    public void supprJl ()
    {
        jl.setVisible (false) ; 
    } 
}
