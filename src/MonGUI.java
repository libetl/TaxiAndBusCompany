import java.util.Random      ; 
import java.util.HashMap     ; 
import java.util.Iterator    ; 
import java.util.ArrayList   ; 
import java.awt.*            ; 
import java.awt.event.*      ; 
import javax.swing.*         ; 
import java.applet.Applet    ; 


 /** Interface graphique de gestion de cdt.
  *  <br> Doit être appellée après la classe Demarrer
  *  <br>
  *  Cette classe exploite une compagnie de transport et utilise toutes les classes 
  *  du "noyau"<br>
  *  En particulier, les classes Requete, Compagnie, Taxi, et Bus
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
  * @see Demarrer
  * @see Compagnie
  * @see Requete
  * @see Taxi
  * @see Bus
  */
public class MonGUI implements ActionListener, MouseListener
{
    private Frame a ; 

    private Compagnie compagnie ; 
    
    private HashMap destinations ; 
    
    private int cG, seuilEnervement ; 
    private boolean [][] matrice ; 
    private boolean definir    = false ; 
    private boolean arrChinois = false ; 
    private Point   destPSauve ; 
    private JTextField tDestACreer ; 
    private JTextField tNomConduct ; 

    private List listeun   = new List () ; 
    private List listed    = new List () ;  
    private List listeCond = new List () ; 
    
    private JLayeredPane ecran  = new JLayeredPane () ; 
    
    private Button ajout   = new Button   ("Acheter un véhicule") ; 
    private Button def     = new Button   ("Definir une destination") ; 
    private Button modif   = new Button   ("Modifier trajets") ; 
    private Button chinois = new Button   ("Arrivée de chinois") ; 
    private Button arret   = new Button   ("Déposer le bilan") ; 
    
    private CheckboxGroup cbg = new CheckboxGroup () ; 
    
    private TextArea jrl      = new TextArea (2, 50) ; 
    
    private TextArea etats    = new TextArea (2, 50) ; 

    private Frame cadre   = new Frame ("Gestion de cdt") ;    
    
    private Panel gui    = new Panel  () ; 
    private Panel haut   = new Panel  () ; 
    private Panel bas    = new Panel  () ; 
    
    private ActionListener taskPerformer = new ActionListener (){
        public void actionPerformed (ActionEvent e){ retour (e) ; }}; 
        
    private Random rand = new Random () ; 
    
    private ArrayList deplacement = new ArrayList () ; 
    
    /* Va chercher des informations sur les personnes non satisfaites */
    private void informerProblemes (ArrayList probleme)
    {
       for (int l = 0 ; l < probleme.size () ; l++)
       {
         jrl.insert ("Une personne qui attend à l'arret "
                      +  ((Requete)probleme.get (l)).getDepart () + 
                         " pour aller a "
                      +  ((Requete)probleme.get (l)).getDestination () +
                         " n'est pas satisfaite.\n", 0) ;  
       }
       jrl.repaint () ; 
       jrl.select  (0, 0) ;       
    }
    
