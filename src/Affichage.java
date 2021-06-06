import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Affichage extends JPanel {

    /* Constantes */
    public static final int LARGPNL = 800;// La largeur de la fenêtre
    public static final int HAUTPNL = 600;/** La hauteur de la fenêtre */
    public static final int CAR_L = 100;  /**La largeur de la voiture et La largeur de la piste*/
    public static final int CAR_H = 100;/** La hauteur de la voiture */
    /* Les coordonnes initiales de la voiture */
    public static final int CAR_X = 350;/** L'abscisse de la voiture */
    public static final int CAR_Y = 500; /** L'ordonne de la voiture */

    public static final int LARGOBS = 100;// La largeur de l'Obstabcle
    public static final int HAUTGOBS = 80;// La hauteurde l'Obstabcle


    BufferedImage image;// image de la voiture
    BufferedImage imageOb;// image de l'obstacle
    BufferedImage imagedecor;
    private Etat etat;
    private JLabel lblvitesse;
    private JLabel lbltemps;
    private JLabel lblscore;
    private JLabel lblvitesse2;//label pour afficher la valeur de la varible vitesse
    private JLabel lbltemps2;//label pour afficher la valeur de la varible temps
    private JLabel lblscore2;//label pour afficher la valeur de la varible postion(score)
    private String vitesse;//stocker le text de lblvitesse2
    private String score;//stocker le text de lblscoree2
    private String temps;//stocker le text de lbltemps2
    public Affichage(Etat etat) throws IOException {

        this.etat=etat;
        image = ImageIO.read(new File("src/image/voiture.jpg"));
        imageOb = ImageIO.read(new File("src/image/obstacle.jpg"));
        imagedecor = ImageIO.read(new File("src/image/decor.jpg"));
        lbltemps=new JLabel(" temps restant");
        lblscore=new JLabel("score");
        lblvitesse=new JLabel("vitesse");
        vitesse=this.etat.getPiste().getVitesse()+" km/h ";
        score=this.etat.getPiste().getPosition()+" ";
        lblvitesse2=new JLabel(vitesse);
        lblscore2=new JLabel(score);
        lbltemps2=new JLabel(etat.getTxtTemps());

        //definir la position et la taille des labels
        lbltemps2.setBounds(690,530,120,50);
        lblscore2.setBounds(730,230,100,50);
        lblvitesse2.setBounds(20,530,120,70);
        lbltemps.setBounds(680,520,120,10);
        lblscore.setBounds(730,205,50,30);
        lblvitesse.setBounds(20,520,50,30);

        //chager le font de label
        Font font=new Font("Calibri",Font.BOLD,24);
        lblvitesse2.setFont(font);
        lblscore2.setFont(font);
        lbltemps2.setFont(font);

        //ajouter les label dans le panel
        this.add(lbltemps);
        this.add(lblscore);
        this.add(lblvitesse);
        this.add(lblvitesse2);
        this.add(lblscore2);
        this.add(lbltemps2);

        //modifier des attribut du panel
        this.setLayout(null);
        setPreferredSize(new Dimension(LARGPNL, HAUTPNL));
        this.setBackground(Color.white);
    }
    public JLabel getLbltemps2(){
         return this.lbltemps2;
    }

    public void paint(Graphics g){
        super.paint(g);
        //dessiner l'image de la voiture
        g.drawImage(image,this.etat.getDistance(),CAR_Y,CAR_L,CAR_H,null);
        //dessiner les obstacles
        paintObstable(g);
        g.drawLine(0,200,800,200);
        //decrease la presision de la le vitesse à deux décimales
        paintVitesse(g);
        //dessiner la piste
        paintPiste(g);
        paintControle(g);
        g.clearRect(0,0,600,200);
        //dessiner les décors
        paintDecors(g);
        //changer le text des labels
        paintLabels(g);

    }
    //decrease la presision de la le vitesse à deux décimales
    public void paintVitesse(Graphics g){
        float f=((float)(Math.round(this.etat.getPiste().getVitesse()*10))/10);
        vitesse=f+" km/h";
        score=this.etat.getPiste().getPosition()+"";
    }
    //changer le text des labels
    public void paintLabels(Graphics g){
        lblscore2.setText(score);
        lblvitesse2.setText(vitesse);
        lbltemps2.setText(etat.getTxtTemps());
    }
    //dessiner les obstacles
    public void paintObstable(Graphics g){
        ArrayList<Point> point = etat.getPiste().getListObstacle();
            Point p = point.get(0);
            g.drawImage(imageOb,p.x,p.y,LARGOBS,HAUTGOBS,null);

    }
    //dessiner des points de contrôles matérialisés par une bande horizontale sur la piste
    public void paintControle(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(3.0f));
        g2.setColor(Color.green);
        g2.drawLine(200, this.etat.getPiste().getPrint_niveau().get(0), 600, this.etat.getPiste().getPrint_niveau().get(0));
        g2.setColor(Color.black);
    }

    //dessiner la piste
    public void paintPiste(Graphics g){
        QuadCurve2D courbe = new QuadCurve2D.Double();
        QuadCurve2D courbe1 = new QuadCurve2D.Double();
        ArrayList<Point> point=etat.getPiste().getListPiste();
        for(int i=0;i<point.size()-2;i++) {
            Point a = point.get(i);
            Point b = point.get(i + 1);
            Point c= point.get(i + 2);
            Point2D debut = new Point2D.Double((a.x+b.x)/2,(a.y+b.y)/2);
            Point2D ctrl = new Point2D.Double(b.x,b.y);
            Point2D fin = new Point2D.Double((b.x+c.x)/2,(b.y+c.y)/2);
            Point2D debut1 = new Point2D.Double((a.x+b.x)/2-CAR_L,(a.y+b.y)/2);
            Point2D ctrl1 = new Point2D.Double(b.x-CAR_L,b.y);
            Point2D fin1 = new Point2D.Double((b.x+c.x)/2-CAR_L,(b.y+c.y)/2);
            courbe.setCurve(debut,ctrl,fin);
            courbe1.setCurve(debut1,ctrl1,fin1);
            Graphics2D g2 = (Graphics2D)g;
            g2.draw(courbe);
            g2.draw(courbe1);

        }
    }
    //dessiner les décors
    public void paintDecors(Graphics g){
        g.drawImage(imagedecor,etat.getPos()-350,0,1600,200,null);
    }

}