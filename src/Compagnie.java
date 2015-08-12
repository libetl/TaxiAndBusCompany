import java.util.ArrayList ; 

/**
 * Représentation choisie pour une compagnie <img src="../imgs/siege.png">
 * <br>Elle gère des listes de requêtes à traiter, de requêtes en cours, 
 * de véhicules et de noms de conducteurs.
 * <br>Elle a aussi des renseignements généraux tels que l'argent disponible ou
 * un nom.
 * 
 * <br> Cette classe contient des méthodes pour gérer à son niveau la gestion 
 * d'une requête (faire monter dans un bus, diriger un taxi...).
 * Ceci permet une centralisation des corrélations requêtes &lt;=&gt; véhicules
 * , ce qui simplifie la programmation.
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
 * @see Vehicule, Taxi, Bus
 */
public class Compagnie
{
    private String nom ; 
    private ArrayList requetes, requetesEnCours, vehicules, nomsC; 
    private int argent ;  

    /**
     * Constructeur vide d'objets de classe Compagnie
     * <br>A utiliser à défaut d'informations
     * <br>Choisit "" comme nom de compagnie et un apport initial de 
     * dix mille k euros.
     * 
     * @return un objet de classe Compagnie
     */
    public Compagnie ()
    {
        this ("", 10000) ; 
    }
    

    /**
     * Constructeur d'objets de classe Compagnie
     * 
     * @param nom    une chaîne informant sur le nom donné à la compagnie
     * @param argent un entier d'apport initial en k euros.
     */
    public Compagnie (String nom, int argent)
    {       
       nomsC                      = new ArrayList () ; 
       requetes                   = new ArrayList () ; 
       requetesEnCours            = new ArrayList () ; 
       vehicules                  = new ArrayList () ; 
       
       this.nom                   = nom ; 
       this.argent                = argent ; 
    }
    
    
    /**
     * Ajoute un bus à la liste des véhicules et diminue l'argent disponible
     * 
     * @param v             Instance de bus créée préalablement
     * @param nomConducteur chaîne prénom pour identifier un véhicule de manière
     *                      unique
     * @return              aucun
     */
    public void acheterBus (Bus v, String nomConducteur)
    {
        v.setCompagnie (this) ; 
        vehicules.add  ((Bus)v)    ;
        nomsC.add  ((String)nomConducteur) ; 
        argent-= 700 ; 
    }
    
    
    /**
     * Ajoute un taxi à la liste des véhicules et diminue l'argent disponible
     * 
     * @param v             Instance de taxi créée préalablement
     * @param nomConducteur chaîne prénom pour identifier un véhicule de manière
     *                      unique
     * @return              aucun
     */
    public void acheterTaxi (Taxi v, String nomConducteur)
    {
        v.setCompagnie (this) ; 
        vehicules.add  ((Taxi)v)    ; 
        nomsC.add  ((String)nomConducteur) ; 
        argent-= 75 ; 
        
    }

    
    /* Déclarer une requête comme étant en cours */
    private void transfertVersEnCours (int i, int nouveau)
    {
      requetesEnCours.add ((Requete)requetes.get (i)) ;  
      requetes.set (i,(Requete) requetes.get (requetes.size () - 1)) ; 
      requetes.remove (requetes.size () - 1) ; 
      ((Requete) requetesEnCours.get (nouveau)).setEntree (nouveau) ; 
    }
    
    
    /**
     * Cherche toutes les personnes qui peuvent monter à bord d'un bus
     * <br>Les enregistre et les fait payer le ticket
     * <br>Fournit des informations sur les personnes non satisfaites.
     * 
     * 
     * @param bus     une instance de bus
     * @param endroit la destination où le bus est arrêté
     * @param seuil   taux d'énervement des clients.
     * @return        Informations sur les personnes non satisfaites
     * @see   Bus.monter, Vehicule.monter
     */
    public ArrayList faireMonterQuelqunBus (Bus bus, String endroit, int seuil)
    {
      int i = -1, nouveau = requetesEnCours.size () ; 
      ArrayList probleme = new ArrayList () ; 
      while (++i < requetes.size () && !bus.busPlein ())
      {
        ((Requete) requetes.get (i)).laisserPasser () ; 
        if (((Requete) requetes.get (i)).getAttente () > seuil)
           probleme.add ((Requete) requetes.get (i)) ; 
           
        if (requetes.get (i) != null && 
            ((Requete)requetes.get (i)).getType     ().equals ("bus")   &&
            ((Requete)requetes.get (i)).getDepart   ().equals (endroit) && 
            bus.vousYAllez (((Requete)requetes.get (i)).getDestination ()))
        {
           transfertVersEnCours (i, nouveau) ; 
           bus.monter ((Requete) requetesEnCours.get (nouveau)) ; 
           ((Requete) requetesEnCours.get (nouveau)).supprJl () ; 
           argent += 1 ; 
        }
      }
      return probleme ; 
    }


