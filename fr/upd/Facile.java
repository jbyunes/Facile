package fr.upd;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.awt.font.*;

/**
 * La classe <code>Facile</code> (ex-classe <code>Deug</code>)
 * permet de réaliser:
 * <ul>
 * <li>des entrées/sorties (lecture/écriture clavier/écran ou sur fichier),
 * <li>l'exécution de commandes <em>systèmes</em>
 * <li>des dessins, en mode absolu ou en mode relatif (tortue de logo), le
 * mode tortue est fonctionnel mais encore expérimental
 * <li>quelques manipulations élémentaires de chaînes de caractères.
 * </ul>
 * L'idée conductrice est de masquer certaines constructions de Java que
 * nous avons considérées comme trop avancées pour une initiation à la
 * programmation.
 * De plus cela permet d'obtenir une certaine uniformité des incantations Java.
 *
 * <H3>Entrées/Sorties</H3>
 * <P>Les entrées ne prennent leur source qu'au clavier et les sorties
 * ne se réalisent qu'à l'écran du terminal.
 * <P> Les fonctions
 * <CODE>print(<I>type</I>)</CODE> et
 * <CODE>println(<I>type</I>)</CODE> permettant d'afficher tout <I>type</I> de
 * données (la seconde série produit en plus un retour à la ligne).
 * <P>Les entrées se font par l'intermédiaire des fonctions
 * <CODE>read<I>Type</I>()</CODE> pour tout type de données primitives Java.
 * Chacune de ces fonctions positionne, en cas d'erreur, un état que l'on peut
 * interroger par les fonctions <CODE>isOk()</CODE> et
 * <CODE>getStatus()</CODE>.
 * <H4>Les fichiers...</H4>
 * <P>On trouvera dans ce qui suit, un exemple d'utilisation des fonctions de lecture et d'écriture :
 * <PRE>
 *	if (Facile.ouvrirFichierEnEcriture("toto.txt")==Facile.NO_ERROR) {
 *	    Facile.ecrireUneLigneDansLeFichier("voici une première ligne");
 *	    Facile.ecrireUneLigneDansLeFichier("voici une seconde ligne");
 *	    Facile.ecrireUneLigneDansLeFichier("et la dernière");
 *	    Facile.fermerFichierEnEcriture();
 *	}
 *	if (Facile.ouvrirFichierEnLecture("toto.txt")==Facile.NO_ERROR) {
 *	    String s;
 *	    while ((s=Facile.lireUneLigneDuFichier())!=null) System.out.println(s);
 *	    Facile.fermerFichierEnLecture();
 *	}
 * </PRE>
 *
 * <H3>Exécution externe</H3>
 * <P>Une interface permet d'exécuter des commandes systèmes et de lire les
 * résultats produits sur l'écran du terminal. La fonction 
 *<CODE>exec(String <I>commande</I>)</CODE> permet de lancer l'exécution de 
 * la commande et de ses arguments ne formant qu'une seule chaîne de caractère.
 * La fonction <CODE>exec(String []<I>args</I>)</CODE> permet de lancer une 
 * commande spécifiée par la <EM>liste de ses argumets</EM>, donc le premier
 * étant la commande elle-même, puis le second le premier argument, etc.
 * L'exemple suivant illustre une utilisation possible de la fonction :
 * <PRE>
 * Facile.exec("ls -ail /");
 * </PRE>
 * ou
 * <PRE>
 * String [] c = new String[3];
 * c[0] = new String("ls");
 * c[1] = new String("-ail");
 * c[2] = new String("/");
 * Facile.exec(c);
 * </PRE>
 *
 * <H3>Dessins</H3>
 *
 * <P>Un exemple de programme graphique typique est le suivant:
 * <PRE>
 * Facile.startDrawings(200,200);
 * for (i=0; i<200; i+=4) {
 *   Facile.setGray(i);
 *   Facile.drawLine(0,i,199,199-i);
 *   Facile.drawLine(i,0,199-i,199);
 * }
 * Facile.stopDrawings();
 * </PRE>
 * Pour obtenir:<BR>
 * <IMG SRC="Dessin.jpg">
 * <H3>Chaînes</H3>
 * On trouvera les fonctions <I>type</I>ToString() et stringTo<I>type</I>()
 * où (<I>type</I> peut être:
 * <CODE>boolean</CODE>, <CODE>int</CODE>, <CODE>long</CODE>,
 * <CODE>float</CODE> ou <CODE>double</CODE>) qui réalisent les conversions
 * attendues.
 * <P>
 * On trouvera aussi les fonctions de comparaison: <CODE>equals()</CODE>,
 * <CODE>compare()</CODE>,
 * <CODE>subString()</CODE>,
 * <CODE>charAt()</CODE> et
 * <CODE>length()</CODE>.
 *
 * @author Jean-Baptiste.Yunes@univ-paris-diderot.fr
 * @author (contributor) Fabien Tarissan
 * @author (contributor) Daniele.Varacca@pps.univ-paris-diderot.fr
 * @version 0.3, 20/03/2013
 */
