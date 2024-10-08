package dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.ApostaDao;
import db.DB;
import entidades.Aposta;
import entidades.Evento;

public class ApostaDaoJDBC implements ApostaDao{
	
	private Connection conn = DB.getConnection();
		
	public void insert(Aposta aposta) {
        String sql = "INSERT INTO Aposta (descricao, idDeEvento, odd, dataDeCriacao, status) "
        			+ "VALUES (?, ?, ?, ?, ?)";
        
        java.sql.Date sqlDate = new java.sql.Date(aposta.getDataDeCriacao().getTime()); //linha de cast de util.Date para sql.Date
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {         
            ps.setDate(4, sqlDate);
            ps.setString(1, aposta.getDescricao());  
            ps.setDouble(3, aposta.getOdd());
            ps.setInt(2, aposta.getIdDeEvento());
            ps.setString(5, "pendente");  //por enquanto o status vai começar como pendente!!
            ps.executeUpdate();            
            ps.close();
        } catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Aposta> ListarApostasPorEventoId(int EventoId){
		
		String sql = "SELECT * FROM aposta WHERE iddeevento = ?";
		
		ArrayList<Aposta> apostas = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
        	ps.setInt(1, EventoId);
        	ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Aposta aposta = new Aposta();
                aposta.setId(rs.getInt("id"));
                aposta.setStatus(rs.getString("status"));              
                aposta.setDataDeCriacao(rs.getDate("datadecriacao"));
                aposta.setOdd(rs.getDouble("odd"));
                aposta.setDescricao(rs.getString("descricao"));
                aposta.setIdDeEvento(rs.getInt("iddeevento"));
                apostas.add(aposta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return apostas;
	}
	
	public boolean deleteById(int id) {
		String sql = "DELETE FROM aposta WHERE id = ?";
		
		try(PreparedStatement ps = conn.prepareStatement(sql)){
			ps.setInt(1, id);
			ps.executeUpdate();
			System.out.println("Aposta deletado com sucesso!");
			ps.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean editarOdd(int id, double odd) {
		String sql = "UPDATE aposta SET odd = ? WHERE id = ?";
		
		try(PreparedStatement ps = conn.prepareStatement(sql)){
			ps.setDouble(1, odd);
			ps.setInt(2, id);
			ps.executeUpdate();
			System.out.println("Aposta atualizado com sucesso!");
			ps.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public ArrayList<Aposta> listarTodasApostas() {
		String sql = "SELECT * FROM Aposta";
		ArrayList<Aposta> apostas = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
            	//não coloquei permissão aqui, não acho que seja necessário que o usuário saiba
                Aposta aposta = new Aposta();
                aposta.setId(rs.getInt("id"));
                aposta.setOdd(rs.getDouble("odd"));
                aposta.setDescricao(rs.getString("descricao"));
                aposta.setDataDeCriacao(rs.getDate("datadecriacao"));
                apostas.add(aposta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return apostas;
    }
	
	public Aposta findApostaById(int idAposta) {
	    String sql = "SELECT * FROM Aposta WHERE id = ?";
	    Aposta aposta = null;

	    try (PreparedStatement ps = conn.prepareStatement(sql)) { 
	        ps.setInt(1, idAposta);
	        try (ResultSet rs = ps.executeQuery()) {  
	            if (rs.next()) {  
	                aposta = new Aposta();
	                aposta.setId(rs.getInt("id"));
	                aposta.setOdd(rs.getDouble("odd"));
	                aposta.setDescricao(rs.getString("descricao"));
	                aposta.setDataDeCriacao(rs.getDate("datadecriacao"));
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return aposta;
	}
	
	public boolean editarDescricao(int id, String descricao) {
		String sql = "UPDATE aposta SET descricao = ? WHERE id = ?";
		
		try(PreparedStatement ps = conn.prepareStatement(sql)){
			ps.setString(1, descricao);
			ps.setInt(2, id);
			ps.executeUpdate();
			System.out.println("Aposta atualizado com sucesso!");
			ps.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	//Daqui para baixo não sei se deveria estar nessa classe ou numa ApostaBilheteDaoJDBC, já que são operações pra tabela que lhes conecta
	public ArrayList<Aposta> findApostasByBilheteId(int idBilhete) {
        ArrayList<Aposta> apostas = new ArrayList<Aposta>();
        String sql = "SELECT Aposta.* FROM Aposta " +
                     "JOIN Bilhete_Aposta ON Aposta.id = Bilhete_Aposta.idaposta " +
                     "WHERE Bilhete_Aposta.idbilhete = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idBilhete);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Aposta aposta = new Aposta();
                    aposta.setId(rs.getInt("id"));
                    aposta.setDescricao(rs.getString("descricao"));
                    aposta.setIdDeEvento(rs.getInt("idDeEvento"));
                    aposta.setOdd(rs.getDouble("odd"));
                    aposta.setDataDeCriacao(rs.getDate("dataDeCriacao"));
                    aposta.setStatus(rs.getString("status"));                   
                    apostas.add(aposta);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }        
        return apostas;
    }
}
