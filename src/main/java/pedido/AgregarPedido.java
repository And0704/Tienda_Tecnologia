/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package pedido;

import cliente.ControladorClientes;
import cliente.ControladorListClientes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mycompany.tienda_tecnologia.Dashboard;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import producto.ControladorListProductos;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import okhttp3.RequestBody;
import producto.EditarProducto;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author Josue
 */
public class AgregarPedido extends javax.swing.JFrame {

    /**
     * Creates new form PedidoEditar
     */
    MediaType JSON = MediaType.get("application/json");
    ControladorClientes consulta = null;
    private Dashboard dashboard;

    ArrayList<Integer> idCliente = new ArrayList<>();
    ArrayList<Integer> idProductos = new ArrayList<>();
    ArrayList<Integer> idProductosSelecccionados = new ArrayList<>();
    ArrayList<ControladorProductoPedido> ProductoPedido = new ArrayList<>();

    public AgregarPedido(Dashboard dashboard) {
        initComponents();

        cargarComboCliente(consultaTodoCliente());
        cargarComboProducto(consultaTodoProducto());
        this.dashboard = dashboard;
        mostrarFecha();
    }

    public List<ControladorListClientes> consultaTodoCliente() {
        OkHttpClient client = new OkHttpClient();
        String enlace = "http://192.168.137.1:3000/clientes";

        Request peticion = new Request.Builder().url(enlace).build();

        try (Response respuesta = client.newCall(peticion).execute()) {
            if (respuesta.isSuccessful() && respuesta.body() != null) {
                String respuestaJSON = respuesta.body().string();

                java.lang.reflect.Type listaElementos = new TypeToken<List<ControladorListClientes>>() {
                }.getType();

                return new Gson().fromJson(respuestaJSON, listaElementos);
            }
        } catch (Exception ex) {

        }
        return null;
    }

    public List<ControladorListProductos> consultaTodoProducto() {
        OkHttpClient client = new OkHttpClient();
        String enlace = "http://192.168.137.1:3000/producto";

        Request peticion = new Request.Builder().url(enlace).build();

        try (Response respuesta = client.newCall(peticion).execute()) {
            if (respuesta.isSuccessful() && respuesta.body() != null) {
                String respuestaJSON = respuesta.body().string();

                java.lang.reflect.Type listaElementos = new TypeToken<List<ControladorListProductos>>() {
                }.getType();

                return new Gson().fromJson(respuestaJSON, listaElementos);
            }
        } catch (Exception ex) {

        }
        return null;
    }

    public ControladorListProductos consultaUNO(String id) {
        OkHttpClient client = new OkHttpClient();
        String enlace = "http://192.168.137.1:3000/producto/" + id;

        Request peticion = new Request.Builder().url(enlace).build();

        try (Response respuesta = client.newCall(peticion).execute()) {
            if (respuesta.isSuccessful() && respuesta.body() != null) {
                String respuestaJSON = respuesta.body().string();

                java.lang.reflect.Type listaElementos = new TypeToken<List<ControladorListProductos>>() {
                }.getType();

                List<ControladorListProductos> datos = new Gson().fromJson(respuestaJSON, listaElementos);

                return datos.get(0);
            }
        } catch (Exception ex) {

        }
        return null;
    }

    public void cargarComboCliente(List<ControladorListClientes> clientes) {
        try {
            for (ControladorListClientes item : clientes) {
                ComboCliente.addItem(item.getNombre() + " " + item.getApellido());
                idCliente.add(item.getId_cliente());
            }
            System.out.println("Se cargo los dato del combo box con exito");
        } catch (Exception e) {
            System.out.println("No se pudo ingresar valores");
        }

    }

    public void cargarComboProducto(List<ControladorListProductos> productos) {
        try {
            for (ControladorListProductos item : productos) {
                ComboProducto.addItem(item.getNombre());
                idProductos.add(item.getId_producto());
            }
            System.out.println("Se cargo los dato del combo box con exito");

        } catch (Exception e) {
            System.out.println("No se pudo ingresar valores");
        }

    }