    /**
     * Constructeur d'objets de classe MonGUI
     * <br>Démarre graphiquement l'interface et les informations sur la cdt.
     * <br>Initialise le temps et le rappel périodique de la fonction retour.
     * @param  a   Appliquette ayant servi à l'affichage de l'écran d'accueil
     * @param  nom Nom donné à la compagnie
     * @param  nCG Information sur le comportement des individus
     * @param  arg Information sur l'apport initial
     * 
     * @return un objet de classe MonGUI
     */
    public MonGUI (JFrame a, String nom, String nCG, String arg)
    {

       compagnie = new Compagnie (nom, Integer.parseInt (arg)) ;     
 
       this.a       = a   ;  
       
       cG           = (nCG.equals ("Patients") ? 200 : 
                       nCG.equals ("Impatients") ? 100 : 40 ) ;                     
                       
       matrice      = new boolean [18] [12] ; 
       
       destinations = new HashMap () ; 

       seuilEnervement = (nCG.equals ("Patients") ? 5 : 
                          nCG.equals ("Impatients") ? 3 : 1 ) ;      
                   
       JLabel jl ; 
              
       for (int i = 0 ; i < 16 ; i++)
       {
         for (int j = 0 ; j < 12 ; j++)
         {
            if (i < 11 || j < 16) 
                 matrice [i] [j] = false ; 
            else matrice [i] [j] = true  ; 
         }
       }   
       
       a.setLayout     (new BorderLayout (0, 0)) ;
       a.setSize       (800, 600) ; 
       a.setBackground (new Color (175, 176, 173)) ; 

       haut.setSize    (800, 20) ; 
       haut.setLayout  (new FlowLayout (FlowLayout.LEFT, 30 , 10))  ;  
       haut.setBackground (new Color (238, 238, 238)) ; 

       ecran.setPreferredSize (new Dimension (800, 500)) ; 
       ecran.setBorder (BorderFactory.createTitledBorder ("Situation Actuelle")) ;  
       ecran.addMouseListener (this) ; 
       bas.setLayout   (new FlowLayout (FlowLayout.RIGHT , 60 , 10)) ; 
       bas.setBackground (new Color (238, 238, 238)) ; 

       matrice [14] [0] = true ; 
       matrice [0] [10] = true ;
       matrice [1] [10] = true ;
       
       
       int i = 0 ; 
       while (i < 30)
       {
          int k = (int) rand.nextInt (15), j = (int)rand.nextInt (10) ; 
          if (!matrice [k] [j])
          { 
            try {
              jl = new JLabel (new ImageIcon 
                          ("imgs/immeuble" + (i / 10 + 1) + ".png")) ;  
              jl.setBounds (k * 47 + 5, j * 47 + 25, 47, 47) ; 
              ecran.add (jl, 30 * j + (30 - k * 2) + 1) ; 
              matrice [k] [j] = true ; 
              i++ ; 
            }catch (Exception e) {
             e.printStackTrace () ; 
            }
          }
       }

       try {
         jl = new JLabel (new ImageIcon ("imgs/siege.png")) ; 
         jl.setBounds (710, 25, 54, 67) ; 
         ecran.add (jl, 30 * 10) ;  
       
         jl = new JLabel (new ImageIcon ("imgs/aeroport.png")) ; 
         jl.setBounds (5, 440, 131, 58) ; 
         ecran.add (jl, 30 * 10) ;  
        }catch (Exception e) {
             e.printStackTrace () ; 
       }
       
       jl = new JLabel (nom) ; 
       jl.setBounds (710, 15, 90, 10) ; 
       ecran.add (jl, 30 * 10) ;        
       
       ajout.addActionListener (this)    ; 
       ajout.setActionCommand  ("Achat") ; 

       def.addActionListener (this)    ; 
       def.setActionCommand  ("NouvDest") ;        
       
       modif.addActionListener (this)    ; 
       modif.setActionCommand  ("ModifVehicule") ; 

       chinois.addActionListener (this)    ; 
       chinois.setActionCommand  ("Chinois") ; 

       
       arret.addActionListener (this)    ; 
       arret.setActionCommand  ("Arret") ; 
       
       haut.add  (new Label ("Commandes possibles :")) ; 
       haut.add  (ajout)   ; 
       haut.add  (def)     ;        
       haut.add  (modif)   ;
       haut.add  (chinois) ;
       haut.add  (arret)   ;
       
       jrl.insert ("Démarrage", 0) ;  
       bas.add    (jrl)   ;
       bas.add    (etats) ;
       
       gui.add (haut)  ; 
       gui.add (ecran) ; 
       gui.add (bas)   ; 
       a.add   (gui)   ; 
       a.pack () ; 
       new Timer (100, taskPerformer).start () ; 
       a.validate ()   ; 
       a.setSize (800, 600) ;
    }

    
    /* Fonction de retour 
     * Appellé périodiquement
     * Met à jour toutes les informations sur les véhicules et passagers
     */
    private void retour (ActionEvent e)
    { 
       String etatsS = "" ; 
       
       if (destinations.size () > 0 && (rand.nextInt (10000) == 0 || arrChinois))
       {
         Bus b ; 
         String[] choix   = (String[]) destinations.keySet ().toArray (new String [0]) ; 
         String  arrivee  =  choix [rand.nextInt (choix.length)] ; 
         
         try {
           JLabel jl  = new JLabel (new ImageIcon ("imgs/type" + rand.nextInt (9) + ".gif")) ; 
           new Requete (compagnie, "bus", "Aeroport Nice cote d'azur", arrivee, jl) ; 
           jl.setBounds (60 , 460, 27, 44) ; 
         ecran.add (jl, 0) ; 
        }catch (Exception exc) {
             exc.printStackTrace () ; 
        }
         
         arrChinois = false ; 
         
         b = compagnie.chercherBusLibre () ; 
         
         if (b != null)
         {
           b.ajoutDest ("Aeroport Nice cote d'azur") ; 
           b.ajoutDest (arrivee) ; 
           b.setlaX (47)  ; 
           b.setlaY (470) ; 
           b.arretSuivant (seuilEnervement) ; 
           b.setEtat ("va chercher les chinois") ; 
         }
         jrl.insert ("Un groupe de chinois veut aller a l'arret " + arrivee + ".\n", 0) ; 
         jrl.repaint () ; 
         jrl.select  (0, 0) ;
        }
    
       if (destinations.size () > 1 && rand.nextInt (cG + 6 - destinations.size () * 3) == 0)
       {
         String  bOuT      = (rand.nextInt (10) < 9 ? "bus" : "taxi") ; 
         String [] choix   = (String[]) destinations.keySet ().toArray (new String [0]) ; 
         String    depart  =  choix [rand.nextInt (choix.length)] ; 
         String    arrivee =  choix [rand.nextInt  (choix.length)] ;
         JLabel de ; 
         
         while (depart.equals (arrivee))
          arrivee = choix [rand.nextInt (destinations.size ())] ; 
         
         de  = (JLabel) destinations.get  (depart) ; 
         
         try {
           JLabel jl  = new JLabel (new ImageIcon ("imgs/type" + rand.nextInt (9) + ".gif")) ; 
           jl.setBounds ((int) de.getBounds ().getX () + rand.nextInt (40) , 
                         (int) de.getBounds ().getY (), 27, 44) ; 
           ecran.add (jl, 0) ; 
           new Requete (compagnie, bOuT, depart, arrivee, jl) ; 
         }catch (Exception exc) {
             exc.printStackTrace () ; 
         }         
         
         jrl.insert ("Quelqu'un attend a " + depart + " pour aller a "+ arrivee + " en "
                      + bOuT +".\n", 0) ;  
         jrl.repaint () ; 
         jrl.select  (0, 0) ; 
       }
       
       for (int i = 0 ; i < compagnie.getNombreVehicules () ; i ++)
       {
          int getX = compagnie.getVehicule (i).getX (), 
              getY = compagnie.getVehicule (i).getY (), 
              laX  = compagnie.getVehicule (i).getlaX (), 
              laY  = compagnie.getVehicule (i).getlaY (), 
              vitesse = compagnie.getVehicule (i).getVitesse () ;   

          if  (laX - getX < 40 && laX - getX > - 40 &&
               laY - getY < 10  &&  laY - getY > - 10 && vitesse >= 13) 
            compagnie.getVehicule (i).setVitesse (vitesse - 3) ; 
          else if  (vitesse < 50 && (laX != 658 || laY != 47)) 
            compagnie.getVehicule (i).setVitesse (vitesse + 1) ; 

          if (deplacement.size () >= i + 1)
          {
              int val = ((Integer) deplacement.get (i)).intValue () ; 
              if (val == 0 && ((getX + getY / 2) % 47 >= 44  || 
                               (getX + getY / 2) % 47 <= 2))
              {
                  if ((getX + getY / 2) % 47 >= 44)
                    compagnie.getVehicule (i).setX 
                    (getX + 47 - (getX + getY / 2) % 47) ; 
                  else compagnie.getVehicule (i).setX 
                  (getX - (getX + getY / 2) % 47)   ;
                  deplacement.set (i, new Integer (rand.nextInt (2))) ; 
              }

              if (val == 1 && (getY % 47 >= 44  || getY % 47 <= 2))
              {
                  if (getY % 47 >= 44)
                   compagnie.getVehicule (i).setY (getY + 47 - getY % 47) ; 
                  else compagnie.getVehicule (i).setY (getY -  getY % 47) ; 
                  deplacement.set (i, new Integer (rand.nextInt (2))) ; 
              }
              if (getX / 47 + 1 > 16 || getX / 47 - 1 < 0)
                  deplacement.set (i, new Integer (1)) ; 
              else if (getY / 47 + 1 > 11 || getY / 47 - 1 < 0)
                  deplacement.set (i, new Integer (0)) ; 
              else
              {
                if (laX > getX && 
                    matrice [(getX + 5)/ 47 + 1] [(getY + 25)/ 47])
                    deplacement.set (i, new Integer (1)) ;
                if (laY > getY && 
                    matrice [(getX + 5) / 47] [(getY + 25) / 47 + 1])
                    deplacement.set (i, new Integer (0)) ;                  
                if (laX < getX && 
                    matrice [(getX + 5) / 47 - 1] [(getY + 25) / 47])
                    deplacement.set (i, new Integer (1)) ;
                if (laY < getY && 
                    matrice [(getX + 5) / 47] [(getY + 25) / 47 - 1])
                    deplacement.set (i, new Integer (0)) ; 
                if (getX >= laX - 3 
                    && getX <= laX + 3)
                    deplacement.set (i, new Integer (1)) ; 
                if (getY >= laY - 3 
                    && getY <= laY + 3)
                    deplacement.set (i, new Integer (0)) ;         
                if (((Vehicule) compagnie.getVehicule (i)).getType ().equals ("taxi")
                    && ((Taxi) compagnie.getVehicule (i)).getDest ().equals (""))
                    {
                      JLabel tmp ; 
                      compagnie.seDirigerTaxi ((Taxi) compagnie.getVehicule (i)) ; 
                      if (!((Taxi) compagnie.getVehicule (i)).getDest ().equals (""))
                      {
                        tmp = (JLabel) destinations.get (
                        ((Taxi) compagnie.getVehicule (i)).getDest ()) ; 
                        ((Taxi) compagnie.getVehicule (i)).setlaX (tmp.getX ()) ; 
                        ((Taxi) compagnie.getVehicule (i)).setlaY (tmp.getY ()) ; 
                        ((Vehicule) compagnie.getVehicule (i)).setEtat 
                                   ("va chercher des passagers a l'arret " + 
                                   ((Taxi) compagnie.getVehicule (i)).getDest ()) ; 
                      }
                    }
                if (getX >= laX - 3 && getX <= laX + 3 && 
                    getY >= laY - 3 && getY <= laY + 3 && 
                    getX != 658 && getY != 47)
                {
                   compagnie.getVehicule (i).setVitesse (0) ; 
                   if (((Vehicule) compagnie.getVehicule (i)).getType ().equals ("bus"))
                   {
                       JLabel tmp ; 
                       ArrayList probleme ; 
                       
                       probleme = ((Bus) compagnie.getVehicule (i)).arretSuivant 
                                                                        (seuilEnervement) ; 
                   
                       if (!(getX <= 50 && getX >= 44 && getY <= 473 && getY >= 466) 
                           && ((Bus) compagnie.getVehicule (i)).getDest (0).equals (
                           "Aeroport Nice cote d'azur"))
                           {
                             ((Bus) compagnie.getVehicule (i)).supprTrajet () ; 
                             ((Bus) compagnie.getVehicule (i)).setlaX (658)   ; 
                             ((Bus) compagnie.getVehicule (i)).setlaY (47)    ;
                             ((Bus) compagnie.getVehicule (i)).revenir ()     ; 
                             ((Bus) compagnie.getVehicule (i)).setEtat ("patiente") ; 
                           }else {                                          
                       
                       informerProblemes (probleme) ;                  
                       tmp = (JLabel) destinations.get (
                       ((Bus) compagnie.getVehicule (i)).getDest ()) ; 
                       ((Bus) compagnie.getVehicule (i)).setlaX (tmp.getX ()) ; 
                       ((Bus) compagnie.getVehicule (i)).setlaY (tmp.getY ()) ; 
                       jrl.insert (compagnie.getConducteur (i) + " se dirige vers l'arret " +
                       ((Bus) compagnie.getVehicule (i)).getDest () + "\n", 0) ;  
                       jrl.repaint () ; 
                       jrl.select  (0, 0) ; 
                       ((Vehicule) compagnie.getVehicule (i)).setEtat 
                                   ("se dirige vers l'arret " + 
                                   ((Bus) compagnie.getVehicule (i)).getDest ()) ;
                                }
                    }else{
                       JLabel tmp ; 
                       if (!(((Taxi) compagnie.getVehicule (i)).vide ()))
                       {
                          ((Vehicule) compagnie.getVehicule (i)).setEtat 
                                   ("a termine sa course") ; 
                          ((Taxi) compagnie.getVehicule (i)).courseTerminee () ; 
                          ((Taxi) compagnie.getVehicule (i)).setDest ("") ; 
                          ((Taxi) compagnie.getVehicule (i)).setlaX (658) ; 
                          ((Taxi) compagnie.getVehicule (i)).setlaY (47) ; 
                       }
                       else
                       {
                         String  endroit = new String 
                                           (((Taxi) compagnie.getVehicule (i)).getDest ()) ; 
                         ArrayList probleme = compagnie.faireMonterQuelqunTaxi 
                           (((Taxi) compagnie.getVehicule (i)), endroit, seuilEnervement) ; 

                         informerProblemes (probleme) ; 
                           
                         tmp = (JLabel) destinations.get (
                         ((Taxi) compagnie.getVehicule (i)).getDest ()) ; 
                         if (tmp != null)
                         {
                           ((Vehicule) compagnie.getVehicule (i)).setEtat 
                                   ("a recupere des passagers pour " +   
                                   (((Taxi) compagnie.getVehicule (i)).getDest ())) ; 
                           ((Taxi) compagnie.getVehicule (i)).setlaX (tmp.getX ()) ; 
                           ((Taxi) compagnie.getVehicule (i)).setlaY (tmp.getY ()) ; 
                         }
                        }
                    }
                }
            }
        }
          else deplacement.add (new Integer (rand.nextInt (2))) ; 
          int val = ((Integer) deplacement.get (i)).intValue () ;           
          if  (laX >= getX && val == 0)
          {
              compagnie.getVehicule (i).setX (getX + vitesse / 10)  ; 
             try{
               if (((Vehicule)compagnie.getVehicule (i)).getType ().equals ("bus")
                   && vitesse >= 10)
                  compagnie.getVehicule (i).getJl ().setIcon (
                                                      new ImageIcon ("imgs/busd.png")) ; 
               else if (vitesse >= 10)
                 compagnie.getVehicule (i).getJl ().setIcon 
                                                      (new ImageIcon ("imgs/taxid.png")) ;
             }catch (Exception exc) {
               exc.printStackTrace () ; 
             }  
          }
          if  (laX < getX && val == 0)
          {
            try{
              compagnie.getVehicule (i).setX (getX - vitesse / 10)  ;
              if (((Vehicule)compagnie.getVehicule (i)).getType ().equals ("bus")
                   && vitesse >= 10)
                compagnie.getVehicule (i).getJl ().setIcon (new ImageIcon ("imgs/busg.png")) ; 
               else if (vitesse >= 10) 
                 compagnie.getVehicule (i).getJl ().setIcon 
                                                      (new ImageIcon ("imgs/taxig.png")) ;
            }catch (Exception exc) {
              exc.printStackTrace () ; 
            }  
          }
          if  (laY >= getY && val == 1)
          {
            try{
              compagnie.getVehicule (i).setY (getY + vitesse / 10)  ;
              compagnie.getVehicule (i).setX (getX + vitesse / 15)  ; 
              if (((Vehicule)compagnie.getVehicule (i)).getType ().equals ("bus")
                   && vitesse >= 10)
               compagnie.getVehicule (i).getJl ().setIcon (new ImageIcon ("imgs/busbd.png")) ; 
              else if (vitesse >= 10)
                compagnie.getVehicule (i).getJl ().setIcon 
                                                      (new ImageIcon ("imgs/taxibd.png")) ;   
            }catch (Exception exc) {
              exc.printStackTrace () ; 
            }  
          }
          
          if  (laY < getY && val == 1)
          {
            try{
              compagnie.getVehicule (i).setY (getY - vitesse / 10)  ;
              compagnie.getVehicule (i).setX (getX - vitesse / 15)  ; 
              if (((Vehicule)compagnie.getVehicule (i)).getType ().equals ("bus")
                   && vitesse >= 10)
               compagnie.getVehicule (i).getJl ().setIcon (new ImageIcon ("imgs/bushg.png")) ; 
              else if (vitesse >= 10)
                compagnie.getVehicule (i).getJl ().setIcon 
                                                      (new ImageIcon ("imgs/taxihg.png")) ;   
            }catch (Exception exc) {
               exc.printStackTrace () ; 
            }  
          }
           
          if (getX > 730 || getX < 5)
          { 
            compagnie.getVehicule (i).setX (730) ; 
            deplacement.set (i, new Integer (1)) ; 
          }
          
            if (compagnie.getVehicule (i).getVitesse () >= 10)
            {
              compagnie.getVehicule (i).getJl ().setBounds 
              (compagnie.getVehicule (i).getX (), 
               compagnie.getVehicule (i).getY (), 47, 47) ;  
              ecran.setLayer (compagnie.getVehicule (i).getJl (),
               30 * (compagnie.getVehicule (i).getY () / 47) + 
               (compagnie.getVehicule (i).getX () / 47 - 1) * 2) ; 
            }
            ((Vehicule) compagnie.getVehicule (i)).avancerTemps () ; 
            etatsS += compagnie.getConducteur (i) + " " + 
                      ((Vehicule) compagnie.getVehicule (i)).getEtat () + " depuis " +
                      (((Vehicule) compagnie.getVehicule (i)).getNbSecondes () / 10 )
                      + " secondes.\n" ; 
        }
        etats.setText (etatsS) ; 
        etats.repaint () ; 
        etats.select  (0, 0) ; 
    }
    
