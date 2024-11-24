# AplikasiResepMasakan
 UTS-M.Irfansyah-2210010176
 
**Deskripsi Program**  
AplikasiResepMasakan adalah aplikasi berbasis Java Swing yang dirancang untuk mengelola dan berbagi resep masakan. Aplikasi ini memungkinkan pengguna untuk menambahkan, mengedit, menghapus, dan mencari resep. Selain itu, aplikasi mendukung fitur pembuatan laporan resep dalam format CSV.

---

## **Komponen-Komponen GUI**  
Aplikasi ini menggunakan berbagai komponen GUI untuk memberikan pengalaman pengguna yang interaktif dan intuitif:

1. Frame  
   - Berfungsi sebagai jendela utama aplikasi.

2. **JPanel**  
   - Digunakan untuk mengorganisasi tata letak komponen antarmuka.

3. **JLabel**  
   - Menampilkan label teks untuk input, seperti *"Nama Resep"*, *"Bahan"*, *"Langkah-langkah"*.

4. **JTextField**  
   - Input teks sederhana untuk data seperti nama resep dan kategori.

5. **JTextArea**  
   - Input atau menampilkan teks yang lebih panjang, seperti daftar bahan dan langkah-langkah resep.

6. **JTable**  
   - Menampilkan daftar resep dalam bentuk tabel dengan kolom:
     - Nama Resep
     - Bahan Masakan
     - Langkah-langkah Masakan
     - Kategori Masakan
     - Gambar Masakan

7. **JButton**  
   - Menyediakan tombol untuk berbagai fungsi, termasuk:
     - Tambah resep
     - Edit resep
     - Hapus resep
     - Cari resep
     - Sortir resep berdasarkan nama atau kategori
     - Ekspor data resep
     - Buat laporan dalam format TXT dan HTML

8. **JFileChooser**  
   - Memudahkan pengguna untuk memilih lokasi file saat menyimpan laporan atau ekspor resep.

## Penjelasan Kode

- Button Tambah
```
 private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {                                          
    String nama = jNama.getText();
        String bahan = jtBahan.getText();
        String langkah = jtLangkah.getText();
        String kategori = (String) jcbList.getSelectedItem();
        if (!nama.isEmpty() && !bahan.isEmpty() && !langkah.isEmpty() && kategori != null) {
            dbHelper.tambahResep(nama, bahan, langkah, kategori, "");
            loadResepData();
            jNama.setText("");
            jtBahan.setText("");
            jtLangkah.setText("");
            jcbList.setSelectedIndex(0);
            lblGambar.setIcon(null);
        } else {
            JOptionPane.showMessageDialog(this, "Isi semua data resep!");
        }
    }                     
```
- Button Ubah
```
private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {                                        
            int selectedRow = tblResep.getSelectedRow();
        if (selectedRow != -1) {
            int id = Integer.parseInt((String) tblResep.getValueAt(selectedRow, 0));
            String nama = jNama.getText();
            String bahan = jtBahan.getText();
            String langkah = jtLangkah.getText();
            String kategori = (String) jcbList.getSelectedItem();
            if (!nama.isEmpty() && !bahan.isEmpty() && !langkah.isEmpty() && kategori != null) {
                dbHelper.updateResep(id, nama, bahan, langkah, kategori);
                loadResepData();
                JOptionPane.showMessageDialog(this, "Resep berhasil diperbarui!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih resep yang ingin diubah!");
        }

    }                   
```
- Button Hapus
```
private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {                                         
    int selectedRow = tblResep.getSelectedRow();
        if (selectedRow != -1) {
            int id = Integer.parseInt((String) tblResep.getValueAt(selectedRow, 0));
            dbHelper.hapusResep(id);
            loadResepData();
        } else {
            JOptionPane.showMessageDialog(this, "Pilih resep yang ingin dihapus!");
        }
    }               
```
- Button cari
```
private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {                                        
   String keyword = jCari.getText();
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Nama", "Bahan", "Langkah", "Kategori", "Gambar"}, 0);
        dbHelper.getResepList().forEach(resep -> {
            if (resep[1].contains(keyword) || resep[2].contains(keyword)) {
                model.addRow(resep);
            }
        });
        tblResep.setModel(model);
    } 
```
- Button Ekxpor
```
btnEkspor.addActionListener(e -> eksporKeCSV())
```
- Button Import
```
btnImport.addActionListener(e -> imporDariCSV());
```
- Menambahkan ListSelectionListener untuk memilih resep di tabel 

