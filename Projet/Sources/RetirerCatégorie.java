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




public class RetirerCatégorie extends JFrame
{

    //liste de tache
    private ListeTache Liste;

    //composant de la fenètre
    private JComboBox Catégorie;
    private JButton Bouton;

    //variable qui enregistre les valeurs selectionner
    private String Catégorie2;

    public RetirerCatégorie(ListeTache liste)
    {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //crée la nouvelle liste de tache a envoyer a la fenetre principale
        Liste = new ListeTache();
        Liste = liste;

        //paramètre la fenetre
        this.setTitle(" Retirer catégorie ");
        this.setPreferredSize(new Dimension(450,220));

        //crée les différent panel
        JPanel Panel[] = new JPanel[2];
        for(int j=0; j<2; j++)
            Panel[j] = new JPanel();

        //crée tous les labels
        JLabel Label1 = new JLabel("Veuillez choisir la catégorie que vous voulez supprimer");
        JLabel Label2 = new JLabel("Catégorie");

        //transforme mon vecteur de catégorie en tableau de catégorie et je mes dans un JComboBox
        Vector<String> Caté = new Vector<String>();
        Caté = Liste.getCatégories();
        String[] Typ = Caté.toArray(new String[Caté.size()]);

        Catégorie = new JComboBox(Typ);

        Bouton = new JButton("  OK  ");

        //ajoute les différents dans les panels
        Panel[0].add(Label1);

        Panel[1].add(Label2);
        Panel[1].add(Catégorie);

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
        Gb.setConstraints(Bouton, Gbc);

        //ajout des panels a la fenètre
        add(Panel[0]);
        add(Panel[1]);
        add(Bouton);

        pack();

        //crée les ecouteur des éléments de la fenètre
        Catégorie.addActionListener(new Actioncatégorie());
        Bouton.addActionListener(new Actionbouton());
    }

    class Actioncatégorie implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            Catégorie2 = (String)Catégorie.getSelectedItem();
        }
    }

    class Actionbouton implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if(Catégorie2 == "")
            {
                JOptionPane Jop = new JOptionPane();
                Jop.showMessageDialog(null, "Erreur, veuillez choisir une catégorie !!", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                //pout toute les taches
                for(int i=0; i<Liste.getTaille(); i++)
                {
                    //si la tache possede la catégorie on lui donne a la plave un string vide
                    if(Liste.getTaches(i).getCatégorie() == Catégorie2)
                        Liste.getTaches(i).setCatégorie("");
                }
                Liste.getCatégories().removeElement(Catégorie2);
                JFrame Fenetre = new FenetrePrincipale(Liste);
                Fenetre.setVisible(true);
                dispose();
            }
        }
    }

    protected void processWindowEvent(WindowEvent e)
    {
        if(e.WINDOW_CLOSING == e.getID())
        {
            JFrame Fenetre = new FenetrePrincipale(Liste);
            Fenetre.setVisible(true);
            dispose();
        }
    }
}
