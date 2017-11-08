import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;





public class RetirerTache extends JFrame
{

    private ListeTache Liste;
    private JLabel Label1;
    private int Classement;
    private JFrame Fenetre;
    public RetirerTache(ListeTache liste, int j)
    {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Liste = liste;
        this.Classement = j;

        //1 = on veut retirer --- 2 = on veut modifier
        if(Classement == 1)
        {
            Label1 = new JLabel("Veuillez choisir le numéro de la tache que vous voulez retirer");
            setTitle(" Retirer tache ");
        }
        else
        {
            Label1 = new JLabel("Veuillez choisir le numéro de la tache que vous voulez modifier");
            setTitle(" Modifier tache ");
        }

        JTextField Numéro = new JTextField("");
        Numéro.setPreferredSize( new Dimension( 200, 24 ) );
        JPanel[] Panel = new JPanel[2];

        //crée les différent panel
        for(int i=0; i<2; i++)
            Panel[i] = new JPanel();

        //ajoute les différents objets dans les panels
        Panel[0].setSize(400, 400);
        Panel[0].add(Label1);

        Panel[1].setSize(400, 400);
        Panel[1].add(Numéro);

        add(Panel[0], "North");
        add(Panel[1], "South");

        pack();

        Numéro.addActionListener(new Actionnuméro());
    }

    class Actionnuméro implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            //on vérifie si le numéro taper est correct
            JTextField Numéro = (JTextField)e.getSource();
            int Num = Integer.parseInt(Numéro.getText());

            //s'il ne l'est pas
            if((Num > Liste.getTaille()) || (Num < 0))
            {
                //on affiche un message d'erreur
                JOptionPane Jop = new JOptionPane();
                Jop.showMessageDialog(null, "Erreur, veuillez choisir un numéro valide !!", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                //on veut retirer une tache
                if(Classement == 1)
                {
                    Liste.getTaches().remove(Num-1);
                    Fenetre = new FenetrePrincipale(Liste);
                    Fenetre.setVisible(true);
                    dispose();
                }
                else
                {
                    //on veut modifier une tache
                    Fenetre = new ModifierTache(Liste, Num-1);
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
