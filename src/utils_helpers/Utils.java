package utils_helpers;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Utils {

    public static int WIDTH = 800;
    public static int HEIGHT = 600;
    public static int NUMBER_OF_OPENED_WINDOWS = 0;

    public static final int getComponentIndex(JComponent component) {
        if (component != null && component.getParent() != null) {
            Container c = component.getParent();
            for (int i = 0; i < c.getComponentCount(); i++) {
                if (c.getComponent(i) == component)
                    return i;
            }
        }
        return -1;
    }

    public static final <T> T getComponentByName(Container container, String inputname){
        if(container != null){
            for(int i=0;i<container.getComponentCount();i++){
                String name = container.getComponent(i).getName();
                if(name != null && name.equals(inputname)){
                    return (T)container.getComponent(i);
                }
                try{
                    JComponent found;
                    Container child = (Container) container.getComponent(i);
                    if(child!=null && child.getComponentCount()>0){
                        found =  getComponentByName(child, inputname);
                        if(found != null){
                            return (T)found;
                        }
                    }
                }
                catch (ClassCastException e){
                }
            }
        }
        return null;
    }

    public static final <T> ArrayList<T> getMultiComponentByName(Container container, String inputname){
        ArrayList<T> components = new ArrayList<T>();
        if(container != null){
            for(int i=0;i<container.getComponentCount();i++){
                String name = container.getComponent(i).getName();
                if(name != null && name.equals(inputname)){
                    components.add((T)container.getComponent(i));
                }
            }
            for (int i = 0; i < container.getComponentCount(); i++) {
                try{
                    Container child = (Container) container.getComponent(i);
                    if(child!=null && child.getComponentCount()>0){
                        components.addAll(getMultiComponentByName(child, inputname));
                    }
                }
                catch (ClassCastException e){
                }
            }
        }
        return components;
    }

    public static Dimension getDimensionToFill(Container container, ToFill to_fill) {
        Dimension dimension = container.getSize();
        for (Component component : container.getComponents()) {
            switch (to_fill){
                case FILL_BOTH:
                    dimension.width -= component.getPreferredSize().width;
                    dimension.height -= component.getPreferredSize().height;
                    break;
                case FILL_WIDTH:
                    dimension.width -= component.getPreferredSize().width;
                    break;
                case FILL_HEIGHT:
                    dimension.height -= component.getPreferredSize().height;
                    break;
            }
        }
        return dimension;
    }

    public static enum ToFill{
        FILL_HEIGHT, FILL_WIDTH, FILL_BOTH
    }

}