public final class Facile {
  /**
   * A private internally useful class to support drawings...
   */
  private static class Drawable extends JPanel {
    private int width, height; // size of drawing area
    private Image backingStore;
    private Graphics2D backingGraphics;
    private MyTurtle turtle;
    private boolean turtleMode; // turtle or coordinates mode ?
    public Drawable(int width,int height) {
      turtle = new Facile.MyTurtle(this);
      turtleMode = false;
      setSize(width,height);
      backingStore = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
      backingGraphics = (Graphics2D)backingStore.getGraphics();
    }
    public Turtle getTurtle() {
      return turtle;
    }
    public boolean isTurtleModeOn() {
      return turtleMode;
    }
    public void turtleOn() {
      setTurtle(true);
    }
    public void turtleOff() {
      set Turtle(false);
    }
    public void setTurtleMode(boolean b) {
      turtleMode = b;
      repaint();
    }
    public void setSize(int width,int height) {
      super.setSize(width,height);
      this.width = width;
      this.height = height;
    }
    public Dimension getPreferredSize() {
      return new Dimension(width,height);
    }
    public void paintComponent(Graphics g) {
      g.clearRect(0,0,width,height);
      g.drawImage(backingStore,0,0,this);
      if (isTurtleModeOn()) {
        g.setColor(Color.RED);
        g.fillOval((int)(turtle.getState().getX())-3,
                   (int)(turtle.getState().getY())-3,6,6);
      }
    }
    public void drawLine(int x0,int y0,int x1, int y1) {
      if (backingGraphics==null) return;
      backingGraphics.drawLine(x0,y0,x1,y1);
      repaint();
    }
    
    public void drawRect(int x, int y, int l, int h) {
      if (backingGraphics==null) return;
      backingGraphics.drawRect(x,y,l,h);
      repaint();
    }

    public void fillRect(int x, int y, int l, int h) {
      if (backingGraphics==null) return;
      backingGraphics.fillRect(x,y,l,h);
      repaint();
    }

    public void fillPolygon(int []x, int []y, int n) {
      if (backingGraphics==null) return;
      backingGraphics.fillPolygon(x,y,n);
      repaint();
    }

    public void drawPolygon(int []x, int []y, int n) {
      if (backingGraphics==null) return;
      backingGraphics.drawPolygon(x,y,n);
      repaint();
    }

    public void drawOval(int x, int y, int l, int h) {
      if (backingGraphics==null) return;
      backingGraphics.drawOval(x,y,l,h);
      repaint();
    }

    public void fillOval(int x, int y, int l, int h) {
      if (backingGraphics==null) return;
      backingGraphics.fillOval(x,y,l,h);
      repaint();
    }

    public void drawString(int x,int y,String s) {
      FontMetrics fm = backingGraphics.getFontMetrics();
      float h = fm.getHeight();
      //      float w = fm.stringWidth(s);
      float descent = fm.getDescent();
      backingGraphics.drawString(s,(int)(x+4),(int)(y+(h/2)-(descent/2)));
      repaint();
    }

    public void drawArc(int x, int y, int l, int h, int angleInit, int angleCouvert) {
      if (backingGraphics==null) return;
      backingGraphics.drawArc(x,y,l,h, angleInit, angleCouvert);
      repaint();
    }

    public void fillArc(int x, int y, int l, int h, int angleInit, int angleCouvert) {
      if (backingGraphics==null) return;
      backingGraphics.fillArc(x,y,l,h, angleInit, angleCouvert);
      repaint();
    }

    public void setColor(int c1,int c2,int c3) {
      if (backingGraphics==null) return;
      backingGraphics.setColor(new Color(c1,c2,c3));
    }
    public void clearArea() {
      if (backingGraphics==null) return;
      backingGraphics.clearRect(0,0,width,height);
      repaint();
    }

  } // end class Drawable

  /**
   * Rien à signaler en ce qui concerne la dernière opération
   * d'entrée/sortie.
   */
  public static final int NO_ERROR=0;
  /**
   * La lecture sur le flot d'entrée s'est révélée impossible.
   */
  public static final int STREAM_ERROR=1;
  /**
   * La conversion de type sur la chaîne de caractère lue sur l'entrée
   * n'a pas été possible.
   */
  public static final int FORMAT_ERROR=2;
  /**
   * L'argument passé est invalide (ex: <CODE>==null</CODE>)
   **/
  public static final int ARG_ERROR=3;
  /**
   * Le fichier n'a pu être ouvert.
   */
  public static final int OPEN_ERROR=4;
  /**
   * Une erreur a été rencontrée lors d'une écriture dans un fichier.
   */
  public static final int WRITE_ERROR=4;
  /**
   * Pas d'instanciation de la classe <code>Facile</code>
   */
  private Facile() { }
  /**
   * Référence sur le flot de découpage en lexèmes.
   */
  private static MyTokenizer theTokenizer =  new MyTokenizer(new InputStreamReader(System.in));
  /**
   * Code d'erreur interne.
   */
  private static int error;
  /**
   * Renvoie l'état de la dernière opération d'entrée/sortie.
   * <DL>
   * <DT><CODE>true</CODE><DD>si tout s'est bien passé;
   * <DT><CODE>false</CODE><DD>si une erreur s'est produite. En ce cas, il
   * peut être utile de consulter la cause de l'erreur
   * (<code>getStatus()</code>).
   * </DL>
   * Un appel est équivalent à <CODE>getStatus()==NO_ERROR</CODE>.
   * @return l'état logique de la dernière opération.
   */
  public static boolean isOk() {
    return getStatus()==NO_ERROR;
  }
  /**
   * Renvoie l'erreur rencontrée lors de la dernière opération
   * d'entrée/sortie.
   * @return la dernière erreur recontrée.
   */
  public static int getStatus() {
    return error;
  }

