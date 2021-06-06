import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Control implements KeyListener{

    public  static volatile boolean flag_control = true;
    public Affichage affichage;
    public Etat etat;

    /**Contructeur*/
    public Control(Affichage affichage,Etat etat){
        this.affichage=affichage;
        this.etat=etat;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(flag_control) {
            //left
            if (e.getKeyCode() == 37) {
                if (etat.getDistance() - Etat.MOVE < 0) {
                    System.out.printf("arreterright\n");
                } else {   //etat.getDistance()>0&&etat.getDistance()<Affichage.LARGPNL-Affichage.CAR_L
                    etat.left();
                    etat.calcul_vitesse();
                    affichage.repaint();
                }

            }
            //up
            if (e.getKeyCode() == 38) {

            }
            //right
            if (e.getKeyCode() == 39) {
                if (this.etat.getDistance() + Affichage.CAR_L + Etat.MOVE > Affichage.LARGPNL) {
                    System.out.printf("arreterright\n");
                } else {

                    etat.right();
                    etat.calcul_vitesse();
                    affichage.repaint();
                }

            }
            //down
            if (e.getKeyCode() == 40) {

            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
