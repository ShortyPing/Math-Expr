/*
Copyright (c) 2015 - 2022 Michael Steinmötzger
All rights are reserved for this project, unless otherwise
stated in a license file.
*/

package dev.steinmoetzger.mathlang.memory;

import java.util.ArrayList;
import java.util.HashMap;

public class UniverseManager {

    private final HashMap<String, Universe> registeredUniverses;
    private Universe currentUniverse;



    public UniverseManager() {
        this.registeredUniverses = new HashMap<>();
    }
    public Universe registerUniverse() {
        Universe universe = new Universe();
        registeredUniverses.put(universe.getUuid().toString(), universe);
        System.out.println("Registered new universe (" + universe.getUuid().toString() + ")");
        return universe;
    }

    public void unregisterUniverse(Universe universe) {
        if(universe == null) return;
        if(currentUniverse == universe)
            currentUniverse = null;

        registeredUniverses.remove(universe.getUuid().toString());
        System.out.println("Unregistered universe (" + universe.getUuid().toString() + ")");

    }

    public Universe getCurrentUniverse() {

        if(currentUniverse == null) {
            System.out.println("Trying to access current universe although none is selected. Creating new one...");
            currentUniverse = registerUniverse();
        }

        return currentUniverse;
    }

    public Universe getCurrentUniverseNoCheck() {

        return currentUniverse;
    }

    public HashMap<String, Universe> getRegisteredUniverses() {
        return registeredUniverses;
    }

    public void setCurrentUniverse(Universe currentUniverse) {
        this.currentUniverse = currentUniverse;
    }


}
