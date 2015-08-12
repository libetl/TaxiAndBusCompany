import java.awt.*           ; 
import java.awt.event.*     ;
import javax.swing.*        ; 
import java.net.URL         ; 


/** Classe créant l'écran d'accueil, et créant l'appliquette 
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
 * 
 */
public class Demarrer extends JFrame implements ActionListener
{


    //variables de l'écran d'accueil
    private Label nomInitial = new Label
                                    ("Compagnie de Transports Terrestres")  ; 
    private Label suite      = new Label ("Renseigner les champs suivants") ; 
    private Label lNom       = new Label ("Choisir un nom de compagnie :")  ; 
    private Label lArg       = new Label ("Donner un apport en K€ :")   ; 
    private Label lNCG       = new Label ("Comportement des clients :") ; 
    private TextField nom    = new TextField ("BougeMoiDeLà") ; 
    private JButton valider  = new JButton ("Demarrer maintenant >")  ; 
    private Font defaultFont = new Font   ("SansSerif", 0, 10) ; 
    private JPanel region    = new JPanel  () ; 
    private JPanel coin1     = new JPanel  () ; 
    private JPanel coin2     = new JPanel  () ; 
    private JPanel coin3     = new JPanel  () ; 
    private JPanel coin4     = new JPanel  () ; 
    private JPanel coin5     = new JPanel  () ; 
    private Choice nCG       = new Choice () ; 
    private Choice arg       = new Choice () ; 
    private HorzLn l         = new HorzLn () ; 
    static final long serialVersionUID = 1 ; 
    
    /**
     * Point d'entrée du programme.
     * <br>
     * Contient les instructions nécessaires à l'affichage initial.
     * 
     * @param      aucun
     * @return     aucun 
     */
    public Demarrer () 
    {    
        super ("Gestion de cdt") ; 
        setLayout      (new BorderLayout (0, 0)) ;
        setBackground  (new Color (236, 233, 216)) ; 

        this.setSize (new Dimension (800, 600)) ; 
        this.setFont (defaultFont) ; 
        
        region.setLayout (new GridLayout (5, 1)) ; 

        l.setBounds  (0, 0, 800, 3) ; 
        
        //Encadrement du haut
        coin1.setLayout (new FlowLayout (FlowLayout.RIGHT, 0, 10)) ; 
        coin1.add  (nomInitial) ; 
        for (int i = 0 ; i < 3 ; i++)
          coin1.add  (new Label("                                           ")) ;
        try {
          ImageIcon im =  new ImageIcon ("imgs/clipart.jpg") ; 
          coin1.add  (new JLabel (im)) ; 
        }catch (Exception e) {
          e.printStackTrace();
        }
        coin1.setBackground (Color.white) ; 

        //Titre du formulaire
        coin2.setLayout (new FlowLayout (FlowLayout.LEFT, 0, 0)) ;
        coin2.add  (suite) ; 
        coin2.add  (l) ;
        coin2.setBackground (new Color (236, 233, 216)) ; 

        //Champ nom de compagnie
        coin3.setLayout (new FlowLayout (FlowLayout.CENTER, 0, 0)) ; 
        nom.setBackground   (Color.white) ; 
        coin3.add  (lNom) ; 
        coin3.add  (nom)  ; 
        coin3.setBackground (new Color (236, 233, 216)) ; 

        nCG.addItem ("Patients") ;
        nCG.addItem ("Impatients") ;
        nCG.addItem ("Malhonnêtes") ;
        nCG.setBackground   (Color.white) ; 
        
        arg.addItem ("100")  ; 
        arg.addItem ("200")  ; 
        arg.addItem ("500")  ; 
        arg.addItem ("1000") ;         
        arg.setBackground   (Color.white) ; 
                
        //Choix déroulants
        coin4.setLayout (new FlowLayout (FlowLayout.CENTER, 0, 0)) ; 
        coin4.add  (lArg)   ;
        coin4.add  (arg)    ;
        coin4.add  (lNCG)   ;
        coin4.add  (nCG)    ;
        coin4.setBackground (new Color (236, 233, 216)) ; 
        
        //Création du bouton valider
        valider.addActionListener (this)      ; 
        valider.setActionCommand  ("Valider") ; 
        coin5.setLayout (new FlowLayout (FlowLayout.RIGHT, 0, 80)) ; 
        coin5.add  (new Label ("")) ; 
        coin5.add  (valider) ; 
        coin5.setBackground (new Color (236, 233, 216)) ; 
        
        region.add (coin1) ;
        region.add (coin2) ; 
        region.add (coin3) ; 
        region.add (coin4) ; 
        region.add (coin5) ;


        setDefaultCloseOperation (EXIT_ON_CLOSE) ; 
        setIconImage (Toolkit.getDefaultToolkit().getImage("imgs/clipart.jpg")) ; 
        setDefaultLookAndFeelDecorated (false) ;  
        add (region) ; 
        setVisible (true) ; 
    }
    
    public static void main (String[] args)
    {
      new Demarrer () ; 
    }
    
    /**
     * Fonction de retour d'évenement
     * 
     * Ici, permet de valider les informations et de passer à l'écran de gestion
     * 
     * @param  e   Précision sur l'évenement déclencheur
     * @return     aucun
     */
    public void actionPerformed (ActionEvent e)
    {
        String s ; 
        s = e.getActionCommand () ; 
        
        //Recherche de la chaîne de commande
        if ("Valider".equals (s))
        {
            MonGUI mg ;
            remove (region) ; 
            mg = new MonGUI (this, nom.getText (), nCG.getSelectedItem (),
                                                   arg.getSelectedItem ()) ; 
        }
    }
}

class HorzLn extends Canvas
{
    static final long serialVersionUID = 1 ; 

    public HorzLn () {}
    
    public void paint (Graphics g)
    {
        g.drawLine (0, 2, 1000, 2) ; 
        g.setColor (Color.gray)   ;  
        g.drawLine (0, 1, 1000, 1) ;
        g.setColor (Color.lightGray) ;  
        g.drawLine (0, 0, 10000, 0) ;        
    }
}