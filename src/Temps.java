import javax.swing.*;

public class Temps extends Thread{
    public  static volatile boolean flag_temps = true;
    private  Etat etat;
    private Affichage affichage;

    /** Constructeur */
    public Temps(Etat etat, Affichage affichage) {
        this.etat=etat;
        this.affichage=affichage;
    }


    public void run() {

        while(flag_temps)
        {
            try{
                if(etat.getTemps()<=0){
                    Avancer.flag_avance=false;
                    flag_temps=false;
                    Control.flag_control=false;
                    String message="Votre temps restant est 0:00. Vous avez perdu. Votre score  est: " + etat.getPiste().getPosition();
                    JOptionPane.showMessageDialog(this.affichage, message);
                    break;

                }
                Thread.sleep(1000);
                this.etat.decrement_temps();
                etat.setTxtTemps();
                this.affichage.revalidate();
                this.affichage.repaint();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


}
