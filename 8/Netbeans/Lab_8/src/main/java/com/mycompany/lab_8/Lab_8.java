/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.lab_8;

/**
 *
 * @author rje24
 */
public class Lab_8 {

    public static void main(String[] args) {
       // Step 1: Initialize the database
        System.out.println("Initializing the database...");
        DatabaseInitializer.initializeDatabase();

        // Step 2: Launch the GUI
        System.out.println("Launching the application...");
        javax.swing.SwingUtilities.invokeLater(() -> new ConferenceGUI());
    }
}
