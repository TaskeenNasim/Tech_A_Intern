import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class Contact {
    private String name;
    private String address;
    private String phone;
    private String email;

    public Contact(String name, String address, String phone, String email) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}

class AddressBookApp extends JFrame implements ActionListener {
    private ArrayList<Contact> contacts;
    private JTextField nameField, addressField, phoneField, emailField;
    private JTextArea displayArea;

    public AddressBookApp() {
        contacts = new ArrayList<>();

        setTitle("Address Book");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);

        inputPanel.add(new JLabel("Name:"), gbc);
        gbc.gridy++;
        inputPanel.add(new JLabel("Address:"), gbc);
        gbc.gridy++;
        inputPanel.add(new JLabel("Phone:"), gbc);
        gbc.gridy++;
        inputPanel.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.LINE_START;
        nameField = new JTextField(20);
        inputPanel.add(nameField, gbc);
        gbc.gridy++;
        addressField = new JTextField(20);
        inputPanel.add(addressField, gbc);
        gbc.gridy++;
        phoneField = new JTextField(20);
        inputPanel.add(phoneField, gbc);
        gbc.gridy++;
        emailField = new JTextField(20);
        inputPanel.add(emailField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addButton = new JButton("Add");
        addButton.addActionListener(this);
        buttonPanel.add(addButton);
        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(this);
        buttonPanel.add(deleteButton);
        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(this);
        buttonPanel.add(updateButton);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(this);
        buttonPanel.add(searchButton);
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(this);
        buttonPanel.add(clearButton);

        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.add(inputPanel, BorderLayout.NORTH);
        controlPanel.add(buttonPanel, BorderLayout.CENTER);

        displayArea = new JTextArea(15, 40);
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        add(controlPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch (action) {
            case "Add":
                String name = nameField.getText();
                String address = addressField.getText();
                String phone = phoneField.getText();
                String email = emailField.getText();
                contacts.add(new Contact(name, address, phone, email));
                clearFields();
                displayContacts();
                break;
            case "Delete":
                String searchName = JOptionPane.showInputDialog(this, "Enter the name to delete:");
                deleteContact(searchName);
                break;
            case "Update":
                String updateName = JOptionPane.showInputDialog(this, "Enter the name to update:");
                updateContact(updateName);
                break;
            case "Search":
                String queryName = JOptionPane.showInputDialog(this, "Enter the name to search:");
                searchContact(queryName);
                break;
            case "Clear":
                clearFields();
                break;
        }
    }

    private void clearFields() {
        nameField.setText("");
        addressField.setText("");
        phoneField.setText("");
        emailField.setText("");
    }

    private void deleteContact(String name) {
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getName().equalsIgnoreCase(name)) {
                contacts.remove(i);
                displayContacts();
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Contact not found!");
    }

    private void updateContact(String name) {
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getName().equalsIgnoreCase(name)) {
                String newName = nameField.getText();
                String newAddress = addressField.getText();
                String newPhone = phoneField.getText();
                String newEmail = emailField.getText();
                contacts.set(i, new Contact(newName, newAddress, newPhone, newEmail));
                clearFields();
                displayContacts();
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Contact not found!");
    }

    private void searchContact(String name) {
        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(name)) {
                displayArea.setText(contact.getName() + "\n" +
                        contact.getAddress() + "\n" +
                        contact.getPhone() + "\n" +
                        contact.getEmail());
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Contact not found!");
    }

    private void displayContacts() {
        StringBuilder builder = new StringBuilder();
        for (Contact contact : contacts) {
            builder.append(contact.getName()).append("\n");
            builder.append(contact.getAddress()).append("\n");
            builder.append(contact.getPhone()).append("\n");
            builder.append(contact.getEmail()).append("\n\n");
        }
        displayArea.setText(builder.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AddressBookApp::new);
    }
}
