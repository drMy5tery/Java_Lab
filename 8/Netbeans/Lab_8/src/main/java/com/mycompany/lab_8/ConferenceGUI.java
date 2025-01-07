/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab_8;

/**
 *
 * @author rje24
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;


class AttendeeForm extends JDialog {
    private JTextField nameField, emailField, contactField, countryField;
    private AttendeeManager attendeeManager;
    private Attendee attendee;

    public AttendeeForm(JFrame parent, String title, Attendee attendee, AttendeeManager attendeeManager) {
        super(parent, title, true);
        this.attendeeManager = attendeeManager;
        this.attendee = attendee;

        setLayout(new GridLayout(5, 2, 10, 10));
        setSize(400, 300);

        add(new JLabel("Full Name:"));
        nameField = new JTextField(attendee != null ? attendee.getFullName() : "");
        add(nameField);

        add(new JLabel("Email:"));
        emailField = new JTextField(attendee != null ? attendee.getEmail() : "");
        add(emailField);

        add(new JLabel("Contact Number:"));
        contactField = new JTextField(attendee != null ? attendee.getContactNumber() : "");
        add(contactField);

        add(new JLabel("Country:"));
        countryField = new JTextField(attendee != null ? attendee.getCountry() : "");
        add(countryField);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> handleSave());
        add(saveButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton);
    }

    private void handleSave() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String contact = contactField.getText().trim();
        String country = countryField.getText().trim();

        // Validation
        if (!validateInput(name, email, contact, country)) {
            return; // Stop processing if validation fails
        }

        if (attendee == null) { // Add operation
            attendeeManager.addAttendee(name, email, contact, country);
        } else { // Edit operation
            attendee.setFullName(name);
            attendee.setEmail(email);
            attendee.setContactNumber(contact);
            attendee.setCountry(country);
            attendeeManager.updateAttendee(attendee);
        }

        dispose();
    }

    private boolean validateInput(String name, String email, String contact, String country) {
        // Name validation
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name cannot be empty!");
            return false;
        }
        if (!name.matches("^[a-zA-Z ]+$")) {
            JOptionPane.showMessageDialog(this, "Name can only contain letters and spaces!");
            return false;
        }

        // Email validation
        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email cannot be empty!");
            return false;
        }
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address!");
            return false;
        }

        // Contact number validation
        if (contact.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Contact number cannot be empty!");
            return false;
        }
        if (!contact.matches("^\\d+$")) {
            JOptionPane.showMessageDialog(this, "Contact number must be numeric!");
            return false;
        }
        if (contact.length() < 8 || contact.length() > 15) {
            JOptionPane.showMessageDialog(this, "Contact number must be between 8 and 15 digits!");
            return false;
        }

        // Country validation
        if (country.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Country cannot be empty!");
            return false;
        }
        if (!country.matches("^[a-zA-Z ]+$")) {
            JOptionPane.showMessageDialog(this, "Country can only contain letters and spaces!");
            return false;
        }

        return true; // Validation passed
    }
}

public class ConferenceGUI {
    private AttendeeManager attendeeManager;
    private JFrame frame;
    private JTable attendeeTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;

    public ConferenceGUI() {
        attendeeManager = new AttendeeManager();
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("Conference Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Top Panel for Search
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel searchLabel = new JLabel("Search by Name or Country:");
        searchField = new JTextField();
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(this::handleSearch);

        topPanel.add(searchLabel, BorderLayout.WEST);
        topPanel.add(searchField, BorderLayout.CENTER);
        topPanel.add(searchButton, BorderLayout.EAST);

        // Center Panel for Table
        String[] columnNames = {"ID", "Full Name", "Email", "Contact", "Country"};
        tableModel = new DefaultTableModel(columnNames, 0);
        attendeeTable = new JTable(tableModel);

        JScrollPane tableScrollPane = new JScrollPane(attendeeTable);

        // Bottom Panel for Buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addButton = new JButton("Add Attendee");
        JButton editButton = new JButton("Edit Attendee");
        JButton deleteButton = new JButton("Delete Attendee");
        JButton refreshButton = new JButton("Refresh");
        JButton statsButton = new JButton("View Statistics");
       

  

        addButton.addActionListener(e -> handleAddAttendee());
        editButton.addActionListener(e -> handleEditAttendee());
        deleteButton.addActionListener(e -> handleDeleteAttendee());
        refreshButton.addActionListener(e -> populateTable());
        statsButton.addActionListener(e -> showStatistics());

        bottomPanel.add(addButton);
        bottomPanel.add(editButton);
        bottomPanel.add(deleteButton);
        bottomPanel.add(refreshButton);
        bottomPanel.add(statsButton);

        // Adding Panels to Frame
        frame.setLayout(new BorderLayout());
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(tableScrollPane, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        populateTable(); // Load initial data into the table
        frame.setVisible(true);
    }
    
//    private void handleAddAttendee() {
//    String fullName = JOptionPane.showInputDialog("Enter Full Name:");
//    String email = JOptionPane.showInputDialog("Enter Email:");
//    String contactNumber = JOptionPane.showInputDialog("Enter Contact Number:");
//    String country = JOptionPane.showInputDialog("Enter Country:");
//
//    if (validateInput(fullName, email, contactNumber, country)) {
//        attendeeManager.addAttendee(fullName, email, contactNumber, country);
//        JOptionPane.showMessageDialog(frame, "Attendee added successfully!");
//        populateTable();
//    }
//}

    // Validation logic
    private boolean validateInput(String fullName, String email, String contactNumber, String country) {
        if (fullName.isEmpty() || email.isEmpty() || contactNumber.isEmpty() || country.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "All fields must be filled out.");
            return false;
        }
        if (!email.matches("^[\\w.%+-]+@[\\w.-]+\\.\\w{2,}$")) {
            JOptionPane.showMessageDialog(frame, "Invalid email format.");
            return false;
        }
        if (!contactNumber.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(frame, "Contact number must be 10 digits.");
            return false;
        }
        return true;
    }
    private void showStatistics() {
    List<String> stats = attendeeManager.generateStatistics();
    
    // Create a dialog to display statistics
    JDialog statsDialog = new JDialog(frame, "Attendee Statistics", true);
    statsDialog.setSize(400, 300);
    statsDialog.setLayout(new BorderLayout());

    // Prepare statistics data
    String[] columnNames = {"Country", "Count"};
    DefaultTableModel statsTableModel = new DefaultTableModel(columnNames, 0);
    JTable statsTable = new JTable(statsTableModel);

    // Populate the table with statistics
    for (String stat : stats) {
        String[] parts = stat.split(": ");
        if (parts.length == 2) {
            statsTableModel.addRow(new Object[]{parts[0], parts[1]});
        }
    }

    // Add components to the dialog
    statsDialog.add(new JScrollPane(statsTable), BorderLayout.CENTER);
    JButton closeButton = new JButton("Close");
    closeButton.addActionListener(e -> statsDialog.dispose());
    statsDialog.add(closeButton, BorderLayout.SOUTH);

    statsDialog.setVisible(true);
}

    private void handleSearch(ActionEvent e) {
        String query = searchField.getText().trim();
        if (!query.isEmpty()) {
            List<Attendee> attendees = attendeeManager.searchAttendees(query);
            updateTable(attendees);
        } else {
            JOptionPane.showMessageDialog(frame, "Please enter a search term.");
        }
    }

    private void handleAddAttendee() {
          AttendeeForm form = new AttendeeForm(frame, "Add Attendee", null, attendeeManager);
          form.setVisible(true);
          populateTable(); // Refresh table after adding// Refresh table after adding
    }

    private void handleEditAttendee() {
        int selectedRow = attendeeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select an attendee to edit.");
            return;
        }
        int id = (int) tableModel.getValueAt(selectedRow, 0);
        Attendee attendee = attendeeManager.getAttendeeById(id);

        AttendeeForm form = new AttendeeForm(frame, "Edit Attendee", attendee, attendeeManager);
        form.setVisible(true);
        populateTable(); // Refresh table after editing
        
    }

    private void handleDeleteAttendee() {
        int selectedRow = attendeeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select an attendee to delete.");
            return;
        }
        int id = (int) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this attendee?");
        if (confirm == JOptionPane.YES_OPTION) {
            attendeeManager.deleteAttendee(id);
            populateTable(); // Refresh table after deletion
        }
    }

    private void populateTable() {
        List<Attendee> attendees = attendeeManager.getAllAttendees();
        updateTable(attendees);
    }
    
    private void updateTable(List<Attendee> attendees) {
        tableModel.setRowCount(0); // Clear existing rows
        for (Attendee attendee : attendees) {
            tableModel.addRow(new Object[]{
                attendee.getId(), attendee.getFullName(), attendee.getEmail(),
                attendee.getContactNumber(), attendee.getCountry()
            });
        }
    }

}



