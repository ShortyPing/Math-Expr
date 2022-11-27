/*
Copyright (c) 2015 - 2022 Michael Steinmötzger
All rights are reserved for this project, unless otherwise
stated in a license file.
*/

package dev.steinmoetzger.mathlang.memory;

import java.util.ArrayList;

public class UniverseManager {

    private final ArrayList<Universe> registeredUniverses;
    private Universe currentUniverse;



    public UniverseManager() {
        this.registeredUniverses = new ArrayList<>();
    }
    public Universe registerUniverse() {
        Universe universe = new Universe();
        registeredUniverses.add(universe);
        System.out.println("Registered new universe (" + universe.getUuid().toString() + ")");
        return universe;
    }

    public void unregisterUniverse(Universe universe) {
        if(universe == null) return;

        registeredUniverses.remove(universe);
        System.out.println("Unregistered universe (" + universe.getUuid().toString() + ")");
    }

    public Universe getCurrentUniverse() {
        return currentUniverse;
    }

    public ArrayList<Universe> getRegisteredUniverses() {
        return registeredUniverses;
    }

    public void setCurrentUniverse(Universe currentUniverse) {
        this.currentUniverse = currentUniverse;
    }
}
