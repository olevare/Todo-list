import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;




public class FenetreBilan extends JFrame
{

    private JTable Tableau;
    private ListeTache Liste;
    private JFrame Fenetre;
    private int Nbnonrealiser;
    private int Nbrealiser;
    private int Nbretard;

    public FenetreBilan(ListeTache liste, GregorianCalendar debut, GregorianCalendar fin)
    {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //crée la nouvelle liste de tache a envoyer a la fenetre principale
        this.Liste = new ListeTache();
        this.Liste = liste;

        JMenuItem Ajout = new JMenuItem("Ajouter");
        JMenuItem Retir = new JMenuItem("Retirer");
        JMenuItem Renommer = new JMenuItem("Renommer");
        JMenuItem Sauver = new JMenuItem("Sauvegarder");
        JMenuItem Charger = new JMenuItem("Charger");
        JMenuItem Fini = new JMenuItem("Fini");
        JMenuItem Encours = new JMenuItem("En cours");
        JMenuItem Bilan = new JMenuItem("Bilan");

        JMenu Catégorie = new JMenu("Catégories");
        JMenu Sauvegarder = new JMenu("Sauvegarder");
        JMenu Taches = new JMenu("Taches");

        Catégorie.add(Ajout);
        Catégorie.add(Retir);
        Catégorie.add(Renommer);

        Sauvegarder.add(Sauver);
        Sauvegarder.add(Charger);

        Taches.add(Fini);
        Taches.add(Encours);
        Taches.add(Bilan);

        JMenuBar Bar = new JMenuBar();
        Bar.add(Catégorie);
        Bar.add(Sauvegarder);
        Bar.add(Taches);

        this.setLocationRelativeTo(null);
        this.setTitle("Bilan");
        this.setSize(1000, 500);

        Tableau = new JTable();

        DefaultTableModel Model = new DefaultTableModel();
        Model.addColumn("Numéro");
        Model.addColumn("Titre");
        Model.addColumn("Catégorie");
        Model.addColumn("échéance");
        Model.addColumn("création");
        Model.addColumn("Retard");
        Model.addColumn("Pourcentage");
        Model.addColumn("Jour restant");

        //pour toutes les taches qui se trouve dans la liste
        for(int i = 0; i < Liste.getTaille(); i++)
        {
            //si la date de début et egale ou apres celle rentrer et la date de fin avant ou egale a celle rentrer
            if((Liste.getTaches(i).getCreation().compareTo(debut) >= 0) && (Liste.getTaches(i).getEcheance().compareTo(fin) <= 0))
            {
                String Retard;
                String Pourcentage;
                String Jourrestant = Integer.toString(Liste.getTaches(i).getJour());

                if(Liste.getTaches(i).getRetard())
                    Retard = "Oui";
                else
                    Retard = "Non";

                //on tansforme la date de création en string
                GregorianCalendar DateGreg = Liste.getTaches(i).getCreation();
                DateFormat DateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date DateDate = DateGreg.getTime();
                String Création = DateFormat.format(DateDate);

                //on tansforme la date d'echeance en string
                GregorianCalendar DateGreg2 = Liste.getTaches(i).getEcheance();
                DateDate = DateGreg2.getTime();
                String Echeance = DateFormat.format(DateDate);

                //si la tache est une tache longue
                if(Liste.getTaches(i) instanceof Longue)
                {
                    //on récupère son pourcentage que l'on transforme en string
                    Longue Longue = (Longue)Liste.getTaches(i);
                    Pourcentage = Integer.toString(Longue.getPourcentage());
                }
                else
                    Pourcentage = "";

                //on rajoute au tableau ta tache
                Model.addRow(new Object[] {Integer.toString(i+1), Liste.getTaches(i).getTitre(), Liste.getTaches(i).getCatégorie(), Echeance, Création, Retard, Pourcentage, Jourrestant});
                Nbnonrealiser++;
            }
        }

        //pour toutes les taches qui se trouve dans la liste
        for(int i = 0; i < Liste.getTailleFini(); i++)
        {
            //si la date de début et egale ou apres celle rentrer et la date de fin avant ou egale a celle rentrer
            if((Liste.getTachesFini(i).getCreation().compareTo(debut) >= 0) && (Liste.getTachesFini(i).getEcheance().compareTo(fin) <= 0))
            {
                if(Liste.getTachesFini(i).getRetard())
                    Nbretard++;
                else
                    Nbrealiser++;
            }
        }

        //on donne à Tableau le tableau crée dans Model
        Tableau.setModel(Model);

        // ajoute une barre de défilement à la table
        JScrollPane scroll = new JScrollPane(Tableau);
        add(scroll);

        JLabel Label1 = new JLabel("Il y a "+((Nbrealiser * 100)/(Nbrealiser + Nbretard + Nbnonrealiser))+"% de tache(s) réaliser a temps \n");
        JLabel Label2 = new JLabel("Il y a "+((Nbretard * 100)/(Nbrealiser + Nbretard + Nbnonrealiser))+"% de tache(s) réaliser en retard \n");
        JLabel Label3 = new JLabel("Il y a "+((Nbnonrealiser * 100)/(Nbrealiser + Nbretard + Nbnonrealiser))+"% de tache(s) en cours \n");

        JPanel Pan = new JPanel();

        Pan.setLayout(new GridLayout(3, 1));
        Pan.add(Label1);
        Pan.add(Label2);
        Pan.add(Label3);

        add(Bar, "North");

        this.getContentPane().add(Pan, BorderLayout.SOUTH);

        Sauver.addActionListener(new Actionsauver());
        Charger.addActionListener(new Actioncharger());
        Ajout.addActionListener(new Actionajout());
        Retir.addActionListener(new Actionretir());
        Renommer.addActionListener(new Actionrenommer());
        Fini.addActionListener(new Actionfini());
        Encours.addActionListener(new Actionencours());
        Bilan.addActionListener(new Actionbilan());
    }

