package mil.af.eglin.ccf.rt.fx.control.skins;

import com.sun.javafx.scene.control.skin.AccordionSkin;

import javafx.collections.ListChangeListener;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;

public class RtAccordionSkin extends AccordionSkin
{
    public RtAccordionSkin(final Accordion accordion)
    {
        super(accordion);

        accordion.getPanes().addListener((ListChangeListener<TitledPane>) c -> 
        {
            while (c.next()) 
            {
                for (final TitledPane tp: c.getAddedSubList())
                {
                    System.out.println("Test");
                }
            }
        });
        for (final TitledPane tp: accordion.getPanes())
        {
            System.out.println("Test");
        }
    }
}
