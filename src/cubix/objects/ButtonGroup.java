package cubix.objects;

import java.util.List;

public class ButtonGroup {
    private List<Button> buttons = new java.util.LinkedList<>();
    private int activeIndex;

    public ButtonGroup(List<Button> buttonList)
    {
        buttons = buttonList;
        activeIndex = 0;
        buttons.get(0).select();
    }

    public void up_select()
    {
        buttons.get(activeIndex--).deselect();
        if (activeIndex < 0)
        {
            activeIndex = buttons.size()-1;
        }
        buttons.get(activeIndex).select();
    }

    public void down_select()
    {
        buttons.get(activeIndex++).deselect();
        if (activeIndex >= buttons.size())
        {
            activeIndex = 0;
        }
        buttons.get(activeIndex).select();
    }

    public int get_selected_index()
    {
        return activeIndex;
    }

}