    /** Gestion des commandes utilisateurs, notamment celles entrainées par la pression
     *  d'un bouton utilisateur
     *  
     *  <br>Ne doit pas être appellé par appel direct
     *  
     *  @param e Fournit les informations sur l'évenement déclencheur
     *  @return rien
     */
    public void actionPerformed (ActionEvent e)
    {
        String s ; 
        s = e.getActionCommand () ; 
        
        if ("NouvDest".equals (s))
        {
          ecran.setCursor (new Cursor (1)) ; 
          definir = !definir ; 
        }
        
        if ("Chinois".equals (s))
          arrChinois = true ; 
        
        if ("Achat".equals (s))
        {
          JPanel a       = new JPanel () ; 
          JPanel b       = new JPanel () ;
          JPanel c       = new JPanel () ; 
          JPanel d       = new JPanel () ; 
          JPanel f       = new JPanel () ; 
          JPanel region  = new JPanel () ;  
          
          tNomConduct   = new JTextField ("", 20) ; 
          
          JButton achat  = new JButton ("Achat") ; 
          
          cadre.removeAll () ; 
          cadre.setBounds (10, 10, 200, 300) ; 
          cadre.setBackground (new Color (238, 238, 238)) ; 
          
          a.add (new Label    ("Argent disponible : " + compagnie.getArgent () + " K€"), 
                               BorderLayout.CENTER) ;

          region.setLayout (new GridLayout (5, 1)) ; 
          b.add (new Checkbox ("Acheter un bus" , cbg, false ))    ; 
          
          try{
          b.add (new JLabel   (new ImageIcon ("imgs/busbd.png")))  ; 
          }catch (Exception exc) {
            exc.printStackTrace () ; 
          }

          b.add (new JLabel    ("700 k€")) ; 
          achat.addActionListener (this)    ;     
          achat.setActionCommand  ("ValiderAchat") ; 
          
          c.add (new Checkbox ("Acheter un taxi", cbg, false))     ; 

          try{
          c.add (new JLabel   (new ImageIcon ("imgs/taxibd.png"))) ; 
          }catch (Exception exc) {
            exc.printStackTrace () ; 
          }

          c.add (new JLabel    ("75 k€")) ; 
          d.add (new JLabel    ("Nom du conducteur : ")) ; 
          d.add (tNomConduct) ; 
          f.add (achat)  ; 

          region.add (a) ; 
          region.add (b) ; 
          region.add (c) ;
          region.add (d) ; 
          region.add (f) ; 
          
          cadre.add  (region) ; 
          cadre.pack () ; 
          cadre.validate () ; 
          cadre.setVisible (true) ; 
        }
        
        if ("AjouterEntree".equals (s))
        {
          listed.add (listeun.getSelectedItem ()) ; 
          listeun.remove (listeun.getSelectedItem ()) ; 
        }
        
        if ("SupprEntree".equals (s))
        {
          listeun.add (listed.getSelectedItem ()) ; 
          listed.remove (listed.getSelectedItem ()) ; 
        }
        
        if ("ModifVehicule".equals (s))
        {
          JPanel a       = new JPanel () ; 
          JPanel b       = new JPanel () ;
          JPanel c       = new JPanel () ; 
          JPanel d       = new JPanel () ; 
          JPanel f       = new JPanel () ; 
          JPanel region  = new JPanel () ;  
          
          JButton aj     = new JButton ("<- Ajouter     ") ; 
          JButton su     = new JButton ("   Supprimer ->") ; 
          JButton v      = new JButton ("Valider") ; 
          
          Iterator it   = destinations.keySet ().iterator () ; 
          
          JButton achat  = new JButton ("Achat") ; 
          
          cadre.removeAll () ; 
          cadre.setBounds (10, 10, 200, 300) ; 
          cadre.setBackground (new Color (236, 238, 238)) ; 
          
          listeCond.removeAll () ; 
          listeun.removeAll () ; 
          listed.removeAll () ; 
          for (int i = 0 ; i < compagnie.getNombreVehicules () ; i++)
          {
            if (((Vehicule) compagnie.getVehicule (i)).getType ().equals ("bus"))
              listeCond.add (compagnie.getConducteur (i)) ; 
          }

          region.setLayout (new GridLayout (2, 2)) ; 
          a.setLayout (new GridLayout (2, 2)) ; 
          a.add (new Label ("Etape un : choisissez le bus")) ;
          a.add (new Label ("")) ; 
          a.add (new Label ("Je prends le bus de ")) ; 
          a.add (listeCond) ; 
          
          b.add (listed) ; 
          
          c.setLayout (new GridLayout (1, 2)) ; 
          c.add (aj) ; 
          c.add (su) ;          
          
          aj.addActionListener (this)    ;     
          aj.setActionCommand  ("AjouterEntree") ; 
          
          su.addActionListener (this)    ;     
          su.setActionCommand  ("SupprEntree") ; 
          
          b.add (c) ; 
          
          while (it.hasNext ())
            listeun. add ((String) it.next ()) ; 
          
          b.add (listeun) ; 
                    
          
          region.add (a) ; 
          region.add (b) ;          
          region.add (new Label  ("")) ;
          
          v.setSize (20, 20) ; 
          region.add (v) ; 
          v.addActionListener (this) ;     
          v.setActionCommand  ("ValiderTrajet") ; 
          
          cadre.add  (region) ; 
          cadre.pack () ; 
          cadre.validate () ; 
          cadre.setVisible (true) ; 
        }
        
        if ("ValiderAchat".equals (s))
        {
           if (cbg.getSelectedCheckbox ().getLabel ().equals ("Acheter un bus"))
           {
              if (compagnie.getArgent () >= 700) 
              {
                Bus b = new Bus () ; 
                cadre.setVisible (false) ; 
                compagnie.acheterBus (b, tNomConduct.getText ()) ; 
                jrl.insert ("Un bus a été acheté. Argent restant : " + 
                            compagnie.getArgent () + " k€\n", 0) ;  
                jrl.repaint () ; 
                jrl.select  (0, 0) ; 
                ecran.add (b.getJl (), 0) ; 
                
              }
           }
           
           if (cbg.getSelectedCheckbox ().getLabel ().equals ("Acheter un taxi"))
           {
              if (compagnie.getArgent () >= 75) 
              {
                Taxi t = new Taxi () ; 
                cadre.setVisible (false) ; 
                compagnie.acheterTaxi (t, tNomConduct.getText ()) ; 
                jrl.insert ("Un taxi a été acheté. Argent restant : " + 
                            compagnie.getArgent () + " k€\n", 0) ;  
                jrl.repaint () ; 
                jrl.select  (0, 0) ; 
                ecran.add (t.getJl (), 0) ;  
              }
           }
        }
        
        if ("ValiderDest".equals (s))
        {
          JLabel texte = new JLabel (tDestACreer.getText ()) ; 
          cadre.setVisible (false) ; 
          destPSauve.setLocation (destPSauve.getX () - destPSauve.getX () % 47,
                                  destPSauve.getY () - destPSauve.getY () % 47) ; 
 
          try{
            JLabel jl    = new JLabel (new ImageIcon ("imgs/abri.png")) ;  
            jl.setBounds ((int) destPSauve.getX (), (int) destPSauve.getY (), 64, 28) ; 
            ecran.add (jl, 30 * ((int) destPSauve.getY () / 47) + 
                           30 - ((int) destPSauve.getX () / 47) * 2 + 1) ; 
            destinations.put (tDestACreer.getText (), jl) ; 
          }catch (Exception exc) {
            exc.printStackTrace () ; 
          }
          texte.setBounds ((int) destPSauve.getX (), 
                           (int) destPSauve.getY () - 11, 64, 14) ;  
          ecran.add (texte, 30 * ((int) destPSauve.getY () / 47) + 
                            30 - ((int) destPSauve.getX () / 47) * 2 + 1) ; 
          jrl.insert ("Nouvelle destination : " + tDestACreer.getText () + "\n", 0) ;  
          jrl.repaint () ; 
          jrl.select  (0, 0) ;         
        }
        
        if ("ValiderTrajet".equals (s))
        {
            JLabel dest      = (JLabel) destinations.get (listed.getItem (0)) ; 
            
            cadre.setVisible (false) ;          
            
            compagnie.getBus (listeCond.getSelectedItem ()).supprTrajet () ; 
            
            for (int i = 0 ; i < listed.getItemCount () ; i++)
              compagnie.getBus (listeCond.getSelectedItem ()).
                                                        ajoutDest (listed.getItem (i)) ; 
                                                        
            compagnie.getBus (listeCond.getSelectedItem ()).revenir () ;            
            compagnie.getBus (listeCond.getSelectedItem ()).setlaX (dest.getX ()) ; 
            compagnie.getBus (listeCond.getSelectedItem ()).setlaY (dest.getY ()) ;  
            
            
            jrl.insert (listeCond.getSelectedItem () +
                        " a effectué le changement de trajet.\n", 0) ;  
            jrl.insert (listeCond.getSelectedItem () + " se dirige vers l'arret " +
                        listed.getItem (0) + "\n", 0) ;  
            jrl.repaint () ; 
            jrl.select  (0, 0) ; 
            compagnie.getBus (listeCond.getSelectedItem ()).setEtat 
              (" se dirige vers l'arret " + (listed.getItem (0))) ; 
        }
        if ("Arret".equals (s))
        {
          System.exit (0) ; 
        }
        
    }
    
