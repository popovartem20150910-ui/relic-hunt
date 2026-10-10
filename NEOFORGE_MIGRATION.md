# Relic Hunt — NeoForge 1.21.1 migration

**Status: migration in progress; this branch is NOT yet a NeoForge mod.**

Source branch: `feature/relic-hunt-0.2.0`. Keep that Fabric branch and its releases working while porting.

## Migration checklist

- [ ] Replace Fabric Loom with NeoGradle or ModDevGradle configured for Minecraft 1.21.1 and a compatible NeoForge 21.1.x version.
- [ ] Replace `fabric.mod.json` with `META-INF/neoforge.mods.toml` and a NeoForge `@Mod` entrypoint.
- [ ] Port item and block registration to `DeferredRegister`, retaining every existing registry ID (`relic_hunt:...`) for saved-world compatibility.
- [ ] Port Fabric use-block callback, client update checker, item groups, and all relic behaviors to NeoForge events/APIs.
- [ ] Port Yarn-mapped Java sources to Mojang official mappings; adapt constructor signatures and item properties.
- [ ] Verify data assets, recipes, doors, item models, and existing textures in-game.
- [ ] Add a **separate** GitHub Actions NeoForge build that uploads a distinctly named artifact. Do not label Fabric JARs as NeoForge.
- [ ] Test dedicated server and multiplayer with matching mod versions.
- [ ] Verify the chosen **third-party TaCZ 1.21.1 NeoForge port** in a clean instance before declaring compatibility; check its distribution permissions and gun-pack format.
- [ ] Create a standalone Relic Hunt gun pack with original 3D models, animations, sounds and definitions; do not copy third-party assets without permission.

## Current limitation

The source code inherited from Fabric does **not** load on NeoForge. This branch is a safe staging point, not a downloadable NeoForge build. Do not install its current JAR in a NeoForge instance.
