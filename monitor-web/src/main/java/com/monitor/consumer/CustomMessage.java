/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monitor.consumer;

/**
 *
 * @author denis
 */
public class CustomMessage {

    private final String id;
    private final String email;

    public CustomMessage(String id, String name) {
        this.id = id;
        this.email = name;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "CustomMessage{"
                + "id=" + id
                + ", name='" + email + '\''
                + '}';
    }
}
