import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class javaCrud {
    private JTextField nombreTf;
    private JButton grabarCrearButton;
    private JButton borrarDeleteButton;
    private JButton actualizarUpdateButton;
    private JTextField idTf;
    private JTextField precioTf;
    private JTextField ciudadTf;
    private JPanel Main;
    private JTextField cantidadTf;
    private JButton limpiarBTN;
    private JButton buscarBTN;


    public static void main(String[] args){

        JFrame frame = new JFrame("JAVA CRUD");
        frame.setContentPane(new javaCrud().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


    public javaCrud() {
        Connect();
        //Boton crear producto
        grabarCrearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Create();


            }
        });

        //Fin de crear producto

        //Boton borrar producto
        borrarDeleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                eliminar();

            }
        });
        //Fin de borrar producto

        //Boton actualizar datos de un producto
        actualizarUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                actualizar();
            }
        });

        //Boton de limpiar pantalla
        limpiarBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiar();
            }
        });
        //Fin de limpiar pantalla

        //Boton de buscar producto

        buscarBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                buscar();
            }
        });
        //Fin de buscar producto
    }

    Connection con;
    PreparedStatement pst;

    //Funcion conectar a la BDD
    public void Connect(){

        final String DB_URL="jdbc:mysql://localhost/misproductos?serverTimezone=UTC";
        final String USERNAME= "root";
        final String PASSWORD = "";

        try{
            Connection conn= DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement stmt= conn.createStatement();

            System.out.print("Conexión exitosa");

        }catch(SQLException ex){
            ex.printStackTrace();
            System.out.print("Sql incorrecto");

        }
    }
    //Fin de conectar a la BDD

    //Funcion añadir producto

    public void Create(){
        String nombre, precio, ciudad, id, cantidad;

        nombre= nombreTf.getText();
        precio= precioTf.getText();
        ciudad= ciudadTf.getText();
        id= idTf.getText();
        cantidad= cantidadTf.getText();

        System.out.println(nombre);
        System.out.println(precio);
        System.out.println(ciudad);
        System.out.println(id);
        System.out.println(cantidad);

        final String DB_URL="jdbc:mysql://localhost/misproductos?serverTimezone=UTC";
        final String USERNAME= "root";
        final String PASSWORD = "";


        try{
            Connection conn= DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement stmt= conn.createStatement();

            String sql= "insert into productos(pombre, pciudad, pprecio, pcantidad)values(?,?,?,?)";
            PreparedStatement pst= conn.prepareStatement(sql);
            pst.setString(1, nombre);
            pst.setString(2, ciudad);
            pst.setString(3, precio);
            pst.setString(4, cantidad);

            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Producto agredado correctamente");


        }catch (SQLException ex){

            ex.printStackTrace();
            System.out.println("SQL incorrecto");
        }

    }
    //Fin de añadir producto


    //Funcion limpiar pantalla
    public void limpiar(){
        nombreTf.setText("");
        precioTf.setText("");
        cantidadTf.setText("");
        idTf.setText("");
        ciudadTf.setText("");
    }

    //Fin de limpiar pantalla

    //Funcion de buscar
    public void buscar(){

        String  id ="0";
        id = idTf.getText();

        final String DB_URL="jdbc:mysql://localhost/misproductos?serverTimezone=UTC";
        final String USERNAME= "root";
        final String PASSWORD = "";


        try{
            Connection conn= DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement stmt= conn.createStatement();

            String sql= "select * from productos where pid = ?";
            pst.setString(1, id);
            PreparedStatement pst= conn.prepareStatement(sql);

            ResultSet rs=pst.executeQuery();
            //pst.executeQuery();
            if (rs.next() ==  true){
                String nombre, ciudad, precio, cantidad;
                nombre = rs.getString(2);
                ciudad = rs.getString(3);
                precio = rs.getString(4);
                cantidad = rs.getString(5);

                System.out.println();
                nombreTf.setText(nombre);
                ciudadTf.setText(ciudad);
                precioTf.setText(precio);
                cantidadTf.setText(cantidad);

            }
            else{

                JOptionPane.showMessageDialog(null, "Producto no encontrado, intente con otro");
                limpiar();
            }



            stmt.close();
            conn.close();


        }catch (SQLException ex){

            ex.printStackTrace();
            System.out.println("SQL incorrecto");
        }
    }
    //Fin de buscar

    //Funcion de actulizar/update
    public void actualizar(){
        String id, nombre, ciudad, precio, cantidad;
        id=idTf.getText();

        nombre=nombreTf.getText();
        ciudad=ciudadTf.getText();
        precio=precioTf.getText();
        cantidad=cantidadTf.getText();

        final String DB_URL="jdbc:mysql://localhost/misproductos?serverTimezone=UTC";
        final String USERNAME="root";
        final String PASSWORD="";


        try{
            Connection conn= DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
            Statement stmt= conn.createStatement();
            String sql="update productos set pnombre=?, pciudad=?,pprecio=?,pcantidad=? where pid=?";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,nombre);
            pst.setString(2,ciudad);
            pst.setString(3,precio);
            pst.setString(4,cantidad);
            pst.setString(5,id);
            //ResultSet resultSet=pst.executeQuery();
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null,"Registro actualizado");
            stmt.close();
            conn.close();

        } catch(SQLException ex){
            ex.printStackTrace();
            System.out.println("SQL incorrecto");

        }

    }

    //Fin de actualicar

    //Funcion de eliminar
    public void eliminar(){
        final String DB_URL="jdbc:mysql://localhost/misproductos?serverTimezone=UTC";
        final String USERNAME="root";
        final String PASSWORD="";
        String borrarid=idTf.getText();

        try{
            Connection conn= DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
            Statement stmt= conn.createStatement();
            String sql="delete from productos where pid=?";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,borrarid);

            pst.executeUpdate();
            JOptionPane.showMessageDialog(null,"Registro borrado");
            stmt.close();
            conn.close();

        } catch(SQLException ex){
            ex.printStackTrace();
            System.out.println("SQL incorrecto");

        }
    }
    //Fin de eliminar producto
}

