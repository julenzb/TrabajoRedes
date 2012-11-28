package Ventanas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import util.SocketManager;
import Excepciones.ActualizarException;
import Excepciones.GPSaOFFException;
import Excepciones.GPSaONException;
import Excepciones.SenaOFFException;
import Excepciones.SenaONException;

public class ObtenerDatos extends JFrame implements ActionListener, MouseListener, WindowListener{

	
		private JButton actualizar;
		private JLabel idt;
		private JLabel Egps;
		private JLabel Etren;
		private JLabel vacia;
		
		private JLabel titulotable;
		
		private JComboBox id;
		private JTextField egps;
		private JTextField etren;
		
		private JTable sen;
		private DefaultTableModel modelotabla;
		private JScrollPane scrolltable;
		
		private JButton salir;
		
		private JButton SenON;
		private JButton SenOFF;

		private JButton EstGpsON;
		private JButton EstGpsOFF;
		
		private JButton GPS;
		
		private JPanel norte;
		private JPanel sur;
		private JPanel centro;
		private JPanel pizq;
		private JPanel pder;
		
		private SocketManager sm;
		
		private JTable MS;
		private DefaultTableModel modelotabla1;
		private JScrollPane scrolltable1;
	
		private JButton ValorActual;
		
		private String id_Sen;
		private String esta;
		
		public ObtenerDatos(SocketManager sm){
			this.sm=sm;
			this.actualizar=new JButton("Actualizar");
			idt = new JLabel("         ID Tren: ");
			Egps = new JLabel("        Estado GPS: ");
			Etren = new JLabel("        Estado Tren: ");
			vacia = new JLabel("");
			
			titulotable = new JLabel("                         Lista de sensores:");
			titulotable.setForeground(Color.black);
			
			id = new JComboBox(); 
			egps = new JTextField(20);
			etren = new JTextField(20);
			
			GPS = new JButton("Obtener coordenadas GPS");
			
			SenON = new JButton("Cambiar Sensor a ON"); 
			SenON.setEnabled(false);
			SenOFF = new JButton("Cambiar Sensor a OFF"); 
			SenOFF.setEnabled(false);
			SenON.setEnabled(false);
			
			norte = new JPanel();
			centro = new JPanel();
			sur = new JPanel();
			pizq= new JPanel();
			pder = new JPanel();
			
			pizq.setLayout(new FlowLayout(FlowLayout.LEFT));
			pder.setLayout(new FlowLayout(FlowLayout.RIGHT));
			
			modelotabla = new DefaultTableModel();
			
			modelotabla.addColumn("ID");
			modelotabla.addColumn("Descripción");
			modelotabla.addColumn("Estado");
			modelotabla.addColumn("Medidas");
			
			sen = new JTable(modelotabla);
			scrolltable = new JScrollPane(sen);
			
			
			ValorActual =  new JButton("Valor actual Sensor");
			salir =new JButton("Salir");
			
			EstGpsON= new JButton("Cambiar GPS a ON");
			EstGpsOFF= new JButton("Cambiar GPS a OFF");
		
			
			modelotabla1 = new DefaultTableModel();
			
			modelotabla1.addColumn("Fecha");
			modelotabla1.addColumn("Hora");
			modelotabla1.addColumn("Coordenadas");
			modelotabla1.addColumn("Valor");
			
			MS = new JTable(modelotabla1);
			scrolltable1 = new JScrollPane(MS);
			
			norte.add(idt);
			norte.add(id);
			norte.add(Egps);
			norte.add(egps);
			norte.add(Etren);
			norte.add(etren);
			norte.add(vacia);
			norte.add(titulotable);
			JPanel actual=new JPanel();
			actual.add(actualizar);
			norte.add(actual);
			
			pizq.add(scrolltable);
			pder.add(scrolltable1);
			
			centro.add(pizq);
			centro.add(pder);
			centro.add(vacia);
			
			sur.add(SenON);
			sur.add(SenOFF);
			sur.add(GPS);
			sur.add(EstGpsON);
			sur.add(EstGpsOFF);
			sur.add(ValorActual);
			sur.add(salir);
				
			norte.setLayout(new GridLayout(4,2));
			this.setLayout(new BorderLayout());
			this.add("North", norte);
			this.add("Center", centro);
			this.add("South",sur);
			this.setSize(1100,700);
			this.addWindowListener(this);
			this.setLocationRelativeTo(null);
			actualizar.addActionListener(this);
			id.addActionListener(this);
			sen.addMouseListener(this);
			salir.addActionListener(this);
			ValorActual.addActionListener(this);
			EstGpsON.addActionListener(this);
			EstGpsOFF.addActionListener(this);
			GPS.addActionListener(this);
			SenON.addActionListener(this);
			SenOFF.addActionListener(this);
			
			id.addItem("Seleccione un id_tren:");//Primer dato del jcombobox vacio, para que sea necesario que el usuario escoja un ID_Tren
			
			RellenarIDTrenes();
			
		}

