package Game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class JogoVelha2 {

	private JFrame frame;
	private BufferedImage img = null;//Recebe a Imagem.
	private JButton[] btn = new JButton[9];
	private JTextField Insert;
	private JTextField Insert2;
	private JLabel lblPontos;
	private JLabel lblPontos2;
	private JLabel lblEmpates;
	private short pontosIA = 0;
	private short pontosJogador = 0;
	private short pontosEmpates = 0;
	private boolean xo = false;
	private boolean intervalo = false;
	private Random randon = new Random();
	private boolean[] clicks = new boolean[9];
	private ButtonGroup bg;
	
	public static void main(String[] args) {
		//String look = "com.jtattoo.plaf.noire.NoireLookAndFeel";
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
		            //UIManager.setLookAndFeel(look);
					JogoVelha2 window = new JogoVelha2();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public JogoVelha2() {
		initialize();
	}

	
	private void initialize() {
		
		try {
			img = ImageIO.read(new File("imagem1.jpg"));//Passando a imagem para a variavel.
		} catch (IOException e) {
			System.out.println("Erro do tipo: " + e.getMessage());
		}
		
		frame = new JFrame();
		frame.setBounds(100, 100, 737, 424);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);	
		frame.setResizable(false);
			
		Panel panelMenu = new Panel();
		panelMenu.setBounds(0, 0, 737, 396);
		frame.getContentPane().add(panelMenu);
		panelMenu.setLayout(null);
		
		//Reconfigura a imagem para o tamanho da largura e altura da janela e faz isto de forma que seja suave.
		Image image = img.getScaledInstance(panelMenu.getWidth(), panelMenu.getHeight(), Image.SCALE_SMOOTH);
				
		//Me permite adicionar como icone em uma label.
		ImageIcon icon = new ImageIcon(image);
		
		JLabel lblJogoDaVelha = new JLabel("Jogo Da Velha");
		lblJogoDaVelha.setForeground(Color.BLUE);
		lblJogoDaVelha.setFont(new Font("Suranna", Font.BOLD | Font.ITALIC, 72));
		lblJogoDaVelha.setBounds(125, 29, 480, 100);
		panelMenu.add(lblJogoDaVelha);
		
		JButton btnSinglePlayer = new JButton("SinglePlayer");
		btnSinglePlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Panel panelNome = new Panel();
				panelNome.setBounds(0, 0, 737, 396);
				frame.getContentPane().add(panelNome);
				panelNome.setLayout(null);
				
				JLabel lblNomeDoPlayer = new JLabel("Nome do Player:");
				lblNomeDoPlayer.setFont(new Font("Purisa", Font.BOLD | Font.ITALIC, 21));
				lblNomeDoPlayer.setForeground(Color.MAGENTA);
				lblNomeDoPlayer.setBounds(107, 71, 213, 30);
				panelNome.add(lblNomeDoPlayer);
				panelMenu.setVisible(false);
				
				Insert = new JTextField();
				Insert.setBounds(338, 71, 184, 30);
				panelNome.add(Insert);
				Insert.setColumns(10);
				
				JLabel lblmaquina = new JLabel("Máquina começa jogando?:");
				lblmaquina.setFont(new Font("Purisa", Font.BOLD | Font.ITALIC, 21));
				lblmaquina.setForeground(Color.MAGENTA);
				lblmaquina.setBounds(107, 128, 328, 40);
				panelNome.add(lblmaquina);
				panelMenu.setVisible(false);
				
				JRadioButton rdbtnOn = new JRadioButton("ON");
				rdbtnOn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						intervalo = true;
					}
				});
				rdbtnOn.setBounds(452, 128, 62, 36);
				rdbtnOn.setFont(new Font("Purisa", Font.BOLD | Font.ITALIC, 23));
				rdbtnOn.setOpaque(false);
				rdbtnOn.setContentAreaFilled(false);
				rdbtnOn.setBorderPainted(false);
				rdbtnOn.setForeground(Color.GREEN);
				panelNome.add(rdbtnOn);
				
				JRadioButton rdbtnOff = new JRadioButton("OFF");
				rdbtnOff.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						intervalo = false;
					}
				});
				rdbtnOff.setBounds(534, 132, 82, 36);
				rdbtnOff.setFont(new Font("Purisa", Font.BOLD | Font.ITALIC, 23));
				rdbtnOff.setOpaque(false);
				rdbtnOff.setContentAreaFilled(false);
				rdbtnOff.setBorderPainted(false);
				rdbtnOff.setForeground(Color.GREEN);
				panelNome.add(rdbtnOff);
				bg = new ButtonGroup();
				bg.add(rdbtnOn);
				bg.add(rdbtnOff);
				
				JButton btnInserir = new JButton("Inserir");
				btnInserir.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						String jogador = Insert.getText();
						if(jogador.isEmpty()) {
							jogador = "Player";
						}
												
						Panel panelSinglePlayer = new Panel();
						panelSinglePlayer.setBounds(0, 0, 737, 396);
						frame.getContentPane().add(panelSinglePlayer);
						panelSinglePlayer.setLayout(null);
						panelNome.setVisible(false);						
						////////////////////////////Criando os Botões//////////////////////////////////////////////////
						int cont = 0;
						for(int i = 0; i < 3; i++) {
							for(int j = 0; j < 3; j++) {
								btn[cont] = new JButton();
								panelSinglePlayer.add(btn[cont]);
								btn[cont].setBounds((115 * i) + 21, (115 * j) + 23, 90, 90 );
								btn[cont].setFont(new Font("Purisa", Font.BOLD, 40));
								btn[cont].setOpaque(false);
								btn[cont].setContentAreaFilled(false);
								btn[cont].setBorderPainted(false);
								cont++;
							}
						}					
						for(int i = 0; i < 9; i++) {
							clicks[i] = false;
						}
						
						//Para Máquina começar marcando tem que ser true.
						if(intervalo) {
							jogadaIA();
						}
						
						btn[0].addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								if(clicks[0] == false) {
									clicks[0] = true;
									jogadaPlayer(btn[0]);
									jogadaIA();//Quando clico ela já chama.
								}
							}
						});
						btn[1].addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								if(clicks[1] == false) {
									clicks[1] = true;
									jogadaPlayer(btn[1]);
									jogadaIA();
								}
							}
						});
						btn[2].addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								if(clicks[2] == false) {
									clicks[2] = true;
									jogadaPlayer(btn[2]);
									jogadaIA();
								}
							}
						});
						btn[3].addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								if(clicks[3] == false) {
									clicks[3] = true;
									jogadaPlayer(btn[3]);
									jogadaIA();
								}
							}
						});
						btn[4].addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								if(clicks[4] == false) {
									clicks[4] = true;
									jogadaPlayer(btn[4]);
									jogadaIA();
								}
							}
						});
						btn[5].addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								if(clicks[5] == false) {
									clicks[5] = true;
									jogadaPlayer(btn[5]);
									jogadaIA();
								}
							}
						});
						btn[6].addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								if(clicks[6] == false) {
									clicks[6] = true;
									jogadaPlayer(btn[6]);
									jogadaIA();
								}
							}
						});
						btn[7].addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								if(clicks[7] == false) {
									clicks[7] = true;
									jogadaPlayer(btn[7]);
									jogadaIA();
								}
							}
						});
						btn[8].addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								if(clicks[8] == false) {
									clicks[8] = true;
									jogadaPlayer(btn[8]);
									jogadaIA();
								}
							}
						});
						////////////////////////////Criando os Botões//////////////////////////////////////////////////
						
						Canvas canvas = new Canvas();
						canvas.setForeground(Color.BLACK);
						canvas.setBackground(Color.BLACK);
						canvas.setBounds(120, 32, 11, 308);
						panelSinglePlayer.add(canvas);
						
						Canvas canvas_1 = new Canvas();
						canvas_1.setForeground(Color.BLACK);
						canvas_1.setBackground(Color.BLACK);
						canvas_1.setBounds(231, 32, 11, 308);
						panelSinglePlayer.add(canvas_1);
						
						Canvas canvas_2 = new Canvas();
						canvas_2.setForeground(Color.BLACK);
						canvas_2.setBackground(Color.BLACK);
						canvas_2.setBounds(29, 117, 321, 14);
						panelSinglePlayer.add(canvas_2);
						
						Canvas canvas_2_1 = new Canvas();
						canvas_2_1.setForeground(Color.BLACK);
						canvas_2_1.setBackground(Color.BLACK);
						canvas_2_1.setBounds(29, 238, 321, 14);
						panelSinglePlayer.add(canvas_2_1);
						
						JLabel lblJogador = new JLabel(jogador);
						lblJogador.setFont(new Font("Purisa", Font.BOLD, 26));
						lblJogador.setForeground(Color.RED);
						lblJogador.setBounds(389, 32, 139, 31);
						panelSinglePlayer.add(lblJogador);
						
						JLabel lblAni = new JLabel("Ani");
						lblAni.setForeground(Color.BLUE);
						lblAni.setFont(new Font("Purisa", Font.BOLD, 26));
						lblAni.setBounds(603, 32, 62, 31);
						panelSinglePlayer.add(lblAni);
						
						lblPontos = new JLabel("Pontos:" + pontosJogador);
						lblPontos.setForeground(Color.RED);
						lblPontos.setFont(new Font("Purisa", Font.BOLD, 20));
						lblPontos.setBounds(401, 97, 139, 31);
						panelSinglePlayer.add(lblPontos);
						
						lblPontos2 = new JLabel("Pontos:" + pontosIA);
						lblPontos2.setForeground(Color.BLUE);
						lblPontos2.setFont(new Font("Purisa", Font.BOLD, 20));
						lblPontos2.setBounds(586, 97, 139, 31);
						panelSinglePlayer.add(lblPontos2);
						
						lblEmpates = new JLabel("Empates:" + pontosEmpates);
						lblEmpates.setForeground(Color.MAGENTA);
						lblEmpates.setFont(new Font("Purisa", Font.BOLD, 22));
						lblEmpates.setBounds(486, 147, 139, 31);
						panelSinglePlayer.add(lblEmpates);
						
						JLabel lblX = new JLabel("X");
						lblX.setFont(new Font("Dialog", Font.BOLD, 20));
						lblX.setForeground(Color.MAGENTA);
						lblX.setBounds(533, 32, 23, 26);
						panelSinglePlayer.add(lblX);
						
						JButton btnVoltarAoMenu = new JButton("Voltar ao Menu");
						btnVoltarAoMenu.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							pontosJogador = 0;
							pontosIA = 0;
							pontosEmpates = 0;
							intervalo = false;
							panelMenu.setVisible(true);
							panelSinglePlayer.setVisible(false);
							}
						});
						btnVoltarAoMenu.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseEntered(MouseEvent e) {
							btnVoltarAoMenu.setFont(new Font("Purisa", Font.BOLD | Font.ITALIC, 36));
						}
						@Override
						public void mouseExited(MouseEvent e) {
							btnVoltarAoMenu.setFont(new Font("Purisa", Font.BOLD | Font.ITALIC, 26));
							}
						});
						btnVoltarAoMenu.setOpaque(false);
						btnVoltarAoMenu.setContentAreaFilled(false);
						btnVoltarAoMenu.setBorderPainted(false);
						btnVoltarAoMenu.setFont(new Font("Purisa", Font.BOLD | Font.ITALIC, 26));
						btnVoltarAoMenu.setForeground(Color.GREEN);
						btnVoltarAoMenu.setBounds(356, 218, 357, 83);
						panelSinglePlayer.add(btnVoltarAoMenu);
												
						JLabel backgroundCopy = new JLabel();
						backgroundCopy.setBounds(0, 0, 737, 396);
						backgroundCopy.setIcon(icon);
						panelSinglePlayer.add(backgroundCopy);
						}
					});
				btnInserir.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) {
						btnInserir.setFont(new Font("Purisa", Font.BOLD | Font.ITALIC, 26));
					}
					@Override
					public void mouseExited(MouseEvent e) {
						btnInserir.setFont(new Font("Purisa", Font.BOLD | Font.ITALIC, 20));
						}
					});
				btnInserir.setOpaque(false);
				btnInserir.setContentAreaFilled(false);
				btnInserir.setBorderPainted(false);
				btnInserir.setFont(new Font("Purisa", Font.BOLD | Font.ITALIC, 20));
				btnInserir.setForeground(Color.RED);
				btnInserir.setBounds(251, 228, 173, 40);
				panelNome.add(btnInserir);
					
				JLabel backgroundCopy = new JLabel();
				backgroundCopy.setBounds(0, 0, 737, 396);
				backgroundCopy.setIcon(icon);
				panelNome.add(backgroundCopy);			
			}
		});
		btnSinglePlayer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnSinglePlayer.setFont(new Font("Purisa", Font.BOLD | Font.ITALIC, 36));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnSinglePlayer.setFont(new Font("Purisa", Font.BOLD | Font.ITALIC, 26));
			}
		});
		btnSinglePlayer.setForeground(Color.RED);
		btnSinglePlayer.setOpaque(false);//Deixa Transparente.
		btnSinglePlayer.setContentAreaFilled(false);//Deixa Transparente.
		btnSinglePlayer.setBorderPainted(false);//Deixa Transparente.
		btnSinglePlayer.setFont(new Font("Purisa", Font.BOLD | Font.ITALIC, 26));
		btnSinglePlayer.setBounds(198, 141, 318, 58);
		btnSinglePlayer.setBorder(null);
		panelMenu.add(btnSinglePlayer);
		
		JButton btnMultiPlayer = new JButton("MultiPlayer");
		btnMultiPlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				Panel panelNomes = new Panel();
				panelNomes.setBounds(0, 0, 737, 396);
				frame.getContentPane().add(panelNomes);
				panelNomes.setLayout(null);
				
				JLabel player1 = new JLabel("Nome do Player1:");
				player1.setFont(new Font("Purisa", Font.BOLD | Font.ITALIC, 21));
				player1.setForeground(Color.MAGENTA);
				player1.setBounds(107, 119, 215, 30);
				panelNomes.add(player1);
				
				JLabel player2 = new JLabel("Nome do Player2:");
				player2.setFont(new Font("Purisa", Font.BOLD | Font.ITALIC, 21));
				player2.setForeground(Color.MAGENTA);
				player2.setBounds(107, 169, 228, 30);
				panelNomes.add(player2);							
				panelMenu.setVisible(false);
				
				Insert = new JTextField();
				Insert.setBounds(342, 122, 184, 30);
				panelNomes.add(Insert);
				Insert.setColumns(10);
				
				Insert2 = new JTextField();
				Insert2.setBounds(342, 172, 184, 30);
				panelNomes.add(Insert2);
				Insert2.setColumns(10);
				
				JButton btnInserir = new JButton("Inserir");
				btnInserir.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						String jogador = Insert.getText();
						String jogador2 = Insert2.getText();
						if(jogador.isEmpty()) {
							jogador = "Player1";
						}					
						if(jogador2.isEmpty()) {
							jogador2 = "Player2";
						}
												
						Panel panelMultiPlayer = new Panel();
						panelMultiPlayer.setBounds(0, 0, 737, 396);
						frame.getContentPane().add(panelMultiPlayer);
						panelMultiPlayer.setLayout(null);
						panelNomes.setVisible(false);
						
						////////////////////////////Criando os Botões//////////////////////////////////////////////////
						int cont = 0;
						for(int i = 0; i < 3; i++) {
							for(int j = 0; j < 3; j++) {
								btn[cont] = new JButton();
								panelMultiPlayer.add(btn[cont]);
								btn[cont].setBounds((115 * i) + 21, (115 * j) + 23, 90, 90 );
								btn[cont].setFont(new Font("Purisa", Font.BOLD, 40));
								btn[cont].setOpaque(false);
								btn[cont].setContentAreaFilled(false);
								btn[cont].setBorderPainted(false);
								cont++;
							}
						}						
						for(int i = 0; i < 9; i++) {
							clicks[i] = false;
						}					
						btn[0].addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								if(clicks[0] == false) {
									clicks[0] = true;
									simbolo(btn[0]);
								}
							}
						});
						btn[1].addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								if(clicks[1] == false) {
									clicks[1] = true;
									simbolo(btn[1]);
								}
							}
						});
						btn[2].addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								if(clicks[2] == false) {
									clicks[2] = true;
									simbolo(btn[2]);
								}
							}
						});
						btn[3].addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								if(clicks[3] == false) {
									clicks[3] = true;
									simbolo(btn[3]);
								}
							}
						});
						btn[4].addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								if(clicks[4] == false) {
									clicks[4] = true;
									simbolo(btn[4]);
								}
							}
						});
						btn[5].addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								if(clicks[5] == false) {
									clicks[5] = true;
									simbolo(btn[5]);
								}
							}
						});
						btn[6].addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								if(clicks[6] == false) {
									clicks[6] = true;
									simbolo(btn[6]);
								}
							}
						});
						btn[7].addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								if(clicks[7] == false) {
									clicks[7] = true;
									simbolo(btn[7]);
								}
							}
						});
						btn[8].addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								if(clicks[8] == false) {
									clicks[8] = true;
									simbolo(btn[8]);
								}
							}
						});
						////////////////////////////Criando os Botões//////////////////////////////////////////////////
						
						Canvas canvas = new Canvas();
						canvas.setForeground(Color.BLACK);
						canvas.setBackground(Color.BLACK);
						canvas.setBounds(120, 32, 11, 308);
						panelMultiPlayer.add(canvas);
						
						Canvas canvas_1 = new Canvas();
						canvas_1.setForeground(Color.BLACK);
						canvas_1.setBackground(Color.BLACK);
						canvas_1.setBounds(231, 32, 11, 308);
						panelMultiPlayer.add(canvas_1);
						
						Canvas canvas_2 = new Canvas();
						canvas_2.setForeground(Color.BLACK);
						canvas_2.setBackground(Color.BLACK);
						canvas_2.setBounds(29, 117, 321, 14);
						panelMultiPlayer.add(canvas_2);
						
						Canvas canvas_2_1 = new Canvas();
						canvas_2_1.setForeground(Color.BLACK);
						canvas_2_1.setBackground(Color.BLACK);
						canvas_2_1.setBounds(29, 238, 321, 14);
						panelMultiPlayer.add(canvas_2_1);
						
						JLabel lblJogador = new JLabel(jogador);
						lblJogador.setFont(new Font("Purisa", Font.BOLD, 26));
						lblJogador.setForeground(Color.RED);
						lblJogador.setBounds(389, 32, 139, 31);
						panelMultiPlayer.add(lblJogador);
						
						JLabel lblJogador2 = new JLabel(jogador2);
						lblJogador2.setForeground(Color.BLUE);
						lblJogador2.setFont(new Font("Purisa", Font.BOLD, 26));
						lblJogador2.setBounds(603, 32, 139, 31);
						panelMultiPlayer.add(lblJogador2);
						
						lblPontos = new JLabel("Pontos:" + pontosJogador);
						lblPontos.setForeground(Color.RED);
						lblPontos.setFont(new Font("Purisa", Font.BOLD, 20));
						lblPontos.setBounds(401, 97, 139, 31);
						panelMultiPlayer.add(lblPontos);
						
						lblPontos2 = new JLabel("Pontos:" + pontosIA);
						lblPontos2.setForeground(Color.BLUE);
						lblPontos2.setFont(new Font("Purisa", Font.BOLD, 20));
						lblPontos2.setBounds(586, 97, 139, 31);
						panelMultiPlayer.add(lblPontos2);
						
						lblEmpates = new JLabel("Empates:" + pontosEmpates);
						lblEmpates.setForeground(Color.MAGENTA);
						lblEmpates.setFont(new Font("Purisa", Font.BOLD, 22));
						lblEmpates.setBounds(486, 147, 139, 31);
						panelMultiPlayer.add(lblEmpates);
						
						JLabel lblX = new JLabel("X");
						lblX.setFont(new Font("Dialog", Font.BOLD, 20));
						lblX.setForeground(Color.MAGENTA);
						lblX.setBounds(533, 32, 23, 26);
						panelMultiPlayer.add(lblX);
						
						JButton btnVoltarAoMenu = new JButton("Voltar ao Menu");
						btnVoltarAoMenu.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							pontosJogador = 0;
							pontosIA = 0;
							pontosEmpates = 0;
							xo = false;
							panelMenu.setVisible(true);
							panelMultiPlayer.setVisible(false);
							}
						});
						btnVoltarAoMenu.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseEntered(MouseEvent e) {
							btnVoltarAoMenu.setFont(new Font("Purisa", Font.BOLD | Font.ITALIC, 36));
						}
						@Override
						public void mouseExited(MouseEvent e) {
							btnVoltarAoMenu.setFont(new Font("Purisa", Font.BOLD | Font.ITALIC, 26));
							}
						});
						btnVoltarAoMenu.setOpaque(false);
						btnVoltarAoMenu.setContentAreaFilled(false);
						btnVoltarAoMenu.setBorderPainted(false);
						btnVoltarAoMenu.setFont(new Font("Purisa", Font.BOLD | Font.ITALIC, 26));
						btnVoltarAoMenu.setForeground(Color.GREEN);
						btnVoltarAoMenu.setBounds(356, 218, 357, 83);
						panelMultiPlayer.add(btnVoltarAoMenu);
												
						JLabel backgroundCopy = new JLabel();
						backgroundCopy.setBounds(0, 0, 737, 396);
						backgroundCopy.setIcon(icon);
						panelMultiPlayer.add(backgroundCopy);
						}
					});
				btnInserir.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) {
						btnInserir.setFont(new Font("Purisa", Font.BOLD | Font.ITALIC, 26));
					}
					@Override
					public void mouseExited(MouseEvent e) {
						btnInserir.setFont(new Font("Purisa", Font.BOLD | Font.ITALIC, 20));
						}
					});
				btnInserir.setOpaque(false);
				btnInserir.setContentAreaFilled(false);
				btnInserir.setBorderPainted(false);
				btnInserir.setFont(new Font("Purisa", Font.BOLD | Font.ITALIC, 20));
				btnInserir.setForeground(Color.RED);
				btnInserir.setBounds(251, 228, 173, 40);
				panelNomes.add(btnInserir);
					
				JLabel backgroundCopy = new JLabel();
				backgroundCopy.setBounds(0, 0, 737, 396);
				backgroundCopy.setIcon(icon);
				panelNomes.add(backgroundCopy);
			}
		});
		btnMultiPlayer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnMultiPlayer.setFont(new Font("Purisa", Font.BOLD | Font.ITALIC, 36));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnMultiPlayer.setFont(new Font("Purisa", Font.BOLD | Font.ITALIC, 26));
			}
		});
		btnMultiPlayer.setForeground(Color.RED);
		btnMultiPlayer.setOpaque(false);//Deixa Transparente.
		btnMultiPlayer.setContentAreaFilled(false);//Deixa Transparente.
		btnMultiPlayer.setBorderPainted(false);//Deixa Transparente.
		btnMultiPlayer.setFont(new Font("Purisa", Font.BOLD | Font.ITALIC, 26));
		btnMultiPlayer.setBounds(198, 211, 318, 58);
		btnMultiPlayer.setBorder(null);
		panelMenu.add(btnMultiPlayer);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnExit.setFont(new Font("Purisa", Font.BOLD | Font.ITALIC, 36));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnExit.setFont(new Font("Purisa", Font.BOLD | Font.ITALIC, 26));
			}
		});
		btnExit.setForeground(Color.GREEN);
		btnExit.setOpaque(false);//Deixa Transparente.
		btnExit.setContentAreaFilled(false);//Deixa Transparente.
		btnExit.setBorderPainted(false);//Deixa Transparente.
		btnExit.setFont(new Font("Purisa", Font.BOLD, 26));
		btnExit.setBounds(273, 281, 181, 47);
		btnExit.setBorder(null);
		panelMenu.add(btnExit);
		
		JLabel lblDevelopedByRtkill = new JLabel("Developed By R1TKILL  Version: 2.0");
		lblDevelopedByRtkill.setFont(new Font("Dialog", Font.BOLD, 16));
		lblDevelopedByRtkill.setForeground(Color.MAGENTA);
		lblDevelopedByRtkill.setBounds(384, 369, 328, 15);
		panelMenu.add(lblDevelopedByRtkill);	
		
		/**Obs:O background deve ficar por ultimo, por se tratar de uma label que consome toda a tela acaba
		escondendo outros conponentes.*/
		
		//Criei uma label do tamanho do Painel e adicionei a ele.
		JLabel background = new JLabel();
		background.setBounds(0, 0, 737, 396);
		background.setIcon(icon);
		panelMenu.add(background);		
	}
	
	/////////**Outros métodos**\\\\\\\\\\
	private void jogadaPlayer(JButton btn) {
		btn.setForeground(Color.BLUE);
		btn.setText("X");
		resultado();
	}
	
	//Em Manuntenção.
	private void jogadaIA() {
		//Ganhar do player: primário.
		//Linhas da direita não tem o valor.
		if(btn[0].getText().equals("O") && btn[1].getText().equals("O") && btn[2].getText().equals("")) {if(clicks[2] == false) {clicks[2] = true; btn[2].setForeground(Color.RED); btn[2].setText("O");}}  
		else if(btn[3].getText().equals("O") && btn[4].getText().equals("O") && btn[5].getText().equals("")) {if(clicks[5] == false) {clicks[5] = true; btn[5].setForeground(Color.RED); btn[5].setText("O");}}
		else if(btn[6].getText().equals("O") && btn[7].getText().equals("O") && btn[8].getText().equals("")) {if(clicks[8] == false) {clicks[8] = true; btn[8].setForeground(Color.RED); btn[8].setText("O");}}
		//Linhas do meio não tem o valor.
		else if(btn[0].getText().equals("O") && btn[1].getText().equals("") && btn[2].getText().equals("O")) {if(clicks[1] == false) {clicks[1] = true; btn[1].setForeground(Color.RED); btn[1].setText("O");}}
		else if(btn[3].getText().equals("O") && btn[4].getText().equals("") && btn[5].getText().equals("O")) {if(clicks[4] == false) {clicks[4] = true; btn[4].setForeground(Color.RED); btn[4].setText("O");}}
		else if(btn[6].getText().equals("O") && btn[7].getText().equals("") && btn[8].getText().equals("O")) {if(clicks[7] == false) {clicks[7] = true; btn[7].setForeground(Color.RED); btn[7].setText("O");}}
		//Casas da esquerda não tem o valor.
		else if(btn[0].getText().equals("") && btn[1].getText().equals("O") && btn[2].getText().equals("O")) {if(clicks[0] == false) {clicks[0] = true; btn[0].setForeground(Color.RED); btn[0].setText("O");}}
		else if(btn[3].getText().equals("") && btn[4].getText().equals("O") && btn[5].getText().equals("O")) {if(clicks[3] == false) {clicks[3] = true; btn[3].setForeground(Color.RED); btn[3].setText("O");}}
		else if(btn[6].getText().equals("") && btn[7].getText().equals("O") && btn[8].getText().equals("O")) {if(clicks[6] == false) {clicks[6] = true; btn[6].setForeground(Color.RED); btn[6].setText("O");}}
		//Colunas.
		//Colunas da direita não tem o valor
		else if(btn[0].getText().equals("O") && btn[3].getText().equals("O") && btn[6].getText().equals("")) {if(clicks[6] == false) {clicks[6] = true; btn[6].setForeground(Color.RED); btn[6].setText("O");}}
		else if(btn[1].getText().equals("O") && btn[4].getText().equals("O") && btn[7].getText().equals("")) {if(clicks[7] == false) {clicks[7] = true; btn[7].setForeground(Color.RED); btn[7].setText("O");}}
		else if(btn[2].getText().equals("O") && btn[5].getText().equals("O") && btn[8].getText().equals("")) {if(clicks[8] == false) {clicks[8] = true; btn[8].setForeground(Color.RED); btn[8].setText("O");}}
		//Colunas do meio não tem o valor.
		else if(btn[0].getText().equals("O") && btn[3].getText().equals("") && btn[6].getText().equals("O")) {if(clicks[3] == false) {clicks[3] = true; btn[3].setForeground(Color.RED); btn[3].setText("O");}}
		else if(btn[1].getText().equals("O") && btn[4].getText().equals("") && btn[7].getText().equals("O")) {if(clicks[4] == false) {clicks[4] = true; btn[4].setForeground(Color.RED); btn[4].setText("O");}}
		else if(btn[2].getText().equals("O") && btn[5].getText().equals("") && btn[8].getText().equals("O")) {if(clicks[5] == false) {clicks[5] = true; btn[5].setForeground(Color.RED); btn[5].setText("O");}}
		//Colunas da esquerda não tem o valor.
		else if(btn[0].getText().equals("") && btn[3].getText().equals("O") && btn[6].getText().equals("O")) {if(clicks[0] == false) {clicks[0] = true; btn[0].setForeground(Color.RED); btn[0].setText("O");}}
		else if(btn[1].getText().equals("") && btn[4].getText().equals("O") && btn[7].getText().equals("O")) {if(clicks[1] == false) {clicks[1] = true; btn[1].setForeground(Color.RED); btn[1].setText("O");}}
		else if(btn[2].getText().equals("") && btn[5].getText().equals("O") && btn[8].getText().equals("O")) {if(clicks[2] == false) {clicks[2] = true; btn[2].setForeground(Color.RED); btn[2].setText("O");}}
		//Diagonais.
		//Diagonal esquerda.
		else if(btn[0].getText().equals("O") && btn[4].getText().equals("O") && btn[8].getText().equals("")) {if(clicks[8] == false) {clicks[8] = true; btn[8].setForeground(Color.RED); btn[8].setText("O");}}
		else if(btn[0].getText().equals("O") && btn[4].getText().equals("") && btn[8].getText().equals("O")) {if(clicks[4] == false) {clicks[4] = true; btn[4].setForeground(Color.RED); btn[4].setText("O");}}
		else if(btn[0].getText().equals("")  && btn[4].getText().equals("O") && btn[8].getText().equals("O")) {if(clicks[0] == false) {clicks[0] = true; btn[0].setForeground(Color.RED); btn[0].setText("O");}}
		//Diagonal direita.
		else if(btn[2].getText().equals("O") && btn[4].getText().equals("O") && btn[6].getText().equals("")) {if(clicks[6] == false) {clicks[6] = true; btn[6].setForeground(Color.RED); btn[6].setText("O");}}
		else if(btn[2].getText().equals("O") && btn[4].getText().equals("") && btn[6].getText().equals("O")) {if(clicks[4] == false) {clicks[4] = true; btn[4].setForeground(Color.RED); btn[4].setText("O");}}
		else if(btn[2].getText().equals("") && btn[4].getText().equals("O") && btn[6].getText().equals("O")) {if(clicks[2] == false) {clicks[2] = true; btn[2].setForeground(Color.RED); btn[2].setText("O");}}			
		
		//Fecha as vitorias do player: Secundário.
		//Linhas da direita não tem o valor.
		else if(btn[0].getText().equals("X") && btn[1].getText().equals("X") && btn[2].getText().equals("")) {if(clicks[2] == false) {clicks[2] = true; btn[2].setForeground(Color.RED); btn[2].setText("O");}}
		else if(btn[3].getText().equals("X") && btn[4].getText().equals("X") && btn[5].getText().equals("")) {if(clicks[5] == false) {clicks[5] = true; btn[5].setForeground(Color.RED); btn[5].setText("O");}}
		else if(btn[6].getText().equals("X") && btn[7].getText().equals("X") && btn[8].getText().equals("")) {if(clicks[8] == false) {clicks[8] = true; btn[8].setForeground(Color.RED); btn[8].setText("O");}}
		//Linhas do meio não tem o valor.
		else if(btn[0].getText().equals("X") && btn[1].getText().equals("") && btn[2].getText().equals("X")) {if(clicks[1] == false) {clicks[1] = true; btn[1].setForeground(Color.RED); btn[1].setText("O");}}
		else if(btn[3].getText().equals("X") && btn[4].getText().equals("") && btn[5].getText().equals("X")) {if(clicks[4] == false) {clicks[4] = true; btn[4].setForeground(Color.RED); btn[4].setText("O");}}
		else if(btn[6].getText().equals("X") && btn[7].getText().equals("") && btn[8].getText().equals("X")) {if(clicks[7] == false) {clicks[7] = true; btn[7].setForeground(Color.RED); btn[7].setText("O");}}
		//Casas da esquerda não tem o valor.
		else if(btn[0].getText().equals("") && btn[1].getText().equals("X") && btn[2].getText().equals("X")) {if(clicks[0] == false) {clicks[0] = true; btn[0].setForeground(Color.RED); btn[0].setText("O");}}
		else if(btn[3].getText().equals("") && btn[4].getText().equals("X") && btn[5].getText().equals("X")) {if(clicks[3] == false) {clicks[3] = true; btn[3].setForeground(Color.RED); btn[3].setText("O");}}
		else if(btn[6].getText().equals("") && btn[7].getText().equals("X") && btn[8].getText().equals("X")) {if(clicks[6] == false) {clicks[6] = true; btn[6].setForeground(Color.RED); btn[6].setText("O");}}
		//Colunas.
		//Colunas da direita não tem o valor
		else if(btn[0].getText().equals("X") && btn[3].getText().equals("X") && btn[6].getText().equals("")) {if(clicks[6] == false) {clicks[6] = true; btn[6].setForeground(Color.RED); btn[6].setText("O");}}
		else if(btn[1].getText().equals("X") && btn[4].getText().equals("X") && btn[7].getText().equals("")) {if(clicks[7] == false) {clicks[7] = true; btn[7].setForeground(Color.RED); btn[7].setText("O");}}
		else if(btn[2].getText().equals("X") && btn[5].getText().equals("X") && btn[8].getText().equals("")) {if(clicks[8] == false) {clicks[8] = true; btn[8].setForeground(Color.RED); btn[8].setText("O");}}
		//Colunas do meio não tem o valor.
		else if(btn[0].getText().equals("X") && btn[3].getText().equals("") && btn[6].getText().equals("X")) {if(clicks[3] == false) {clicks[3] = true; btn[3].setForeground(Color.RED); btn[3].setText("O");}}
		else if(btn[1].getText().equals("X") && btn[4].getText().equals("") && btn[7].getText().equals("X")) {if(clicks[4] == false) {clicks[4] = true; btn[4].setForeground(Color.RED); btn[4].setText("O");}}
		else if(btn[2].getText().equals("X") && btn[5].getText().equals("") && btn[8].getText().equals("X")) {if(clicks[5] == false) {clicks[5] = true; btn[5].setForeground(Color.RED); btn[5].setText("O");}}
		//Colunas da esquerda não tem o valor.
		else if(btn[0].getText().equals("") && btn[3].getText().equals("X") && btn[6].getText().equals("X")) {if(clicks[0] == false) {clicks[0] = true; btn[0].setForeground(Color.RED); btn[0].setText("O");}}
		else if(btn[1].getText().equals("") && btn[4].getText().equals("X") && btn[7].getText().equals("X")) {if(clicks[1] == false) {clicks[1] = true; btn[1].setForeground(Color.RED); btn[1].setText("O");}}
		else if(btn[2].getText().equals("") && btn[5].getText().equals("X") && btn[8].getText().equals("X")) {if(clicks[2] == false) {clicks[2] = true; btn[2].setForeground(Color.RED); btn[2].setText("O");}}
		//Diagonais.
		//Diagonal esquerda.
		else if(btn[0].getText().equals("X") && btn[4].getText().equals("X") && btn[8].getText().equals("")) {if(clicks[8] == false) {clicks[8] = true; btn[8].setForeground(Color.RED); btn[8].setText("O");}}
		else if(btn[0].getText().equals("X") && btn[4].getText().equals("") && btn[8].getText().equals("X")) {if(clicks[4] == false) {clicks[4] = true; btn[4].setForeground(Color.RED); btn[4].setText("O");}}
		else if(btn[0].getText().equals("") && btn[4].getText().equals("X") && btn[8].getText().equals("X")) {if(clicks[0] == false) {clicks[0] = true; btn[0].setForeground(Color.RED); btn[0].setText("O");}}
		//Diagonal direita.
		else if(btn[2].getText().equals("X") && btn[4].getText().equals("X") && btn[6].getText().equals("")) {if(clicks[6] == false) {clicks[6] = true; btn[6].setForeground(Color.RED); btn[6].setText("O");}}
		else if(btn[2].getText().equals("X") && btn[4].getText().equals("") && btn[6].getText().equals("X")) {if(clicks[4] == false) {clicks[4] = true; btn[4].setForeground(Color.RED); btn[4].setText("O");}}
		else if(btn[2].getText().equals("") && btn[4].getText().equals("X") && btn[6].getText().equals("X")) {if(clicks[2] == false) {clicks[2] = true; btn[2].setForeground(Color.RED); btn[2].setText("O");}}
		else {
			for(int i = 0; i < 9; i++) {
				int randomPosition = randon.nextInt(9);//de 0 até 8.
				if(clicks[randomPosition] == false){
					clicks[randomPosition] = true;
					btn[randomPosition].setForeground(Color.RED);
					btn[randomPosition].setText("O");
					break;
				}
			}
		}		
		resultado();
	}
	
	private void simbolo(JButton btn) {
		if(xo) {
			btn.setForeground(Color.RED);
			btn.setText("O");
			xo = false;
		}
		else {
			btn.setForeground(Color.BLUE);
			btn.setText("X");
			xo = true;
		}		
		resultado();
	}
	
	private void resultado()
	{
		int cont = 0;	
		for(int i = 0; i < 9; i++) {
			if(clicks[i] == true) {
				cont++;
			}
		}
		
		if((btn[0].getText().equals("X") && btn[1].getText().equals("X") && btn[2].getText().equals("X")) ||
		   (btn[3].getText().equals("X") && btn[4].getText().equals("X") && btn[5].getText().equals("X")) ||
		   (btn[6].getText().equals("X") && btn[7].getText().equals("X") && btn[8].getText().equals("X")) ||
		   (btn[0].getText().equals("X") && btn[3].getText().equals("X") && btn[6].getText().equals("X")) ||
		   (btn[1].getText().equals("X") && btn[4].getText().equals("X") && btn[7].getText().equals("X")) ||
		   (btn[2].getText().equals("X") && btn[5].getText().equals("X") && btn[8].getText().equals("X")) ||
		   (btn[2].getText().equals("X") && btn[4].getText().equals("X") && btn[6].getText().equals("X")) ||
		   (btn[0].getText().equals("X") && btn[4].getText().equals("X") && btn[8].getText().equals("X")))
		{
			JOptionPane.showMessageDialog(null, "Parábens o jogador 1 venceu.");
			pontosJogador++;
			placar();
			clear();
		}		
		else if((btn[0].getText().equals("O") && btn[1].getText().equals("O") && btn[2].getText().equals("O")) ||
				(btn[3].getText().equals("O") && btn[4].getText().equals("O") && btn[5].getText().equals("O")) ||
				(btn[6].getText().equals("O") && btn[7].getText().equals("O") && btn[8].getText().equals("O")) ||
				(btn[0].getText().equals("O") && btn[3].getText().equals("O") && btn[6].getText().equals("O")) ||
				(btn[1].getText().equals("O") && btn[4].getText().equals("O") && btn[7].getText().equals("O")) ||
				(btn[2].getText().equals("O") && btn[5].getText().equals("O") && btn[8].getText().equals("O")) ||
				(btn[2].getText().equals("O") && btn[4].getText().equals("O") && btn[6].getText().equals("O")) ||
				(btn[0].getText().equals("O") && btn[4].getText().equals("O") && btn[8].getText().equals("O")))
		{
			JOptionPane.showMessageDialog(null, "Parábens o jogador 2 venceu.");
			pontosIA++;	
			placar();
			clear();
		}		
		else if (cont == 9) {
			JOptionPane.showMessageDialog(null, "Empatou.");
			pontosEmpates++;
			placar();
			clear();
		}
	}
	
	private void clear() {
		for(int i = 0; i < 9; i++) {
			btn[i].setText("");
			clicks[i] = false;
			xo = false;
			if(intervalo)
				intervalo = true;
		}
	}
	
	private void placar() {
		lblPontos.setText("Pontos:" + pontosJogador);	
		lblPontos2.setText("Pontos:" + pontosIA);
		lblEmpates.setText("Empates:" + pontosEmpates);
	}
}
