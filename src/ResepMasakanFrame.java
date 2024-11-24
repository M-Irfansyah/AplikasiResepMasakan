import java.awt.Component;
import java.awt.Image;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.table.TableModel;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;


public class ResepMasakanFrame extends javax.swing.JFrame {
    private final Resep dbHelper;

    /**
     * Creates new form ResepMasakanFrame
     */
    public ResepMasakanFrame() {
        initComponents();
        dbHelper = new Resep(); // Inisialisasi dbHelper di sini
        loadResepData(); // Memuat data Resep pada saat form pertama kali dibuka
        btnTambah.addActionListener(this::btnTambahActionPerformed);
        btnUbah.addActionListener(this::btnUbahActionPerformed);
        btnHapus.addActionListener(this::btnHapusActionPerformed);
        btnCari.addActionListener(this::btnCariActionPerformed);
        btnEkspor.addActionListener(e -> eksporKeCSV());
        btnImport.addActionListener(e -> imporDariCSV());

        tblResep.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tblResep.getSelectedRow() != -1) {
                int selectedRow = tblResep.getSelectedRow();
                jNama.setText((String) tblResep.getValueAt(selectedRow, 1)); // Kolom Nama masakan
                jtBahan.setText((String) tblResep.getValueAt(selectedRow, 2)); // Kolom bahan masakan
                jtLangkah.setText((String) tblResep.getValueAt(selectedRow, 3)); // Kolom langkah memasak
                jcbList.setSelectedItem((String) tblResep.getValueAt(selectedRow, 4)); // Kolom Kategori masakan

                // Tampilkan gambar
                String gambar = (String) tblResep.getValueAt(selectedRow, 5); // Kolom gambar
                if (gambar != null && !gambar.isEmpty()) {
                    lblGambar.setIcon(new ImageIcon(new ImageIcon(gambar).getImage()
                            .getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
                } else {
                    lblGambar.setIcon(null);
                }
            }
        });

        btnUnggah.addActionListener(e -> unggahGambar());
    }

    private void unggahGambar() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "jpg", "png", "jpeg"));
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String imagePath = selectedFile.getAbsolutePath();

            // Tampilkan gambar pada JLabel
            lblGambar.setIcon(new ImageIcon(new ImageIcon(imagePath).getImage()
                    .getScaledInstance(200, 200, Image.SCALE_SMOOTH)));

            // Simpan path gambar ke database
            int selectedRow = tblResep.getSelectedRow();
            if (selectedRow != -1) {
                int id = Integer.parseInt((String) tblResep.getValueAt(selectedRow, 0));
                dbHelper.simpanPathGambar(id, imagePath);
                loadResepData();
            } else {
                JOptionPane.showMessageDialog(this, "Pilih resep untuk mengunggah gambar!");
            }
        }
    }

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
                if (data.length == 6) { // Pastikan ada 6 kolom (ID, Nama, Bahan, Langkah, Kategori, Gambar)
                    dbHelper.tambahResep(data[1], data[2], data[3], data[4], data[5]);
                }
            }
            JOptionPane.showMessageDialog(this, "Data berhasil diimpor!");
            loadResepData();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Gagal mengimpor data!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jNama = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtBahan = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtLangkah = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        jListKategori = new javax.swing.JList<>();
        btnTambah = new javax.swing.JButton();
        btnCari = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnUbah = new javax.swing.JButton();
        btnEkspor = new javax.swing.JButton();
        btnImport = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblResep = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jcbList = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jCari = new javax.swing.JTextField();
        lblGambar = new javax.swing.JLabel();
        btnUnggah = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 255, 102));

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        jLabel1.setText("Aplikasi Resep Masakan");

        jLabel2.setText("Nama Masakan");

        jLabel3.setText("Bahan Masakan");

        jLabel4.setText("Langkah-Langkah Membuat");

        jtBahan.setColumns(20);
        jtBahan.setRows(5);
        jScrollPane1.setViewportView(jtBahan);

        jtLangkah.setColumns(20);
        jtLangkah.setRows(5);
        jScrollPane2.setViewportView(jtLangkah);

        jListKategori.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Goreng", "Berkuah", "Panggang" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jListKategori.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListKategoriValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(jListKategori);

        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnCari.setText("Cari");
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnUbah.setText("Ubah");
        btnUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUbahActionPerformed(evt);
            }
        });

        btnEkspor.setText("Ekspor");
        btnEkspor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEksporActionPerformed(evt);
            }
        });

        btnImport.setText("Import");
        btnImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImportActionPerformed(evt);
            }
        });

        tblResep.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "No", "Nama Masakan", "Bahan Masakan", "Langkah-langkah Memasak", "Kategori Masakan", "Gambar"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tblResep);

        jLabel5.setText("Kategori Masakan");

        jcbList.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Goreng", "Berkuah", "Panggang" }));

        jLabel6.setText("Cari Resep Masakan");

        btnUnggah.setText("Unggah Gambar");
        btnUnggah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUnggahActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addComponent(jLabel1)
                        .addGap(18, 18, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollPane2)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                                    .addComponent(jNama))
                                .addGap(0, 3, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnUnggah)
                                .addGap(18, 18, 18)
                                .addComponent(lblGambar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jcbList, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCari, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(btnCari, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 608, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnEkspor, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)
                        .addComponent(btnImport, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(32, 32, 32))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(126, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCari))
                .addGap(31, 31, 31)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnImport)
                    .addComponent(btnEkspor))
                .addGap(216, 216, 216))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel1)
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jcbList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnTambah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnUbah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnHapus)
                        .addGap(11, 11, 11))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(44, 44, 44)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblGambar, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUnggah))
                .addGap(132, 132, 132))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loadResepData() {
    DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Nama", "Bahan", "Langkah", "Kategori", "Gambar"}, 0) {
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnIndex == 5 ? ImageIcon.class : String.class; // Kolom ke-5 adalah ImageIcon
    }
};

