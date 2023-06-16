package com.example.bankingSystem;

public class Account {
        private String name;
        private String id;
        private String username;
        private String password;
        private String role;



        public Account(String name, String id, String username, String password) {
            this.name = name;
            this.id = id;
            this.username = username;
            this.password = password;
            this.role = role;
        }
        public void setName(String name) {
            this.name = name;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getName() {
            return name;
        }

        public String getId() {
            return id;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public String getRole() {
            return role;
        }
    }


