import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class BioskopSystemGUI {
    private static final String DATA_FILE = "bsi_cinema_data.ser";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final double SERVICE_FEE = 4000.0;
    private static final int SEAT_ROWS = 9;
    private static final int SEAT_COLS = 12;
    private static final int TOTAL_SEATS = SEAT_ROWS * SEAT_COLS;
    private static final char[] ROW_LABELS = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'};
    private static final String BORDER = "==========================================================================";

    private static final Pattern DATE_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
    private static final Pattern TIME_PATTERN = Pattern.compile("^([01]?[0-9]|2[0-3]):[0-5][0-9]$");

    private static JFrame mainFrame;
    private static CardLayout cardLayout;
    private static JPanel cardPanel;
    private static String currentUser;
    private static String currentUserRole;

    public static void main(String[] args) {
        initData();
        createAndShowGUI();
    }

    // Data structure classes (must implement Serializable)
    static class User implements Serializable {
        final String username;
        final String password;
        final String role;

        User(String username, String password, String role) {
            this.username = username;
            this.password = password;
            this.role = role;
        }
    }

    static class Film implements Serializable {
        final int id;
        String title;
        String genre;
        String duration;
        double price;

        Film(int id, String title, String genre, String duration, double price) {
            this.id = id;
            this.title = title;
            this.genre = genre;
            this.duration = duration;
            this.price = price;
        }
    }

    static class Schedule implements Serializable {
        final int id;
        int filmId;
        String date;
        String time;
        String studio;
        int availableSeats;
        boolean[][] seatMap;

        Schedule(int id, int filmId, String date, String time, String studio) {
            this.id = id;
            this.filmId = filmId;
            this.date = date;
            this.time = time;
            this.studio = studio;
            this.availableSeats = TOTAL_SEATS;
            this.seatMap = new boolean[SEAT_ROWS][SEAT_COLS];
        }
    }

    static class Transaction implements Serializable {
        final int id;
        final String username;
        final int filmId;
        final int scheduleId;
        final int quantity;
        final double totalPrice;
        final String transactionDate;
        final List<String> selectedSeats;

        Transaction(int id, String username, int filmId, int scheduleId, int quantity, 
                   double totalPrice, String transactionDate, List<String> selectedSeats) {
            this.id = id;
            this.username = username;
            this.filmId = filmId;
            this.scheduleId = scheduleId;
            this.quantity = quantity;
            this.totalPrice = totalPrice;
            this.transactionDate = transactionDate;
            this.selectedSeats = selectedSeats;
        }
    }
    
    static class BioskopData implements Serializable {
        final List<User> users = new ArrayList<>();
        final List<Film> films = new ArrayList<>();
        final List<Schedule> schedules = new ArrayList<>();
        final List<Transaction> transactions = new ArrayList<>();
    }
    
    // Initialize data
    private static void initData() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            try {
                BioskopData data = new BioskopData();
                data.users.add(new User("BSI", "123", "admin"));
                data.users.add(new User("user", "123", "user"));
                saveData(data);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Gagal membuat data awal: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Save data to file
    private static void saveData(BioskopData data) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(data);
        }
    }

    // Load data from file
    private static BioskopData loadData() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            return new BioskopData();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            return (BioskopData) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Gagal memuat data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return new BioskopData();
        }
    }

    private static void createAndShowGUI() {
        // Create main frame
        mainFrame = new JFrame("BSI Cinema System");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen
        mainFrame.setMinimumSize(new Dimension(1024, 768));

        // Create card layout for screens
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Create screens
        createMainMenuScreen();
        createLoginScreen();
        createRegisterScreen();
        createAdminDashboardScreen();
        createUserDashboardScreen();
        createManageFilmsScreen();
        createAddFilmScreen();
        createViewFilmsScreen();
        createEditFilmScreen();
        createDeleteFilmScreen();
        createManageSchedulesScreen();
        createAddScheduleScreen();
        createViewSchedulesScreen();
        createEditScheduleScreen();
        createDeleteScheduleScreen();
        createSalesReportScreen();
        createViewFilmSchedulesScreen();
        createBuyTicketScreen();
        createPurchaseHistoryScreen();
        createSeatSelectionScreen();
        createPaymentScreen();
        createReceiptScreen();

        // Add screens to card panel
        mainFrame.add(cardPanel);

        // Show the frame
        mainFrame.setVisible(true);
    }

private static void createMainMenuScreen() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    panel.setBackground(new Color(30, 30, 30)); // Background gelap

    // Header
    JLabel headerLabel = new JLabel("🎬 SELAMAT DATANG DI BSI CINEMA", SwingConstants.CENTER);
    headerLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
    headerLabel.setForeground(Color.WHITE);
    headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));
    panel.add(headerLabel, BorderLayout.NORTH);

    // Button panel
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(3, 1, 15, 15));
    buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 150, 50, 150));
    buttonPanel.setBackground(new Color(30, 30, 30));

    // Styling tombol
    JButton loginButton = createStyledButton("🔐 LOGIN");
    loginButton.addActionListener(e -> cardLayout.show(cardPanel, "LoginScreen"));

    JButton registerButton = createStyledButton("📝 REGISTRASI");
    registerButton.addActionListener(e -> cardLayout.show(cardPanel, "RegisterScreen"));

    JButton exitButton = createStyledButton("❌ KELUAR");
    exitButton.addActionListener(e -> System.exit(0));

    buttonPanel.add(loginButton);
    buttonPanel.add(registerButton);
    buttonPanel.add(exitButton);

    panel.add(buttonPanel, BorderLayout.CENTER);
    cardPanel.add(panel, "MainMenuScreen");
}

// Method bantu untuk styling tombol
private static JButton createStyledButton(String text) {
    JButton button = new JButton(text);
    button.setFont(new Font("SansSerif", Font.BOLD, 18));
    button.setBackground(new Color(220, 53, 69)); // Warna merah modern
    button.setForeground(Color.WHITE);
    button.setFocusPainted(false);
    button.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(Color.WHITE),
        BorderFactory.createEmptyBorder(10, 20, 10, 20)
    ));
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    return button;
}


