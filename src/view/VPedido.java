package view;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;

import datos.Descuento;
import datos.Establecimiento;
import datos.Menu;
import controller.factorias.DescuentoADFactory;
import controller.factorias.EstablecimientoADFactory;
import controller.factorias.MenuADFactory;
import controller.factorias.PedidoADFactory;
import controller.factorias.ProductoADFactory;
import resources.IconCellRender;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Component;
import javax.swing.JSeparator;
import java.awt.GridLayout;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.time.LocalDate;
import java.awt.Point;
import java.awt.Cursor;

import javax.swing.JTable;
import javax.swing.JScrollPane;

import datos.Producto;
import exceptions.GestorExcepciones;
import resources.fuentes.Fuentes;
import users.Cliente;
import users.Usuarie;
import datos.NombrePrecio;
import datos.Pedido;

import javax.swing.JTextField;
import javax.swing.JComboBox;

public class VPedido extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3378271115951639896L;

	private final JPanel contentPanel = new JPanel();

	private JButton btnAtras;
	private JDialog vMenuMenu;
	private int tablaActual = 0;
	private static JButton btnX;
	private static JButton btnMenus;
	private static JButton btnComida;
	private static JButton btnAperitivos;
	private static JButton btnBebida;
	private static JButton btnAdd;
	private static JButton btnDescuento;
	private JLabel lblMenu;
	private JLabel lblProducto1;
	private JLabel lblProducto2;
	private JLabel lblProducto3;
	private JLabel lblDescuentoCantidad;
	private JTextField textDescuento;
	private JButton btnPedir;
	private JScrollPane scrollPane;
	private TreeMap<String, Establecimiento> listado;
	private JComboBox<String> comboEstablecimiento;
	private JTable table;
	private Producto prdAux;
	private String[] arrayCodigos;
	private String[] arrayCodPrd = new String[3];
	private float[] arrayPrecios = new float[]{0,0,0};
	private static Point point = new Point(0, 0);
	private Cliente pUser;
	private ImageIcon icon1 = new ImageIcon(getClass().getResource("/resources/icon_placeholder_menu.png"));
	private ImageIcon icon2 = new ImageIcon(getClass().getResource("/resources/icon_placeholder_comida.png"));
	private ImageIcon icon3 = new ImageIcon(getClass().getResource("/resources/icon_placeholder_aperitivo.png"));
	private ImageIcon icon4 = new ImageIcon(getClass().getResource("/resources/icon_placeholder_bebida.png"));

	// private float descuento = 0;
	private float precioTotal = 0;

	// Definir colores
	private static Color colorMoradoClaro = new Color(118, 38, 161);
	private static Color colorAzulClaro = new Color(21, 131, 170);
	private static Color colorVerdeClaro = new Color(30, 180, 132);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			cargarTipografia();
			VPedido dialog = new VPedido(null, null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Carga la tipografia Iosevka Aile Heavy
	 *
	 */
	private static void cargarTipografia() {
		Fuentes fe = new Fuentes();
		fe.cargarTipografia();
	}

	/**
	 * Create the dialog.
	 */
	public VPedido(JDialog vMC, Usuarie pUsuarie) {
		super(vMC, "Taco Bell", true);
		setUndecorated(true);
		vMenuMenu = vMC;
		pUser = (Cliente)pUsuarie;

		setBounds(100, 100, 1185, 685);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);

		JPanel panelMenu = new JPanel();
		panelMenu.setBackground(new Color(240, 240, 240));
		panelMenu.setBounds(5, 32, 1174, 98);
		panelMenu.setAlignmentY(Component.TOP_ALIGNMENT);
		FlowLayout flowLayout = (FlowLayout) panelMenu.getLayout();
		flowLayout.setHgap(0);
		flowLayout.setVgap(0);
		contentPanel.add(panelMenu);

		JPanel panelMenuFondo = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panelMenuFondo.getLayout();
		flowLayout_1.setVgap(10);
		flowLayout_1.setHgap(180);
		panelMenuFondo.setBackground(colorMoradoClaro);
		panelMenu.add(panelMenuFondo);

		JPanel panelVolver = new JPanel();
		FlowLayout flowLayout_6 = (FlowLayout) panelVolver.getLayout();
		flowLayout_6.setVgap(0);
		flowLayout_6.setHgap(0);
		panelVolver.setBackground(colorMoradoClaro);
		panelMenuFondo.add(panelVolver);

		btnAtras = new JButton("");
		btnAtras.setBorder(null);
		btnAtras.setBackground(colorMoradoClaro);
		btnAtras.setIcon(new ImageIcon(VPedido.class.getResource("/resources/icon_atras.png")));
		btnAtras.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelVolver.add(btnAtras);
		btnAtras.addActionListener(this);
		btnAtras.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				btnAtras.setBackground(new Color(98, 18, 141));
			}

			public void mouseExited(MouseEvent evt) {
				btnAtras.setBackground(colorMoradoClaro);
			}
		});

		JPanel panelMenus = new JPanel();
		panelMenus.setBackground(colorMoradoClaro);
		panelMenuFondo.add(panelMenus);
		panelMenus.setLayout(new BoxLayout(panelMenus, BoxLayout.Y_AXIS));

		btnMenus = new JButton("");
		btnMenus.setIcon(new ImageIcon(VPedido.class.getResource("/resources/icon_menus.png")));

		btnMenus.setForeground(Color.WHITE);
		btnMenus.setFont(new Font("Iosevka Aile Heavy", Font.PLAIN, 24));
		btnMenus.setBorder(null);
		btnMenus.setBackground(colorMoradoClaro);
		btnMenus.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelMenus.add(btnMenus);
		btnMenus.addActionListener(this);
		btnMenus.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				btnMenus.setBackground(new Color(98, 18, 141));
			}

			public void mouseExited(MouseEvent evt) {
				btnMenus.setBackground(colorMoradoClaro);
			}
		});

		JPanel panelComida = new JPanel();
		panelComida.setBackground(colorMoradoClaro);
		panelMenuFondo.add(panelComida);
		panelComida.setLayout(new BoxLayout(panelComida, BoxLayout.Y_AXIS));

		btnComida = new JButton("");
		btnComida.setIcon(new ImageIcon(VPedido.class.getResource("/resources/icon_comida.png")));
		btnComida.setForeground(Color.WHITE);
		btnComida.setFont(new Font("Iosevka Aile Heavy", Font.PLAIN, 24));
		btnComida.setBorder(null);
		btnComida.setBackground(colorMoradoClaro);
		btnComida.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelComida.add(btnComida);
		btnComida.addActionListener(this);
		btnComida.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				btnComida.setBackground(new Color(98, 18, 141));
			}

			public void mouseExited(MouseEvent evt) {
				btnComida.setBackground(colorMoradoClaro);
			}
		});

		JPanel panelAperitivos = new JPanel();
		panelAperitivos.setBackground(colorMoradoClaro);
		panelMenuFondo.add(panelAperitivos);
		panelAperitivos.setLayout(new BoxLayout(panelAperitivos, BoxLayout.Y_AXIS));

		btnAperitivos = new JButton("");
		btnAperitivos.setIcon(new ImageIcon(VPedido.class.getResource("/resources/icon_aperitivos.png")));

		btnAperitivos.setForeground(Color.WHITE);
		btnAperitivos.setFont(new Font("Iosevka Aile Heavy", Font.PLAIN, 24));
		btnAperitivos.setBorder(null);
		btnAperitivos.setBackground(colorMoradoClaro);
		btnAperitivos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelAperitivos.add(btnAperitivos);
		btnAperitivos.addActionListener(this);
		btnAperitivos.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				btnAperitivos.setBackground(new Color(98, 18, 141));
			}

			public void mouseExited(MouseEvent evt) {
				btnAperitivos.setBackground(colorMoradoClaro);
			}
		});

		JPanel panelBebida = new JPanel();
		panelBebida.setBackground(colorMoradoClaro);
		panelMenuFondo.add(panelBebida);
		panelBebida.setLayout(new BoxLayout(panelBebida, BoxLayout.Y_AXIS));

		btnBebida = new JButton("");
		btnBebida.setIcon(new ImageIcon(VPedido.class.getResource("/resources/icon_bebidas.png")));
		btnBebida.setForeground(Color.WHITE);
		btnBebida.setFont(new Font("Iosevka Aile Heavy", Font.PLAIN, 24));
		btnBebida.setBorder(null);
		btnBebida.setBackground(colorMoradoClaro);
		btnBebida.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelBebida.add(btnBebida);
		btnBebida.addActionListener(this);
		btnBebida.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				btnBebida.setBackground(new Color(98, 18, 141));
			}

			public void mouseExited(MouseEvent evt) {
				btnBebida.setBackground(colorMoradoClaro);
			}
		});

		JPanel panelProductos = new JPanel();
		panelProductos.setBounds(347, 141, 832, 491);
		contentPanel.add(panelProductos);
		panelProductos.setLayout(new GridLayout(0, 1, 0, 0));

		JSeparator separator = new JSeparator();
		separator.setForeground(Color.DARK_GRAY);
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(335, 141, 2, 514);
		contentPanel.add(separator);

		JPanel panelPedido = new JPanel();
		panelPedido.setLayout(null);
		panelPedido.setBounds(5, 118, 320, 537);
		contentPanel.add(panelPedido);

		btnPedir = new JButton("TOTAL: " + precioTotal + " \u20AC");
		btnPedir.setForeground(Color.WHITE);
		btnPedir.setBackground(colorVerdeClaro);
		btnPedir.setBorder(null);
		btnPedir.setFont(new Font("Iosevka Aile Heavy", Font.BOLD, 32));
		btnPedir.setBounds(10, 466, 300, 60);
		btnPedir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panelPedido.add(btnPedir);
		btnPedir.addActionListener(this);
		btnPedir.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				btnPedir.setBackground(new Color(10, 160, 112));
			}

			public void mouseExited(MouseEvent evt) {
				btnPedir.setBackground(colorVerdeClaro);
			}
		});

		JPanel panelSeleccion = new JPanel();
		panelSeleccion.setBounds(10, 26, 300, 396);
		panelPedido.add(panelSeleccion);
		panelSeleccion.setLayout(null);

		JPanel panelTextDescuento = new JPanel();
		panelTextDescuento.setBounds(10, 348, 182, 37);
		panelSeleccion.add(panelTextDescuento);
		panelTextDescuento.setLayout(new BoxLayout(panelTextDescuento, BoxLayout.Y_AXIS));

		textDescuento = new JTextField();
		textDescuento.setBorder(null);
		textDescuento.setFont(new Font("Iosevka Aile Heavy", Font.PLAIN, 20));
		panelTextDescuento.add(textDescuento);
		textDescuento.setColumns(10);

		JSeparator separator_1 = new JSeparator();
		panelTextDescuento.add(separator_1);

		btnDescuento = new JButton("OK");
		btnDescuento.setActionCommand("OK");
		btnDescuento.setForeground(Color.WHITE);
		btnDescuento.setFont(new Font("Dialog", Font.BOLD, 32));
		btnDescuento.setBorder(null);
		btnDescuento.setBackground(new Color(30, 180, 132));
		btnDescuento.setBounds(209, 348, 81, 37);
		panelSeleccion.add(btnDescuento);
		btnDescuento.addActionListener(this);

		JLabel lblEstablecimiento = new JLabel("Establecimiento");
		lblEstablecimiento.setFont(new Font("Iosevka Aile Heavy", Font.PLAIN, 20));
		lblEstablecimiento.setBounds(10, 310, 145, 27);
		panelSeleccion.add(lblEstablecimiento);

		comboEstablecimiento = new JComboBox<>();
		comboEstablecimiento.setBounds(165, 310, 125, 27);
		panelSeleccion.add(comboEstablecimiento);

		lblMenu = new JLabel("- Menu personalizado");
		lblMenu.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblMenu.setBounds(10, 11, 280, 44);
		panelSeleccion.add(lblMenu);

		lblProducto1 = new JLabel("- ");
		lblProducto1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblProducto1.setBounds(38, 72, 252, 44);
		panelSeleccion.add(lblProducto1);

		lblProducto2 = new JLabel("- ");
		lblProducto2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblProducto2.setBounds(38, 127, 252, 44);
		panelSeleccion.add(lblProducto2);

		lblProducto3 = new JLabel("- ");
		lblProducto3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblProducto3.setBounds(38, 187, 252, 44);
		panelSeleccion.add(lblProducto3);

		try {
			listado = EstablecimientoADFactory.getAccessEstablecimiento().listarEstablecimientos();
		} catch (GestorExcepciones ex) {
			JOptionPane.showMessageDialog(this,
					ex.getMsg(),
					"Warning",
					JOptionPane.WARNING_MESSAGE);
		}
		for (Establecimiento e : listado.values()) {
			comboEstablecimiento.addItem(e.getNombre());
		}

		JPanel panelDescuento = new JPanel();
		panelDescuento.setBounds(10, 401, 300, 54);
		panelPedido.add(panelDescuento);
		panelDescuento.setLayout(null);

		JLabel lblDescuento = new JLabel("Descuento");
		lblDescuento.setHorizontalAlignment(SwingConstants.LEFT);
		lblDescuento.setFont(new Font("Iosevka Aile Heavy", Font.PLAIN, 24));
		lblDescuento.setForeground(colorAzulClaro);
		lblDescuento.setBounds(10, 11, 146, 56);
		panelDescuento.add(lblDescuento);

		lblDescuentoCantidad = new JLabel("0€");
		lblDescuentoCantidad.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDescuentoCantidad.setFont(new Font("Iosevka Aile Heavy", Font.PLAIN, 24));
		lblDescuentoCantidad.setForeground(colorAzulClaro);
		lblDescuentoCantidad.setBounds(197, 11, 93, 56);
		panelDescuento.add(lblDescuentoCantidad);

		JPanel panel_4 = new JPanel();
		FlowLayout flowLayout_7 = (FlowLayout) panel_4.getLayout();
		flowLayout_7.setAlignment(FlowLayout.RIGHT);
		panel_4.setBounds(1017, 0, 162, 32);
		contentPanel.add(panel_4);

		btnX = new JButton("");
		btnX.setSelectedIcon(new ImageIcon(VPedido.class.getResource("/resources/icon_x_active.png")));
		btnX.setIcon(new ImageIcon(VPedido.class.getResource("/resources/icon_x_inactive.png")));
		btnX.setBackground(Color.WHITE);
		btnX.setBorder(null);
		btnX.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panel_4.add(btnX);
		btnX.addActionListener(this);
		btnX.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				btnX.setIcon(new ImageIcon(VLogin.class.getResource("/resources/icon_x_active.png")));
			}

			public void mouseExited(MouseEvent evt) {
				btnX.setIcon(new ImageIcon(VLogin.class.getResource("/resources/icon_x_inactive.png")));
			}
		});

		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				point.x = e.getX();
				point.y = e.getY();
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				Point p = getLocation();
				setLocation(p.x + e.getX() - point.x, p.y + e.getY() - point.y);
			}
		});
		scrollPane = new JScrollPane();
		scrollPane.setBounds(24, 209, 583, 121);
		panelProductos.add(scrollPane);

		table = actualizarTabla(tablaActual);
		refreshTabla();
		scrollPane.setViewportView(table);

		btnAdd = new JButton("A\u00F1adir");
		btnAdd.setForeground(Color.WHITE);
		btnAdd.setFont(new Font("Iosevka Aile Heavy", Font.BOLD, 24));
		btnAdd.setBorder(null);
		btnAdd.setBackground(new Color(30, 180, 132));
		btnAdd.setActionCommand("OK");
		btnAdd.setBounds(1094, 637, 81, 37);
		btnAdd.addActionListener(this);
		contentPanel.add(btnAdd);
		JTableHeader tableHeader = table.getTableHeader();
		tableHeader.setBackground(colorVerdeClaro);
		tableHeader.setForeground(Color.WHITE);
		tableHeader.setFont(new Font("Iosevka Aile Heavy", Font.BOLD, 15));
		tableHeader.setBorder(null);
		tableHeader.setEnabled(false);
	}

	private JTable actualizarTabla(int x) {
		try {
			switch (x) {
				case 0:// Menús
					return cargarDatos(0);
				case 1:// Comida
					return cargarDatos(1);
				case 2:// Aperitivos
					return cargarDatos(2);
				default:// Bebida
					return cargarDatos(3);
			}
		} catch (GestorExcepciones e) {
			JOptionPane.showMessageDialog(this,
					e.getMsg(),
					"Warning",
					JOptionPane.WARNING_MESSAGE);
			return null;
		}
	}

	private void refreshTabla() {
		table.setSelectionBackground(colorMoradoClaro);
		table.setSelectionForeground(Color.WHITE);
		table.setRowMargin(0);
		table.setRowHeight(225);
		table.setShowVerticalLines(true);
		table.setDefaultRenderer(Object.class, new IconCellRender());
		table.getColumnModel().getColumn(2).setWidth(1);
		table.setFont(new Font("Iosevka Aile Heavy", Font.PLAIN, 26));
		scrollPane.setViewportView(table);
	}

	/**
	 * Carga de selecciones para los distintos menus
	 *
	 * <p>
	 * Necesario para cargar los distintos productos/menus disponibles segun pestaña
	 * elegida.
	 *
	 * @param menu indica la pestaña seleccionada
	 * @return tabla con los productos disponibles
	 * @throws GestorExcepciones
	 */
	private JTable cargarDatos(int menu) throws GestorExcepciones {

		List<NombrePrecio<String, Float>> nombrePrecio = null;

		// menus
		if (menu == 0) {
			Collection<Menu> menues = MenuADFactory.getAccessMenu().listarMenus().values();
			nombrePrecio = menues.stream()
					.map(p -> new NombrePrecio(p.getNombre(), p.getPrecio(), p.getCodMnu()))
					.collect(Collectors.toList());
		} else {
			Collection<Producto> productos = ProductoADFactory.getAccessProductos().listarProductos().values();
			// comida
			if (menu == 1) {
				nombrePrecio = productos.stream()
						.filter(p -> p.getTipo().equals("Comida"))
						.map(p -> new NombrePrecio(p.getNombre(), p.getPrecio(), p.getCodPrd()))
						.collect(Collectors.toList());
			}
			// aperitivos
			else if (menu == 2) {
				nombrePrecio = productos.stream()
						.filter(p -> p.getTipo().equals("Aperitivo"))
						.map(p -> new NombrePrecio(p.getNombre(), p.getPrecio(), p.getCodPrd()))
						.collect(Collectors.toList());
			}
			// bebidas
			else if (menu == 3) {
				nombrePrecio = productos.stream()
						.filter(p -> p.getTipo().equals("Bebida"))
						.map(p -> new NombrePrecio(p.getNombre(), p.getPrecio(), p.getCodPrd()))
						.collect(Collectors.toList());
			}
			// error
			else
				throw new GestorExcepciones(011);
		}

		Object[][] listado = new Object[nombrePrecio.size()][3];
		arrayCodigos = new String[nombrePrecio.size()];
		for (int i = 0; i < nombrePrecio.size(); i++) {
			listado[i][0] = new JLabel((menu == 0) ? icon1 : (menu == 1) ? icon2 : (menu == 2) ? icon3 : icon4);
			listado[i][1] = nombrePrecio.get(i).getFst();
			listado[i][2] = nombrePrecio.get(i).getSnd() + "€";
			arrayCodigos[i] = (String) nombrePrecio.get(i).getTrd();
		}
		String[] titulo = { "", "", "" };

		return new JTable(listado, titulo);
	}

	public float calcularPrecioTotal() {
		return arrayPrecios[0]+arrayPrecios[1]+arrayPrecios[2]-Float.parseFloat(lblDescuentoCantidad.getText().substring(0, lblDescuentoCantidad.getText().indexOf('€'))) ;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnAtras)) {
			this.dispose();
			vMenuMenu.setVisible(true);
		}
		if (e.getSource().equals(btnX)) {
			this.dispose();
			vMenuMenu.dispose();
		}
		if (e.getSource().equals(btnMenus)) {
			tablaActual = 0;
			table = actualizarTabla(tablaActual);
			refreshTabla();
		}
		if (e.getSource().equals(btnComida)) {
			tablaActual = 1;
			table = actualizarTabla(tablaActual);
			refreshTabla();
		}
		if (e.getSource().equals(btnAperitivos)) {
			tablaActual = 2;
			table = actualizarTabla(tablaActual);
			refreshTabla();
		}
		if (e.getSource().equals(btnBebida)) {
			tablaActual = 3;
			table = actualizarTabla(tablaActual);
			refreshTabla();
		}
		if (e.getSource().equals(btnPedir)) {
			try {
			Optional<Establecimiento> est = listado.values().stream()
					.filter(estab -> estab.getNombre()
							.equalsIgnoreCase((String) comboEstablecimiento.getSelectedItem()))
					.findFirst();
			Menu pMenu;
			if (lblMenu.getText().equals("- Menu personalizado")) {
				pMenu = new Menu(MenuADFactory.getAccessMenu().generateCodigo(), textDescuento.getText().isEmpty()?null:textDescuento.getText(), arrayCodPrd, precioTotal, "Menu pesonalizado");
				MenuADFactory.getAccessMenu().grabarMenu(pMenu);
			} else {
				pMenu = MenuADFactory.getAccessMenu().buscarMenuPorCodigo(arrayCodigos[table.getSelectedRow()]);
			}
			Pedido pPedido = new Pedido(PedidoADFactory.getAccessPedido().generateCodigo(), LocalDate.now(), pUser.getCodUsr(), "RE00003", est.get().getCodEst(), pMenu);
			PedidoADFactory.getAccessPedido().grabarPedido(pPedido);
			JOptionPane.showMessageDialog(this,
						"Pedido realizado.",
						"¡Genial!",
						JOptionPane.OK_OPTION);
		} catch (GestorExcepciones ex) {
				JOptionPane.showMessageDialog(this,
						ex.getMsg(),
						"Warning",
						JOptionPane.WARNING_MESSAGE);
			}
		}
		if (e.getSource().equals(btnAdd)) {
			try {
				if (table.getSelectedRowCount() == 1) {
					if (tablaActual == 0) {
						for (int i = 0; i < arrayPrecios.length; i++) {
							arrayPrecios[i] = 0;
						}
						lblMenu.setText((String) table.getValueAt(table.getSelectedRow(), 1));
						String strAux = (String) table.getValueAt(table.getSelectedRow(), 2);
						arrayCodPrd = MenuADFactory.getAccessMenu().getCodigosProductos(MenuADFactory.getAccessMenu()
								.buscarMenuPorCodigo(arrayCodigos[table.getSelectedRow()]));
						for (int i = 0; i < arrayCodPrd.length; i++) {
							prdAux = ProductoADFactory.getAccessProductos().buscarProductoPorCodigo(arrayCodPrd[i]);
							if (prdAux.getTipo().equals("Comida")) {
								lblProducto1.setText(prdAux.getNombre());
							} 
							if (prdAux.getTipo().equals("Aperitivo")) {
								lblProducto2.setText(prdAux.getNombre());
							} else {
								lblProducto3.setText(prdAux.getNombre());
							}
						}

						precioTotal = Float.parseFloat(strAux.substring(0, strAux.indexOf('€'))) - Float.parseFloat(lblDescuentoCantidad.getText().substring(0, lblDescuentoCantidad.getText().indexOf('€')));
						btnPedir.setText("TOTAL: " + Math.round(precioTotal*100.0) /100.0 + " \u20AC");
					} else {
						if (!lblMenu.getText().equals("- Menu personalizado")){
							lblMenu.setText("- Menu personalizado");
							lblProducto1.setText("- ");
							lblProducto2.setText("- ");
							lblProducto3.setText("- ");
							precioTotal = 0;
						}
					}

					if (tablaActual == 1) {
						lblProducto1.setText(ProductoADFactory.getAccessProductos().buscarProductoPorCodigo(arrayCodigos[table.getSelectedRow()]).getNombre());
						arrayCodPrd[0] = ProductoADFactory.getAccessProductos().buscarProductoPorCodigo(arrayCodigos[table.getSelectedRow()]).getCodPrd();
						String strAux = (String) table.getValueAt(table.getSelectedRow(), 2);
						arrayPrecios[0] = Float.parseFloat(strAux.substring(0, strAux.indexOf('€')));
						precioTotal = calcularPrecioTotal();
						btnPedir.setText("TOTAL: " + Math.round(precioTotal*100.0) /100.0 + " \u20AC");
					}
					if (tablaActual == 2) {
						lblProducto2.setText(ProductoADFactory.getAccessProductos().buscarProductoPorCodigo(arrayCodigos[table.getSelectedRow()]).getNombre());
						arrayCodPrd[1] = ProductoADFactory.getAccessProductos().buscarProductoPorCodigo(arrayCodigos[table.getSelectedRow()]).getCodPrd();
						String strAux = (String) table.getValueAt(table.getSelectedRow(), 2);
						arrayPrecios[1] = Float.parseFloat(strAux.substring(0, strAux.indexOf('€')));
						precioTotal = calcularPrecioTotal();
						btnPedir.setText("TOTAL: " + Math.round(precioTotal*100.0) /100.0 + " \u20AC");
					}
					if (tablaActual == 3) {
						lblProducto3.setText(ProductoADFactory.getAccessProductos().buscarProductoPorCodigo(arrayCodigos[table.getSelectedRow()]).getNombre());
						arrayCodPrd[2] = ProductoADFactory.getAccessProductos().buscarProductoPorCodigo(arrayCodigos[table.getSelectedRow()]).getCodPrd();
						String strAux = (String) table.getValueAt(table.getSelectedRow(), 2);
						arrayPrecios[2] =Float.parseFloat(strAux.substring(0, strAux.indexOf('€')));
						precioTotal = calcularPrecioTotal();
						btnPedir.setText("TOTAL: " + Math.round(precioTotal*100.0) /100.0 + " \u20AC");
					}
				} else {
					if (table.getSelectedRowCount() == 0)
						JOptionPane.showMessageDialog(this,
								"Ninguna seleccionada.",
								"Warning",
								JOptionPane.WARNING_MESSAGE);
					else
						JOptionPane.showMessageDialog(this,
								"Muchas seleccionadas.",
								"Warning",
								JOptionPane.WARNING_MESSAGE);
				}
			} catch (GestorExcepciones ex) {
				JOptionPane.showMessageDialog(this,
						ex.getMsg(),
						"Warning",
						JOptionPane.WARNING_MESSAGE);
			}
		}
		if (e.getSource().equals(btnDescuento)) {
			try {
				Descuento descAux = DescuentoADFactory.getAccessDescuento().buscarDescuentoPorCodigo(textDescuento.getText());
			if (textDescuento.getText().equals(descAux.getCodDsc())) {
				lblDescuentoCantidad.setText((descAux).getCantidadDsc() + "€");
				descAux.setUsos(descAux.getUsos()-1);
				DescuentoADFactory.getAccessDescuento().modificarDescuento(descAux);
			} else {
				JOptionPane.showMessageDialog(this,
								"No existe este descuento.",
								"Warning",
								JOptionPane.WARNING_MESSAGE);
			}
		} catch (GestorExcepciones ex) {
			JOptionPane.showMessageDialog(this,
								ex.getMsg(),
								"Warning",
								JOptionPane.WARNING_MESSAGE);
		}
		}
	}
}
