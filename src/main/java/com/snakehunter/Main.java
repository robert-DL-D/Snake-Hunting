package com.snakehunter;

import com.snakehunter.controller.LoginController;
import com.snakehunter.view.LoginView;


public class Main {

    public static void main(String[] args) {
        new LoginController(new LoginView());
    }

    public static boolean isDebugMode() {
        return false;
    }
}

/* FIXME:
 *  Add message when snake eats human
 *  Sometime not all the ladders or snakes get save
 *  Remove ladders at the start of final stage
 */

/* TODO:
 *  Add remove function at setup
 *  Add function to click on board
 *  Add animation for piece moving
 *  Save all turn messages
 *  Save last dice roll for human
 */

/* TODO: Add a setting menu
 *   options:
 *   number of snakes and ladders
 *   game mode: human is 1 to 4 players or 1 players control all the pieces
 *   number of snake guards
 *   movable ladders
 */
