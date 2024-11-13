package raven.application.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import raven.application.utils.Livro;
import raven.application.utils.ConnectionFactory;

public class LivroDAO extends ConnectionFactory {

    public List<Livro> listarLivros() {
        List<Livro> livros = new ArrayList<>();
    
        String sql = "SELECT L.id, L.titulo, L.autor_id AS autorId, L.categoria_id AS categoriaId, L.data_publicacao AS ano_publicacao, L.status " +
                     "FROM Livros L";
    
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
    
            while (rs.next()) {
                int id = rs.getInt("id");
                String titulo = rs.getString("titulo");
                int autorId = rs.getInt("autorId");
                int categoriaId = rs.getInt("categoriaId");
                Date anoPublicacao = rs.getDate("ano_publicacao");
                String statusStr = rs.getString("status");
    
                // Converte o valor de status para o tipo ENUM, se necessário
                Livro.Status status = Livro.Status.valueOf(statusStr);
    
                Livro livro = new Livro(id, titulo, autorId, categoriaId, anoPublicacao, status);
                livros.add(livro);
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return livros;
    }
    

    public void salvarLivro(String titulo, int autorId, int categoriaId, Date anoPublicacao) throws SQLException {
        String sql = "INSERT INTO Livros (titulo, autor_id, categoria_id, data_publicacao) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setString(1, titulo);
            stmt.setInt(2, autorId);
            stmt.setInt(3, categoriaId);
            stmt.setDate(4, anoPublicacao);
    
            stmt.executeUpdate();
        }
    }
}
