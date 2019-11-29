/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmtdswing;


import com.sun.scenario.effect.Color4f;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Shape;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/**
 *
 * @author fodil
 */
public class mainPanel extends javax.swing.JFrame {
    Point p,pS,pD;
    Point pClicked1,pClicked2;
    static String myWeight;
    class Edge{
        int source;
        int destination;
        float weight;
    }
    class boucle{
        int source;
        int destination;
        float weight;
    }
    LinkedList<Edge> preMatrice = new LinkedList<>();
    LinkedList<boucle> boucleArray = new LinkedList<>();
    
    Image img;
    
    
    Graphics2D gfx;
    int nodeSize=50;
    int xc=20,yc=50;
    HashMap<Integer,Point> location;
    HashMap<Integer, HashSet> nodes;
    int id=0;
    int select=-1;
    public mainPanel() {
        System.out.println("main construct");
        initComponents();
        location=new HashMap<>();
        nodes=new HashMap<>();
        img=panel.createImage(panel.getWidth(),panel.getHeight());
        gfx=(Graphics2D)img.getGraphics() ;
    }
    private int selecteNode(int xcoordinate,int ycoordinate){
        for(int i=0;i<location.size();i++){
            Point thePoint=(Point)location.values().toArray()[i];
            int deltaX=xcoordinate-(thePoint.x-nodeSize/2);
            int deltaY=ycoordinate-(thePoint.y-nodeSize/2);
            if(Math.sqrt(deltaX*deltaX+deltaY*deltaY)<=(nodeSize+30)){
                return (int)location.keySet().toArray()[i];
            }
        }
        return -1;
    }
    public void draw(){

        for(int i=0;i<location.size();i++){
            Point p=(Point) location.values().toArray()[i];
            gfx.setColor(Color.RED);
            gfx.drawOval(p.x-nodeSize/2,p.y-nodeSize/2,nodeSize,nodeSize);
            gfx.setColor(Color.BLACK);
            gfx.drawString("E"+(i+1), p.x, p.y);
            
        }
        for(int i=0;i<nodes.size();i++){
            Point pS=(Point)location.values().toArray()[i];
            for(Integer dest:(HashSet<Integer>)nodes.values().toArray()[i]){
                Point pD=(Point) location.get(dest);
                 gfx.setColor(Color.BLUE);
                 if(pS.x ==pClicked1.x && pS.y == pClicked1.y
                         && pD.x ==pClicked2.x && pD.y == pClicked2.y){
                    drawArrowLine(gfx, pS.x, pS.y, pD.x, pD.y, 20, 10);
                 //drawArrowLine(gfx, 15, 20, 25, 10, 20, 10);
              //   gfx.drawString(myWeight, (pS.x+pD.x)/4, (pS.y+pD.y)/2);
                 }
                 
            }
        }
        

        panel.getGraphics().drawImage(img,0, 0, this); 
       // panel.getGraphics().setColor(Color.white);

        
    }
    public void drawEdge(Point pS,Point pD){
        drawArrowLine(gfx, pS.x, pS.y, pD.x, pD.y, 20, 10);
        panel.getGraphics().drawImage(img, 0, 0, this); 
    }
    