			private void RellenarIDTrenes() {
			// TODO Auto-generated method stub
			try {
				sm.Escribir("IDTREN"+'\n');
				boolean seguir=true;
				while(seguir)
				{
					System.out.println("Estoy dentro del bucle");
					String sentencia=sm.Leer();
					System.out.println(sentencia);
					StringTokenizer token=new StringTokenizer(sentencia);
					if(token.nextToken().equals("222"))
						seguir=false;
					else
					id.addItem(sentencia);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog (null, "ERROR EL ADMIN LE HA EXPULSADO", "Operacion bloqueada", JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
			}
		}
			
			private void RellenarTablaSensores(String senRecibido){
				StringTokenizer token = new StringTokenizer(senRecibido);
				String a1 = token.nextToken();
				String a2 = token.nextToken();
				String a3 = token.nextToken();
				String [] atributos = {a1,a2,a3,"Hacer Click aquí"}; //Primer dato del jcombobox vacio
				modelotabla.addRow(atributos); /**introduzco las filas de la Jtable*/
			}
				
			void RellenarTablaMedidas(String senRecibido){
				StringTokenizer token = new StringTokenizer(senRecibido);
				String a1 = token.nextToken();
				String a2 = token.nextToken();
				String a3 = token.nextToken();
				String a4 = token.nextToken();
				String [] atributos = {a1,a2,a3,a4}; 
				modelotabla1.addRow(atributos); /**introduzco las filas de la Jtable*/
			}


			public static void main (String []args){
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
					/**cambio de aspecto a la ventana*/;
				} catch (Exception e) {
				}
				
				ObtenerDatos o = new ObtenerDatos(null);
				o.setVisible(true);
			}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			if (e.getSource()== id){
				
				SenOFF.setEnabled(false);
				SenON.setEnabled(false);
				
				if (id.getSelectedItem().toString().equals("Seleccione un id_tren:")){
					GPS.setEnabled(false);	
					EstGpsON.setEnabled(false);
					EstGpsOFF.setEnabled(false);
					actualizar.setEnabled(false);
				}
				else {GPS.setEnabled(true);	
				EstGpsON.setEnabled(true);
				EstGpsOFF.setEnabled(true);
				actualizar.setEnabled(true);
				}
				
				modelotabla1.setNumRows(0);
				modelotabla.setNumRows(0);
				egps.setText("");
				etren.setText("");
				if(id.getSelectedIndex()!=0)
				{
				RellenarCampos();
				boolean seguir=true;
				String Codigo = id.getSelectedItem().toString();
				String sentenciaAEnviar="";
			    String senRecibido="";
				sentenciaAEnviar = "LISTSENSOR " + Codigo ;
				try {
					sm.Escribir(sentenciaAEnviar+'\n');
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog (null, "ERROR EL ADMIN LE HA EXPULSADO", "Operacion bloqueada", JOptionPane.INFORMATION_MESSAGE);
					System.exit(0);
				}
				
				while(seguir)
				{
					try {
						senRecibido= sm.Leer();
						System.out.println("Sentencia recibida:" +senRecibido);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog (null, "ERROR EL ADMIN LE HA EXPULSADO", "Operacion bloqueada", JOptionPane.INFORMATION_MESSAGE);
						System.exit(0);
					}
					
					StringTokenizer token=new StringTokenizer(senRecibido);
					if(token.nextToken().equals("222"))
					{
						seguir=false;
						System.out.println("FIN, lista de sensores recibida");
					}
					else
					RellenarTablaSensores(senRecibido);
				}
		
			}
			}
			
			else
			if (e.getSource()==salir){
				String sentenciaAEnviar="";
			    String senRecibido="";
				try {
					sentenciaAEnviar = "SALIR";
					sm.Escribir(sentenciaAEnviar+'\n');
					System.out.println("Sentencia enviada: "+sentenciaAEnviar);
					senRecibido= sm.Leer();
					System.out.println("Sentencia recibida: "+senRecibido);
					StringTokenizer token=new StringTokenizer(senRecibido);
					if(token.nextToken().equals("218"))
					{
						System.exit(0);
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog (null, "ERROR EL ADMIN LE HA EXPULSADO", "Operacion bloqueada", JOptionPane.INFORMATION_MESSAGE);
					System.exit(0);
				}
			}
			
			
			if (e.getSource()== ValorActual){
				String idt = id.getSelectedItem().toString();
					TablaDesc td = new TablaDesc(sm,idt,this);
					td.setVisible(true);
					this.setVisible(false);
				}
			
			if (e.getSource()== EstGpsON){
				String id_tren = id.getSelectedItem().toString();
				
				if (egps.getText().equals("OFF")){
				String senRecibida = "";
				String sentenciaAEnviar = "ONGPS " + id_tren ;
					try {
						sm.Escribir(sentenciaAEnviar+'\n');
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog (null, "ERROR EL ADMIN LE HA EXPULSADO", "Operacion bloqueada", JOptionPane.INFORMATION_MESSAGE);
						System.exit(0);
					}
					try {
						senRecibida= sm.Leer();
							JOptionPane.showMessageDialog(null, senRecibida," ", 0);
							egps.setText("ON");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog (null, "ERROR EL ADMIN LE HA EXPULSADO", "Operacion bloqueada", JOptionPane.INFORMATION_MESSAGE);
						System.exit(0);
					}
					System.out.println("Sentencia recibida:" +senRecibida);
				
			
				}
				else if(egps.getText().equals("ON")){
					try {
						throw new GPSaONException();
					} catch (GPSaONException e1) {
						// TODO Auto-generated catch block
						System.out.println("salta excepción");
					}
				}
				}
				
			
			if (e.getSource()== EstGpsOFF){
				String id_tren = id.getSelectedItem().toString();
				
				if (egps.getText().equals("ON")){
				String senRecibida = "";
				String sentenciaAEnviar = "OFFGPS " + id_tren ;
					try {
						sm.Escribir(sentenciaAEnviar+'\n');	
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog (null, "ERROR EL ADMIN LE HA EXPULSADO", "Operacion bloqueada", JOptionPane.INFORMATION_MESSAGE);
						System.exit(0);
					}
					try {
						senRecibida= sm.Leer();
							JOptionPane.showMessageDialog(null, senRecibida," ", 0);
							egps.setText("OFF");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog (null, "ERROR EL ADMIN LE HA EXPULSADO", "Operacion bloqueada", JOptionPane.INFORMATION_MESSAGE);
						System.exit(0);
					}
					System.out.println("Sentencia recibida:" +senRecibida);
				}
				
				else if(egps.getText().equals("OFF")){
						try {
							throw new GPSaOFFException();
						} catch (GPSaOFFException e1) {
							// TODO Auto-generated catch block
							System.out.println("salta excepción");
						}
				}
			}
			
			if (e.getSource() == GPS){
				String sentenciaAEnviar = "";
				String senRecibido = "";
				sentenciaAEnviar = "GET_LOC";
				try {
					sm.Escribir(sentenciaAEnviar+'\n');
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog (null, "ERROR EL ADMIN LE HA EXPULSADO", "Operacion bloqueada", JOptionPane.INFORMATION_MESSAGE);
					System.exit(0);
				}
				System.out.println("Sentencia enviada: "+sentenciaAEnviar);
				try {
					senRecibido= sm.Leer();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog (null, "ERROR EL ADMIN LE HA EXPULSADO", "Operacion bloqueada", JOptionPane.INFORMATION_MESSAGE);
					System.exit(0);
				}
				
				System.out.println("Sentencia recibida: "+senRecibido);
				StringTokenizer token=new StringTokenizer(senRecibido);
				token.nextToken();
				token.nextToken();
				String valor = token.nextToken();
				GPS g = new GPS (valor);
				g.setVisible(true);
			} 
		
			
			if(e.getSource()==SenON){
				if (esta.equals("OFF")){
						String senRecibida = "";
						String sentenciaAEnviar = "ON " + id_Sen ;
								try {
									sm.Escribir(sentenciaAEnviar+'\n');
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									JOptionPane.showMessageDialog (null, "ERROR EL ADMIN LE HA EXPULSADO", "Operacion bloqueada", JOptionPane.INFORMATION_MESSAGE);
									System.exit(0);
								}
								System.out.println(sentenciaAEnviar);

								try {
									senRecibida= sm.Leer();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									JOptionPane.showMessageDialog (null, "ERROR EL ADMIN LE HA EXPULSADO", "Operacion bloqueada", JOptionPane.INFORMATION_MESSAGE);
									System.exit(0);
								}
									JOptionPane.showMessageDialog(null, senRecibida," ", 0);
									SenOFF.setEnabled(false);
									SenON.setEnabled(false);
								modelotabla.setNumRows(0);
								boolean seguir=true;
								String Codigo = id.getSelectedItem().toString();
								String sentenciaEnviar="";
							    String senRecibido="";
								sentenciaAEnviar = "LISTSENSOR " + Codigo ;
								try {
									sm.Escribir(sentenciaAEnviar+'\n');
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									JOptionPane.showMessageDialog (null, "ERROR EL ADMIN LE HA EXPULSADO", "Operacion bloqueada", JOptionPane.INFORMATION_MESSAGE);
									System.exit(0);
								}
								
								while(seguir)
								{
									try {
										senRecibido= sm.Leer();
										System.out.println("Sentencia recibida:" +senRecibido);
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										JOptionPane.showMessageDialog (null, "ERROR EL ADMIN LE HA EXPULSADO", "Operacion bloqueada", JOptionPane.INFORMATION_MESSAGE);
										System.exit(0);
									}
									
									StringTokenizer token=new StringTokenizer(senRecibido);
									if(token.nextToken().equals("222"))
									{
										seguir=false;
										System.out.println("FIN, lista de sensores recibida");
									}
									else
									RellenarTablaSensores(senRecibido);
								}
					}
				else if (esta.equals("ON")){
					try {
						throw new SenaONException();
					} catch (SenaONException e1) {
						// TODO Auto-generated catch block
						System.out.println("salta excepción");
					}
					SenOFF.setEnabled(false);
					SenON.setEnabled(false);
				}
			}
			
			else
				if(e.getSource()==actualizar)
				{
					String id_tren = id.getSelectedItem().toString();
					try {
						sm.Escribir("ComprobarEstadoTren "+id_tren+'\n');
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					String senRecibido = "";
					try {
						senRecibido = sm.Leer();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					System.out.println("Sentencia recibida:" +senRecibido);
					if (senRecibido.equals("En mantenimiento")){
					try {
						sm.Escribir("ACTUALIZAR"+'\n');
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog (null, "ERROR EL ADMIN LE HA EXPULSADO", "Operacion bloqueada", JOptionPane.INFORMATION_MESSAGE);
						System.exit(0);
					}
					}
					
					else if(senRecibido != "En mantenimiento"){
						try {
							throw new ActualizarException();
						} catch (ActualizarException e1) {
							// TODO Auto-generated catch block
							System.out.println();
						}
					}
				}
			
			if(e.getSource()==SenOFF){
				if (esta.equals("ON")){
						String senRecibida = "";
						String sentenciaAEnviar = "OFF " + id_Sen ;
								try {
									sm.Escribir(sentenciaAEnviar+'\n');
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									JOptionPane.showMessageDialog (null, "ERROR EL ADMIN LE HA EXPULSADO", "Operacion bloqueada", JOptionPane.INFORMATION_MESSAGE);
									System.exit(0);
								}
								System.out.println(sentenciaAEnviar);

								try {
									senRecibida= sm.Leer();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									JOptionPane.showMessageDialog (null, "ERROR EL ADMIN LE HA EXPULSADO", "Operacion bloqueada", JOptionPane.INFORMATION_MESSAGE);
									System.exit(0);
								}
									JOptionPane.showMessageDialog(null, senRecibida," ", 0);
									SenOFF.setEnabled(false);
									SenON.setEnabled(false);
									modelotabla.setNumRows(0);
									boolean seguir=true;
									String Codigo = id.getSelectedItem().toString();
									String sentenciaEnviar="";
								    String senRecibido="";
									sentenciaAEnviar = "LISTSENSOR " + Codigo ;
									try {
										sm.Escribir(sentenciaAEnviar+'\n');
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										JOptionPane.showMessageDialog (null, "ERROR EL ADMIN LE HA EXPULSADO", "Operacion bloqueada", JOptionPane.INFORMATION_MESSAGE);
										System.exit(0);
									}
									
									while(seguir)
									{
										try {
											senRecibido= sm.Leer();
											System.out.println("Sentencia recibida:" +senRecibido);
										} catch (IOException e1) {
											// TODO Auto-generated catch block
											JOptionPane.showMessageDialog (null, "ERROR EL ADMIN LE HA EXPULSADO", "Operacion bloqueada", JOptionPane.INFORMATION_MESSAGE);
											System.exit(0);
										}
										
										StringTokenizer token=new StringTokenizer(senRecibido);
										if(token.nextToken().equals("222"))
										{
											seguir=false;
											System.out.println("FIN, lista de sensores recibida");
										}
										else
										RellenarTablaSensores(senRecibido);
									}
								}
									else if (esta.equals("OFF")){
										try {
											throw new SenaOFFException();
										} catch (SenaOFFException e1) {
											// TODO Auto-generated catch block
											System.out.println("salta excepción");
										}
										SenOFF.setEnabled(false);
										SenON.setEnabled(false);
									}
							}
			
			}
				
		
		private void RellenarCampos() {
			// TODO Auto-generated method stub
			String sentenciaAEnviar="";
		    String senRecibido="";
			try {
				sentenciaAEnviar = "ESTADOTREN " + id.getSelectedItem().toString();
				sm.Escribir(sentenciaAEnviar+'\n');
				System.out.println("Sentencia enviada: "+sentenciaAEnviar);
				senRecibido= sm.Leer();
				System.out.println("Sentencia recibida:" +senRecibido);
				etren.setText(senRecibido);
				sentenciaAEnviar = "ESTADOGPS " + id.getSelectedItem().toString();
				sm.Escribir(sentenciaAEnviar+'\n');
				senRecibido= sm.Leer();
				System.out.println("Sentencia recibida:" +senRecibido);
				egps.setText(senRecibido);
			
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog (null, "ERROR EL ADMIN LE HA EXPULSADO", "Operacion bloqueada", JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
			}
			
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			modelotabla1.setNumRows(0);
			int click = sen.rowAtPoint(e.getPoint()); // fila que selecciono con el ratón
			
			System.out.println(click);
			this.id_Sen = ((String)modelotabla.getValueAt(click, 0));// 0 es el número de columna, quiero coger el id_sensor
			String tiposen = ((String)modelotabla.getValueAt(click, 1));// quiero coger el tipo del sensor
			this.esta = ((String)modelotabla.getValueAt(click, 2));
			
				boolean seguir = true;
				String sentenciaAEnviar="";
			    String senRecibida="";
				sentenciaAEnviar = "HISTORICO " + id_Sen ;
				try {
					sm.Escribir(sentenciaAEnviar+'\n');
					System.out.println(sentenciaAEnviar);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog (null, "ERROR EL ADMIN LE HA EXPULSADO", "Operacion bloqueada", JOptionPane.INFORMATION_MESSAGE);
					System.exit(0);
				}
				while(seguir)
				{
					try {
						senRecibida= sm.Leer();
						System.out.println("Sentencia recibida:" +senRecibida);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog (null, "ERROR EL ADMIN LE HA EXPULSADO", "Operacion bloqueada", JOptionPane.INFORMATION_MESSAGE);
						System.exit(0);
					}
					
					StringTokenizer token=new StringTokenizer(senRecibida);
					if(token.nextToken().equals("222"))
					{
						seguir=false;
						System.out.println("FIN, lista de medidas recibida");
					}
					else
						RellenarTablaMedidas(senRecibida);	
				}
				
					SenOFF.setEnabled(true);
					SenON.setEnabled(true);
					
		}
		

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowActivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowClosed(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowClosing(WindowEvent e) {
			// TODO Auto-generated method stub
			String sentenciaAEnviar="";
		    String senRecibido="";
			try {
				sentenciaAEnviar = "SALIR";
				sm.Escribir(sentenciaAEnviar+'\n');
				System.out.println("Sentencia enviada: "+sentenciaAEnviar);
				senRecibido= sm.Leer();
				System.out.println("Sentencia recibida: "+senRecibido);
				StringTokenizer token=new StringTokenizer(senRecibido);
				if(token.nextToken().equals("218"))
				{
					System.exit(0);
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog (null, "ERROR EL ADMIN LE HA EXPULSADO", "Operacion bloqueada", JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
			}
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowIconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowOpened(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		
}