private static void createLoginScreen() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    panel.setBackground(new Color(30, 30, 30));

    JLabel headerLabel = new JLabel("🔐 LOGIN", SwingConstants.CENTER);
    headerLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
    headerLabel.setForeground(Color.WHITE);
    headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));
    panel.add(headerLabel, BorderLayout.NORTH);

    JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 15));
    formPanel.setBorder(BorderFactory.createEmptyBorder(50, 150, 50, 150));
    formPanel.setBackground(new Color(30, 30, 30));

    JLabel usernameLabel = new JLabel("Username:");
    usernameLabel.setForeground(Color.WHITE);
    JTextField usernameField = new JTextField();

    JLabel passwordLabel = new JLabel("Password:");
    passwordLabel.setForeground(Color.WHITE);
    JPasswordField passwordField = new JPasswordField();

    JButton loginButton = createStyledButton("Login");
    loginButton.addActionListener(e -> {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        BioskopData data = loadData();
        Optional<User> user = data.users.stream()
            .filter(u -> u.username.equals(username) && u.password.equals(password))
            .findFirst();

        if (user.isPresent()) {
            currentUser = username;
            currentUserRole = user.get().role;
            if (currentUserRole.equals("admin")) {
                cardLayout.show(cardPanel, "AdminDashboardScreen");
            } else {
                cardLayout.show(cardPanel, "UserDashboardScreen");
            }
        } else {
            JOptionPane.showMessageDialog(panel, "Username atau password salah!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    });

    JButton backButton = createStyledButton("⬅ Kembali");
    backButton.addActionListener(e -> cardLayout.show(cardPanel, "MainMenuScreen"));

    formPanel.add(usernameLabel);
    formPanel.add(usernameField);
    formPanel.add(passwordLabel);
    formPanel.add(passwordField);
    formPanel.add(loginButton);
    formPanel.add(backButton);

    panel.add(formPanel, BorderLayout.CENTER);
    cardPanel.add(panel, "LoginScreen");
}


private static void createRegisterScreen() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    panel.setBackground(new Color(30, 30, 30));

    JLabel headerLabel = new JLabel("📝 REGISTRASI PENGGUNA BARU", SwingConstants.CENTER);
    headerLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
    headerLabel.setForeground(Color.WHITE);
    headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));
    panel.add(headerLabel, BorderLayout.NORTH);

    JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
    formPanel.setBorder(BorderFactory.createEmptyBorder(50, 150, 50, 150));
    formPanel.setBackground(new Color(30, 30, 30));

    JLabel usernameLabel = new JLabel("Username:");
    usernameLabel.setForeground(Color.WHITE);
    JTextField usernameField = new JTextField();

    JLabel passwordLabel = new JLabel("Password:");
    passwordLabel.setForeground(Color.WHITE);
    JPasswordField passwordField = new JPasswordField();

    JButton registerButton = createStyledButton("Registrasi");
    registerButton.addActionListener(e -> {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(panel, "Username dan password tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        BioskopData data = loadData();
        if (data.users.stream().anyMatch(u -> u.username.equals(username))) {
            JOptionPane.showMessageDialog(panel, "Username sudah digunakan!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        data.users.add(new User(username, password, "user"));
        try {
            saveData(data);
            JOptionPane.showMessageDialog(panel, "Registrasi berhasil!", "Success", JOptionPane.INFORMATION_MESSAGE);
            cardLayout.show(cardPanel, "MainMenuScreen");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(panel, "Gagal menyimpan data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    });

    JButton backButton = createStyledButton("⬅ Kembali");
    backButton.addActionListener(e -> cardLayout.show(cardPanel, "MainMenuScreen"));

    formPanel.add(usernameLabel);
    formPanel.add(usernameField);
    formPanel.add(passwordLabel);
    formPanel.add(passwordField);
    formPanel.add(registerButton);
    formPanel.add(backButton);

    panel.add(formPanel, BorderLayout.CENTER);
    cardPanel.add(panel, "RegisterScreen");
}


private static void createAdminDashboardScreen() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    panel.setBackground(new Color(30, 30, 30));

    JLabel headerLabel = new JLabel("📋 DASHBOARD ADMIN - " + currentUser, SwingConstants.CENTER);
    headerLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
    headerLabel.setForeground(Color.WHITE);
    headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));
    panel.add(headerLabel, BorderLayout.NORTH);

    JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 15, 15));
    buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 150, 50, 150));
    buttonPanel.setBackground(new Color(30, 30, 30));

    JButton manageFilmsButton = createStyledButton("🎞 Kelola Film");
    manageFilmsButton.addActionListener(e -> cardLayout.show(cardPanel, "ManageFilmsScreen"));

    JButton manageSchedulesButton = createStyledButton("🗓 Kelola Jadwal");
    manageSchedulesButton.addActionListener(e -> cardLayout.show(cardPanel, "ManageSchedulesScreen"));

    JButton salesReportButton = createStyledButton("📊 Laporan Penjualan");
    salesReportButton.addActionListener(e -> cardLayout.show(cardPanel, "SalesReportScreen"));

    JButton logoutButton = createStyledButton("🚪 Logout");
    logoutButton.addActionListener(e -> {
        currentUser = null;
        currentUserRole = null;
        cardLayout.show(cardPanel, "MainMenuScreen");
    });

    buttonPanel.add(manageFilmsButton);
    buttonPanel.add(manageSchedulesButton);
    buttonPanel.add(salesReportButton);
    buttonPanel.add(logoutButton);

    panel.add(buttonPanel, BorderLayout.CENTER);
    cardPanel.add(panel, "AdminDashboardScreen");
}

private static void createUserDashboardScreen() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    panel.setBackground(new Color(30, 30, 30)); // Dark background

    // Header
    JLabel headerLabel = new JLabel("🎬 BSI CINEMA - " + currentUser, SwingConstants.CENTER);
    headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
    headerLabel.setForeground(Color.WHITE);
    panel.add(headerLabel, BorderLayout.NORTH);

    // Button Panel
    JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 15, 15));
    buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 200, 50, 200));
    buttonPanel.setBackground(new Color(30, 30, 30));

    // Buttons
    JButton viewSchedulesButton = createStyledButton("📅 Lihat Jadwal Film");
    viewSchedulesButton.addActionListener(e -> cardLayout.show(cardPanel, "ViewFilmSchedulesScreen"));

    JButton buyTicketButton = createStyledButton("🎟️ Beli Tiket");
    buyTicketButton.addActionListener(e -> cardLayout.show(cardPanel, "BuyTicketScreen"));

    JButton purchaseHistoryButton = createStyledButton("🧾 Riwayat Pembelian");
    purchaseHistoryButton.addActionListener(e -> cardLayout.show(cardPanel, "PurchaseHistoryScreen"));

    JButton logoutButton = createStyledButton("🚪 Logout");
    logoutButton.addActionListener(e -> {
        currentUser = null;
        currentUserRole = null;
        cardLayout.show(cardPanel, "MainMenuScreen");
    });

    // Add buttons to panel
    buttonPanel.add(viewSchedulesButton);
    buttonPanel.add(buyTicketButton);
    buttonPanel.add(purchaseHistoryButton);
    buttonPanel.add(logoutButton);

    panel.add(buttonPanel, BorderLayout.CENTER);

    cardPanel.add(panel, "UserDashboardScreen");
}


 private static void createManageFilmsScreen() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBackground(new Color(30, 30, 30));
    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    JLabel headerLabel = new JLabel("🎞️ KELOLA FILM", SwingConstants.CENTER);
    headerLabel.setForeground(Color.WHITE);
    headerLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
    panel.add(headerLabel, BorderLayout.NORTH);

    JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 15, 15));
    buttonPanel.setBackground(new Color(30, 30, 30));
    buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 150, 50, 150));

    String[] buttonTexts = {
        "➕ Tambah Film", "📋 Lihat Daftar Film", "✏️ Edit Film", "🗑️ Hapus Film", "🔙 Kembali"
    };
    String[] cardTargets = {
        "AddFilmScreen", "ViewFilmsScreen", "EditFilmScreen", "DeleteFilmScreen", "AdminDashboardScreen"
    };

    for (int i = 0; i < buttonTexts.length; i++) {
        JButton button = createStyledButton(buttonTexts[i]);
        String target = cardTargets[i];
        button.addActionListener(e -> cardLayout.show(cardPanel, target));
        buttonPanel.add(button);
    }

    panel.add(buttonPanel, BorderLayout.CENTER);
    cardPanel.add(panel, "ManageFilmsScreen");
}


