# Relic Hunt — Minecraft 1.21.1 (Fabric)

Lightning Crystal: right-click to summon lightning at the block you aim at (up to 20 blocks away); 5-second cooldown.

## Get the JAR (no Java installation needed)

1. Upload the **contents of this folder** to a GitHub repository (not the ZIP file itself). Keep `.github/workflows/build.yml` in its original folder.
2. Open **Actions** > **Build Fabric mod** > latest successful run.
3. Download **relic-hunt-1.21.1** artifact, extract its ZIP and put `relic-hunt-0.1.0.jar` in `.minecraft/mods`.
4. Install Minecraft **1.21.1**, Fabric Loader and Fabric API for 1.21.1.

Test with `/give @s relic_hunt:lightning_crystal`.

**Note:** This project has not been compiled in this environment. GitHub Actions will perform the build and show any errors.
