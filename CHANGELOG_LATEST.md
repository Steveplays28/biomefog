### Added

- World blacklisting
    - Via commands `/biomefog worldblacklist add` and `/biomefog worldblacklist remove`
    - Saved to `config/biome_fog/biome_fog_blacklisted_worlds.json`

### Changed

- Moved configuration files into the `config/biome_fog/` directory

### Fixed

- Lava fog taking biome fog color instead of normal red color
- Water fog not behaving in the same way as vanilla, with the fog start being close and gradually moving further away
  after entering the water
