import javax.swing.*;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class Piste {
    //Le score pour passer le premier niveau
    public static final int INITNIVEAU=4000;
    //la vitesse initale
    public static final float INITVITESSE=20;
    //nombre des points de contrôles
    public static final int NB_NIVEAU=15;
    // les points qui sont utilisés pour construire la piste
    private ArrayList<Point> listPiste = new ArrayList<Point>();
    //Distance totale parcourue par la voiture
    private int position;
    //la vitesse de deplacement de la voiture (piste)
    private float vitesse;
    // les points qui sont utilisé pour les obstacles
    private ArrayList<Point> listObstacle = new ArrayList<Point>();
    private  ArrayList<Integer> niveau ;
    // la liste des points pour afficher
    // les points de contrôles (représentés par une ligne horizontale sur la piste)
    private  ArrayList<Integer> print_niveau ;

    //constructeur de la Piste
    public Piste(){
        this.vitesse=INITVITESSE;
        this.position=0;
        Random rand = new Random();
        Point p1=new Point();
        p1.x=450;
        p1.y=600;
        this.listPiste.add(p1);
        //Générer aléatoirement un nombre entre chaqu'un de 500-400,400-300,300-200,200-100
        for(int i=2;i<=5;i++){
            Point p = new Point();
            int r = rand.nextInt(100)+(600-(100*(i)));
            p.x=rand.nextInt(151)+375;
            p.y = r;
            listPiste.add(p);
        }

        Point p2 = new Point();
        p2.x=rand.nextInt(300)+150;
        p2.y=rand.nextInt(150);
        listObstacle.add(p2);

        niveau=new ArrayList<Integer>();
        print_niveau=new ArrayList<Integer>();
        int postion=0;
        for (int i=0;i<NB_NIVEAU;i++){
            postion=postion+INITNIVEAU+1000*i;
            niveau.add(postion);
        }
        for(int i=0;i<niveau.size();i++ ){
            int y=Affichage.CAR_Y-niveau.get(i);
            print_niveau.add(y);
        }


    }

    /*getter of the vitesse*/
    public float getVitesse() {
        return this.vitesse;
    }
    /*getter of the position*/
    public int getPosition() {
        return this.position;
    }
    /*getter of the list des points*/
    public ArrayList<Point> getListPiste(){
        return this.listPiste;
    }
    /*getter of the list des obstacles*/
    public ArrayList<Point> getListObstacle(){
        return this.listObstacle;
    }
    //getter of the la liste des niveau
    public ArrayList<Integer> getNiveau(){
        return this.niveau;
    }
    //getter of the la liste print_niveau
    public ArrayList<Integer> getPrint_niveau(){
        return this.print_niveau;
    }
    /*setter of the position*/
    public void setPosition() {
        this.position = this.position+(int)this.vitesse;
    }
    /*setter of the vitesse */
    public void setVitesse(float v){ this.vitesse=v; }


    /* mis a jour la liste des points et la list print_niveau
    en ajoutant la valeur de vitesse à leur valeur d’ordonne.  */
    public void upadatePistePoints(){

        for(int i=0;i<listPiste.size();i++){
            Point p=listPiste.get(i);
            int y=p.y+(int)this.vitesse;
            p.y=y;
        }
        for(int i=0;i<print_niveau.size();i++)
        {
            int y2=print_niveau.get(i);
            print_niveau.set(i,y2+(int)this.vitesse);
            if(print_niveau.get(i)>Affichage.LARGPNL)
                print_niveau.remove(i);
        }
        this.setPosition();
        this.ajouter();
    }

    //Lorsque le dernier point rentre dans la fenêtre visible,
    // générer un point supplémentaire pour que la piste ne s’interrompe pas.
    public void ajouter() {

        Random rand=new Random();
        Point p=listPiste.get(listPiste.size()-2);
        if(p.y>=200){
            Point p1 = new Point();
            int x=rand.nextInt(151)+375;
            p1.x=x;
            p1.y=rand.nextInt(10);
            listPiste.add(p1);
        }

       remove();
    }
    //Lorsque le deuxième point sur de la zone visible,
    // retirer le premier point de la list

    public void remove() {
        Point p= listPiste.get(2);
        if(p.y>=Affichage.HAUTPNL){
           listPiste.remove(0);
        }
    }

    //mis a jout les obstacles
    public void updateObtacle(){
            Point p = listObstacle.get(0);
            int y = p.y + (int) this.vitesse;
            p.y = y;

        this.ajoutObtacle();

    }

    public void ajoutObtacle(){

        Random rand=new Random();
        Point p=listPiste.get(2);

        if(p.y>=500){
            Point p2 = new Point();
            p2.x=rand.nextInt(300)+150;
            p2.y=rand.nextInt(150);
            listObstacle.add(p2);
        }
        if(listObstacle.size()>1){

        this.removeObtacle();}
    }
    public void removeObtacle(){

        Point po= listObstacle.get(0);
        if (po.y >= Affichage.HAUTPNL) {
            listObstacle.remove(0);
        }
    }


}
