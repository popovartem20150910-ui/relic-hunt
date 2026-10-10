# Relic Hunt — actual NeoForge source tree (work in progress)

This is the beginning of a **real NeoForge 1.21.1 port**, not a renamed Fabric JAR.

Implemented so far: NeoForge Gradle configuration, NeoForge mod metadata, @Mod entrypoint, deferred item/block registries for ruby ore, polishing stone, raw ruby, ruby shard and ruby.

Not yet ported: relic abilities, maces, tools, doors, textures/assets, recipes, update checker, creative tabs, polishing interaction, TaCZ gun pack.

**Do not install a JAR built from this directory as a complete Relic Hunt.** Compilation has not yet been verified. The top-level repository remains the Fabric project until the port is ready.

To build locally after installing Java 21 and Gradle 8.12+: `cd neoforge && gradle build`. The repository's current GitHub Actions still builds the top-level Fabric project.
