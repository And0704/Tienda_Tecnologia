/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.tienda_tecnologia;

import pedido.InsertarPedidos;
import pedido.ControladorListPedidos;
import cliente.ControladorListClientes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import producto.ControladorListProductos;
import producto.ControladorProductos;

/**
 *
 * @author Josue
 */
public class EditarPedido extends javax.swing.JFrame {

    private Dashboard dashboard;
    private String selectedProduct;
    MediaType JSON = MediaType.get("application/json");
    InsertarPedidos consulta;
    private String IdCliente;
    private int IdDetalle;
    private String nombreCliente;
    Map<Integer, String> productMap = new HashMap<>();
    private Integer cantidadModificada;
    Integer codigo;
    List<ControladorListPedidos> pedido;

    /**
     * Creates new form EditarPedido
     */
    public EditarPedido(Dashboard dashboard) {
        initComponents();
        consulta = new InsertarPedidos();
        this.dashboard = dashboard;
        cargarComboProducto(consultaTodoProducto());

    }

    public void setID(String IdCliente, String nombreCliente) {
        this.IdCliente = IdCliente;
        this.nombreCliente = nombreCliente;
        pedido = consultaUnoCliente(IdCliente);
        NombreCliente.setText(nombreCliente);
        mostrarTodo(pedido);

        // Llenar el modelo con los datos
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

    public List<ControladorListPedidos> consultaTodoPedido() {
        OkHttpClient client = new OkHttpClient();
        String enlace = "http://192.168.137.1:3000/detallePedido";

        Request peticion = new Request.Builder().url(enlace).build();

        try (Response respuesta = client.newCall(peticion).execute()) {
            if (respuesta.isSuccessful() && respuesta.body() != null) {
                String respuestaJSON = respuesta.body().string();

                java.lang.reflect.Type listaElementos = new TypeToken<List<ControladorListPedidos>>() {
                }.getType();

                return new Gson().fromJson(respuestaJSON, listaElementos);
            }
        } catch (Exception ex) {

        }
        return null;
    }

    public void eliminarProducto(String id) {
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();
        String enlace = "http://192.168.137.1:3000/detallePedido/" + id;

        Request peticion = new Request.Builder().url(enlace).delete().build();
        try (Response respuesta = client.newCall(peticion).execute()) {
            if (respuesta.isSuccessful()) {
                System.out.println("Exito al registro");

            } else {
                System.out.println("Error al insertar el registro");

            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<ControladorListPedidos> consultaUnoCliente(String id) {
        OkHttpClient client = new OkHttpClient();
        String enlace = "http://192.168.137.1:3000/detallePedido/" + id;

        Request peticion = new Request.Builder().url(enlace).build();

        try (Response respuesta = client.newCall(peticion).execute()) {
            if (respuesta.isSuccessful() && respuesta.body() != null) {
                String respuestaJSON = respuesta.body().string();

                java.lang.reflect.Type listaElementos = new TypeToken<List<ControladorListPedidos>>() {
                }.getType();

                return new Gson().fromJson(respuestaJSON, listaElementos);
            }
        } catch (Exception ex) {

        }
        return null;
    }

    public void mostrarTodo(List<ControladorListPedidos> clientes) {
        // Crear un modelo de tabla con el id_cliente como columna adicional (oculta)
        DefaultTableModel modeloTabla = new DefaultTableModel(new Object[]{"ID_PRODUCTO", "ID_PEDIDO", "Fecha", "NOMBRE PRODUCTO", "CANTIDAD", "PRECIO UNITARIO", "TOTAL", "ID_DETALLE"}, 0) ;

        // Llenar el modelo con los datos
        for (ControladorListPedidos item : clientes) {
            String fechaTratada = item.getFechaPedido();
            Double total = item.getPrecioUnitario() * item.getCantidad();
            modeloTabla.addRow(new Object[]{item.getIdProducto(), item.getIdPedido(), fechaTratada.split("T")[0], item.getNombreProducto(), item.getCantidad(), item.getPrecioUnitario(), total, item.getIdDetallePedido()});

        }

        // Asignar el modelo a la tabla
        jTable1.setModel(modeloTabla);
        jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(0).setWidth(0);

        jTable1.getColumnModel().getColumn(7).setMinWidth(0);
        jTable1.getColumnModel().getColumn(7).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(7).setWidth(0);

        // Ocultar la columna del ID (ID_CLIENTE)
    }

    public void eliminarPedido(String id) {
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();
        String enlace = "http://192.168.137.1:3000/pedido/" + id;

        Request peticion = new Request.Builder().url(enlace).delete().build();
        try (Response respuesta = client.newCall(peticion).execute()) {
            if (respuesta.isSuccessful()) {
                System.out.println("Se a eliminado de manera correcta");
            } else {
                System.out.println("Error al insertar el registro");

            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void editarPedido(InsertarPedidos nuevoProducto) {
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();
        String enlace = "http://192.168.137.1:3000/detallePedido/" + nuevoProducto.getId_detalle_pedido();
        System.out.println(enlace);
        String datosJSON = gson.toJson(nuevoProducto);

        RequestBody cuerpo = RequestBody.create(datosJSON, JSON);

        Request peticion = new Request.Builder().url(enlace).put(cuerpo).build();
        try (Response respuesta = client.newCall(peticion).execute()) {
            if (respuesta.isSuccessful()) {

            } else {
                System.out.println("Error al insertar el registro");

            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void cargarComboProducto(List<ControladorListProductos> productos) {
        try {
            for (ControladorListProductos item : productos) {
                String productName = item.getNombre();
                Integer productId = item.getId_producto();
                System.out.println(productId);
                productMap.put(productId, productName);
                ComboProducto.addItem(productName);
            }
            System.out.println("Se cargo los dato del combo box con exito");
        } catch (Exception e) {
            System.out.println("No se pudo ingresar valores");
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        ComboProducto = new javax.swing.JComboBox<>();
        EstadoBox = new javax.swing.JComboBox<>();
        EliminarDetalleBoton = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        EliminarPedido = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        NombreCliente = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        cantidadProducto = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 3, 24)); // NOI18N
        jLabel1.setText("Editar Pedido");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setText("CANTIDAD:");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel3.setText("Producto");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel4.setText("NOMBRE:");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel5.setText("ESTADO:");

        EstadoBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PAGADO ", "PENDIENTE" }));

        EliminarDetalleBoton.setBackground(new java.awt.Color(255, 102, 102));
        EliminarDetalleBoton.setText("ELIMINAR DETALLE");
        EliminarDetalleBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EliminarDetalleBotonActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(153, 204, 255));
        jButton2.setText("GUARDAR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

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

        jButton3.setBackground(new java.awt.Color(204, 255, 153));
        jButton3.setText("CONSULTAR");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        EliminarPedido.setBackground(new java.awt.Color(255, 102, 102));
        EliminarPedido.setText("ELIMINAR PEDIDO");
        EliminarPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EliminarPedidoActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel6.setText("CLIENTE:");

        NombreCliente.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        jButton1.setBackground(new java.awt.Color(255, 153, 102));
        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("REGRESAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        cantidadProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                cantidadProductoKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(113, 113, 113)
                        .addComponent(EliminarPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(97, 97, 97)
                        .addComponent(EliminarDetalleBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(179, 179, 179)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(NombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 646, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(ComboProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(22, 22, 22))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel5)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(EstadoBox, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel2)
                                            .addGap(18, 18, 18)
                                            .addComponent(cantidadProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGap(18, 18, 18)))
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(31, 31, 31)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(ComboProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cantidadProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(EstadoBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(52, 52, 52)
                        .addComponent(jButton3)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(NombreCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(12, 12, 12)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(EliminarPedido)
                    .addComponent(EliminarDetalleBoton))
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 920, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 17, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 19, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        int respuesta = JOptionPane.showConfirmDialog(
                null,
                "¿Está seguro de que desea salir de la edicion del  pedido?",
                "Confirmar salida",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (respuesta == JOptionPane.YES_OPTION) {
            dashboard.setEnabled(true);
            // Cerrar la ventana actual
            this.dispose();

        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (cantidadProducto.getText().isEmpty()) {

            // Mostrar un mensaje de advertencia si alguno de los campos está vacío
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.",
                    "Error", JOptionPane.WARNING_MESSAGE);
        } else {
            Integer cantidad = Integer.parseInt(cantidadProducto.getText());

            consulta.setId_producto(codigo);
            consulta.setCantidad(cantidad);
            consulta.setEstado((String) EstadoBox.getSelectedItem());

            consulta.setId_detalle_pedido(IdDetalle);
            editarPedido(consulta);
            mostrarTodo(pedido);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int index = jTable1.getSelectedRow();
        if (index == -1) {
            // No se seleccionó ninguna fila
            JOptionPane.showMessageDialog(null, "Por favor, seleccione una fila de la tabla.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        } else {
            codigo = (Integer) jTable1.getValueAt(index, 0);
            selectedProduct = productMap.get(codigo);
            ComboProducto.setSelectedItem(selectedProduct);
            cantidadModificada = (Integer) jTable1.getValueAt(index, 4);
            cantidadProducto.setText(String.valueOf(cantidadModificada));
            IdDetalle = (Integer) jTable1.getValueAt(index, 7);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void EliminarDetalleBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EliminarDetalleBotonActionPerformed
        int index = jTable1.getSelectedRow(); // Obtiene el índice de la fila seleccionada
        if (index == -1) {
            // Si no se seleccionó ninguna fila, muestra un mensaje de advertencia
            JOptionPane.showMessageDialog(null, "Por favor, seleccione una fila de la tabla.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        } else {
            // Obtén el ID del cliente desde la columna oculta
            String codigo = jTable1.getValueAt(index, 7).toString();
            String nombre = jTable1.getValueAt(index, 3).toString();

            // Muestra un cuadro de diálogo de confirmación
            int respuesta = JOptionPane.showConfirmDialog(
                    null,
                    "¿Está seguro de que desea eliminar el detalle de: " + nombre + "?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            // Si el usuario confirma la eliminación
            if (respuesta == JOptionPane.YES_OPTION) {
                eliminarProducto(codigo); // Llama al método para eliminar el cliente
                JOptionPane.showMessageDialog(null, "Registro eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
                modelo.setRowCount(0); // Limpia la tabla
                List<ControladorListPedidos> pedido = consultaUnoCliente(IdCliente);
                mostrarTodo(pedido);
            }
        }


    }//GEN-LAST:event_EliminarDetalleBotonActionPerformed

    private void EliminarPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EliminarPedidoActionPerformed
        int index = jTable1.getSelectedRow(); // Obtiene el índice de la fila seleccionada
        if (index == -1) {
            // Si no se seleccionó ninguna fila, muestra un mensaje de advertencia
            JOptionPane.showMessageDialog(null, "Por favor, seleccione una fila de la tabla.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        } else {
            // Obtén el ID del cliente desde la columna oculta
            String codigo = jTable1.getValueAt(index, 1).toString();
            String nombre = jTable1.getValueAt(index, 3).toString();

            // Muestra un cuadro de diálogo de confirmación
            int respuesta = JOptionPane.showConfirmDialog(
                    null,
                    "¿Está seguro de que desea eliminar al producto: " + nombre + "?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            // Si el usuario confirma la eliminación
            if (respuesta == JOptionPane.YES_OPTION) {
                eliminarPedido(codigo); // Llama al método para eliminar el cliente
                JOptionPane.showMessageDialog(null, "Pedido correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
                modelo.setRowCount(0); // Limpia la tabla
                List<ControladorListPedidos> pedido = consultaUnoCliente(IdCliente);
                mostrarTodo(pedido);
            }
        }
    }//GEN-LAST:event_EliminarPedidoActionPerformed

    private void cantidadProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cantidadProductoKeyTyped
        char c = evt.getKeyChar();
        if(c<'0'||c>'9') evt.consume();
    }//GEN-LAST:event_cantidadProductoKeyTyped

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
            java.util.logging.Logger.getLogger(EditarPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditarPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditarPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditarPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboProducto;
    private javax.swing.JButton EliminarDetalleBoton;
    private javax.swing.JButton EliminarPedido;
    private javax.swing.JComboBox<String> EstadoBox;
    private javax.swing.JLabel NombreCliente;
    private javax.swing.JTextField cantidadProducto;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
