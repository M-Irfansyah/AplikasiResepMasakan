import java.sql.Connection;
import java.sql.DriverManager;
import static java.sql.DriverManager.getConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List; 


public class Resep {
    private Connection conn;

    // Konstruktor untuk membuat koneksi ke database
    public Resep() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:Resep.db");
            createTable(); // Membuat tabel jika belum ada
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method untuk membuat tabel "resep" jika belum ada
    private void createTable() {
    String sqlCreateTable = "CREATE TABLE IF NOT EXISTS resep ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "nama TEXT NOT NULL, "
            + "bahan TEXT NOT NULL, "
            + "langkah TEXT NOT NULL, "
            + "kategori TEXT NOT NULL, "
            + "gambar BLOB)"; // Tambahkan kolom gambar
            
    try (Statement stmt = conn.createStatement()) {
        stmt.execute(sqlCreateTable);
        System.out.println("Tabel resep berhasil dibuat atau sudah ada.");
    } catch (SQLException e) {
        e.printStackTrace();
    }
    }

    // Method untuk menambahkan resep baru
    public void tambahResep(String nama, String bahan, String langkah, String kategori, String gambar) {
        String sqlInsert = "INSERT INTO resep (nama, bahan, langkah, kategori, gambar) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sqlInsert)) {
            pstmt.setString(1, nama);
            pstmt.setString(2, bahan);
            pstmt.setString(3, langkah);
            pstmt.setString(4, kategori);
            pstmt.setString(5, gambar); // Simpan path gambar
            pstmt.executeUpdate();
            System.out.println("Resep berhasil ditambahkan.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method untuk memperbarui path gambar berdasarkan ID resep
    public void simpanPathGambar(int id, String imagePath) {
        String sqlUpdate = "UPDATE resep SET gambar = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdate)) {
            pstmt.setString(1, imagePath);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            System.out.println("Path gambar berhasil diperbarui.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method untuk menghapus resep berdasarkan ID
    public void hapusResep(int id) {
        String sqlDelete = "DELETE FROM resep WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sqlDelete)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Resep berhasil dihapus.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method untuk memperbarui data resep berdasarkan ID
    public void updateResep(int id, String nama, String bahan, String langkah, String kategori) {
        String sqlUpdate = "UPDATE resep SET nama = ?, bahan = ?, langkah = ?, kategori = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdate)) {
            pstmt.setString(1, nama);
            pstmt.setString(2, bahan);
            pstmt.setString(3, langkah);
            pstmt.setString(4, kategori);
            pstmt.setInt(5, id);
            pstmt.executeUpdate();
            System.out.println("Resep berhasil diperbarui.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method untuk mengambil semua data resep dari database
    public List<String[]> getResepList() {
        List<String[]> resepList = new ArrayList<>();
        String sqlSelect = "SELECT * FROM resep";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlSelect)) {
            while (rs.next()) {
                resepList.add(new String[]{
                        String.valueOf(rs.getInt("id")),
                        rs.getString("nama"),
                        rs.getString("bahan"),
                        rs.getString("langkah"),
                        rs.getString("kategori"),
                        rs.getString("gambar") != null ? rs.getString("gambar") : "Tidak ada gambar"
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resepList;
    }

    // Method untuk mendapatkan koneksi ke database (jika diperlukan di masa depan)
    private Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:sqlite:Resep.db");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
