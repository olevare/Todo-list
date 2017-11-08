import java.io.Serializable;
import java.util.Vector;


public class ListeTache implements Serializable
{

    private Vector<Tache> Taches;
    private Vector<String> Catégories;
    private Vector<Tache> Fini;

    public ListeTache()
    {
        Taches = new Vector<Tache>();
        Fini = new Vector<Tache>();
        Catégories = new Vector<String>();
        Catégories.add("Travail");
        Catégories.add("Personelle");
    }

    public ListeTache(Vector<Tache> taches, Vector<String> catégories)
    {
        Taches = taches;
        Catégories = catégories;
    }

    public Tache getTaches(int i)
    {
        return Taches.get(i);
    }

    public void setTaches(Tache tache)
    {
        Taches.add(tache);
    }

    public int getTaille()
    {
        return Taches.size();
    }

    public Vector<Tache> getTaches()
    {
        return Taches;
    }

    public Vector<String> getCatégories()
    {
        return Catégories;
    }

    public void setCatégories(Vector<String> catégories)
    {
        Catégories = catégories;
    }

    public void setCatégories(String i)
    {
        Catégories.add(i);
    }

    //supprime une catégorie
    public void supCatégories(String s)
    {
        for(int i=0; i<Taches.size(); i++)
        {
            if(Taches.get(i).getCatégorie() == s)
                Taches.get(i).setCatégorie("");
        }
    }

    public Tache getTachesFini(int i)
    {
        return Fini.get(i);
    }

    public void setFini(Tache tache)
    {
        Fini.add(tache);
    }

    public int getTailleFini()
    {
        return Fini.size();
    }

}
