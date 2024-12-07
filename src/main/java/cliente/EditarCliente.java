/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package cliente;

import cliente.ControladorListClientes;
import cliente.ControladorClientes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 *
 * @author Josue
 */
public class EditarCliente extends javax.swing.JFrame {
    MediaType JSON = MediaType.get("application/json");
    ControladorClientes consulta = null;
    /**
     * Creates new form AgregarCliente1
     */
    
    private String idCliente;
    public EditarCliente() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    public void setID(String idCliente){
        this.idCliente = idCliente;
        System.out.println(idCliente);
        
        ControladorListClientes cliente = consultaUNO(idCliente);
        
        consulta = new ControladorClientes();
        consulta.setId_cliente(cliente.getId_cliente());  
        consulta.setNombre(cliente.getNombre());        
        consulta.setApellido(cliente.getApellido());
        consulta.setEmail(cliente.getEmail());
        consulta.setTelefono(cliente.getTelefono());
        
       IngresoNombre.setText(consulta.getNombre());       
       IngresoApellido.setText(consulta.getApellido());
       IngresoEmail.setText(consulta.getEmail());
       IngresoTelefono.setText(consulta.getTelefono());
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        IngresoNombre = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        IngresoApellido = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        IngresoTelefono = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        IngresoEmail = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setText("Editar informacion del cliente");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 2, 14)); // NOI18N
        jLabel2.setText("Nombre");

        IngresoNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IngresoNombreActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Times New Roman", 2, 14)); // NOI18N
        jLabel3.setText("Apellido");

        IngresoApellido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IngresoApellidoActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Times New Roman", 2, 14)); // NOI18N
        jLabel4.setText("Telefono");

        IngresoTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IngresoTelefonoActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Times New Roman", 2, 14)); // NOI18N
        jLabel6.setText("Email");

        IngresoEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IngresoEmailActionPerformed(evt);
            }
        });

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator2.setToolTipText("");

        jButton1.setBackground(new java.awt.Color(255, 153, 102));
        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("REGRESAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(0, 204, 255));
        jButton2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Confirmar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jSeparator1)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(IngresoApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(IngresoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(IngresoTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(IngresoEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(94, 94, 94))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(94, 94, 94)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(356, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(325, 325, 325)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(12, 12, 12)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(IngresoTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(IngresoEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(IngresoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(IngresoApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(149, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(323, Short.MAX_VALUE)
                    .addComponent(jButton2)
                    .addGap(66, 66, 66)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 417, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void IngresoNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IngresoNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IngresoNombreActionPerformed

    private void IngresoApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IngresoApellidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IngresoApellidoActionPerformed

    private void IngresoTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IngresoTelefonoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IngresoTelefonoActionPerformed

    private void IngresoEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IngresoEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IngresoEmailActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        consulta.setNombre(IngresoNombre.getText());        
        consulta.setApellido(IngresoApellido.getText());
        
        consulta.setEmail(IngresoEmail.getText());
        consulta.setTelefono(IngresoTelefono.getText());
        editarCliente(consulta);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    
   
   
    public ControladorListClientes consultaUNO(String id) {
        OkHttpClient client = new OkHttpClient();
        String enlace = "http://localhost:3000/clientes/" + id;

        Request peticion = new Request.Builder().url(enlace).build();

        try ( Response respuesta = client.newCall(peticion).execute()) {
            if (respuesta.isSuccessful() && respuesta.body() != null) {
                String respuestaJSON = respuesta.body().string();

                 java.lang.reflect.Type listaElementos = new TypeToken<List<ControladorListClientes>>() {
                }.getType();

                List<ControladorListClientes> datos= new Gson().fromJson(respuestaJSON, listaElementos);
                
                return datos.get(0);
            }
        } catch (Exception ex) {

        }
        return null;
    }
    
    public void insertarCliente(ControladorClientes nuevoCliente) {
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();
        String enlace = "http://localhost:3000/clientes";

        String datosJSON = gson.toJson(nuevoCliente);

        RequestBody cuerpo = RequestBody.create(datosJSON, JSON);

        Request peticion = new Request.Builder().url(enlace).post(cuerpo).build();
        try ( Response respuesta = client.newCall(peticion).execute()) {
            if (respuesta.isSuccessful()) {
                System.out.print("Se a editado de manera correcta");
            } else {
                System.out.println("Error al insertar el registro");

            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
    public void mostrarTodo(List<ControladorListClientes> clientes) {
        DefaultTableModel modeloTabla = new DefaultTableModel(new Object[]{"ID_CLIENTE", "NOMBRE", "APELLIDO","EMAIL", "TELEFONO"}, 1);
        for (ControladorListClientes item : clientes) {
            modeloTabla.addRow(new Object[]{item.getId_cliente(), item.getNombre(), item.getApellido(),item.getEmail(), item.getTelefono()});
        }

    }
    
    public List<ControladorListClientes> consultaTodo() {
        OkHttpClient client = new OkHttpClient();
        String enlace = "http://localhost:3000/clientes";

        Request peticion = new Request.Builder().url(enlace).build();

        try ( Response respuesta = client.newCall(peticion).execute()) {
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
    
    public void editarCliente(ControladorClientes nuevoCliente) {
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();
        String enlace = "http://localhost:3000/clientes/"+nuevoCliente.getId_cliente();
        System.out.println(enlace);
        String datosJSON = gson.toJson(nuevoCliente);

        RequestBody cuerpo = RequestBody.create(datosJSON, JSON);

        Request peticion = new Request.Builder().url(enlace).put(cuerpo).build();
        try ( Response respuesta = client.newCall(peticion).execute()) {
            if (respuesta.isSuccessful()) {
                mostrarTodo(consultaTodo());
            } else {
                System.out.println("Error al insertar el registro");

            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
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
            java.util.logging.Logger.getLogger(EditarCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditarCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditarCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditarCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditarCliente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField IngresoApellido;
    private javax.swing.JTextField IngresoEmail;
    private javax.swing.JTextField IngresoNombre;
    private javax.swing.JTextField IngresoTelefono;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    // End of variables declaration//GEN-END:variables
}
