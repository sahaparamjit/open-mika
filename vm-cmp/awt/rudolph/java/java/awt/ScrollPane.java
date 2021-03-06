/**************************************************************************
* Parts copyright (c) 2001, 2002, 2003 by Punch Telematix. All rights     *
* reserved.                                                               *
* Parts copyright (c) 2012 by Chris Gray, /k/ Embedded Java Solutions.    *
* All rights reserved.                                                    *
*                                                                         *
* Redistribution and use in source and binary forms, with or without      *
* modification, are permitted provided that the following conditions      *
* are met:                                                                *
* 1. Redistributions of source code must retain the above copyright       *
*    notice, this list of conditions and the following disclaimer.        *
* 2. Redistributions in binary form must reproduce the above copyright    *
*    notice, this list of conditions and the following disclaimer in the  *
* 3. Neither the name of Punch Telematix or of /k/ Embedded Java Solutions*
*    nor the names of other contributors may be used to endorse or promote*
*    products derived from this software without specific prior written   *
*    permission.                                                          *
*                                                                         *
* THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESS OR IMPLIED          *
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF    *
* MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.    *
* IN NO EVENT SHALL PUNCH TELEMATIX, /K/ EMBEDDED JAVA SOLUTIONS OR OTHER *
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,   *
* EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,     *
* PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR      *
* PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF  *
* LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING    *
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS      *
* SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.            *
**************************************************************************/

package java.awt;

import java.awt.Toolkit;
import java.awt.event.*;
import java.awt.peer.*;


public class ScrollPane extends Container implements AdjustmentListener, ComponentListener {

  // TODO: ScrollPane should not implement AdjustmentListener - use an inner/helper-class or so?

  public final static int SCROLLBARS_AS_NEEDED = 0;
  public final static int SCROLLBARS_ALWAYS = 1;
  public final static int SCROLLBARS_NEVER = 2;

  private Scrollbar hsb;
  private Scrollbar vsb;

  private int vwidth;
  private int vheight;

  private int scrollbarDisplayPolicy;

  private Component comp = null;
  private Panel viewport = new Panel(null);
  private Panel componentPanel;

  private boolean loop = false;

  public ScrollPane() {
    this(SCROLLBARS_AS_NEEDED);
  }
  
  public ScrollPane(int policy) {
    if (policy < 0 || policy > 2) {
      throw new IllegalArgumentException("invalid policy specified : " + policy);
    } 
  
    hsb = new Scrollbar(Scrollbar.HORIZONTAL, 0, 5, 0, 5);
    vsb = new Scrollbar(Scrollbar.VERTICAL, 0, 5, 0, 5);
    
    hsb.setVisible(false);
    vsb.setVisible(false);
    
    hsb.addAdjustmentListener(this);
    vsb.addAdjustmentListener(this);
    
    scrollbarDisplayPolicy = policy;

    setLayout(new ScrollPaneLayout());

    add(hsb, ScrollPaneLayout.HSB);
    add(vsb, ScrollPaneLayout.VSB);
    add(viewport, ScrollPaneLayout.VIEW);

    invalidate();
  }

  public void addNotify() {
    if (peer == null) {
      peer = toolkit.createScrollPane(this);
    }

    if (notified == false) {
      super.addNotify();
    }
  }

  protected final void addImpl(Component component, Object constraints, int position) {

    /*
    ** A ScrollPane can only have one child component so we remove
    ** existing child components, if any.
    */
  
    component.addComponentListener(this);

    if (component != hsb && component != vsb && component != viewport) {
      
      if (comp != null) {
        if (comp instanceof Container) {
          viewport.remove(comp);
        } 
        else {
          viewport.remove(componentPanel);
        }
      }

      comp = component;
      
      if (component instanceof Container) {
        viewport.add(comp);
      } 
      else {
        componentPanel = new Panel();
        componentPanel.add(comp);
        viewport.add(componentPanel);
      }

      doLayout();
    } 
    else {
      super.addImpl(component, constraints, position);
    }
  }

