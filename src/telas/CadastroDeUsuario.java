package telas;

import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dao.impl.DaoFactory;
import entidades.Administrador;
import entidades.Pessoa;
import entidades.Usuario;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;

import org.mindrot.jbcrypt.BCrypt;

public class CadastroDeUsuario extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNome;
	private JTextField txtDataNascimento;
	private JTextField txtSenha;
	private JTextField txtConfirmarSenha;
	private JTextField txtEmail;
	private JPasswordField campoSenha;
	private JPasswordField campoConfirmarSenha;
	private JTextField campoEmail;
	private JTextField campoNome;
	private JTextField campoCPF;
	private JTextField txtCPF;
	private JTextField campoDataDeNascimento;
	private JButton btnVoltar;
	private JTextField txtTipoDaConta;
	private JTextField campoCodigoAdm;
	private JTextField txtCodigoAdm;
	
	DateTimeFormatter formatoBrasileiro = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadastroDeUsuario frame = new CadastroDeUsuario();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CadastroDeUsuario() {
		CadastroDeUsuario essaTela = this;
		setTitle("cadastro");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 546, 535);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 128, 128));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 505, 484);
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBackground(new Color(0, 64, 0));
		contentPane.add(panel);
		panel.setLayout(null);
		
		JTextArea txtCadastrar = new JTextArea();
		txtCadastrar.setBounds(164, 0, 158, 42);
		txtCadastrar.setText("Cadastrar");
		txtCadastrar.setForeground(new Color(128, 255, 255));
		txtCadastrar.setFont(new Font("Tahoma", Font.BOLD, 31));
		txtCadastrar.setEditable(false);
		txtCadastrar.setBackground(new Color(0, 64, 0));
		panel.add(txtCadastrar);
		
		campoCodigoAdm = new JTextField();
		campoCodigoAdm.setHorizontalAlignment(SwingConstants.CENTER);
		campoCodigoAdm.setBounds(203, 310, 192, 20);
		panel.add(campoCodigoAdm);
		campoCodigoAdm.setColumns(10);
		
		txtCodigoAdm = new JTextField();
		txtCodigoAdm.setText("Código ADM");
		txtCodigoAdm.setHorizontalAlignment(SwingConstants.CENTER);
		txtCodigoAdm.setEditable(false);
		txtCodigoAdm.setColumns(10);
		txtCodigoAdm.setBackground(new Color(0, 128, 128));
		txtCodigoAdm.setBounds(47, 310, 116, 20);
		panel.add(txtCodigoAdm);
		
		txtDataNascimento = new JTextField();
		txtDataNascimento.setBounds(47, 112, 116, 20);
		txtDataNascimento.setText("Data de nascimento");
		txtDataNascimento.setHorizontalAlignment(SwingConstants.CENTER);
		txtDataNascimento.setEditable(false);
		txtDataNascimento.setColumns(10);
		txtDataNascimento.setBackground(new Color(0, 128, 128));
		panel.add(txtDataNascimento);
		
		txtSenha = new JTextField();
		txtSenha.setBounds(47, 177, 116, 20);
		txtSenha.setText("Senha");
		txtSenha.setHorizontalAlignment(SwingConstants.CENTER);
		txtSenha.setEditable(false);
		txtSenha.setColumns(10);
		txtSenha.setBackground(new Color(0, 128, 128));
		panel.add(txtSenha);
		
		txtConfirmarSenha = new JTextField();
		txtConfirmarSenha.setBounds(47, 208, 116, 20);
		txtConfirmarSenha.setText("Confirmar a senha");
		txtConfirmarSenha.setHorizontalAlignment(SwingConstants.CENTER);
		txtConfirmarSenha.setEditable(false);
		txtConfirmarSenha.setColumns(10);
		txtConfirmarSenha.setBackground(new Color(0, 128, 128));
		panel.add(txtConfirmarSenha);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(47, 81, 116, 20);
		txtEmail.setText("E-mail");
		txtEmail.setHorizontalAlignment(SwingConstants.CENTER);
		txtEmail.setEditable(false);
		txtEmail.setColumns(10);
		txtEmail.setBackground(new Color(0, 128, 128));
		panel.add(txtEmail);
		
		DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.addElement("Apostador");
        listModel.addElement("Administrador");
		
		JList<String> list = new JList<String>(listModel);
        campoCodigoAdm.setVisible(false); // Inicialmente invisível
        txtCodigoAdm.setVisible(false); //começa invisível

	        // Adiciona um ListSelectionListener à JList
	        list.addListSelectionListener(new ListSelectionListener() {
	            @Override
	            public void valueChanged(ListSelectionEvent e) {
	                // Obtém o valor selecionado
	                String selectedValue = list.getSelectedValue();

	                // Mostra ou oculta o campo com base na seleção
	                if ("Administrador".equals(selectedValue)) {
	                    campoCodigoAdm.setVisible(true); // Torna o campo visível
	                    txtCodigoAdm.setVisible(true);
	                } else {
	                    campoCodigoAdm.setVisible(false); // Torna o campo invisível
	                    txtCodigoAdm.setVisible(false);
	                }

	                // Revalida o frame para aplicar as mudanças de visibilidade
	                essaTela.revalidate();
	                essaTela.repaint();
	            }
	        });
		list.setBackground(Color.WHITE);
		list.setForeground(new Color(0, 0, 0));
		list.setBounds(203, 239, 192, 42);
		panel.add(list);
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.setBounds(47, 432, 102, 23);
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Pessoa pessoa = new Pessoa();
				DaoFactory dao = new DaoFactory();
								
				//verificação senha
				if(campoSenha.getText().isEmpty()) {
					//JOptionPane.showMessageDialog(btnCadastrar, "Senha não digitada!"); //obviamente tirar essa linha depois
					return; //para interromper a ação
				}
				else if(campoSenha.getText().equals(campoConfirmarSenha.getText())) {
					// Criptografa a senha usando BCrypt
		            String senhaHash = BCrypt.hashpw(new String(campoSenha.getPassword()), BCrypt.gensalt());
		            System.out.println(senhaHash);
		            pessoa.setSenha(senhaHash);
					//pessoa.setSenha(campoSenha.getText());
					//JOptionPane.showMessageDialog(btnCadastrar, "Senha: " + pessoa.getSenha()); //obviamente tirar essa linha depois
				}
				else {
					JOptionPane.showMessageDialog(btnCadastrar, "Senhas não correspondem!");
					return;
				}
				
				//verificação nome
				if(!campoNome.getText().equals("")) {
					pessoa.setNome(campoNome.getText());
					///JOptionPane.showMessageDialog(btnCadastrar, "Nome: " + pessoa.getNome());
				}
				else {
					JOptionPane.showMessageDialog(btnCadastrar, "Nome não inserido!");
					return;
				}
				
				//verificação data de nascimento
				if (!campoDataDeNascimento.getText().equals("")) {
				    try {
				        // Converte a string para java.util.Date
				        LocalDate dataNascimentoLocal = LocalDate.parse(campoDataDeNascimento.getText(), formatoBrasileiro);
				        
				        // Converte java.util.Date para java.sql.Date
				        java.sql.Date dataNascimentoSQL = java.sql.Date.valueOf(dataNascimentoLocal);
				        
				        // Define a data na entidade 'Pessoa'
				        pessoa.setDataNascimento(dataNascimentoSQL);

				        // Verifica se a pessoa tem mais de 18 anos				        		  		       
				        int idade = Period.between(dataNascimentoLocal, LocalDate.now()).getYears();  // Calcula a idade				        
				        
				        if(idade < 18) {
				        	 JOptionPane.showMessageDialog(btnCadastrar, "Você deve ter pelo menos 18 anos!");
					         return;
				        }				        
				        else{
				        	if(idade < 112) {
				        		JOptionPane.showMessageDialog(btnCadastrar, "Data de nascimento: " + pessoa.getDataNascimento());
				        	}
				        	else {
					            JOptionPane.showMessageDialog(btnCadastrar, "Idade inadequada!\n(Segundo o Guiness World Records, a pessoa mais velha do mundo possui 112 anos!)");
					            return;
				        	}
				        }			        
				    } catch (DateTimeParseException e1) {
				        e1.printStackTrace();				        				       
				        JOptionPane.showMessageDialog(btnCadastrar, "Data de nascimento inválida!\n" + e1.getMessage() + "\nUtilize o formato dd/MM/yyyy");
				        return;
				    }
				} else {
				    JOptionPane.showMessageDialog(btnCadastrar, "Data de nascimento não digitada!\nUtilize o formato dd/MM/yyyy");
				    return;
				}
				
				//verificação email
				if(!campoEmail.getText().equals("")) {
					pessoa.setEmail(campoEmail.getText());
					
				}
				else {
					JOptionPane.showMessageDialog(btnCadastrar, "Email não digitado!");
					return;
				}
				
				//verificação cpf - faltam melhorias
				if(!campoCPF.getText().equals("")) {
					pessoa.setCpf(Integer.parseInt(campoCPF.getText()));					
				}
				else {
					JOptionPane.showMessageDialog(btnCadastrar, "CPF não digitado!");
					return;
				}
				
				//inserindo no banco de dados um apostador ou adm!				
				if("Apostador".equals(list.getSelectedValue())) {
					try {
						Usuario usuario = new Usuario(pessoa);
						dao.criarPessoaDaoJDBC().insert(pessoa);
						JOptionPane.showInternalMessageDialog(btnCadastrar, "Cadastrado com sucesso!");
					} catch (SQLException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "Erro inesperado! Erro : " + e1.getMessage());
					}
				}
				else if("Administrador".equals(list.getSelectedValue())) {
					if(dao.criarCodigoDeCadastroAdmDaoJDBC().findCodigoAdmById(campoCodigoAdm.getText())) {
						try {
							Administrador adm = new Administrador(pessoa);
							dao.criarPessoaDaoJDBC().insertAdm(pessoa);
							JOptionPane.showInternalMessageDialog(null, "Cadastrado com sucesso!");
						} catch (SQLException e2) {							
							e2.printStackTrace();
							JOptionPane.showMessageDialog(btnCadastrar, "Erro inesperado! Erro : " + e2.getMessage());
						}
					}
					else {
						JOptionPane.showMessageDialog(btnCadastrar,"Código de adiministrador digitado é invalido!");
					}
				}
				else{
					JOptionPane.showMessageDialog(btnCadastrar, "Selecione uma opção de tipo de conta!");
				}
				
			}
		});
		btnCadastrar.setForeground(new Color(0, 0, 128));
		btnCadastrar.setBackground(UIManager.getColor("CheckBox.focus"));
		panel.add(btnCadastrar);
		
		campoSenha = new JPasswordField();
		campoSenha.setBounds(203, 177, 192, 20);
		panel.add(campoSenha);
		
		campoConfirmarSenha = new JPasswordField();
		campoConfirmarSenha.setBounds(203, 208, 192, 20);
		panel.add(campoConfirmarSenha);
		
		campoEmail = new JTextField();
		campoEmail.setBounds(203, 81, 192, 20);
		campoEmail.setColumns(10);
		panel.add(campoEmail);
		
		campoNome = new JTextField();
		campoNome.setBounds(203, 50, 192, 20);
		campoNome.setColumns(10);
		panel.add(campoNome);
		
		txtNome = new JTextField();
		txtNome.setBounds(47, 50, 116, 20);
		txtNome.setText("Nome de usuário");
		txtNome.setHorizontalAlignment(SwingConstants.CENTER);
		txtNome.setEditable(false);
		txtNome.setColumns(10);
		txtNome.setBackground(new Color(0, 128, 128));
		panel.add(txtNome);
		
		campoCPF = new JTextField();
		campoCPF.setBounds(203, 143, 192, 20);
		campoCPF.setColumns(10);
		panel.add(campoCPF);
		
		txtCPF = new JTextField();
		txtCPF.setBounds(47, 143, 116, 20);
		txtCPF.setText("CPF");
		txtCPF.setHorizontalAlignment(SwingConstants.CENTER);
		txtCPF.setEditable(false);
		txtCPF.setColumns(10);
		txtCPF.setBackground(new Color(0, 128, 128));
		panel.add(txtCPF);
		
		campoDataDeNascimento = new JTextField();
		campoDataDeNascimento.setBounds(203, 112, 192, 20);
		campoDataDeNascimento.setColumns(10);
		panel.add(campoDataDeNascimento);
		
		
		btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {								
        		essaTela.setVisible(false);
        		new BoasVindas().setVisible(true);       		     		
			}			
		});
		btnVoltar.setForeground(new Color(0, 0, 128));
		btnVoltar.setBackground(UIManager.getColor("CheckBox.focus"));
		btnVoltar.setBounds(384, 432, 81, 23);
		panel.add(btnVoltar);
		
		
		txtTipoDaConta = new JTextField();
		txtTipoDaConta.setText("Tipo da Conta");
		txtTipoDaConta.setHorizontalAlignment(SwingConstants.CENTER);
		txtTipoDaConta.setEditable(false);
		txtTipoDaConta.setColumns(10);
		txtTipoDaConta.setBackground(new Color(0, 128, 128));
		txtTipoDaConta.setBounds(47, 243, 116, 20);
		panel.add(txtTipoDaConta);
		
	}
}
