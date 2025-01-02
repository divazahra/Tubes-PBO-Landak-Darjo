import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class KelolaPS implements CRUD {

    private static DatabaseConnection dbConnection;
    private String userId;
    private boolean hasData = false;
    private PS ps = null;
    public boolean hasData() {
        return hasData;
    }

    public KelolaPS(String userId) {
        this.userId = userId;
        dbConnection = new DatabaseConnection();
    }

    @Override
    public void create(String details) {
        String[] parts = details.split(",");
        String tipePs = parts[0];
        String id = parts[1];
        int extraController = 2;
        String extraVRDevice = "iya";
        String status = "available";

        switch (tipePs) {
            case "PS3":
                ps = new PS3(id);
                break;
            case "PS4":
                ps = new PS4(id, extraController);
                break;
            case "PS5":
                ps = new PS5(id, extraController, extraVRDevice);
                break;
        }

        if (ps != null) {
            try {
                Connection conn = dbConnection.getConnection();
                String checkSql = "SELECT COUNT(*) AS count FROM ps WHERE id = ? AND userId = ?";
                PreparedStatement checkStmt = conn.prepareStatement(checkSql);
                checkStmt.setString(1, id);
                checkStmt.setString(2, userId);
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next() && rs.getInt("count") > 0) {
                    System.out.println("PS dengan ID " + id + " sudah ada");
                    return;
                }

                String sql = "INSERT INTO ps (id, type, extraController, extraVRDevice, status, userId) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, id);
                pstmt.setString(2, tipePs);
                pstmt.setInt(3, extraController);
                pstmt.setString(4, extraVRDevice);
                pstmt.setString(5, status);
                pstmt.setString(6, userId);

                int rowsInserted = pstmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("PS berhasil ditambahkan");
                }

                pstmt.close();
            } catch (SQLException e) {
                System.err.println("Gagal menyimpan data PS ke database: " + e.getMessage());
            }
        } else {
            System.out.println("Tipe PS tidak valid.");
        }
    }

    @Override
    public void delete(String details) {
        String id = details;

        try {
            Connection conn = dbConnection.getConnection();
            String checkSql = "SELECT * FROM ps WHERE id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, id);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                String deleteSql = "DELETE FROM ps WHERE id = ?";
                PreparedStatement deleteStmt = conn.prepareStatement(deleteSql);
                deleteStmt.setString(1, id);

                int rowsDeleted = deleteStmt.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("Data PS berhasil dihapus!");
                }

                deleteStmt.close();
            } else {
                System.out.println("ID PS tidak ditemukan.");
            }

            rs.close();
            checkStmt.close();
        } catch (SQLException e) {
            System.err.println("Terjadi kesalahan saat menghapus data PS: " + e.getMessage());
        }
    }

    @Override
    public void view() {
        try {
            Connection conn = dbConnection.getConnection();
            String sql = "SELECT * FROM ps WHERE userId = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();

            hasData = rs.isBeforeFirst();

            if (!rs.isBeforeFirst()) {
                System.out.print("Tidak ada data PS yang ditemukan");
            } else {
                while (rs.next()) {
                    String id = rs.getString("id");
                    String tipePs = rs.getString("type");

                    System.out.println("ID: " + id);
                    System.out.println("Tipe: " + tipePs);

                    System.out.println("------------");
                }
            }

            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.err.println("Gagal mengambil data PS: " + e.getMessage());
        }
    }

    public boolean viewAvailable(String tipePS) {
        boolean hasData = false;
        try {
            Connection conn = dbConnection.getConnection();
            String sql = "SELECT * FROM ps WHERE status = 'available' AND type = ? AND userId = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, tipePS);
            pstmt.setString(2, userId);

            ResultSet rs = pstmt.executeQuery();

            if (!rs.isBeforeFirst()) {
                System.out.println("");
            } else {
                hasData = true;
                System.out.println("\n==== " + tipePS + " Tersedia ====");
                while (rs.next()) {
                    String id = rs.getString("id");

                    System.out.println("ID: " + id);
                    System.out.println("------------");
                }
            }

            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.err.println("Gagal mengambil data PS: " + e.getMessage());
        }

        return hasData;
    }
}