dbHelper.getResepList().forEach((resep) -> {
    ImageIcon imageIcon = null;
    if (!"Tidak ada gambar".equals(resep[5])) {
        String imagePath = resep[5]; // Asumsikan path gambar disimpan di database
        imageIcon = new ImageIcon(imagePath);
        Image scaledImage = imageIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(scaledImage);
    }
    model.addRow(new Object[]{resep[0], resep[1], resep[2], resep[3], resep[4], imageIcon});
    });
    tblResep.setModel(model);

// Set renderer untuk kolom gambar
    tblResep.setRowHeight(60);
    tblResep.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return new JLabel((ImageIcon) value);
    }
});
    }
    
    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
   String keyword = jCari.getText();
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Nama", "Bahan", "Langkah", "Kategori", "Gambar"}, 0);
        dbHelper.getResepList().forEach(resep -> {
            if (resep[1].contains(keyword) || resep[2].contains(keyword)) {
                model.addRow(resep);
            }
        });
        tblResep.setModel(model);
    }//GEN-LAST:event_btnCariActionPerformed

    private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahActionPerformed
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

    }//GEN-LAST:event_btnUbahActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
    int selectedRow = tblResep.getSelectedRow();
        if (selectedRow != -1) {
            int id = Integer.parseInt((String) tblResep.getValueAt(selectedRow, 0));
            dbHelper.hapusResep(id);
            loadResepData();
        } else {
            JOptionPane.showMessageDialog(this, "Pilih resep yang ingin dihapus!");
        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnEksporActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEksporActionPerformed
    btnEkspor.addActionListener(e -> eksporKeCSV());
    }//GEN-LAST:event_btnEksporActionPerformed

    private void btnImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportActionPerformed
    btnImport.addActionListener(e -> imporDariCSV());
    }//GEN-LAST:event_btnImportActionPerformed

    private void jListKategoriValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListKategoriValueChanged
    jListKategori.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedCategory = jListKategori.getSelectedValue();
                jcbList.setSelectedItem(selectedCategory); // Sinkronkan dengan JComboBox
            }
        });
    }//GEN-LAST:event_jListKategoriValueChanged

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
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
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnUnggahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUnggahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUnggahActionPerformed

    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ResepMasakanFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ResepMasakanFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ResepMasakanFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ResepMasakanFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ResepMasakanFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnEkspor;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnImport;
    private javax.swing.JButton btnTambah;
    private javax.swing.JButton btnUbah;
    private javax.swing.JButton btnUnggah;
    private javax.swing.JTextField jCari;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JList<String> jListKategori;
    private javax.swing.JTextField jNama;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JComboBox<String> jcbList;
    private javax.swing.JTextArea jtBahan;
    private javax.swing.JTextArea jtLangkah;
    private javax.swing.JLabel lblGambar;
    private javax.swing.JTable tblResep;
    // End of variables declaration//GEN-END:variables
}
