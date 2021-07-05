/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.siguasystem.facturacionjava;

import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author juan
 */
public class Tablabuscaprofac extends AbstractTableModel implements TableModelListener {

    private String[] columnNames = {"#","Cod. Producto", "Descripcion", "Precio Venta","Categoria","Impuesto"};
    private Object[][] data = {};

    public DefaultTableModel crearTabla() {
        DefaultTableModel df = new DefaultTableModel(columnNames, 0) {
            public boolean isCellEditable(int row, int col) {
                 return false;
            }
        };
      
        return df;
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        int column = e.getColumn();
        TableModel model = (TableModel) e.getSource();
        String columnName = model.getColumnName(column);
        Object data = model.getValueAt(row, column);
        //JOptionPane.showMessageDialog(null, data.toString());
    }

    @Override
    public int getRowCount() {
        return this.data.length;
    }

    @Override
    public int getColumnCount() {
        return this.columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    public double getSuma(int col) {
        double tot = 0;
        for (int i = 0; i < this.data.length; i++) {
            tot = tot + Double.parseDouble(data[i][col].toString());
        }
        return tot;
    }

    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }
}


