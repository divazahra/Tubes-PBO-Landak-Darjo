import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class PengelolaRental {
    private static DatabaseConnection dbConnection;
    private String userId;
    private String password;
    private String username;
    private String email;
    

    public PengelolaRental() {
        dbConnection = new DatabaseConnection();
    }

    public boolean login(String username, String password) {
        String query = "SELECT * FROM user WHERE username = ? AND password = ?";
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                this.userId = rs.getString("userId");
                this.username = rs.getString("username");
                this.email = rs.getString("email");
                return true;
            }

            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void register(String username, String password, String email) {
        String query = "INSERT INTO user (username, password, email) VALUES (?, ?, ?)";
        try (Connection conn = dbConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, email);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Registrasi berhasil!");
            } else {
                System.out.println("Registrasi gagal.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewPendapatanInput() {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Masukkan tanggal (format: YYYY-MM-DD): ");
        String inputTanggal = scanner.nextLine();

        try {
            Connection conn = dbConnection.getConnection();
            String sql = "SELECT SUM(totalHarga) AS totalPendapatan FROM datarental WHERE tanggal = ? AND userId = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, inputTanggal);
            pstmt.setString(2, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                double totalPendapatan = rs.getDouble("totalPendapatan");
                System.out.println("Pendapatan pada tanggal " + inputTanggal + ": Rp " + totalPendapatan);
            } else {
                System.out.println("Tidak ada data pendapatan pada tanggal " + inputTanggal);
            }

            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.err.println("Gagal mengambil data pendapatan: " + e.getMessage());
        }
    }

    public void viewPendapatan10Hari() {
        try {
            Connection conn = dbConnection.getConnection();
            String sql = "SELECT tanggal, SUM(totalHarga) AS totalPendapatan " +
                    "FROM datarental " +
                    "WHERE tanggal >= CURDATE() - INTERVAL 10 DAY AND userId = ? " +
                    "GROUP BY tanggal " +
                    "ORDER BY tanggal DESC";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("\n==== Pendapatan Harian 10 Hari Terakhir ====");
            boolean data = false;
            while (rs.next()) {
                data = true;
                String tanggalRental = rs.getDate("tanggal").toString();
                double totalPendapatan = rs.getDouble("totalPendapatan");

                System.out.println("Tanggal: " + tanggalRental + " | Pendapatan: Rp " + totalPendapatan);
            }

            if (!data) {
                System.out.println("Tidak ada data pendapatan dalam 10 hari terakhir");
            }

            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.err.println("Gagal mengambil data pendapatan: " + e.getMessage());
        }
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}