import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class AjoutTache extends JFrame
{

//liste de tache en parametre global
    private ListeTache Liste;

//composant de la fenètre
    private JTextField Titre;
    private JComboBox Catégorie;
    private JComboBox Type;
    private JTextField Jour;
    private JTextField Mois;
    private JTextField Années;
    private JButton Bouton;

    private JFrame Fenetre;

//variable qui enregistre les valeurs selectionner
    private String Catégorie2;
    private String Type2;

    public AjoutTache(ListeTache liste)
    {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //crée la nouvelle liste de tache a envoyer a la fenetre principale
        this.Liste = new ListeTache();
        this.Liste = liste;

        //paramètre la fenetre
        setTitle(" Ajouter tache ");
        this.setPreferredSize(new Dimension(450,220));

        //crée les différent panel
        JPanel Panel[] = new JPanel[4];
        for(int i=0; i<4; i++)
            Panel[i] = new JPanel();

        //crée tous les labels
        JLabel Label1 = new JLabel("Titre");
        JLabel Label2 = new JLabel("Catégorie");
        JLabel Label3 = new JLabel("Date échéance  JJ :");
        JLabel Label4 = new JLabel(" MM :");
        JLabel Label5 = new JLabel(" NNNN :");
        JLabel Label6 = new JLabel("Type");


        String[] Typ = {"ponctuelle", "longue"};
        Type = new JComboBox(Typ);

        //transforme mon vecteur de catégorie en tableau de catégorie et je mes dans un JComboBox
        Vector<String> Caté = new Vector<String>();
        Caté = Liste.getCatégories();
        String[] Catégo = Caté.toArray(new String[Caté.size()]);
        Catégorie = new JComboBox(Catégo);

        Titre = new JTextField("");
        Titre.setPreferredSize( new Dimension( 200, 24 ) );
        Jour = new JTextField("");
        Jour.setPreferredSize( new Dimension( 50, 24));
        Mois = new JTextField("");
        Mois.setPreferredSize( new Dimension( 50, 24));
        Années = new JTextField("");
        Années.setPreferredSize( new Dimension( 50, 24));
        Bouton = new JButton("  OK  ");

        //ajoute les différents objets dans les panels
        Panel[0].add(Label1);
        Panel[0].add(Titre);

        Panel[1].add(Label2);
        Panel[1].add(Catégorie);

        Panel[2].add(Label3);
        Panel[2].add(Jour);
        Panel[2].add(Label4);
        Panel[2].add(Mois);
        Panel[2].add(Label5);
        Panel[2].add(Années);

        Panel[3].add(Label6);
        Panel[3].add(Type);

        //mise en forme des éléments dans la fenètre
        GridBagLayout Gb = new GridBagLayout();
        GridBagConstraints Gbc = new GridBagConstraints();
        setLayout(Gb);
        Gbc.fill = GridBagConstraints.BOTH;
        Gbc.weightx = 1;
        Gbc.weighty = 1;
        Gbc.gridx = 1;
        Gb.setConstraints(Panel[0], Gbc); // mise en forme des objets
        Gb.setConstraints(Panel[3], Gbc);
        Gb.setConstraints(Panel[1], Gbc);
        Gb.setConstraints(Panel[2], Gbc);
        Gb.setConstraints(Bouton, Gbc);

        //ajout des panels a la fenètre
        add(Panel[0]);
        add(Panel[3]);
        add(Panel[1]);
        add(Panel[2]);
        add(Bouton);


        pack();

        //crée les ecouteur des éléments de la fenètre
        Titre.addActionListener(new Actiontitre());
        Catégorie.addActionListener(new Actioncatégorie());
        Type.addActionListener(new Actiontype());
        Jour.addActionListener(new Actionjour());
        Mois.addActionListener(new Actionmois());
        Années.addActionListener(new Actionannées());
        Bouton.addActionListener(new Actionbouton());

    }

    class Actiontitre implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            Titre = (JTextField)e.getSource();
        }
    }

    class Actioncatégorie implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            Catégorie2 = (String)Catégorie.getSelectedItem();
        }
    }

    class Actiontype implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            Type2 = (String)Type.getSelectedItem();
        }
    }

    class Actionjour implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            Jour = (JTextField)e.getSource();
        }
    }

    class Actionmois implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            Mois = (JTextField)e.getSource();
        }
    }

    class Actionannées implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            Années = (JTextField)e.getSource();
        }
    }

    class Actionbouton implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            //enregistre les valeur donner par l'utilisteur pour retirer 1 au mois et corriger le bug du GregorianCalandar qui rajoute toujours 1 au mois
            int J = Integer.parseInt(Jour.getText());
            int M = Integer.parseInt(Mois.getText());
            int A = Integer.parseInt(Années.getText());

            //regarde si la date rentrer et plus vieille que celle actuelle
            GregorianCalendar User = new GregorianCalendar(A, M-1, J);
            GregorianCalendar Now = new GregorianCalendar();
            if(User.compareTo(Now) == 1)
            {
                if((J < 1) || (J > 31))
                {
                    JOptionPane Jop = new JOptionPane();
                    Jop.showMessageDialog(null, "Erreur, veuillez choisir un jour valide !!", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
                if((M < 1) || (M > 12))
                {
                    JOptionPane Jop = new JOptionPane();
                    Jop.showMessageDialog(null, "Erreur, veuillez choisir un mois valide !!", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
                //si c'est une tache longue
                else
                {
                    if(Type2 == "longue")
                    {
                        //calcule le nombre de jour qui sépare la date de création a la date d'echeance
                        Date date1 = new GregorianCalendar(A, M-1, J).getTime( );
                        Date today = new Date( );
                        long diff = date1.getTime( ) - today.getTime( );
                        diff = diff / (1000*60*60*24);

                        //crée la nouvelle tache, l'ajoute a la liste et renvoie le tout a la fenetre principale
                        Longue Tache = new Longue(new GregorianCalendar(A, M-1, J), Now, Catégorie2, Titre.getText(), false, false, (int)diff, 0);
                        Liste.setTaches(Tache);
                        Fenetre = new FenetrePrincipale(Liste);
                        Fenetre.setVisible(true);
                        dispose();
                    }
                    //si elle est ponctuelle
                    else
                    {
                        //calcule le nombre de jour qui sépare la date de création a la date d'echeance
                        Date date1 = new GregorianCalendar(A, M-1, J).getTime( );
                        Date today = new Date( );
                        long diff = date1.getTime( ) - today.getTime( );
                        diff = diff / (1000*60*60*24);

                        //crée la nouvelle tache, l'ajoute a la liste et renvoie le tout a la fenetre principale
                        Tache Tache = new Tache(new GregorianCalendar(A, M-1, J), Now, Catégorie2, Titre.getText(), false, false, (int)diff );
                        Liste.setTaches(Tache);
                        Fenetre = new FenetrePrincipale(Liste);
                        Fenetre.setVisible(true);
                        dispose();
                    }
                }
            }
            else
            {
                JOptionPane Jop = new JOptionPane();
                Jop.showMessageDialog(null, "Erreur, veuillez choisir une date valide !!", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    protected void processWindowEvent(WindowEvent e)
    {
        if(e.WINDOW_CLOSING == e.getID())
        {
            Fenetre = new FenetrePrincipale(Liste);
            Fenetre.setVisible(true);
            dispose();
        }
    }
}
