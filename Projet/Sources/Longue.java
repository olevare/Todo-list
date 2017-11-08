import java.io.Serializable;
import java.util.GregorianCalendar;


public class Longue extends Tache implements Serializable
{
    private int Pourcentage;

    public Longue(GregorianCalendar echeance, GregorianCalendar creation,
                  String catégorie, String titre, Boolean fini, Boolean retard, int jour, int pourcentage)
    {
        super(echeance, creation, catégorie, titre, fini, retard, jour);
        this.Pourcentage = pourcentage;
    }

    public int getPourcentage()
    {
        return Pourcentage;
    }

    public void setPourcentage(int pourcentage)
    {
        Pourcentage = pourcentage;
    }



}
