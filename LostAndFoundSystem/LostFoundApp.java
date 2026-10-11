
// LostFoundApp.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class LostFoundApp extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private ItemDatabase db;

    // Components for Lost panel
    private JTextField lostNameField, lostLocationField, lostDateField;
    private JComboBox<String> lostCategoryBox;
    private JTextArea lostDescArea;

    // Components for Found panel
    private JTextField foundNameField, foundLocationField, foundDateField;
    private JComboBox<String> foundCategoryBox;
    private JTextArea foundDescArea;

    // Components for Search panel
    private JTextField searchField;
    private JTextArea searchResultArea;
    private JComboBox<String> viewFilterBox;

    public LostFoundApp() {
        super("University Lost & Found System");
        db = new ItemDatabase();
        initUI();
        setSize(700, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // center
    }

    private void initUI() {
        // Top navigation
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnLost = new JButton("Report Lost Item");
        JButton btnFound = new JButton("Report Found Item");
        JButton btnSearch = new JButton("Search / View");
        topPanel.add(btnLost);
        topPanel.add(btnFound);
        topPanel.add(btnSearch);

        // mainPanel with CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(createLostPanel(), "LOST");
        mainPanel.add(createFoundPanel(), "FOUND");
        mainPanel.add(createSearchPanel(), "SEARCH");

        // default: show SEARCH
        cardLayout.show(mainPanel, "SEARCH");

        // Button actions
        btnLost.addActionListener(e -> cardLayout.show(mainPanel, "LOST"));
        btnFound.addActionListener(e -> cardLayout.show(mainPanel, "FOUND"));
        btnSearch.addActionListener(e -> {
            refreshSearchView();
            cardLayout.show(mainPanel, "SEARCH");
        });

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createLostPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6,6,6,6);
        c.fill = GridBagConstraints.HORIZONTAL;

        // Labels and fields
        c.gridx = 0; c.gridy = 0; form.add(new JLabel("Item Name:"), c);
        c.gridx = 1; lostNameField = new JTextField(20); form.add(lostNameField, c);

        c.gridx = 0; c.gridy = 1; form.add(new JLabel("Category:"), c);
        c.gridx = 1; lostCategoryBox = new JComboBox<>(new String[]{"Electronics","Books","Clothes","Accessories","Others"}); form.add(lostCategoryBox, c);

        c.gridx = 0; c.gridy = 2; form.add(new JLabel("Location (where lost):"), c);
        c.gridx = 1; lostLocationField = new JTextField(20); form.add(lostLocationField, c);

        c.gridx = 0; c.gridy = 3; form.add(new JLabel("Date Lost (YYYY-MM-DD):"), c);
        c.gridx = 1; lostDateField = new JTextField(10); form.add(lostDateField, c);

        c.gridx = 0; c.gridy = 4; c.anchor = GridBagConstraints.NORTH; form.add(new JLabel("Description:"), c);
        c.gridx = 1; lostDescArea = new JTextArea(6, 20); form.add(new JScrollPane(lostDescArea), c);

        JButton submitLost = new JButton("Submit Lost Report");
        submitLost.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                submitLost();
            }
        });

        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        south.add(submitLost);

        panel.add(new JLabel("Report Lost Item", SwingConstants.CENTER), BorderLayout.NORTH);
        panel.add(form, BorderLayout.CENTER);
        panel.add(south, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createFoundPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6,6,6,6);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0; c.gridy = 0; form.add(new JLabel("Item Name:"), c);
        c.gridx = 1; foundNameField = new JTextField(20); form.add(foundNameField, c);

        c.gridx = 0; c.gridy = 1; form.add(new JLabel("Category:"), c);
        c.gridx = 1; foundCategoryBox = new JComboBox<>(new String[]{"Electronics","Books","Clothes","Accessories","Others"}); form.add(foundCategoryBox, c);

        c.gridx = 0; c.gridy = 2; form.add(new JLabel("Location (where found):"), c);
        c.gridx = 1; foundLocationField = new JTextField(20); form.add(foundLocationField, c);

        c.gridx = 0; c.gridy = 3; form.add(new JLabel("Date Found (YYYY-MM-DD):"), c);
        c.gridx = 1; foundDateField = new JTextField(10); form.add(foundDateField, c);

        c.gridx = 0; c.gridy = 4; c.anchor = GridBagConstraints.NORTH; form.add(new JLabel("Description:"), c);
        c.gridx = 1; foundDescArea = new JTextArea(6, 20); form.add(new JScrollPane(foundDescArea), c);

        JButton submitFound = new JButton("Submit Found Report");
        submitFound.addActionListener(e -> submitFound());

        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        south.add(submitFound);

        panel.add(new JLabel("Report Found Item", SwingConstants.CENTER), BorderLayout.NORTH);
        panel.add(form, BorderLayout.CENTER);
        panel.add(south, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Search keyword:"));
        searchField = new JTextField(20);
        top.add(searchField);
        JButton btnSearch = new JButton("Search");
        top.add(btnSearch);

        top.add(new JLabel("View:"));
        viewFilterBox = new JComboBox<>(new String[]{"All","Lost","Found"});
        top.add(viewFilterBox);

        btnSearch.addActionListener(e -> performSearch());
        viewFilterBox.addActionListener(e -> refreshSearchView());

        searchResultArea = new JTextArea();
        searchResultArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(searchResultArea);

        // Quick demo data button
        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton demoBtn = new JButton("Load Demo Data");
        demoBtn.addActionListener(e -> loadDemoData());
        south.add(demoBtn);

        panel.add(new JLabel("Search & Browse Items", SwingConstants.CENTER), BorderLayout.NORTH);
        panel.add(top, BorderLayout.PAGE_START);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(south, BorderLayout.SOUTH);

        return panel;
    }

    // Handlers
    private void submitLost() {
        String name = lostNameField.getText().trim();
        String cat = (String)lostCategoryBox.getSelectedItem();
        String loc = lostLocationField.getText().trim();
        String date = lostDateField.getText().trim();
        String desc = lostDescArea.getText().trim();

        if (name.isEmpty() || loc.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter at least item name and location.", "Incomplete", JOptionPane.WARNING_MESSAGE);
            return;
        }

        LostItem li = new LostItem(name, cat, desc, loc, date.isEmpty() ? "Unknown" : date);
        db.addItem(li);
        JOptionPane.showMessageDialog(this, "Lost item recorded. ID: " + li.getItemId(), "Success", JOptionPane.INFORMATION_MESSAGE);
        clearLostForm();
    }

    private void submitFound() {
        String name = foundNameField.getText().trim();
        String cat = (String)foundCategoryBox.getSelectedItem();
        String loc = foundLocationField.getText().trim();
        String date = foundDateField.getText().trim();
        String desc = foundDescArea.getText().trim();

        if (name.isEmpty() || loc.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter at least item name and location.", "Incomplete", JOptionPane.WARNING_MESSAGE);
            return;
        }

        FoundItem fi = new FoundItem(name, cat, desc, loc, date.isEmpty() ? "Unknown" : date);
        db.addItem(fi);
        JOptionPane.showMessageDialog(this, "Found item recorded. ID: " + fi.getItemId(), "Success", JOptionPane.INFORMATION_MESSAGE);
        clearFoundForm();
    }

    private void performSearch() {
        String key = searchField.getText().trim();
        List<Item> results = db.search(key);
        StringBuilder sb = new StringBuilder();
        for (Item it : results) {
            if (filterByView(it)) sb.append(it.toString()).append("\n");
        }
        if (sb.length() == 0) sb.append("No items found matching: ").append(key).append("\n");
        searchResultArea.setText(sb.toString());
    }

    private boolean filterByView(Item it) {
        String view = (String) viewFilterBox.getSelectedItem();
        if (view.equals("All")) return true;
        return it.getStatus().equalsIgnoreCase(view);
    }

    private void refreshSearchView() {
        String view = (String) viewFilterBox.getSelectedItem();
        List<Item> items;
        if (view.equals("All")) items = db.getAll();
        else items = db.getByStatus(view);
        StringBuilder sb = new StringBuilder();
        if (items.isEmpty()) sb.append("No items to display.\n");
        else for (Item it : items) sb.append(it.toString()).append("\n");
        searchResultArea.setText(sb.toString());
    }

    private void loadDemoData() {
        db.addItem(new LostItem("Black Backpack","Accessories","Leather backpack with red keychain","Library 3rd floor","2025-09-25"));
        db.addItem(new FoundItem("iPhone 12","Electronics","Black, no case, has lockscreen 'Jay'","Cafeteria","2025-09-30"));
        db.addItem(new LostItem("Calculus Textbook","Books","Blue cover, annotations inside","Lecture Hall B","2025-09-20"));
        refreshSearchView();
        JOptionPane.showMessageDialog(this, "Demo data loaded.", "Demo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void clearLostForm() {
        lostNameField.setText("");
        lostLocationField.setText("");
        lostDateField.setText("");
        lostDescArea.setText("");
        lostCategoryBox.setSelectedIndex(0);
    }

    private void clearFoundForm() {
        foundNameField.setText("");
        foundLocationField.setText("");
        foundDateField.setText("");
        foundDescArea.setText("");
        foundCategoryBox.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LostFoundApp app = new LostFoundApp();
            app.setVisible(true);
        });
    }
}