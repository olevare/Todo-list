import javax.swing.JFrame;


public class Main
{

    private static ListeTache Liste;

    public static void main(String args[])
    {
        Liste = new ListeTache();
        JFrame Fenetre = new FenetrePrincipale(Liste);
        Fenetre.setVisible(true);
    }
}