    /**
     * Se dirige vers l'arrêt où la première personne ayant demandé un taxi se 
     * trouve.
     * 
     * @param  taxi une instance de taxi
     * @return aucun
     */
    public void seDirigerTaxi (Taxi taxi)
    {
      int i = -1, nouveau = requetesEnCours.size () ; 
      String destination = "", depart = "" ; 
      while (++i < requetes.size () && requetes.get (i) != null && 
                     !((Requete) requetes.get (i)).getType ().equals ("taxi")) ; 

      if (i < requetes.size ())
      taxi.setDest (((Requete) requetes.get (i)).getDepart ()) ; 
    }  

    
    /**
     * Cherche toutes les personnes qui peuvent monter à bord d'un taxi
     * <br>Elles doivent se diriger au même endroit que la personne qui est 
     * montée en premier (par ordre d'arrivée à l'arrêt de bus)
     * <br>Les enregistre et les fait payer
     * <br>Fournit des informations sur les personnes non satisfaites.
     * 
     * 
     * @param bus     une instance de taxi
     * @param depart  la destination où le taxi est arrêté
     * @param seuil   taux d'énervement des clients.
     * @return        Informations sur les personnes non satisfaites
     * @see   Taxi.monter, Vehicule.monter
     */   
    public ArrayList faireMonterQuelqunTaxi (Taxi taxi, String depart, int seuil)
    {
      int i = -1, nouveau = requetesEnCours.size () ; 
      ArrayList probleme = new ArrayList () ; 
      String destination = "" ; 
      taxi.setDest ("") ; 
      while (i < requetes.size () && !taxi.plein ()) 
      {
        while (++i < requetes.size () && requetes.get (i) != null && 
                     !((Requete) requetes.get (i)).getType ().equals ("taxi"))
        {
          ((Requete) requetes.get (i)).laisserPasser () ; 
          if (((Requete) requetes.get (i)).getAttente () > seuil)
              probleme.add ((Requete) requetes.get (i)) ; 
        } 
        if (requetes.size () > 0 && i < requetes.size () &&
            (destination.equals ("") || 
             (destination.equals 
                (((Requete) requetes.get (i)).getDestination ())) && 
             (depart.equals (((Requete) requetes.get (i)).getDepart ()))))
        {
          transfertVersEnCours (i, nouveau) ; 
          taxi.monter ((Requete) requetesEnCours.get (nouveau)) ; 
          ((Requete) requetesEnCours.get (nouveau)).supprJl () ; 
          argent += 10 ; 
        }
      }
      return probleme ; 
    }   

    
    /**
     * Supprime une entrée des requêtes en cours
     * <br>Ne marche pas
     * 
     * @param entree entrée à supprimer
     * @return aucun
     */
    public void faireDescendre (int entree)
    {
      //requetesEnCours.remove (entree) ; 
    }

    
    /**
     * Cherche un bus qui n'a pas de trajet pour par exemple servir de navette
     * 
     * @param aucun
     * @return une instance de Bus sans trajets
     */
    public Bus chercherBusLibre ()
    {
       int i = -1 ; 
       boolean trouve = false ; 
       while (!trouve && ++i < vehicules.size ())
       {
         trouve = (((Vehicule)vehicules.get (i)).getType ().equals ("bus") && 
                   ((Bus)vehicules.get (i)).pasDeTrajet ()) ; 
       }
       return (i < vehicules.size () ? (Bus) vehicules.get (i) : null) ; 
    }
    
    
    /**
     * Ajoute une personne en attente devant un arrêt.
     * 
     * <br>Ne devrait pas être appellé.
     * <br>A utiliser : "new Requete ()"
     * @param r une requête
     * @return la nouvelle taille de la liste des requêtes
     * 
     * @see Requete
     */
    public int ajouterRequete(Requete r)
    {
        this.requetes.add    ((Requete)r) ; 
        return requetes.size () - 1 ; 
    }


    /**
     * Regarde s'il on peu faire descendre une requête, essaie de le faire,
     * et informe celui qui a executé la méthode si ça a été fait en pratique.
     * <br>Utilisé uniquement pour les bus.
     * 
     * @param entree  indice de requête
     * @param endroit destination où le bus est arrêté
     */
    public boolean signalDepart (int entree, String endroit)
    {
        boolean condition = 
            ((Requete) requetesEnCours.get (entree)).getDestination ()
                       .equals (endroit) ; 
        if (condition) faireDescendre (entree) ; 
        return condition ; 
    }
    
    
    /**
     * Obtenir l'argent disponible
     * 
     * @param aucun
     * @return l'entier "argent disponible"
     */
    public int getArgent ()
    {
       return argent ; 
    }
    
    
    /**
     * Obtenir l'instance de véhicule referencé dans la table comme étant le
     * indice-ième
     * 
     * @param indice numéro de véhicule de la table
     * @return une instance de véhicule
     */
    public Vehicule getVehicule (int indice)
    {
       return (Vehicule) vehicules.get (indice) ; 
    }
    
    
    /**
     * Cherche l'instance de bus correspondant à un nom de conducteur
     * 
     * @param conducteur la chaîne conducteur
     * @return l'instance de bus recherchée
     */
    public Bus getBus (String conducteur)
    {
       int a = nomsC.indexOf ((String) conducteur) ; 
       return (Bus) vehicules.get (a) ; 
    }
    
    
    /**
     * Cherche le nom du conducteur selon l'entier indice réferençant le
     * indice-ième véhicule
     * 
     * @param  indice numéro de véhicule de la table
     * @return une chaîne conducteur
     */
    public String getConducteur (int indice)
    {
       return (String) nomsC.get (indice) ; 
    }
    
    
    /**
     * Obtenir le nombre de véhicules
     * 
     * @param  aucun
     * @return le nombre de véhicules (entier)
     */
    public int getNombreVehicules ()
    {
       return vehicules.size () ; 
    }
}

