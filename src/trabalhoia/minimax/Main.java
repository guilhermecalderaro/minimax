/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhoia.minimax;

import NineMensMorris.Game;
import java.awt.EventQueue;

/**
 *
 * @author heck_
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        boolean b = true;
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                Game moinho = new Game();
                moinho.setVisible(true);
                moinho.addAgent(new NewAgent());
            }
        });
    }
    
}