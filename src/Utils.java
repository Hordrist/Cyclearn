import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Utils {

    public static int WIDTH = 800;
    public static int HEIGHT = 600;

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

    public static final JComponent getComponentByName(Container container, String inputname){
        if(container != null){
            for(int i=0;i<container.getComponentCount();i++){
                String name = container.getComponent(i).getName();
                if(name != null && name.equals(inputname)){
                    return (JComponent)container.getComponent(i);
                }
                try{
                    JComponent found;
                    Container child = (Container) container.getComponent(i);
                    if(child!=null && child.getComponentCount()>0){
                        found =  getComponentByName(child, inputname);
                        if(found != null){
                            return found;
                        }
                    }
                }
                catch (ClassCastException e){
                }
            }
        }
        return null;
    }

    public static final ArrayList<JComponent> getMultiComponentByName(Container container, String inputname){
        ArrayList<JComponent> components = new ArrayList<JComponent>();
        if(container != null){
            for(int i=0;i<container.getComponentCount();i++){
                String name = container.getComponent(i).getName();
                if(name != null && name.equals(inputname)){
                    components.add((JComponent)container.getComponent(i));
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

}
