import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.util.ArrayList;

import static java.lang.Math.abs;

public class Etat {

    //le temps à augmenter lorsque le joueur atteint un nouveau niveau
    public static final int TEMPS_AJOUTER=20;
    //la vitesse à diminuer lorsqu'il a une collision
    public static final float COLLISION=4;
    //la liste des niveaux a atteindre
    private int temps_res;//le temps restant
    private String txtTemps;//le text affichié dans lbltemps2
    //distance de chaque deplacement
    public static final int MOVE = 30;
    //Accélération maximale
    public static final float MAX_A = 2;
    // la vitesse maximale
    public static final float MAX_VITESSE = 50;
    //L'abscisse de la voiture
    private int distanceX;
    private Piste piste;
    private int pos;
    //l'Accélération
    private float a;



    /*constructor*/
    public Etat(Piste piste){
        this.a=MAX_A;
        this.piste=piste;
        this.distanceX=Affichage.CAR_X;
        this.pos=0;
        temps_res=30;
        txtTemps="00:"+temps_res;


    }



    /**Etat**/
    // getter of the Piste
    public Piste getPiste(){
        return this.piste;
    }
    //retourner L'abscisse de la voiture
    public int getDistance() {
        return distanceX;
    }

    //retourner L'abscisse des dédors
    public int getPos(){return pos;}

    //getter of the temps_res
    public int getTemps(){
        return this.temps_res;
    }
    //getter of the txtTemps
    public String getTxtTemps(){
        return this.txtTemps;
    }


    //mis a jour le temps_res et le txtTemps
    public void setTemps(){
        temps_res=temps_res+TEMPS_AJOUTER;
        int mm =temps_res / 60 % 60;
        int ss = temps_res % 60;
        txtTemps=mm+":"+ss;
    }

    public void decrement_temps(){
        this.temps_res--;
    }
    //setter of the txtatemps
    public void setTxtTemps (){
        int mm =temps_res / 60 % 60;
        int ss = temps_res % 60;
        if(mm<10&&ss>=10){
            String m="0"+mm;
           txtTemps=m+":"+ss;
        }
        else if(mm>=10&&ss<10){
            String s="0"+ss;
            txtTemps=mm+":"+s;
        }
        else if(mm<10&&ss<10){
            String m="0"+mm;
            String s="0"+ss;
            txtTemps=m+":"+s;
        }
        else {
            txtTemps=mm+":"+ss;

        }
    }

    //Déplacez la voiture vers la gauche
    public void left(){

            distanceX = distanceX - MOVE;
            pos = pos + MOVE;

    }
    //Déplacez la voiture vers la droite
    public void right()
    {
            distanceX = distanceX + MOVE;
            pos = pos - MOVE;
    }
    //Verifier la voiture heurte un obstacle
    public boolean Detection_collision(){
        int i=0;
        while(i<this.piste.getListObstacle().size()){
            Point p=this.piste.getListObstacle().get(i);
            if(p.y<Affichage.CAR_Y+Affichage.CAR_H&&p.y>Affichage.CAR_Y-Affichage.HAUTGOBS){
                if(p.x>distanceX-Affichage.LARGOBS&&p.x<distanceX+Affichage.CAR_L)
                {
                     return true;
                }
                else
                    return false;
            }
            i++;
        }
        return false;

    }



    //calcul and reset the value of the vitesse
    public void calcul_vitesse(){
        Point p1=this.piste.getListPiste().get(0);
        Point p2=this.piste.getListPiste().get(1);
        Point p3=this.piste.getListPiste().get(2);
        float p1x=((float)p1.x+(float)p2.x)/2;
        float p1y=((float)p1.y+(float)p2.y)/2;
        float p2x=((float)p2.x+(float)p3.x)/2;
        float p2y=((float)p2.y+(float)p3.y)/2;
        float distance=0;
       if(p1x==p2x){
           distance= abs((float)distanceX+(float)Affichage.CAR_L- p1x);
       }
       else {
           //calculer la pente de la ligne en utilisant la formule (y2-y1)/(x2-x1)=pente
           float pente = (p2y - p1y) / (p2x - p1x);

           //obtenir la valeur de b par la formule y=kx+b
           float b = p1y - pente * p1x;
           float x = ((float) Affichage.HAUTPNL - b) / pente;

           //Obtenez la distance absolue entre la voiture et la piste
           distance = abs((float) distanceX + (float) Affichage.CAR_L - x);
       }

        //Si la voiture est complètement hors piste, l'accélération de la voiture devient  -2
        if(distance>Affichage.CAR_L){
            a=-2;
        }
        //sinon L'accélération de la voiture augmente à mesure que sa distance
        // par rapport au centre de la piste diminue
        else {
            a=MAX_A-(distance/((float) Affichage.CAR_L/ MAX_A));

        }
        //Réduisez l'erreur entre la ligne brisee  et la courbe
        QuadCurve2D courbe = new QuadCurve2D.Double();
        QuadCurve2D courbe1 = new QuadCurve2D.Double();
        Point2D debut = new Point2D.Double(p1x,p1y);
        Point2D ctrl = new Point2D.Double(p2.x,p2.y);
        Point2D fin = new Point2D.Double(p2x,p2y);
        Point2D debut1 = new Point2D.Double(p1x-Affichage.CAR_L,p1y);
        Point2D ctrl1 = new Point2D.Double(p2.x-Affichage.CAR_L,p2.y);
        Point2D fin1 = new Point2D.Double(p2x-Affichage.CAR_L,p2y);
        courbe.setCurve(debut,ctrl,fin);
        courbe1.setCurve(debut1,ctrl1,fin1);
        if(p1x>p2x){
            if(courbe.contains(distanceX ,Affichage.CAR_Y)){
                a=0;
            }
            if(courbe1.contains( distanceX +Affichage.CAR_L,Affichage.CAR_Y)) {
                a = 1;
            }
        }
        else{
            if(courbe.contains( distanceX ,Affichage.CAR_Y)){
                a=1;
            }
            if(courbe1.contains( distanceX + Affichage.CAR_L,Affichage.CAR_Y)) {
                a = 0;
            }

        }
        System.out.printf("a :%f\n",a);

        //Mis a jour la vitesse de la voituer et la vitesse  doit rester entre [0,MAX_VITESSE]
        if(Detection_collision()){
            if(piste.getVitesse()-COLLISION<0){
                System.out.printf("collision:%f\n",a);
                this.piste.setVitesse(0);
            }
            else
                this.piste.setVitesse(piste.getVitesse()-COLLISION);
        }
        if(piste.getVitesse()+a<=0){
            System.out.printf("%f\n",a);
            this.piste.setVitesse(0);
        }
        else if(a+piste.getVitesse()>=MAX_VITESSE){
            this.piste.setVitesse(MAX_VITESSE);
        }
        else {
            this.piste.setVitesse(a+piste.getVitesse());
        }
    }



}


