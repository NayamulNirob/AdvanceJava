/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package totalWiew;

import com.toedter.calendar.JDateChooser;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import utill.DbUtill;

/**
 *
 * @author nayam
 */
public class ProductView extends javax.swing.JFrame {

    DbUtill db = new DbUtill();
    PreparedStatement ps;
    ResultSet rs;
    LocalDate currentDate = LocalDate.now();
    Date sqlCurrentDate = Date.valueOf(currentDate);
    public static float stockQuantity=0;

    /**
     * Creates new form StoreView
     */
    public ProductView() {
        initComponents();
        showProductOnTable();
        showProductCombo();
        showStockOnTable();

        comProductName.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                getProductSalesPrice(e);

            }

        });

    }
    
  

    public void getSalesReport() {

        String[] salesViewTableCloumn = {"Sl", "Name", "Unit Price", "Quantity", "Total Price", "Date"};

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(salesViewTableCloumn);
        tblReports.setModel(model);
        java.util.Date fromDate = dateFromReports.getDate();
        java.util.Date toDate = dateToReports.getDate();

        String sql = "select* from sales where salesDate between ? and ?";
        String sql1 = "select sum(salesTotalPrice)from sales where salesDate between ? and ?";

        try {
            ps = db.getCon().prepareStatement(sql);
            ps.setDate(1, convertUtilDateToSqlDate(fromDate));
            ps.setDate(2, convertUtilDateToSqlDate(toDate));
            rs = ps.executeQuery();
            int sl = 1;
            while (rs.next()) {

                String name = rs.getString("name");
                float unitPrice = rs.getFloat("salesunitePrice");
                float quantity = rs.getFloat("salesQuantity");
                float totalPricee = rs.getFloat("salesTotalPrice");
                Date salesDate = rs.getDate("salesDate");
                model.addRow(new Object[]{sl, name, unitPrice, quantity, totalPricee, salesDate});
                sl++;
            }
            ps.close();
            rs.close();
            db.getCon().close();

            ps = db.getCon().prepareStatement(sql1);
            ps.setDate(1, convertUtilDateToSqlDate(fromDate));
            ps.setDate(2, convertUtilDateToSqlDate(toDate));
            rs = ps.executeQuery();

            while (rs.next()) {
                float totalPricee = rs.getFloat("sum(salesTotalPrice)");
                model.addRow(new Object[]{"", "", "", "Total Amount:", totalPricee});
            }
            ps.close();
            rs.close();
            db.getCon().close();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void getGrossProfit() {
        java.util.Date fromDate = dateFromReports.getDate();
        java.util.Date toDate = dateToReports.getDate();

        String sql = "select sum(salesTotalPrice)from sales where salesDate between ? and ?";
        String sqll = "select sum(TotalPrice)from product where purchaseDate between ? and ?";
        try {
            ps = db.getCon().prepareStatement(sqll);
            ps.setDate(1, convertUtilDateToSqlDate(fromDate));
            ps.setDate(2, convertUtilDateToSqlDate(toDate));
            rs = ps.executeQuery();
            float totalParchasePricee = 0;
            while (rs.next()) {

                totalParchasePricee = rs.getFloat("sum(TotalPrice)");

            }
            ps.close();
            rs.close();
            db.getCon().close();
            
            ps = db.getCon().prepareStatement(sql);
            ps.setDate(1, convertUtilDateToSqlDate(fromDate));
            ps.setDate(2, convertUtilDateToSqlDate(toDate));
            rs = ps.executeQuery();
            
             float totalSalesPricee = 0;
            while (rs.next()) {
                totalSalesPricee = rs.getFloat("sum(salesTotalPrice)");
              
            }
            ps.close();
            rs.close();
            db.getCon().close();
            
            float grossProfit = totalSalesPricee -totalParchasePricee;
            lblProfit.setText("Profit : "+grossProfit);

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getPurchaseReports() {

        String[] productViewTableCloumn = {"Sl", "Name", "Unit Price", "Quantity", "Total Price", "Sales Price", "Date"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(productViewTableCloumn);
        tblReports.setModel(model);

        java.util.Date fromDate = dateFromReports.getDate();
        java.util.Date toDate = dateToReports.getDate();

        String sql = "select* from product where purchaseDate between ? and ?";
        try {
            ps = db.getCon().prepareStatement(sql);
            ps.setDate(1, convertUtilDateToSqlDate(fromDate));
            ps.setDate(2, convertUtilDateToSqlDate(toDate));
            rs = ps.executeQuery();
            int sl = 1;
            while (rs.next()) {

                String name = rs.getString("name");
                float unitPrice = rs.getFloat("unitPrice");
                float quantity = rs.getFloat("Quantity");
                float totalPricee = rs.getFloat("TotalPrice");
                float SalesPrice = rs.getFloat("SalesPrice");
                Date purchaseDate = rs.getDate("purchaseDate");
                model.addRow(new Object[]{sl, name, unitPrice, quantity, totalPricee, SalesPrice, purchaseDate});
                sl++;
            }
            ps.close();
            rs.close();
            db.getCon().close();

            String sqll = "select sum(TotalPrice)from product where purchaseDate between ? and ?";
            ps = db.getCon().prepareStatement(sqll);
            ps.setDate(1, convertUtilDateToSqlDate(fromDate));
            ps.setDate(2, convertUtilDateToSqlDate(toDate));
            rs = ps.executeQuery();
            while (rs.next()) {

                float totalPricee = rs.getFloat("sum(TotalPrice)");

                model.addRow(new Object[]{"", "", "", "Total Amount :", totalPricee});

            }
            ps.close();
            rs.close();
            db.getCon().close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public boolean getStockProductList() {
        String sql = "select name from stock";
        boolean status = false;
        String purchesProductName = txtPrductName.getText().trim();

        try {
            ps = db.getCon().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String productName = rs.getString("name");
                if (productName.equalsIgnoreCase(purchesProductName)) {
                    status = true;
                    break;
                }

            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }

    public void addproductToStock() {
        boolean status = getStockProductList();

        if (status) {
            String sql = "update stock set quantity=quantity+? where name=?";
            try {
                ps = db.getCon().prepareStatement(sql);
                ps.setFloat(1, Float.parseFloat(txtProductQuantity.getText().trim()));
                ps.setString(2, txtPrductName.getText().trim());
                ps.executeUpdate();
                ps.close();
                db.getCon().close();

            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {

            String sql = "insert into stock(name,parchesPrice,Quantity)values(?,?,?)";

            try {
                ps = db.getCon().prepareStatement(sql);

                ps.setString(1, txtPrductName.getText().trim());
                ps.setFloat(2, Float.parseFloat(txtPrductUnitPrice.getText().trim()));
                ps.setFloat(3, Float.parseFloat(txtProductQuantity.getText().trim()));

                ps.executeUpdate();
                ps.close();
                db.getCon().close();

            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void addProduct() {

        String sql = "insert into product(name,unitPrice,Quantity,TotalPrice,SalesPrice,purchaseDate)values(?,?,?,?,?,?)";

        try {
            ps = db.getCon().prepareStatement(sql);
            ps.setString(1, txtPrductName.getText().trim());
            ps.setFloat(2, Float.parseFloat(txtPrductUnitPrice.getText().trim()));
            ps.setFloat(3, Float.parseFloat(txtProductQuantity.getText().trim()));
            ps.setFloat(4, Float.parseFloat(txtPrductTotalPrice.getText().trim()));
            ps.setFloat(5, Float.parseFloat(txtPrductSalesPrice.getText().trim()));
            ps.setDate(6, sqlCurrentDate);

            ps.executeUpdate();
            ps.close();
            db.getCon().close();
            JOptionPane.showMessageDialog(this, "Add product Successfully");
            addproductToStock();
            showStockOnTable();
            clear();
            showProductOnTable();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Add product Unsuccessful");
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void getTotalPrice() {

        float unitPrice = Float.parseFloat(txtPrductUnitPrice.getText().trim());
        float quantity = Float.parseFloat(txtProductQuantity.getText().trim());
        float totalPrice = unitPrice * quantity;
        txtPrductTotalPrice.setText(totalPrice + "");
    }

    public void clear() {
        txtProductId.setText("");
        txtPrductName.setText("");
        txtPrductUnitPrice.setText("");
        txtProductQuantity.setText("");
        txtPrductTotalPrice.setText("");
        txtPrductSalesPrice.setText("");

    }

    String[] productViewTableCloumn = {"Id", "Name", "Unit Price", "Quantity", "Total Price", "Sales Price"};

    public void showProductOnTable() {
        String sql = "select* from product";
        PreparedStatement ps;
        ResultSet rs;

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(productViewTableCloumn);
        tblProductView.setModel(model);

        try {
            ps = db.getCon().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                float unitPrice = rs.getFloat("unitPrice");
                float quantity = rs.getFloat("quantity");
                float totalPrice = rs.getFloat("totalprice");
                float salesPrice = rs.getFloat("salesPrice");
                model.addRow(new Object[]{id, name, unitPrice, quantity, totalPrice, salesPrice});
            }
            rs.close();
            ps.close();
            db.getCon();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    String[] stockViewTableCloumn = {"Id", "Name", "Quantity", "Unit Price"};

    public void showStockOnTable() {
        String sql = "select* from stock";
        PreparedStatement ps;
        ResultSet rs;

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(stockViewTableCloumn);
        tblStock.setModel(model);

        try {
            ps = db.getCon().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                float quantity = rs.getFloat("quantity");
                float unitPrice = rs.getFloat("parchesPrice");
                model.addRow(new Object[]{id, name, quantity, unitPrice});
            }
            rs.close();
            ps.close();
            db.getCon();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void delectProduct() {

        String sql = "delete from product where id=?";
        PreparedStatement ps;

        try {
            ps = db.getCon().prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(txtProductId.getText()));
            ps.executeUpdate();
            ps.close();
            db.getCon();
            JOptionPane.showMessageDialog(this, "Delete product Successfully");
            clear();
            showProductOnTable();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Delete product Unsuccessfull");
        }

    }

    public void editProduct() {

        String sql = "update  product set name=?,unitPrice=?,quantity=?,totalprice=?,salesprice=? where id=?";
        PreparedStatement ps;
        try {

            ps = db.getCon().prepareStatement(sql);
            ps.setString(1, txtPrductName.getText().trim());
            ps.setFloat(2, Float.parseFloat(txtPrductUnitPrice.getText().trim()));
            ps.setFloat(3, Float.parseFloat(txtProductQuantity.getText().trim()));
            ps.setFloat(4, Float.parseFloat(txtPrductTotalPrice.getText().trim()));
            ps.setFloat(5, Float.parseFloat(txtPrductSalesPrice.getText().trim()));
            ps.setInt(6, Integer.parseInt(txtProductId.getText()));
            ps.executeUpdate();
            ps.close();
            db.getCon();
            JOptionPane.showMessageDialog(this, "Update product Successfully");
            clear();
            showProductOnTable();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Update product UnSuccessfull");
        } catch (SQLException ex) {
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Update product Unsuccessfull");
        }

    }

    public void showProductCombo() {
        String sql = "select name from product";
        PreparedStatement ps;
        ResultSet rs;
        comProductName.removeAllItems();
        try {
            ps = db.getCon().prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String productName = rs.getString("name");
                comProductName.addItem(productName);

            }
            ps.close();
            db.getCon().close();
            rs.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void getProductSalesPrice(ItemEvent e) {
        String selectedProductName = "";
        if (e.getStateChange() == ItemEvent.SELECTED) {

            selectedProductName = (String) e.getItem();
            extractSalesPrice(selectedProductName);
        }
        String sql = "select quantity from stock where name=?";
        try {
            ps = db.getCon().prepareStatement(sql);
            ps.setString(1, selectedProductName);
            rs = ps.executeQuery();
            while (rs.next()) {
                stockQuantity = rs.getFloat("quantity");
                lblStock.setText(stockQuantity + "");
            }
            ps.close();
            db.getCon().close();
            rs.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    
    public void  salesValidationQuantity(){
    float salesQuantity = Float.parseFloat(txtSelesQuantity.getText().trim());
    if(salesQuantity>stockQuantity){
    
    JOptionPane.showMessageDialog(this, "Sales Quantity is more than Stock ");
    txtSelesQuantity.setText("0");
    txtSelesQuantity.requestFocus();
    
    }
    else{
    
    }
    
    }
    
    
    
    
    public void extractSalesPrice(String productName) {
        String sql = "select SalesPrice from product where name=?";
        PreparedStatement ps;
        ResultSet rs;

        try {
            ps = db.getCon().prepareStatement(sql);
            ps.setString(1, productName);
            rs = ps.executeQuery();

            while (rs.next()) {
                String SalesPrice = rs.getString("SalesPrice");
                txtSalesUnitePrice.setText(SalesPrice);
            }

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void getTotalsalesPrice() {

        float quantity = Float.parseFloat(txtSelesQuantity.getText().toString().trim());
        float unitprice = Float.parseFloat(txtSalesUnitePrice.getText().toString().trim());

        float salesTotalPrice = quantity * unitprice;
        txtSalesTotalPrice.setText(salesTotalPrice + "");

    }

    public static String formateDateToDDMMYYYY(JDateChooser date) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(date);
    }

    public static java.sql.Date convertUtilDateToSqlDate(java.util.Date utilDate) {
        if (utilDate != null) {
            return new java.sql.Date(utilDate.getTime());
        }
        return null;
    }

    public static java.sql.Date convertStringToSqlDate(String dateString) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("MM-dd-yyyy");
        try {
            java.util.Date utilDate = inputFormat.parse(dateString);

            // Convert to "yyyy-MM-dd" format
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = outputFormat.format(utilDate);

            return java.sql.Date.valueOf(formattedDate);
        } catch (ParseException e) {
            return null; // Return null or handle the error as needed
        }
    }

    public void stockUpdateOnSales() {
        String sql = "update stock set quantity=quantity-? where name=?";
        try {
            ps = db.getCon().prepareStatement(sql);
            ps.setFloat(1, Float.parseFloat(txtSelesQuantity.getText().trim()));
            ps.setString(2, comProductName.getSelectedItem().toString());
            ps.executeUpdate();
            ps.close();
            db.getCon().close();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addSales() {

        java.util.Date date = convertUtilDateToSqlDate(salesDate.getDate());

        PreparedStatement ps;
        String sql = "insert into sales(name, salesunitePrice,salesQuantity,salesTotalPrice,salesDate) "
                + "values(?,?,?,?,?)";

        try {
            ps = db.getCon().prepareStatement(sql);
            ps.setString(1, comProductName.getSelectedItem().toString());
            ps.setFloat(2, Float.parseFloat(txtSalesUnitePrice.getText()));
            ps.setFloat(3, Float.parseFloat(txtSelesQuantity.getText()));
            ps.setFloat(4, Float.parseFloat(txtSalesTotalPrice.getText()));
            ps.setDate(5, convertUtilDateToSqlDate(date));

            ps.executeUpdate();

            ps.close();
            db.getCon().close();

            JOptionPane.showMessageDialog(this, "Add Sales Successfully");
            stockUpdateOnSales();
            showStockOnTable();
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(this, "Add Sales Unsuccessfully");
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
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
        jPanel2 = new javax.swing.JPanel();
        btnAddProduct = new javax.swing.JButton();
        btnSalesProduct = new javax.swing.JButton();
        btnStockProdct = new javax.swing.JButton();
        btnProductrReport = new javax.swing.JButton();
        productView = new javax.swing.JTabbedPane();
        add = new javax.swing.JPanel();
        add1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtProductId = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtPrductName = new javax.swing.JTextField();
        txtPrductUnitPrice = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtProductQuantity = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtPrductTotalPrice = new javax.swing.JTextField();
        btnProductAdd = new javax.swing.JButton();
        btnProductDelete = new javax.swing.JButton();
        btnProductEdit = new javax.swing.JButton();
        btnProductReset = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        txtPrductSalesPrice = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProductView = new javax.swing.JTable();
        sales = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        comProductName = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        txtSalesTotalPrice = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        salesDate = new com.toedter.calendar.JDateChooser();
        jLabel14 = new javax.swing.JLabel();
        txtSalesUnitePrice = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtSelesQuantity = new javax.swing.JTextField();
        btnSalesSave = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        lblStock = new javax.swing.JLabel();
        stock = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblStock = new javax.swing.JTable();
        report = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        dateToReports = new com.toedter.calendar.JDateChooser();
        dateFromReports = new com.toedter.calendar.JDateChooser();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        btnReportsSales = new javax.swing.JButton();
        btnReportsGrossProfit = new javax.swing.JButton();
        btnReportsReset = new javax.swing.JButton();
        btnReportsPurchase = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblReports = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        lblProfit = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 0, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 204, 204));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Store(JEE59)");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 130));

        jPanel2.setBackground(new java.awt.Color(0, 255, 255));

        btnAddProduct.setText("Add Prodoct");
        btnAddProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddProductMouseClicked(evt);
            }
        });

        btnSalesProduct.setText("Sales Product");
        btnSalesProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSalesProductMouseClicked(evt);
            }
        });

        btnStockProdct.setText("Stock Product");
        btnStockProdct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnStockProdctMouseClicked(evt);
            }
        });
        btnStockProdct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStockProdctActionPerformed(evt);
            }
        });

        btnProductrReport.setText("Product Report");
        btnProductrReport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProductrReportMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnProductrReport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnStockProdct, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAddProduct, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSalesProduct, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(btnAddProduct)
                .addGap(35, 35, 35)
                .addComponent(btnSalesProduct)
                .addGap(32, 32, 32)
                .addComponent(btnStockProdct)
                .addGap(30, 30, 30)
                .addComponent(btnProductrReport)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 220, 520));

        productView.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                productViewMouseClicked(evt);
            }
        });

        add.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        add1.setPreferredSize(new java.awt.Dimension(1000, 650));
        add1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(0, 153, 255));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Product");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 770, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(224, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        add1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("ID");
        add1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 60, 20));

        txtProductId.setEditable(false);
        txtProductId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProductIdActionPerformed(evt);
            }
        });
        add1.add(txtProductId, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 100, 180, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Name");
        add1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 60, 20));
        add1.add(txtPrductName, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 140, 180, -1));
        add1.add(txtPrductUnitPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 180, 180, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Unit Price");
        add1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 70, 20));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Quantity");
        add1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 220, 90, 20));

        txtProductQuantity.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtProductQuantityFocusLost(evt);
            }
        });
        add1.add(txtProductQuantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 220, 180, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Total Price");
        add1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 260, 90, 20));

        txtPrductTotalPrice.setEditable(false);
        txtPrductTotalPrice.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPrductTotalPriceFocusLost(evt);
            }
        });
        txtPrductTotalPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrductTotalPriceActionPerformed(evt);
            }
        });
        add1.add(txtPrductTotalPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 260, 180, -1));

        btnProductAdd.setText("Add");
        btnProductAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProductAddMouseClicked(evt);
            }
        });
        add1.add(btnProductAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 130, -1, -1));

        btnProductDelete.setText("Delete");
        btnProductDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProductDeleteMouseClicked(evt);
            }
        });
        add1.add(btnProductDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 130, -1, -1));

        btnProductEdit.setText("Edit");
        btnProductEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProductEditMouseClicked(evt);
            }
        });
        add1.add(btnProductEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 220, -1, -1));

        btnProductReset.setText("Reset");
        btnProductReset.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProductResetMouseClicked(evt);
            }
        });
        add1.add(btnProductReset, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 220, -1, -1));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Sales Price");
        add1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, 90, 20));
        add1.add(txtPrductSalesPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 300, 180, -1));

        tblProductView.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblProductView.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProductViewMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblProductView);

        add1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 330, 780, 180));

        add.add(add1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 780, -1));

        productView.addTab("Add", add);

        sales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                salesMouseClicked(evt);
            }
        });
        sales.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(204, 0, 204));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 204));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Product Sales");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 768, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(7, Short.MAX_VALUE))
        );

        sales.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 780, 60));

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Name");
        sales.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 50, 30));

        comProductName.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comProductName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                comProductNameMouseClicked(evt);
            }
        });
        sales.add(comProductName, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 80, 210, 30));

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Quantity");
        sales.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 80, 60, 30));

        txtSalesTotalPrice.setEditable(false);
        sales.add(txtSalesTotalPrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 130, 140, 30));

        jLabel15.setText("Date :");
        sales.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 80, 40, 30));
        sales.add(salesDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 82, 170, 30));

        jLabel14.setText("Unit Price");
        sales.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 80, 30));

        txtSalesUnitePrice.setEditable(false);
        sales.add(txtSalesUnitePrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 130, 210, 30));

        jLabel16.setText("Total Price");
        sales.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 130, 60, 30));

        txtSelesQuantity.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSelesQuantityFocusLost(evt);
            }
        });
        sales.add(txtSelesQuantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 80, 140, 30));

        btnSalesSave.setText("Save");
        btnSalesSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSalesSaveMouseClicked(evt);
            }
        });
        sales.add(btnSalesSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 200, -1, -1));

        jButton2.setText("Edit");
        sales.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 200, -1, -1));

        jButton3.setText("Reset");
        sales.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 200, -1, -1));

        jButton4.setText("Delete");
        sales.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 200, -1, -1));

        jLabel4.setText("Stock ");
        sales.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 136, 40, 20));
        sales.add(lblStock, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 130, 170, 30));

        productView.addTab("Sales", sales);

        stock.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBackground(new java.awt.Color(0, 255, 204));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(102, 0, 102));
        jLabel17.setText("Stock");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(337, 337, 337)
                .addComponent(jLabel17)
                .addContainerGap(381, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel17)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        stock.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 780, 70));

        tblStock.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblStock.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jScrollPane2.setViewportView(tblStock);

        stock.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 780, 320));

        productView.addTab("Stock", stock);

        report.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBackground(new java.awt.Color(255, 51, 51));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 153));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Reports");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                .addContainerGap())
        );

        report.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 780, 80));
        report.add(dateToReports, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 120, 190, 30));
        report.add(dateFromReports, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 120, 190, 30));

        jLabel18.setText("To");
        report.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 120, 50, 30));

        jLabel19.setText("From");
        report.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 40, 30));

        btnReportsSales.setText("Sales");
        btnReportsSales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnReportsSalesMouseClicked(evt);
            }
        });
        report.add(btnReportsSales, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 180, -1, -1));

        btnReportsGrossProfit.setText("Gross Profit");
        btnReportsGrossProfit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnReportsGrossProfitMouseClicked(evt);
            }
        });
        report.add(btnReportsGrossProfit, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 180, -1, -1));

        btnReportsReset.setText("Reset");
        btnReportsReset.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnReportsResetMouseClicked(evt);
            }
        });
        report.add(btnReportsReset, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 180, -1, -1));

        btnReportsPurchase.setText("Pruchase");
        btnReportsPurchase.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnReportsPurchaseMouseClicked(evt);
            }
        });
        report.add(btnReportsPurchase, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, -1, -1));

        tblReports.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tblReports);

        report.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 230, 780, 200));

        jPanel7.setBackground(new java.awt.Color(0, 204, 153));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblProfit.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblProfit.setForeground(new java.awt.Color(0, 0, 153));
        lblProfit.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblProfit.setText("Profit :");
        jPanel7.add(lblProfit, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 250, 40));

        report.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 170, 250, 40));

        productView.addTab("Report", report);

        getContentPane().add(productView, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 130, 780, 520));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnStockProdctActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStockProdctActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnStockProdctActionPerformed

    private void btnAddProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddProductMouseClicked
        productView.setSelectedIndex(0);
    }//GEN-LAST:event_btnAddProductMouseClicked

    private void btnSalesProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalesProductMouseClicked
        productView.setSelectedIndex(1);
        showProductCombo();
    }//GEN-LAST:event_btnSalesProductMouseClicked

    private void btnStockProdctMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStockProdctMouseClicked
        productView.setSelectedIndex(2);
    }//GEN-LAST:event_btnStockProdctMouseClicked

    private void btnProductrReportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProductrReportMouseClicked
        productView.setSelectedIndex(3);
    }//GEN-LAST:event_btnProductrReportMouseClicked

    private void btnProductAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProductAddMouseClicked
        // TODO add your handling code here:
        addProduct();
    }//GEN-LAST:event_btnProductAddMouseClicked

    private void txtPrductTotalPriceFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPrductTotalPriceFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrductTotalPriceFocusLost

    private void txtProductQuantityFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtProductQuantityFocusLost
        // TODO add your handling code here:
        getTotalPrice();
    }//GEN-LAST:event_txtProductQuantityFocusLost

    private void txtProductIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProductIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProductIdActionPerformed

    private void txtPrductTotalPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrductTotalPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrductTotalPriceActionPerformed

    private void tblProductViewMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProductViewMouseClicked
        // TODO add your handling code here:
        int rowindex = tblProductView.getSelectedRow();
        String id = tblProductView.getModel().getValueAt(rowindex, 0).toString();
        String name = tblProductView.getModel().getValueAt(rowindex, 1).toString();
        String unitPrice = tblProductView.getModel().getValueAt(rowindex, 2).toString();
        String quantity = tblProductView.getModel().getValueAt(rowindex, 3).toString();
        String totalPrice = tblProductView.getModel().getValueAt(rowindex, 4).toString();
        String salesPrice = tblProductView.getModel().getValueAt(rowindex, 5).toString();

        txtProductId.setText(id);
        txtPrductName.setText(name);
        txtPrductUnitPrice.setText(unitPrice);
        txtProductQuantity.setText(quantity);
        txtPrductTotalPrice.setText(totalPrice);
        txtPrductSalesPrice.setText(salesPrice);

    }//GEN-LAST:event_tblProductViewMouseClicked

    private void btnProductDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProductDeleteMouseClicked
        // TODO add your handling code here:
        delectProduct();

    }//GEN-LAST:event_btnProductDeleteMouseClicked

    private void btnProductEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProductEditMouseClicked
        // TODO add your handling code here:
        editProduct();
    }//GEN-LAST:event_btnProductEditMouseClicked

    private void productViewMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_productViewMouseClicked
        // TODO add your handling code here:
        showProductCombo();

    }//GEN-LAST:event_productViewMouseClicked

    private void comProductNameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comProductNameMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_comProductNameMouseClicked

    private void txtSelesQuantityFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSelesQuantityFocusLost
        // TODO add your handling code here:
        getTotalsalesPrice();
        salesValidationQuantity();
    }//GEN-LAST:event_txtSelesQuantityFocusLost

    private void btnSalesSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalesSaveMouseClicked
        // TODO add your handling code here:

        addSales();
    }//GEN-LAST:event_btnSalesSaveMouseClicked

    private void salesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_salesMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_salesMouseClicked

    private void btnReportsPurchaseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReportsPurchaseMouseClicked
        // TODO add your handling code here:
        getPurchaseReports();
    }//GEN-LAST:event_btnReportsPurchaseMouseClicked

    private void btnReportsSalesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReportsSalesMouseClicked
        // TODO add your handling code here:
        getSalesReport();
    }//GEN-LAST:event_btnReportsSalesMouseClicked

    private void btnReportsGrossProfitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReportsGrossProfitMouseClicked
        // TODO add your handling code here:
        getGrossProfit();
    }//GEN-LAST:event_btnReportsGrossProfitMouseClicked

    private void btnProductResetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProductResetMouseClicked
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_btnProductResetMouseClicked

    private void btnReportsResetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReportsResetMouseClicked
        // TODO add your handling code here:
        DefaultTableModel model =new DefaultTableModel();
        tblReports.setModel(model);
        model.setRowCount(0);
    }//GEN-LAST:event_btnReportsResetMouseClicked

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
            java.util.logging.Logger.getLogger(ProductView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProductView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProductView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProductView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ProductView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel add;
    private javax.swing.JPanel add1;
    private javax.swing.JButton btnAddProduct;
    private javax.swing.JButton btnProductAdd;
    private javax.swing.JButton btnProductDelete;
    private javax.swing.JButton btnProductEdit;
    private javax.swing.JButton btnProductReset;
    private javax.swing.JButton btnProductrReport;
    private javax.swing.JButton btnReportsGrossProfit;
    private javax.swing.JButton btnReportsPurchase;
    private javax.swing.JButton btnReportsReset;
    private javax.swing.JButton btnReportsSales;
    private javax.swing.JButton btnSalesProduct;
    private javax.swing.JButton btnSalesSave;
    private javax.swing.JButton btnStockProdct;
    private javax.swing.JComboBox<String> comProductName;
    private com.toedter.calendar.JDateChooser dateFromReports;
    private com.toedter.calendar.JDateChooser dateToReports;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblProfit;
    private javax.swing.JLabel lblStock;
    private javax.swing.JTabbedPane productView;
    private javax.swing.JPanel report;
    private javax.swing.JPanel sales;
    private com.toedter.calendar.JDateChooser salesDate;
    private javax.swing.JPanel stock;
    private javax.swing.JTable tblProductView;
    private javax.swing.JTable tblReports;
    private javax.swing.JTable tblStock;
    private javax.swing.JTextField txtPrductName;
    private javax.swing.JTextField txtPrductSalesPrice;
    private javax.swing.JTextField txtPrductTotalPrice;
    private javax.swing.JTextField txtPrductUnitPrice;
    private javax.swing.JTextField txtProductId;
    private javax.swing.JTextField txtProductQuantity;
    private javax.swing.JTextField txtSalesTotalPrice;
    private javax.swing.JTextField txtSalesUnitePrice;
    private javax.swing.JTextField txtSelesQuantity;
    // End of variables declaration//GEN-END:variables

}
