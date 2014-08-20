
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    public Panel() throws InvalidObjectException {
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
                
                try {
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
                } catch (InvalidObjectException ex) {
                    Logger.getLogger(Panel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        setLocationRelativeTo(null);
    }

    private void generate() throws InvalidObjectException{
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
    
    private void update(boolean moved) throws InvalidObjectException{
        if(!moved)
            return;
        
        empty.clear();
        
        for(Box[] row : grid){
            for(Box b : row){
                if(b.getValue() == 0)
                    empty.add(b);
            }
        }
        
        Box.clearMarks();
        
        generate();
            
        if(!canMove())
            gameOver();
    }
    
    private void moveUp() throws InvalidObjectException{
        int u, v;
        boolean moved = false;
        
        for(int j=0; j<GRID_SIZE; ++j){
            for(int i=1; i<GRID_SIZE; ++i){
                for(int p=i; p>0; --p){
                    u = grid[p-1][j].getValue();
                    v = grid[p][j].getValue();

                    if(v == 0)
                        break;

                    // Keep pushing up until merge or no match
                    if(u == 0){
                        grid[p-1][j].setValue(v);
                        grid[p][j].setValue(0);
                        moved = true;
                    }
                    else if(u == v && !grid[p-1][j].isMarked()){
                        grid[p-1][j].setValue(u + u);
                        grid[p-1][j].setMark();
                        grid[p][j].setValue(0);
                        moved = true;                        
                        break;
                    }
                    else {
                        break;
                    }
                }
            }
        }
        
        update(moved);
    }
    
    private void moveDown() throws InvalidObjectException{
        int u, v;
        boolean moved = false;
        
        for(int j=0; j<GRID_SIZE; ++j){
            for(int i=GRID_SIZE-1; i>=0; --i){
                for(int p=i; p<GRID_SIZE-1; ++p){
                    u = grid[p+1][j].getValue();
                    v = grid[p][j].getValue();

                    if(v == 0)
                        break;

                    // Keep pushing up until merge or no match
                    if(u == 0){
                        grid[p+1][j].setValue(v);
                        grid[p][j].setValue(0);
                        moved = true;
                    }
                    else if(u == v && !grid[p+1][j].isMarked()){
                        grid[p+1][j].setValue(u + u);
                        grid[p+1][j].setMark();
                        grid[p][j].setValue(0);
                        moved = true;                        
                        break;
                    }
                    else {
                        break;
                    }
                }
            }
        }
        
        update(moved);
    }
    
    private void moveLeft() throws InvalidObjectException{
        int u, v;
        boolean moved = false;
        
        for(int i=0; i<GRID_SIZE; ++i){
            for(int j=1; j<GRID_SIZE; ++j){
                for(int p=j; p>0; --p){
                    u = grid[i][p-1].getValue();
                    v = grid[i][p].getValue();

                    if(v == 0)
                        break;

                    // Keep pushing up until merge or no match
                    if(u == 0){
                        grid[i][p-1].setValue(v);
                        grid[i][p].setValue(0);
                        moved = true;
                    }
                    else if(u == v && !grid[i][p-1].isMarked()){
                        grid[i][p-1].setValue(u + u);
                        grid[i][p-1].setMark();
                        grid[i][p].setValue(0);
                        moved = true;                        
                        break;
                    }
                    else {
                        break;
                    }
                }
            }
        }
        
        update(moved);
    }
    
    private void moveRight() throws InvalidObjectException{
        int u, v;
        boolean moved = false;
        
        for(int i=0; i<GRID_SIZE; ++i){
            for(int j=GRID_SIZE-1; j>=0; --j){
                for(int p=j; p<GRID_SIZE-1; ++p){
                    u = grid[i][p+1].getValue();
                    v = grid[i][p].getValue();

                    if(v == 0)
                        break;

                    // Keep pushing up until merge or no match
                    if(u == 0){
                        grid[i][p+1].setValue(v);
                        grid[i][p].setValue(0);
                        moved = true;
                    }
                    else if(u == v && !grid[i][p+1].isMarked()){
                        grid[i][p+1].setValue(u + u);
                        grid[i][p+1].setMark();
                        grid[i][p].setValue(0);
                        moved = true;                        
                        break;
                    }
                    else {
                        break;
                    }
                }
            }
        }
        
        update(moved);
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
                try {
                    new Panel().setVisible(true);
                } catch (InvalidObjectException ex) {
                    Logger.getLogger(Panel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