    public void cargarDatosTabla() {
        // Obtener el modelo existente de la tabla
       DefaultTableModel modeloTabla = (DefaultTableModel) jTable1.getModel();
        // Configurar las columnas solo si no están configuradas
        if (modeloTabla.getColumnCount() == 0) {
            modeloTabla.setColumnIdentifiers(new Object[]{"ID Producto", "Nombre del producto", "Cantidad", "Precio Unitario", "Total"});
            // Eliminar cualquier fila inicial (como las que aparecen por defecto al iniciar)
            modeloTabla.setRowCount(0); // Esto limpia las filas del modelo
        }
        // Obtener los datos del producto seleccionado
        String nombreProducto = (String) ComboProducto.getSelectedItem();
        String cantidadStr = ProductoCantidad.getText();
        Integer indexProducto = ComboProducto.getSelectedIndex();
        Integer IdProducto = idProductos.get(indexProducto);
        idProductosSelecccionados.add(IdProducto);
        ControladorListProductos productoPrecio = consultaUNO(Integer.toString(IdProducto));
        
        
        // Calcular el total
        double precioUnitario = Double.parseDouble(productoPrecio.getPrecio());
        int cantidad = Integer.parseInt(cantidadStr); // Valida el dato si es ingresado por el usuario
        double total = precioUnitario * cantidad;
        // Crear una nueva fila con los datos
        Object[] nuevaFila = {IdProducto, nombreProducto, cantidad, precioUnitario, total};
        // Agregar la nueva fila al modelo existente
        modeloTabla.addRow(nuevaFila);
        jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(0).setWidth(0);
    }

    public void insertarPedido(ArrayList<ControladorProductoPedido> ProductoPedido) {
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();
        String enlace = "http://192.168.137.1:3000/pedido";
        Integer indexCliente = ComboCliente.getSelectedIndex();

        // Crear un mapa para construir la estructura JSON necesaria
        Map<String, Object> datosPedido = new HashMap<>();
        datosPedido.put("id_cliente", idCliente.get(indexCliente - 1));
        datosPedido.put("fecha", Fecha.getText());
        datosPedido.put("estado", (String) ComboEstado.getSelectedItem());

        // Convertir la lista de productos en una lista compatible con JSON
        List<Map<String, Object>> detallesJSON = new ArrayList<>();

        // Iterar sobre la lista ProductoPedido
        for (ControladorProductoPedido producto : ProductoPedido) {
            Map<String, Object> detalleMapa = new HashMap<>();
            detalleMapa.put("id_producto", producto.getId_producto());
            detalleMapa.put("nombre", producto.getNombre());
            detalleMapa.put("cantidad", producto.getCantidad());
            detallesJSON.add(detalleMapa);
        }

        datosPedido.put("detalles", detallesJSON);

        // Convertir los datos del pedido a JSON
        String datosJSON = gson.toJson(datosPedido);

        // Crear el cuerpo de la solicitud
        RequestBody cuerpo = RequestBody.create(datosJSON, MediaType.parse("application/json"));

        // Crear la solicitud HTTP
        Request peticion = new Request.Builder()
                .url(enlace)
                .post(cuerpo)
                .build();

        try (Response respuesta = client.newCall(peticion).execute()) {
            if (respuesta.isSuccessful()) {
                System.out.println("Éxito al insertar el registro");
            } else {
                System.out.println("Error al insertar el registro: " + respuesta.message());
            }
        } catch (Exception ex) {
            System.out.println("Error en la solicitud: " + ex.getMessage());
        }
    }

    private void mostrarFecha() {
        // Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();
        // Definir el formato de la fecha
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // Formatear la fecha
        String fechaFormateada = fechaActual.format(formatoFecha);
        // Establecer la fecha formateada en el JTextField
        Fecha.setText(fechaFormateada);
    }