  public void doLayout() {
    loop = true;

    super.doLayout();

    /*
    ** Get the size of the scrollpane
    */

    vwidth = getSize().width;
    vheight = getSize().height;

    if (comp != null) {
      
      Dimension ps = comp.getPreferredSize();

      if(ps == null) ps = new Dimension(0, 0);
    
      /*
      ** Decide wether to show the scrollbars.
      */

      switch(scrollbarDisplayPolicy) {
      
        case SCROLLBARS_NEVER:   
          break;

        case SCROLLBARS_ALWAYS: 
          hsb.setVisible(true);
          vheight -= getHScrollbarHeight();
          vsb.setVisible(true);
          vwidth -= getVScrollbarWidth();
          break;

        case SCROLLBARS_AS_NEEDED:
          if(ps.width > vwidth) {
            hsb.setVisible(true); 
            vheight -= getHScrollbarHeight();
          } 
          else {
            hsb.setVisible(false);
          }
          if(ps.getSize().height > vheight) {
            vsb.setVisible(true); 
            vwidth -= getVScrollbarWidth();
          } 
          else {
            vsb.setVisible(false);
          }
          break;
      }

      /*
      ** Calculate the scrollbar properties.
      */

      hsb.setMaximum(Math.max(0, ps.width - vwidth) + vwidth);
      vsb.setMaximum(Math.max(0, ps.height - vheight) + vheight);
      hsb.setVisibleAmount(vwidth);
      vsb.setVisibleAmount(vheight);

      /*
      ** Make the component grow larger if it's not as wide or
      ** not as high as the viewport
      */

      if (ps.width < vwidth) {
        ps.width = vwidth;
      }

      if (ps.height < vheight) {
        ps.height = vheight;
      }

      /*
      ** Change the size of the component (or the panel which
      ** contains the component)
      */
      
      toolkit.lockAWT();
      try {
        viewport.setBounds(0, 0, vwidth, vheight);
        
        if (comp instanceof Container) {
          comp.setBounds(0, 0, ps.width, ps.height);
        } 
        else {
          componentPanel.setBounds(0, 0, ps.width, ps.height);
          comp.setBounds(0, 0, ps.width, ps.height);
        }

        // TODO: get rid of componentPanel.  Note that this cludge is also being used in ScrollPanePeer ...

        ((ScrollPanePeer)peer).childResized(vwidth, vheight);
      } finally {
        toolkit.unlockAWT();
      }
    }

    loop = false;
  }

  public int getScrollbarDisplayPolicy() {
    return scrollbarDisplayPolicy;
  }

  public Adjustable getHAdjustable() {
    return hsb;
  }

  public Adjustable getVAdjustable() {
    return vsb;
  }

  public int getHScrollbarHeight() {
    return hsb.getHeight();
  }

  public Point getScrollPosition() {
    return new Point(hsb.getValue(), vsb.getValue());
  }

  public int getVScrollbarWidth() {
    return vsb.getWidth();
  }
 
  public Dimension getViewportSize() {
    return new Dimension(vwidth, vheight);
  }

  public void setScrollPosition(Point p) {
    setScrollPosition(p.x, p.y);
  }
 
  public void setScrollPosition(int x, int y) {
    if (x > comp.getSize().width - vwidth) {
      x = Math.max(0, comp.getSize().width - vwidth);
    }
    if (y > comp.getSize().height - vheight) {
      y = Math.max(0, comp.getSize().height - vheight);
    }

    hsb.setValue(x);
    vsb.setValue(y);
      // TODO: does these calls cause 'ScrollPane.adjustmentValueChanged()' to be invoked?  If so, we are redrawing the ScrollPane three times ...

    ((ScrollPanePeer)peer).setScrollPosition(x, y);
  }

  public void adjustmentValueChanged(AdjustmentEvent event) {
    ((ScrollPanePeer)peer).setValue(event.getAdjustable(), event.getValue());
  }

  public String paramString() {
    return "";
  }

  public void printComponents(Graphics g) {
    System.out.println("[TODO] java.awt.ScrollPane.printComponents(Graphics g)");
  }

  // TODO: java.awt.Component.getParent() is not overridden here so I think comp.getParent() would return the dummy panel, componentPanel, which sounds like a bug really ...  Anyhow, we should try to get rid of componentPanel.

  public Component getComponent(int index) {
    if (comp != null && index == 0) {
      return comp;
    } 
    else {
      throw new ArrayIndexOutOfBoundsException();
    }
  }
  
  public int getComponentCount() {
    if (comp != null) {
      return 1;
    } 
    else {
      return 0;
    }
  }
  
  public Component[] getComponents() {
    Component[] list = new Component[0];

    if (comp != null) {
      list = new Component[1];
      list[0] = comp;
    }

    return list;
  }

  public void componentHidden(ComponentEvent event) {
    if(!loop) doLayout();
  }
  
  public void componentMoved(ComponentEvent event) {
    if(!loop) doLayout();
  }
  
  public void componentResized(ComponentEvent event) {
    if(!loop) doLayout();
  }
  
  public void componentShown(ComponentEvent event) {
    if(!loop) doLayout();
  }

}

