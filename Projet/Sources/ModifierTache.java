import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;





public class ModifierTache extends JFrame
{
    //liste de tache
    private ListeTache Liste;

    //composant de la fenètre
    private JTextField Titre;
    private JComboBox Catégorie;
    private JComboBox Choix;
    private JTextField Pourcentage;
    private JButton Bouton;

    private JFrame Fenetre;

    //variable qui enregistre les valeurs selectionner
    private String Catégorie2;
    private String Choix2;
    private int i;

    public ModifierTache(ListeTache liste, int classement)
    {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //crée la nouvelle liste de tache a envoyer a la fenetre principale
        Liste = new ListeTache();
        Liste = liste;

        //emplacement de la tache a modifier
        i = classement;

        //paramètre la fenetre
        this.setTitle(" Modifier tache ");
        this.setPreferredSize(new Dimension(450,220));

        //crée les différent panel
        JPanel Panel[] = new JPanel[4];
        for(int j=0; j<4; j++)
            Panel[j] = new JPanel();

        //crée tous les labels
        JLabel Label1 = new JLabel("Titre");
        JLabel Label2 = new JLabel("Catégorie");
        JLabel Label3 = new JLabel("Pourcentage");
        JLabel Label4 = new JLabel("Fini");

        //transforme mon vecteur de catégorie en tableau de catégorie et je mes dans un JComboBox
        Vector<String> Caté = new Vector<String>();
        Caté = Liste.getCatégories();
        String[] Typ = Caté.toArray(new String[Caté.size()]);

        Catégorie = new JComboBox(Typ);

        String[] Typ2 = {"Non", "Oui"};
        Choix = new JComboBox(Typ2);

        Titre = new JTextField(Liste.getTaches(i).getTitre());
        Titre.setPreferredSize( new Dimension(200, 24));

        //si c'est une tache longue
        if(Liste.getTaches(i) instanceof Longue)
        {
            //on crée un JTextField pour le placer dans la fenetre et pouvoir modifier ca valeur
            Longue Longue = (Longue)Liste.getTaches(i);
            Pourcentage = new JTextField(Integer.toString(Longue.getPourcentage()));
            Pourcentage.setPreferredSize( new Dimension(50, 24));

        }
        //crée un vide pour pouvoir l'initier plus loin
        else
            Pourcentage = new JTextField("");

        Bouton = new JButton("  OK  ");

        //ajoute les différents dans les panels
        Panel[0].add(Label1);
        Panel[0].add(Titre);

        Panel[1].add(Label2);
        Panel[1].add(Catégorie);

        Panel[2].add(Label3);
        Panel[2].add(Pourcentage);

        Panel[3].add(Label4);
        Panel[3].add(Choix);

        //mise en forme des éléments dans la fenètre
        GridBagLayout Gb = new GridBagLayout();
        GridBagConstraints Gbc = new GridBagConstraints();
        setLayout(Gb);
        Gbc.fill = GridBagConstraints.BOTH;
        Gbc.weightx = 1;
        Gbc.weighty = 1;
        Gbc.gridx = 1;
        Gb.setConstraints(Panel[0], Gbc); // mise en forme des objets
        Gb.setConstraints(Panel[1], Gbc);
        Gb.setConstraints(Panel[2], Gbc);
        Gb.setConstraints(Panel[3], Gbc);
        Gb.setConstraints(Bouton, Gbc);

        //ajout des panels a la fenètre
        add(Panel[0]);
        add(Panel[1]);
        if(Liste.getTaches(i) instanceof Longue)
            add(Panel[2]);
        else
            add(Panel[3]);
        add(Bouton);

        pack();

        //crée les ecouteur des éléments de la fenètre
        Titre.addActionListener(new Actiontitre());
        Catégorie.addActionListener(new Actioncatégorie());
        Choix.addActionListener(new Actionchoix());
        Pourcentage.addActionListener(new ActionPourcentage());
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

    class Actionchoix implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            Choix2 = (String)Choix.getSelectedItem();
        }
    }

    class ActionPourcentage implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            Pourcentage = (JTextField)e.getSource();
        }
    }

    class Actionbouton implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            //si c'est une tache longue
            if(Liste.getTaches(i) instanceof Longue)
            {
                //si la tache arrive a 100% (qu'elle est fini)
                if(Integer.parseInt(Pourcentage.getText()) == 100)
                {
                    ((Longue)(Liste.getTaches(i))).setTitre(Titre.getText());
                    ((Longue)(Liste.getTaches(i))).setCatégorie(Catégorie2);
                    ((Longue)(Liste.getTaches(i))).setPourcentage(Integer.parseInt(Pourcentage.getText()));
                    Liste.getTaches(i).setFini(true);
                    Liste.setFini(Liste.getTaches(i));
                    Liste.getTaches().remove(i);
                    Fenetre = new FenetrePrincipale(Liste);
                    Fenetre.setVisible(true);
                    dispose();
                }
                else
                {
                    //si le pourcentage est supérieur au précédent et inférieur a 101
                    if((Integer.parseInt(Pourcentage.getText()) >= ((Longue)(Liste.getTaches(i))).getPourcentage()) &&  ((Integer.parseInt(Pourcentage.getText()) <= 100)))
                    {
                        ((Longue)(Liste.getTaches(i))).setTitre(Titre.getText());
                        ((Longue)(Liste.getTaches(i))).setCatégorie(Catégorie2);
                        ((Longue)(Liste.getTaches(i))).setPourcentage(Integer.parseInt(Pourcentage.getText()));
                        Fenetre = new FenetrePrincipale(Liste);
                        Fenetre.setVisible(true);
                        dispose();
                    }
                    //fenetre pour signaler l'erreur
                    else
                    {
                        JOptionPane Jop = new JOptionPane();
                        Jop.showMessageDialog(null, "Erreur, veuillez choisir un pourcentage valide !!", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }

            }
            //si c'est une tache ponctuelle
            else
            {
                //si l'utilisateur decide que la tache est fini
                if(Choix2 == "Oui")
                {
                    Liste.getTaches(i).setTitre(Titre.getText());
                    Liste.getTaches(i).setCatégorie(Catégorie2);
                    Liste.getTaches(i).setFini(true);
                    Liste.setFini(Liste.getTaches(i));
                    Liste.getTaches().remove(i);
                    Fenetre = new FenetrePrincipale(Liste);
                    Fenetre.setVisible(true);
                    dispose();
                }
                else
                {
                    Liste.getTaches(i).setTitre(Titre.getText());
                    Liste.getTaches(i).setCatégorie(Catégorie2);
                    Fenetre = new FenetrePrincipale(Liste);
                    Fenetre.setVisible(true);
                    dispose();
                }

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
