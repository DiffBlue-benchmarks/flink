package org.nakedobjects.object.exploration;

import org.nakedobjects.object.NakedObject;
import org.nakedobjects.object.NakedObjectSpecification;
import org.nakedobjects.object.NakedObjectSpecificationLoader;
import org.nakedobjects.object.persistence.NakedObjectManager;

import java.util.Vector;

import org.apache.log4j.Logger;


public abstract class ExplorationSetUp {
    private static final Logger LOG = Logger.getLogger(ExplorationSetUp.class);

    private NakedObjectManager objectManager;
    private Vector classes;
    private Vector fixtures;
    private Vector newInstances = new Vector();

    public ExplorationSetUp() {
        classes = new Vector();
        fixtures = new Vector();
    }

    public void installFixtures() {
        objectManager.startTransaction();

        for (int i = 0, last = fixtures.size(); i < last; i++) {
            ExplorationFixture fixture = (ExplorationFixture) fixtures.elementAt(i);
            fixture.setContainer(this);
            fixture.install();
        }

        // make all new objects persistent
        try {
            for (int i = 0; i < newInstances.size(); i++) {
                NakedObject object = (NakedObject) newInstances.elementAt(i);
                LOG.info("Persisting " + object);

                boolean notPersistent = object.getOid() == null;
                if (notPersistent) {
                    objectManager.makePersistent(object);
                }
            }
            objectManager.endTransaction();
        } catch (RuntimeException e) {
            objectManager.abortTransaction();
        }

        //       setInitialUser();
    }

/*    private NakedObjectManager getObjectManager() {
        if (objectManager == null) {
            objectManager = context.getObjectManager();
        }

        return objectManager;
    }
*/
    
    
    private void addInstance(NakedObject object) {
        LOG.info("Adding " + object);
        newInstances.addElement(object);
    }

    /**
     * Helper method to create an instance of the given type. Provided for
     * exploration programs that need to set up instances.
     */
    protected final Object createInstance(Class type) {
        NakedObjectSpecification nc = NakedObjectSpecificationLoader.getInstance().loadSpecification(type.getName());
        if (nc == null) {
            objectManager.generatorError("Could not create an object of class " + type, null);
        }
        return createInstance(nc);
    }

    /**
     * Helper method to create an instance of the given type. Provided for
     * exploration programs that need to set up instances.
     */
    protected final Object createInstance(String className) {
        NakedObjectSpecification nc = NakedObjectSpecificationLoader.getInstance().loadSpecification(className);
        if (nc == null) {
            return objectManager.generatorError("Could not create an object of class " + className, null);
        }
        return createInstance(nc);
    }

    private Object createInstance(NakedObjectSpecification nc) {
        NakedObject object = objectManager.createTransientInstance(nc);
        addInstance(object);
        return object.getObject();
    }

    public void registerClass(String className) {
        classes.addElement(className);
    }

    public String[] getClasses() {
        String[] classNames = new String[classes.size()];
        classes.copyInto(classNames);
        return classNames;
    }

    public void addFixture(ExplorationFixture fixture) {
        fixtures.addElement(fixture);
    }

    public void setObjectManager(NakedObjectManager objectManager) {
        this.objectManager = objectManager;
    }
    
    /**
     * Expose as a .NET property
     * 
     * @property
     */
    public void set_ObjectManager(NakedObjectManager objectManager) {
        this.objectManager = objectManager;
    }

}

/*
 * Naked Objects - a framework that exposes behaviourally complete business
 * objects directly to the user. Copyright (C) 2000 - 2005 Naked Objects Group
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