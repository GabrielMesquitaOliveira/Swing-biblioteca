package raven.application.utils;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class LivroTable extends JFrame {
    private JTable table;
    private DefaultTableModel model;

    public LivroTable() {
        // Configuração do modelo de tabela
        String[] columnNames = {"ID", "Título", "Autor ID", "Categoria ID", "Ano de Publicação", "Status"};
        Object[][] data = {}; 
        
        model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Permitir edição em todas as colunas exceto o ID
                return column != 0;
            }
        };

        table = new JTable(model);
        loadTableData();

        // Ouvinte para capturar edições
        model.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    int row = e.getFirstRow();
                    int column = e.getColumn();
                    updateDatabase(row, column);
                }
            }
        });

        add(new JScrollPane(table));
        setTitle("Edição de Livros");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);
        setVisible(true);
    }

    private void loadTableData() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Biblioteca", "root", "root");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Livros")) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String titulo = rs.getString("titulo");
                int autorId = rs.getInt("autor_id");
                int categoriaId = rs.getInt("categoria_id");
                Date anoPublicacao = rs.getDate("ano_publicacao");
                String status = rs.getString("status"); // Obter o status

                model.addRow(new Object[]{id, titulo, autorId, categoriaId, anoPublicacao, status});
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateDatabase(int row, int column) {
        int id = (int) model.getValueAt(row, 0);  // Obter o ID da linha para identificar o registro
        Object newValue = model.getValueAt(row, column);
        
        String columnName = model.getColumnName(column);  // Obter o nome da coluna alterada

        String sql = "UPDATE Livros SET " + columnName + " = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Biblioteca", "root", "root");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, newValue);  // Novo valor editado
            stmt.setInt(2, id);  // ID do livro

            stmt.executeUpdate();
            System.out.println("Banco de dados atualizado para ID: " + id + ", Coluna: " + columnName);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LivroTable());
    }
}
