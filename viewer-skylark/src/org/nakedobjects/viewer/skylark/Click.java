package org.nakedobjects.viewer.skylark;

/**
 * Describes a mouse click event.
 */
public class Click extends PointerEvent {
    protected final Location location;
    protected final Location locationWithinViewer;

    /**
     * Creates a new click event object.
     * 
     * @param view
     *                       the view over which the pointer was when this click occurred
     * @param location
     *                       the location within the specified view
     * @param mods
     *                       the button and key held down during the click (@see
     *                       java.awt.event.MouseEvent)
     */
    public Click(View view, Location location, Location locationWithinViewer, int mods) {
        super(view, location, mods);

        this.location = location;
        this.locationWithinViewer = locationWithinViewer;
    }

    /**
     * Returns the location of the mouse, within the view that was clicked on.
     */
    public Location getLocation() {
        return location;
    }

    public Location getLocationWithinViewer() {
        return locationWithinViewer;
    }

    /**
     * Returns the view that clicked on.
     */
    public View getView() {
        return view;
    }

    /**
     * Translate the location of this event by the specified offset.
     */
    public void move(int x, int y) {
        location.move(x, y);
    }

    public String toString() {
        return "Click [type=" + type + "," + super.toString() + "]";
    }
}

/*
 * Naked Objects - a framework that exposes behaviourally complete business
 * objects directly to the user. Copyright (C) 2000 - 2003 Naked Objects Group
 * Ltd
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 * The authors can be contacted via www.nakedobjects.org (the registered address
 * of Naked Objects Group is Kingsway House, 123 Goldworth Road, Woking GU21
 * 1NR, UK).
 */