    public void obtenerProductosDeTabla(JTable tabla) {
       DefaultTableModel modeloTabla = (DefaultTableModel) tabla.getModel();
        // Recorrer las filas de la tabla
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            // Obtener los valores de cada columna
            Integer id_producto = (Integer) modeloTabla.getValueAt(i, 0);  // Columna 0 -> ID del producto
            String nombre = (String) modeloTabla.getValueAt(i, 1);  // Columna 1 -> Nombre del producto
            Integer cantidad = (Integer) modeloTabla.getValueAt(i, 2);  // Columna 2 -> Cantidad
            Double precioUnitario = (Double) modeloTabla.getValueAt(i, 3);  // Columna 3 -> Precio Unitario
            Double total = (Double) modeloTabla.getValueAt(i, 4);  // Columna 4 -> Total
            // Verificar si idProducto es nulo o inválido
            if (id_producto == null || id_producto == 0) {
                JOptionPane.showMessageDialog(null, "El ID del producto no es válido en la fila " + (i + 1), "Error", JOptionPane.ERROR_MESSAGE);
                continue; // Omitir este producto si el ID es inválido
            }
            // Crear un objeto Producto y añadirlo a la lista
            ControladorProductoPedido producto = new ControladorProductoPedido(id_producto, nombre, cantidad, precioUnitario, total);
            ProductoPedido.add(producto);
        }
    }

    public void insertarPedido(ControladorProductoPedido nuevoPedido) {
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();
        String enlace = "http://192.168.137.1:3000/pedido";
        Integer indexCliente = ComboCliente.getSelectedIndex();

        // Crear un mapa para construir la estructura JSON necesaria
        Map<String, Object> datosPedido = new HashMap<>();
        datosPedido.put("id_cliente", idCliente.get(indexCliente - 1)); // Asegúrate que idCliente esté correctamente definido
        datosPedido.put("fecha", Fecha.getText());
        datosPedido.put("estado", (String) ComboEstado.getSelectedItem());

        // Convertir la lista de productos en una lista compatible con JSON
        List<Map<String, Object>> detallesJSON = new ArrayList<>();

        // Iterar sobre la lista ProductoPedido
        for (ControladorProductoPedido producto : ProductoPedido) {
            // Verificar que idProducto no sea NULL o inválido
            if (producto.getId_producto() == 0) {
                System.out.println("Error: El producto con nombre " + producto.getNombre() + " tiene un ID inválido");
                continue; // Omitir este producto si su ID es inválido
            }

            Map<String, Object> detalleMapa = new HashMap<>();
            detalleMapa.put("id_producto", producto.getId_producto()); // ID del producto
            detalleMapa.put("nombre", producto.getNombre()); // Nombre del producto
            detalleMapa.put("cantidad", producto.getCantidad()); // Cantidad
            detallesJSON.add(detalleMapa);
        }

        // Agregar los detalles a los datos del pedido
        datosPedido.put("detalles", detallesJSON);

        // Convertir los datos del pedido a JSON
        String datosJSON = gson.toJson(datosPedido);

        // Imprimir los datos JSON antes de enviarlos (para depuración)
        System.out.println("Datos del pedido en formato JSON: " + datosJSON);

        // Crear el cuerpo de la solicitud
        RequestBody cuerpo = RequestBody.create(datosJSON, MediaType.parse("application/json"));

        // Crear la solicitud HTTP
        Request peticion = new Request.Builder()
                .url(enlace)
                .post(cuerpo)
                .build();

        try (Response respuesta = client.newCall(peticion).execute()) {
            if (respuesta.isSuccessful()) {
                System.out.println("Éxito al insertar el registro");
            } else {
                System.out.println("Error al insertar el registro: " + respuesta.message());
            }
        } catch (Exception ex) {
            System.out.println("Error en la solicitud: " + ex.getMessage());
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

        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        AgregarCarrito = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        ComboCliente = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        Fecha = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        ComboEstado = new javax.swing.JComboBox<>();
        jButton4 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        ComboProducto = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        ProductoCantidad = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        jButton3.setBackground(new java.awt.Color(153, 204, 255));
        jButton3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButton3.setText("Enviar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(153, 204, 255));
        jButton5.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButton5.setText("Agregar al carrito");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 2, 24)); // NOI18N
        jLabel1.setText("Crear Pedido");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable1);

        AgregarCarrito.setBackground(new java.awt.Color(153, 204, 255));
        AgregarCarrito.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        AgregarCarrito.setText("Agregar al carrito");
        AgregarCarrito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarCarritoActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel2.setText("Nombre:");

        ComboCliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "................................" }));
        ComboCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboClienteActionPerformed(evt);
            }
        });

        jLabel3.setText("Fecha: ");

        Fecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FechaActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel4.setText("Estado:");

        ComboEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pagado ", "Pendiente" }));
        ComboEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboEstadoActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(255, 102, 102));
        jButton4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButton4.setText("Eliminar del Carrito");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel5.setText("Producto");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel6.setText("CLIENTE");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel7.setText("Nombre:");

        ComboProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboProductoActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel8.setText("Cantidad:");

        ProductoCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProductoCantidadActionPerformed(evt);
            }
        });
        ProductoCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                ProductoCantidadKeyTyped(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(153, 255, 153));
        jButton6.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButton6.setText(" Registrar pedido");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(255, 153, 102));
        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("REGRESAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(128, 128, 128)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 435, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(142, 142, 142)
                        .addComponent(jLabel1)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ComboProducto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(ComboCliente, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(Fecha)
                                            .addComponent(ComboEstado, 0, 138, Short.MAX_VALUE))
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(ProductoCantidad)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(AgregarCarrito, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(89, 89, 89))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(88, 88, 88))))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ComboCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(ComboEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ComboProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ProductoCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(60, 60, 60)
                        .addComponent(AgregarCarrito)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4)
                    .addComponent(jButton6))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 13, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void AgregarCarritoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarCarritoActionPerformed

        if (ComboProducto.getSelectedItem() == null || ProductoCantidad.getText().isEmpty()) {

            JOptionPane.showMessageDialog(null, "Por favor llene todos los campos de producto", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {

            cargarDatosTabla();
        }

    }//GEN-LAST:event_AgregarCarritoActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        int index = jTable1.getSelectedRow(); // Obtener la fila seleccionada
        if (index == -1) {
            // No se seleccionó ninguna fila
            JOptionPane.showMessageDialog(null, "Por favor, seleccione una fila de la tabla.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        } else {
            // Obtener el modelo de la tabla
            DefaultTableModel modeloTabla = (DefaultTableModel) jTable1.getModel();

            // Eliminar la fila seleccionada del modelo
            modeloTabla.removeRow(index);

            // La tabla se actualiza automáticamente porque está vinculada al modelo
        }

    }//GEN-LAST:event_jButton4ActionPerformed

    private void ComboClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboClienteActionPerformed

    }//GEN-LAST:event_ComboClienteActionPerformed

    private void ComboProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboProductoActionPerformed

    private void ProductoCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProductoCantidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ProductoCantidadActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        if (ComboCliente.getSelectedItem() == "................................" || ComboEstado.getSelectedItem() == null) {

            JOptionPane.showMessageDialog(null, "Por favor llene todos los campos del apartdo cliente", "Alerta", JOptionPane.INFORMATION_MESSAGE);
        } else {
            int respuesta = JOptionPane.showConfirmDialog(
                    null,
                    "¿Está seguro de que desea generar el nuevo pedido ?",
                    "Confirmar pedido",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            // Si el usuario confirma la eliminación
            if (respuesta == JOptionPane.YES_OPTION) {
                obtenerProductosDeTabla(jTable1);
                insertarPedido(ProductoPedido);
                dashboard.setEnabled(true);
                this.dispose();

            }

        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        int respuesta = JOptionPane.showConfirmDialog(
                null,
                "¿Está seguro de que desea cancelar el pedido?",
                "Confirmar salida",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (respuesta == JOptionPane.YES_OPTION) {

            dashboard.setEnabled(true);
            this.dispose();

        }

    }//GEN-LAST:event_jButton1ActionPerformed


    private void ComboEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboEstadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboEstadoActionPerformed

    private void FechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FechaActionPerformed

    }//GEN-LAST:event_FechaActionPerformed

    private void ProductoCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProductoCantidadKeyTyped
        char c = evt.getKeyChar();
        if(c<'0'||c>'9') evt.consume();
    }//GEN-LAST:event_ProductoCantidadKeyTyped

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
            java.util.logging.Logger.getLogger(AgregarPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AgregarPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AgregarPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AgregarPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AgregarCarrito;
    private javax.swing.JComboBox<String> ComboCliente;
    private javax.swing.JComboBox<String> ComboEstado;
    private javax.swing.JComboBox<String> ComboProducto;
    private javax.swing.JTextField Fecha;
    private javax.swing.JTextField ProductoCantidad;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