  /**
   * Lit sur l'entrée un caractère et renvoie sa valeur.
   *
   * <P>Les octets lus l'ont été sur l'entrée standard.
   *
   * @return le caractère lu sur l'entrée. Si le caractère renvoyé est
   * le caractère <code>&lt;nul&gt;</code> il est alors nécessaire de
   * consulter l'état de l'opération (<code>isOk()</code>).
   */
  public static char readChar() {
    int c;
    try {
      c = theTokenizer.read();
      if (c!=MyTokenizer.EOF) {
        error = NO_ERROR;
        return (char)c;
      } else {
        error = STREAM_ERROR;
        return '\u0000';
      }
    } catch(java.io.IOException ioe) {
      error = STREAM_ERROR;
      return '\u0000';
    }
  }

  /**
   * Termine toute exécution de la machine.
   */
  public static void exit() {
    System.exit(0);
  }

  /**
   * Lit l'ensemble des caractères jusqu'à la prochaine fin de ligne.
   *
   * <P>Les caractères lus le sont par l'intermédiaire de la méthode
   * <CODE>readChar()</CODE>
   * @return la ligne suivante disponible sur l'entrée.
   */
  public static String readLine() {
    StringBuffer theString=new StringBuffer();
    char c;
    while ( ((c=readChar())!='\n') && isOk() ) {
      theString.append(c);
    }
    if (!isOk()) return null;
    return theString.toString();
  }

