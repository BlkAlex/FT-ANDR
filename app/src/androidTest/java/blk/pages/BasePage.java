package blk.pages;


import blk.elements.DrawerElement;

public abstract class BasePage {

    private final DrawerElement drawer;

    public BasePage() {
        this.drawer = new DrawerElement();
    }

    public DrawerElement getDrawer() {
        return drawer;
    }

}

