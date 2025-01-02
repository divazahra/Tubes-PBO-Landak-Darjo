import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class KelolaRental implements CRUD {

    private static DatabaseConnection dbConnection;
    private String userId;
    private boolean hasData = false;

    public boolean hasData() {
        return hasData;
    }

    public int hargaVR(String extraVRDevice) {
        return ("iya".equalsIgnoreCase(extraVRDevice) ? 10000 : 0);
    }

    public KelolaRental(String userId) {
        this.userId = userId;
        dbConnection = new DatabaseConnection();
    }

    @Override
    public void create(String details) {
        Scanner scanner = new Scanner(System.in);
        String[] parts = details.split(",");
        String namaKustomer = parts[0];
        String tipePS = parts[1];
        String psId = parts[2];
        int jam = Integer.parseInt(parts[3]);
        int extraController = 0;
        String extraVRDevice = "tidak";
        double totalHarga = 0;
        

        try {
            Connection conn = dbConnection.getConnection();

            String psCheckQuery = "SELECT type, status FROM ps WHERE id = ? AND userId = ?";
            PreparedStatement psCheckStmt = conn.prepareStatement(psCheckQuery);
            psCheckStmt.setString(1, psId);
            psCheckStmt.setString(2, userId);
            ResultSet rs = psCheckStmt.executeQuery();

            if (rs.next()) {
                String tipePs = rs.getString("type");
                String status = rs.getString("status");

                if (!status.equals("available")) {
                    System.out.println("PS dengan ID " + psId + " sedang tidak tersedia.");
                    return;
                }

                if (tipePs.equals("PS4") || tipePs.equals("PS5")) {
                    while (true) {
                        System.out.print("Apakah anda ingin ekstra controller? (Maksimal 2): ");
                        String inputExtra = scanner.nextLine();
                        try {
                            extraController = Integer.parseInt(inputExtra);
                            if (extraController >= 0 && extraController <= 2) {
                                break;
                            } else {
                                System.out.println("Input tidak valid. Masukkan angka antara 0 hingga 2");
                            }
                        } catch (NumberFormatException e) {
                        System.out.println("Input tidak valid. Masukkan angka antara 0 hingga 2.");
                        } 
                    }

                    if (tipePs.equals("PS5")) {
                        while (true) {
                            System.out.print("Apakah anda ingin meminjam VR device? (iya/tidak): ");
                            extraVRDevice = scanner.nextLine();
                            if (extraVRDevice.equals("iya") || extraVRDevice.equals("tidak")) {
                                break;
                            } else {
                                System.out.println("Input tidak valid. Masukkan 'iya' atau 'tidak'");
                            }
                        }
                    }
                }

                switch (tipePS) {
                case "PS3":
                    PS ps3 = new PS3(psId);
                    totalHarga = ps3.hitungHarga(jam);
                    break;
                case "PS4":
                    PS ps4 = new PS4(psId, extraController);
                    totalHarga = ps4.hitungHarga(jam);
                    break;
                case "PS5":
                    PS ps5 = new PS5(psId, extraController, extraVRDevice);
                    totalHarga = ps5.hitungHarga(jam);
                    break;
                default:
                    System.out.println("Tipe PS tidak valid: " + tipePS);
                    return;
            }

                String insertRentalQuery = "INSERT INTO rental (namaKustomer, psId, jam, extraController, extraVRDevice, totalHarga, userId, tanggal) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement insertRentalStmt = conn.prepareStatement(insertRentalQuery);

                LocalDate today = LocalDate.now();
                
                insertRentalStmt.setString(1, namaKustomer);
                insertRentalStmt.setString(2, psId);
                insertRentalStmt.setInt(3, jam);
                insertRentalStmt.setInt(4, extraController);
                insertRentalStmt.setString(5, extraVRDevice);
                insertRentalStmt.setDouble(6, totalHarga);
                insertRentalStmt.setString(7, userId);
                insertRentalStmt.setDate(8, java.sql.Date.valueOf(today));
                
                String insertDataRentalQuery = "INSERT INTO dataRental (namaKustomer, psId, jam, extraController, extraVRDevice, totalHarga, userId, tanggal) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement insertDataRentalStmt = conn.prepareStatement(insertDataRentalQuery);
                insertDataRentalStmt.setString(1, namaKustomer);
                insertDataRentalStmt.setString(2, psId);
                insertDataRentalStmt.setInt(3, jam);
                insertDataRentalStmt.setInt(4, extraController);
                insertDataRentalStmt.setString(5, extraVRDevice);
                insertDataRentalStmt.setDouble(6, totalHarga);
                insertDataRentalStmt.setString(7, userId);
                insertDataRentalStmt.setDate(8, java.sql.Date.valueOf(today));
                
                int rowsInserted = insertRentalStmt.executeUpdate();
                int rowsInsertedDataRental = insertDataRentalStmt.executeUpdate();
                if (rowsInserted > 0 && rowsInsertedDataRental > 0) {
                    System.out.println("Rental berhasil ditambahkan ke database!");

                    String updateStatusQuery = "UPDATE ps SET status = 'not available' WHERE id = ? AND userId = ?";
                    PreparedStatement updateStatusStmt = conn.prepareStatement(updateStatusQuery);
                    updateStatusStmt.setString(1, psId);
                    updateStatusStmt.setString(2, userId);
                    updateStatusStmt.executeUpdate();
                    updateStatusStmt.close();
                }

                insertRentalStmt.close();
            } else {
                System.out.println("PS ID tidak ditemukan.");
            }

            rs.close();
            psCheckStmt.close();
        } catch (SQLException e) {
            System.err.println("Gagal menyimpan data rental ke database: " + e.getMessage());
        }
    }

    @Override
    public void delete(String details) {
        String rentalId = details;

        try {
            Connection conn = dbConnection.getConnection();

            String selectRentalQuery = "SELECT psId FROM rental WHERE rentalId = ? AND userId = ?";
            PreparedStatement selectRentalStmt = conn.prepareStatement(selectRentalQuery);
            selectRentalStmt.setString(1, rentalId);
            selectRentalStmt.setString(2, userId);
            ResultSet rs = selectRentalStmt.executeQuery();

            if (rs.next()) {
                String psId = rs.getString("psId");

                String deleteRentalQuery = "DELETE FROM rental WHERE rentalId = ? AND userId = ?";
                PreparedStatement deleteRentalStmt = conn.prepareStatement(deleteRentalQuery);
                deleteRentalStmt.setString(1, rentalId);
                deleteRentalStmt.setString(2, userId);

                int rowsDeleted = deleteRentalStmt.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("Rental selesai");

                    String updateStatusQuery = "UPDATE ps SET status = 'available' WHERE id = ? AND userId = ?";
                    PreparedStatement updateStatusStmt = conn.prepareStatement(updateStatusQuery);
                    updateStatusStmt.setString(1, psId);
                    updateStatusStmt.setString(2, userId);
                    updateStatusStmt.executeUpdate();
                    updateStatusStmt.close();
                }

                deleteRentalStmt.close();
            } else {
                System.out.println("Rental ID tidak ditemukan.");
            }

            rs.close();
            selectRentalStmt.close();
        } catch (SQLException e) {
            System.err.println("Gagal menghapus data rental dari database: " + e.getMessage());
        }
    }

    @Override
    public void view() {
        try {
            Connection conn = dbConnection.getConnection();
            String sql = "SELECT * FROM rental WHERE userId = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();;

            if (!rs.isBeforeFirst()) {
                System.out.println("Tidak ada data rental yang ditemukan");
            } else {
                while (rs.next()) {
                    String id = rs.getString("rentalId");
                    String customerName = rs.getString("namaKustomer");
                    String psId = rs.getString("psId");
                    int hoursRented = rs.getInt("jam");
                    int extraController = rs.getInt("extraController");
                    String extraVrDevice = rs.getString("extraVRDevice");
                    int totalPrice = rs.getInt("totalHarga");
                    String tanggalRental = rs.getDate("tanggal").toString();

                    System.out.println("ID Rental       : " + id);
                    System.out.println("Nama Customer   : " + customerName);
                    System.out.println("ID PS           : " + psId);
                    System.out.println("Jam Sewa        : " + hoursRented + " jam");
                    System.out.println("Tanggal Rental  : " + tanggalRental);
                    System.out.println();
                    System.out.println("Tambahan");
                    System.out.println("Controller      : " + extraController);
                    System.out.println("VR Device       : " + extraVrDevice);
                    System.out.println();
                    System.out.println("Total Harga     : " + totalPrice);
                    System.out.println("---------------------------");
                }
            }

            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.err.println("Gagal mengambil data rental: " + e.getMessage());
        }
    }

    public void viewPSRental() {
        try {
            Connection conn = dbConnection.getConnection();
            String sql = "SELECT * FROM rental WHERE userId = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            hasData = rs.isBeforeFirst();
    
            if (!rs.isBeforeFirst()) {
                System.out.print("Tidak ada data rental");
            } else {
                while (rs.next()) {
                    String id = rs.getString("rentalId");
                    String idPs = rs.getString("psId");
    
                    System.out.println("ID Rental: " + id);
                    System.out.println("ID Ps: " + idPs);
    
                    System.out.println("------------");
                }
            }
    
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.err.println("Gagal mengambil data PS: " + e.getMessage());
        }
    }
}