  /**
   * Convertit une chaîne de caractères en <CODE>boolean</CODE>.
   * @param s la chaîne
   * @return la valeur obtenue après conversion (<CODE>boolean</CODE>) <DL>
   * <DT><CODE>true</CODE><DD>si <CODE>s</CODE> est égale à
   * <CODE>"true"</CODE>
   * <DT><CODE>false</CODE><DD>sinon
   * </DL>
   **/
  public static boolean stringToBoolean(String s) {
    error = NO_ERROR;
    if (s==null) {
      error = ARG_ERROR;
      return false;
    }
    try {
      return Boolean.valueOf(s).booleanValue();
    } catch(NumberFormatException nfe) {
      error = FORMAT_ERROR;
      return false;
    }
  }
  /**
   * Convertit une chaîne de caractères en <CODE>int</CODE>.
   * <P>
   * En cas d'erreur de conversion l'état est positionné en conséquence.
   * @param s la chaîne à convertir
   * @return la valeur obtenue après conversion (<CODE>int</CODE>)
   * <B>Attention</B>: si la valeur renvoyée est <CODE>0</CODE>, il est
   * nécessaire de consulter le statut de l'opération...
   **/
  public static int stringToInt(String s) {
    error = NO_ERROR;
    if (s==null) {
      error = ARG_ERROR;
      return (int)0;
    }
    try {
      return Integer.parseInt(s);
    } catch(NumberFormatException nfe) {
      error = FORMAT_ERROR;
      return (int)0;
    }
  }
  /**
   * Convertit une chaîne de caractères en <CODE>byte</CODE>.
   * <P>
   * En cas d'erreur de conversion l'état est positionné en conséquence.
   * @param s la chaîne à convertir
   * @return la valeur obtenue après conversion (<CODE>byte</CODE>)
   * <B>Attention</B>: si la valeur renvoyée est <CODE>0</CODE>, il est
   * nécessaire de consulter le statut de l'opération...
   **/
  public static byte stringToByte(String s) {
    error = NO_ERROR;
    if (s==null) {
      error = ARG_ERROR;
      return (byte)0;
    }
    try {
      return Byte.parseByte(s);
    } catch(NumberFormatException nfe) {
      error = FORMAT_ERROR;
      return (byte)0;
    }
  }
  /**
   * Convertit une chaîne de caractères en <CODE>double</CODE>.
   * <P>
   * En cas d'erreur de conversion l'état est positionné en conséquence.
   * @param s la chaîne à convertir
   * @return la valeur obtenue après conversion (<CODE>double</CODE>)
   * <B>Attention</B>: si la valeur renvoyée est <CODE>0</CODE>, il est
   * nécessaire de consulter le statut de l'opération...
   **/
  public static double stringToDouble(String s) {
    error = NO_ERROR;
    if (s==null) {
      error = ARG_ERROR;
      return (double)0;
    }
    try {
      return Double.parseDouble(s);
    } catch(NumberFormatException nfe) {
      error = FORMAT_ERROR;
      return (double)0;
    }
  }
  /**
   * Convertit une chaîne de caractères en <CODE>float</CODE>.
   * <P>
   * En cas d'erreur de conversion l'état est positionné en conséquence.
   * @param s la chaîne à convertir
   * @return la valeur obtenue après conversion (<CODE>float</CODE>)
   * <B>Attention</B>: si la valeur renvoyée est <CODE>0</CODE>, il est
   * nécessaire de consulter le statut de l'opération...
   **/
  public static float stringToFloat(String s) {
    error = NO_ERROR;
    if (s==null) {
      error = ARG_ERROR;
      return (float)0;
    }
    try {
      return Float.parseFloat(s);
    } catch(NumberFormatException nfe) {
      error = FORMAT_ERROR;
      return (float)0;
    }
  }
  /**
   * Convertit une chaîne de caractères en <CODE>long</CODE>.
   * <P>
   * En cas d'erreur de conversion l'état est positionné en conséquence.
   * @param s la chaîne à convertir
   * @return la valeur obtenue après conversion (<CODE>long</CODE>)
   * <B>Attention</B>: si la valeur renvoyée est <CODE>0</CODE>, il est
   * nécessaire de consulter le statut de l'opération...
   **/
  public static long stringToLong(String s) {
    error = NO_ERROR;
    if (s==null) {
      error = ARG_ERROR;
      return (long)0;
    }
    try {
      return Long.parseLong(s);
    } catch(NumberFormatException nfe) {
      error = FORMAT_ERROR;
      return (long)0;
    }
  }
  /**
   * Lit un lexème sur l'entrée et le convertit en <CODE>int</CODE>.
   * <p>
   * En cas d'erreur (fin du flot ou problème de conversion) l'état est
   * positionné en conséquence.
   * @return le lexème suivant en tant qu'entier (<CODE>int</CODE>)
   */
  public static int readInt() {
    int tokenType;
    tokenType = theTokenizer.nextToken();
    if (tokenType==MyTokenizer.EOF) {
      error = STREAM_ERROR;
      return 0;
    }
    return stringToInt(theTokenizer.currentToken());
  }
  /**
   * Lit un lexème sur l'entrée et le convertit en <CODE>long</CODE>.
   * <p>
   * En cas d'erreur (fin du flot ou problème de conversion) l'état est
   * positionné en conséquence.
   * @return le lexème suivant en tant qu'entier long (<CODE>long</CODE>)
   */
  public static long readLong() {
    int tokenType;
    tokenType = theTokenizer.nextToken();
    if (tokenType==MyTokenizer.EOF) {
      error = STREAM_ERROR;
      return 0;
    }
    return stringToLong(theTokenizer.currentToken());
  }
  /**
   * Lit un lexème sur l'entrée et le convertit en <CODE>float</CODE>.
   * <p>
   * En cas d'erreur (fin du flot ou problème de conversion) l'état est
   * positionné en conséquence.
   * @return le lexème suivant en tant qu'entier (<CODE>float</CODE>)
   */
  public static float readFloat() {
    int tokenType;
    tokenType = theTokenizer.nextToken();
    if (tokenType==MyTokenizer.EOF) {
      error = STREAM_ERROR;
      return 0;
    }
    return stringToFloat(theTokenizer.currentToken());
  }
  /**
   * Lit un lexème sur l'entrée et le convertit en <CODE>double</CODE>.
   * <p>
   * En cas d'erreur (fin du flot ou problème de conversion) l'état est
   * positionné en conséquence.
   * @return le lexème suivant en tant qu'entier (<CODE>double</CODE>)
   */
  public static double readDouble() {
    int tokenType;
    tokenType = theTokenizer.nextToken();
    if (tokenType==MyTokenizer.EOF) {
      error = STREAM_ERROR;
      return 0;
    }
    return stringToDouble(theTokenizer.currentToken());
  }
  /**
   * Lit un mot sur l'entrée.
   * <p>
   * En cas d'erreur (fin du flot) l'état est
   * positionné en conséquence.
   * @return le mot suivant sur l'entrée (<code>null</code> en cas d'échec).
   */
  public static String readString() {
    int tokenType;
    tokenType = theTokenizer.nextToken();
    if (tokenType==MyTokenizer.EOF) {
      error = STREAM_ERROR;
      return null;
    }
    error = NO_ERROR;
    return theTokenizer.currentToken();
  }
  /**
   * Affiche un caractère Unicode.
   * @param c Le caractère à afficher.
   */
  public static void print(char c) {
    System.out.print(c);
  }
  /**
   * Affiche la valeur d'un entier (<code>int</code>)
   * @param i L'entier à afficher
   */
  public static void print(int i) {
    System.out.print(i);
  }
  /**
   * Affiche la valeur d'un booléen (<code>boolean</code>)
   * @param b Le booléen à afficher
   */
  public static void print(boolean b) {
    System.out.print(b);
  }
  /**
   * Affiche la valeur d'un entier long (<code>long</code>)
   * @param l L'entier long à afficher
   */
  public static void print(long l) {
    System.out.print(l);
  }
  /**
   * Affiche la valeur d'un flottant simple précision (<code>float</code>)
   * @param f Le flottant à afficher
   */
  public static void print(float f) {
    System.out.print(f);
  }
  /**
   * Affiche la valeur d'un flottant double précision (<code>double</code>)
   * @param d Le flottant à afficher
   */
  public static void print(double d) {
    System.out.print(d);
  }
  /**
   * Affiche la représentation en <code>String</code> de l'objet
   * @param o L'objet à afficher
   */
  public static void print(Object o) {
    System.out.print(o);
  }
  /**
   * Affiche la chaîne de caractère (<code>String</code>)
   * @param s La chaîne à afficher
   */
  public static void print(String s) {
    System.out.print(s);
  }
  /**
   * Affiche un caractère suivi d'un saut de ligne.
   * Un appel à cette fonction est équivalent à
   * <code>print(c); println();</code>.
   * @param c Le caractère à afficher.
   */
  public static void println(char c) {
    System.out.println(c);
  }
  /**
   * Affiche la valeur d'un booléen (<code>boolean</code>) suivie
   * d'un saut de ligne.
   * Un appel à cette fonction est équivalent à
   * <code>print(b); println();</code>.
   * @param b Le booléen à afficher
   */
  public static void println(boolean b) {
    System.out.println(b);
  }
  /**
   * Affiche la valeur d'un entier (<code>int</code>) suivie
   * d'un saut de ligne.
   * Un appel à cette fonction est équivalent à
   * <code>print(i); println();</code>.
   * @param i L'entier à afficher
   */
  public static void println(int i) {
    System.out.println(i);
  }
  /**
   * Affiche la valeur d'un entier long (<code>long</code>) suivie
   * d'un saut de ligne.
   * Un appel à cette fonction est équivalent à
   * <code>print(l); println();</code>.
   * @param l L'entier long à afficher
   */
  public static void println(long l) {
    System.out.println(l);
  }
  /**
   * Affiche la valeur d'un flottant simple précision (<code>float</code>)
   * suivie d'un saut de ligne.
   * Un appel à cette fonction est équivalent à
   * <code>print(f); println();</code>.
   * @param f Le flottant à afficher
   */
  public static void println(float f) {
    System.out.println(f);
  }
  /**
   * Affiche un flottant double précision suivi d'une fin de ligne.
   * Un tel appel se comporte comme <code>print(d); println()</code>.
   * @param d Le <code>double</code> à afficher.
   */
  public static void println(double d) {
    System.out.println(d);
  }
  public static void println(Object o) {
    System.out.println(o);
  }
  public static void println(String s) {
    System.out.println(s);
  }
  /**
   * Affiche un saut de ligne.
   */
  public static void println() {
    System.out.println();
  }
  public static char charAt(String aString,int position) {
    return aString.charAt(position);
  }
  /**
   * Extrait une sous-chaîne d'une chaîne donnée.
   * @param aString la chaîne d'où extraire
   * @param begin indice du premier caractère à extraire
   * @param end indice du caractère exclu de l'extraction
   * @return la chaîne extraite
   */
  public static String subString(String aString,int begin,int end) {
    return aString.substring(begin,end);
  }
  /**
   * Convertit un booléen en chaîne de caractères.
   * @param value le booléen
   * @return la chaîne
   */
  public static String booleanToString(boolean value) {
    return String.valueOf(value);
  }
  /**
   * Convertit un caractère en une de chaîne de caractères.
   * @param value le caractère
   * @return la chaîne
   */
  public static String charToString(char value) {
    return String.valueOf(value);
  }
  /**
   * Convertit un réel simple en chaîne de caractères.
   * @param value le réel simple précision
   * @return la chaîne
   */
  public static String floatToString(float value) {
    return String.valueOf(value);
  }
  /**
   * Convertit un réel double en de chaîne de caractères.
   * @param value le réel double précision
   * @return la chaîne
   */
  public static String doubleToString(double value) {
    return String.valueOf(value);
  }
  /**
   * Convertit un entier en chaîne de caractères.
   * @param value l'entier
   * @return la chaîne
   */
  public static String intToString(int value) {
    return String.valueOf(value);
  }
  /**
   * Convertit un entier long en chaîne de caractères.
   * @param value l'entier long
   * @return la chaîne
   */
  public static String longToString(long value) {
    return String.valueOf(value);
  }
  /**
   * Compare deux chaînes de caractères.
   * @param aString Une chaîne de caractères.
   * @param anotherString Une chaîne de caractères.
   * @return <code>true</code> si les deux chaînes sont égales,
   * <code>false</code> sinon.
   */
  public static boolean equals(String aString,String anotherString) {
    return aString.equals(anotherString);
  }
  /**
   * Compare deux chaînes de caractères.
   * @param aString une première chaîne
   * @param anotherString une seconde chaîne
   * @return 0 si les deux chaînes sont égales; une valeur négative
   * si la seconde est plus grande que la première dans l'ordre
   * lexicographique et une valeur positive si la première est plus grande
   * (dans l'ordre lexicographique) que la seconde.
   */
  public static int compare(String aString,String anotherString) {
    return aString.compareTo(anotherString);
  }
  /**
   * Calcule la longueur d'une chaîne de caractères.
   * @param aString la chaîne
   * @return la longueur
   */
  public static int length(String aString) {
    return aString.length();
  }
  private static JFrame theFrame = null;
  private static Drawable theDrawable = null;
  /**
   * Prépare une zone d'affichage de taille 300x300.
   */
  public static void startDrawings() {
    startDrawings(300,300);
  }
  private static class GraphicsInitializer implements Runnable {
    private int width, height;
    private JFrame theFrame;
    private Drawable theDrawable;
    public JFrame getTheFrame() {
      return theFrame;
    }
    public Drawable getTheDrawable() {
      return theDrawable;
    }
    public GraphicsInitializer(int w,int h) {
      width = w;
      height = h;
    }
    public void run() {
      theFrame = new JFrame("Dessin");
      theFrame.setResizable(false);
      theDrawable = new Drawable(width,height);
      theFrame.getContentPane().add(theDrawable);
      theFrame.pack();
      theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      theFrame.setVisible(true);
    }
  }
  /**
   * Prépare une zone d'affichage de taille arbitraire.
   * @param width La largeur de la zone d'affichage
   * @param height La hauteur de la zone d'affichage
   */
  public static void startDrawings(int width,int height) {
    if (theFrame!=null) return;
    GraphicsInitializer t = new GraphicsInitializer(width,height);
    try {
      SwingUtilities.invokeAndWait(t);
      theFrame = t.getTheFrame();
      theDrawable = t.getTheDrawable();
    } catch(Exception e) {
      System.err.println("Impossible de créer le contexte graphique lors de l'appel à startDrawings()");
    }
  }
  /**
   * Ferme la fenêtre graphique.
   */
  public static void stopDrawings() {
    if (theFrame==null) return;
    theFrame.dispose();
    theFrame=null;
  }
  /**
   * Dessine un point dans la zone d'affichage.
   * @param x L'abscisse du point.
   * @param y L'ordonnée du point.
   */
  public static void drawPoint(int x,int y) {
    if (theFrame==null)
      throw new RuntimeException("il manque un appel à startDrawings()");
    theDrawable.drawLine(x,y,x,y);
  }
  /**
   * Modifie la couleur de l'encre des points à dessiner.
   * Les couleurs disponibles sont au nombre de 256 <em>niveaux</em> de gris,
   * de <code>0</code> pour coder le noir à <code>255</code> pour
   * coder le blanc. Un gris médian s'obtient donc avec <code>127</code>.
   * @param c La couleur.
   */
  public static void setGray(int c) {
    if (theFrame==null)
      throw new RuntimeException("il manque un appel à startDrawings()");
    theDrawable.setColor(c,c,c);
  }
  /**
   * Modifie la couleur de l'encre des points à dessiner.
   * @param r la valeur du canal rouge
   * @param g la valeur du canal vert
   * @param b la valeur du canal bleu
   */
  public static void setColor(int r,int g,int b) {
    if (theFrame==null)
      throw new RuntimeException("il manque un appel à startDrawings()");
    theDrawable.setColor(r,g,b);
  }
  /**
   * Dessine un segment de droite dans la zone d'affichage.
   * @param x0 L'abscisse du premier point.
   * @param y0 L'ordonnée du premier point.
   * @param x1 L'abscisse du second point.
   * @param y1 L'ordonnée du second point.
   */
  public static void drawLine(int x0,int y0,int x1, int y1) {
    if (theFrame==null)
      throw new RuntimeException("il manque un appel à startDrawings()");
    theDrawable.drawLine(x0,y0,x1,y1);
  }
  /**
   * Dessine le polygone fermé défini par les coordonnées de ses n points
   * @param x Les abcisses des n points.
   * @param y Les ordonnées des n points.
   * @param n le nombre de points définis
   */
  public static void drawPolygon(int []x,int []y,int n) {
    if (theFrame==null)
      throw new RuntimeException("il manque un appel à startDrawings()");
    theDrawable.drawPolygon(x,y,n);
  }
  /**
   * Remplit le polygone fermé défini par les coordonnées de ses n points
   * @param x Les abcisses des n points.
   * @param y Les ordonnées des n points.
   * @param n le nombre de points définis
   */
  public static void fillPolygon(int []x,int []y,int n) {
    if (theFrame==null)
      throw new RuntimeException("il manque un appel à startDrawings()");
    theDrawable.fillPolygon(x,y,n);
  }


