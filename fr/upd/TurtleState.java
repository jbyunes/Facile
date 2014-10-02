package fr.upd;
import java.awt.geom.*;

/**
 * Attention : cette classe est expérimentale. Vous ne devez en aucun cas
 * tenter d'accéder à quoi que ce soit sous peine de perdre toute
 * compatibilité future. En théorie cette classe ne devrait rien exposer
 * à la vue de ses utilisateurs.
 *
 * @author JBY
 * @version 0.1, 27/03/2013
 */
public class TurtleState extends Point2D implements Cloneable {
  private double x;
  private double y;
  private double angle;
  public TurtleState(double x,double y,double a) {
    setLocation(x,y);
    setAngle(a);
  }
  public Object clone() {
    return super.clone();
  }
  public double getX() {
    return x;
  }
  public double getY() {
    return y;
  }
  public double getAngle() {
    return angle;
  }
  public void setAngle(double angle) {
    this.angle = angle;
  }
  public void setLocation(double x,double y) {
    this.x = x;
    this.y = y;
  }
  public String toString() {
    return "TurtleState[x="+x+",y="+y+",angle="+angle+"]";
  }
}
