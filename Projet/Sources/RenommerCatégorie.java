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


public class RenommerCatégorie extends JFrame
{
    //liste de tache
    private ListeTache Liste;

    //composant de la fenètre
    private JComboBox Catégorie;

    //variable qui enregistre les valeurs selectionner
    private String Catégorie2;

    public RenommerCatégorie(ListeTache liste)
    {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //crée la nouvelle liste de tache a envoyer a la fenetre principale
        Liste = new ListeTache();
        Liste = liste;

        //paramètre la fenetre
        this.setTitle(" Renommer catégorie ");
        this.setPreferredSize(new Dimension(450,220));

        //crée les différent panel
        JPanel Panel[] = new JPanel[3];
        for(int j=0; j<3; j++)
            Panel[j] = new JPanel();

        //crée tous les labels
        JLabel Label1 = new JLabel("Veuillez choisir la catégorie que vous voulez renommer");
        JLabel Label2 = new JLabel("Catégorie");
        JLabel Label3 = new JLabel("Renommer");

        //transforme mon vecteur de catégorie en tableau de catégorie et je mes dans un JComboBox
        Vector<String> Caté = new Vector<String>();
        Caté = Liste.getCatégories();
        String[] Typ = Caté.toArray(new String[Caté.size()]);

        Catégorie = new JComboBox(Typ);

        JTextField Catégori = new JTextField("");
        Catégori.setPreferredSize( new Dimension( 200, 24));


        //ajoute les différents dans les panels
        Panel[0].add(Label1);

        Panel[1].add(Label2);
        Panel[1].add(Catégorie);

        Panel[2].add(Label3);
        Panel[2].add(Catégori);

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

        //ajout des panels a la fenètre
        add(Panel[0]);
        add(Panel[1]);
        add(Panel[2]);

        pack();

        //crée les ecouteur des éléments de la fenètre
        Catégorie.addActionListener(new Actioncatégorie());
        Catégori.addActionListener(new ActioncatégorieT());
    }

    class Actioncatégorie implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            Catégorie2 = (String)Catégorie.getSelectedItem();
        }
    }

    class ActioncatégorieT implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
	    //modifie la catégorie
	    Liste.getCatégories().remove(Catégorie2);
	    Liste.getCatégories().addElement(CatégorieT2);

            //récupère le texte taper
            JTextField C = (JTextField)e.getSource();
            String CatégorieT2 = C.getText();

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
                    //si la tache possède l'ancienne catégorie on lui donne la nouvelle
                    if(Liste.getTaches(i).getCatégorie() == Catégorie2)
                        Liste.getTaches(i).setCatégorie(CatégorieT2);
                }
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