  /**
   * Dessine un rectangle vide dans la zone d'affichage.
   * <p>
   * Les droites délimitant le rectangle à gauche et à droite sont
   * situées aux abscisses <code>x</code> et <code>x+l</code>.
   * Celles délimitant le rectangle en haut et en bas sont situées
   * aux ordonnées <code>y</code> et <code>y+h</code>.
   * @param x L'abscisse du coin nord-ouest du rectangle.
   * @param y L'ordonnée du coin nord-ouest du rectangle.
   * @param l La largeur du rectangle.
   * @param h La hauteur du rectangle.
   */
  public static void drawRect(int x,int y,int l, int h) {
    if (theFrame==null)
      throw new RuntimeException("il manque un appel à startDrawings()");
    theDrawable.drawRect(x,y,l,h);
  }

  /**
   * Dessine un rectangle plein dans la zone d'affichage.
   * <p>
   * Les droites délimitant le rectangle à gauche et à droite sont
   * situées aux abscisses <code>x</code> et <code>x+l</code>.
   * Celles délimitant le rectangle en haut et en bas sont situées
   * aux ordonnées <code>y</code> et <code>y+h</code>.
   * @param x L'abscisse du coin nord-ouest du rectangle.
   * @param y L'ordonnée du coin nord-ouest du rectangle.
   * @param l La largeur du rectangle.
   * @param h La hauteur du rectangle.
   */
  public static void fillRect(int x,int y,int l, int h) {
    if (theFrame==null)
      throw new RuntimeException("il manque un appel à startDrawings()");
    theDrawable.fillRect(x,y,l,h);
  }

