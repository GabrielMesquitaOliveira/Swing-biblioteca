package raven.application.form.other;

import com.formdev.flatlaf.FlatClientProperties;
import raven.application.model.LivroDAO;
import raven.application.utils.Livro;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Formulário de cadastro de livros
 */
public class FormBookCreate extends javax.swing.JPanel {

    public FormBookCreate() {
        initComponents();
    }

    private void initComponents() {
        // Configuração do Layout
        setLayout(new GridBagLayout());  // Define o layout do painel como GridBagLayout

        lb = new JLabel("Cadastro de Livro");
        lb.putClientProperty(FlatClientProperties.STYLE, "font:$h1.font");

        // Criação dos componentes do formulário
        JLabel titleLabel = new JLabel("Título do Livro:");
        JTextField titleField = new JTextField(20);

        JLabel authorLabel = new JLabel("ID do Autor:");
        JTextField authorField = new JTextField(20);

        JLabel categoryLabel = new JLabel("ID da Categoria:");
        JTextField categoryField = new JTextField(20);

        JLabel yearLabel = new JLabel("Data de Publicação:");
        JTextField yearField = new JTextField(20);

        JButton saveButton = new JButton("Salvar");

        // Configuração de GridBagConstraints para alinhamento centralizado
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Espaçamento entre componentes
        gbc.anchor = GridBagConstraints.CENTER;   // Alinhamento centralizado
        gbc.fill = GridBagConstraints.HORIZONTAL; // Permite o preenchimento horizontal dos campos

        // Adicionando os componentes ao layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(lb, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(titleLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(titleField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(authorLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        add(authorField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(categoryLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        add(categoryField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(yearLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        add(yearField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        add(saveButton, gbc);

        // Ação do botão Salvar
        saveButton.addActionListener(e -> {
            // Captura dos dados inseridos
            String titulo = titleField.getText();
            String autorIdStr = authorField.getText();
            String categoriaIdStr = categoryField.getText();
            String ano = yearField.getText();

            // Validação simples
            if (titulo.isEmpty() || autorIdStr.isEmpty() || categoriaIdStr.isEmpty() || ano.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                // Conversão do ID de autor e categoria para inteiros
                int autorId = Integer.parseInt(autorIdStr);
                int categoriaId = Integer.parseInt(categoriaIdStr);
                Date anoPublicacao = convertToDate(ano);

                // Salvar os dados no banco
                LivroDAO livroDAO = new LivroDAO();
                livroDAO.salvarLivro(titulo, autorId, categoriaId, anoPublicacao);  // Método para salvar no banco
                JOptionPane.showMessageDialog(this, "Livro cadastrado com sucesso!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Os campos de ID do autor e categoria devem ser numéricos!", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao salvar o livro no banco.", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao converter a data de publicação.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    // Método para converter ano para o formato Date (1º de Janeiro)
    private Date convertToDate(String dataStr) throws ParseException {
        // Verifica se a data está no formato correto (dd-MM-yyyy)
        if (dataStr == null || !dataStr.matches("\\d{2}-\\d{2}-\\d{4}")) {
            throw new ParseException("Formato de data inválido. Use dd-MM-yyyy.", 0);
        }

        // Define o formato da data de entrada
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        // Converte a string de data para um objeto java.util.Date
        java.util.Date utilDate = sdf.parse(dataStr);

        // Retorna o objeto java.sql.Date
        return new java.sql.Date(utilDate.getTime());
    }

    // Variável de declaração da label principal
    private javax.swing.JLabel lb;
}
