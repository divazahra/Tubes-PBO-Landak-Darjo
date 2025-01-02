import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PengelolaRental pengelolaRental = new PengelolaRental();

        while (true) {
            System.out.println("==== Rental PS Management ====");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Pilih menu: ");
            int pilihan = scanner.nextInt();
            scanner.nextLine();

            if (pilihan == 1) {
                System.out.print("Masukkan Username: ");
                String username = scanner.nextLine();
                System.out.print("Masukkan Password: ");
                String password = scanner.nextLine();

                if (pengelolaRental.login(username, password)) {
                    
                    String userId = pengelolaRental.getUserId();
                    System.out.println("Login berhasil. Selamat datang, " + pengelolaRental.getUsername());
                    
                    KelolaPS kelolaPs = new KelolaPS(userId);
                    KelolaRental kelolaRental = new KelolaRental(userId);
                    mainMenu(kelolaPs, kelolaRental, pengelolaRental, scanner);
                    break;
                } else {
                    System.out.println("Username atau password salah");
                    System.out.println();
                }
            } else if (pilihan == 2) {
                System.out.print("Masukkan Username Baru: ");
                String newUsername = scanner.nextLine();
                System.out.print("Masukkan Password Baru: ");
                String newPassword = scanner.nextLine();
                System.out.print("Masukkan Email: ");
                String email = scanner.nextLine();

                PengelolaRental.register(newUsername, newPassword, email);
            } else if (pilihan == 3) {
                System.out.println("Terima kasih telah menggunakan Rental PS Management");
                break;
            } else {
                System.out.println("Pilihan tidak valid");
            }
        }
    }



    public static void mainMenu(KelolaPS kelolaPS, KelolaRental kelolaRental, PengelolaRental pengelolaRental, Scanner scanner) {

        while (true) {
            System.out.println("\n==== Menu Utama ====");
            System.out.println("1. Kelola Data PS");
            System.out.println("2. Kelola Data Rental");
            System.out.println("3. Lihat Pendapatan Harian");
            System.out.println("4. Logout");
            System.out.print("Pilih menu:  ");
            int pilihan = scanner.nextInt();
            scanner.nextLine();

            if (pilihan == 1) {
                kelolaDataPS(kelolaPS, scanner);
            } else if (pilihan == 2) {
                kelolaDataRental(kelolaRental, kelolaPS, scanner);
            } else if (pilihan == 3) {
                System.out.println("\n==== Lihat Pendapatan Harian ====");
                System.out.println("1. Lihat Pendapatan 10 Hari Terakhir");
                System.out.println("2. Lihat Pendapatan Berdasarkan Tanggal");
                System.out.print("Pilih opsi: ");
                int pilihan2 = scanner.nextInt();
                scanner.nextLine();

                switch (pilihan2) {
                    case 1:
                        pengelolaRental.viewPendapatan10Hari();
                        break;
                    case 2:
                        pengelolaRental.viewPendapatanInput();
                        break;
                    default:
                        System.out.println("Pilihan tidak valid");
                        break;
                }
                mainMenu(kelolaPS, kelolaRental, pengelolaRental, scanner);
                break;
            } else if (pilihan == 4) {
                System.out.println("Logout berhasil. Sampai jumpa " + pengelolaRental.getUsername());
                break;
            } else {
                System.out.println("Pilihan tidak valid");
            }
        }
    }

    public static void kelolaDataPS(KelolaPS kelolaPS, Scanner scanner) {
        while (true) {
            System.out.println("\n==== Kelola Data PS ====");
            System.out.println("1. Tambah PS");
            System.out.println("2. Hapus PS");
            System.out.println("3. Lihat PS");
            System.out.println("4. Kembali ke Menu Utama");
            System.out.print("Pilih menu: ");
            int pilihan = scanner.nextInt();
            scanner.nextLine();

            if (pilihan == 1) {
                System.out.println("\n=== Tambah PS ===");
                kelolaPS.view();
                System.out.print("Masukkan tipe PS (PS3, PS4, PS5): ");
                String tipePs = scanner.nextLine();
                if (tipePs.equals("-")) {
                    System.out.println("Proses tambah PS dibatalkan, kembali ke Kelola Data PS...");
                    kelolaDataPS(kelolaPS, scanner);
                }

                if (!tipePs.equals("PS3") && !tipePs.equals("PS4") && !tipePs.equals("PS5")) {
                    System.out.println("Tipe PS tidak valid. Kembali ke Kelola PS...");
                    continue;
                }

                System.out.print("Masukkan ID PS (contoh: PS4-01): ");
                String id = scanner.nextLine();
                String details = tipePs + "," + id;
                kelolaPS.create(details);

            } else if (pilihan == 2) {
                System.out.println("\n=== Hapus PS ===");
                kelolaPS.view();

                if (!kelolaPS.hasData()) {
                    System.out.println(", kembali ke Kelola Data PS...");
                    continue;
                }

                System.out.print("Masukkan ID PS yang ingin dihapus: ");
                String id = scanner.nextLine();
                if (id.equals("-")) {
                    System.out.println("Proses hapus PS dibatalkan, kembali ke Kelola Data PS...");
                    kelolaDataPS(kelolaPS, scanner);
                }
                kelolaPS.delete(id);

            } else if (pilihan == 3) {
            System.out.println("\n=== Daftar PS ===");
                kelolaPS.view();

            } else if (pilihan == 4) {
                break;

            } else {
                System.out.println("Pilihan tidak valid");
            }
        }
    }

    public static void kelolaDataRental(KelolaRental kelolaRental, KelolaPS kelolaPS, Scanner scanner) {
        while (true) {
            System.out.println("\n==== Kelola Data Rental ====");
            System.out.println("1. Tambah Rental");
            System.out.println("2. Rental Selesai");
            System.out.println("3. Lihat Rental");
            System.out.println("4. Kembali ke Menu Utama");
            System.out.print("Pilih menu: ");
            int pilihan = scanner.nextInt();
            scanner.nextLine();

            if (pilihan == 1) {
                System.out.println("\n=== Tambah Rental ===");
                    System.out.print("Masukkan nama pelanggan: ");
                    String namaKustomer = scanner.nextLine();
                    if (namaKustomer.equals("-")) {
                        System.out.println("Proses tambah Rental dibatalkan, kembali ke Kelola Data Rental...");
                        kelolaDataRental(kelolaRental, kelolaPS, scanner);
                    }
                    System.out.print("Masukkan tipe PS: ");
                    String tipePs = scanner.nextLine();

                    if (!tipePs.equals("PS3") && !tipePs.equals("PS4") && !tipePs.equals("PS5")) {
                        System.out.println("Tipe PS tidak valid. Kembali ke Kelola Rental...");
                        continue;
                    }

                    if (!kelolaPS.viewAvailable(tipePs)) {
                        System.out.println("Tidak ada " + tipePs + " yang tersedia saat ini. Kembali ke Kelola Rental...");
                        continue;
                    }

                    System.out.print("Masukkan ID PS yang akan dirental: ");
                    String psId = scanner.nextLine();

                    System.out.print("Masukkan durasi rental (jam): ");
                    int jam = scanner.nextInt();
                    scanner.nextLine();

                    String details = namaKustomer + "," + tipePs + "," + psId + "," + jam;

                    kelolaRental.create(details);

            } else if (pilihan == 2) {
                System.out.println("\n=== Rental Selesai ===");
                kelolaRental.viewPSRental();

                if (!kelolaRental.hasData()) {
                    System.out.println(", kembali ke Kelola Rental...");
                    continue;
                }

                System.out.print("Masukkan ID Rental yang sudah selesai: ");
                String rentalId = scanner.nextLine();
                if (rentalId.equals("-")) {
                    System.out.println("Proses tambah Rental dibatalkan, kembali ke Kelola Data Rental...");
                    kelolaDataRental(kelolaRental, kelolaPS, scanner);
                }
                kelolaRental.delete(rentalId);

            } else if (pilihan == 3) {
                System.out.println("\n=== Daftar Rental ===");
                    kelolaRental.view();

            } else if (pilihan == 4) {
                break;

            } else {
                System.out.println("Pilihan tidak valid");
            }
        }
    }
}