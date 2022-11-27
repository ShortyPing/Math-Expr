/*
Copyright (c) 2015 - 2022 Michael Steinmötzger
All rights are reserved for this project, unless otherwise
stated in a license file.
*/

package dev.steinmoetzger.mathlang.memory;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class Universe {

    private final HashMap<String, Double> definedVariables;
    private final UUID uuid;

    public Universe() {
        this.definedVariables = new HashMap<>();
        this.uuid = UUID.randomUUID();
    }

    public HashMap<String, Double> getDefinedVariables() {
        return definedVariables;
    }

    public UUID getUuid() {
        return uuid;
    }


}