    /**
     * Methode non utilisée dans cette classe
     */
    public void mousePressed  (MouseEvent e) 
    {}

    /**
     * Methode non utilisée dans cette classe
     */
    public void mouseReleased (MouseEvent e) 
    {}

    /**
     * Methode non utilisée dans cette classe
     */
    public void mouseEntered  (MouseEvent e) 
    {}

    /**
     * Methode non utilisée dans cette classe
     */
    public void mouseExited  (MouseEvent e) 
    {}

    /**
     * Fonction de prise en charge de la souris lors d'un clic.
     * 
     * <br>Permet de repérer par coordonnées une nouvelle destination afin de la créer
     * 
     * @param  e Fournit des informations sur l'état de la souris.
     */
    public void mouseClicked (MouseEvent e) 
    {
        if (definir)
        {
          Panel a       = new Panel () ;
          Panel b       = new Panel () ; 
          Panel c       = new Panel () ; 
          Panel region  = new Panel () ;  
          
          Button creer  = new Button  ("Créer") ; 
          
          tDestACreer = new JTextField ("", 20) ;
          
          definir = false ; 
          ecran.setCursor (new Cursor (0)) ; 
          destPSauve = e.getPoint () ; 
          
          cadre.removeAll () ; 
          cadre.setBounds (10, 10, 200, 300) ; 
          cadre.setBackground (new Color (238, 238, 238)) ; 
          
          region.setLayout (new GridLayout (2, 2)) ; 
          a.add (new Label ("Donner un nom a cet arret."), BorderLayout.CENTER) ; 

          b.add (tDestACreer) ; 

          creer.addActionListener (this)    ;     
          creer.setActionCommand  ("ValiderDest") ; 
          c.add (creer) ; 

          region.add (a) ; 
          region.add (b) ; 
          region.add (c) ;
          
          cadre.add  (region) ; 
          cadre.pack     () ; 
          cadre.validate () ; 
          cadre.setVisible (true) ; 
        }
    }
}