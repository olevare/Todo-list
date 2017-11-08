import java.awt.BorderLayout;
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
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class FenetrePrincipale extends JFrame
{

    private JTable Tableau;
    private JButton Ajouter = new JButton("Ajouter");
    private JButton Retirer = new JButton("Retirer");
    private JButton Modifier = new JButton("Modifier");
    private ListeTache Liste;
    private JFrame Fenetre;

    public FenetrePrincipale(ListeTache liste)
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
        this.setTitle("Gestionnaire de taches");
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

        //si il n'y a pas de tache dans la liste on desactive des boutons
        if(Liste.getTaille() == 0)
        {
            Retirer.setVisible(false);
            Modifier.setVisible(false);
        }

        //pour toutes les taches qui se trouve dans la liste on met a jour le temps restant et les retards
        for(int i = 0; i < Liste.getTaille(); i++)
        {
            //si la tache est en retard est que c'est une tache longue
            if(Liste.getTaches(i) instanceof Longue)
            {
                Longue Longue = (Longue)Liste.getTaches(i);

                //calcule le nombre de jour restant et l'enregistre
                Date date1 = Longue.getEcheance().getTime( );
                Date today = new Date( );
                long diff = date1.getTime( ) - today.getTime( );
                diff = diff / (1000*60*60*24);
                Liste.getTaches(i).setJour((int)diff);

                //calcule le nombre de jour qui sépare la date de création a la date d'echeance
                date1 = Longue.getEcheance().getTime( );
                today = Longue.getCreation().getTime();
                diff = date1.getTime( ) - today.getTime( );
                diff = diff / (1000*60*60*24);

                //vérifie si la tache est en retard
                int JourEcouler = (int)diff - Liste.getTaches(i).getJour();
                int Quart = (int)diff / 4;
                int Pourcent = Longue.getPourcentage();

                //si la tache est crée aujourd'hui
                if(JourEcouler == 0)
                {
                    Liste.getTaches(i).setRetard(false);
                }
                else
                {
                    //si la date d'echeance n'est pas dépasser
                    if(JourEcouler > 0)
                    {
                        //si le nombre de jour écouler est plus grand que le quart du nombre de jour pour accomplir la tache et que le pourcentage de la tache est >= que 25%
                        if((JourEcouler > Quart) && (Pourcent >= 25))
                        {
                            if((JourEcouler > Quart*2) && (Pourcent >= 50))
                            {
                                if((JourEcouler > Quart*3) && (Pourcent >= 75))
                                {
                                    if(JourEcouler > Quart*4)
                                    {
                                        Liste.getTaches(i).setRetard(true);
                                    }
                                    Liste.getTaches(i).setRetard(false);
                                }
                                else
                                {
                                    if(Pourcent >= 75)
                                        Liste.getTaches(i).setRetard(false);
                                    else
                                        Liste.getTaches(i).setRetard(true);
                                }
                            }
                            else
                            {
                                if(Pourcent >= 50)
                                    Liste.getTaches(i).setRetard(false);
                                else
                                    Liste.getTaches(i).setRetard(true);
                            }
                        }
                        else
                        {
                            if(Pourcent >= 25)
                                Liste.getTaches(i).setRetard(false);
                            else
                                Liste.getTaches(i).setRetard(true);
                        }
                    }
                    else
                        Liste.getTaches(i).setRetard(true);
                }
            }
            //sinon regarde si la tache ponctuelle est en retard
            else
            {
                //calcule le nombre de jour restant
                Date date1 = Liste.getTaches(i).getEcheance().getTime( );
                Date today = new Date( );
                long diff = date1.getTime( ) - today.getTime( );
                diff = diff / (1000*60*60*24);
                Liste.getTaches(i).setJour((int)diff);

                if((int)diff >= 0)
                    Liste.getTaches(i).setRetard(false);
                else
                    Liste.getTaches(i).setRetard(true);
            }
        }

        Collections.sort(Liste.getTaches(), new ComparatorParJourrestant());

        //pour toutes les taches (pour les afficher cette fois)
        for(int i = 0; i < Liste.getTaille(); i++)
        {
            String Retard;
            String Pourcentage;
            String Jourrestant;

            //on tansforme la date de création en string
            GregorianCalendar DateGreg = Liste.getTaches(i).getCreation();
            DateFormat DateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date DateDate = DateGreg.getTime();
            String Création = DateFormat.format(DateDate);

            //on tansforme la date d'echeance en string
            GregorianCalendar DateGreg2 = Liste.getTaches(i).getEcheance();
            DateDate = DateGreg2.getTime();
            String Echeance = DateFormat.format(DateDate);

            if(Liste.getTaches(i).getRetard())
                Retard = "Oui";
            else
                Retard = "Non";

            //si la tache est une tache longue
            if(Liste.getTaches(i) instanceof Longue)
            {
                //on récupère son pourcentage que l'on transforme en string
                Longue Longue = (Longue)Liste.getTaches(i);
                Pourcentage = Integer.toString(Longue.getPourcentage());
            }
            else
                Pourcentage = "";

            Jourrestant = Integer.toString(Liste.getTaches(i).getJour());

            //on rajoute au tableau ta tache
            Model.addRow(new Object[] {Integer.toString(i+1), Liste.getTaches(i).getTitre(), Liste.getTaches(i).getCatégorie(), Echeance, Création, Retard, Pourcentage, Jourrestant});

        }

        //on donne à Tableau le tableau crée dans Model
        Tableau.setModel(Model);

        // ajoute une barre de défilement à la table
        JScrollPane scroll = new JScrollPane(Tableau);
        add(scroll);


        JPanel Pan = new JPanel();

        Pan.add(Ajouter);
        Pan.add(Retirer);
        Pan.add(Modifier);

        add(Bar, "North");

        this.getContentPane().add(Pan, BorderLayout.SOUTH);

        Ajouter.addActionListener(new Actionajouter());
        Retirer.addActionListener(new Actionretirer());
        Modifier.addActionListener(new Actionmodifier());
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

    class Actionretirer implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            //1 = on veut retirer
            Fenetre = new RetirerTache(Liste, 1);
            Fenetre.setVisible(true);
            dispose();
        }
    }

    class Actionmodifier implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            //2 = on veut modifier
            Fenetre = new RetirerTache(Liste, 2);
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


