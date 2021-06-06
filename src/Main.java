import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/** La classe principale */
public class Main {
    public static void main(String [] args) throws IOException {
        //L'écran d'accueil
        JFrame acceuil = new JFrame ("Accueil");
        //Le bouton pour commencer le jeu
        JButton button=new JButton("Start");
        acceuil.setLayout(null);
        button.setBounds(300,350,200,50);
        acceuil.add(button);
        //L'écran du jeu
        JLabel titre=new JLabel("Course de voiture");
        titre.setBounds(160 ,200,500,100);
        Font font=new Font("Algerian",Font.BOLD,48);
        titre.setFont(font);
        acceuil.add(titre);
        acceuil.setPreferredSize(new Dimension(Affichage.LARGPNL, Affichage.HAUTPNL));
        acceuil.getContentPane();
        acceuil.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        acceuil.pack();
        acceuil.setVisible(true);


        Piste piste = new Piste();
        Etat etat = new Etat(piste);
        Affichage affichage= new Affichage(etat);
        Control control=new Control(affichage,etat);
        JFrame fenetre = new JFrame ("Course de voiture");


        fenetre.getContentPane();
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //ajouter les composants dans l'interface du jeu
        fenetre.add(affichage);
        fenetre.addKeyListener(control);
        fenetre.pack();

        //ajouter un evenement à bouton pour masquer l'accueil et afficher l'interface du jeu
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                acceuil.dispose();
                fenetre.setVisible(true);
                //commencer les threads
                (new Avancer(etat,affichage)).start();
                (new Temps(etat,affichage)).start();
            }
        });
    }
}