  /**
   * Dessine un cercle vide dans la zone d'affichage.
   * @param x L'abscisse du centre du cercle.
   * @param y L'ordonnée du centre du cercle.
   * @param r Le rayon du cercle.
   */
  public static void drawCircle(int x,int y,int r) {
    if (theFrame==null)
      throw new RuntimeException("il manque un appel à startDrawings()");
    theDrawable.drawOval(x-r, y-r, 2*r, 2*r);
  }

  /**
   * Dessine un cercle plein dans la zone d'affichage.
   * @param x L'abscisse du centre du cercle.
   * @param y L'ordonnée du centre du cercle.
   * @param r Le rayon du cercle.
   */
  public static void fillCircle(int x,int y,int r) {
    if (theFrame==null)
      throw new RuntimeException("il manque un appel à startDrawings()");
    theDrawable.fillOval(x-r, y-r, 2*r, 2*r);
  }


  /**
   * Dessine un arc de cercle vide dans la zone d'affichage.
   * <p>
   * La valeur des angles est spécifiée en degré.
   * Le sens de rotation est celui des cercles trigonométriques.
   * L'angle de départ est <code>angleInit</code>
   * et l'angle de fin <code>angleInit + angleCouvert</code>
   * @param x L'abscisse du centre du cercle.
   * @param y L'ordonnée du centre du cercle.
   * @param r Le rayon du cercle.
   * @param angleInit Angle de départ.
   * @param angleCouvert Angle de rotation.
   */
  public static void drawArc(int x,int y,int r, int angleInit, int angleCouvert) {
    if (theFrame==null)
      throw new RuntimeException("il manque un appel à startDrawings()");
    theDrawable.drawArc(x-r, y-r, 2*r, 2*r, angleInit, angleCouvert);
  }

