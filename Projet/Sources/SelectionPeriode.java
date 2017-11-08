import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
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


public class SelectionPeriode extends JFrame
{

    //liste de tache en parametre global
    private ListeTache Liste;

    //composant de la fenètre
    private JTextField Jour;
    private JTextField Mois;
    private JTextField Années;
    private JTextField Jour2;
    private JTextField Mois2;
    private JTextField Années2;
    private JButton Bouton;

    private JFrame Fenetre;

    public SelectionPeriode(ListeTache liste)
    {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //crée la nouvelle liste de tache a envoyer a la fenetre principale
        this.Liste = new ListeTache();
        this.Liste = liste;

        //paramètre la fenetre
        setTitle(" Selection periode ");
        this.setPreferredSize(new Dimension(450,220));

        //crée les différent panel
        JPanel Panel[] = new JPanel[2];
        for(int i=0; i<2; i++)
            Panel[i] = new JPanel();

        //crée tous les labels
        JLabel Label1 = new JLabel("Date création  JJ :");
        JLabel Label2 = new JLabel("Date échéance  JJ :");
        JLabel Label3 = new JLabel(" MM :");
        JLabel Label4 = new JLabel(" NNNN :");
        JLabel Label5 = new JLabel(" MM :");
        JLabel Label6 = new JLabel(" NNNN :");


        Jour = new JTextField("");
        Jour.setPreferredSize( new Dimension( 50, 24));
        Mois = new JTextField("");
        Mois.setPreferredSize( new Dimension( 50, 24));
        Années = new JTextField("");
        Années.setPreferredSize( new Dimension( 50, 24));
        Jour2 = new JTextField("");
        Jour2.setPreferredSize( new Dimension( 50, 24));
        Mois2 = new JTextField("");
        Mois2.setPreferredSize( new Dimension( 50, 24));
        Années2 = new JTextField("");
        Années2.setPreferredSize( new Dimension( 50, 24));
        Bouton = new JButton("  OK  ");

        //ajoute les différents objets dans les panels
        Panel[0].add(Label1);
        Panel[0].add(Jour);
        Panel[0].add(Label3);
        Panel[0].add(Mois);
        Panel[0].add(Label4);
        Panel[0].add(Années);

        Panel[1].add(Label2);
        Panel[1].add(Jour2);
        Panel[1].add(Label5);
        Panel[1].add(Mois2);
        Panel[1].add(Label6);
        Panel[1].add(Années2);

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
        Jour.addActionListener(new Actionjour());
        Mois.addActionListener(new Actionmois());
        Années.addActionListener(new Actionannées());
        Jour2.addActionListener(new Actionjour());
        Mois2.addActionListener(new Actionmois());
        Années2.addActionListener(new Actionannées());
        Bouton.addActionListener(new Actionbouton());

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

    class Actionjour2 implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            Jour = (JTextField)e.getSource();
        }
    }

    class Actionmois2 implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            Mois = (JTextField)e.getSource();
        }
    }

    class Actionannées2 implements ActionListener
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

            int J2 = Integer.parseInt(Jour2.getText());
            int M2 = Integer.parseInt(Mois2.getText());
            int A2 = Integer.parseInt(Années2.getText());

            //Enregistre les valeur pour les comparer avec les autres taches
            GregorianCalendar Debut = new GregorianCalendar(A, M-1, J);
            GregorianCalendar Fin = new GregorianCalendar(A2, (M2)-1, J2);

            //si la date de fin est apres le debut
            if(Fin.compareTo(Debut) >= 0)
            {
                if(((J < 1) || (J > 31)) || (((J2) < 1) || ((J2) > 31)))
                {
                    JOptionPane Jop = new JOptionPane();
                    Jop.showMessageDialog(null, "Erreur, veuillez choisir un jour valide !!", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
                if(((M < 1) || (M > 12)) || (((M2) < 1) || ((M2) > 12)))
                {
                    JOptionPane Jop = new JOptionPane();
                    Jop.showMessageDialog(null, "Erreur, veuillez choisir un mois valide !!", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    JFrame Fenetre = new FenetreBilan(Liste, Debut, Fin);
                    Fenetre.setVisible(true);
                    dispose();
                }
            }

            //alors la date de fin est avnt le début
            else
            {
                JOptionPane Jop = new JOptionPane();
                Jop.showMessageDialog(null, "Erreur, veuillez choisir une date de création antérieur a la date d'échéance !!", "Erreur", JOptionPane.ERROR_MESSAGE);
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