    public void drawArrowLine(Graphics g, int x1, int y1, int x2, int y2, int d, int h) {
        int dx = x2 - x1, dy = y2 - y1;
        double D = Math.sqrt(dx*dx + dy*dy);
        double xm = D - d, xn = xm, ym = h, yn = -h, x;
        double sin = dy / D, cos = dx / D;

        x = xm*cos - ym*sin + x1;
        ym = xm*sin + ym*cos + y1;
        xm = x;

        x = xn*cos - yn*sin + x1;
        yn = xn*sin + yn*cos + y1;
        xn = x;

        int[] xpoints = {x2, (int) xm, (int) xn};
        int[] ypoints = {y2, (int) ym, (int) yn};

        g.drawLine(x1, y1, x2, y2);
        
        g.fillPolygon(xpoints, ypoints, 3);
       // System.out.println("myweight is :"+myWeight);
        
        if(myWeight!=null){
            Point pS=new Point(x1,y1);
            Point pD=new Point(x2,y2);
            /*if (pS.x==pD.x && pS.y==pD.y)
               gfx.drawString(myWeight, ((pS.x+pD.x)/2) ,((pS.y+pD.y)/2-40)); 
            else*/
            gfx.drawString(myWeight, ((pS.x+pD.x)/2) ,((pS.y+pD.y)/2));
            
            // gfx.drawString(myWeight, 20 ,10);
           
            int source = -1, destination = -1;
            float distMinSrc = 1000000, distMinDest = 1000000;
            for(Object o : location.keySet()){
                Point pt = location.get(o);
                float distSrc = (float) Math.sqrt(Math.pow(pt.x - pS.x, 2) + Math.pow(pt.y - pS.y, 2));
                float distDest = (float) Math.sqrt(Math.pow(pt.x - pD.x, 2) + Math.pow(pt.y - pD.y, 2));
         //       System.out.println(" la dist a dest: " + distDest);
           //     System.out.println("la dist a source: " + distSrc);
                if( distSrc < distMinSrc ){
                    source = (Integer)o;
                    distMinSrc = distSrc;
                }
                if( distDest < distMinDest ){
                    destination = (Integer)o;
                    distMinDest = distDest;
                }
            }
            //System.out.println("source: " + source);
            //System.out.println("dest : " + destination);
            Edge e = new Edge();
            e.source = source;
            e.destination = destination;
            e.weight = Float.valueOf(myWeight);
            preMatrice.add(e);
        }
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g); //To change body of generated methods, choose Tools | Templates.
        //draw();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new javax.swing.JPanel();
        Terminer = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 255, 255));

        panel.setBackground(new java.awt.Color(204, 204, 255));
        panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panel.setForeground(new java.awt.Color(255, 255, 255));
        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panelMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                panelMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 627, Short.MAX_VALUE)
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 425, Short.MAX_VALUE)
        );

        Terminer.setText("CMTD incluse");
        Terminer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TerminerMouseClicked(evt);
            }
        });

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator1.setForeground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(620, 620, 620)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addComponent(Terminer, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(169, 169, 169)
                        .addComponent(Terminer))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 5, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void panelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelMousePressed
       
        select=selecteNode(evt.getX(), evt.getY());
        pClicked1 =new Point(evt.getX(), evt.getY());
        if(select==-1){
           location.put(id,new Point(evt.getX(),evt.getY()));
           nodes.put(id, new HashSet());
            id++;
            draw(); 
          
        }
               
    }//GEN-LAST:event_panelMousePressed

    private void panelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelMouseReleased
        int destination=selecteNode(evt.getX(), evt.getY());
        pClicked2 =new Point(evt.getX(), evt.getY());
        if(destination!=select && destination>-1 && select>-1 ){
           // nodes.get(destination).add(select);
            nodes.get(select).add(destination);
            demanderLePoidsDeLarc();
        }
        draw();
        
    }//GEN-LAST:event_panelMouseReleased

    private void TerminerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TerminerMouseClicked
        // TODO add your handling code here:
        int size = location.size();
        double[][] matrice = new double[size][size];
        for (double[] tab : matrice){
            Arrays.fill(tab, 0);
        }

        for(Edge e : preMatrice){
            matrice[e.source][e.destination] = e.weight;
        }

        System.out.println(" CMTC :");
        for (double[] tab : matrice){
            for (double weight : tab){
                System.out.print(weight);
                System.out.print(" ");
            }
            System.out.println();
        }
        
        //Aller vers la CMTD incluse
        System.out.println(" CMTD incluse :");
        int n=location.size();
        double[][] CMTDincluse=new double[n][n];
        for(int i=0;i<n;i++)
            for(int j=0;j<n;j++){
                double somme=sommeLigne(matrice[i], n);
                if(somme!=0)
                    CMTDincluse[i][j]=matrice[i][j]/somme;
                else
                    CMTDincluse[i][j]=0;
            }
                
        
       
        MatriceStoch m=new MatriceStoch(CMTDincluse,n);
       
        m.afficherMatrice();
        m.saisirP0();
        boolean ire=m.irréductible();
        if(ire)System.out.println("c'est irreductible");
        else System.out.println("c'est non irreductible");
        
        boolean ap= m.aperiodic();
        if(ap)System.out.println("c'est aperiodique");
        else System.out.println("c'est periodique");
        if(ap&&ire)  
         m.afficherP(m.RégimeStationaire());
        else
            System.err.println("Régime stationnaire n'existe pas");
        
    }//GEN-LAST:event_TerminerMouseClicked

    private void panelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount()==2 && !evt.isConsumed()){
            evt.consume();
            demanderLePoidsDeLarc();
            
           
            
            
            
        }
    }//GEN-LAST:event_panelMouseClicked
    private void demanderLePoidsDeLarc(){
        poids panelsFrame=new poids(this);
        panelsFrame.setVisible(true);    
      //  System.out.println(panelsFrame.poidsDeLarc);
    }
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
            java.util.logging.Logger.getLogger(mainPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(mainPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(mainPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(mainPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new mainPanel().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Terminer;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel panel;
    // End of variables declaration//GEN-END:variables

    private Color Color(int i, int i0, int i1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private double sommeLigne(double[] t,int n){
        double s=0;
        for(int i=0;i<n;i++)
            s=s+t[i];
        return s;
    }
}