  /**
   *
   */
  public static void drawString(int x,int y,String s) {
    if (theFrame==null)
      throw new RuntimeException("il manque un appel à startDrawings()");
    theDrawable.drawString(x,y,s);
  }

  /**
   * Dessine un arc de cercle plein dans la zone d'affichage.
   * <p>
   * La valeur des angles est spécifiée en degré.
   * Le sens de rotation est celui des cercles trigonométriques.
   * L'angle de départ est <code>angleInit</code>
   * et l'angle de fin <code>angleInit + angleCouvert</code>
   * @param x L'abscisse du centre du cercle.
   * @param y L'ordonnée du centre du cercle.
   * @param r Le rayon du cercle.
   * @param angleInit L'angle de départ.
   * @param angleCouvert L'angle de rotation.
   */
  public static void fillArc(int x,int y,int r, int angleInit, int angleCouvert) {
    if (theFrame==null)
      throw new RuntimeException("il manque un appel à startDrawings()");
    theDrawable.fillArc(x-r, y-r, 2*r, 2*r, angleInit, angleCouvert);
  }

  /**
   * Efface intégralement le contenu de la fenêtre graphique.
   */
  public static void clearArea() {
    if (theFrame==null)
      throw new RuntimeException("il manque un appel à startDrawings()");
    theDrawable.clearArea();
  }

  private static void printExternalProcessOutputs(Process p) {
    try {
      BufferedReader bf = new BufferedReader(new InputStreamReader(p.getInputStream()));
      String s;
      while ( (s=bf.readLine())!= null) {
        System.out.println(s);
      }
      bf.close();
    } catch (Exception ex) {
      throw new RuntimeException("Impossible de lire les résultats de la commande");
    }
  }

  /**
   * Exécute une commande par l'intermédiaire d'un processus externe.
   * La commande est spécifiée par son nom (premier argument du tableau) puis 
   * par ses options (arguments à partir du rang 2).
   * @param args Le tableau des arguments
   * @since 0.2
   */
  public static void exec(String []args) {
    Process p = null;
    try {
      p = Runtime.getRuntime().exec(args);
    } catch(Exception ex) {
      throw new RuntimeException("Impossible de lancer "+args[0]);
    }
    printExternalProcessOutputs(p);
    try {
      p.waitFor();
    } catch (Exception ex) {}
  }

  /**
   * Exécute une commande par l'intermédiaire d'un processus externe.
   * La commande est spécifiée par une chaîne de caractère contenant son nom
   * et ses options. Les arguments devant être séparés par des espacements
   * (espace, tabulation).
   * @param command La chaîne de commande
   * @since 0.2
   */
  public static void exec(String command) {
    Process p = null;
    try {
      p = Runtime.getRuntime().exec(command);
    } catch(Exception ex) {
      throw new RuntimeException("Impossible de lancer "+command);
    }
    printExternalProcessOutputs(p);
    try {
      p.waitFor();
    } catch (Exception ex) {}
  }

