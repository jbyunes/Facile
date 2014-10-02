package fr.upd;
import java.awt.geom.*;

/**
 * Attention cette classe est encore expérimentale. Des modifications sont
 * à prévoir, mais les usages standards devraient être respectés.
 *
 * @author JBY
 * @version 0.1, 27/03/2013
 */
public abstract class Turtle {
  private TurtleState state;
  Turtle(double x,double y,double angle) {
    state = new TurtleState(x,y,angle);
  }
  Turtle() {
    this(0.0,0.0,0.0);
  }
  public void setState(TurtleState s) {
    state = (TurtleState) s.clone();
  }
  public TurtleState getState() {
    return (TurtleState) state.clone();
  }
  public void setLocation(double x,double y) {
    state.setLocation(x,y);
  }
  public void setLocation(Point2D p) {
    state.setLocation(p.getX(),p.getY());
  }
  public void setAngle(double angle) {
    state.setAngle(angle);
  }
  public void lineTo(Point2D p) {
    lineTo(state.getX(),state.getY());
}
  public abstract void lineTo(double x,double y);
  public void jumpTo(Point2D p) {
    jumpTo(state.getX(),state.getY());
  }
  public void jumpTo(double x,double y) {
    state.setLocation(x,y);
  }
  public void jumpTo(double distance) {
    jumpTo(state.getX()+distance*Math.cos(state.getAngle()),
           state.getY()+distance*Math.sin(state.getAngle()));
  }
  public abstract void lineTo(double distance);
  public void rotate(double angle) {
    state.setAngle(state.getAngle()+angle);
  }
  public abstract void rectangle(int size);
}
