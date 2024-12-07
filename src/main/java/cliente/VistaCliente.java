/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package cliente;


import cliente.EditarCliente;
import cliente.ControladorListClientes;
import cliente.ControladorClientes;
import cliente.AgregarCliente;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.awt.BorderLayout;
import java.util.List;
import javax.swing.JPanel;
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
public class VistaCliente extends javax.swing.JPanel {
    MediaType JSON = MediaType.get("application/json");
    ControladorClientes consulta = null;
    /**
     * Creates new form VistaCliente
     */
    public VistaCliente() {
        initComponents();
        mostrarTodo(consultaTodo());
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

    public void mostrarTodo(List<ControladorListClientes> clientes) {
        DefaultTableModel modeloTabla = new DefaultTableModel(new Object[]{"ID_CLIENTE", "NOMBRE", "APELLIDO","EMAIL", "TELEFONO"}, 1);
        for (ControladorListClientes item : clientes) {
            modeloTabla.addRow(new Object[]{item.getId_cliente(), item.getNombre(), item.getApellido(),item.getEmail(), item.getTelefono()});
        }

        jTable1.setModel(modeloTabla);

    }
    
    public void eliminarCliente(String id) {
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();
        String enlace = "http://localhost:3000/clientes/"+id;

        Request peticion = new Request.Builder().url(enlace).delete().build();
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
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        contenidoCliente = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        BotonAgregarCliente = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        BotonAgregarCliente.setBackground(new java.awt.Color(153, 255, 153));
        BotonAgregarCliente.setText("Agregar");
        BotonAgregarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonAgregarClienteActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Times New Roman", 2, 24)); // NOI18N
        jLabel1.setText("Cliente");

        jButton2.setBackground(new java.awt.Color(255, 255, 153));
        jButton2.setText("Editar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(255, 102, 102));
        jButton3.setText("Eliminar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout contenidoClienteLayout = new javax.swing.GroupLayout(contenidoCliente);
        contenidoCliente.setLayout(contenidoClienteLayout);
        contenidoClienteLayout.setHorizontalGroup(
            contenidoClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, contenidoClienteLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BotonAgregarCliente)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addGap(18, 18, 18)
                .addComponent(jButton3)
                .addGap(48, 48, 48))
            .addGroup(contenidoClienteLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(contenidoClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 757, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        contenidoClienteLayout.setVerticalGroup(
            contenidoClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contenidoClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(contenidoClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(BotonAgregarCliente)
                    .addComponent(jButton3))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(contenidoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(contenidoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        mostrarTodo(consultaTodo());// TODO add your handling code here:
    }//GEN-LAST:event_formComponentShown

    private void BotonAgregarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonAgregarClienteActionPerformed
        AgregarCliente ac1 = new AgregarCliente();
        
        ac1.setVisible(true);
        
        
        
        
    }//GEN-LAST:event_BotonAgregarClienteActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int index = jTable1.getSelectedRow();
        String codigo = jTable1.getValueAt(index, 0).toString();
        eliminarCliente(codigo);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       EditarCliente ed1 = new EditarCliente();
       int index = jTable1.getSelectedRow();
       String codigo = jTable1.getValueAt(index, 0).toString();
       ed1.setID(codigo);
       ed1.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BotonAgregarCliente;
    private javax.swing.JPanel contenidoCliente;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