  private static BufferedReader bf;
  private static String rs;
  /**
   * Cette fonction permet de préparer un fichier de sorte que des lectures
   * puissent y être réalisées.
   * @param nom Le nom du fichier à ouvrir, et qui doit exister préalablement.
   * @return <CODE>Facile.NO_ERROR</CODE> si tout s'est bien passé,
   * <CODE>Facile.OPEN_ERROR</CODE> sinon.
   * @since 0.21
   * @see #fermerFichierEnLecture()
   * @see #lireUneLigneDuFichier()
   */
  public static int ouvrirFichierEnLecture(String nom) {
    if (bf!=null) throw new RuntimeException("il faut fermer le fichier déjà ouvert avant d'en ouvrir un autre");
    try {
      bf = new BufferedReader(new FileReader(nom));
    } catch(FileNotFoundException ex) {
      return OPEN_ERROR;
    }
    return NO_ERROR;
  }
  /**
   * Cette fonction doit être appellée lorsqu'on ne désire plus effectuer 
   * de lecture dans le fichier
   * précédemment ouvert.
   * @see #ouvrirFichierEnLecture(String nom)
   * @since 0.21
   */
  public static void fermerFichierEnLecture() {
    if (bf==null) return;
    try {
      bf.close();
    } catch(IOException ex) {}
    bf = null;
  }
  /**
   * Cette fonction permet d'obtenir une ligne du fichier précédemment ouvert.
   * La ligne est renvoyée sous la forme d'une chaîne de caratères.
   * @return une chaîne de caractères; <CODE>null</CODE> s'il n'y a plus
   * rien à lire.
   * @see #ouvrirFichierEnLecture(String nom)
   * @since 0.21
   */
  public static String lireUneLigneDuFichier() {
    if (bf==null)
      throw new RuntimeException("il faut d'abord ouvrir un fichier avant de tenter de lire...");
    try {
      return bf.readLine();
    } catch(IOException ex) {
      return null;
    }
  }
  private static PrintWriter pw;
  /**
   * Cette fonction permet de préparer un fichier de sorte que des écritures puissent y être 
   * réalisées.
   * @param nom Le nom du fichier à ouvrir, et qui doit exister préalablement.
   * @return <CODE>Facile.NO_ERROR</CODE> si tout s'est bien passé,
   * <CODE>Facile.OPEN_ERROR</CODE> sinon.
   * @since 0.21
   * @see #fermerFichierEnEcriture()
   * @see #ecrireUneLigneDansLeFichier(String)
   */
  public static int ouvrirFichierEnEcriture(String nom) {
    if (pw!=null)
      throw new RuntimeException("il faut fermer le fichier déjà ouvert avant d'en ouvrir un autre");
    try {
      pw = new PrintWriter(new FileWriter(nom));
    } catch(IOException ex) {
      return OPEN_ERROR;
    }
    return NO_ERROR;
  }
  /**
   * Cette fonction doit être appellée lorsqu'on ne désire plus effectuer d'écriture dans le fichier
   * précédemment ouvert.
   * @see #ouvrirFichierEnEcriture(String nom)
   * @since 0.21
   */
  public static void fermerFichierEnEcriture() {
    if (pw==null) return;
    pw.close();
    pw = null;
  }
  /**
   * Cette fonction permet d'écrire une ligne dans le fichier précédemment ouvert.
   * La ligne écrite est constituée de la chaùine de caractères suivie d'un caractère de fin de ligne.
   * @return une chaîne de caractères; <CODE>null</CODE> s'il n'y a plus rien à lire.
   * @see #ouvrirFichierEnEcriture(String nom)
   * @since 0.21
   */
  public static int ecrireUneLigneDansLeFichier(String aEcrire) {
    if (pw==null)
      throw new RuntimeException("il faut d'abord ouvrir un fichier avant de tenter d'y écrire...");
    pw.println(aEcrire);
    return pw.checkError() ? WRITE_ERROR : NO_ERROR;
  }
  /**
   * Cette méthode permet de dormir pour une durée exprimée en
   * millisecondes.
   * @param n La durée du sommeil exprimée en millisecondes.
   * @since 0.23
   */
  public static void sleep(int n) {
    try {
      Thread.sleep(n);
    } catch(Exception e) {
    }
  }
  /**
   * Gets the inner Turtle.
   */
  public static Turtle getTurtle() {
    if (theFrame==null)
      throw new RuntimeException("il manque un appel à startDrawings()");
    return theDrawable.getTurtle();
  }
  /**
   * Shows the turtle on screen.
   */
  public static void setTurtleMode(boolean b) {
    if (theFrame==null)
      throw new RuntimeException("il manque un appel à startDrawings()");
    theDrawable.setTurtleMode(b);
  }
  private static class MyTurtle extends Turtle {
    private Drawable d;
    public MyTurtle(Point2D p,double angle,Drawable d) {
      this(p.getX(),p.getY(),angle,d);
    }
    public MyTurtle(double x,double y,double angle,Drawable d) {
      super(x,y,angle);
      this.d = d;
    }
    public void rectangle(int size) {
      if (d==null) return;
      d.fillRect((int)getState().getX()-size/2,(int)getState().getY()-size/2,
                 size,size);
      d.repaint();
    }
    public MyTurtle(Drawable d) {
      super();
      this.d = d;
    }
    public void lineTo(double x,double y) {
      if (d!=null) d.drawLine((int)(getState().getX()),
                              (int)(getState().getY()),(int)x,(int)y);
      jumpTo(x,y);
    }
    public void jumpTo(double x,double y) {
      super.jumpTo(x,y);
      if (d!=null) d.repaint();
    }
    public void jumpTo(double distance) {
      super.jumpTo(distance);
      if (d!=null) d.repaint();
    }
    public void lineTo(double distance) {
      double newX = getState().getX()+distance*Math.cos(getState().getAngle());
      double newY = getState().getY()+distance*Math.sin(getState().getAngle());
      if (d!=null) d.drawLine((int)(getState().getX()),(int)(getState().getY()),
                              (int)newX,(int)newY);
      jumpTo(distance);
    }
    // public void invalidate() {
    //   d = null;
    // }
  }
}