```
    tblResep.getSelectionModel().addListSelectionListener(e -> {
        if (!e.getValueIsAdjusting() && tblResep.getSelectedRow() != -1) {
            int selectedRow = tblResep.getSelectedRow();
            jNama.setText((String) tblResep.getValueAt(selectedRow, 1)); // Menampilkan nama resep
            jtBahan.setText((String) tblResep.getValueAt(selectedRow, 2)); // Menampilkan bahan
            jtLangkah.setText((String) tblResep.getValueAt(selectedRow, 3)); // Menampilkan langkah
            jcbList.setSelectedItem((String) tblResep.getValueAt(selectedRow, 4)); // Menampilkan kategori
            
            // Menampilkan gambar
            String gambar = (String) tblResep.getValueAt(selectedRow, 5); // Menampilkan gambar resep
            if (gambar != null && !gambar.isEmpty()) {
                lblGambar.setIcon(new ImageIcon(new ImageIcon(gambar).getImage()
                        .getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
            } else {
                lblGambar.setIcon(null); // Jika gambar tidak ada, kosongkan ikon
            }
        }
    });
    
    btnUnggah.addActionListener(e -> unggahGambar()); // Menangani upload gambar
}
```


- Method pilihGambar : untuk memilih gambar dan mengonversinya menjadi array byte untuk disimpan ke database.

```
private byte[] pilihGambar() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Gambar", "jpg", "png", "jpeg"));
    int result = fileChooser.showOpenDialog(this);
    
    if (result == JFileChooser.APPROVE_OPTION) {
        File file = fileChooser.getSelectedFile();
        try (FileInputStream fis = new FileInputStream(file)) {
            lblGambar.setIcon(new ImageIcon(new ImageIcon(file.getPath()).getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
            return fis.readAllBytes(); // Mengembalikan data gambar dalam bentuk byte array
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal membaca file gambar!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    return null;
}
```


- Method eksporKeCSV : Mengekspor daftar resep ke file CSV dengan format yang telah ditentukan.

```
private void eksporKeCSV() {
    try (FileWriter writer = new FileWriter("Resep Masakan.csv")) {
        writer.append("ID,Nama,Bahan,Langkah,Kategori,Gambar\n");
        for (String[] resep : dbHelper.getResepList()) {
            writer.append(String.join(",", resep)).append("\n");
        }
        JOptionPane.showMessageDialog(this, "Data berhasil diekspor ke Resep Masakan.csv!");
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Gagal mengekspor data!", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
```


- Method imporDariCSV : Mengimpor data resep dari file CSV dan menambahkannya ke dalam database.

```
private void imporDariCSV() {
    try (BufferedReader reader = new BufferedReader(new FileReader("Resep Masakan.csv"))) {
        String line;
        boolean header = true;
        while ((line = reader.readLine()) != null) {
            if (header) {
                header = false;
                continue;
            }
            String[] data = line.split(",");
            if (data.length == 6) { // Memastikan data memiliki 6 kolom
                dbHelper.tambahResep(data[1], data[2], data[3], data[4], data[5]);
            }
        }
        JOptionPane.showMessageDialog(this, "Data berhasil diimpor!");
        loadResepData();
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Gagal mengimpor data!", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
```


- Method loadResepData : Memuat dan menampilkan data resep dari database ke dalam tabel tblResep.

```
private void loadResepData() {
    DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Nama", "Bahan", "Langkah", "Kategori", "Gambar"}, 0) {
        @Override
        public Class<?> getColumnClass(int column) {
            return column == 5 ? ImageIcon.class : String.class; // Kolom gambar menggunakan ImageIcon
        }
    };

    dbHelper.getResepList().forEach((resep) -> {
        byte[] gambar = dbHelper.getGambar(resep[0]); // Mendapatkan gambar dari database
        ImageIcon icon = gambar != null ? new ImageIcon(new ImageIcon(gambar).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)) : null;
        model.addRow(new Object[]{resep[0], resep[1], resep[2], resep[3], resep[4], icon});
    });

    tblResep.setModel(model);
}
```


- Koneksi Resep : Menginisialisasi koneksi database dan membuat tabel jika belum ada.

```
public Resep() {
    try {
        conn = DriverManager.getConnection("jdbc:sqlite:Resep.db");
        createTable(); // Membuat tabel jika belum ada
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
```

- Method createTable : Membuat tabel resep di database jika tabel tersebut belum ada.

````
private void createTable() {
    String sqlCreateTable = "CREATE TABLE IF NOT EXISTS resep ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "nama TEXT NOT NULL, "
            + "bahan TEXT NOT NULL, "
            + "langkah TEXT NOT NULL, "
            + "kategori TEXT NOT NULL, "
            + "gambar BLOB)";
    try (Statement stmt = conn.createStatement()) {
        stmt.execute(sqlCreateTable);
        System.out.println("Tabel resep berhasil dibuat atau sudah ada.");
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
```

- Method tambahResep :Menambahkan resep baru ke dalam database.
```
public void tambahResep(String nama, String bahan, String langkah, String kategori, Byte gambar) {
    String sqlInsert = "INSERT INTO resep (nama, bahan, langkah, kategori, gambar) VALUES (?, ?, ?, ?, ?)";
    try (PreparedStatement pstmt = conn.prepareStatement(sqlInsert)) {
        pstmt.setString(1, nama);
        pstmt.setString(2, bahan);
        pstmt.setString(3, langkah);
        pstmt.setString(4, kategori);
        pstmt.setByte(5, gambar); // Menyimpan gambar dalam database
        pstmt.executeUpdate();
        System.out.println("Resep berhasil ditambahkan.");
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
```

