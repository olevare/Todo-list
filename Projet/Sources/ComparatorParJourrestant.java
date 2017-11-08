import java.util.Comparator;


public class ComparatorParJourrestant implements Comparator<Tache>
{

    public int compare(Tache o1, Tache o2)
    {
        return (o1.getJour() - o2.getJour());
    }
}
