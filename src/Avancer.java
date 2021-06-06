import javax.swing.*;

public class Avancer extends Thread{
    public  static volatile boolean flag_avance = true;
    private  Etat etat;
    private Affichage affichage;

    /** Constructeur */
    public Avancer(Etat etat, Affichage affichage) {
        this.etat=etat;
        this.affichage=affichage;
    }

    public void run() {

        while(flag_avance)
        {
            try{
                //mis a jour la piste
                this.etat.getPiste().upadatePistePoints();
                this.etat.getPiste().updateObtacle();

                // Lorsque le joueur a passé tous les points de controlês,afficher une fenêtre de félicitations
                if(etat.getPiste().getNiveau().size()==0){
                    Avancer.flag_avance=false;
                    Temps.flag_temps=false;
                    Control.flag_control=false;
                    String message="Félicitations.Vous avez gagné. Votre score  est: " + this.etat.getPiste().getPosition();
                    JOptionPane.showMessageDialog(this.affichage, message);
                    break;

                }

               else {
                        if (etat.getPiste().getPosition() > etat.getPiste().getNiveau().get(0)) {
                            this.etat.setTemps();
                            etat.getPiste().getNiveau().remove(0);
                        }
                        //recalculer la vitesse
                        this.etat.calcul_vitesse();
                        affichage.revalidate();
                        affichage.repaint();
                        if(this.etat.getPiste().getVitesse()==0){
                            Avancer.flag_avance=false;
                            Temps.flag_temps=false;
                            Control.flag_control=false;
                            String message="Votre vitesse est 0. Vous avez perdu. Votre score  est: " + this.etat.getPiste().getPosition();
                            JOptionPane.showMessageDialog(this.affichage, message);
                            break;
                        }
                }

                Thread.sleep(200);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