    class Actionajouter implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            Fenetre = new AjoutTache(Liste);
            Fenetre.setVisible(true);
            dispose();
        }
    }

    class Actionsauver implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            //on sérialise
            FileOutputStream Fichier = null;
            try
            {
                Fichier = new FileOutputStream("test.ser");
            }
            catch (FileNotFoundException e1)
            {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            ObjectOutputStream oos = null;
            try
            {
                oos = new ObjectOutputStream(Fichier);
            }
            catch (IOException e1)
            {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            try
            {
                oos.writeObject(Liste);
            }
            catch (IOException e1)
            {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            try
            {
                oos.close();
            }
            catch (IOException e1)
            {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            try
            {
                Fichier.close();
            }
            catch (IOException e1)
            {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }

    class Actioncharger implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            //on désérialise
            FileInputStream Fichier = null;
            try
            {
                Fichier = new FileInputStream("test.ser");
            }
            catch (FileNotFoundException e1)
            {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            ObjectInputStream in = null;
            try
            {
                in = new ObjectInputStream(Fichier);
            }
            catch (IOException e1)
            {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            try
            {
                Liste = (ListeTache)in.readObject();
            }
            catch (ClassNotFoundException | IOException e1)
            {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            try
            {
                in.close();
            }
            catch (IOException e1)
            {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            try
            {
                Fichier.close();
            }
            catch (IOException e1)
            {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            Fenetre = new FenetrePrincipale(Liste);
            Fenetre.setVisible(true);
            dispose();
        }
    }

    class Actionajout implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            Fenetre = new AjouterCatégorie(Liste);
            Fenetre.setVisible(true);
            dispose();
        }
    }

    class Actionretir implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            Fenetre = new RetirerCatégorie(Liste);
            Fenetre.setVisible(true);
            dispose();
        }
    }

    class Actionrenommer implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            Fenetre = new RenommerCatégorie(Liste);
            Fenetre.setVisible(true);
            dispose();
        }
    }

    class Actionfini implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            Fenetre = new FenetreTacheFini(Liste);
            Fenetre.setVisible(true);
            dispose();
        }
    }

    class Actionencours implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            Fenetre = new FenetrePrincipale(Liste);
            Fenetre.setVisible(true);
            dispose();
        }
    }

    class Actionbilan implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            Fenetre = new SelectionPeriode(Liste);
            Fenetre.setVisible(true);
            dispose();
        }
    }
}
