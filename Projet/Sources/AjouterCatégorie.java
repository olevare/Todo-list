import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class AjouterCatégorie extends JFrame
{

    private ListeTache Liste;
    private JLabel Label1;
    private JFrame Fenetre;

    public AjouterCatégorie(ListeTache liste)
    {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //crée la nouvelle liste de tache a envoyer a la fenetre principale
        Liste = liste;

        //paramètre la fenetre
        setTitle(" Ajouter catégorie ");

        Label1 = new JLabel("Veuillez choisir le nom de la catégorie que vous voulez ajouter");

        JTextField Catégorie = new JTextField("");
        Catégorie.setPreferredSize( new Dimension( 200, 24));

        //crée tous les labels
        JPanel[] Panel = new JPanel[2];
        for(int i=0; i<2; i++)
            Panel[i] = new JPanel();

        Panel[0].setSize(400, 400);
        Panel[0].add(Label1);

        Panel[1].setSize(400, 400);
        Panel[1].add(Catégorie);

        add(Panel[0], "North");
        add(Panel[1], "South");

        pack();

        //créer les écouteurs
        Catégorie.addActionListener(new Actioncatégorie());
    }

    class Actioncatégorie implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            JTextField Catégorie = (JTextField)e.getSource();
            String s = Catégorie.getText();
            if(s == "")
            {
                JOptionPane Jop = new JOptionPane();
                Jop.showMessageDialog(null, "Erreur, veuillez choisir une catégorie valide !!", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                //ajoute la catégorie en renvoie le tout a la fenetre principale
                Liste.setCatégories(s);
                Fenetre = new FenetrePrincipale(Liste);
                Fenetre.setVisible(true);
                dispose();
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
