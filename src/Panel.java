
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Panel.java
 *
 * Created on Aug 15, 2014, 12:22:20 PM
 */
/**
 *
 * @author Dany
 */
public class Panel extends javax.swing.JFrame {
    private final static int GRID_SIZE = 4;
    private Box[][] grid;
    private ArrayList<Box> empty;
    private Random random;
    
    public Panel() {
        grid = new Box[GRID_SIZE][GRID_SIZE];
        empty =  new ArrayList<Box>(GRID_SIZE * GRID_SIZE);
        initComponents();
        
        JPanel pane = (JPanel) getContentPane();
        
        pane.setBorder(new EmptyBorder(10, 10, 10, 10));
        pane.setBackground(new Color(187, 173, 160));
        
        GridLayout l = (GridLayout) pane.getLayout();
        l.setHgap(10);
        l.setVgap(10);
        setSize(600, 600);
        
        for(int i=0; i<4; ++i){
            for(int j=0; j<4; ++j){
                Box b = new Box();
                grid[i][j] = b;
                empty.add(b);
                add(b);
            }
        }
        
        random = new Random(System.nanoTime());
        generate();
        generate();
        
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                int key = ke.getKeyCode();
                
                if(key == KeyEvent.VK_RIGHT){
                    moveRight();
                }
                else if(key == KeyEvent.VK_LEFT){
                    moveLeft();
                }
                else if(key == KeyEvent.VK_UP){
                    moveUp();
                }
                else if(key == KeyEvent.VK_DOWN){
                    moveDown();
                }
            }
        });
        
        setLocationRelativeTo(null);
    }

    private void generate(){
        Box b = empty.remove(random.nextInt(empty.size()));
        b.setValue(2);
    }
    
    private boolean canMove(){
        if(empty.size() > 0)
            return true;
        
        for(int i=0; i<GRID_SIZE - 1; ++i){
            for(int j=0; j<GRID_SIZE - 1; ++j){
                if(grid[i][j].getValue() == grid[i+1][j].getValue() ||
                   grid[i][j].getValue() == grid[i][j+1].getValue()){
                    return true;
                }
            }
        }
        
        // Last row
        int i = GRID_SIZE - 1;
        for(int j=0; j<GRID_SIZE - 1; ++j){
            if(grid[i][j].getValue() == grid[i][j+1].getValue()){
                return true;
            }
        }
        
        // Last column
        i = GRID_SIZE - 1;
        for(int j=0; j<GRID_SIZE - 1; ++j){
            if(grid[j][i].getValue() == grid[j+1][i].getValue()){
                return true;
            }
        }
        
        return false;
    }
    
    private void update(boolean moved){
        if(!moved)
            return;
        
        empty.clear();
        
        for(Box[] row : grid){
            for(Box b : row){
                if(b.getValue() == 0)
                    empty.add(b);
            }
        }
        
        generate();
            
        if(!canMove())
            gameOver();
    }
    
    private void moveUp(){
        int u, v;
        boolean moved = false;
        
        for(int i=1; i<GRID_SIZE; ++i){
            for(int j=0; j<GRID_SIZE; ++j){
                u = grid[i-1][j].getValue();
                v = grid[i][j].getValue();
                
                if(u == 0){
                    grid[i-1][j].setValue(v);
                    grid[i][j].setValue(0);
                    moved = true;
                }
                else if(v == u){
                    grid[i-1][j].setValue(v * 2);
                    grid[i][j].setValue(0);
                    moved = true;
                }
            }
        }
        
        update(moved);
    }
    
    private void moveDown(){
        int u, v;
        boolean moved = false;
        
        for(int i=GRID_SIZE-1; i>0; --i){
            for(int j=0; j<GRID_SIZE; ++j){
                u = grid[i][j].getValue();
                v = grid[i-1][j].getValue();
                
                if(u == 0){
                    grid[i][j].setValue(v);
                    grid[i-1][j].setValue(0);
                    moved = true;
                }
                else if(v == u){
                    grid[i][j].setValue(v * 2);
                    grid[i-1][j].setValue(0);
                    moved = true;
                }
            }
        }
        
        update(moved);
    }
    
    private void moveLeft(){

    }
    
    private void moveRight(){

    }
    
    private void gameOver(){
        
        dispose();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new java.awt.GridLayout(4, 4));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new Panel().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
