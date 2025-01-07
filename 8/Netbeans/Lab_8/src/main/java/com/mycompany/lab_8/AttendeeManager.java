/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab_8;

/**
 *
 * @author rje24
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttendeeManager {
    private Connection connection;

    public AttendeeManager() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ConferenceDB", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add attendee
    public void addAttendee(String fullName, String email, String contactNumber, String country) {
        String sql = "INSERT INTO attendees (full_name, email, contact_number, country) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, fullName);
            ps.setString(2, email);
            ps.setString(3, contactNumber);
            ps.setString(4, country);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Update an attendee
    public void updateAttendee(Attendee attendee) {
        String sql = "UPDATE attendees SET full_name = ?, email = ?, contact_number = ?, country = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, attendee.getFullName());
            ps.setString(2, attendee.getEmail());
            ps.setString(3, attendee.getContactNumber());
            ps.setString(4, attendee.getCountry());
            ps.setInt(5, attendee.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get attendee by ID
    public Attendee getAttendeeById(int id) {
        String sql = "SELECT * FROM attendees WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Attendee(
                    rs.getInt("id"),
                    rs.getString("full_name"),
                    rs.getString("email"),
                    rs.getString("contact_number"),
                    rs.getString("country")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get all attendees
    public List<Attendee> getAllAttendees() {
        List<Attendee> attendees = new ArrayList<>();
        String sql = "SELECT * FROM attendees";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                attendees.add(new Attendee(
                    rs.getInt("id"),
                    rs.getString("full_name"),
                    rs.getString("email"),
                    rs.getString("contact_number"),
                    rs.getString("country")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attendees;
    }

    // Search attendees
    public List<Attendee> searchAttendees(String keyword) {
        List<Attendee> attendees = new ArrayList<>();
        String sql = "SELECT * FROM attendees WHERE full_name LIKE ? OR country LIKE ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                attendees.add(new Attendee(
                    rs.getInt("id"),
                    rs.getString("full_name"),
                    rs.getString("email"),
                    rs.getString("contact_number"),
                    rs.getString("country")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attendees;
    }

    // Delete attendee
    public void deleteAttendee(int id) {
        String sql = "DELETE FROM attendees WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Generate attendee statistics by country
    public List<String> generateStatistics() {
    List<String> statistics = new ArrayList<>();
    String sql = "SELECT country, COUNT(*) AS count FROM attendees GROUP BY country";
    try (Statement stmt = connection.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
        while (rs.next()) {
            String country = rs.getString("country");
            int count = rs.getInt("count");
            statistics.add(country + ": " + count);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return statistics;
}
}
