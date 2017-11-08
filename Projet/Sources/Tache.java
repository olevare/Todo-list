import java.io.Serializable;
import java.util.GregorianCalendar;



public class Tache implements Serializable, Comparable<Tache>
{
    private GregorianCalendar Echeance;
    private GregorianCalendar Creation;
    private String Catégorie;
    private String Titre;
    private Boolean Fini;
    private Boolean Retard;
    private int Jourrestant;

    public Tache(GregorianCalendar echeance, GregorianCalendar creation, String catégorie,
                 String titre, Boolean fini, Boolean retard, int jour)
    {
        super();
        this.Echeance = echeance;
        this.Creation = creation;
        this.Catégorie = catégorie;
        this.Titre = titre;
        this.Fini = fini;
        this.Retard = retard;
        this.Jourrestant = jour;
    }

    public GregorianCalendar getEcheance()
    {
        return Echeance;
    }

    public void setEcheance(GregorianCalendar echeance)
    {
        this.Echeance = echeance;
    }

    public GregorianCalendar getCreation()
    {
        return Creation;
    }

    public void setCreation(GregorianCalendar creation)
    {
        this.Creation = creation;
    }

    public String getTitre()
    {
        return Titre;
    }

    public void setTitre(String titre)
    {
        this.Titre = titre;
    }

    public Boolean getFini()
    {
        return Fini;
    }

    public void setFini(Boolean fini)
    {
        this.Fini = fini;
    }

    public Boolean getRetard()
    {
        return Retard;
    }

    public void setRetard(Boolean retard)
    {
        this.Retard = retard;
    }

    public String getCatégorie()
    {
        return Catégorie;
    }

    public void setCatégorie(String catégorie)
    {
        this.Catégorie = catégorie;
    }

    public int getJour()
    {
        return Jourrestant;
    }

    public void setJour(int jour)
    {
        Jourrestant = jour;
    }

    public int compareTo(Tache o)
    {
        return 0;
    }



}
