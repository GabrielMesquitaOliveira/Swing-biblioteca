package raven.application.form.other;

import raven.application.model.LivroDAO;
import raven.application.utils.Livro;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Formulário para exibir a lista de livros cadastrados
 */
public class FormBookList extends JPanel {

    private JTable bookTable;
    private DefaultTableModel tableModel;

    public FormBookList() {
        initComponents();
        loadBookData();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JLabel lb = new JLabel("Lista de Livros Cadastrados");
        lb.setHorizontalAlignment(SwingConstants.CENTER);
        lb.putClientProperty("FlatClientProperties.STYLE", "font:$h1.font");

        // Configuração da tabela e modelo de dados
        String[] columnNames = { "id", "titulo", "autor_id", "categoria_id", "data_publicacao", "status" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Permitir edição em todas as colunas exceto o ID
                return column != 0;
            }
        };
        bookTable = new JTable(tableModel);

        // Observer para capturar edições
        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    int row = e.getFirstRow();
                    int column = e.getColumn();
                    updateDatabase(row, column);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(bookTable);

        add(lb, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadBookData() {
        LivroDAO livroDAO = new LivroDAO();
        List<Livro> livros = livroDAO.listarLivros();

        for (Livro livro : livros) {
            Object[] rowData = {
                    livro.getId(),
                    livro.getTitulo(),
                    livro.getAutor(),
                    livro.getCategoria(),
                    livro.getAnoPublicacao(),
                    livro.getStatus() // Novo campo status
            };
            tableModel.addRow(rowData);
        }
    }

    private void updateDatabase(int row, int column) {
        int id = (int) tableModel.getValueAt(row, 0);  // Obter o ID da linha para identificar o registro
        Object newValue = tableModel.getValueAt(row, column);

        String columnName = tableModel.getColumnName(column);  // Obter o nome da coluna alterada

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
}