private static void createAddFilmScreen() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBackground(new Color(30, 30, 30));
    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    JLabel headerLabel = new JLabel("🎬 TAMBAH FILM", SwingConstants.CENTER);
    headerLabel.setForeground(Color.WHITE);
    headerLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
    panel.add(headerLabel, BorderLayout.NORTH);

    JPanel formPanel = new JPanel(new GridLayout(5, 2, 15, 15));
    formPanel.setBackground(new Color(30, 30, 30));
    formPanel.setBorder(BorderFactory.createEmptyBorder(50, 150, 50, 150));

    JLabel[] labels = {
        new JLabel("🎞️ Judul Film:"), new JLabel("🎭 Genre:"), 
        new JLabel("⏱️ Durasi (Menit):"), new JLabel("💵 Harga Tiket:")
    };

    JTextField titleField = new JTextField();
    JTextField genreField = new JTextField();
    JTextField durationField = new JTextField();
    JTextField priceField = new JTextField();
    JTextField[] fields = {titleField, genreField, durationField, priceField};

    for (int i = 0; i < labels.length; i++) {
        labels[i].setForeground(Color.WHITE);
        labels[i].setFont(new Font("SansSerif", Font.PLAIN, 14));
        formPanel.add(labels[i]);
        formPanel.add(fields[i]);
    }

    JButton saveButton = createStyledButton("✅ Simpan");
    saveButton.addActionListener(e -> {
        try {
            String title = titleField.getText().trim();
            String genre = genreField.getText().trim();
            String duration = durationField.getText().trim();
            double price = Double.parseDouble(priceField.getText().trim());

            if (title.isEmpty() || genre.isEmpty() || duration.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "❌ Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            BioskopData data = loadData();
            int newId = data.films.isEmpty() ? 1 : data.films.get(data.films.size() - 1).id + 1;
            data.films.add(new Film(newId, title, genre, duration, price));
            saveData(data);

            JOptionPane.showMessageDialog(panel, "✅ Film berhasil ditambahkan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            cardLayout.show(cardPanel, "ManageFilmsScreen");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(panel, "❌ Harga harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(panel, "❌ Gagal menyimpan data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    });

    JButton backButton = createStyledButton("🔙 Kembali");
    backButton.addActionListener(e -> cardLayout.show(cardPanel, "ManageFilmsScreen"));

    formPanel.add(saveButton);
    formPanel.add(backButton);

    panel.add(formPanel, BorderLayout.CENTER);
    cardPanel.add(panel, "AddFilmScreen");
}

 private static void createViewFilmsScreen() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBackground(new Color(30, 30, 30));
    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    JLabel headerLabel = new JLabel("📋 DAFTAR FILM", SwingConstants.CENTER);
    headerLabel.setForeground(Color.WHITE);
    headerLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
    panel.add(headerLabel, BorderLayout.NORTH);

    JPanel tablePanel = new JPanel(new BorderLayout());
    tablePanel.setBackground(new Color(30, 30, 30));
    tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    BioskopData data = loadData();
    String[] columnNames = {"ID", "Judul", "Genre", "Durasi", "Harga"};
    Object[][] rowData = new Object[data.films.size()][5];

    for (int i = 0; i < data.films.size(); i++) {
        Film film = data.films.get(i);
        rowData[i][0] = film.id;
        rowData[i][1] = film.title;
        rowData[i][2] = film.genre;
        rowData[i][3] = film.duration;
        rowData[i][4] = String.format("Rp %,.2f", film.price);
    }

    JTable table = new JTable(rowData, columnNames);
    table.setFont(new Font("SansSerif", Font.PLAIN, 14));
    table.setRowHeight(28);
    table.setGridColor(Color.GRAY);
    table.setForeground(Color.WHITE);
    table.setBackground(new Color(45, 45, 45));
    table.setEnabled(false); // non-editable
    table.getTableHeader().setBackground(new Color(70, 70, 70));
    table.getTableHeader().setForeground(Color.WHITE);
    table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));

    JScrollPane scrollPane = new JScrollPane(table);
    tablePanel.add(scrollPane, BorderLayout.CENTER);
    panel.add(tablePanel, BorderLayout.CENTER);

    JButton backButton = createStyledButton("🔙 Kembali");
    backButton.addActionListener(e -> cardLayout.show(cardPanel, "ManageFilmsScreen"));
    panel.add(backButton, BorderLayout.SOUTH);

    cardPanel.add(panel, "ViewFilmsScreen");
}


private static void createBuyTicketScreen() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    panel.setBackground(Color.DARK_GRAY);

    JLabel headerLabel = new JLabel("🎟️ PEMBELIAN TIKET", SwingConstants.CENTER);
    headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
    headerLabel.setForeground(Color.WHITE);
    panel.add(headerLabel, BorderLayout.NORTH);

    JPanel contentPanel = new JPanel(new BorderLayout(20, 20));
    contentPanel.setBackground(Color.DARK_GRAY);

    JPanel schedulePanel = new JPanel(new BorderLayout());
    schedulePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY), "Pilih Jadwal", TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.PLAIN, 14), Color.WHITE));
    schedulePanel.setBackground(Color.DARK_GRAY);

    DefaultListModel<String> scheduleListModel = new DefaultListModel<>();
    BioskopData data = loadData();
    for (Schedule schedule : data.schedules) {
        Optional<Film> film = data.films.stream().filter(f -> f.id == schedule.filmId).findFirst();
        film.ifPresent(f -> scheduleListModel.addElement(String.format("%d - %s (%s %s) 🎬 Studio: %s 🎫 Kursi: %d",
            schedule.id, f.title, schedule.date, schedule.time, schedule.studio, schedule.availableSeats)));
    }

    JList<String> scheduleList = new JList<>(scheduleListModel);
    scheduleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    scheduleList.setBackground(Color.LIGHT_GRAY);
    schedulePanel.add(new JScrollPane(scheduleList), BorderLayout.CENTER);

    JPanel quantityPanel = new JPanel();
    quantityPanel.setBackground(Color.DARK_GRAY);
    quantityPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY), "Jumlah Tiket", TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.PLAIN, 14), Color.WHITE));
    JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
    quantityPanel.add(quantitySpinner);

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
    buttonPanel.setBackground(Color.DARK_GRAY);

    JButton nextButton = new JButton("Lanjut ➡️");
    nextButton.addActionListener(e -> {
        int selectedIndex = scheduleList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(panel, "Pilih jadwal terlebih dahulu!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int quantity = (Integer) quantitySpinner.getValue();
        Schedule selectedSchedule = data.schedules.get(selectedIndex);

        if (quantity > selectedSchedule.availableSeats) {
            JOptionPane.showMessageDialog(panel, "Jumlah tiket melebihi kursi yang tersedia!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Simpan state ke variabel global jika diperlukan
        cardLayout.show(cardPanel, "SeatSelectionScreen");
    });

    JButton backButton = new JButton("⬅️ Kembali");
    backButton.addActionListener(e -> cardLayout.show(cardPanel, "UserDashboardScreen"));

    buttonPanel.add(nextButton);
    buttonPanel.add(backButton);

    contentPanel.add(schedulePanel, BorderLayout.CENTER);
    contentPanel.add(quantityPanel, BorderLayout.SOUTH);

    panel.add(contentPanel, BorderLayout.CENTER);
    panel.add(buttonPanel, BorderLayout.SOUTH);

    cardPanel.add(panel, "BuyTicketScreen");
}


private static void createSeatSelectionScreen() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBackground(Color.DARK_GRAY);
    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    JLabel headerLabel = new JLabel("🪑 PEMILIHAN KURSI", SwingConstants.CENTER);
    headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
    headerLabel.setForeground(Color.WHITE);
    panel.add(headerLabel, BorderLayout.NORTH);

    JPanel seatMapPanel = new JPanel(new GridLayout(SEAT_ROWS + 2, SEAT_COLS + 2));
    seatMapPanel.setBackground(Color.DARK_GRAY);
    seatMapPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    // Kolom Header
    seatMapPanel.add(new JLabel(""));
    for (int col = 1; col <= SEAT_COLS; col++) {
        JLabel colLabel = new JLabel(String.valueOf(col), SwingConstants.CENTER);
        colLabel.setForeground(Color.WHITE);
        seatMapPanel.add(colLabel);
    }
    seatMapPanel.add(new JLabel(""));

    // Baris Kursi
    for (int row = 0; row < SEAT_ROWS; row++) {
        JLabel rowLabel = new JLabel(String.valueOf(ROW_LABELS[row]), SwingConstants.CENTER);
        rowLabel.setForeground(Color.WHITE);
        seatMapPanel.add(rowLabel);

        for (int col = 0; col < SEAT_COLS; col++) {
            JButton seatButton = new JButton(" ");
            seatButton.setPreferredSize(new Dimension(40, 40));
            seatButton.setBackground(Color.LIGHT_GRAY);
            seatButton.setEnabled(true); // logic untuk available
            seatMapPanel.add(seatButton);
        }
        seatMapPanel.add(new JLabel(""));
    }

    // Label layar
    for (int i = 0; i < SEAT_COLS + 2; i++) {
        seatMapPanel.add(i == (SEAT_COLS + 2) / 2 - 1 ? new JLabel("LAYAR", SwingConstants.CENTER) : new JLabel(""));
    }

    JPanel infoPanel = new JPanel(new GridLayout(2, 1));
    infoPanel.setBackground(Color.DARK_GRAY);
    infoPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY), "Informasi", TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.PLAIN, 14), Color.WHITE));
    JLabel label1 = new JLabel("Kursi tersedia: [ ]");
    JLabel label2 = new JLabel("Kursi terisi: [X]");
    label1.setForeground(Color.WHITE);
    label2.setForeground(Color.WHITE);
    infoPanel.add(label1);
    infoPanel.add(label2);

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
    buttonPanel.setBackground(Color.DARK_GRAY);
    JButton confirmButton = new JButton("✅ Konfirmasi");
    confirmButton.addActionListener(e -> cardLayout.show(cardPanel, "PaymentScreen"));
    JButton backButton = new JButton("⬅️ Kembali");
    backButton.addActionListener(e -> cardLayout.show(cardPanel, "BuyTicketScreen"));
    buttonPanel.add(confirmButton);
    buttonPanel.add(backButton);

    JPanel centerPanel = new JPanel(new BorderLayout());
    centerPanel.setBackground(Color.DARK_GRAY);
    centerPanel.add(seatMapPanel, BorderLayout.CENTER);
    centerPanel.add(infoPanel, BorderLayout.EAST);

    panel.add(centerPanel, BorderLayout.CENTER);
    panel.add(buttonPanel, BorderLayout.SOUTH);

    cardPanel.add(panel, "SeatSelectionScreen");
}

    private static void createPaymentScreen() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header
        JLabel headerLabel = new JLabel("PEMBAYARAN", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(headerLabel, BorderLayout.NORTH);

        // Payment details panel
        JPanel detailsPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));

        // (In a real implementation, you would populate these with actual data)
        detailsPanel.add(new JLabel("Film:"));
        detailsPanel.add(new JLabel("Avengers: Endgame"));
        
        detailsPanel.add(new JLabel("Tanggal:"));
        detailsPanel.add(new JLabel("2023-05-20"));
        
        detailsPanel.add(new JLabel("Waktu:"));
        detailsPanel.add(new JLabel("19:00"));
        
        detailsPanel.add(new JLabel("Studio:"));
        detailsPanel.add(new JLabel("Studio 1"));
        
        detailsPanel.add(new JLabel("Kursi:"));
        detailsPanel.add(new JLabel("A1, A2, A3"));
        
        detailsPanel.add(new JLabel("Total Harga:"));
        detailsPanel.add(new JLabel("Rp 120,000"));

        // Payment method panel
        JPanel methodPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        methodPanel.setBorder(BorderFactory.createTitledBorder("Metode Pembayaran"));
        
        ButtonGroup paymentGroup = new ButtonGroup();
        JRadioButton cashRadio = new JRadioButton("Tunai");
        JRadioButton transferRadio = new JRadioButton("Transfer Bank");
        paymentGroup.add(cashRadio);
        paymentGroup.add(transferRadio);
        methodPanel.add(cashRadio);
        methodPanel.add(transferRadio);
        cashRadio.setSelected(true);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton payButton = new JButton("Bayar");
        payButton.addActionListener(e -> cardLayout.show(cardPanel, "ReceiptScreen"));
        
        JButton backButton = new JButton("Kembali");
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "SeatSelectionScreen"));
        buttonPanel.add(payButton);
        buttonPanel.add(backButton);

        // Add components to main panel
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(detailsPanel, BorderLayout.NORTH);
        centerPanel.add(methodPanel, BorderLayout.CENTER);

        panel.add(centerPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        cardPanel.add(panel, "PaymentScreen");
    }

    private static void createReceiptScreen() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header
        JLabel headerLabel = new JLabel("STRUK PEMBELIAN", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(headerLabel, BorderLayout.NORTH);

        // Receipt panel
        JPanel receiptPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        receiptPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));

        // (In a real implementation, you would populate these with actual data)
        receiptPanel.add(new JLabel("ID Transaksi: TRX-20230520-001"));
        receiptPanel.add(new JLabel("Tanggal: 2023-05-20 18:30:45"));
        receiptPanel.add(new JLabel("Nama Pembeli: " + currentUser));
        receiptPanel.add(new JLabel("----------------------------------------"));
        receiptPanel.add(new JLabel("Film: Avengers: Endgame"));
        receiptPanel.add(new JLabel("Genre: Action, Adventure, Sci-Fi"));
        receiptPanel.add(new JLabel("Durasi: 181 menit"));
        receiptPanel.add(new JLabel("----------------------------------------"));
        receiptPanel.add(new JLabel("Tanggal Tayang: 2023-05-20"));
        receiptPanel.add(new JLabel("Jam Tayang: 19:00"));
        receiptPanel.add(new JLabel("Studio: Studio 1"));
        receiptPanel.add(new JLabel("----------------------------------------"));
        receiptPanel.add(new JLabel("Jumlah Tiket: 3"));
        receiptPanel.add(new JLabel("Nomor Kursi: A1, A2, A3"));
        receiptPanel.add(new JLabel("----------------------------------------"));
        receiptPanel.add(new JLabel("Rincian Pembayaran:"));
        receiptPanel.add(new JLabel("1. TIKET      Rp 30,000 x 3 = Rp 90,000"));
        receiptPanel.add(new JLabel("2. LAYANAN    Rp 4,000 x 3  = Rp 12,000"));
        receiptPanel.add(new JLabel("----------------------------------------"));
        receiptPanel.add(new JLabel("TOTAL PEMBAYARAN: Rp 102,000"));
        receiptPanel.add(new JLabel("----------------------------------------"));
        receiptPanel.add(new JLabel("Terima kasih telah membeli tiket di bioskop kami"));
        receiptPanel.add(new JLabel("Catatan:"));
        receiptPanel.add(new JLabel("- Harap datang 30 menit sebelum pemutaran film"));
        receiptPanel.add(new JLabel("- Tunjukkan struk ini saat masuk"));
        receiptPanel.add(new JLabel("- Tiket tidak dapat dibatalkan setelah pembelian"));

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton finishButton = new JButton("Selesai");
        finishButton.addActionListener(e -> cardLayout.show(cardPanel, "UserDashboardScreen"));
        buttonPanel.add(finishButton);

        // Add components to main panel
        panel.add(receiptPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        cardPanel.add(panel, "ReceiptScreen");
    }

    @SuppressWarnings("UseSpecificCatch")
    private static void createEditFilmScreen() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    panel.setBackground(new Color(30, 30, 30));

    JLabel headerLabel = new JLabel("✏️ EDIT FILM", SwingConstants.CENTER);
    headerLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
    headerLabel.setForeground(Color.WHITE);
    panel.add(headerLabel, BorderLayout.NORTH);

    JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
    formPanel.setBorder(BorderFactory.createEmptyBorder(30, 150, 30, 150));
    formPanel.setBackground(new Color(30, 30, 30));

    JLabel idLabel = new JLabel("ID Film:");
    JTextField idField = new JTextField();

    JLabel titleLabel = new JLabel("Judul Film:");
    JTextField titleField = new JTextField();

    JLabel genreLabel = new JLabel("Genre:");
    JTextField genreField = new JTextField();

    JLabel durationLabel = new JLabel("Durasi (Menit):");
    JTextField durationField = new JTextField();

    JLabel priceLabel = new JLabel("Harga Tiket:");
    JTextField priceField = new JTextField();

    for (JLabel lbl : new JLabel[]{idLabel, titleLabel, genreLabel, durationLabel, priceLabel}) {
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
    }

    formPanel.add(idLabel); formPanel.add(idField);
    formPanel.add(titleLabel); formPanel.add(titleField);
    formPanel.add(genreLabel); formPanel.add(genreField);
    formPanel.add(durationLabel); formPanel.add(durationField);
    formPanel.add(priceLabel); formPanel.add(priceField);

    JButton searchButton = createStyledButton("🔍 Cari");
    searchButton.addActionListener(e -> {
        try {
            int id = Integer.parseInt(idField.getText());
            BioskopData data = loadData();
            Optional<Film> film = data.films.stream().filter(f -> f.id == id).findFirst();
            if (film.isPresent()) {
                Film f = film.get();
                titleField.setText(f.title);
                genreField.setText(f.genre);
                durationField.setText(f.duration);
                priceField.setText(String.valueOf(f.price));
            } else {
                JOptionPane.showMessageDialog(panel, "Film tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(panel, "ID harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    });

    JButton saveButton = createStyledButton("💾 Simpan");
    saveButton.addActionListener(e -> {
        try {
            int id = Integer.parseInt(idField.getText());
            String title = titleField.getText().trim();
            String genre = genreField.getText().trim();
            String duration = durationField.getText().trim();
            double price = Double.parseDouble(priceField.getText());

            if (title.isEmpty() || genre.isEmpty() || duration.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Semua field wajib diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            BioskopData data = loadData();
            Optional<Film> filmOpt = data.films.stream().filter(f -> f.id == id).findFirst();

            if (filmOpt.isPresent()) {
                Film f = filmOpt.get();
                f.title = title;
                f.genre = genre;
                f.duration = duration;
                f.price = price;
                saveData(data);
                JOptionPane.showMessageDialog(panel, "Film berhasil diperbarui!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                cardLayout.show(cardPanel, "ManageFilmsScreen");
            } else {
                JOptionPane.showMessageDialog(panel, "Film tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(panel, "Input tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    });

    JButton backButton = createStyledButton("⬅️ Kembali");
    backButton.addActionListener(e -> cardLayout.show(cardPanel, "ManageFilmsScreen"));

    formPanel.add(searchButton); formPanel.add(saveButton);
    formPanel.add(backButton); // sisa slot akan tetap kosong secara otomatis karena GridLayout

    panel.add(formPanel, BorderLayout.CENTER);
    cardPanel.add(panel, "EditFilmScreen");
}

    @SuppressWarnings("UseSpecificCatch")
    private static void createDeleteFilmScreen() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBackground(new Color(30, 30, 30));
    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    JLabel headerLabel = new JLabel("🗑️ HAPUS FILM", SwingConstants.CENTER);
    headerLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
    headerLabel.setForeground(Color.WHITE);
    panel.add(headerLabel, BorderLayout.NORTH);

    JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
    formPanel.setBorder(BorderFactory.createEmptyBorder(30, 200, 30, 200));
    formPanel.setBackground(new Color(30, 30, 30));

    JLabel idLabel = new JLabel("ID Film:");
    idLabel.setForeground(Color.WHITE);
    idLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
    JTextField idField = new JTextField();

    formPanel.add(idLabel); formPanel.add(idField);

    JButton deleteButton = createStyledButton("🗑️ Hapus");
    deleteButton.addActionListener((var e) -> {
        try {
            int id = Integer.parseInt(idField.getText());
            BioskopData data = loadData();
            Optional<Film> film = data.films.stream().filter(f -> f.id == id).findFirst();

            if (film.isPresent()) {
                Film f = film.get();
                int confirm = JOptionPane.showConfirmDialog(panel,
                    "Hapus film berikut?\nJudul: " + f.title + "\nGenre: " + f.genre + "\nDurasi: " + f.duration,
                    "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    data.films.remove(f);
                    data.schedules.removeIf(s -> s.filmId == id);
                    saveData(data);
                    JOptionPane.showMessageDialog(panel, "Film berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    cardLayout.show(cardPanel, "ManageFilmsScreen");
                }
            } else {
                JOptionPane.showMessageDialog(panel, "Film tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(panel, "ID harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    });

    JButton backButton = createStyledButton("⬅️ Kembali");
    backButton.addActionListener(e -> cardLayout.show(cardPanel, "ManageFilmsScreen"));

    formPanel.add(deleteButton); formPanel.add(backButton);

    panel.add(formPanel, BorderLayout.CENTER);
    cardPanel.add(panel, "DeleteFilmScreen");
}

private static void createManageSchedulesScreen() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBackground(new Color(30, 30, 30));
    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    // Header
    JLabel headerLabel = new JLabel("📅 KELOLA JADWAL", SwingConstants.CENTER);
    headerLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
    headerLabel.setForeground(Color.WHITE);
    panel.add(headerLabel, BorderLayout.NORTH);

    // Button panel dengan grid 5 baris, 1 kolom, jarak 15px
    JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 0, 15));
    buttonPanel.setBorder(BorderFactory.createEmptyBorder(40, 150, 40, 150));
    buttonPanel.setBackground(new Color(30, 30, 30));

    // Nama button dan screen tujuan
    String[] actions = {
        "➕ Tambah Jadwal",
        "📄 Lihat Jadwal",
        "✏️ Edit Jadwal",
        "🗑️ Hapus Jadwal",
        "⬅️ Kembali"
    };

    String[] screens = {
        "AddScheduleScreen",
        "ViewSchedulesScreen",
        "EditScheduleScreen",
        "DeleteScheduleScreen",
        "AdminDashboardScreen"
    };

    // Buat button dan tambahkan action listener untuk pindah screen
    for (int i = 0; i < actions.length; i++) {
        JButton button = createStyledButton(actions[i]);
        String screen = screens[i];
        button.addActionListener(e -> cardLayout.show(cardPanel, screen));
        buttonPanel.add(button);
    }

    panel.add(buttonPanel, BorderLayout.CENTER);

    cardPanel.add(panel, "ManageSchedulesScreen");
}




private static void createAddScheduleScreen() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    panel.setBackground(new Color(30, 30, 30)); // Background gelap

    // Header
    JLabel headerLabel = new JLabel("🎬 TAMBAH JADWAL", SwingConstants.CENTER);
    headerLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
    headerLabel.setForeground(Color.WHITE);
    headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));
    panel.add(headerLabel, BorderLayout.NORTH);

    // Form panel
    JPanel formPanel = new JPanel(new GridLayout(6, 2, 15, 15));
    formPanel.setBorder(BorderFactory.createEmptyBorder(50, 150, 50, 150));
    formPanel.setBackground(new Color(30, 30, 30));

    // Styling labels
    JLabel filmIdLabel = createStyledLabel("🎞️ ID Film:");
    JTextField filmIdField = createStyledTextField();
    formPanel.add(filmIdLabel);
    formPanel.add(filmIdField);

    JLabel dateLabel = createStyledLabel("📅 Tanggal (YYYY-MM-DD):");
    JTextField dateField = createStyledTextField();
    formPanel.add(dateLabel);
    formPanel.add(dateField);

    JLabel timeLabel = createStyledLabel("⏰ Waktu (HH:MM):");
    JTextField timeField = createStyledTextField();
    formPanel.add(timeLabel);
    formPanel.add(timeField);

    JLabel studioLabel = createStyledLabel("🎭 Studio:");
    JTextField studioField = createStyledTextField();
    formPanel.add(studioLabel);
    formPanel.add(studioField);

    JButton saveButton = createStyledButton("💾 Simpan");
    saveButton.addActionListener(e -> {
        try {
            int filmId = Integer.parseInt(filmIdField.getText());
            String date = dateField.getText();
            String time = timeField.getText();
            String studio = studioField.getText();

            if (!DATE_PATTERN.matcher(date).matches()) {
                JOptionPane.showMessageDialog(panel, "Format tanggal tidak valid! Gunakan YYYY-MM-DD", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!TIME_PATTERN.matcher(time).matches()) {
                JOptionPane.showMessageDialog(panel, "Format waktu tidak valid! Gunakan HH:MM", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (studio.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Studio tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            BioskopData data = loadData();
            // Check if film exists
            if (data.films.stream().noneMatch(f -> f.id == filmId)) {
                JOptionPane.showMessageDialog(panel, "Film dengan ID tersebut tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int newId = data.schedules.isEmpty() ? 1 : data.schedules.get(data.schedules.size() - 1).id + 1;
            data.schedules.add(new Schedule(newId, filmId, date, time, studio));
            saveData(data);
            
            JOptionPane.showMessageDialog(panel, "Jadwal berhasil ditambahkan!", "Success", JOptionPane.INFORMATION_MESSAGE);
            cardLayout.show(cardPanel, "ManageSchedulesScreen");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(panel, "ID Film harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(panel, "Gagal menyimpan data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    });
    formPanel.add(saveButton);

    JButton backButton = createStyledButton("↩️ Kembali");
    backButton.addActionListener(e -> cardLayout.show(cardPanel, "ManageSchedulesScreen"));
    formPanel.add(backButton);

    panel.add(formPanel, BorderLayout.CENTER);

    cardPanel.add(panel, "AddScheduleScreen");
}

// Helper methods for consistent styling
private static JLabel createStyledLabel(String text) {
    JLabel label = new JLabel(text);
    label.setFont(new Font("SansSerif", Font.BOLD, 16));
    label.setForeground(Color.WHITE);
    return label;
}

private static JTextField createStyledTextField() {
    JTextField textField = new JTextField();
    textField.setFont(new Font("SansSerif", Font.PLAIN, 16));
    textField.setBackground(new Color(60, 60, 60));
    textField.setForeground(Color.WHITE);
    textField.setCaretColor(Color.WHITE);
    textField.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(100, 100, 100)),
        BorderFactory.createEmptyBorder(5, 10, 5, 10)
    ));
    return textField;
}

private static void createViewSchedulesScreen() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBackground(new Color(30, 30, 30));
    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    // Header
    JLabel headerLabel = new JLabel("🎬 DAFTAR JADWAL", SwingConstants.CENTER);
    headerLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
    headerLabel.setForeground(Color.WHITE);
    headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));
    panel.add(headerLabel, BorderLayout.NORTH);

    // Table panel
    JPanel tablePanel = new JPanel(new BorderLayout());
    tablePanel.setBackground(new Color(30, 30, 30));
    tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    BioskopData data = loadData();
    String[] columnNames = {"ID", "Judul Film", "Tanggal", "Waktu", "Studio", "Kursi Tersedia"};
    Object[][] rowData = new Object[data.schedules.size()][6];

    for (int i = 0; i < data.schedules.size(); i++) {
        Schedule schedule = data.schedules.get(i);
        Optional<Film> film = data.films.stream()
            .filter(f -> f.id == schedule.filmId)
            .findFirst();

        rowData[i][0] = schedule.id;
        rowData[i][1] = film.isPresent() ? film.get().title : "Unknown";
        rowData[i][2] = schedule.date;
        rowData[i][3] = schedule.time;
        rowData[i][4] = schedule.studio;
        rowData[i][5] = schedule.availableSeats;
    }

    JTable table = new JTable(rowData, columnNames) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Non-editable cells
        }
    };

    // Styling table agar sesuai tema
    table.setFont(new Font("SansSerif", Font.PLAIN, 12));
    table.setBackground(new Color(50, 50, 50));
    table.setForeground(Color.WHITE);
    table.setGridColor(new Color(70, 70, 70));
    table.setSelectionBackground(new Color(220, 53, 69));
    table.setSelectionForeground(Color.WHITE);
    table.setRowHeight(25);

    // Header styling
    table.getTableHeader().setBackground(new Color(70, 70, 70));
    table.getTableHeader().setForeground(Color.WHITE);
    table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));

    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.getViewport().setBackground(new Color(30, 30, 30));
    scrollPane.setBorder(BorderFactory.createEmptyBorder());
    tablePanel.add(scrollPane, BorderLayout.CENTER);

    panel.add(tablePanel, BorderLayout.CENTER);

    // Back button panel
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
    buttonPanel.setBackground(new Color(30, 30, 30));
    JButton backButton = createStyledButton("⬅️ Kembali");
    backButton.addActionListener(e -> cardLayout.show(cardPanel, "ManageSchedulesScreen"));
    buttonPanel.add(backButton);

    panel.add(buttonPanel, BorderLayout.SOUTH);

    cardPanel.add(panel, "ViewSchedulesScreen");
}


private static void createEditScheduleScreen() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    panel.setBackground(new Color(30, 30, 30));

    // Header
    JLabel headerLabel = new JLabel("🎬 EDIT JADWAL", SwingConstants.CENTER);
    headerLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
    headerLabel.setForeground(Color.WHITE);
    headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));
    panel.add(headerLabel, BorderLayout.NORTH);

    // Form panel
    JPanel formPanel = new JPanel(new GridLayout(7, 2, 15, 15));
    formPanel.setBorder(BorderFactory.createEmptyBorder(50, 150, 50, 150));
    formPanel.setBackground(new Color(30, 30, 30));

    JLabel idLabel = createStyledLabel("🔖 ID Jadwal:");
    JTextField idField = createStyledTextField();
    idField.setEditable(false);
    formPanel.add(idLabel);
    formPanel.add(idField);

    JLabel filmIdLabel = createStyledLabel("🎞️ ID Film:");
    JTextField filmIdField = createStyledTextField();
    formPanel.add(filmIdLabel);
    formPanel.add(filmIdField);

    JLabel dateLabel = createStyledLabel("📅 Tanggal (YYYY-MM-DD):");
    JTextField dateField = createStyledTextField();
    formPanel.add(dateLabel);
    formPanel.add(dateField);

    JLabel timeLabel = createStyledLabel("⏰ Waktu (HH:MM):");
    JTextField timeField = createStyledTextField();
    formPanel.add(timeLabel);
    formPanel.add(timeField);

    JLabel studioLabel = createStyledLabel("🎭 Studio:");
    JTextField studioField = createStyledTextField();
    formPanel.add(studioLabel);
    formPanel.add(studioField);

    JButton searchButton = createStyledButton("🔍 Cari");
    searchButton.addActionListener(e -> {
        try {
            int id = Integer.parseInt(idField.getText());
            BioskopData data = loadData();
            Optional<Schedule> schedule = data.schedules.stream()
                .filter(s -> s.id == id)
                .findFirst();

            if (schedule.isPresent()) {
                Schedule s = schedule.get();
                filmIdField.setText(String.valueOf(s.filmId));
                dateField.setText(s.date);
                timeField.setText(s.time);
                studioField.setText(s.studio);
            } else {
                JOptionPane.showMessageDialog(panel, "Jadwal dengan ID tersebut tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(panel, "ID harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    });
    formPanel.add(searchButton);

    JButton saveButton = createStyledButton("💾 Simpan");
    saveButton.addActionListener(e -> {
        try {
            int id = Integer.parseInt(idField.getText());
            int filmId = Integer.parseInt(filmIdField.getText());
            String date = dateField.getText();
            String time = timeField.getText();
            String studio = studioField.getText();

            if (!DATE_PATTERN.matcher(date).matches()) {
                JOptionPane.showMessageDialog(panel, "Format tanggal tidak valid! Gunakan YYYY-MM-DD", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!TIME_PATTERN.matcher(time).matches()) {
                JOptionPane.showMessageDialog(panel, "Format waktu tidak valid! Gunakan HH:MM", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (studio.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Studio tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            BioskopData data = loadData();
            if (data.films.stream().noneMatch(f -> f.id == filmId)) {
                JOptionPane.showMessageDialog(panel, "Film dengan ID tersebut tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Optional<Schedule> schedule = data.schedules.stream()
                .filter(s -> s.id == id)
                .findFirst();

            if (schedule.isPresent()) {
                Schedule s = schedule.get();
                s.filmId = filmId;
                s.date = date;
                s.time = time;
                s.studio = studio;
                saveData(data);
                
                JOptionPane.showMessageDialog(panel, "Jadwal berhasil diperbarui!", "Success", JOptionPane.INFORMATION_MESSAGE);
                cardLayout.show(cardPanel, "ManageSchedulesScreen");
            } else {
                JOptionPane.showMessageDialog(panel, "Jadwal dengan ID tersebut tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(panel, "ID Film harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(panel, "Gagal menyimpan data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    });
    formPanel.add(saveButton);

    JButton backButton = createStyledButton("↩️ Kembali");
    backButton.addActionListener(e -> cardLayout.show(cardPanel, "ManageSchedulesScreen"));
    formPanel.add(backButton);

    panel.add(formPanel, BorderLayout.CENTER);

    cardPanel.add(panel, "EditScheduleScreen");
}

private static void createDeleteScheduleScreen() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    panel.setBackground(new Color(30, 30, 30));

    // Header
    JLabel headerLabel = new JLabel("🎬 HAPUS JADWAL", SwingConstants.CENTER);
    headerLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
    headerLabel.setForeground(Color.WHITE);
    headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));
    panel.add(headerLabel, BorderLayout.NORTH);

    // Form panel
    JPanel formPanel = new JPanel(new GridLayout(3, 2, 15, 15));
    formPanel.setBorder(BorderFactory.createEmptyBorder(50, 150, 50, 150));
    formPanel.setBackground(new Color(30, 30, 30));

    JLabel idLabel = createStyledLabel("🔖 ID Jadwal:");
    JTextField idField = createStyledTextField();
    formPanel.add(idLabel);
    formPanel.add(idField);

    JButton searchButton = createStyledButton("🔍 Cari");
    searchButton.addActionListener(e -> {
        try {
            int id = Integer.parseInt(idField.getText());
            BioskopData data = loadData();
            Optional<Schedule> schedule = data.schedules.stream()
                .filter(s -> s.id == id)
                .findFirst();

            if (schedule.isPresent()) {
                Schedule s = schedule.get();
                Optional<Film> film = data.films.stream()
                    .filter(f -> f.id == s.filmId)
                    .findFirst();
                
                String filmTitle = film.isPresent() ? film.get().title : "Unknown";
                
                int confirm = JOptionPane.showConfirmDialog(
                    panel, 
                    """
                    Apakah Anda yakin ingin menghapus jadwal berikut?
                    Film: """ + filmTitle + "\n" +
                    "Tanggal: " + s.date + "\n" +
                    "Waktu: " + s.time + "\n" +
                    "Studio: " + s.studio,
                    "Konfirmasi Hapus",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    data.transactions.removeIf(t -> t.scheduleId == id);
                    saveData(data);
                    JOptionPane.showMessageDialog(panel, "Jadwal berhasil dihapus!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    cardLayout.show(cardPanel, "ManageSchedulesScreen");
                }
            } else {
                JOptionPane.showMessageDialog(panel, "Jadwal dengan ID tersebut tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(panel, "ID harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(panel, "Gagal menyimpan data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    });
    formPanel.add(searchButton);

    JButton backButton = createStyledButton("↩️ Kembali");
    backButton.addActionListener(e -> cardLayout.show(cardPanel, "ManageSchedulesScreen"));
    formPanel.add(backButton);

    panel.add(formPanel, BorderLayout.CENTER);

    cardPanel.add(panel, "DeleteScheduleScreen");
}

private static void createSalesReportScreen() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    panel.setBackground(new Color(30, 30, 30));

    // Header
    JLabel headerLabel = new JLabel("📊 LAPORAN PENJUALAN", SwingConstants.CENTER);
    headerLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
    headerLabel.setForeground(Color.WHITE);
    panel.add(headerLabel, BorderLayout.NORTH);

    // Report Panel
    JPanel reportPanel = new JPanel(new BorderLayout());
    reportPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    reportPanel.setBackground(new Color(30, 30, 30));

    BioskopData data = loadData();

    double totalSales = data.transactions.stream().mapToDouble(t -> t.totalPrice).sum();
    long totalTickets = data.transactions.stream().mapToInt(t -> t.quantity).sum();

    JTextArea reportText = new JTextArea();
    reportText.setEditable(false);
    reportText.setFont(new Font("Monospaced", Font.PLAIN, 13));
    reportText.setBackground(new Color(50, 50, 50));
    reportText.setForeground(Color.WHITE);
    reportText.setMargin(new Insets(10, 10, 10, 10));

    String borderLine = "------------------------------------------------------------";

    reportText.append("📌 LAPORAN PENJUALAN BSI CINEMA\n");
    reportText.append(borderLine + "\n");
    reportText.append(String.format("Total Transaksi     : %d\n", data.transactions.size()));
    reportText.append(String.format("Total Tiket Terjual : %d\n", totalTickets));
    reportText.append(String.format("Total Pendapatan    : Rp %,.2f\n", totalSales));
    reportText.append(borderLine + "\n");
    reportText.append("📋 DETAIL TRANSAKSI:\n\n");

    for (Transaction t : data.transactions) {
        Optional<Film> film = data.films.stream().filter(f -> f.id == t.filmId).findFirst();
        Optional<Schedule> schedule = data.schedules.stream().filter(s -> s.id == t.scheduleId).findFirst();

        String filmTitle = film.map(f -> f.title).orElse("Unknown");
        String scheduleTime = schedule.map(s -> s.date + " " + s.time).orElse("Unknown");

        reportText.append(String.format("🧾 ID Transaksi   : %d\n", t.id));
        reportText.append(String.format("🗓️  Tanggal       : %s\n", t.transactionDate));
        reportText.append(String.format("🎬 Film           : %s\n", filmTitle));
        reportText.append(String.format("⏰ Jadwal         : %s\n", scheduleTime));
        reportText.append(String.format("🎟️  Tiket         : %d\n", t.quantity));
        reportText.append(String.format("💺 Kursi          : %s\n", String.join(", ", t.selectedSeats)));
        reportText.append(String.format("💰 Total Harga    : Rp %,.2f\n", t.totalPrice));
        reportText.append(borderLine + "\n");
    }

    JScrollPane scrollPane = new JScrollPane(reportText);
    reportPanel.add(scrollPane, BorderLayout.CENTER);
    panel.add(reportPanel, BorderLayout.CENTER);

    // Button Panel
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    buttonPanel.setBackground(new Color(30, 30, 30));
    JButton backButton = createStyledButton("⬅️ Kembali");
    backButton.addActionListener(e -> cardLayout.show(cardPanel, "AdminDashboardScreen"));
    buttonPanel.add(backButton);
    panel.add(buttonPanel, BorderLayout.SOUTH);

    cardPanel.add(panel, "SalesReportScreen");
}

private static void createViewFilmSchedulesScreen() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBackground(new Color(30, 30, 30));
    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    // Header
    JLabel headerLabel = new JLabel("🎥 JADWAL FILM", SwingConstants.CENTER);
    headerLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
    headerLabel.setForeground(Color.WHITE);
    panel.add(headerLabel, BorderLayout.NORTH);

    // Table panel
    JPanel tablePanel = new JPanel(new BorderLayout());
    tablePanel.setBackground(new Color(30, 30, 30));
    tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    BioskopData data = loadData();
    String[] columnNames = {
        "ID", "Judul Film", "Genre", "Durasi", "Harga",
        "Tanggal", "Waktu", "Studio", "Kursi Tersedia"
    };

    List<Object[]> rowDataList = new ArrayList<>();
    for (Schedule schedule : data.schedules) {
        Optional<Film> filmOpt = data.films.stream()
            .filter(f -> f.id == schedule.filmId)
            .findFirst();

        if (filmOpt.isPresent()) {
            Film f = filmOpt.get();
            Object[] row = {
                schedule.id,
                f.title,
                f.genre,
                f.duration + " menit",
                String.format("Rp %,.2f", f.price),
                schedule.date,
                schedule.time,
                schedule.studio,
                schedule.availableSeats
            };
            rowDataList.add(row);
        }
    }

    Object[][] rowData = rowDataList.toArray(new Object[0][0]);

    JTable table = new JTable(rowData, columnNames) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    // Styling table agar konsisten dengan tema
    table.setBackground(new Color(50, 50, 50));
    table.setForeground(Color.WHITE);
    table.setFont(new Font("SansSerif", Font.PLAIN, 12));
    table.getTableHeader().setBackground(new Color(70, 70, 70));
    table.getTableHeader().setForeground(Color.WHITE);
    table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
    table.setRowHeight(25);

    JScrollPane scrollPane = new JScrollPane(table);
    tablePanel.add(scrollPane, BorderLayout.CENTER);
    panel.add(tablePanel, BorderLayout.CENTER);

    // Button panel untuk kembali
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
    buttonPanel.setBackground(new Color(30, 30, 30));

    JButton backButton = createStyledButton("⬅️ Kembali");
    backButton.addActionListener(e -> cardLayout.show(cardPanel, "UserDashboardScreen"));
    buttonPanel.add(backButton);

    panel.add(buttonPanel, BorderLayout.SOUTH);

    cardPanel.add(panel, "ViewFilmSchedulesScreen");
}



private static void createPurchaseHistoryScreen() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    panel.setBackground(new Color(30, 30, 30)); // Background gelap

    // Header
    JLabel headerLabel = new JLabel("🧾 RIWAYAT PEMBELIAN", SwingConstants.CENTER);
    headerLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
    headerLabel.setForeground(Color.WHITE);
    panel.add(headerLabel, BorderLayout.NORTH);

    // History panel
    JPanel historyPanel = new JPanel(new BorderLayout());
    historyPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    historyPanel.setBackground(new Color(30, 30, 30));

    BioskopData data = loadData();
    List<Transaction> userTransactions = data.transactions.stream()
        .filter(t -> t.username.equals(currentUser))
        .collect(Collectors.toList());

    if (userTransactions.isEmpty()) {
        JLabel noDataLabel = new JLabel("⚠️ Anda belum memiliki riwayat pembelian.", SwingConstants.CENTER);
        noDataLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        noDataLabel.setForeground(Color.LIGHT_GRAY);
        historyPanel.add(noDataLabel, BorderLayout.CENTER);
    } else {
        String[] columnNames = {"ID", "Film", "Tanggal", "Waktu", "Studio", "Jumlah", "Kursi", "Total"};
        Object[][] rowData = new Object[userTransactions.size()][8];

        for (int i = 0; i < userTransactions.size(); i++) {
            Transaction t = userTransactions.get(i);
            Optional<Film> film = data.films.stream()
                .filter(f -> f.id == t.filmId)
                .findFirst();
            Optional<Schedule> schedule = data.schedules.stream()
                .filter(s -> s.id == t.scheduleId)
                .findFirst();

            String filmTitle = film.map(f -> f.title).orElse("Unknown");
            String scheduleDate = schedule.map(s -> s.date).orElse("Unknown");
            String scheduleTime = schedule.map(s -> s.time).orElse("Unknown");
            String studio = schedule.map(s -> s.studio).orElse("Unknown");

            rowData[i][0] = t.id;
            rowData[i][1] = filmTitle;
            rowData[i][2] = scheduleDate;
            rowData[i][3] = scheduleTime;
            rowData[i][4] = studio;
            rowData[i][5] = t.quantity;
            rowData[i][6] = String.join(", ", t.selectedSeats);
            rowData[i][7] = String.format("Rp %,.2f", t.totalPrice);
        }

        JTable table = new JTable(rowData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Supaya tabel tidak bisa diedit
            }
        };

        // Styling table
        table.setBackground(new Color(50, 50, 50));
        table.setForeground(Color.WHITE);
        table.setFont(new Font("SansSerif", Font.PLAIN, 12));
        table.setRowHeight(25);
        table.getTableHeader().setBackground(new Color(70, 70, 70));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(table);
        historyPanel.add(scrollPane, BorderLayout.CENTER);
    }

    panel.add(historyPanel, BorderLayout.CENTER);

    // Button panel
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
    buttonPanel.setBackground(new Color(30, 30, 30));

    JButton backButton = createStyledButton("⬅️ Kembali");
    backButton.addActionListener(e -> cardLayout.show(cardPanel, "UserDashboardScreen")); // Fixed screen name
    buttonPanel.add(backButton);

    panel.add(buttonPanel, BorderLayout.SOUTH);

    cardPanel.add(panel, "PurchaseHistoryScreen");
  }
}